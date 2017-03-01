package com.mockuai.tradecenter.core.service.action.order.add;

import com.mockuai.tradecenter.common.api.TradeResponse;
import com.mockuai.tradecenter.common.constant.ActionEnum;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.service.RequestContext;
import com.mockuai.tradecenter.core.service.action.Action;
import com.mockuai.tradecenter.core.service.action.order.add.step.*;

/**
 * Created by zengzhangqiang on 5/23/16.
 */
public class AddOrder implements Action {
    private StepPipe addOrderStepPipe;

    public AddOrder() {
        addOrderStepPipe = new StepPipeImpl();
        addOrderStepPipe.addStep(new CheckBaseParamStep());
        addOrderStepPipe.addStep(new CheckOrderPaymentStep());
        addOrderStepPipe.addStep(new CheckOrderDeliveryTypeStep());
        addOrderStepPipe.addStep(new LoadOrderItemStep());
        addOrderStepPipe.addStep(new CheckOrderItemStep());
        addOrderStepPipe.addStep(new CheckPreOrderStep());
        addOrderStepPipe.addStep(new HandleItemFreezingStep());
        addOrderStepPipe.addStep(new HandleOrderSplitStep());
        addOrderStepPipe.addStep(new HandleOrderSettlementStep());
//        addOrderStepPipe.addStep(new HandleHigoExtraInfoStep());
        addOrderStepPipe.addStep(new HandleGiftItemStep());
        addOrderStepPipe.addStep(new HandleOrderAssemblingStep());
//        addOrderStepPipe.addStep(new HandleDistributorInfoStep());
//        addOrderStepPipe.addStep(new HandleGiftPackBuyLimitStep());
        addOrderStepPipe.addStep(new LoadPayTimeoutConfStep());
        addOrderStepPipe.addStep(new HandleOrderTransactionStep());
        addOrderStepPipe.addStep(new LastStep());
    }

    @Override
    public TradeResponse execute(RequestContext context) throws TradeException {
        return addOrderStepPipe.execute(context);
    }

    @Override
    public String getName() {
        return ActionEnum.ADD_ORDER.getActionName();
    }
}
