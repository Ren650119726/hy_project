package com.mockuai.messagecenter.common.api;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 
 * @author zhangqiang.zeng
 *
 */
public class BaseRequest implements Request {
	private static final long serialVersionUID = -5466899657256730640L;
	private String command;
	private Map<String, Object> params = new HashMap<String, Object>(8);
	private final Map<String, Object> attrs = new HashMap<String, Object>();
	
	public BaseRequest() {
	}

	public BaseRequest(String command) {
		this.command = command;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public Set<String> getParamNames() {
		return params.keySet();
	}

	public Object getParam(String key) {
		return params.get(key);
	}

	public void setParam(String key, Object value) {
		params.put(key, value);
	}

	/**
	 * 
	 * @param key
	 * @param value
	 */
	public void setAttribute(String key, Object value) {
		attrs.put(key, value);
	}

	/**
	 * 
	 * @param key
	 * @return
	 */
	public Object getAttribte(String key) {
		return attrs.get(key);
	}
}
