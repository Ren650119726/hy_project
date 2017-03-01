package com.mockuai.tradecenter.core.api.impl;

import java.util.Set;

import com.mockuai.tradecenter.common.api.Request;
import com.mockuai.tradecenter.core.service.TradeRequest;

/**
 * 
 * @author zhangqiang.zeng
 *
 */
public class RequestAdapter extends TradeRequest {
	private static final long serialVersionUID = 8350035885108155607L;

	private Request request;

	public RequestAdapter(Request request) {
		this.request = request;
	}

	@Override
	public Long getLong(String key) {
		Object value = request.getParam(key);
		return value==null ? null:Long.parseLong(value.toString());
	}

	@Override
	public Boolean getBoolean(String key) {
		return (Boolean) request.getParam(key);
	}

	@Override
	public Integer getInteger(String key) {
		return (Integer) request.getParam(key);
	}

	@Override
	public Double getDouble(String key) {
		return (Double) request.getParam(key);
	}

	@Override
	public Float getFloat(String key) {
		return (Float) request.getParam(key);
	}

	@Override
	public Object getObject(String key) {
		return request.getParam(key);
	}

	@Override
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

	@Override
	public String[] getStrings(String key) {
		return (String[])request.getParam(key);
	}

}
