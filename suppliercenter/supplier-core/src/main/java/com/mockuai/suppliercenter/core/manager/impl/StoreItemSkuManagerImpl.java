package com.mockuai.suppliercenter.core.manager.impl;

import com.google.common.collect.Lists;
import com.mockuai.itemcenter.client.ItemClient;
import com.mockuai.itemcenter.client.ItemSkuClient;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.domain.dto.CompositeItemDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemSkuDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemQTO;
import com.mockuai.itemcenter.common.domain.qto.ItemSkuQTO;
import com.mockuai.suppliercenter.common.constant.ResponseCode;
import com.mockuai.suppliercenter.common.dto.StoreItemSkuDTO;
import com.mockuai.suppliercenter.common.qto.StoreItemSkuForOrderQTO;
import com.mockuai.suppliercenter.common.qto.StoreItemSkuQTO;
import com.mockuai.suppliercenter.common.qto.StoreQTO;
import com.mockuai.suppliercenter.core.dao.StoreDAO;
import com.mockuai.suppliercenter.core.dao.StoreItemSkuDAO;
import com.mockuai.suppliercenter.core.domain.StoreDO;
import com.mockuai.suppliercenter.core.domain.StoreItemSkuDO;
import com.mockuai.suppliercenter.core.exception.SupplierException;
import com.mockuai.suppliercenter.core.manager.StoreItemSkuManager;
import com.mockuai.suppliercenter.core.util.JsonUtil;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class StoreItemSkuManagerImpl implements StoreItemSkuManager {

    private static final Logger log = LoggerFactory.getLogger(StoreManagerImpl.class);

    @Resource
    private StoreItemSkuDAO storeItemSkuManagerDAO;

    @Resource
    private StoreDAO storeDAO;

    @Resource
    private ItemSkuClient itemSkuClient;

    @Resource
    private ItemClient itemClient;

    @Resource
    private StoreItemSkuDAO storeItemSkuDAO;


    /**
     * 根据仓库id、itemSkuId查询StoreItemSkuDTO
     *
     * @param storeItemSkuQTO
     * @return
     * @throws com.mockuai.suppliercenter.core.exception.SupplierException
     */
    public StoreItemSkuDTO getStoreItemSku(StoreItemSkuQTO storeItemSkuQTO) throws SupplierException {

        try {
            //查询该sku是否在该仓库存在
            StoreItemSkuDO storeItemSkuDO = storeItemSkuManagerDAO.getStoreItemSku(storeItemSkuQTO);
            log.info("[{}] storeItemSkuDO:{}", JsonUtil.toJson(storeItemSkuDO));
            StoreItemSkuDTO storeItemSkuDTO = null;
            if (null != storeItemSkuDO) {
                storeItemSkuDTO = new StoreItemSkuDTO();
                BeanUtils.copyProperties(storeItemSkuDO, storeItemSkuDTO);
            }

            return storeItemSkuDTO;
        } catch (Exception e) {
            log.error("[{}] storeItemSkuQTO:{}", JsonUtil.toJson(storeItemSkuQTO));
            throw new SupplierException(ResponseCode.B_SELECT_ERROR,
                    "getStoreItemSku, get store item sku error,storeItemSkuQTO:" + JsonUtil.toJson(storeItemSkuQTO));
        }


    }


    /**
     * 添加关联
     */
    public StoreItemSkuDTO addStoreItemSku(StoreItemSkuDTO storeItemSkuDTO)
            throws SupplierException {

        Long storeId = storeItemSkuDTO.getStoreId();
        if (null == storeId) {
            throw new SupplierException(ResponseCode.P_PARAM_NULL, "storeId is null");
        }
        Long itmeSkuId = storeItemSkuDTO.getItemSkuId();
        if (null == itmeSkuId) {
            throw new SupplierException(ResponseCode.P_PARAM_NULL, "itmeSkuId is null");
        }

        Long supplierId = storeItemSkuDTO.getSupplierId();

        if (null == supplierId) {
            throw new SupplierException(ResponseCode.P_PARAM_NULL, "supplierId is null");
        }

        Long itemId = storeItemSkuDTO.getItemId();
        if (null == itemId) {
            throw new SupplierException(ResponseCode.P_PARAM_NULL, "itemId is null");
        }

        String itemName = storeItemSkuDTO.getItemName();
        if (null == itemName) {
            throw new SupplierException(ResponseCode.P_PARAM_NULL, "itemName is null");
        }

        String itemSkuSn = storeItemSkuDTO.getSupplierItmeSkuSn().trim();
        if (null == itemSkuSn) {
            throw new SupplierException(ResponseCode.P_PARAM_NULL, "itemSkuSn is null");
        }

        Long sellerId = storeItemSkuDTO.getSellerId();

        if (null == sellerId) {
            throw new SupplierException(ResponseCode.P_PARAM_NULL, "sellerId is null");
        }

        StoreItemSkuQTO storeItemSkuQTO = new StoreItemSkuQTO();
        storeItemSkuQTO.setStoreId(storeItemSkuDTO.getStoreId());
        storeItemSkuQTO.setItemSkuId(storeItemSkuDTO.getItemSkuId());

        //查询该sku是否在该仓库存在
        StoreItemSkuDO storeItemSkuDO1 = storeItemSkuManagerDAO
                .getStoreItemSku(storeItemSkuQTO);

        if (storeItemSkuDO1 != null) {
            throw new SupplierException(ResponseCode.B_ADD_ERROR, "该仓库已经存在该商品的关联关系");
        }

        //查询该商品编码itemSkuSn是否在数据库存在
        log.error("addStoreItemSku:{},storeID:{},itemSkuSn:{}", itemSkuSn, storeId);
        StoreItemSkuQTO storeItemSkuQTO2 = new StoreItemSkuQTO();
        storeItemSkuQTO2.setSupplierItmeSkuSn(itemSkuSn);
        storeItemSkuQTO2.setStoreId(storeId);

        StoreItemSkuDO storeItemSkuSnDO = storeItemSkuDAO.getStoreItemSku(storeItemSkuQTO2);
        log.error("[{}] storeItemSkuSnDO：{}", storeItemSkuSnDO);
        if (null != storeItemSkuSnDO) {
            log.error("1111111111111111");
            throw new SupplierException(ResponseCode.B_ADD_ERROR, "该商品编码" + itemSkuSn + "已经存在");

        }
        log.error("222222222222222222");
        StoreItemSkuDO storeItemSkuDO = new StoreItemSkuDO();
        BeanUtils.copyProperties(storeItemSkuDTO, storeItemSkuDO);

        //设置时间戳
        Long timestamp = new Date().getTime();
        storeItemSkuDO.setTimestamp(timestamp);
        storeItemSkuDO.setBizCode("hanshu");
        storeItemSkuDO.setIconUrl("0");
        Long id = storeItemSkuManagerDAO.addStoreItemSku(storeItemSkuDO);
        log.info("[{}] id:{}", id);
        return storeItemSkuDTO;

    }

    public StoreItemSkuDTO getStoreItemSkuById(Long id)
            throws SupplierException {

        if (null == id) {
            throw new SupplierException(ResponseCode.P_PARAM_NULL, "id is null");
        }

        if (id <= 0) {
            throw new SupplierException(ResponseCode.P_PARAM_ERROR,
                    "id must greater than 0");
        }

        StoreItemSkuDO storeItemSkuDO = storeItemSkuManagerDAO
                .getStoreItemSkuById(id);
        StoreItemSkuDTO storeItemSkuDTO = null;

        if (null != storeItemSkuDO) {
            storeItemSkuDTO = new StoreItemSkuDTO();
            BeanUtils.copyProperties(storeItemSkuDO, storeItemSkuDTO);
        }
        return storeItemSkuDTO;
    }

    /**
     * 取消关联
     */
    public int cancleStoreItemSku(StoreItemSkuDTO storeItemSkuDTO, String appKey)
            throws SupplierException {
        // 取消关联时，先判断是不是唯一关联，如果唯一关联，商品下架（置总库存为0）

        if (null == storeItemSkuDTO) {
            throw new SupplierException(ResponseCode.P_PARAM_NULL,
                    "storeItemSkuDTO is null");
        }

        String bizCode = storeItemSkuDTO.getBizCode();

        Long storeId = storeItemSkuDTO.getStoreId();

        Long itemSkuId = storeItemSkuDTO.getItemSkuId();

        if (bizCode == null) {
            throw new SupplierException(ResponseCode.P_PARAM_NULL,
                    "bizCode is null");
        }
        if (storeId == null || storeId <= 0) {
            throw new SupplierException(ResponseCode.P_PARAM_ERROR,
                    "storeId is error");
        }

        if (itemSkuId == null || itemSkuId <= 0) {
            throw new SupplierException(ResponseCode.P_PARAM_ERROR,
                    "itemSkuId is error");
        }
        StoreItemSkuQTO storeItemSkuQTO = new StoreItemSkuQTO();
        storeItemSkuQTO.setStoreId(storeItemSkuDTO.getStoreId());
        storeItemSkuQTO.setItemSkuId(storeItemSkuDTO.getItemSkuId());
        //查询该sku是否在该仓库存在
        StoreItemSkuDO storeItemSkuDO1 = storeItemSkuManagerDAO
                .getStoreItemSku(storeItemSkuQTO);

        if (storeItemSkuDO1 != null && storeItemSkuDO1.getFrozenStockNum() > 0) {
            throw new SupplierException(ResponseCode.B_CANCLESTORESKU_ERROR,
                    "锁定库存不为0，不能取消关联");
        }

        StoreItemSkuDO storeItemSkuDO = new StoreItemSkuDO();
        BeanUtils.copyProperties(storeItemSkuDTO, storeItemSkuDO);

        log.info(
                "edit Store ItemSku, bizCode = {},  itemSkuId ={} , supplierItmeSkuSn = {}",
                storeItemSkuDTO.getBizCode(), storeItemSkuDTO.getItemSkuId(),
                storeItemSkuDTO.getSupplierItmeSkuSn());

        int result = storeItemSkuManagerDAO.cancleStoreItemSku(storeItemSkuDO);
        if (result != 1) {
            throw new SupplierException(ResponseCode.B_UPDATE_ERROR,
                    "update error");
        }

        return result;

    }


    /**
     * 取消多个关联
     */
    public int cancleStoreItemSkuList(List<Long> skuIdList, String appKey) throws SupplierException {

        if (null == skuIdList) {
            throw new SupplierException(ResponseCode.P_PARAM_NULL,
                    " skuIdList is null");
        }

        int result = storeItemSkuManagerDAO.cancleStoreItemSkuList(skuIdList);
        if (result < 0) {
            throw new SupplierException(ResponseCode.B_UPDATE_ERROR,
                    " cancleStoreItemSkuList  error");
        }

        return result;
    }

    /**
     * 根据条件查询商品sku库存信息
     *
     * @Date 2016-09-25
     */
    public List<StoreItemSkuDTO> queryStoreItemSku(StoreItemSkuQTO storeItemSkuQTO, String appKey) throws SupplierException {

        log.info("[{}] storeItemSkuQTO:{queryStoreItemSku}", JsonUtil.toJson(storeItemSkuQTO));

        if (0 == storeItemSkuQTO.getPageSize()) {//初始化默认查询长度
            storeItemSkuQTO.setPageSize(1000);
        }

        List<StoreItemSkuDO> storeItemSkuDOs = storeItemSkuManagerDAO.queryStoreItemSku(storeItemSkuQTO);

        List<Long> itemSkuList = new ArrayList<Long>();

        if (storeItemSkuDOs.size() == 0) {
            ItemSkuQTO itemSkuQTO = new ItemSkuQTO();
            itemSkuQTO.setNeedQueryStock(false);
            itemSkuQTO.setId(storeItemSkuQTO.getItemSkuId());
            Response<List<CompositeItemDTO>> response = itemSkuClient.QueryCompositeBySkuId(itemSkuQTO, appKey);
            if (!response.isSuccess()) {
                log.error("QueryCompositeBySkuId error, itemSkuQTO: {}", JsonUtil.toJson(itemSkuQTO));
                throw new SupplierException(ResponseCode.SYS_E_DATABASE_ERROR, response.getMessage());
            }
            if (CollectionUtils.isEmpty(response.getModule())) {
                return Lists.newArrayList();
            }
            List<CompositeItemDTO> compositeItemDTOList = response.getModule();
            for (CompositeItemDTO compositeItemDTO : compositeItemDTOList) {
                Long itemSkuId = compositeItemDTO.getSubSkuId();
                itemSkuList.add(itemSkuId);
            }

            StoreItemSkuQTO storeItemSkuQTO1 = new StoreItemSkuQTO();
            storeItemSkuQTO1.setItemSkuIdList(itemSkuList);
            if (0 == storeItemSkuQTO1.getPageSize()) {
                storeItemSkuQTO1.setPageSize(1000);
            }
            log.info("[{}] storeItemSkuQTO:{2}", JsonUtil.toJson(storeItemSkuQTO1));


            storeItemSkuDOs = storeItemSkuManagerDAO.queryStoreItemSku(storeItemSkuQTO1);

        }
        List<StoreItemSkuDTO> storeItemSkuDTOs = new ArrayList<StoreItemSkuDTO>();

        for (StoreItemSkuDO storeItemSkuDOt : storeItemSkuDOs) {
            StoreItemSkuDTO storeItemSkuDTO = new StoreItemSkuDTO();
            BeanUtils.copyProperties(storeItemSkuDOt, storeItemSkuDTO);

            /** 這裡调用sku表查价格回写价格*/
            Response<ItemSkuDTO> res = itemSkuClient.getItemSku(storeItemSkuDTO.getItemSkuId(), storeItemSkuDTO.getSellerId(), appKey);

            if (null != res && res.isSuccess() == true && null != res.getModule()) {
                ItemSkuDTO itemSkuDTO = res.getModule();

                storeItemSkuDTO.setPromotionPrice(itemSkuDTO.getPromotionPrice() == null ? 0L : itemSkuDTO.getPromotionPrice());
                storeItemSkuDTO.setMarketPrice(itemSkuDTO.getMarketPrice() == null ? 0L : itemSkuDTO.getMarketPrice());
            }

            //根据sellerid设置商品名称
            Long sellerId = storeItemSkuDTO.getSellerId();
            ItemQTO qto = new ItemQTO();

            if (null == sellerId) {
                storeItemSkuDTO.setItemName("");
            } else {
                qto.setSellerId(sellerId);
                qto.setId(storeItemSkuDTO.getItemId());
                qto.setNeedStockNum(false);
                //查询对应商品信息
                Response<List<ItemDTO>> itemDtoList = itemClient.queryItem(qto, appKey);

                if (null == itemDtoList || itemDtoList.isSuccess() == false || null == itemDtoList.getModule()) {
                    throw new SupplierException(ResponseCode.P_PARAM_NULL, "查询商品名字返回为空");
                }

                List<ItemDTO> itemDTOs = itemDtoList.getModule();
                ItemDTO itemDTO = new ItemDTO();

                if (null != itemDTOs && itemDTOs.size() > 0) {
                    itemDTO = itemDTOs.get(0);
                }

                storeItemSkuDTO.setItemName(itemDTO.getItemName());
            }

            //查供应商名字、仓库名字
            StoreDO storeDO = storeDAO.getStoreById(storeItemSkuDTO.getStoreId());

            if (storeDO != null) {
                storeItemSkuDTO.setSupplierName(storeDO.getSupplierName());
                storeItemSkuDTO.setStoreName(storeDO.getName());
                storeItemSkuDTO.setStatus(storeDO.getStatus());
                storeItemSkuDTO.setType(storeDO.getType());
            }

            storeItemSkuDTOs.add(storeItemSkuDTO);
        }

        return storeItemSkuDTOs;
    }

    /**
     * 查询指定查询条件下的仓库总数
     *
     * @author csy
     * @Date 2016-09-25
     */
    public Long getTotalCount(StoreItemSkuQTO storeItemSkuQTO) throws SupplierException {
        if (null == storeItemSkuQTO) {
            throw new SupplierException(ResponseCode.P_PARAM_NULL, "storeItemSkuQTO is null");
        }

        Long totalCount = storeItemSkuManagerDAO.getTotalCount(storeItemSkuQTO);
        return totalCount;
    }

    public List<StoreItemSkuDTO> queryItemStoreNumForOrder(
            StoreItemSkuQTO storeItemSkuQTO) throws SupplierException {
        if (null == storeItemSkuQTO) {
            throw new SupplierException(ResponseCode.P_PARAM_NULL,
                    "storeItemSkuQTO is null");
        }


        List<StoreItemSkuDO> storeItemSkuDOs = storeItemSkuManagerDAO
                .queryStoreItemSku(storeItemSkuQTO);
        List<StoreItemSkuDTO> storeItemSkuDTOs = new ArrayList<StoreItemSkuDTO>();
        StoreItemSkuDTO storeItemSkuDTO = null;
        for (StoreItemSkuDO storeItemSkuDO1 : storeItemSkuDOs) {
            storeItemSkuDTO = new StoreItemSkuDTO();

            StoreDO storeDO = storeDAO.getStoreById(storeItemSkuDO1
                    .getStoreId());

            if (storeItemSkuQTO.getStoreName() != null
                    && storeItemSkuQTO.getSupplierName() != null) {

                if (storeItemSkuQTO.getStoreName().equals(storeDO.getName())
                        && storeItemSkuQTO.getSupplierName().equals(
                        storeDO.getSupplierName())) {
                    BeanUtils.copyProperties(storeItemSkuDO1, storeItemSkuDTO);
                    storeItemSkuDTO.setStoreName(storeDO.getName());
                    storeItemSkuDTO.setStatus(storeDO.getStatus());
                    storeItemSkuDTO.setType(storeDO.getType());
                    storeItemSkuDTO.setSupplierName(storeDO.getSupplierName());
                    storeItemSkuDTOs.add(storeItemSkuDTO);
                }
                continue;
            }

            if (storeItemSkuQTO.getStoreName() != null) {
                if (storeItemSkuQTO.getStoreName().equals(storeDO.getName())) {

                    BeanUtils.copyProperties(storeItemSkuDO1, storeItemSkuDTO);
                    storeItemSkuDTO.setStatus(storeDO.getStatus());
                    storeItemSkuDTO.setType(storeDO.getType());
                    storeItemSkuDTO.setStoreName(storeDO.getName());
                    storeItemSkuDTO.setSupplierName(storeDO.getSupplierName());
                    storeItemSkuDTOs.add(storeItemSkuDTO);

                }
                continue;
            }

            if (storeItemSkuQTO.getSupplierName() != null) {
                if (storeItemSkuQTO.getSupplierName().equals(
                        storeDO.getSupplierName())) {
                    BeanUtils.copyProperties(storeItemSkuDO1, storeItemSkuDTO);
                    storeItemSkuDTO.setStatus(storeDO.getStatus());
                    storeItemSkuDTO.setType(storeDO.getType());
                    storeItemSkuDTO.setStoreName(storeDO.getName());
                    storeItemSkuDTO.setSupplierName(storeDO.getSupplierName());
                    storeItemSkuDTOs.add(storeItemSkuDTO);

                }
                continue;
            }
            BeanUtils.copyProperties(storeItemSkuDO1, storeItemSkuDTO);
            storeItemSkuDTO.setStatus(storeDO.getStatus());
            storeItemSkuDTO.setType(storeDO.getType());
            storeItemSkuDTO.setStoreName(storeDO.getName());
            storeItemSkuDTO.setSupplierName(storeDO.getSupplierName());
            storeItemSkuDTOs.add(storeItemSkuDTO);

        }

        return storeItemSkuDTOs;
    }


    public List<StoreItemSkuDTO> queryItemsStoresInfForOrder(
            StoreItemSkuForOrderQTO storeItemSkuForOrderQTO) throws SupplierException {


        if (null == storeItemSkuForOrderQTO) {
            throw new SupplierException(ResponseCode.P_PARAM_NULL,
                    "storeItemSkuForOrderQTO is null");
        }
        List<StoreItemSkuForOrderQTO.StoreItme> list = storeItemSkuForOrderQTO.getSkuIdList();
        if (list.size() > 0) {

            List<StoreItemSkuDO> storeItemSkuDOs = storeItemSkuManagerDAO
                    .queryStoresItemSkuInfForOrder(list.get(0));

            List<StoreItemSkuDO> list2 = new ArrayList<StoreItemSkuDO>();

            Boolean t = false;
            for (StoreItemSkuDO storeItemSkuDO : storeItemSkuDOs) {
                int j = 0;
                for (StoreItemSkuForOrderQTO.StoreItme storeItme : list) {

                    // 计算仓库的库存是否满足第二个及后面的sku的销量，满足继续里面这个for循环，不满足继续外面的for循环
                    StoreItemSkuDO demo = storeItemSkuManagerDAO.queryStoresItemSkuNumForOrderOtherSku(storeItme.getItemSkuId(), storeItme.getOrderNum(), storeItemSkuDO.getStoreId());
                    if (null != demo) {
                        t = true;
                        continue;

                    }
                    t = false;
                    j += 1;
                    break;

                }

                if (t == true) {
                    list2.add(storeItemSkuDO);

                }

            }
            List<StoreItemSkuDTO> storeItemSkuDTOs = new ArrayList<StoreItemSkuDTO>();
            StoreItemSkuDTO storeItemSkuDTO = null;
            for (StoreItemSkuDO storeItemSkuDO1 : list2) {
                storeItemSkuDTO = new StoreItemSkuDTO();

                StoreDO storeDO = storeDAO.getStoreById(storeItemSkuDO1
                        .getStoreId());
                if (storeDO != null) {
                    if (storeItemSkuForOrderQTO.getStoreName() != null
                            && storeItemSkuForOrderQTO.getSupplierName() != null) {

                        if (storeItemSkuForOrderQTO.getStoreName().equals(storeDO.getName())
                                && storeItemSkuForOrderQTO.getSupplierName().equals(
                                storeDO.getSupplierName())) {
                            BeanUtils.copyProperties(storeItemSkuDO1, storeItemSkuDTO);
                            storeItemSkuDTO.setStoreName(storeDO.getName());
                            storeItemSkuDTO.setStatus(storeDO.getStatus());
                            storeItemSkuDTO.setType(storeDO.getType());
                            storeItemSkuDTO.setSupplierName(storeDO.getSupplierName());
                            storeItemSkuDTOs.add(storeItemSkuDTO);
                        }
                        continue;
                    }

                    if (storeItemSkuForOrderQTO.getStoreName() != null) {
                        if (storeItemSkuForOrderQTO.getStoreName().equals(storeDO.getName())) {

                            BeanUtils.copyProperties(storeItemSkuDO1, storeItemSkuDTO);
                            storeItemSkuDTO.setStatus(storeDO.getStatus());
                            storeItemSkuDTO.setType(storeDO.getType());
                            storeItemSkuDTO.setStoreName(storeDO.getName());
                            storeItemSkuDTO.setSupplierName(storeDO.getSupplierName());
                            storeItemSkuDTOs.add(storeItemSkuDTO);

                        }
                        continue;
                    }

                    if (storeItemSkuForOrderQTO.getSupplierName() != null) {
                        if (storeItemSkuForOrderQTO.getSupplierName().equals(
                                storeDO.getSupplierName())) {
                            BeanUtils.copyProperties(storeItemSkuDO1, storeItemSkuDTO);
                            storeItemSkuDTO.setStatus(storeDO.getStatus());
                            storeItemSkuDTO.setType(storeDO.getType());
                            storeItemSkuDTO.setStoreName(storeDO.getName());
                            storeItemSkuDTO.setSupplierName(storeDO.getSupplierName());
                            storeItemSkuDTOs.add(storeItemSkuDTO);

                        }
                        continue;
                    }
                    BeanUtils.copyProperties(storeItemSkuDO1, storeItemSkuDTO);
                    storeItemSkuDTO.setStatus(storeDO.getStatus());
                    storeItemSkuDTO.setType(storeDO.getType());
                    storeItemSkuDTO.setStoreName(storeDO.getName());
                    storeItemSkuDTO.setSupplierName(storeDO.getSupplierName());
                    storeItemSkuDTOs.add(storeItemSkuDTO);

                }


            }

            return storeItemSkuDTOs;
        }


        return null;


    }

    /**
     * 从现有sku的库存复制为新的sku的库存
     *
     * @param itemSkuId
     * @param itemSkuIdNew
     * @param stock
     * @throws com.mockuai.suppliercenter.core.exception.SupplierException
     */
    public void copySkuStock(Long itemSkuId, Long itemSkuIdNew, Long stock, String appKey)
            throws SupplierException {

        // 查出所有正常的仓库
        StoreQTO storeQTO = new StoreQTO();
        storeQTO.setStatus(1);   //没禁用
        storeQTO.setDeleteMark(0);
        List<Long> idList = storeDAO.getStoreIdList(storeQTO);

        StoreItemSkuQTO storeItemSkuQTO = new StoreItemSkuQTO();
        storeItemSkuQTO.setItemSkuId(itemSkuId);
        storeItemSkuQTO.setOffset(0);
        storeItemSkuQTO.setPageSize(100);

        // 根据skuid获取仓库库存,按照优先级排序
        List<StoreItemSkuDO> storeItemSkuDOs = storeItemSkuManagerDAO
                .queryStoreItemSku(storeItemSkuQTO);

        if (storeItemSkuDOs.size() == 0) {
            // 如果无库存，直接返回
            throw new SupplierException(ResponseCode.B_ORDERNUMBER_ERROR,
                    " 库存量不足，请检查 ");

        }

        // 将有效库存相加如果大于需要转换库存，则开始转换，小于抛错
        Long num = 0L;

        for (StoreItemSkuDO storeItemSkuDO : storeItemSkuDOs) {

            // 判断仓库包含在有效仓库中减库存
            for (Long id : idList) {

                if (id.equals(storeItemSkuDO.getStoreId())) {

                    num = num + (storeItemSkuDO.getSalesNum());

                }
            }
        }

        if (num >= stock) {

            num = 0L;

            label:
            for (StoreItemSkuDO storeItemSkuDO : storeItemSkuDOs) {

                // 判断仓库包含在有效仓库中减库存
                for (Long id : idList) {

                    if (id.equals(storeItemSkuDO.getStoreId())) {

                        Long endStock = storeItemSkuDO.getSalesNum();

                        log.info("[{}] endStock:{}", endStock);

                        num = stock - endStock;

                        log.info("[{}] num:{st}", num);

                        if (num > 0) {

                            stock = stock - endStock;

                            //更新有效仓库的库存数量
                            storeItemSkuManagerDAO.reduceStoreNum(
                                    storeItemSkuDO.getStoreId(),
                                    storeItemSkuDO.getItemSkuId(),
                                    endStock, 0);

                            storeItemSkuQTO.setStoreIdList(idList);

                            storeItemSkuQTO.setItemSkuId(itemSkuId);

                            StoreItemSkuQTO qto = new StoreItemSkuQTO();
                            qto.setStoreId(storeItemSkuDO.getStoreId());
                            qto.setItemSkuId(itemSkuIdNew);

                            // 查询该sku是否在该仓库存在
                            StoreItemSkuDO storeItemSkuDO1 = storeItemSkuManagerDAO
                                    .getStoreItemSku(qto);
                            if (storeItemSkuDO1 != null) {

                                storeItemSkuDO1.setSalesNum(endStock);

                                //更新新的itemSkuId库存数量
                                int res = storeItemSkuManagerDAO.updateStoreItemSku(storeItemSkuDO1);
                                if (res != 1) {
                                    throw new SupplierException(ResponseCode.B_UPDATE_ERROR,
                                            "update error");
                                }

                            } else {

                                storeItemSkuDO.setItemSkuId(itemSkuIdNew);
                                storeItemSkuDO.setSalesNum(endStock);
                                storeItemSkuDO.setStockNum(0L);
                                storeItemSkuDO.setSupplierItmeSkuSn("msbm" + storeItemSkuDO.getSupplierItmeSkuSn() + "");
                                storeItemSkuDO.setItemName("（秒杀商品）" + storeItemSkuDO.getItemName() + "");

                                // 锁定库存都是异常数据，忽略掉
                                storeItemSkuDO.setFrozenStockNum(0L);

                                log.info("add storeItem, bizCode = {},itemSkuId ={} ",
                                        storeItemSkuDO.getBizCode(), storeItemSkuDO.getItemSkuId());

                                storeItemSkuManagerDAO.addStoreItemSku(storeItemSkuDO);

                            }

                        } else {

                            int result = storeItemSkuManagerDAO.reduceStoreNum(
                                    storeItemSkuDO.getStoreId(),
                                    storeItemSkuDO.getItemSkuId(), stock, 0);
                            log.info("[{}] result:{0}", result);

                            storeItemSkuQTO.setStoreIdList(idList);

                            //统计未禁用的skuId总库存，然后调用商品接口置总库存
                            storeItemSkuQTO.setItemSkuId(itemSkuId);

                            StoreItemSkuQTO qto = new StoreItemSkuQTO();
                            qto.setStoreId(storeItemSkuDO.getStoreId());
                            qto.setItemSkuId(itemSkuIdNew);

                            // 查询该sku是否在该仓库存在
                            StoreItemSkuDO storeItemSkuDO1 = storeItemSkuManagerDAO
                                    .getStoreItemSku(qto);

                            if (storeItemSkuDO1 != null) {

                                storeItemSkuDO1.setSalesNum(stock);
                                int res = storeItemSkuManagerDAO.updateStoreItemSku(storeItemSkuDO1);

                                // 编辑库存，改总库存
                                if (res != 1) {
                                    throw new SupplierException(ResponseCode.B_UPDATE_ERROR,
                                            "update error");
                                }

                            } else {
                                storeItemSkuDO.setSalesNum(stock);
                                storeItemSkuDO.setStockNum(0L);
                                storeItemSkuDO.setItemSkuId(itemSkuIdNew);
                                storeItemSkuDO.setSupplierItmeSkuSn("msbm" + storeItemSkuDO.getSupplierItmeSkuSn() + "");
                                storeItemSkuDO.setItemName("（秒杀商品）" + storeItemSkuDO.getItemName() + "");

                                // 锁定库存都是异常数据，忽略掉
                                storeItemSkuDO.setFrozenStockNum(0L);

                                log.info("add storeItem, bizCode = {},itemSkuId ={} ",
                                        storeItemSkuDO.getBizCode(), storeItemSkuDO.getItemSkuId());

                                storeItemSkuManagerDAO.addStoreItemSku(storeItemSkuDO);

                            }

                            storeItemSkuQTO.setItemSkuId(itemSkuIdNew);

                            break label;

                        }

                    }
                }

            }


        } else {

            // 如果无库存，直接返回
            throw new SupplierException(ResponseCode.B_ORDERNUMBER_ERROR,
                    " 库存量不足，请检查 ", num);
        }

    }


    /**
     * 返还从现有sku的库存复制为新的sku的库存，与上面相反的操作
     *
     * @param itemSkuId
     * @param itemSkuIdNew
     * @throws com.mockuai.suppliercenter.core.exception.SupplierException
     */
    public void copySkuStockReturn(Long itemSkuId, Long itemSkuIdNew, String appKey)
            throws SupplierException {

        // 查出所有仓库的仓库状态：没禁用、没删除
        StoreQTO storeQTO = new StoreQTO();
        storeQTO.setStatus(1);
        storeQTO.setDeleteMark(0);

        StoreItemSkuQTO storeItemSkuQTO = new StoreItemSkuQTO();
        storeItemSkuQTO.setItemSkuId(itemSkuIdNew);
        storeItemSkuQTO.setOffset(0);
        storeItemSkuQTO.setPageSize(100);

        // 根据skuid去查仓库库存,按照优先级排序
        List<StoreItemSkuDO> storeItemSkuDOs = storeItemSkuManagerDAO
                .queryStoreItemSku(storeItemSkuQTO);

        for (StoreItemSkuDO storeItemSkuDO : storeItemSkuDOs) {

            if (storeItemSkuDO.getSalesNum() > 0) {

                //返回新库存商品sku_id的库存数量
                storeItemSkuManagerDAO.reduceStoreNum(
                        storeItemSkuDO.getStoreId(),
                        storeItemSkuDO.getItemSkuId(),
                        storeItemSkuDO.getSalesNum(), 0);

                storeItemSkuManagerDAO.increaseStoreNum(
                        storeItemSkuDO.getStoreId(), itemSkuId,
                        storeItemSkuDO.getSalesNum());

            }

        }

    }

    public int increaseStoreNum(StoreItemSkuDTO storeItemSkuDTO)
            throws SupplierException {
        if (null == storeItemSkuDTO) {
            throw new SupplierException(ResponseCode.P_PARAM_NULL,
                    "storeItemSkuDTO is null");
        }

        String bizCode = storeItemSkuDTO.getBizCode();

        if (bizCode == null) {
            throw new SupplierException(ResponseCode.P_PARAM_NULL,
                    "bizCode is null");
        }

        if (storeItemSkuDTO.getFrozenStockNum() < 0) {
            throw new SupplierException(ResponseCode.P_PARAM_ERROR,
                    "num param error");
        }

        log.info(
                "increase StoreNum , bizCode = {}, storeId = {}, skuId = {}, num = {}",
                storeItemSkuDTO.getBizCode(), storeItemSkuDTO.getStoreId(),
                storeItemSkuDTO.getItemSkuId(), storeItemSkuDTO.getFrozenStockNum());

        int result = storeItemSkuManagerDAO.increaseStoreNum(
                storeItemSkuDTO.getStoreId(), storeItemSkuDTO.getItemSkuId(),
                storeItemSkuDTO.getFrozenStockNum());

        if (result != 1) {
            throw new SupplierException(ResponseCode.B_UPDATE_ERROR,
                    "update error");
        }
        return result;
    }

    public int reduceStoreNum(StoreItemSkuDTO storeItemSkuDTO)
            throws SupplierException {
        if (null == storeItemSkuDTO) {
            throw new SupplierException(ResponseCode.P_PARAM_NULL,
                    "storeItemSkuDTO is null");
        }

        String bizCode = storeItemSkuDTO.getBizCode();

        if (bizCode == null) {
            throw new SupplierException(ResponseCode.P_PARAM_NULL,
                    "bizCode is null");
        }

        if (storeItemSkuDTO.getFrozenStockNum() < 0) {
            throw new SupplierException(ResponseCode.P_PARAM_NULL,
                    "num param error");
        }

        Long storeNumNow = getStoreNumByStoreSku(storeItemSkuDTO.getStoreId(),
                storeItemSkuDTO.getItemSkuId());
        if (storeNumNow < storeItemSkuDTO.getFrozenStockNum()) {
            throw new SupplierException(ResponseCode.P_PARAM_ERROR,
                    "num param is bigger than storeNum");
        }

        log.info(
                "increase StoreNum , bizCode = {}, storeId = {}, skuId = {}, storeNum = {}",
                storeItemSkuDTO.getBizCode(), storeItemSkuDTO.getStoreId(),
                storeItemSkuDTO.getItemSkuId(), storeItemSkuDTO.getSalesNum());

        int result = storeItemSkuManagerDAO.reduceStoreNum(
                storeItemSkuDTO.getStoreId(), storeItemSkuDTO.getItemSkuId(),
                storeItemSkuDTO.getFrozenStockNum(), 0);

        if (result != 1) {
            throw new SupplierException(ResponseCode.B_UPDATE_ERROR,
                    "reduce StoreNum error");
        }
        return result;
    }

    public int updateStoreItemSku(StoreItemSkuDTO storeItemSkuDTO)
            throws SupplierException {
        if (null == storeItemSkuDTO) {
            throw new SupplierException(ResponseCode.P_PARAM_NULL,
                    "storeItemSkuDTO is null");
        }

        String bizCode = storeItemSkuDTO.getBizCode();

        if (bizCode == null) {
            throw new SupplierException(ResponseCode.P_PARAM_NULL,
                    "bizCode is null");
        }
        if (storeItemSkuDTO.getId() == null) {
            throw new SupplierException(ResponseCode.P_PARAM_NULL, "id is null");
        }

        StoreItemSkuDO storeItemSkuDO = new StoreItemSkuDO();
        BeanUtils.copyProperties(storeItemSkuDTO, storeItemSkuDO);

        log.info(
                "edit Store ItemSku, bizCode = {}, supplierId = {}, itemId = {},itemSkuId ={} , supplierItmeSkuSn = {},stock ={} ,",
                storeItemSkuDTO.getBizCode(), storeItemSkuDTO.getSupplierId(),
                storeItemSkuDTO.getItemId(), storeItemSkuDTO.getItemSkuId(),
                storeItemSkuDTO.getSupplierItmeSkuSn(),
                storeItemSkuDTO.getSalesNum());

        if (storeItemSkuDO.getStockNum() != null) {

            //查询锁定库存是否大于编辑库存，如果大于返回异常，不允许编辑库存
            StoreItemSkuDO storeItemSkuDo = storeItemSkuManagerDAO.getStoreItemSkuById(storeItemSkuDO.getId());

            if (storeItemSkuDO != null && storeItemSkuDO.getStockNum() < storeItemSkuDo.getFrozenStockNum()) {
                throw new SupplierException(ResponseCode.B_STOCKTOSMALL_ERROR, "更改库存不能小于冻结库存");
            }

        }


        int result = storeItemSkuManagerDAO.updateStoreItemSku(storeItemSkuDO);
        // 编辑库存，改总库存
        if (result != 1) {
            throw new SupplierException(ResponseCode.B_UPDATE_ERROR,
                    "update error");
        }
        return result;

    }

    public int updateStockToGyerpBySkuSn(StoreItemSkuDTO storeItemSkuDTO) throws SupplierException {
        StoreItemSkuDO storeItemSkuDO = new StoreItemSkuDO();
        storeItemSkuDO.setTimestamp(new Date().getTime());
        BeanUtils.copyProperties(storeItemSkuDTO, storeItemSkuDO);
        int result = storeItemSkuManagerDAO.updateStockToGyerpBySkuSn(storeItemSkuDO);

        if (result != 1) {
            throw new SupplierException(ResponseCode.B_UPDATE_ERROR,
                    "updateStockToGyerpBySkuSn error");
        }
        return result;
    }

    public Long getStoreNumByStoreSku(Long storeId, Long skuId)
            throws SupplierException {

        if (null == storeId || storeId < 0) {
            throw new SupplierException(ResponseCode.P_PARAM_NULL,
                    "storeId param error");
        }
        if (null == skuId || skuId < 0) {
            throw new SupplierException(ResponseCode.P_PARAM_NULL,
                    "skuId param error");
        }
        StoreItemSkuQTO storeItemSkuQTO = new StoreItemSkuQTO();
        storeItemSkuQTO.setStoreId(storeId);
        storeItemSkuQTO.setItemSkuId(skuId);
        Long num = storeItemSkuManagerDAO
                .getStoreNumByStoreSku(storeItemSkuQTO);

        return num;
    }

    /**
     * 根据itemid查询商品库存数据
     *
     * @Date 2016-10-07
     */
    @Override
    public List<StoreItemSkuDTO> queryStoreItemSkuByItemId(StoreItemSkuQTO storeItemSkuQTO, String appKey) throws SupplierException {
        if (null == storeItemSkuQTO) {
            throw new SupplierException(ResponseCode.P_PARAM_NULL, "storeItemSkuQTO is null");
        }

        List<StoreItemSkuDO> storeItemSkuDOs = storeItemSkuManagerDAO.queryStoreItemSkuByItemId(storeItemSkuQTO);
        List<StoreItemSkuDTO> storeItemSkuDTOs = new ArrayList<StoreItemSkuDTO>();

        for (StoreItemSkuDO storeItemSkuDOt : storeItemSkuDOs) {
            StoreItemSkuDTO storeItemSkuDTO = new StoreItemSkuDTO();
            BeanUtils.copyProperties(storeItemSkuDOt, storeItemSkuDTO);
            //1841254L 平台标志默认sellerid
            Response<ItemSkuDTO> res = itemSkuClient.getItemSku(storeItemSkuDTO.getItemSkuId(), 1841254L, appKey);

            if (null != res && res.isSuccess() == true && null != res.getModule()) {
                ItemSkuDTO itemSkuDTO = res.getModule();

                storeItemSkuDTO.setSkuCode(itemSkuDTO.getSkuCode());
            } else {
                storeItemSkuDTO.setSkuCode("无规格");
            }

            //查供应商名字、仓库名字
            StoreDO storeDO = storeDAO.getStoreById(storeItemSkuDTO.getStoreId());

            if (storeDO != null) {
                storeItemSkuDTO.setSupplierName(storeDO.getSupplierName());
                storeItemSkuDTO.setStoreName(storeDO.getName());
                storeItemSkuDTO.setStatus(storeDO.getStatus());
                storeItemSkuDTO.setType(storeDO.getType());
            }

            storeItemSkuDTOs.add(storeItemSkuDTO);
        }

        return storeItemSkuDTOs;
    }

    /**
     * 根据itemList查询skuList数据
     *
     * @author csy
     * @Date 2016-10-11
     */
    @Override
    public List<StoreItemSkuDTO> queryStoreItemSkuList(StoreItemSkuQTO storeItemSkuQTO, String appKey) throws SupplierException {
        if (null == storeItemSkuQTO) {
            throw new SupplierException(ResponseCode.P_PARAM_NULL, "storeItemSkuQTO is null");
        }

        List<StoreItemSkuDO> storeItemSkuDOs = storeItemSkuManagerDAO.queryStoreItemSku(storeItemSkuQTO);

        List<StoreItemSkuDTO> storeItemSkuDTOs = new ArrayList<StoreItemSkuDTO>();

        for (StoreItemSkuDO storeItemSkuDO : storeItemSkuDOs) {
            StoreItemSkuDTO StoreItemSkuDTO = new StoreItemSkuDTO();
            BeanUtils.copyProperties(storeItemSkuDO, StoreItemSkuDTO);

            storeItemSkuDTOs.add(StoreItemSkuDTO);
        }

        return storeItemSkuDTOs;
    }
}
