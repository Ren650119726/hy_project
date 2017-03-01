package com.mockuai.marketingcenter.core.service.action.activity.TimePurchase;

import com.mockuai.marketingcenter.common.api.MarketingResponse;
import com.mockuai.marketingcenter.common.constant.ActionEnum;
import com.mockuai.marketingcenter.common.constant.ActivityType;
import com.mockuai.marketingcenter.common.constant.ResponseCode;
import com.mockuai.marketingcenter.common.domain.dto.LimitedGoodsCorrelationDTO;
import com.mockuai.marketingcenter.common.domain.dto.LimitedPurchaseDTO;
import com.mockuai.marketingcenter.common.domain.dto.LimitedPurchaseGoodsDTO;
import com.mockuai.marketingcenter.common.domain.qto.TimePurchaseGoodsQTO;
import com.mockuai.marketingcenter.common.domain.qto.TimePurchaseQTO;
import com.mockuai.marketingcenter.common.domain.qto.TimePurchaseSKUGoodsQTO;
import com.mockuai.marketingcenter.core.dao.LimitedPurchaseGoodsDAO;
import com.mockuai.marketingcenter.core.domain.LimitedGoodsCorrelationDO;
import com.mockuai.marketingcenter.core.domain.LimitedPurchaseDO;
import com.mockuai.marketingcenter.core.domain.LimitedPurchaseGoodsDO;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.marketingcenter.core.manager.LimitedGoodsCorrelationManager;
import com.mockuai.marketingcenter.core.manager.LimitedPurchaseGoodsManager;
import com.mockuai.marketingcenter.core.manager.LimitedPurchaseManager;
import com.mockuai.marketingcenter.core.service.RequestContext;
import com.mockuai.marketingcenter.core.service.action.TransAction;
import com.mockuai.marketingcenter.core.util.MarketingUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**修改活动和活动产品信息
 * Created by huangsiqian on 2016/10/10.
 */
@Service
public class UpdateTimePurchaseAction extends TransAction {
    @Autowired
    private LimitedPurchaseManager activityManager;
    @Autowired
    private LimitedPurchaseGoodsManager activityGoodsManager;
    @Autowired
    private LimitedGoodsCorrelationManager limitedGoodsCorrelationManager;
    @Autowired
    private LimitedPurchaseGoodsDAO limitedPurchaseGoodsDAO;

