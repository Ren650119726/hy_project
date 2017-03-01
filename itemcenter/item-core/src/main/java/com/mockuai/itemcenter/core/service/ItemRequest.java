package com.mockuai.itemcenter.core.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.mockuai.itemcenter.common.api.Request;

/**
 * @author wujin.zzq
 */
public class ItemRequest implements Request {

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

	/**
	 * 获取格式化好的value
	 * 
	 * @param key
	 * @return
	 */
	public Long getLong(String key) {
		return null;
	}

	public String getString(String key) {
		return null;
	}

	/**
	 * 
	 * 获取格式化好的value
	 * 
	 * @param key
	 * @return
	 */
	public Boolean getBoolean(String key) {
		return null;
	}

	/**
	 * 
	 * 获取格式化好的value
	 * 
	 * @param key
	 * @return
	 */
	public Integer getInteger(String key) {
		return null;
	}

	/**
	 * 
	 * 获取格式化好的value
	 * 
	 * @param key
	 * @return
	 */
	public Double getDouble(String key) {
		return null;
	}

	/**
	 * 
	 * 获取格式化好的value
	 * 
	 * @param key
	 * @return
	 */
	public Float getFloat(String key) {
		return null;
	}

	public String[] getStrings(String key) {
		return null;
	}

	/**
	 * 
	 * 获取格式化好的value
	 * 
	 * @param key
	 * @return
	 */
	public Object getObject(String key) {
		return null;
	}

	/**
	 *
	 * 获取格式化好的value
	 *
	 * @param key
	 * @return
	 */
	public <T> T getObject(String key,Class<T> clazz) {
		return null;
	}

	@Override
	public void setCommand(String command) {
		this.command = command;
	}

	@Override
	public String getCommand() {
		return command;
	}

	@Override
	public Set<String> getParamNames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getParam(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setParam(String key, Object value) {
		// TODO Auto-generated method stub

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

	public void addAttributes(Map<String, Object> attributes) {
		this.attributes.putAll(attributes);
	}

	public Map<String, Object> getAttributes() {
		return attributes;
	}
}
