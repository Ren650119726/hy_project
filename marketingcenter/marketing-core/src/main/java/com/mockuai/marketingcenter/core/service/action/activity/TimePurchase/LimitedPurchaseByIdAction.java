package com.mockuai.marketingcenter.core.service.action.activity.TimePurchase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.JsonObject;
import com.mockuai.itemcenter.client.ItemClient;
import com.mockuai.itemcenter.client.ItemSkuClient;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemSkuDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemSkuQTO;
import com.mockuai.marketingcenter.common.api.MarketingResponse;
import com.mockuai.marketingcenter.common.constant.ActionEnum;
import com.mockuai.marketingcenter.common.constant.ResponseCode;
import com.mockuai.marketingcenter.common.domain.dto.LimitedPurchaseDTO;
import com.mockuai.marketingcenter.common.domain.dto.LimitedPurchaseGoodsDTO;
import com.mockuai.marketingcenter.common.domain.qto.TimePurchaseGoodsQTO;
import com.mockuai.marketingcenter.common.domain.qto.TimePurchaseQTO;
import com.mockuai.marketingcenter.common.domain.qto.TimePurchaseSKUGoodsQTO;
import com.mockuai.marketingcenter.core.domain.LimitedPurchaseGoodsDO;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.marketingcenter.core.manager.LimitedPurchaseGoodsManager;
import com.mockuai.marketingcenter.core.manager.LimitedPurchaseManager;
import com.mockuai.marketingcenter.core.service.RequestContext;
import com.mockuai.marketingcenter.core.service.action.TransAction;
import com.mockuai.marketingcenter.core.util.JsonUtil;
import com.mockuai.marketingcenter.core.util.MarketingUtils;

/**限时购单个活动和所有活动产品查询
 * Created by huangsiqian on 2016/10/10.
 */
@Service
public class LimitedPurchaseByIdAction extends TransAction {
    public static final Logger LOGGER = LoggerFactory.getLogger(LimitedPurchaseByIdAction.class);

