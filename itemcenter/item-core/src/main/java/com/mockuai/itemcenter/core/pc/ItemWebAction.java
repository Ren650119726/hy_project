package com.mockuai.itemcenter.core.pc;

import com.mockuai.appcenter.common.domain.AppInfoDTO;
import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.domain.dto.*;
import com.mockuai.itemcenter.common.domain.qto.*;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.*;
import com.mockuai.itemcenter.core.util.JsonUtil;
import com.mockuai.itemcenter.core.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by yindingyu on 15/11/23.
 */
@Controller
@RequestMapping("/item")
public class ItemWebAction extends BaseWebAction {

    @Resource
    private ItemSearchManager itemSearchManager;


    private static final Logger log = LoggerFactory.getLogger(ItemWebAction.class);

    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Resource
    private ItemManager itemManager;

    @Resource
    private ItemSkuManager itemSkuManager;

    @Resource
    private ItemImageManager itemImageManager;

    @Resource
    private SkuPropertyManager skuPropertyManager;

    @Resource
    private ItemPropertyManager itemPropertyManager;

    @Resource
    private SellerBrandManager sellerBrandManager;

    @Resource
    private ItemBuyLimitManager itemBuyLimitManager;

    @Resource
    private MarketingManager marketingManager;

    @Resource
    private AppManager appManager;

    @RequestMapping("/search.do")
    @ResponseBody
    public String searchItem(HttpServletRequest request){

        try {

            String appKey = getAppkey(request);
            String bizCode = getBizCode(appKey);

            ItemSearchQTO itemSearchQTO = getParameter(request, "itemSearchQTO", ItemSearchQTO.class);
            itemSearchQTO.setBizCode(bizCode);


            if(itemSearchQTO.getKeyword()!=null){
                //线上http接口中文参数显式编解码
                try {
                    itemSearchQTO.setKeyword(UriUtils.decode(itemSearchQTO.getKeyword(), "utf-8"));
                } catch (UnsupportedEncodingException e) {
                    //将异常降级处理
                }
            }

            ItemSearchResultDTO itemSearchResultDTO = itemSearchManager.searchItemIndex(itemSearchQTO);


            ItemResponse<ItemSearchResultDTO> response = ResponseUtil.getSuccessResponse(itemSearchResultDTO);

            response.setTotalCount(itemSearchResultDTO.getCount());

            String s = JsonUtil.toJson(response);

            return JsonUtil.toJson(response);

        } catch (ItemException e) {
            ItemResponse response = ResponseUtil.getErrorResponse(e.getCode(), e.getMessage());
            return JsonUtil.toJson(response);
        }

    }


    @RequestMapping("/query.do")
    @ResponseBody
    public String queryItem(HttpServletRequest request){
        try {


            String appKey = getAppkey(request);
            String bizCode = getBizCode(appKey);




            ItemQTO itemQTO = getParameter(request, "itemQTO", ItemQTO.class);
            itemQTO.setBizCode(bizCode);
            List<ItemDTO> itemDTOList = itemManager.queryItem(itemQTO);

            ItemResponse response = ResponseUtil.getSuccessResponse(itemDTOList, itemQTO.getTotalCount());

            return JsonUtil.toJson(response);
        } catch (ItemException e) {
            ItemResponse response = ResponseUtil.getErrorResponse(e.getCode(), e.getMessage());
            return JsonUtil.toJson(response);
        }

    }



