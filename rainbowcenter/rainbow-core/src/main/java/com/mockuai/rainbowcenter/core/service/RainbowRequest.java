package com.mockuai.rainbowcenter.core.service;


import com.mockuai.rainbowcenter.common.api.Request;
import com.mockuai.rainbowcenter.core.exception.RainbowException;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wujin.zzq
 */
public abstract class RainbowRequest implements Request {

	private static final long serialVersionUID = -6902403430608462635L;
	private String command;
	private Context context;

	private Map<String, Object> attributes = new HashMap<String, Object>();
	/**
	 * 获取系统context
	 * 
	 * @return
	 */
	public Context getContext() {
		return context;
	}

	/**
	 * 设置系统context
	 * 
	 * @param context
	 */
	public void setContext(Context context) {
		this.context = context;
	}

	@Override
	public void setCommand(String command) {
		this.command = command;
	}

	@Override
	public String getCommand() {
		return command;
	}

	/**
	 * 设置应用本次请求的全局变量
	 *
	 * @param key
	 * @param val
	 */
	public void setAttribute(String key, Object val) {
		attributes.put(key, val);
	}

	/**
	 *
	 * 获取应用本次请求的全局变量
	 *
	 * @param key
	 * @return
	 */
	public Object getAttribute(String key) {
		return attributes.get(key);
	}

	public void addAttributes(Map<String,Object> attributes){
		this.attributes.putAll(attributes);
	}

	public Map<String, Object> getAttributes() {
		return attributes;
	}

	/**
	 *
	 * @param key
	 * @param allowNull
	 * @return
	 * @throws com.mockuai.rainbowcenter.core.exception.RainbowException
	 */
	public abstract Long getLong(String key, boolean allowNull) throws RainbowException;

	/**
	 *
	 * @param key
	 * @param allowNull
	 * @return
	 * @throws com.mockuai.rainbowcenter.core.exception.RainbowException
	 */
	public abstract String getString(String key, boolean allowNull) throws RainbowException;

	/**
	 *
	 * @param key
	 * @param allowNull
	 * @return
	 * @throws com.mockuai.rainbowcenter.core.exception.RainbowException
	 */
	public abstract Boolean getBoolean(String key, boolean allowNull) throws RainbowException;

	/**
	 *
	 * @param key
	 * @param allowNull
	 * @return
	 * @throws com.mockuai.rainbowcenter.core.exception.RainbowException
	 */
	public abstract Integer getInteger(String key, boolean allowNull) throws RainbowException;

	/**
	 *
	 * @param key
	 * @param allowNull
	 * @return
	 * @throws com.mockuai.rainbowcenter.core.exception.RainbowException
	 */
	public abstract Double getDouble(String key, boolean allowNull) throws RainbowException;

	/**
	 *
	 * @param key
	 * @param allowNull
	 * @return
	 * @throws com.mockuai.rainbowcenter.core.exception.RainbowException
	 */
	public abstract Float getFloat(String key, boolean allowNull) throws RainbowException;

	/**
	 *
	 * @param key
	 * @param allowNull
	 * @return
	 * @throws com.mockuai.rainbowcenter.core.exception.RainbowException
	 */
	public abstract String[] getStrings(String key, boolean allowNull) throws RainbowException;
}
