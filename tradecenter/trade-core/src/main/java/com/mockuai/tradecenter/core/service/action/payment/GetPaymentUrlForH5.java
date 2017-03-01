package com.mockuai.tradecenter.core.service.action.payment;

import com.mockuai.tradecenter.common.api.Request;
import com.mockuai.tradecenter.common.api.TradeResponse;
import com.mockuai.tradecenter.common.constant.ActionEnum;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.common.domain.PaymentUrlDTO;
import com.mockuai.tradecenter.common.util.XmlUtil;
import com.mockuai.tradecenter.core.domain.OrderDO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.OrderManager;
import com.mockuai.tradecenter.core.service.RequestContext;
import com.mockuai.tradecenter.core.service.ResponseUtils;
import com.mockuai.tradecenter.core.service.action.Action;
import com.mockuai.tradecenter.core.util.HttpUtil;
import com.mockuai.tradecenter.core.util.JsonUtil;
import com.mockuai.tradecenter.core.util.SignUtils;
import com.unionpay.acp.sdk.HttpClient;
import com.unionpay.acp.sdk.SDKConfig;
import com.unionpay.acp.sdk.SDKUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import payment.alipay.AlipayConfig;
import payment.alipay.AlipaySubmit;
import payment.alipay.RSA;
import payment.alipay.UtilDate;

import javax.annotation.Resource;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;

public class GetPaymentUrlForH5 implements Action {
    private static final Logger log = LoggerFactory.getLogger(GetPaymentUrlForH5.class);

    @Resource
    private OrderManager orderManager;

    public GetPaymentUrlForH5() {
        SDKConfig.getConfig().loadPropertiesFromSrc();
    }

    public TradeResponse<PaymentUrlDTO> execute(RequestContext context)
            throws TradeException {
        Request request = context.getRequest();
        TradeResponse response = null;

        if (request.getParam("orderId") == null) {
            log.error("orderId is null");
            response = ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING, "orderId is null");
        }

