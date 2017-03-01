package com.mockuai.rainbowcenter.mop.api.action.duiba;

import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.constant.MopRespCode;
import com.mockuai.mop.common.constant.ResponseFormat;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.mop.common.service.action.Request;
import com.mockuai.rainbowcenter.common.api.BaseRequest;
import com.mockuai.rainbowcenter.common.api.Response;
import com.mockuai.rainbowcenter.common.constant.ActionEnum;
import com.mockuai.rainbowcenter.mop.api.action.BaseAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Created by lizg on 2016/7/18.
 */
public class OrderExchangeResult extends BaseAction {

    private static final Logger log = LoggerFactory.getLogger(OrderExchangeResult.class);

    private static String APP_KEY = "4WmLRUCPSZEqfape98w68TVA49ez";

    public MopResponse execute(Request request) {
        String isSuccess = (String) request.getParam("success"); //兑换是否成功
        log.info("[{}] isSuccess:{}",isSuccess);
        String errorMessage = (String) request.getParam("errorMessage");
        String orderNum = (String) request.getParam("orderNum");//兑吧订单号
        String bizId = (String) request.getParam("bizId"); //我们的订单号
        String appKey = (String)request.getParam("appKey");
        String timestamp = (String)request.getParam("timestamp");

        if (isSuccess == null) {
            log.error("success is null");
            return new MopResponse(MopRespCode.P_E_PARAM_ISNULL, "success is null");
        }

        if (orderNum == null) {
            log.error("order num is null");
            return new MopResponse((MopRespCode.P_E_PARAM_ISNULL),"order num is null");
        }
        if (bizId == null) {
           log.error("biz id is null");
            return new MopResponse((MopRespCode.P_E_PARAM_ISNULL),"bizid is null");
        }
        if (appKey == null) {
            log.error("app key is null");
            return new MopResponse((MopRespCode.P_E_PARAM_ISNULL),"appKey is null");
        }

        if (!APP_KEY.equals(appKey)) {
            return new MopResponse(MopRespCode.P_E_PARAM_ISNULL, "appKey不匹配");
        }

        if(timestamp ==null){
            return new MopResponse(MopRespCode.P_E_PARAM_ISNULL, "请求中没有带时间戳");
        }
        log.info("[{}] errorMessage :{}",errorMessage);
        try {
            errorMessage = URLDecoder.decode(errorMessage, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setParam("success",isSuccess);
        baseRequest.setParam("errorMessage",errorMessage);
        baseRequest.setParam("orderNum",orderNum);
        baseRequest.setParam("bizId",bizId);
        baseRequest.setParam("appKey",appKey);
        baseRequest.setCommand(ActionEnum.DUIBA_EXCHANGE_RESULT.getActionName());


        Response<String> response = getRainbowService().execute(baseRequest);
        if (!response.isSuccess()) {
            log.error("duiba exchange credits error, success: {},orderNum:{}, msg: {}", isSuccess, orderNum, response.getMessage());
            return new MopResponse(MopRespCode.S_E_SERVICE_ERROR, response.getMessage());
        }
        return new MopResponse(response.getModule());
    }


    public String getName() {
        return "/duiba/exchange/result";
    }


    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.NO_AUTH;
    }


    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_GET;
    }

    /**
     * 用户自定义返回格式
     * @return
     */
    public ResponseFormat getResponseFormat() {
        return ResponseFormat.CUSTOMIZE;
    }
}
