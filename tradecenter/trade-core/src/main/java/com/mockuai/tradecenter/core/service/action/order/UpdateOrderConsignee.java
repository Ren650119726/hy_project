package com.mockuai.tradecenter.core.service.action.order;

import com.mockuai.tradecenter.common.api.Request;
import com.mockuai.tradecenter.common.api.TradeResponse;
import com.mockuai.tradecenter.common.constant.ActionEnum;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.common.domain.OrderConsigneeDTO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.OrderConsigneeManager;
import com.mockuai.tradecenter.core.service.RequestContext;
import com.mockuai.tradecenter.core.service.ResponseUtils;
import com.mockuai.tradecenter.core.service.action.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * Created by lizg on 2016/9/20.
 */
public class UpdateOrderConsignee implements Action {

    private static final Logger log = LoggerFactory.getLogger(UpdateOrderConsignee.class);

    @Resource
    private OrderConsigneeManager orderConsigneeManager;

    @Override
    public TradeResponse<Boolean> execute(RequestContext context) throws TradeException {

        Request request = context.getRequest();

        TradeResponse<Boolean> response = null;

        OrderConsigneeDTO orderConsigneeDTO = (OrderConsigneeDTO) request.getParam("orderConsigneeDTO");

        if (null == orderConsigneeDTO.getOrderId()) {
            log.error("orderId is null ");
            return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING, "orderId is null");
        }
        if (null == orderConsigneeDTO.getConsignee()) {
            log.error("consignee is null ");
            return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING, "consignee is null");

        }

        if (null == orderConsigneeDTO.getAddress()) {
            log.error("address is null ");
            return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING, "address is null");

        }

        if (null == orderConsigneeDTO.getMobile()) {
            log.error("mobile is null ");
            return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING, "mobile is null");

        }
        if (null == orderConsigneeDTO.getProvinceCode()) {
            log.error("provinceCode is null ");
            return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING, "provinceCode is null");

        }
        if (null == orderConsigneeDTO.getCityCode()) {
            log.error("cityCode is null ");
            return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING, "cityCode is null");

        }
        if (null == orderConsigneeDTO.getAreaCode()) {
            log.error("areaCode is null ");
            return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING, "areaCode is null");

        }

        int result =0;
        try {
            result = orderConsigneeManager.updateOrderConsignee(orderConsigneeDTO);
        } catch (TradeException e) {
            log.error("db error : " + e);
            throw new TradeException(e.getResponseCode());
        }

        if (result > 0) {
            response = ResponseUtils.getSuccessResponse(true);
        } else {
            log.error("updated failed ");
            response = ResponseUtils.getFailResponse(ResponseCode.BIZ_E_RECORD_NOT_EXIST);
        }

        return response;
    }

    @Override
    public String getName() {
        return ActionEnum.UPDATE_ORDER_CONSIGNEE.getActionName();
    }
}
