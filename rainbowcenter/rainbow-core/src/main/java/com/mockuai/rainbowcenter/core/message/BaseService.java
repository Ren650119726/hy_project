package com.mockuai.rainbowcenter.core.message;

import org.slf4j.Logger;

import com.alibaba.fastjson.JSONObject;

public class BaseService {

	/**
	 * 进入服务打印日志
	 * 
	 * @param log
	 * @param serviceName
	 * @param req
	 */
	protected void printIntoService(Logger log, String serviceName, Object req,String uuid) {
		if (log.isInfoEnabled()) {
			log.info("进入" + serviceName + " correlationID=["+uuid+"]请求参数:" + JSONObject.toJSONString(req));
		}
	}

	/**
	 * 调用其它服务打印日志
	 * 
	 * @param log
	 * @param serviceName
	 * @param req
	 */
	protected void printInvokeService(Logger log, String serviceName, Object req,String uuid) {
		if (log.isInfoEnabled()) {
			log.info("调用" + serviceName + " correlationID=["+uuid+"]响应参数:" + JSONObject.toJSONString(req));
		}
	}
	
	/**
	 * 执行过程中日志
	 * @param log
	 * @param serviceName
	 * @param message
	 * @param uuid
	 */
	protected void printInfoService(Logger log, String serviceName, 
			String uuid, String message) {
		if (log.isInfoEnabled()) {
			log.info("调用" + serviceName + " correlationID=["+uuid+"]执行消息:" + message);
		}
		
	}

	/**
	 * 调用服务返回
	 * 
	 * @param log
	 * @param serviceName
	 * @param res
	 */
	protected void printInvokeServiceReturn(Logger log, String serviceName, Object res,String uuid) {
		if (log.isInfoEnabled()) {
			log.info("调用" + serviceName + " correlationID=["+uuid+"]返回参数:" + JSONObject.toJSONString(res));
		}
		
	}

	/**
	 * 退出服务
	 * 
	 * @param log
	 * @param serviceName
	 * @param req
	 */
	protected void printOutService(Logger log, String serviceName, Object res,String uuid) {
		if (log.isInfoEnabled()) {
		log.info("退出" + serviceName + " correlationID=["+uuid+"]响应参数:" + JSONObject.toJSONString(res));
		}
	}
	
	/**s
	 * @param log
	 * @param serviceName
	 * @param uuid
	 * @param msg
	 */
	protected void printErrorLog(Logger log, String serviceName, String uuid,String msg) {
		if (log.isInfoEnabled()) {
			log.error("调用" + serviceName + " correlationID=["+uuid+"]异常:" + msg);
		}	
	}
	
}