    @RequestMapping("/get.do")
    @ResponseBody
    public String execute(HttpServletRequest request) throws ItemException {

        String appKey = getAppkey(request);
        String bizCode = getBizCode(appKey);



        Long itemId = getLong(request, "id");// 商品品牌ID
        Long sellerId = getLong(request, "seller_id");// 供应商ID


        ItemDTO itemDTO;

        // 如果是需要详细信息时候  需要找到普通属性和销售属性值 －－ updated by cwr
        if(request.getParameter("needDetail") !=null && ((Boolean.parseBoolean(request.getParameter("needDetail"))))){



            try {

                itemDTO = itemManager.getItem(itemId, sellerId, bizCode);
                ItemSkuQTO itemSkuQTO = new ItemSkuQTO();
                itemSkuQTO.setItemId(itemId);
                itemSkuQTO.setSellerId(sellerId);
                itemSkuQTO.setBizCode(bizCode);
                // 获取ItemSku列表
                List<ItemSkuDTO> itemSkuDTOList = itemSkuManager.queryItemSku(itemSkuQTO);

                // 根据itemId查找该商品下的所有的基本属性
                ItemPropertyQTO itemPropertyQTO = new ItemPropertyQTO();
                itemPropertyQTO.setItemId(itemId);
                itemPropertyQTO.setSellerId(sellerId);
                itemPropertyQTO.setNeedPaging(null); //不需要分页
                List<ItemPropertyDTO> itemPropertyList = this.itemPropertyManager.queryItemProperty(itemPropertyQTO);

                //TODO 根据appkey判断应用类型，并根据property的bizMark来控制是否返回数据（有些字段在面向买家的应用是不返回的）

                itemDTO.setItemPropertyList(itemPropertyList);

                // 根据itemId查找该商品下的所有的销售属性值
                //q
                SkuPropertyQTO skuPropertyQTO =new SkuPropertyQTO();
                skuPropertyQTO.setSellerId(sellerId);
                skuPropertyQTO.setItemId(itemId);
                skuPropertyQTO.setNeedPaging(null);//不需要分页
                skuPropertyQTO.setBizCode(bizCode);
                List<SkuPropertyDTO> skuPropertyList = this.skuPropertyManager.querySkuProperty(skuPropertyQTO);

                // 根据item_id查询所有的sku属性时候 有重复  比如：sku1 和 sku2 都有尺码L这个属性 结果会有重复 需要去除重复
                //去重操作 本段代码修改移到item-mop层修改 不然会导致商家中心sku部分出问题

//				List<SkuPropertyDTO> returnPropertyList = new ArrayList<SkuPropertyDTO>();
//				Set<Long> valueIdList = new HashSet<Long>();

//				if(skuPropertyList !=null){
//					for(SkuPropertyDTO item : skuPropertyList){
//						if(!valueIdList.contains(item.getVid())){
//							valueIdList.add(item.getVid());
//							returnPropertyList.add(item);
//						}
//					}
//				}
                itemDTO.setSkuPropertyList(skuPropertyList);

                // 获取副图列表
                ItemImageQTO itemImageQTO = new ItemImageQTO();
                itemImageQTO.setItemId(itemId);
                itemImageQTO.setSellerId(sellerId);
                itemImageQTO.setBizCode(bizCode);
                List<ItemImageDTO> itemImageDTOList = itemImageManager.queryItemImage(itemImageQTO);

                //过滤商品图片，将商品副图和SKU图片分开，这里只提取商品副图，过滤掉商品SKU图片
//				List<ItemImageDTO> itemExtraImageList = getItemExtraImageList(itemImageDTOList);

                //往商品SKU中填充商品sku图片
                fillItemSkuImage(itemImageDTOList, itemSkuDTOList);

                itemDTO.setItemImageDTOList(itemImageDTOList);
                itemDTO.setItemSkuDTOList(itemSkuDTOList);
                //TODO 商品价格填充逻辑重构
                if(itemSkuDTOList!=null && itemSkuDTOList.isEmpty()==false){
                    ItemSkuDTO itemSkuDTO = itemSkuDTOList.get(0);
                    itemDTO.setMarketPrice(itemSkuDTO.getMarketPrice());
                    itemDTO.setPromotionPrice(itemSkuDTO.getPromotionPrice());
                    itemDTO.setWirelessPrice(itemSkuDTO.getWirelessPrice());
                }

            } catch (ItemException e) {
                ItemResponse<ItemDTO> response = ResponseUtil.getErrorResponse(e.getCode(), e.getMessage());
                return JsonUtil.toJson(response);
            }
        }else{ //不需要查找普通属性和销售属性值的时候

            try {
                itemDTO = itemManager.getItem(itemId, sellerId, bizCode);
                ItemSkuQTO itemSkuQTO = new ItemSkuQTO();
                itemSkuQTO.setItemId(itemId);
                itemSkuQTO.setSellerId(sellerId);
                itemSkuQTO.setBizCode(bizCode);
                // 获取ItemSku列表
                List<ItemSkuDTO> itemSkuDTOList = itemSkuManager.queryItemSku(itemSkuQTO);
                // 获取副图列表
                ItemImageQTO itemImageQTO = new ItemImageQTO();
                itemImageQTO.setItemId(itemId);
                itemImageQTO.setSellerId(sellerId);
                itemImageQTO.setBizCode(bizCode);
                List<ItemImageDTO> itemImageDTOList =
                        itemImageManager.queryItemImage(itemImageQTO);

                //过滤商品图片，将商品副图和SKU图片分开，这里只提取商品副图，过滤掉商品SKU图片
//				List<ItemImageDTO> itemExtraImageList = getItemExtraImageList(itemImageDTOList);

                //往商品SKU中填充商品sku图片
                fillItemSkuImage(itemImageDTOList, itemSkuDTOList);

                itemDTO.setItemImageDTOList(itemImageDTOList);
                itemDTO.setItemSkuDTOList(itemSkuDTOList);
            } catch (ItemException e) {
                ItemResponse response = ResponseUtil.getErrorResponse(e.getCode(), e.getMessage());
                return JsonUtil.toJson(response);
            }
        }

        //获取商品品牌信息
        Long itemBrandId = itemDTO.getItemBrandId();
        if(itemBrandId != null){

            SellerBrandDTO sellerBrand = sellerBrandManager.getSellerBrand(itemBrandId);
            // fixbug.
//            if(sellerBrand == null) {
//                ItemException e = ExceptionUtil.getException(ResponseCode.BASE_PARAM_E_RECORD_NOT_EXIST, "brand not exists for the item.");
//                ItemResponse response = ResponseUtil.getErrorResponse(e.getCode(), e.getMessage());
//                return JsonUtil.toJson(response);
//            }
            itemDTO.setItemBrandDTO(sellerBrand);
            //TODO 这里暂时返回兼容itemProperty中的品牌属性，后续需要考虑在商家中心来插入，平台不感知该属性 add by zengzhangqiang on 2015-06-11
            if(itemDTO.getItemPropertyList() == null){
                itemDTO.setItemPropertyList(new ArrayList<ItemPropertyDTO>());
            }
            ItemPropertyDTO itemPropertyDTO = new ItemPropertyDTO();
            itemPropertyDTO.setCode("IC_APP_P_ITEM_000001");
            itemPropertyDTO.setName("品牌");
            itemPropertyDTO.setValue(sellerBrand.getBrandName());
            itemPropertyDTO.setValueType(1);
            itemDTO.getItemPropertyList().add(itemPropertyDTO);
        }
        // 判断商品限购;
        List<ItemBuyLimitDTO> itemBuyLimitDTOs = itemBuyLimitManager.queryItemBuyLimitRecord(sellerId, itemId);
        if(itemBuyLimitDTOs != null) {
            List<LimitEntity> limitEntities = new ArrayList<LimitEntity>();
            for(ItemBuyLimitDTO itemBuyLimitDTO: itemBuyLimitDTOs) {
                LimitEntity limitEntity = new LimitEntity();
                limitEntity.setLimitCount(itemBuyLimitDTO.getBuyCount());
                try{
                    //如果限购时间为空，则代表永久限购，时间上可以填充一个默认最小值和最大值
                    if(itemBuyLimitDTO.getBeginTime() == null){
                        limitEntity.setBeginTime(dateFormat.parse("2000-01-01 00:00:01"));
                    }else{
                        limitEntity.setBeginTime(itemBuyLimitDTO.getBeginTime());
                    }

                    if(itemBuyLimitDTO.getEndTime() == null){
                        limitEntity.setEndTime(dateFormat.parse("2102-01-01 00:00:01"));
                    }else{
                        limitEntity.setEndTime(itemBuyLimitDTO.getEndTime());
                    }

                    Date date = new Date();

                    //只有当前时间在限购时间段内，才返回限购数量
                    //if(date.after(limitEntity.getBeginTime())&&date.before(limitEntity.getEndTime())){
                    //   limitEntity.setLimitCount(itemBuyLimitDTO.getBuyCount());
                    //}


                }catch(Exception e){
                    log.error("", e);
                }
                limitEntities.add(limitEntity);
            }
            itemDTO.setBuyLimit(limitEntities);
        }

//        //填充商品优惠信息
//        List<DiscountInfo> discountInfos = marketingManager.queryItemDiscountInfo(itemDTO, appKey);
//        itemDTO.setDiscountInfoList(discountInfos);


        //TODO 判断商品收藏状态

        ItemResponse<ItemDTO> response = ResponseUtil.getSuccessResponse(itemDTO);
        return JsonUtil.toJson(response);


    }


