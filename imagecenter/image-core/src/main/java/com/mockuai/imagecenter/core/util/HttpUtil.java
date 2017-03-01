package com.mockuai.imagecenter.core.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public class HttpUtil {
	
	private static CloseableHttpClient hc = new DefaultHttpClient();

    /**
     * Get请求
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
            HttpResponse httpresponse = hc.execute(httpget);
            // 获取返回数据
            HttpEntity entity = httpresponse.getEntity();

            body = EntityUtils.toString(entity);
            if (entity != null) {
                entity.consumeContent();
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } finally {
            try {
                if (hc != null) {
                    hc.close();
                }
            } catch (IOException e) {
            }
        }
        return body;
    }

    public static byte[] downloadFile(String url) {
        CloseableHttpClient hc = HttpClientBuilder.create().build();
        byte[] body = null;
        try {
            // Get请求
            HttpGet httpget = new HttpGet(url);
            // 设置参数

            httpget.setURI(new URI(httpget.getURI().toString() ));
            // 发送请求
            HttpResponse httpresponse = hc.execute(httpget);
            // 获取返回数据
            HttpEntity entity = httpresponse.getEntity();

            body = EntityUtils.toByteArray(entity);
            if (entity != null) {
                entity.consumeContent();
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } finally {
            try {
                if (hc != null) {
                    hc.close();
                }
            } catch (IOException e) {
            }
        }
        return body;
    }


    /*public static String post(String url, Map<String,String> params){
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        for(Map.Entry<String,String> entry: params.entrySet()){
            NameValuePair nameValuePair = new BasicNameValuePair(entry.getKey(), entry.getValue());
            pairs.add(nameValuePair);
        }

        return post(url, pairs);
    }*/

    /**
     * // Post请求
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
            httppost.setEntity(new UrlEncodedFormEntity(params, "utf-8"));
            // 发送请求
            HttpResponse httpresponse = hc.execute(httppost);
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
    
    /**
     * 文件上传接口
     * @param url
     * @param params
     * @param ins
     * @return
     */
    public static String updateFile(String url, List<NameValuePair> params, InputStream ins){
    	 String body = null;
    	 /*
    	 try {
             // Post请求
        	 
             HttpPost httppost = new HttpPost(url);
             
             //MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
             MultipartEntityBuilder builder = MultipartEntityBuilder.create();
             //builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
             builder.addBinaryBody("images", ins,ContentType.DEFAULT_BINARY,"test");
             builder.addTextBody("media_auth_key", "6r4XkF6EcE");
            HttpEntity httpEntity = builder.build();
            httppost.setEntity(httpEntity);
             
             
             //MultipartEntity multiPartEntity = new MultipartEntity();
             //InputStreamRequestEntity ins = null;
             //multiPartEntity.addPart(name, contentBody);
            
//             multipartEntityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
//             multipartEntityBuilder.setCharset(h);
             
             // 设置参数
             //httppost.setEntity(new UrlEncodedFormEntity(params));
             // 发送请求
             
             HttpResponse httpresponse = hc.execute(httppost);
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
         */
       return null;
    	
    }
	
    public static void main(String args[])throws Exception{
    	FileInputStream ins = new FileInputStream("a.png"); 
    	String s = updateFile("b.taojae.com/service.php",null,ins);
//    	 List<NameValuePair> params = new ArrayList<NameValuePair>();
//
//         params.add(new BasicNameValuePair("media_auth_key", "6r4XkF6EcE"));
//    	
//    	String s= post("http://b.taojae.com/service.php",params);
    	
    	System.out.println(s);
    }
    
}	