        if (request.getParam("userId") == null) {
            log.error("userId is null");
            response = ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING, "userId is null");
        }

        int payType = 3;
        if (request.getParam("payType") != null) {
            payType = Integer.parseInt((String) request.getParam("payType"));
        }

        Long orderId = (Long) request.getParam("orderId");
        Long userId = (Long) request.getParam("userId");

        OrderDO orderDO = this.orderManager.getOrder(orderId.longValue(), userId);
        response = new TradeResponse(genPayInfo(orderDO, payType));
        return response;
    }

    public String getName() {
        return ActionEnum.GET_PAYMENT_URL.getActionName();
    }

    private PaymentUrlDTO genPayInfo(OrderDO orderDO, int payType) {
        PaymentUrlDTO paymentUrlDTO = new PaymentUrlDTO();

        int paymentId = 2;
        String tradeNo = "123";
        long totalFee = 1L;

        if (orderDO.getPaymentId() != null) {
            paymentId = orderDO.getPaymentId().intValue();
        }

        if (orderDO.getOrderSn() != null) {
            tradeNo = orderDO.getOrderSn();
        }

        if (orderDO.getTotalAmount() != null) {
            totalFee = orderDO.getTotalAmount().longValue();
        }

        String payUrl = null;
        paymentId = payType;
        try {
            if (paymentId == 1) {//支付宝native支付
                payUrl = "https://www.alipay.com/cooperate/gateway.do";
                Map params = getAlipayParam(orderDO);
                paymentUrlDTO.setRequestMethod(1);
                paymentUrlDTO.setParams(params);
                paymentUrlDTO.setPayType(1);
            } else if (paymentId == 2) {//微信native支付
                String appId = "wx78ebcaf0d747991e";
                String appKey = "a3icaMHFXZ8HfIWVk28UD8kkkQQk3ssbGvYZQjJkDJIKkVNlNurGNScyJ0hjPjrtuNTcpJKL4qBVHpXT1qcpzRacQ2stts5Fcjv4Yn9gIshyxLcaxsxEhnMQJJI2j0sX";
                String partnerId = "1236287801";
                String partnerKey = "48d15a39462fbe06f6391328ff685954";
                String appSecret = "a7bd405156459a78433afe60e523721a";
                String nonceStr = "5K8264ILTKCH16CQ2502SI8ZNMTM67VS";
                String outTradeNo = orderDO.getOrderSn();
                String orderUid = ""+orderDO.getSellerId()+"_"+orderDO.getUserId()+"_"+orderDO.getId();
                String attachData = orderUid;
                Map paramMap = getWxPayParam(appId, nonceStr, partnerId, partnerKey, outTradeNo, attachData);
                paymentUrlDTO.setRequestMethod(2);
                paymentUrlDTO.setPayType(2);
                paymentUrlDTO.setParams(paramMap);
            } else if (paymentId == 3) {//银联native支付
                payUrl = "http://payment-test.chinapay.com/pay/TransGet";
                Map paramMap = getUnionPayParam();
                paymentUrlDTO.setRequestMethod(2);
                paymentUrlDTO.setPayType(3);
                paymentUrlDTO.setParams(paramMap);
            } else if(paymentId == 5){//支付宝H5快捷支付

            } else if(paymentId == 6){//微信H5快捷支付
                String appId = "wx78ebcaf0d747991e";
                String appKey = "a3icaMHFXZ8HfIWVk28UD8kkkQQk3ssbGvYZQjJkDJIKkVNlNurGNScyJ0hjPjrtuNTcpJKL4qBVHpXT1qcpzRacQ2stts5Fcjv4Yn9gIshyxLcaxsxEhnMQJJI2j0sX";
                String partnerId = "1236287801";
                String partnerKey = "48d15a39462fbe06f6391328ff685954";
                String appSecret = "a7bd405156459a78433afe60e523721a";
                String nonceStr = "5K8264ILTKCH16CQ2502SI8ZNMTM67VS";
                String outTradeNo = orderDO.getOrderSn();
                String orderUid = ""+orderDO.getSellerId()+"_"+orderDO.getUserId()+"_"+orderDO.getId();
                String attachData = orderUid;
                Map paramMap = getWxPayParamForH5(appId, nonceStr, partnerId, partnerKey, outTradeNo, attachData);
                paymentUrlDTO.setRequestMethod(2);
                paymentUrlDTO.setPayType(6);
                paymentUrlDTO.setParams(paramMap);
            } else if(paymentId == 7){//银联H5快捷支付

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        paymentUrlDTO.setPayUrl(payUrl);

        return paymentUrlDTO;
    }

    private Map<String,String> getAlipayParamForH5(){
        //支付宝网关地址
        String ALIPAY_GATEWAY_NEW = "http://wappaygw.alipay.com/service/rest.htm?";

        //调用授权接口alipay.wap.trade.create.direct获取授权码token
        //返回格式
        String format = "xml";
        //必填，不需要修改
        //返回格式
        String v = "2.0";
        //请求号，必填，须保证每次请求都是唯一
        String req_id = UtilDate.getOrderNum();

        //req_data详细信息
        //服务器异步通知页面路径；需http://格式的完整路径，不能加?id=123这类自定义参数
        String notify_url = "http://127.0.0.1:8080/WS_WAP_PAYWAP-JAVA-UTF-8/notify_url.jsp";

        //页面跳转同步通知页面路径；需http://格式的完整路径，不能加?id=123这类自定义参数，不能写成http://localhost/
        String call_back_url = "http://127.0.0.1:8080/WS_WAP_PAYWAP-JAVA-UTF-8/call_back_url.jsp";

        //操作中断返回地址；用户付款中途退出返回商户的地址。需http://格式的完整路径，不允许加?id=123这类自定义参数
        String merchant_url = "http://127.0.0.1:8080/WS_WAP_PAYWAP-JAVA-UTF-8/xxxxxx.jsp";

        //商户订单号；商户网站订单系统中唯一订单号，必填
        String out_trade_no = ""+System.currentTimeMillis();//TODO 改为正确值

        //订单名称
        String subject = "order test";//TODO
        //必填

        //付款金额，必填
        String total_fee = "1000";//TODO

        //TODO 重构下面XML拼装代码
        //请求业务参数详细，必填
        String req_dataToken = "<direct_trade_create_req><notify_url>" + notify_url + "</notify_url><call_back_url>" + call_back_url + "</call_back_url><seller_account_name>" + AlipayConfig.seller_email + "</seller_account_name><out_trade_no>" + out_trade_no + "</out_trade_no><subject>" + subject + "</subject><total_fee>" + total_fee + "</total_fee><merchant_url>" + merchant_url + "</merchant_url></direct_trade_create_req>";


        //把请求参数打包成数组
        Map<String, String> sParaTempToken = new HashMap<String, String>();
        sParaTempToken.put("service", "alipay.wap.trade.create.direct");
        sParaTempToken.put("partner", AlipayConfig.partner);
        sParaTempToken.put("_input_charset", AlipayConfig.input_charset);
        sParaTempToken.put("sec_id", AlipayConfig.sign_type);
        sParaTempToken.put("format", format);
        sParaTempToken.put("v", v);
        sParaTempToken.put("req_id", req_id);
        sParaTempToken.put("req_data", req_dataToken);


        try{
            String sHtmlTextToken = AlipaySubmit.buildRequest(ALIPAY_GATEWAY_NEW, "", "", sParaTempToken);;
//            String sHtmlTextToken = HttpUtil.post(ALIPAY_GATEWAY_NEW+"_input_charset="+AlipayConfig.input_charset,sParaTempToken);
            //URLDECODE返回的信息
//            sHtmlTextToken = URLDecoder.decode(sHtmlTextToken, AlipayConfig.input_charset);
            System.out.println("sHtmlTextToken:"+sHtmlTextToken);

            //获取token
            String requestToken = AlipaySubmit.getRequestToken(sHtmlTextToken);
//            String requestToken = this.getAlipayRequestToken(sHtmlTextToken);
            System.out.println("requestToken:"+requestToken);


            //业务详细
            String req_data = "<auth_and_execute_req><request_token>" + requestToken + "</request_token></auth_and_execute_req>";
            //必填

            //把请求参数打包成数组
            Map<String, String> sParaTemp = new HashMap<String, String>();
            sParaTemp.put("service", "alipay.wap.auth.authAndExecute");
            sParaTemp.put("partner", AlipayConfig.partner);
            sParaTemp.put("_input_charset", AlipayConfig.input_charset);
            sParaTemp.put("sec_id", AlipayConfig.sign_type);
            sParaTemp.put("format", format);
            sParaTemp.put("v", v);
            sParaTemp.put("req_data", req_data);

            //建立请求
            String sHtmlText = HttpUtil.get(ALIPAY_GATEWAY_NEW, sParaTemp);
//            System.out.println("sHtmlText:"+sHtmlText);
        }catch(Exception e){
            log.error("", e);
            //TODO error handle
        }

        return null;
    }

    private String getAlipayRequestToken(String text) throws Exception {
        String request_token = "";
        //以“&”字符切割字符串
        String[] strSplitText = text.split("&");
        //把切割后的字符串数组变成变量与数值组合的字典数组
        Map<String, String> paraText = new HashMap<String, String>();
        for (int i = 0; i < strSplitText.length; i++) {

            //获得第一个=字符的位置
            int nPos = strSplitText[i].indexOf("=");
            //获得字符串长度
            int nLen = strSplitText[i].length();
            //获得变量名
            String strKey = strSplitText[i].substring(0, nPos);
            //获得数值
            String strValue = strSplitText[i].substring(nPos+1,nLen);
            //放入MAP类中
            paraText.put(strKey, strValue);
        }

        if (paraText.get("res_data") != null) {
            String res_data = paraText.get("res_data");
            //解析加密部分字符串（RSA与MD5区别仅此一句）
            if(AlipayConfig.sign_type.equals("0001")) {
                res_data = RSA.decrypt(res_data, AlipayConfig.private_key, AlipayConfig.input_charset);
            }

            //token从res_data中解析出来（也就是说res_data中已经包含token的内容）
            Document document = DocumentHelper.parseText(res_data);
            request_token = document.selectSingleNode( "//direct_trade_create_res/request_token" ).getText();
        }
        return request_token;
    }

    private Map<String, String> getAlipayParam(OrderDO orderDO) {
        Map params = new HashMap();
        try {
            Map<String, String> paramMap = new TreeMap<String, String>();
            paramMap.put("service", "mobile.securitypay.pay");
            paramMap.put("partner", "2088011641764235");
            paramMap.put("notify_url", "http://218.244.147.209:8080/trade/order/payment/callback/alipay_notify");
            paramMap.put("return_url", "m.alipay.com");
            paramMap.put("_input_charset", "utf-8");
            paramMap.put("subject", "测试的商品");
            paramMap.put("body", "该测试商品的详细描述");
            paramMap.put("total_fee", "0.01");
            String orderUid = ""+orderDO.getSellerId()+"_"+orderDO.getUserId()+"_"+orderDO.getId();
            paramMap.put("out_trade_no", orderUid);
            paramMap.put("payment_type", "1");
            paramMap.put("seller_id", "account@axetime.com");
            paramMap.put("it_b_pay", "30m");

            StringBuilder paramSb = new StringBuilder();
            StringBuilder signSb = new StringBuilder();
            for (Entry<String, String> entry : paramMap.entrySet()) {
                paramSb.append((String) entry.getKey());
                paramSb.append("=");
                paramSb.append("\"");
                paramSb.append((String) entry.getValue());
                paramSb.append("\"");
                paramSb.append("&");
            }


            String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAN+C7OBa8ONp6Cu0+c1H5R2vLX3/X8Bg4LaKHRmaK6qHuHmq2CRCyZ9i0jNislJaT9O8eh47sQDgNpuycgK9WaONiPDw/ZZ6ZqXTfnnDMF1UToE4c45y7b+Nr4WKsswHikJwb3RGyJ10iHp0nDjZU/n6+LJ57jfhyVB2WXRyYvDNAgMBAAECgYEAvvOl188Z+c/jMGP+/mgrz/53SSvB7CNYF9tLHTJfl+M3sYpJ+kCs7GSK3Ke3XTAW/vgJBIdOo0bqoH4FdV27k6vlRTDqV1nKno1O/B+jpZFXMEciGOY0NMDi3PocLnr9xosWyYCTI3e11CWG1tHoabkirwHDkSYoogifwl/TBekCQQD8lE6ePet7XiFhiR18VLeKYe/SfoGkUjiu/oq4s+/2mNF3mr9kUw1KF9u0TZpB+gOeba+Fxp+XPkTrYZULGBYfAkEA4onW+fLd4nv7t5GDrXN6Kj+qaocbDYQ9lxB1QF0/EXaT+3ex/RzMIafAvoT5BtzivU7iyAemJXaWhjIeVngjkwJAZ/Mxl/ar344R9u5TcEP9dbpw1RSzjkk4guzHctS7QaYLL0pE0qlLot7G0SHPBrA6pQ1HW6svKzO5cZYrnPFAyQJAUXrqi7RDAnc9rmT0S6rBQfjsPqyhmlb6IB+XTLM9P/a6ezQuHLAC7Af+V4hUWZeRJi39e5zksYzZzyKvrsI/aQJAbCXmK/0Qd8pHV4D7tZCFW4QLPmd6AO61LHP8e0qFmupKYc4hHkrJ2Ewk/1atoPnEzScg6tjoBChnqhlwYK4WNQ==";

            paramSb.deleteCharAt(paramSb.length() - 1);
            System.out.println(new StringBuilder().append("paramSb:").append(paramSb.toString()).toString());
            String signStr = SignUtils.sign(paramSb.toString(), privateKey);
            signStr = URLEncoder.encode(signStr, "utf-8");
            String paramStr = new StringBuilder().append(paramSb.toString()).append("&sign_type=\"RSA\"&sign=\"").append(signStr).append("\"").toString();
            params.put("param", paramStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return params;
    }



    private Map<String, String> getUnionPayParam() {
        String signType = "MD5";

        if (!"SHA1withRSA".equalsIgnoreCase(signType)) {
            signType = "MD5";
        }

        SDKConfig.getConfig().loadPropertiesFromSrc();
        Map data = new HashMap();
        data.put("version", "5.0.0");
        data.put("encoding", "UTF-8");
        data.put("signMethod", "01");
        data.put("txnType", "01");
        data.put("txnSubType", "01");
        data.put("bizType", "000201");
        data.put("channelType", "08");
        data.put("frontUrl", "");
        data.put("backUrl", "http://218.244.147.209:8080/trade/order/payment/callback/unionpay_notify");
        data.put("accessType", "0");
        data.put("merId", "777290058113196");
        data.put("orderId", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
        data.put("txnTime", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
        data.put("txnAmt", "1");
        data.put("currencyCode", "156");
        data.put("reqReserved", "透传信息");
        data.put("orderDesc", "订单描述");
        data = signData(data);

        String requestAppUrl = SDKConfig.getConfig().getAppRequestUrl();

        Map resmap = submitUrl(data, requestAppUrl);
        Map paramMap = new HashMap();
        paramMap.put("tn", resmap.get("tn"));
        return paramMap;
    }

    private Map<String, String> signData(Map<String, ?> contentData) {
        Entry obj = null;
        Map submitFromData = new HashMap();
        for (Iterator it = contentData.entrySet().iterator(); it.hasNext(); ) {
            obj = (Entry) it.next();
            String value = (String) obj.getValue();
            if (StringUtils.isNotBlank(value)) {
                submitFromData.put(obj.getKey(), value.trim());
                System.out.println(new StringBuilder().append((String) obj.getKey()).append("-->").append(String.valueOf(value)).toString());
            }

        }

        SDKUtil.sign(submitFromData, "UTF-8");

        return submitFromData;
    }

    public Map<String, String> submitUrl(Map<String, String> submitFromData, String requestUrl) {
        String resultString = "";
        System.out.println(new StringBuilder().append("requestUrl====").append(requestUrl).toString());
        System.out.println(new StringBuilder().append("submitFromData====").append(submitFromData.toString()).toString());

        HttpClient hc = new HttpClient(requestUrl, 30000, 30000);
        try {
            int status = hc.send(submitFromData, "UTF-8");
            if (200 == status)
                resultString = hc.getResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map resData = new HashMap();

        if ((null != resultString) && (!"".equals(resultString))) {
            resData = SDKUtil.convertResultStringToMap(resultString);
            if (SDKUtil.validate(resData, "UTF-8"))
                System.out.println("验证签名成功");
            else {
                System.out.println("验证签名失败");
            }

            System.out.println(new StringBuilder().append("打印返回报文：").append(resultString).toString());
        }
        return resData;
    }

    private Map<String, String> getWxPayParamForH5(String appId, String noncestr, String partnerid, String partnerKey, String outTradeNo, String attach) {
        String timestamp = new StringBuilder().append("").append(System.currentTimeMillis() / 1000L).toString();

        Map resMap = getWxPrepayId(appId, partnerid, partnerKey, noncestr, outTradeNo, attach);
        Map paramMap = new TreeMap();
        paramMap.put("appId", appId);
        paramMap.put("package", "prepay_id="+resMap.get("prepay_id"));
        paramMap.put("nonceStr", resMap.get("nonce_str"));
        paramMap.put("timeStamp", timestamp);
        paramMap.put("signType", "MD5");
        paramMap.put("paySign", getWxParamSign(paramMap, partnerKey));

        return paramMap;
    }

    private Map<String, String> getWxPayParam(String appId, String noncestr, String partnerid, String partnerKey, String outTradeNo, String attach) {
        String timestamp = new StringBuilder().append("").append(System.currentTimeMillis() / 1000L).toString();

        Map resMap = getWxPrepayId(appId, partnerid, partnerKey, noncestr, outTradeNo, attach);
        Map paramMap = new TreeMap();
        paramMap.put("appid", appId);
        paramMap.put("prepayid", resMap.get("prepay_id"));
        paramMap.put("partnerid", partnerid);
        paramMap.put("package", "Sign=WXPay");
        paramMap.put("noncestr", resMap.get("nonce_str"));
        paramMap.put("timestamp", timestamp);

        paramMap.put("sign", getWxParamSign(paramMap, partnerKey));

        return paramMap;
    }

    private String getWxParamSign(Map<String, String> paramMap, String partnerKey) {
        StringBuilder signSb = new StringBuilder();
        for (Entry entry : paramMap.entrySet()) {
            signSb.append((String) entry.getKey()).append("=").append((String) entry.getValue()).append("&");
        }

        String toSignStr = new StringBuilder().append(signSb.toString()).append("key=").append(partnerKey).toString();
        System.out.println(new StringBuilder().append("signSb:").append(toSignStr).toString());

        String sign = DigestUtils.md5Hex(toSignStr).toUpperCase();
        return sign;
    }

    private Map<String, String> getWxPrepayId(String appId, String partnerId, String partnerKey, String noncestr, String outTradeNo, String attach) {
        String reqUrl = "https://api.mch.weixin.qq.com/pay/unifiedorder";
        try {
            long totalFee = 1L;
            Map paramMap = new TreeMap();
            paramMap.put("appid", appId);
            paramMap.put("mch_id", partnerId);
            paramMap.put("body", "test");
            paramMap.put("nonce_str", noncestr);
            paramMap.put("out_trade_no", outTradeNo);

            paramMap.put("total_fee", "1");
            paramMap.put("spbill_create_ip", "183.157.67.92");
            paramMap.put("notify_url", "http://218.244.147.209:8080/trade/order/payment/callback/wechat_notify");
            paramMap.put("trade_type", "APP");
            paramMap.put("attach",attach);
            paramMap.put("sign", getWxParamSign(paramMap, partnerKey));

            System.out.println(new StringBuilder().append("prepayIdReq:").append(JsonUtil.toJson(paramMap)).toString());

            String xmlData = XmlUtil.map2XmlStr(paramMap);

            String response = HttpUtil.postXml(reqUrl, xmlData);
            System.out.println(new StringBuilder().append("response=").append(response).toString());
            return XmlUtil.xmlStr2Map(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
        GetPaymentUrlForH5 getPaymentUrl = new GetPaymentUrlForH5();

//        System.out.println(new StringBuilder().append("length=").append("MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAN+C7OBa8ONp6Cu0+c1H5R2vLX3/X8Bg4LaKHRmaK6qHuHmq2CRCyZ9i0jNislJaT9O8eh47sQDgNpuycgK9WaONiPDw/ZZ6ZqXTfnnDMF1UToE4c45y7b+Nr4WKsswHikJwb3RGyJ10iHp0nDjZU/n6+LJ57jfhyVB2WXRyYvDNAgMBAAECgYEAvvOl188Z+c/jMGP+/mgrz/53SSvB7CNYF9tLHTJfl+M3sYpJ+kCs7GSK3Ke3XTAW/vgJBIdOo0bqoH4FdV27k6vlRTDqV1nKno1O/B+jpZFXMEciGOY0NMDi3PocLnr9xosWyYCTI3e11CWG1tHoabkirwHDkSYoogifwl/TBekCQQD8lE6ePet7XiFhiR18VLeKYe/SfoGkUjiu/oq4s+/2mNF3mr9kUw1KF9u0TZpB+gOeba+Fxp+XPkTrYZULGBYfAkEA4onW+fLd4nv7t5GDrXN6Kj+qaocbDYQ9lxB1QF0/EXaT+3ex/RzMIafAvoT5BtzivU7iyAemJXaWhjIeVngjkwJAZ/Mxl/ar344R9u5TcEP9dbpw1RSzjkk4guzHctS7QaYLL0pE0qlLot7G0SHPBrA6pQ1HW6svKzO5cZYrnPFAyQJAUXrqi7RDAnc9rmT0S6rBQfjsPqyhmlb6IB+XTLM9P/a6ezQuHLAC7Af+V4hUWZeRJi39e5zksYzZzyKvrsI/aQJAbCXmK/0Qd8pHV4D7tZCFW4QLPmd6AO61LHP8e0qFmupKYc4hHkrJ2Ewk/1atoPnEzScg6tjoBChnqhlwYK4WNQ==".length()).toString());
//        String privateKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC3szlIbqdNr9PIyrqWvSyplbKA\n/RtOD9mDQaU+b8m9jGESGuKWMFnh3C6nhl7XXueB5b09bhPRNvY1mEddiE68aXBm\nfBFidfJgGqgHWdKLFYMbF9XQWFsaUZkpjAL24v3+5CaLsyM7IoAipQowHe3pxef/\nlbv4Ouj9p1qGUd5GPQIDAQAB";
//
//        System.out.println(new StringBuilder().append("privateKey:").append(new String(privateKey.getBytes("us-ascii"), "utf-8")).toString());
//        System.out.println(new StringBuilder().append("data:").append(JsonUtil.toJson(getPaymentUrl.getAlipayParam())).toString());
        getPaymentUrl.getAlipayParamForH5();
    }
}

/* Location:           /work/tmp/tradecenter/WEB-INF/classes/
 * Qualified Name:     com.mockuai.tradecenter.core.service.action.payment.GetPaymentUrl
 * JD-Core Version:    0.6.2
 */