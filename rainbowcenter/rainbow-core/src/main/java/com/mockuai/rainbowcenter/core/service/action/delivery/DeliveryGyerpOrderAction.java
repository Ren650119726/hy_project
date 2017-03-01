package com.mockuai.rainbowcenter.core.service.action.delivery;

import com.mockuai.rainbowcenter.common.api.RainbowResponse;
import com.mockuai.rainbowcenter.common.constant.ActionEnum;
import com.mockuai.rainbowcenter.common.constant.ResponseCode;
import com.mockuai.rainbowcenter.core.exception.RainbowException;
import com.mockuai.rainbowcenter.core.manager.HsOrderManager;
import com.mockuai.rainbowcenter.core.service.RainbowRequest;
import com.mockuai.rainbowcenter.core.service.RequestContext;
import com.mockuai.rainbowcenter.core.service.action.Action;
import com.mockuai.tradecenter.common.domain.OrderDTO;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

/**
 * Created by lzig on 2016/6/1.
 * 支付成功的订单推送给管易ERP业务action控制类
 */
@Controller
public class DeliveryGyerpOrderAction implements Action {

    @Resource
    private HsOrderManager hsOrderManager;

    @Override
    public RainbowResponse execute(RequestContext context) {
        RainbowRequest request = context.getRequest();
        OrderDTO orderDTO = (OrderDTO) request.getParam("orderDTO");

        try {
            if (null == orderDTO) {
                throw new RainbowException(ResponseCode.PARAM_E_PARAM_NULL);
            }
            Boolean tsResult = this.hsOrderManager.deliveryOrderToGyerp(orderDTO);
            return new RainbowResponse(tsResult);
        } catch (RainbowException e) {
            return new RainbowResponse(e.getResponseCode(), e.getMessage());
        }
    }

    @Override
    public String getName() {
        return ActionEnum.DELIVERY_GYERP_ORDER.getActionName();
    }
}
