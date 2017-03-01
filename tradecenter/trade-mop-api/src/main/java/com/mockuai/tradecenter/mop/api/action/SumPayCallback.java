package com.mockuai.tradecenter.mop.api.action;


import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.constant.MopRespCode;
import com.mockuai.mop.common.constant.ResponseFormat;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.tradecenter.common.api.BaseRequest;
import com.mockuai.tradecenter.common.api.Response;
import com.mockuai.tradecenter.common.constant.ActionEnum;
import com.mockuai.tradecenter.common.domain.SumPayCallBackDTO;
import com.mockuai.tradecenter.common.domain.WxPaymentDTO;
import com.mockuai.tradecenter.common.util.XmlUtil;
import com.mockuai.tradecenter.mop.api.util.JsonUtil;
import com.mockuai.tradecenter.mop.api.util.MopApiUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;


public class SumPayCallback extends BaseAction {
    private static final Logger log = LoggerFactory.getLogger(SumPayCallback.class);

    public MopResponse execute(com.mockuai.mop.common.service.action.Request request) {
        byte[] postData = request.getPostData();
        if(postData == null){
            return new MopResponse(MopRespCode.P_E_PARAM_ISNULL);
        }
        SumPayCallBackDTO sumPayCallBack = new SumPayCallBackDTO();
        try{
        	String response = new String(postData, "utf-8");
        	log.error("sumpay call back response:"+response);
        	 sumPayCallBack = JsonUtil.parseJson(response, SumPayCallBackDTO.class);
            
//            sumPayCallBack.setOriginParamMap(xmlDataMap);
        }catch(Exception e){
            log.error("", e);
        }

        com.mockuai.tradecenter.common.api.Request tradeReq = new BaseRequest();
        tradeReq.setCommand(ActionEnum.SUM_PAY_CALLBACK.getActionName());
        tradeReq.setParam("sumPayCallBackDTO", sumPayCallBack);
        Response tradeResp = getTradeService().execute(tradeReq);

        return MopApiUtil.transferResp(tradeResp);
    }

    public String getName() {
        return "/trade/order/payment/callback/sumpay_notify";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.NO_AUTH;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.NO_LIMIT;
    }

    public ResponseFormat getResponseFormat() {
        return ResponseFormat.CUSTOMIZE;
    }
}

