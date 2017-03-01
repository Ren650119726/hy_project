package com.mockuai.suppliercenter.core.service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Context implements Serializable {

    private static final long serialVersionUID = 8332699715561224149L;

    private Map<String, Object> kvs = new HashMap<String, Object>(4);
    private Map<String, Object> errors = new HashMap<String, Object>(2);

    public void put(String key, Object value) {
        kvs.put(key, value);
    }

    public Object get(String key) {
        return kvs.get(key);
    }

    public void addProcessError(String errorCode, Exception e) {
        errors.put(errorCode, e);

    }

    public void addProcessError(String errorCode, String msg) {
        errors.put(errorCode, msg);
    }

    public Map<String, Object> getErrors() {
        return errors;
    }

}
