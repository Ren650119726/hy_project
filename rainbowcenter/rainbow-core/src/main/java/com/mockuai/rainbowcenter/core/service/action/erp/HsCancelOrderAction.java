package com.mockuai.rainbowcenter.core.service.action.erp;

import com.mockuai.rainbowcenter.common.api.RainbowResponse;
import com.mockuai.rainbowcenter.common.constant.ActionEnum;
import com.mockuai.rainbowcenter.common.constant.ResponseCode;
import com.mockuai.rainbowcenter.core.exception.RainbowException;
import com.mockuai.rainbowcenter.core.manager.HsOrderManager;
import com.mockuai.rainbowcenter.core.service.RainbowRequest;
import com.mockuai.rainbowcenter.core.service.RequestContext;
import com.mockuai.rainbowcenter.core.service.action.Action;
import com.mockuai.tradecenter.common.domain.OrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * Created by lizg on 2016/6/5.
 * 退款
 */
@Controller
public class HsCancelOrderAction implements Action{

    @Autowired
    private HsOrderManager hsOrderManager;
    @Override
    public RainbowResponse execute(RequestContext context) {
        RainbowRequest request = context.getRequest();
        OrderDTO orderDTO = (OrderDTO)request.getParam("orderDTO");
        Long itemId = (Long) request.getParam("itemId");
        try {
            boolean  result = this.hsOrderManager.hsCancelErpOrder(orderDTO,itemId);
            return new RainbowResponse(result);
        } catch (RainbowException e) {
            return new RainbowResponse(ResponseCode.SYS_E_SERVICE_EXCEPTION, e.getMessage());
        }

    }

    @Override
    public String getName() {
        return ActionEnum.HS_CANCEL_ORDER.getActionName();
    }
}
