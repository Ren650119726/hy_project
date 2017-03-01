package com.mockuai.dts.core.manager.impl;

import com.mockuai.dts.common.TaskStatusEnum;
import com.mockuai.dts.common.constant.Constants;
import com.mockuai.dts.common.constant.ResponseCode;
import com.mockuai.dts.common.domain.ItemExportQTO;
import com.mockuai.dts.core.api.impl.OSSClientAPI;
import com.mockuai.dts.core.dao.ExportTaskDAO;
import com.mockuai.dts.core.domain.ExportTaskDO;
import com.mockuai.dts.core.exception.DtsException;
import com.mockuai.dts.core.manager.ItemExportManager;
import com.mockuai.dts.core.util.ExceptionUtil;
import com.mockuai.dts.core.util.FileUtil;
import com.mockuai.dts.core.util.OSSFileLinkUtil;
import com.mockuai.itemcenter.client.ItemBrandClient;
import com.mockuai.itemcenter.client.ItemCategoryClient;
import com.mockuai.itemcenter.client.ItemClient;
import com.mockuai.itemcenter.client.ItemSkuClient;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.domain.dto.ItemCategoryDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemSkuDTO;
import com.mockuai.itemcenter.common.domain.dto.SellerBrandDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemCategoryQTO;
import com.mockuai.itemcenter.common.domain.qto.ItemQTO;
import com.mockuai.itemcenter.common.domain.qto.ItemSkuQTO;
import com.mockuai.itemcenter.common.domain.qto.SellerBrandQTO;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.File;
import java.util.*;

/**
 * luliang
 */
public class ItemExportManagerImpl implements ItemExportManager {

    private static final Logger log = LoggerFactory.getLogger(ItemExportManagerImpl.class);

    @Resource
    private ExportTaskDAO exportTaskDAO;

    @Resource
    private OSSClientAPI ossClientAPI;

    @Resource
    private ItemClient itemClient;

    @Resource
    private ItemSkuClient itemSkuClient;

    @Resource
    private ItemCategoryClient itemCategoryClient;

    @Resource
    private ItemBrandClient itemBrandClient;

