package com.mockuai.tradecenter.mop.api.action.refund;


import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.constant.MopRespCode;
import com.mockuai.mop.common.constant.ResponseFormat;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.tradecenter.common.api.BaseRequest;
import com.mockuai.tradecenter.common.api.Response;
import com.mockuai.tradecenter.common.constant.ActionEnum;
import com.mockuai.tradecenter.common.domain.SumPayCallBackDTO;
import com.mockuai.tradecenter.common.util.XmlUtil;
import com.mockuai.tradecenter.mop.api.action.BaseAction;
import com.mockuai.tradecenter.mop.api.util.JsonUtil;
import com.mockuai.tradecenter.mop.api.util.MopApiUtil;


public class SumPayRefundCallback extends BaseAction {
    private static final Logger log = LoggerFactory.getLogger(SumPayRefundCallback.class);

    public MopResponse execute(com.mockuai.mop.common.service.action.Request request) {
    	
    	byte[] postData = request.getPostData();
        if(postData == null){
            return new MopResponse(MopRespCode.P_E_PARAM_ISNULL);
        }
        SumPayCallBackDTO sumPayCallBack = new SumPayCallBackDTO();
        try{
        	String response = new String(postData, "utf-8");
        	sumPayCallBack = JsonUtil.parseJson(response, SumPayCallBackDTO.class);
            
        }catch(Exception e){
            log.error("", e);
        }

        com.mockuai.tradecenter.common.api.Request tradeReq = new BaseRequest();
        tradeReq.setCommand(ActionEnum.SUMPAY_REFUND_CALLBACK.getActionName());
        tradeReq.setParam("sumPayCallBackDTO", sumPayCallBack);
        Response tradeResp = getTradeService().execute(tradeReq);

        return MopApiUtil.transferResp(tradeResp);
    }

    public String getName() {
        return "/trade/order/refund/callback/sumpay_notify";
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

