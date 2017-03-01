package com.mockuai.marketingcenter.core.service.action.activity.TimePurchase;

import com.mockuai.marketingcenter.common.api.MarketingResponse;
import com.mockuai.marketingcenter.common.constant.ActionEnum;
import com.mockuai.marketingcenter.common.constant.ResponseCode;
import com.mockuai.marketingcenter.common.domain.dto.LimitedPurchaseGoodsDTO;
import com.mockuai.marketingcenter.core.domain.LimitedGoodsCorrelationDO;
import com.mockuai.marketingcenter.core.domain.LimitedPurchaseGoodsDO;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.marketingcenter.core.manager.LimitedGoodsCorrelationManager;
import com.mockuai.marketingcenter.core.manager.LimitedPurchaseGoodsManager;
import com.mockuai.marketingcenter.core.service.RequestContext;
import com.mockuai.marketingcenter.core.service.action.TransAction;
import com.mockuai.marketingcenter.core.util.MarketingUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by huangsiqian on 2016/10/10.
 */
@Service
public class DeleteActivityGoodsAction extends TransAction {
    @Autowired
    private LimitedPurchaseGoodsManager activityGoodsManager;
    @Autowired
    private LimitedGoodsCorrelationManager limitedGoodsCorrelationManager;

    @Override
    protected MarketingResponse doTransaction(RequestContext context) throws MarketingException {
        LimitedPurchaseGoodsDTO activityGoodsDTO = (LimitedPurchaseGoodsDTO) context.getRequest().getParam("activityGoodsDTO");
        //入参校验
        if (activityGoodsDTO == null) {
            return new MarketingResponse(ResponseCode.PARAMETER_NULL, "没有传入商品信息");
        }
        if (activityGoodsDTO.getActivityId() == null || "".equals(activityGoodsDTO.getActivityId())) {
            return new MarketingResponse(ResponseCode.PARAMETER_NULL, "请输入活动id");
        }
        if (activityGoodsDTO.getItemId() == null || "".equals(activityGoodsDTO.getItemId())) {
            return new MarketingResponse(ResponseCode.PARAMETER_NULL, "请输入产品id");
        }

        LimitedPurchaseGoodsDO activityGoodsDO = new LimitedPurchaseGoodsDO();
        activityGoodsDO.setActivityId(activityGoodsDTO.getActivityId());
        activityGoodsDO.setItemId(activityGoodsDTO.getItemId());
        Boolean flag = activityGoodsManager.deleteActivityGoods(activityGoodsDO);
        if (!flag) {
            return new MarketingResponse(ResponseCode.PARAMETER_NULL, "删除商品失败");
        }
        //限时购商品删除完成后，再删除活动中间表中商品
        LimitedGoodsCorrelationDO goodsCorrelationDO = new LimitedGoodsCorrelationDO();
        goodsCorrelationDO.setActivityId(activityGoodsDTO.getActivityId());
        goodsCorrelationDO.setItemId(activityGoodsDTO.getItemId());
        goodsCorrelationDO.setActivityType(0);
        Boolean flag2 = limitedGoodsCorrelationManager.stopActivity(goodsCorrelationDO);
        if (!flag2) {
            return new MarketingResponse(ResponseCode.PARAMETER_NULL, "删除中间表商品失败");
        }
        return MarketingUtils.getSuccessResponse(true);
    }

    @Override
    public String getName() {
        return ActionEnum.DELETE_TIME_PURCHASE_GOODS.getActionName();
    }
}