    @Override
    public boolean exportItems(ItemExportQTO itemExportQTO, ExportTaskDO exportTaskDO) throws DtsException {

        log.info("enter export task.");
        System.out.println("enter");
        // 每次查询500条,然后存储;
        int offset = 0;
        int count = 500;
        int page = 1;
        ItemQTO itemQto = new ItemQTO();

        //只查询普通类型的商品
        itemQto.setItemType(1);
        boolean byKeyWord = false, byId = false;
        String keyWord = null;
        Long itemId = null;
        if(!StringUtils.isBlank(itemExportQTO.getKey())){
            byKeyWord = true;
            if(StringUtils.isNumeric(itemExportQTO.getKey())){ // 按id查询
                itemId = Long.valueOf(itemExportQTO.getKey());
                byId = true;
            }else{ // 产品关键字查询
                keyWord = itemExportQTO.getKey();
            }
        }

        Long sellerId = itemExportQTO.getSellerId();
        itemQto.setSellerId(sellerId);
        itemQto.setCategoryId(itemExportQTO.getCategoryId());

        String barCode = itemExportQTO.getBarCode();
        Long brandId = itemExportQTO.getBrandId();
        Integer itemStatus = itemExportQTO.getItemStatus();

        //如果不是根据二维码来查询
        if(StringUtils.isEmpty(barCode)){
            if(byKeyWord){
                if(byId){
                    itemQto.setId(itemId);
                }else{
                    itemQto.setItemName(keyWord);
                }
            }
            itemQto.setItemBrandId(brandId);
            itemQto.setItemStatus(itemStatus);
            //按照了录入的时间范围查询
            itemQto.setCreateTimeBegin(itemExportQTO.getStartTime());
            itemQto.setCreateTimeEnd(itemExportQTO.getEndTime());
        }

        if(!StringUtils.isEmpty(barCode)){
            List<Long> itemIdList = queryItemIdByBarCode(barCode, sellerId, exportTaskDO.getAppKey());
            if(CollectionUtils.isEmpty(itemIdList)){
                // return
                return false;
            }
            System.out.println("itemIdList: " + itemIdList);
            itemQto.setIdList(itemIdList);
        }

        // 文件名;
        String tmpFileName = exportTaskDO.getOssObjectKey();

        List<ItemDTO> itemDTOs = getItemDTOs(itemQto, exportTaskDO.getAppKey());

        List<Long> itemIdList = new ArrayList<Long>();

        for(ItemDTO itemDTO :itemDTOs){
            itemIdList.add(itemDTO.getId());
        }

        ItemSkuQTO itemSkuQTO = new ItemSkuQTO();
        itemSkuQTO.setSellerId(sellerId);
        itemSkuQTO.setItemIdList(itemIdList);


        while (true) {
            try{
                // 500条一页;
                itemSkuQTO.setPageSize(count);
                // from 0page;
                itemSkuQTO.setCurrentPage(page);
                itemSkuQTO.setNeedPaging(true);



                List<ItemSkuDTO> itemSkuDTOs = getItemSkuDTOs(itemSkuQTO, exportTaskDO.getAppKey());


                FileUtil.writeItems(tmpFileName, itemDTOs,itemSkuDTOs);
                offset += itemSkuDTOs.size();
                if(itemSkuDTOs.size() < 500) {
                    // 最后一页可以退出了;
                    break;
                }

                long total = itemSkuQTO.getTotalCount();
                // 更新进度;
                int process = 0;
                if(total == 0) {
                    process = 0;
                } else {
                    process = (int)(offset / total) * 100 / 2;
                }

                exportTaskDO.setTaskProcess(process);
                exportTaskDO.setTaskStatus(TaskStatusEnum.RUNNING_TASK.getStatus());
                exportTaskDAO.updateTask(exportTaskDO);
                page++;

            }catch(Exception e){
                FileUtil.destroyFile(tmpFileName);
                throw new DtsException(ResponseCode.SYS_E_SERVICE_EXCEPTION, e.getCause());
            }
        }
        // 上传到OSS;
        ossClientAPI.uploadFile(tmpFileName, FileUtil.getTmpFilePath(tmpFileName));
//        ossClientAPI.multipartUpload(tmpFileName, FileUtil.getTmpFilePath(tmpFileName));
        // 完成;
        exportTaskDO.setTaskProcess(100);
        exportTaskDO.setTaskStatus(TaskStatusEnum.COMPLETE_TASK.getStatus());
        exportTaskDO.setFileLink(OSSFileLinkUtil.
                generateFileLink(exportTaskDO.getOssBucketName(), exportTaskDO.getOssObjectKey()));
        exportTaskDAO.updateTask(exportTaskDO);
        // 上传完之后删除;
        FileUtil.destroyFile(tmpFileName);
        return true;
    }

    private List<ItemSkuDTO> getItemSkuDTOs(ItemSkuQTO itemSkuQto, String appKey) throws DtsException {
        Response<List<ItemSkuDTO>> response  = null;

        try{
            response = itemSkuClient.queryItemSku(itemSkuQto,appKey);
        }catch(Exception e){
            throw ExceptionUtil.getException(ResponseCode.SYS_E_DEFAULT_ERROR,"");
        }

        if(response.isSuccess()){
            itemSkuQto.setTotalCount((int) response.getTotalCount());
            return response.getModule();
        }else {
            throw ExceptionUtil.getException(ResponseCode.SYS_E_DEFAULT_ERROR,"");
        }
    }

    /**
     * 如果是根据条码查询商品 需要先查找到sku 在找到对应的id
     * @param barCode
     * @param sellerId
     * @return
     */
    private List<Long> queryItemIdByBarCode(String barCode,Long sellerId, String appKey)throws DtsException{
        ItemSkuQTO itemSkuQTO =new ItemSkuQTO();
        itemSkuQTO.setBarCode(barCode);
        itemSkuQTO.setSellerId(sellerId);

        Response<List<ItemSkuDTO>> response =null;
        response = this.itemSkuClient.queryItemSku(itemSkuQTO, appKey);
        List<ItemSkuDTO> list = null;
        if (response.getCode() == Constants.SERVICE_PROCESS_SUCCESS) {
            list = response.getModule();
        } else {
            throw new DtsException(response.getCode(),
                    response.getMessage());
        }
        if(CollectionUtils.isEmpty(list))
            return null;
        //
        Set<Long> itemIdSet =new HashSet<Long>();
        for(ItemSkuDTO item : list){
            if(item.getItemId() != null){
                itemIdSet.add(item.getItemId());
            }
        }
        List<Long> result = new ArrayList<Long>();
        result.addAll(itemIdSet);
        return result;
    }

