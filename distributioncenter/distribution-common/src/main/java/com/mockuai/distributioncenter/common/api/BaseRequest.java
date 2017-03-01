package com.mockuai.distributioncenter.common.api;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by duke on 15/10/28.
 */
public class BaseRequest implements Request {
    private final Map<String, Object> params = new HashMap<String, Object>();
    private final Map<String, Object> attributes = new HashMap<String, Object>();
    private String command;

    public String getCommand() {
        return this.command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public void setParam(String paramName, Object paramObject) {
        this.params.put(paramName, paramObject);
    }

    public Object getParam(String paramName) {
        return this.params.get(paramName);
    }

    public Set<String> getParamNames() {
        return this.params.keySet();
    }

    public Object getAttribute(String attrName) {
        return this.attributes.get(attrName);
    }

    public void setAttribute(String attrName, Object value) {
        this.attributes.put(attrName, value);
    }
}