    private List<ItemImageDTO> getItemExtraImageList(List<ItemImageDTO> itemImageDTOs){
        if(itemImageDTOs==null || itemImageDTOs.isEmpty()){
            return Collections.EMPTY_LIST;
        }

        List<ItemImageDTO> itemExtraImageList = new ArrayList<ItemImageDTO>();
        Map<Long, ItemImageDTO> skuImageMap = new HashMap<Long, ItemImageDTO>();
        for(ItemImageDTO itemImageDTO: itemImageDTOs){
            if(itemImageDTO.getPropertyValueId()==null || itemImageDTO.getPropertyValueId().longValue()<=0){
                //商品副图
                itemExtraImageList.add(itemImageDTO);
            }
        }

        return itemExtraImageList;
    }

    private void fillItemSkuImage(List<ItemImageDTO> itemImageDTOs,
                                  List<ItemSkuDTO> itemSkuDTOList){
        if(itemImageDTOs==null || itemSkuDTOList==null){
            return;
        }
        Map<Long, ItemImageDTO> skuImageMap = new HashMap<Long, ItemImageDTO>();
        for(ItemImageDTO itemImageDTO: itemImageDTOs){
            if(itemImageDTO.getPropertyValueId()!=null && itemImageDTO.getPropertyValueId().longValue()>0){
                //商品SKU图片
                skuImageMap.put(itemImageDTO.getPropertyValueId(), itemImageDTO);
            }
        }

        //FIXME 商品sku图片与商品sku是根据商品sku属性上的propertyValueId进行关联的
        for(ItemSkuDTO itemSkuDTO: itemSkuDTOList){
            if(itemSkuDTO.getSkuPropertyDTOList() != null){
                for(SkuPropertyDTO skuPropertyDTO: itemSkuDTO.getSkuPropertyDTOList()){
                    if(skuPropertyDTO.getPropertyValueId()!=null
                            && skuImageMap.containsKey(skuPropertyDTO.getPropertyValueId())){
                        itemSkuDTO.setImageUrl(skuImageMap.get(skuPropertyDTO.getPropertyValueId()).getImageUrl());
                    }
                }
            }
        }

        return;
    }


    public String getBizCode(String appKey) throws ItemException {


        AppInfoDTO appInfoDTO = appManager.getAppInfo(appKey);

        return appInfoDTO.getBizCode();
    }
}