    private List<ItemDTO> getItemDTOs(ItemQTO itemQto, String appKey) throws DtsException {
        Response<List<ItemDTO>> serviceResponse = null;
        serviceResponse = queryItem(itemQto, appKey);

        //构造参数去查询类目名称 和 品牌名称
        // 后续可能需要优化
        Set<Long> brandIdSet = new HashSet<Long>();
        Set<Long> categoryIdSet =new HashSet<Long>();
        if(!CollectionUtils.isEmpty(serviceResponse.getModule())){
            for(ItemDTO item : serviceResponse.getModule()){
                brandIdSet.add(item.getItemBrandId());
                categoryIdSet.add(item.getCategoryId());
            }

            Long[] brandIds = new Long[brandIdSet.size()];
            brandIdSet.toArray(brandIds);
            List<Long> categoryIds = new ArrayList<Long>(categoryIdSet);


            ItemCategoryQTO categoryQTO = new ItemCategoryQTO();
            categoryQTO.setIdList(categoryIds);

            SellerBrandQTO sellerBrandQTO =new SellerBrandQTO();
            sellerBrandQTO.setIds(brandIds);

            List<ItemCategoryDTO> categoryList =null;
            List<SellerBrandDTO> brandList = null;
            try{
                categoryList = queryCategory(categoryQTO, appKey);
                brandList = querySellerBrand(sellerBrandQTO, appKey);
            }catch(Throwable e){
//                return ServiceResponseHandler.serviceExceptionHandler(e);
            }
            Map<Long,String> brandNameMap =new HashMap<Long,String>();
            Map<Long,String> categoryNameMap =new HashMap<Long,String>();

            if(!CollectionUtils.isEmpty(categoryList)){
                for(ItemCategoryDTO item : categoryList){
                    categoryNameMap.put(item.getId(), item.getCateName());
                }
            }
            if(!CollectionUtils.isEmpty(brandList)){
                for(SellerBrandDTO item : brandList){
                    brandNameMap.put(item.getId(), item.getBrandName());
                }
            }
            List<ItemDTO> itemList = serviceResponse.getModule();
            if(itemList != null){
                for(ItemDTO item: serviceResponse.getModule()){
                    item.setBrandName(brandNameMap.get(item.getItemBrandId()));
                    item.setCategoryName(categoryNameMap.get(item.getCategoryId()));
                }
            }
        }
        return serviceResponse.getModule();

    }

    public Response<List<ItemDTO>> queryItem(ItemQTO itemQTO, String appKey)throws DtsException{
        Response<List<ItemDTO>> response = this.itemClient.queryItem(itemQTO, appKey);

        if(response.getCode() != Constants.SERVICE_PROCESS_SUCCESS){
            int errorCode = Integer.valueOf(response.getCode());
            throw new DtsException(errorCode,response.getMessage());
        }
        return response;
    }

    public List<ItemCategoryDTO> queryCategory(ItemCategoryQTO itemCategoryQTO, String appKey) throws DtsException {
        Response<List<ItemCategoryDTO>> response =null;
        response = itemCategoryClient.queryItemCategory(itemCategoryQTO, appKey);
        if(response.getCode() == Constants.SERVICE_PROCESS_SUCCESS){
            return response.getModule();
        }else{
            int code = Integer.valueOf(response.getCode());
            throw new DtsException(code,response.getMessage());
        }
    }

    public List<SellerBrandDTO> querySellerBrand(SellerBrandQTO sellerBrandQTO, String appKey)throws DtsException {
        Response<List<SellerBrandDTO>> response = null;
        response = itemBrandClient.querySellerBrand(sellerBrandQTO, appKey);
        if(response.getCode() == Constants.SERVICE_PROCESS_SUCCESS){
            return response.getModule();
        }else{
            throw new DtsException(response.getCode(),response.getMessage());
        }
    }

    public ExportTaskDAO getExportTaskDAO() {
        return exportTaskDAO;
    }

    public void setExportTaskDAO(ExportTaskDAO exportTaskDAO) {
        this.exportTaskDAO = exportTaskDAO;
    }

}
