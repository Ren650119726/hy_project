package com.mockuai.rainbowcenter.core.service.action.erp;

import com.mockuai.rainbowcenter.common.api.RainbowResponse;
import com.mockuai.rainbowcenter.common.api.Request;
import com.mockuai.rainbowcenter.common.constant.ActionEnum;
import com.mockuai.rainbowcenter.common.constant.ResponseCode;
import com.mockuai.rainbowcenter.common.dto.ErpOrderDTO;
import com.mockuai.rainbowcenter.core.exception.RainbowException;
import com.mockuai.rainbowcenter.core.manager.ActivistOrderManager;
import com.mockuai.rainbowcenter.core.service.RequestContext;
import com.mockuai.rainbowcenter.core.service.action.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

/**
 * Created by lizg on 2016/7/16.
 */

@Controller
public class GetRefundOrderInfoAction implements Action {

    private static final Logger log = LoggerFactory.getLogger(GetRefundOrderInfoAction.class);


    @Resource
    private ActivistOrderManager activistOrderManager;

    @Override
    public RainbowResponse execute(RequestContext context) {
        Request request = context.getRequest();
        String orderSn = (String) request.getParam("orderSn");

        ErpOrderDTO erpOrderDTO;
        try {
            if (orderSn == null) {
                log.error("order sn is null");
                throw new RainbowException(ResponseCode.PARAM_E_PARAM_NULL, "order sn is null");
            }

            erpOrderDTO = activistOrderManager.getRefundOrderInfo(orderSn);

        } catch (RainbowException e) {
            return new RainbowResponse(e.getResponseCode(), e.getMessage());
        }

        return new RainbowResponse(erpOrderDTO);

    }

    @Override
    public String getName() {
        return ActionEnum.GET_REFUND_ORDER_INFO.getActionName();
    }
}
