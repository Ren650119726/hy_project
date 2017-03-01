package com.mockuai.rainbowcenter.core.util;

import java.net.URLEncoder;
import com.alibaba.fastjson.JSONObject;
import com.mockuai.rainbowcenter.common.constant.ResponseCode;
import com.mockuai.rainbowcenter.core.exception.RainbowException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by lizg on 2016/6/2.
 */
public class GyERPUtils {

    private static final Logger logger = LoggerFactory.getLogger(GyERPUtils.class);

    /**
     * 发送post请求到管易erp
     * @param url 请求地址
     * @param data 签名后的json格式入参
     * @throws com.mockuai.rainbowcenter.core.exception.RainbowException
     */
    public static String sendPost(String url, String data) throws RainbowException {

        if (null == url) {
            logger.error("gyerp url is null");
            throw new RainbowException(ResponseCode.PARAM_E_PARAM_NULL);
        }

        if (null == data) {
            logger.error("data is null");
            throw new RainbowException(ResponseCode.PARAM_E_PARAM_NULL);
        }
        logger.info("The GYERP  url:{}",url);
        logger.info("The GYERP  request:{}", data);
        String response =null;
        try {
            CloseableHttpClient httpclient = null;
            CloseableHttpResponse httpresponse = null;
            try {
                httpclient = HttpClients.createDefault();
                HttpPost httppost = new HttpPost(url);

                StringEntity stringentity = new StringEntity(URLEncoder.encode(
                        data, "UTF-8"),
                        ContentType.create("text/json", "UTF-8"));
                httppost.setEntity(stringentity);
                httpresponse = httpclient.execute(httppost);
                 response = EntityUtils
                        .toString(httpresponse.getEntity());
                logger.info("response{}" + response);
                return response;
            } finally {
                if (httpclient != null) {
                    httpclient.close();
                }
                if (httpresponse != null) {
                    httpresponse.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }


    public static String sendPost(String url, JSONObject data) throws RainbowException {

        if (null == url) {
            logger.error("gyerp url is null");
            throw new RainbowException(ResponseCode.PARAM_E_PARAM_NULL);
        }

        if (null == data) {
            logger.error("data is null");
            throw new RainbowException(ResponseCode.PARAM_E_PARAM_NULL);
        }
        logger.info("The deliverys  url:{}",url);
        logger.info("The deliverys  request:{}", data.toString());
        String sign = GyERPSignUtil.sign(data.toString(),PropertyConfig.getGyerpSecret());
        logger.info("gy.erp.trade.deliverys.get sign: {}",sign);
         data.put("sign",sign);
        String dataIn = data.toString();
        logger.info("gy.erp.trade.deliverys.get dataIn: {}",dataIn);
        String response =null;
        try {
            CloseableHttpClient httpclient = null;
            CloseableHttpResponse httpresponse = null;
            try {
                httpclient = HttpClients.createDefault();
                HttpPost httppost = new HttpPost(url);

                StringEntity stringentity = new StringEntity(URLEncoder.encode(
                        dataIn, "UTF-8"),
                        ContentType.create("text/json", "UTF-8"));
                httppost.setEntity(stringentity);
                httpresponse = httpclient.execute(httppost);
                response = EntityUtils
                        .toString(httpresponse.getEntity());
                logger.info("response{}" + response);
                return response;
            } finally {
                if (httpclient != null) {
                    httpclient.close();
                }
                if (httpresponse != null) {
                    httpresponse.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }


    public static String sendPostStock(String url, JSONObject data) throws RainbowException {

        if (null == url) {
            logger.error("gyerp url is null");
            throw new RainbowException(ResponseCode.PARAM_E_PARAM_NULL);
        }

        if (null == data) {
            logger.error("data is null");
            throw new RainbowException(ResponseCode.PARAM_E_PARAM_NULL);
        }
        logger.info("The deliverys  url:{}",url);
        if(data.toString().contains("sign")) {
           data.remove("sign");
        }
        logger.info("The deliverys  request:{}", data.toString());
        String sign = GyERPSignUtil.sign(data.toString(),PropertyConfig.getGyerpSecret());
        logger.info("gy.erp.trade.deliverys.get sign: {}",sign);
        data.put("sign",sign);
        String dataIn = data.toString();
        logger.info("gy.erp.trade.deliverys.get dataIn: {2}",dataIn);
        String response =null;
        try {
            CloseableHttpClient httpclient = null;
            CloseableHttpResponse httpresponse = null;
            try {
                httpclient = HttpClients.createDefault();
                HttpPost httppost = new HttpPost(url);

                StringEntity stringentity = new StringEntity(URLEncoder.encode(
                        dataIn, "UTF-8"),
                        ContentType.create("text/json", "UTF-8"));
                httppost.setEntity(stringentity);
                httpresponse = httpclient.execute(httppost);
                response = EntityUtils
                        .toString(httpresponse.getEntity());
                logger.info("response{}" + response);
                return response;
            } finally {
                if (httpclient != null) {
                    httpclient.close();
                }
                if (httpresponse != null) {
                    httpresponse.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
}
