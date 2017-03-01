package com.mockuai.distributioncenter.common.api;

import java.io.Serializable;
import java.util.Set;

/**
 * Created by duke on 15/10/28.
 */
public interface Request extends Serializable {
    /**
     * 获得业务Action名字
     * */
    String getCommand();

    /**
     * 设置业务Action名字
     * @param command
     * */
    void setCommand(String command);

    /**
     * 设置参数
     * @param paramName
     * @param paramObject
     * */
    void setParam(String paramName, Object paramObject);

    /**
     * 获得参数
     * @param paramName
     * */
    Object getParam(String paramName);

    /**
     * 获得属性
     * */
    Object getAttribute(String attrName);

    /**
     * 设置属性
     * */
    void setAttribute(String attrName, Object value);

    Set<String> getParamNames();
}
