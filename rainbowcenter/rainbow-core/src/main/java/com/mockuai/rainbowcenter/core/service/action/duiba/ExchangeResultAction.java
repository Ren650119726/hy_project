package com.mockuai.rainbowcenter.core.service.action.duiba;

import com.mockuai.rainbowcenter.common.api.RainbowResponse;
import com.mockuai.rainbowcenter.common.api.Request;
import com.mockuai.rainbowcenter.common.constant.ActionEnum;
import com.mockuai.rainbowcenter.common.constant.ResponseCode;
import com.mockuai.rainbowcenter.common.dto.DuibaRecordOrderDTO;
import com.mockuai.rainbowcenter.core.exception.RainbowException;
import com.mockuai.rainbowcenter.core.manager.DuiBaManager;
import com.mockuai.rainbowcenter.core.service.RequestContext;
import com.mockuai.rainbowcenter.core.service.action.Action;
import com.mockuai.rainbowcenter.core.util.PropertyHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

/**
 * Created by lizg on 2016/7/19.
 */

@Controller
public class ExchangeResultAction implements Action {

    private static final Logger log = LoggerFactory.getLogger(ExchangeResultAction.class);

    @Resource
    private DuiBaManager duiBaManager;

    @Override
    public RainbowResponse execute(RequestContext context) {

        Request request = context.getRequest();

        String isSuccess = (String) request.getParam("success"); //兑换是否成功
        String errorMessage = (String) request.getParam("errorMessage");
        String orderNum = (String) request.getParam("orderNum");//兑吧订单号
        String bizId = (String) request.getParam("bizId"); //我们的订单号
        String appKey = PropertyHelper.getKeyValue("haiyun_appkey");
        String result;

        try {
            if (isSuccess == null) {
                log.error("isSuccess is null");
                throw new RainbowException(ResponseCode.PARAM_E_PARAM_NULL, "isSuccess is null");
            }
            if (orderNum == null) {
                log.error("orderNum is null");
                throw new RainbowException(ResponseCode.PARAM_E_PARAM_NULL, "orderNum is null");
            }
            if (appKey == null) {
                log.error("appKey is null");
                throw new RainbowException(ResponseCode.PARAM_E_PARAM_NULL, "appKey is null");
            }
            DuibaRecordOrderDTO duibaRecordOrderDTO = new DuibaRecordOrderDTO();
            duibaRecordOrderDTO.setBizNum(bizId);
            duibaRecordOrderDTO.setOrderNum(orderNum);
            duibaRecordOrderDTO.setErrorMessage(errorMessage);
            duibaRecordOrderDTO.setSuccess(Boolean.valueOf(isSuccess));

            result = duiBaManager.exchangeResultNotice(duibaRecordOrderDTO, appKey);

        } catch (RainbowException e) {
            return new RainbowResponse(e.getResponseCode(), e.getMessage());
        }


        return new RainbowResponse(result);
    }

    @Override
    public String getName() {
        return ActionEnum.DUIBA_EXCHANGE_RESULT.getActionName();
    }
}
