package com.mockuai.tradecenter.core.job;

import java.io.File;

import javax.annotation.Resource;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import com.mockuai.tradecenter.core.api.impl.OSSClientAPI;
import com.mockuai.tradecenter.core.api.impl.OSSUtil;
import com.mockuai.tradecenter.core.service.job.BaseJob;
import com.mockuai.tradecenter.core.util.FileUtils;
import com.mockuai.tradecenter.core.util.TradeCoreConfig;

/**
 * 下载证书job    
 * 
 *
 */
public class DownloadCertJob extends BaseJob {
	private static final Logger log = LoggerFactory.getLogger(DownloadCertJob.class);
	@Resource
	private OSSClientAPI oSSClientAPI;
	
	@Resource
	private TradeCoreConfig tradeCoreConfig;
	
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		downloadCert();
		
	}

	public void downloadCert(){
		try{
			log.info(" downloadCertTask start ");
			oSSClientAPI.init();
			ObjectListing objectListing = OSSUtil.listObjects(oSSClientAPI.getOssClient(), "cert-dir");
			  // 遍历所有Object
	        for (OSSObjectSummary objectSummary : objectListing.getObjectSummaries()) {
	        	
	        	String keyName = objectSummary.getKey();
	        	log.info("keyname:"+keyName);
	        	String keyArray[] = keyName.split("\\/");
	        	
	        	if(keyArray.length==3&&keyArray[0].equals("unipay")){
	        		String bizCode = keyArray[1];
	        		String bizCodeUnipayDir = tradeCoreConfig.getUnipayCertDir()+"/"+bizCode;
	        		FileUtils.mkdir(bizCodeUnipayDir);
	        		
        			String filename = bizCodeUnipayDir+"/"+keyArray[2];
        			File file = new File( filename );
        			if( ! file.exists() ){
						oSSClientAPI.downloadFile(oSSClientAPI.getOssClient(),"cert-dir",keyName,
								filename);
					}
	        		
	        		
	        	}else if(keyArray.length==4&&keyArray[0].equals("wechatpay")){
	        		String bizCode = keyArray[1];
	        		String paymentType = keyArray[2];
	        		String bizCodeWechatPayDir = tradeCoreConfig.getWechatpayCertDir()+"/"+bizCode+"/"+paymentType;
	        		FileUtils.mkdir(bizCodeWechatPayDir);
	        		String fileName = bizCodeWechatPayDir+"/"+keyArray[3];
					
					File file = new File( fileName );
					
					if( ! file.exists() ){
						oSSClientAPI.downloadFile(oSSClientAPI.getOssClient(),"cert-dir",keyName,
								fileName);
					}
	        	}
	        	
	        	
	        	
	        }
			log.info(" downloadCertTask end ");
		}catch(Exception e){
			
		}
		
	}
}