    @Autowired
    private LimitedPurchaseManager activityManager;
    @Autowired
    private LimitedPurchaseGoodsManager activityGoodsManager;
    @Autowired
    private ItemSkuClient itemSkuClient;
    @Autowired
    private ItemClient itemClient;
    // 单个活动和所有活动产品查询
    @Override
    protected MarketingResponse doTransaction(RequestContext context) throws MarketingException {
        TimePurchaseQTO timePurchaseQTO = (TimePurchaseQTO) context.getRequest().getParam("timePurchaseQTO");
        String appKey = (String) context.getRequest().getParam("appKey");
        //卖家id（定值）
        Long sellerId = 1841254L;
        Long id = timePurchaseQTO.getId();
        LimitedPurchaseDTO activityDTO = activityManager.activityById(id);
        LimitedPurchaseGoodsDO activityGoodsDO = new LimitedPurchaseGoodsDO();
        activityGoodsDO.setActivityId(id);
        //取出活动对应的所有sku商品信息
        List<LimitedPurchaseGoodsDTO> allSkuGoods = activityGoodsManager.activityGoodsList(activityGoodsDO);
        //取出活动所有对应item_id（过滤所有重复item_id）
        List<Long> itemIdList = activityGoodsManager.selectGoodsItemId(id);
        LOGGER.info("itemIdList:{}",JsonUtil.toJson(itemIdList));
        //取出活动所有对应skuId 来取出对应的sku级别商品信息
        List<Long> allSkuId = activityGoodsManager.selectAllSkuId(id);
        LOGGER.info("allSkuid:{}",JsonUtil.toJson(allSkuId));
        Response<List<ItemSkuDTO>> res3 = null;
        try {
            ItemSkuQTO itemSkuQTO = new ItemSkuQTO();
            itemSkuQTO.setSellerId(sellerId);
            itemSkuQTO.setNeedImage(1);
            itemSkuQTO.setIdList(allSkuId);
            res3 = itemSkuClient.queryItemSku(itemSkuQTO, appKey);

        } catch (Exception e) {
            e.printStackTrace();
        }
        LOGGER.info("itemMsg:{}",JsonUtil.toJson(res3.getModule()));
        if (!res3.isSuccess()) {
            return new MarketingResponse(ResponseCode.PARAMETER_NULL, "未查到对应的商品信息");
        }
        List<ItemSkuDTO> itemSkuDTOs = res3.getModule();
        //  根据itemId来包装商品信息
        List<TimePurchaseGoodsQTO> SPU = new ArrayList<>();
        for (Long itemId : itemIdList) {
            ItemDTO itemDTO = null;
            try {
                //根据item_id取spu详细信息
                Response<ItemDTO> res2 = itemClient.getItem(itemId, sellerId, true, appKey);
                LOGGER.info(" ####### res2:{} ",JsonUtil.toJson(res2));
                if(res2.getModule() == null){
                	continue;
                }
                itemDTO = res2.getModule();
            }catch (Exception e){
                e.printStackTrace();
            }


            //返回一个spu级别商品
            TimePurchaseGoodsQTO goodsQTO = new TimePurchaseGoodsQTO();
            goodsQTO.setActivityId(id);
            goodsQTO.setItemId(itemId);
            //把商品spu信息代入
            Map map = new HashMap();
            map.put("spu", itemDTO);
            goodsQTO.setMap(map);

            
            
            List<TimePurchaseSKUGoodsQTO> SKU = new ArrayList<>();
            for (LimitedPurchaseGoodsDTO skuGoods : allSkuGoods) {
                TimePurchaseSKUGoodsQTO sku = new TimePurchaseSKUGoodsQTO();
                if(skuGoods.getItemId().equals(itemId)){

                    sku.setSkuId(skuGoods.getSkuId());
                    sku.setGoodsPrice(skuGoods.getGoodsPrice());
                    sku.setGoodsQuantity(skuGoods.getGoodsQuantity());
                    for (ItemSkuDTO itemSkuDTO : itemSkuDTOs) {
                        if (skuGoods.getSkuId().equals(itemSkuDTO.getId())) {
                            sku.setPromotionPrice(itemSkuDTO.getPromotionPrice());
                            sku.setSkuCode(itemSkuDTO.getSkuCode());

                            LOGGER.info("itemMsg:{}", JsonUtil.toJson(itemSkuDTO));
                            sku.setStockNum(itemSkuDTO.getStockNum());
                        }
                    }
                    // 兼容商品删除逻辑，待重构
                    if(sku.getPromotionPrice() != null || sku.getStockNum() != null || StringUtils.isNotBlank(sku.getSkuCode())){
                    	SKU.add(sku);
                    }
                    
                }
            }
            //一个spu相关信息处理完之后再加进包装类中
            LOGGER.info("SKUMSG:{}",JsonUtil.toJson(SKU));
            goodsQTO.setSkuInfo(SKU);
            //spu级别添加进list
            SPU.add(goodsQTO);
        }
        LOGGER.info("SPUMSG:{}",JsonUtil.toJson(SPU));

        TimePurchaseQTO purchaseQTO =new TimePurchaseQTO();
        purchaseQTO.setId(activityDTO.getId());
        purchaseQTO.setActivityName(activityDTO.getActivityName());
        purchaseQTO.setStartTime(activityDTO.getStartTime());
        purchaseQTO.setEndTime(activityDTO.getEndTime());
        purchaseQTO.setIssueStatus(activityDTO.getIssueStatus());
        purchaseQTO.setRunStatus(activityDTO.getRunStatus());
        purchaseQTO.setActivityTag(activityDTO.getActivityTag());
        purchaseQTO.setVoucherType(activityDTO.getVoucherType());
        //商品信息
        purchaseQTO.setGoodsInfo(SPU);
        return MarketingUtils.getSuccessResponse(purchaseQTO);
    }

    @Override
    public String getName() {
        return ActionEnum.TIME_PURCHASE_BY_ID.getActionName();
    }
}
