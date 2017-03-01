package com.mockuai.appcenter.common.api;

import java.io.Serializable;
import java.util.Set;

/**
 * 
 * @author wujin.zzq
 *
 */
public interface Request extends Serializable {

	/**
	 * 获取请求命令类型
	 * 
	 * @return
	 */
	public String getCommand();

	/**
	 * 设置请求命令类型
	 * 
	 * @return
	 */
	public void setCommand(String command);

	/**
	 * 穷举参数名字
	 * 
	 * @return
	 */
	public Set<String> getParamNames();

	/**
	 * 根据名字, 获取参数值
	 * 
	 * @param key
	 * @return 返回对象, 如果没有指定的key, 则返回<code>null</code>
	 */
	public Object getParam(String key);

	/**
	 * 设置k-v对
	 * 
	 * @param key
	 * @param value
	 */
	public void setParam(String key, Object value);
}
