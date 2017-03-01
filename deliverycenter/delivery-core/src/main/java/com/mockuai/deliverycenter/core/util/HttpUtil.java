package com.mockuai.deliverycenter.core.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;

import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zengzhangqiang on 4/29/15.
 */
public class HttpUtil {
    private static final Logger log = LoggerFactory.getLogger(HttpUtil.class);
    private static HttpClient httpClient;

    private static HttpParams httpParams;
    private static ClientConnectionManager connectionManager;

    /**
     * 最大连接数
     */
    public final static int MAX_TOTAL_CONNECTIONS = 800;
    /**
     * 获取连接的最大等待时间
     */
    public final static int WAIT_TIMEOUT = 60000;
    /**
     * 每个路由最大连接数
     */
    public final static int MAX_ROUTE_CONNECTIONS = 400;
    /**
     * 连接超时时间
     */
    public final static int CONNECT_TIMEOUT = 30000;
    /**
     * 读取超时时间
     */
    public final static int READ_TIMEOUT = 30000;

    static {
//        httpParams = new BasicHttpParams();
//        // 设置最大连接数
//        ConnManagerParams.setMaxTotalConnections(httpParams, MAX_TOTAL_CONNECTIONS);
//        // 设置获取连接的最大等待时间
//        ConnManagerParams.setTimeout(httpParams, WAIT_TIMEOUT);
//        // 设置每个路由最大连接数
//        ConnPerRouteBean connPerRoute = new ConnPerRouteBean(MAX_ROUTE_CONNECTIONS);
//        ConnManagerParams.setMaxConnectionsPerRoute(httpParams, connPerRoute);
//        // 设置连接超时时间
//        HttpConnectionParams.setConnectionTimeout(httpParams, CONNECT_TIMEOUT);
//        // 设置读取超时时间
//        HttpConnectionParams.setSoTimeout(httpParams, READ_TIMEOUT);
//
//        SchemeRegistry registry = new SchemeRegistry();
//        registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
//        registry.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));

//        connectionManager = new ThreadSafeClientConnManager(httpParams, registry);
        //TODO httpClient调优
        //TODO httpClient设置以上初始化参数后，https://api.weixin.qq.com/sns/userinfo 访问不了，该问题待排查
        httpClient = new DefaultHttpClient();
    }

    public static String get(String url, Map<String, String> params) {
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            NameValuePair nameValuePair = new BasicNameValuePair(entry.getKey(), entry.getValue());
            pairs.add(nameValuePair);
        }

        return get(url, pairs);
    }


    /**
     * Get请求
     *
     * @param url
     * @param params
     * @return
     */
    public static String get(String url, List<NameValuePair> params) {
        String body = null;
        try {
            // Get请求
            HttpGet httpget = new HttpGet(url);
            // 设置参数
            String str = EntityUtils.toString(new UrlEncodedFormEntity(params));
            httpget.setURI(new URI(httpget.getURI().toString() + "?" + str));
            // 发送请求
            synchronized(httpClient){
            	HttpResponse httpresponse = httpClient.execute(httpget);
                // 获取返回数据
                HttpEntity entity = httpresponse.getEntity();
                body = EntityUtils.toString(entity);
                
                if (entity != null) {
                    entity.consumeContent();
                }
            }
            
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return body;
    }

    public static String post(String url, Map<String, String> params) {
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            NameValuePair nameValuePair = new BasicNameValuePair(entry.getKey(), entry.getValue());
            pairs.add(nameValuePair);
        }

        return post(url, pairs);
    }

    /**
     * // Post请求
     *
     * @param url
     * @param params
     * @return
     */
    public static String post(String url, List<NameValuePair> params) {
        String body = null;
        try {
            // Post请求
            HttpPost httppost = new HttpPost(url);
            // 设置参数
            httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
            // 发送请求
            HttpResponse httpresponse = httpClient.execute(httppost);
            // 获取返回数据
            HttpEntity entity = httpresponse.getEntity();
            body = EntityUtils.toString(entity);
            if (entity != null) {
                entity.consumeContent();
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return body;
    }

    public static String postXml(String url, String xmlData) {
        HttpPost post = new HttpPost(url);
        InputStream is = null;
        ByteArrayOutputStream baos = null;
        try {
            StringEntity entity = new StringEntity(xmlData);
            post.setEntity(entity);
            post.setHeader("Content-Type", "text/xml;charset=UTF-8");

            HttpResponse response = httpClient.execute(post);
            is = response.getEntity().getContent();
            baos = new ByteArrayOutputStream();
            byte[] data = new byte[1024];
            int count = is.read(data);
            while (count != -1) {
                baos.write(data, 0, count);
                count = is.read(data);
            }
            return baos.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null)
                try {
                    is.close();
                } catch (Exception ignore) {
                }
            if (baos != null)
                try {
                    baos.close();
                } catch (Exception ignore) {
                }
        }
        return null;
    }


    public static void main(String[] args){


        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("id", "cdc1337ddc107607"));
        params.add(new BasicNameValuePair("com", "shunfeng"));
		//nu
        params.add(new BasicNameValuePair("nu", "shu123456nfeng"));
        String url = "http://api.kuaidi100.com/api";
        String response = HttpUtil.get(url, params);
        System.out.println("response:"+response);

       

        
    }
}
