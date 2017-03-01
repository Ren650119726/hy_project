package com.mockuai.rainbowcenter.core.api.impl;

import com.mockuai.rainbowcenter.common.api.Request;
import com.mockuai.rainbowcenter.common.constant.ResponseCode;
import com.mockuai.rainbowcenter.core.exception.RainbowException;
import com.mockuai.rainbowcenter.core.service.RainbowRequest;

import java.util.Set;

/**
 *
 * @author zhangqiang.zeng
 *
 */
public class RequestAdapter extends RainbowRequest {
	private static final long serialVersionUID = 8350035885108155607L;

	private Request request;

	public RequestAdapter(Request request) {
		this.request = request;
	}

	@Override
	public Long getLong(String key, boolean allowNull) throws RainbowException {
		String value = (String)request.getParam(key);
		boolean isNull = isNull(value, allowNull);
		if(isNull){
			return null;
		}

		try{
			return Long.parseLong(value);
		}catch(Exception e){
			throw new RainbowException(ResponseCode.PARAM_E_PARAM_FORMAT_INVALID,
					"the format of param is invalid, paramKey:"+key);
		}
	}

	@Override
	public String getString(String key, boolean allowNull) throws RainbowException {
		Object value = request.getParam(key);
		boolean isNull = isNull(value, allowNull);
		if(isNull){
			return null;
		}

		try{
			return (String)value;
		}catch(Exception e){
			throw new RainbowException(ResponseCode.PARAM_E_PARAM_FORMAT_INVALID,
					"the format of param is invalid, paramKey:"+key);
		}
	}

	@Override
	public Boolean getBoolean(String key, boolean allowNull) throws RainbowException {
		String value = (String)request.getParam(key);
		boolean isNull = isNull(value, allowNull);
		if(isNull){
			return null;
		}

		try{
			return Boolean.parseBoolean(value);
		}catch(Exception e){
			throw new RainbowException(ResponseCode.PARAM_E_PARAM_FORMAT_INVALID,
					"the format of param is invalid, paramKey:"+key);
		}
	}

	@Override
	public Integer getInteger(String key, boolean allowNull) throws RainbowException {
		String value = (String)request.getParam(key);
		boolean isNull = isNull(value, allowNull);
		if(isNull){
			return null;
		}

		try{
			return Integer.parseInt(value);
		}catch(Exception e){
			throw new RainbowException(ResponseCode.PARAM_E_PARAM_FORMAT_INVALID,
					"the format of param is invalid, paramKey:"+key);
		}
	}

	@Override
	public Double getDouble(String key, boolean allowNull) throws RainbowException {
		String value = (String)request.getParam(key);
		boolean isNull = isNull(value, allowNull);
		if(isNull){
			return null;
		}

		try{
			return Double.parseDouble(value);
		}catch(Exception e){
			throw new RainbowException(ResponseCode.PARAM_E_PARAM_FORMAT_INVALID,
					"the format of param is invalid, paramKey:"+key);
		}
	}

	@Override
	public Float getFloat(String key, boolean allowNull) throws RainbowException {
		String value = (String)request.getParam(key);
		boolean isNull = isNull(value, allowNull);
		if(isNull){
			return null;
		}

		try{
			return Float.parseFloat(value);
		}catch(Exception e){
			throw new RainbowException(ResponseCode.PARAM_E_PARAM_FORMAT_INVALID,
					"the format of param is invalid, paramKey:"+key);
		}
	}

	@Override
	public String[] getStrings(String key, boolean allowNull) throws RainbowException {
		Object value = request.getParam(key);
		boolean isNull = isNull(value, allowNull);
		if(isNull){
			return null;
		}

		try{
			return (String[])value;
		}catch(Exception e){
			throw new RainbowException(ResponseCode.PARAM_E_PARAM_FORMAT_INVALID,
					"the format of param is invalid, paramKey:"+key);
		}
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
	public void setParam(String key, Object value) {
		request.setParam(key, value);
	}

	private boolean isNull(Object value, boolean allowNull) throws RainbowException{
		if(value==null && allowNull==false){
			throw new RainbowException(ResponseCode.PARAM_E_PARAM_MISSING,
					"the specified param is missing, paramKey:"+value);
		}

		if(value == null){
			return true;
		}else{
			return false;
		}
	}

}
