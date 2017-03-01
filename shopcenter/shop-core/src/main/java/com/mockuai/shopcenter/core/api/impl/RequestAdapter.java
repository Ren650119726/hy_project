package com.mockuai.shopcenter.core.api.impl;

import com.mockuai.shopcenter.api.Request;
import com.mockuai.shopcenter.core.service.ShopRequest;

import java.util.Set;

/**
 * 
 * @author ziqi
 *
 */
public class RequestAdapter extends ShopRequest {
	private static final long serialVersionUID = 1L;

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
	public <T> T getObject(String key,Class<T> clazz) {

        Object object = request.getParam(key);

        if(object==null || !clazz.isInstance(object)){
            return null;
        }

        return clazz.cast(object);
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
