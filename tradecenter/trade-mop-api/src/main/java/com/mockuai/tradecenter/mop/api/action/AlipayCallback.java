package com.mockuai.tradecenter.mop.api.action;


import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.constant.ResponseFormat;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.tradecenter.common.api.BaseRequest;
import com.mockuai.tradecenter.common.api.Response;
import com.mockuai.tradecenter.common.constant.ActionEnum;
import com.mockuai.tradecenter.common.domain.AlipaymentDTO;
import com.mockuai.tradecenter.mop.api.util.MopApiUtil;


public class AlipayCallback extends BaseAction {
    public MopResponse execute(com.mockuai.mop.common.service.action.Request request) {
//        String resultStatus = (String)request.getParam("resultStatus");
//        String result = (String)request.getParam("result");
//        String memo = (String)request.getParam("memo");

//        String isSuccess = (String) request.getParam("is_success");
        String sign = (String) request.getParam("sign");
        String signType = (String) request.getParam("sign_type");
        String notifyType = (String) request.getParam("notify_type");
        String notifyId = (String) request.getParam("notify_id");
        String notifyTime = (String) request.getParam("notify_time");
        String outTradeNo = (String) request.getParam("out_trade_no");
        String tradeNo = (String) request.getParam("trade_no");
        String totalFee = (String) request.getParam("total_fee");
        String tradeStatus = (String) request.getParam("trade_status");
        String batchNo = (String) request.getParam("batch_no");
        String successDetails = (String)request.getParam("success_details");
        String failDetails = (String)request.getParam("fail_details");
        String result_details = (String)request.getParam("result_details");//退款详细信息
        
        
        AlipaymentDTO alipaymentDTO = new AlipaymentDTO();
//        alipaymentDTO.setExtraCommonParam(extraCommonParam);
        alipaymentDTO.setTradeNo(tradeNo);
        alipaymentDTO.setOutTradeNo(outTradeNo);
        alipaymentDTO.setTradeStatus(tradeStatus);
        alipaymentDTO.setTotalFee(totalFee);
        alipaymentDTO.setSignType(signType);
        alipaymentDTO.setSign(sign);
        alipaymentDTO.setNotifyTime(notifyTime);
        alipaymentDTO.setNotifyType(notifyType);
        alipaymentDTO.setNotifyId(notifyId);
        alipaymentDTO.setBatchNo(batchNo);
        alipaymentDTO.setSuccessDetails(successDetails);
        alipaymentDTO.setFailDetails(failDetails);
        alipaymentDTO.setOriginParamMap(request.getParamMap());
        if(null!=result_details){
        	 alipaymentDTO.setResultDetails(result_details);
        }
       
        com.mockuai.tradecenter.common.api.Request tradeReq = new BaseRequest();
        tradeReq.setCommand(ActionEnum.ALIPAY_CALLBACK.getActionName());
        tradeReq.setParam("alipaymentDTO", alipaymentDTO);

        Response tradeResp = getTradeService().execute(tradeReq);

        return MopApiUtil.transferResp(tradeResp);
    }


    public String getName() {
        return "/trade/order/payment/callback/alipay_notify";
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
 * Qualified Name:     com.mockuai.tradecenter.mop.api.action.AlipayCallback2
 * JD-Core Version:    0.6.2
 */