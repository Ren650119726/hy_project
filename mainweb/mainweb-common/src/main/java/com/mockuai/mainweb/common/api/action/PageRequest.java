package com.mockuai.mainweb.common.api.action;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2016/9/19.
 */
public class PageRequest implements Request{
//    private static final long serialVersionUID = 1L;


    private String command;
    private Map<String, Object> params = new HashMap<String, Object>(8);
    private final Map<String, Object> attrs = new HashMap<String, Object>();

    public PageRequest() {
    }

    public PageRequest(String command) {
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
