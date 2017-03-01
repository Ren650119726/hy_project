package com.mockuai.dts.core.api.impl;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.MultipartUpload;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.OSSObjectSummary;
import com.mockuai.dts.core.util.OSSUtil;
import com.mockuai.tradecenter.common.util.DateUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * Created by luliang on 15/7/1.
 */
public class OSSClientAPI {

    private static final Logger logger = LoggerFactory.getLogger(OSSClientAPI.class);

    private OSSClient ossClient;

    private String accessKeyId;
    private String accessKeySecret;
    private String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
    private String bucketName = "exceloss";

    public OSSClientAPI(String endpoint, String bucketName, String accessKeyId, String accessKeySecret) {
        this.endpoint = endpoint;
        this.accessKeyId = accessKeyId;
        this.accessKeySecret = accessKeySecret;
        this.bucketName = bucketName;
    }

    public OSSClientAPI(String accessKeyId, String accessKeySecret) {
        this.accessKeyId = accessKeyId;
        this.accessKeySecret = accessKeySecret;
    }

    private void init() {
        ClientConfiguration conf = new ClientConfiguration();
        // 设置HTTP最大连接数为10
        conf.setMaxConnections(10);
        // 设置TCP连接超时为5000毫秒
        conf.setConnectionTimeout(5000);
        // 设置最大的重试次数为3
        conf.setMaxErrorRetry(3);
        // 设置Socket传输数据超时的时间为2000毫秒
        conf.setSocketTimeout(2000);
        try {
            ossClient = new OSSClient(endpoint, getAccessKeyId(), getAccessKeySecret(), conf);
            if (!ossClient.doesBucketExist(bucketName)) {
                ossClient.createBucket(bucketName);
            }
        } catch (ClientException e) {
            logger.error("init oss client error, errMsg: {}", e.getMessage());
        }
    }

    public void multipartUpload(String key, String filePath) {
        try {        	
            OSSUtil.multipartUpload(this.ossClient, bucketName, key, filePath);
        } catch (IOException e) {
            logger.error("multipartUpload failed, path: " + filePath + ", dest bucketName: " + bucketName, e);
        }
    }

    public void uploadFile(String key, String filePath) {
        try {
        	logger.info(" oss multipartUpload , key: "+key+" filePath : "+filePath+" accessKeyId : "+accessKeyId+" accessKeySecret : "+accessKeySecret+" endpoint : "+endpoint+" bucketName : "+bucketName);
            OSSUtil.putObject(this.ossClient, bucketName, key, filePath);
        } catch (IOException e) {
            logger.error("multipartUpload failed, path: " + filePath + ", dest bucketName: " + bucketName, e);
        }
    }

    public OSSObject getObject(String bucketName, String key) throws IOException {
        OSSObject ossObject = OSSUtil.getObject(ossClient, bucketName, key);
        return ossObject;
    }


    public OSSClient getOssClient() {
        return ossClient;
    }

    public void setOssClient(OSSClient ossClient) {
        this.ossClient = ossClient;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }
    
    public static void downloadFile(OSSClient client, String bucketName, String Objectkey, String filename)
            throws OSSException, ClientException {
        client.getObject(new GetObjectRequest(bucketName, Objectkey),new File(filename));
    }
    
    public static void main(String args[]) {
    	try{
    		OSSClientAPI  ossClientAPI = new OSSClientAPI("http://oss-cn-hangzhou.aliyuncs.com","exceloss","scl16iPO2OUD1goj","1J9wWa1ZSVzZ6pSFZ6nTGVhT8BvjG9");
    		ossClientAPI.init();
    		String key = DateUtil.convertDateToString(new Date())+"/"+"order20160702-114206_11";
    		ossClientAPI.ossClient.deleteObject("exceloss", key);
    		
    		for (OSSObjectSummary objectSummary : OSSUtil.listObjects(ossClientAPI.getOssClient(), "exceloss").getObjectSummaries()) {
    			String keyName = objectSummary.getKey();
            	logger.info("keyname:"+keyName);
            	String keyArray[] = keyName.split("\\/");
            	if(keyArray.length==4){
            		logger.info("type:"+keyArray[0]);
            		logger.info("bizcode:"+keyArray[1]);
            		
            		logger.info("");
            	}
    		}
    		
    		
    		///Users/hzmk/Downloads/unipay/wechat/mockuai_demo/wap
//    		ossClientAPI.uploadFile("wechatpay-mockuai_demo-wap.zip",
//    				"/Users/hzmk/Downloads/unipay/wechat/mockuai_demo/wap/wechatpay-mockuai_demo-wap.zip");
//    		OSSUtil.listObjects(ossClientAPI.getOssClient(), "cert-dir");
//    		FileUtils.mkdir("/Users/hzmk/Downloads/unipay3/wechat/wap");
//    		String filename = "/Users/hzmk/Downloads/unipay3/wechat/wap/wechatpay-mockuai_demo-wap.zip";
//    		OSSObject ossObject = ossClientAPI.getObject("cert-dir", "wechatpay-mockuai_demo-wap.zip");
    		
//    		FileUtils.writeFile(ossObject.getObjectContent(), filename);
//    		downloadFile(ossClientAPI.ossClient,"cert-dir","wechatpay-mockuai_demo-wap.zip",
//    				"/Users/hzmk/Downloads/unipay3/wechat/wap/wechatpay-mockuai_demo-wap.zip");
    		String filePath = "C:/Users/Administrator/Desktop/order20160702-151938_11.xls";
//    		String key = DateUtil.convertDateToString(new Date())+"/"+"order20160702-151938_11";
    		OSSUtil.putObject(ossClientAPI.ossClient, "exceloss", key, filePath);
    		
    	}catch(Exception e){
    		e.printStackTrace();
    	}
		
    }
    
}