    @Override
    protected MarketingResponse doTransaction(RequestContext context) throws MarketingException {
        TimePurchaseQTO timePurchaseQTO = (TimePurchaseQTO) context.getRequest().getParam("timePurchaseQTO");
        //入参校验
        if (timePurchaseQTO == null) {
            return new MarketingResponse(ResponseCode.PARAMETER_NULL, "timePurchaseQTO is null");
        }
        if (timePurchaseQTO.getId() == null) {
            return new MarketingResponse(ResponseCode.PARAMETER_NULL, "请传入id");
        }
        Long id = timePurchaseQTO.getId();


        //更改前删除商品信息 和 中间表商品信息

        activityGoodsManager.deleteGoods(id);
        limitedGoodsCorrelationManager.deleteGoods(id);

        //添加商品信息
        List<TimePurchaseGoodsQTO> list = timePurchaseQTO.getGoodsInfo();

        //判断是否有商品传入
        if (list.isEmpty()) {
            return new MarketingResponse(ResponseCode.PARAMETER_NULL, "请传入商品信息");
        }
        //添加入表
        for (TimePurchaseGoodsQTO timePurchaseGoodsQTO : list) {
            //判断是否有商品参加过别的活动
            Long itemId = timePurchaseGoodsQTO.getItemId();
            List<LimitedGoodsCorrelationDTO> goodsMsg = limitedGoodsCorrelationManager.selectMsgByItemId(itemId);
            if (goodsMsg != null || goodsMsg.size() > 0) {
                    Long startTime = timePurchaseQTO.getStartTime().getTime();
                    Long endTime = timePurchaseQTO.getEndTime().getTime();

                Iterator<LimitedGoodsCorrelationDTO> it3 = goodsMsg.iterator();
                while (it3.hasNext()) {
                    LimitedGoodsCorrelationDTO goodsCorrelationDTO = it3.next();
                    Long tableStartTime = goodsCorrelationDTO.getStartTime().getTime();
                    Long tableEndTime = goodsCorrelationDTO.getEndTime().getTime();
                    if ((startTime < tableStartTime || startTime.equals(tableStartTime))
                            && (tableStartTime < endTime || tableStartTime.equals(endTime))) {
                        return MarketingUtils.getFailResponse(ResponseCode.PARAMETER_ERROR.getCode(), timePurchaseGoodsQTO.getItemName() + "已参加活动");
                    }
                     if ((tableStartTime < startTime || startTime.equals(tableStartTime))
                            && (tableEndTime > startTime || tableEndTime.equals(endTime))) {
                         return MarketingUtils.getFailResponse(ResponseCode.PARAMETER_ERROR.getCode(), timePurchaseGoodsQTO.getItemName() + "已参加活动");
                    }
                }
            }

            //sku商品添加
            List<TimePurchaseSKUGoodsQTO> skuGoods = timePurchaseGoodsQTO.getSkuInfo();
            if (skuGoods == null || skuGoods.size() == 0) {
                return new MarketingResponse(ResponseCode.PARAMETER_NULL, "请传入商品信息");
            }
                LimitedGoodsCorrelationDO goodsCorrelationDO = new LimitedGoodsCorrelationDO();
                //添加到营销商品中间表中
                goodsCorrelationDO.setActivityId(timePurchaseQTO.getId());
                goodsCorrelationDO.setItemId(timePurchaseGoodsQTO.getItemId());
                goodsCorrelationDO.setStartTime(timePurchaseQTO.getStartTime());
                goodsCorrelationDO.setEndTime(timePurchaseQTO.getEndTime());
                //定义限时购活动类型为0
                goodsCorrelationDO.setActivityType(ActivityType.LIMITEDPURCHASE.getValue());
                Boolean flag = limitedGoodsCorrelationManager.addActivityGoods(goodsCorrelationDO);

            for (TimePurchaseSKUGoodsQTO SKUGoodsQTO : skuGoods) {
                LimitedPurchaseGoodsDO purchaseGoodsDO = new LimitedPurchaseGoodsDO();

                //商品信息添加到商品表中
                purchaseGoodsDO.setActivityId(timePurchaseQTO.getId());
                purchaseGoodsDO.setItemId(timePurchaseGoodsQTO.getItemId());
                purchaseGoodsDO.setGoodsQuantity(SKUGoodsQTO.getGoodsQuantity());
                purchaseGoodsDO.setGoodsPrice(SKUGoodsQTO.getGoodsPrice());
                purchaseGoodsDO.setSkuId(SKUGoodsQTO.getSkuId());
                activityGoodsManager.addActivityGoods(purchaseGoodsDO);
            }

        }

        //更改活动信息
        LimitedPurchaseDO activityDO = new LimitedPurchaseDO();

        if (null == timePurchaseQTO.getActivityName() || "".equals(timePurchaseQTO.getActivityName())) {
            return new MarketingResponse<>(ResponseCode.PARAMETER_NULL, "请传入活动名");
        }
        if (timePurchaseQTO.getActivityName().length() > 50) {
            return new MarketingResponse<>(ResponseCode.PARAMETER_NULL, "活动名超过限制");
        }
        if (null == timePurchaseQTO.getActivityTag() || "".equals(timePurchaseQTO.getActivityTag())) {
            return new MarketingResponse<>(ResponseCode.PARAMETER_NULL, "请传入活动角标");
        }
        if (null == timePurchaseQTO.getVoucherType() || "".equals(timePurchaseQTO.getVoucherType())) {
            return new MarketingResponse<>(ResponseCode.PARAMETER_NULL, "请传入角标参数");
        }
        if (null == timePurchaseQTO.getActivityName() || "".equals(timePurchaseQTO.getActivityName())) {
            return new MarketingResponse<>(ResponseCode.PARAMETER_NULL, "请传入活动名");
        }
        if (timePurchaseQTO.getActivityName().length() > 50) {
            return new MarketingResponse<>(ResponseCode.PARAMETER_NULL, "活动名超过限制");
        }
        if (null == timePurchaseQTO.getActivityTag() || "".equals(timePurchaseQTO.getActivityTag())) {
            return new MarketingResponse<>(ResponseCode.PARAMETER_NULL, "请传入活动角标");
        }
        if (null == timePurchaseQTO.getVoucherType() || "".equals(timePurchaseQTO.getVoucherType())) {
            return new MarketingResponse<>(ResponseCode.PARAMETER_NULL, "请传入角标参数");
        }
        activityDO.setId(timePurchaseQTO.getId());
        activityDO.setStartTime(timePurchaseQTO.getStartTime());
        activityDO.setEndTime(timePurchaseQTO.getEndTime());
        activityDO.setActivityName(timePurchaseQTO.getActivityName());
        activityDO.setActivityTag(timePurchaseQTO.getActivityTag());
        activityDO.setVoucherType(timePurchaseQTO.getVoucherType());


        //  修改活动信息
        Boolean flag = activityManager.modifyActivity(activityDO);


        return MarketingUtils.getSuccessResponse(true);
    }



    @Override
    public String getName() {

        return ActionEnum.UPDATE_TIME_PURCHASE.getActionName();
    }
}

