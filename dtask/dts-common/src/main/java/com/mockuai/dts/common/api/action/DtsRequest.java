package com.mockuai.dts.common.api.action;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by luliang
 */
public class DtsRequest implements Request, Serializable {

    private static final long serialVersionUID = 1L;

    private String command;
    private Map<String, Object> params = new HashMap<String, Object>(8);
    private final Map<String, Object> attrs = new HashMap<String, Object>();

    public DtsRequest() {
    }

    public DtsRequest(String command) {
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
