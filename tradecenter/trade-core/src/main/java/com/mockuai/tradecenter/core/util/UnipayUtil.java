package com.mockuai.tradecenter.core.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.unionpay.acp.sdk.CertUtil;
import com.unionpay.acp.sdk.HttpClient;
import com.unionpay.acp.sdk.LogUtil;
import com.unionpay.acp.sdk.SDKUtil;
import com.unionpay.acp.sdk.SecureUtil;

public class UnipayUtil {
	
	private static final Logger log = LoggerFactory.getLogger(UnipayUtil.class);
	
	public static Map<String, String> submitUrl(
			Map<String, String> submitFromData,String requestUrl) {
		String resultString = "";
		System.out.println("requestUrl====" + requestUrl);
		System.out.println("submitFromData====" + submitFromData.toString());
		/**
		 * 发送
		 */
		HttpClient hc = new HttpClient(requestUrl, 30000, 30000);
		try {
			int status = hc.send(submitFromData, "utf-8");
			if (200 == status) {
				resultString = hc.getResult();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Map<String, String> resData = new HashMap<String, String>();
		System.out.println("resultString====" + resultString.toString());
		/**
		 * 验证签名
		 */
		if (null != resultString && !"".equals(resultString)) {
			// 将返回结果转换为map
			resData = SDKUtil.convertResultStringToMap(resultString);
//			if (SDKUtil.validate(resData, "utf-8")) {
//				System.out.println("验证签名成功");
//			} else {
//				System.out.println("验证签名失败");
//			}
			// 打印返回报文
			System.out.println("打印返回报文：" + resultString);
		}
		return resData;
	}
	
	public static Map<String, String> signData(Map<String, ?> contentData,String certPath,String pwd,String certType) {
		Entry obj = null;
		Map submitFromData = new HashMap();
		for (Iterator it = contentData.entrySet().iterator(); it.hasNext();) {
			obj = (Entry) it.next();
			String value = (String) obj.getValue();
			if (StringUtils.isNotBlank(value)) {
				submitFromData.put(obj.getKey(), value.trim());
				System.out.println(new StringBuilder().append((String) obj.getKey()).append("-->")
						.append(String.valueOf(value)).toString());
			}
		}

//		SDKUtil.sign(submitFromData, "UTF-8");
		sign(submitFromData,"UTF-8",certPath,pwd,certType);
		return submitFromData;
	}
	
	public static boolean sign(Map<String, String> data, String encoding,String certPath,String pwd,String certType)
	  {
	    log.info("签名处理开始.");

	    data.put("certId", CertUtil.getCertIdByCertPath(certPath,pwd,certType));

	    String stringData = SDKUtil.coverMap2String(data);
	    LogUtil.writeLog(new StringBuilder().append("报文签名之前的字符串(不含signature域)=[").append(stringData).append("]").toString());

	    byte[] byteSign = null;
	    String stringSign = null;
	    try
	    {
	      byte[] signDigest = SecureUtil.sha1X16(stringData, encoding);
	      LogUtil.writeLog(new StringBuilder().append("SHA1->16进制转换后的摘要=[").append(new String(signDigest)).append("]").toString());

	      byteSign = SecureUtil.base64Encode(SecureUtil.signBySoft(CertUtil.getSignCertPrivateKey(), signDigest));

	      stringSign = new String(byteSign);
	      LogUtil.writeLog(new StringBuilder().append("报文签名之后的字符串=[").append(stringSign).append("]").toString());

	      data.put("signature", stringSign);
	      LogUtil.writeLog("签名处理结束.");
	      return true;
	    } catch (Exception e) {
	      LogUtil.writeErrorLog("签名异常", e);
	    }return false;
	  }

}
