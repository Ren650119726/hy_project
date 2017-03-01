package com.mockuai.tradecenter.core.service.action.message;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.mockuai.appcenter.common.domain.BizPropertyDTO;
import com.mockuai.tradecenter.common.api.Request;
import com.mockuai.tradecenter.common.api.TradeResponse;
import com.mockuai.tradecenter.common.constant.ActionEnum;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.common.domain.message.WxTemplateDTO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.CacheManager;
import com.mockuai.tradecenter.core.manager.OrderManager;
import com.mockuai.tradecenter.core.manager.StoreManager;
import com.mockuai.tradecenter.core.service.RequestContext;
import com.mockuai.tradecenter.core.service.ResponseUtils;
import com.mockuai.tradecenter.core.service.action.Action;
import com.mockuai.tradecenter.core.util.HttpUtil;
import com.mockuai.tradecenter.core.util.JsonUtil;
import com.mockuai.tradecenter.core.util.TradeUtil;
import com.unionpay.acp.sdk.BaseHttpSSLSocketFactory.MyX509TrustManager;

/**
 * 发送微信消息
 *
 */
public class SendWechatMessage implements Action {
	private static final Logger log = LoggerFactory.getLogger(SendWechatMessage.class);


	@Resource
	private OrderManager orderManager;

	@Resource
	private StoreManager storeManager;
	@Resource
	private CacheManager cacheManager;

	public TradeResponse<String> execute(RequestContext context) {

		Request request = context.getRequest();
		TradeResponse<String> response = null;
		String bizCode = (String) context.get("bizCode");
		if (request.getParam("wxTemplateDTO") == null) {
			log.error("wxTemplateDTO is null");
			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING, "wxTemplateDTO is null");
		}
		

		WxTemplateDTO wxTplMessageDTO = (WxTemplateDTO) request.getParam("wxTemplateDTO");
		if(wxTplMessageDTO.getTemplate_id()==null){
			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING, "template_id is null");
		}
		if(null==wxTplMessageDTO.getTouser()){
			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING, "toUser is null");
		}
		if(null==wxTplMessageDTO.getData()){
			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING, "tpl data is null");
		}
		
		try {
			Map<String, BizPropertyDTO> bizPropertyMap = (Map<String, BizPropertyDTO>) context.get("bizPropertyMap");
			if (null == bizPropertyMap) {
				throw new TradeException(bizCode + " bizPropertyMap is null");
			}
			
			String wapAppId = TradeUtil.getWxWapPayAppId(bizPropertyMap);
			String wapAppSecret = TradeUtil.getWxWapPayAppsecret(bizPropertyMap);
			
			doSendWxMessage(wapAppId,wapAppSecret,wxTplMessageDTO);
			
			String appPayAppId = TradeUtil.getWxAppPayAppId(bizPropertyMap);
			String appPayAppSecret = TradeUtil.getWxAppPayAppsecret(bizPropertyMap);
			
			doSendWxMessage(appPayAppId,appPayAppSecret,wxTplMessageDTO);
			
		} catch (TradeException e) {
			log.error("db error:", e);
			return ResponseUtils.getFailResponse(ResponseCode.SYS_E_DATABASE_ERROR,e.getMessage());
		}

		
		response = ResponseUtils.getSuccessResponse("true");
		
		return response;
		
	}
	
	private void doSendWxMessage(String appId,String appSecret,WxTemplateDTO wxTplMessageDTO) {
		try{
			String accessToken = getWxAccessToken(appId,appSecret);
			String messageUrl = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+accessToken;
			String wxResponse = httpRequest(messageUrl,"POST",JsonUtil.toJson(wxTplMessageDTO));
			Map<String, Object> respMap = JsonUtil.parseJson(wxResponse, Map.class);
			log.info("wxResponse"+respMap);
			Double errcode = (Double) respMap.get("errcode");
			String errmsg = (String) respMap.get("errmsg");
			if(errcode.doubleValue()!=0){
				throw new TradeException(errmsg);
			}
			log.info("wechat send message success"+JsonUtil.toJson(wxTplMessageDTO));
		}catch(Exception e){
			log.error("send wechat message error",e);
		}
		
	}
	
	
	private String pushMessage(String accessToken,WxTemplateDTO wxTplMessageDTO){
		String messageUrl = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+accessToken;
		Map<String,String> params = new HashMap<String,String>();
		params.put("post", JsonUtil.toJson(wxTplMessageDTO));
		String response = HttpUtil.post(messageUrl, params);
		log.info("send wechat message response:"+response);
		
		return response;
	}
	
	private String getWxAccessToken(String appId, String secret)throws TradeException {
		String accessToken = null;
		Map<String, String> params = new HashMap<String, String>();
		params.put("grant_type", "client_credential");
		params.put("appid", appId);
		params.put("secret", secret);
		String response = HttpUtil.get("https://api.weixin.qq.com/cgi-bin/token", params);
		Map<String, String> respMap = JsonUtil.parseJson(response, Map.class);
		accessToken = respMap.get("access_token");
		if (StringUtils.isNotBlank(accessToken)) {
			return accessToken;
		}else{
			throw new TradeException("getWxAccessToken error");
		}
	}
	
    public static String httpRequest(String requestUrl, String requestMethod, String outputStr) {  
        JSONObject jsonObject = null;  
        StringBuffer buffer = new StringBuffer();  
        try {  
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化  
            TrustManager[] tm = { new MyX509TrustManager() };  
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");  
            sslContext.init(null, tm, new java.security.SecureRandom());  
            // 从上述SSLContext对象中得到SSLSocketFactory对象  
            SSLSocketFactory ssf = sslContext.getSocketFactory();  
  
            URL url = new URL(requestUrl);  
            HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();  
            httpUrlConn.setSSLSocketFactory(ssf);  
  
            httpUrlConn.setDoOutput(true);  
            httpUrlConn.setDoInput(true);  
            httpUrlConn.setUseCaches(false);  
            // 设置请求方式（GET/POST）  
            httpUrlConn.setRequestMethod(requestMethod);  
  
            if ("GET".equalsIgnoreCase(requestMethod))  
                httpUrlConn.connect();  
  
            // 当有数据需要提交时  
            if (null != outputStr) {  
                OutputStream outputStream = httpUrlConn.getOutputStream();  
                // 注意编码格式，防止中文乱码  
                outputStream.write(outputStr.getBytes("UTF-8"));  
                outputStream.close();  
            }  
  
            // 将返回的输入流转换成字符串  
            InputStream inputStream = httpUrlConn.getInputStream();  
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");  
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);  
  
            String str = null;  
            while ((str = bufferedReader.readLine()) != null) {  
                buffer.append(str);  
            }  
            bufferedReader.close();  
            inputStreamReader.close();  
            // 释放资源  
            inputStream.close();  
            inputStream = null;  
            httpUrlConn.disconnect();
            return buffer.toString();
        } catch (ConnectException ce) {  
            ce.printStackTrace();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return null;
    }  
	
	
	

	@Override
	public String getName() {
		return ActionEnum.SEND_WECHAT_MESSAGE.getActionName();
	}
}
