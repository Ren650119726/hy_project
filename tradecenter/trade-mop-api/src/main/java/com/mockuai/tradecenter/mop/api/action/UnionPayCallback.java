package com.mockuai.tradecenter.mop.api.action;


import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.constant.ResponseFormat;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.tradecenter.common.api.BaseRequest;
import com.mockuai.tradecenter.common.api.Response;
import com.mockuai.tradecenter.common.constant.ActionEnum;
import com.mockuai.tradecenter.common.domain.AlipaymentDTO;
import com.mockuai.tradecenter.common.domain.UnionPaymentDTO;
import com.mockuai.tradecenter.mop.api.util.MopApiUtil;


public class UnionPayCallback extends BaseAction {
    public MopResponse execute(com.mockuai.mop.common.service.action.Request request) {
        String orderId = (String) request.getParam("orderId");
        String reqReserved = (String) request.getParam("reqReserved");
        String txnAmt = (String) request.getParam("txnAmt");
        String merId = (String) request.getParam("merId");
        String respCode = (String) request.getParam("respCode");
        String respMsg = (String) request.getParam("respMsg");
        String settleAmt = (String) request.getParam("settleAmt");
        String queryId = (String) request.getParam("queryId");
        String traceNo = (String) request.getParam("traceNo");
        String traceTime = (String) request.getParam("traceTime");


        UnionPaymentDTO unionPaymentDTO = new UnionPaymentDTO();
        unionPaymentDTO.setOrderId(orderId);
        unionPaymentDTO.setReqReserved(reqReserved);
        unionPaymentDTO.setTxnAmt(txnAmt);
        unionPaymentDTO.setMerId(merId);
        unionPaymentDTO.setRespCode(respCode);
        unionPaymentDTO.setRespMsg(respMsg);
        unionPaymentDTO.setSettleAmt(settleAmt);
        unionPaymentDTO.setQueryId(queryId);
        unionPaymentDTO.setTraceNo(traceNo);
        unionPaymentDTO.setTraceTime(traceTime);
        unionPaymentDTO.setOriginParamMap(request.getParamMap());

        com.mockuai.tradecenter.common.api.Request tradeReq = new BaseRequest();
        tradeReq.setCommand(ActionEnum.UNION_PAY_CALLBACK.getActionName());
        tradeReq.setParam("unionPaymentDTO", unionPaymentDTO);

        Response tradeResp = getTradeService().execute(tradeReq);

        return MopApiUtil.transferResp(tradeResp);
    }


    public String getName() {
        return "/trade/order/payment/callback/unionpay_notify";
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

//*/
/* Location:           /work/tmp/trade-mop-api-0.0.1-20150519.033139-22.jar
/*/
/* Qualified Name:     com.mockuai.tradecenter.mop.api.action.AlipayCallback2
/*/
/* JD-Core Version:    0.6.2
/*/
/*/*/
