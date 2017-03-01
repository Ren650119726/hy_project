package com.mockuai.rainbowcenter.core.util;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by yeliming on 16/1/1.
 */
public class HttpUtil {
    private static final String UTF8 = "UTF-8";

    private static int connectionTimeOut = 25000;
    private static int socketTimeOut = 25000;
    private static int maxConnectionPerHost = 20;
    private static int maxTotalConnections = 20;


    private static HttpClient httpClient = null;

    private static HttpConnectionManager httpConnectionManager;


    //初始化
    static {
        httpConnectionManager = new SimpleHttpConnectionManager();
        HttpConnectionManagerParams params = httpConnectionManager.getParams();
        params.setConnectionTimeout(connectionTimeOut);
        params.setSoTimeout(socketTimeOut);
        params.setDefaultMaxConnectionsPerHost(maxConnectionPerHost);
        params.setMaxTotalConnections(maxTotalConnections);
        httpClient = new HttpClient(httpConnectionManager);
    }

    public static String doGet(String url, Map<String, String> params) {
        return doGet(url, params, UTF8);
    }

    public static String doGet(String url, Map<String, String> params, String charSet) {
        StringBuffer sb = new StringBuffer(url);
        sb.append("?");
        for (Map.Entry<String, String> param : params.entrySet()) {
            try {
                String key = param.getKey();
                String value = URLEncoder.encode(param.getValue(), charSet);
                sb.append(key).append("=").append(value).append("&");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        url = sb.toString();
        url = url.substring(0, url.length() - 1);
        System.out.println(url);

        GetMethod getMethod = new GetMethod(url);
        getMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=" + charSet);

        String response = null;
        try {
            int statusCode = httpClient.executeMethod(getMethod);
            if (HttpStatus.SC_OK == statusCode) {
                response = getMethod.getResponseBodyAsString();
            } else {
//                log.debug(getMethod.getStatusCode());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (getMethod != null) {
                getMethod.releaseConnection();
            }
        }
        return response;
    }

    public static String doPost(String url, Map<String, String> params) {
        return doPost(url, params, UTF8);
    }

    public static String doPost(String url, Map<String, String> params, String charSet) {
        PostMethod postMethod = new PostMethod(url);
        postMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=" + charSet);
        for (Map.Entry<String, String> param : params.entrySet()) {
            postMethod.addParameter(param.getKey(),param.getValue());
        }

        String response = null;
        try {
            int statusCode = httpClient.executeMethod(postMethod);
            if(statusCode == HttpStatus.SC_OK){
                response = postMethod.getResponseBodyAsString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(postMethod!=null){
                postMethod.releaseConnection();
            }
        }
        return response;
    }
}
