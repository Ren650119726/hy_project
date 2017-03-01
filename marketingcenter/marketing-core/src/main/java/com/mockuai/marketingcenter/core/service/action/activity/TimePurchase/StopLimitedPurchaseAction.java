package com.mockuai.marketingcenter.core.service.action.activity.TimePurchase;

import com.mockuai.marketingcenter.common.api.MarketingResponse;
import com.mockuai.marketingcenter.common.constant.ActionEnum;
import com.mockuai.marketingcenter.common.constant.ActivityType;
import com.mockuai.marketingcenter.common.constant.LimitTimeActivityStatus;
import com.mockuai.marketingcenter.common.constant.ResponseCode;
import com.mockuai.marketingcenter.common.domain.dto.LimitedPurchaseDTO;
import com.mockuai.marketingcenter.common.domain.qto.TimePurchaseQTO;
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

import java.util.List;

/**使活动失效
 * Created by huangsiqian on 2016/10/10.
 */
@Service
public class StopLimitedPurchaseAction extends TransAction {
    @Autowired
    private LimitedPurchaseManager limitedPurchaseManager;
    @Autowired
    private LimitedPurchaseGoodsManager limitedPurchaseGoodsManager;
    @Autowired
    private LimitedGoodsCorrelationManager limitedGoodsCorrelationManager;


    @Override
    protected MarketingResponse doTransaction(RequestContext context) throws MarketingException {
        TimePurchaseQTO timePurchaseQTO = (TimePurchaseQTO)context.getRequest().getParam("timePurchaseQTO");
        //入参校验
        if (timePurchaseQTO == null) {
            return new MarketingResponse(ResponseCode.PARAMETER_NULL, "timePurchaseQTO is null");
        }
        if(timePurchaseQTO.getId()==null){
            return new MarketingResponse(ResponseCode.PARAMETER_NULL, "请传入id");
        }
        Long id= timePurchaseQTO.getId();
        LimitedPurchaseDO activityDO = new LimitedPurchaseDO();
        //先检查活动状态
        LimitedPurchaseDTO activity = limitedPurchaseManager.activityById(id);
        if(activity.getRunStatus().equals(LimitTimeActivityStatus.ALREADYOVER.getValue())){
            return new MarketingResponse<>(ResponseCode.PARAMETER_MISSING,"活动已失效");
        }
        activityDO.setId(id);
        activityDO.setRunStatus(LimitTimeActivityStatus.ALREADYOVER.getValue());
        //活动失效参数为2
        Boolean flag1 = limitedPurchaseManager.updateActivity(activityDO);
        if(!flag1){
            return new MarketingResponse<>(ResponseCode.PARAMETER_MISSING,"活动信息失效失败");
        }
        //商品中间表商品失效
        LimitedGoodsCorrelationDO goodsCorrelationDO = new LimitedGoodsCorrelationDO();
        goodsCorrelationDO.setActivityId(id);
        //限时购活动类型定为0
        goodsCorrelationDO.setActivityType(ActivityType.LIMITEDPURCHASE.getValue());
        Boolean flag2 = limitedGoodsCorrelationManager.stopActivity(goodsCorrelationDO);
        if(!flag2){
            return new MarketingResponse<>(ResponseCode.SUCCESS,"中间表商品失效失败");
        }
        //商品表中商品失效
        LimitedPurchaseGoodsDO limitedGoodsDO = new LimitedPurchaseGoodsDO();
        limitedGoodsDO.setActivityId(id);

        Boolean flag3 = limitedPurchaseGoodsManager.invalidateActivityGoods(limitedGoodsDO);
        if(!flag3){
            return new MarketingResponse<>(ResponseCode.SUCCESS,"商品表商品失效失败");
        }

        return MarketingUtils.getSuccessResponse(true);
    }

    @Override
    public String getName() {
        return ActionEnum.STOP_TIME_PURCHASE.getActionName();
    }
}
