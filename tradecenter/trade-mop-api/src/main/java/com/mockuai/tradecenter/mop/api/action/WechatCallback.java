package com.mockuai.tradecenter.mop.api.action;


import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.constant.MopRespCode;
import com.mockuai.mop.common.constant.ResponseFormat;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.tradecenter.common.api.BaseRequest;
import com.mockuai.tradecenter.common.api.Response;
import com.mockuai.tradecenter.common.constant.ActionEnum;
import com.mockuai.tradecenter.common.domain.WxPaymentDTO;
import com.mockuai.tradecenter.common.util.XmlUtil;
import com.mockuai.tradecenter.mop.api.util.MopApiUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;


public class WechatCallback extends BaseAction {
    private static final Logger log = LoggerFactory.getLogger(WechatCallback.class);

    public MopResponse execute(com.mockuai.mop.common.service.action.Request request) {
        byte[] postData = request.getPostData();
        if(postData == null){
            return new MopResponse(MopRespCode.P_E_PARAM_ISNULL);
        }
        WxPaymentDTO wxPaymentDTO = new WxPaymentDTO();
        try{
            Map<String,String> xmlDataMap = XmlUtil.xmlStr2Map(new String(postData, "utf-8"));
            String returnCode = xmlDataMap.get("return_code");
            String returnMsg = xmlDataMap.get("return_msg");
            String outerTradeNo = xmlDataMap.get("outer_trade_no");
            String resultCode = xmlDataMap.get("result_code");
            String totalFeeStr = xmlDataMap.get("total_fee");
            String attach = xmlDataMap.get("attach");
            String transactionId = xmlDataMap.get("transaction_id");
            String timeEnd = xmlDataMap.get("timeEnd");
            String sign = xmlDataMap.get("sign");
            wxPaymentDTO.setReturnCode(returnCode);
            wxPaymentDTO.setReturnMsg(returnMsg);
            wxPaymentDTO.setOuterTradeNo(outerTradeNo);
            wxPaymentDTO.setResultCode(resultCode);
            wxPaymentDTO.setTotalFeeStr(totalFeeStr);
            wxPaymentDTO.setAttach(attach);
            wxPaymentDTO.setTransactionId(transactionId);
            wxPaymentDTO.setTimeEnd(timeEnd);
            wxPaymentDTO.setSign(sign);
            wxPaymentDTO.setOriginParamMap(xmlDataMap);
        }catch(Exception e){
            log.error("", e);
        }

        com.mockuai.tradecenter.common.api.Request tradeReq = new BaseRequest();
        tradeReq.setCommand(ActionEnum.WECHAT_PAY_CALLBACK.getActionName());
        tradeReq.setParam("wxPaymentDTO", wxPaymentDTO);
        Response tradeResp = getTradeService().execute(tradeReq);

        return MopApiUtil.transferResp(tradeResp);
    }

    public String getName() {
        return "/trade/order/payment/callback/wechat_notify";
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

/* Location:           /work/tmp/trade-mop-api-0.0.1-20150519.033139-22.jar
 * Qualified Name:     com.mockuai.tradecenter.mop.api.action.WechatCallback
 * JD-Core Version:    0.6.2
 */