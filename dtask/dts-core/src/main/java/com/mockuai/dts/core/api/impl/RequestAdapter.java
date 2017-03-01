package com.mockuai.dts.core.api.impl;

import com.mockuai.dts.common.api.action.DtsRequest;
import com.mockuai.dts.common.api.action.Request;

import java.util.Set;


/**
 * 
 * @author luliang
 *
 */
public class RequestAdapter extends DtsRequest {

	private Request request;

	public RequestAdapter(Request request) {
		this.request = request;
	}

	public Long getLong(String key) {
		Object value = request.getParam(key);
		return value==null ? null:Long.parseLong(value.toString());
	}

	public Boolean getBoolean(String key) {
		return (Boolean) request.getParam(key);
	}

	public Integer getInteger(String key) {
		return (Integer) request.getParam(key);
	}

	public Double getDouble(String key) {
		return (Double) request.getParam(key);
	}

	public Float getFloat(String key) {
		return (Float) request.getParam(key);
	}

	public Object getObject(String key) {
		return request.getParam(key);
	}

	public String getString(String key) {
		return (String) request.getParam(key);
	}

	@Override
	public String getCommand() {
		return request.getCommand();
	}

	@Override
	public Set<String> getParamNames() {
		return request.getParamNames();
	}

	@Override
	public Object getParam(String key) {
		return request.getParam(key);
	}

	public String[] getStrings(String key) {
		return (String[])request.getParam(key);
	}

}
