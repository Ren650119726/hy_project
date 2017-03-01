package com.mockuai.itemcenter.core.service.action.item;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ItemType;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.ItemBuyLimitDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemSkuDTO;
import com.mockuai.itemcenter.common.domain.dto.LimitEntity;
import com.mockuai.itemcenter.common.domain.qto.ItemSkuQTO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.*;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.service.action.Action;
import com.mockuai.itemcenter.core.util.JsonUtil;
import com.mockuai.itemcenter.core.util.ResponseUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 查看商品动态信息 Action
 * 查询商品收益 价格等信息
 * 对app前台接口
 *
 * @author guansheng
 */

@Service
public class GetItemDynamicAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(GetItemDynamicAction.class);

    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Resource
    private ItemManager itemManager;

    @Resource
    private ItemSkuManager itemSkuManager;


    @Resource
    private ItemBuyLimitManager itemBuyLimitManager;

    @Resource
    private DistributorManager distributorManager;


    @Resource
    private CompositeItemManager compositeItemManager;


    @Override
    public ItemResponse execute(RequestContext context) throws ItemException {
        ItemDTO itemDTO = null;
        ItemResponse response = null;
        Double maxGain = 0d;

        ItemRequest request = context.getRequest();
        String appKey = (String) context.get("appKey");
        String bizCode = (String) context.get("bizCode");

        // 一级收益比例（直接上级）
        BigDecimal oneGains =  new BigDecimal(distributorManager.getGainsSet(appKey).getOneGains()).divide(new BigDecimal(100));
        // 收益比例描述
        String gainPercentDesc = "";
        //1.2.8期收益比例描述（原接口保留）
        String gainsSharingDesc = "";
        String sharingGains = "";


        // 验证ID
        if (request.getLong("id") == null) {
            return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "itemID is missing");
        }
        if (request.getLong("supplierId") == null) {
            return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "sellerId is missing");
        }
        Long itemId = request.getLong("id");// 商品品牌ID
        Long sellerId = request.getLong("supplierId");// 供应商ID
        Long shareUserId = request.getLong("shareUserId");
        Integer itemType = request.getInteger("itemType");
        if (itemType== null) {
            itemType = 1;
        }
        try {
            itemDTO = new ItemDTO();
            itemDTO.setId(itemId);
            itemDTO.setSellerId(sellerId);

            ItemSkuQTO itemSkuQTO = new ItemSkuQTO();
            itemSkuQTO.setItemId(itemId);
            itemSkuQTO.setSellerId(sellerId);
            itemSkuQTO.setBizCode(bizCode);
            // 获取ItemSku列表
            List<ItemSkuDTO> itemSkuDTOList = itemSkuManager.queryItemDynamic(itemSkuQTO);

            Integer itemStatus =  itemManager.selectItemStatus(itemId);
            if(itemStatus != null && itemStatus == 4){
                itemDTO.setItemStatus(4);
            }
            if(itemStatus == null || itemStatus == 5 ){
                itemDTO.setItemStatus(5);
            }
            /**
             * 按组合商品 查询  算出
             */

            /**
             * 按组合商品 查询  算出
             */
            try {
                Map map = new HashMap();
                if (itemType == ItemType.COMPOSITE_ITEM.getType()) {
                    maxGain = compositeItemManager.getCompositeGain(itemId, appKey);
                    //1.2.8期收益调整
                    map =compositeItemManager.getIntervalGain(itemId,oneGains,itemSkuDTOList,appKey);
//                    log.info("compositeItem,map:{}",map);

                    // 收益比例描述
                    gainPercentDesc = compositeItemManager.getCompositeIntervalGain(itemId,oneGains,itemSkuDTOList,appKey);
                } else {
                    maxGain = itemManager.getNormalItemGains(itemId, itemSkuDTOList, appKey);
                    //1.2.8期收益调整
                    map=itemManager.getNormalIntervalGains(itemId, itemSkuDTOList,oneGains,appKey);
//                    log.info("commonItem,map:{}",map);

                    // 收益比例描述
                    gainPercentDesc = itemManager.getNormalItemIntervalGains(itemId, itemSkuDTOList,oneGains, appKey);

                }
//                log.info("map:{}",map);
                if (null!=map) {
                    gainsSharingDesc = (String) map.get("gainPercent");
                    sharingGains = (String) map.get("sharingGains");
//                    log.info("gainsSharingDesc:{},sharingGains:{} ",gainsSharingDesc,sharingGains);
                }
            }catch (ItemException e){
                //
                if(e.getResponseCode().getCode() == ResponseCode.BASE_STATE_ITEM_NOT_EXIST.getCode()){
                    //下架
                    itemDTO.setItemStatus(5);
                }else {
                    throw  e;
                }
            }
            
            itemDTO.setGainPercentDesc(gainPercentDesc);

            //1.2.8期收益调整（原接口保留）
            itemDTO.setGainSharingDesc(gainsSharingDesc);
            itemDTO.setSharingGains(sharingGains);
//            log.info(" ##### itemDTO :{} ",JsonUtil.toJson(itemDTO));


            //过滤商品图片，将商品副图和SKU图片分开，这里只提取商品副图，过滤掉商品SKU图片
//				List<ItemImageDTO> itemExtraImageList = getItemExtraImageList(itemImageDTOList);

            //往商品SKU中填充商品sku图片

            itemDTO.setItemSkuDTOList(itemSkuDTOList);
            //TODO 商品价格填充逻辑重构
            if (itemSkuDTOList != null && itemSkuDTOList.isEmpty() == false) {
                ItemSkuDTO itemSkuDTO = itemSkuDTOList.get(0);
                itemDTO.setMarketPrice(itemSkuDTO.getMarketPrice());
                itemDTO.setPromotionPrice(itemSkuDTO.getPromotionPrice());
                itemDTO.setWirelessPrice(itemSkuDTO.getWirelessPrice());
            }

        } catch (ItemException e) {
            response = ResponseUtil.getErrorResponse(e.getCode(), e.getMessage());
            log.error("do action:" + request.getCommand() + " occur Exception:" + e.getMessage(), e);
            return response;
        }


        // 判断商品限购;
        List<ItemBuyLimitDTO> itemBuyLimitDTOs = itemBuyLimitManager.queryItemBuyLimitRecord(sellerId, itemId);
        if (itemBuyLimitDTOs != null) {
            List<LimitEntity> limitEntities = new ArrayList<LimitEntity>();
            for (ItemBuyLimitDTO itemBuyLimitDTO : itemBuyLimitDTOs) {
                LimitEntity limitEntity = new LimitEntity();
                limitEntity.setLimitCount(itemBuyLimitDTO.getBuyCount());
                try {
                    //如果限购时间为空，则代表永久限购，时间上可以填充一个默认最小值和最大值
                    if (itemBuyLimitDTO.getBeginTime() == null) {
                        limitEntity.setBeginTime(dateFormat.parse("2000-01-01 00:00:01"));
                    } else {
                        limitEntity.setBeginTime(itemBuyLimitDTO.getBeginTime());
                    }

                    if (itemBuyLimitDTO.getEndTime() == null) {
                        limitEntity.setEndTime(dateFormat.parse("2102-01-01 00:00:01"));
                    } else {
                        limitEntity.setEndTime(itemBuyLimitDTO.getEndTime());
                    }

                    Date date = new Date();

                    //只有当前时间在限购时间段内，才返回限购数量
                    //if(date.after(limitEntity.getBeginTime())&&date.before(limitEntity.getEndTime())){
                    //   limitEntity.setLimitCount(itemBuyLimitDTO.getBuyCount());
                    //}


                } catch (Exception e) {
                    log.error("", e);
                }
                limitEntities.add(limitEntity);
            }
            itemDTO.setBuyLimit(limitEntities);
        }


        try {
            // 降级处理收益
            itemDTO.setGains(Math.round(maxGain * distributorManager.getGainsSet(appKey).getOneGains() / 100));
        } catch (Exception e) {
            log.error("", "查询收益异常", e.getMessage());
        }

        //收益人
        itemDTO.setShareUserId(shareUserId);

        log.info(" ########## itemDTO :{} ",JsonUtil.toJson(itemDTO));

        //TODO 判断商品收藏状态
        response = ResponseUtil.getSuccessResponse(itemDTO);


        return response;


    }


    @Override
    public String getName() {
        return ActionEnum.GET_ITEM_DYNAMIC.getActionName();
    }
}
