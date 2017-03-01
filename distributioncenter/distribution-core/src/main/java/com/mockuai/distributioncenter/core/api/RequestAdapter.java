package com.mockuai.distributioncenter.core.api;

import com.mockuai.distributioncenter.common.api.Request;

import java.util.Set;

/**
 * Created by duke on 15/10/28.
 */
public class RequestAdapter implements Request {
    private Request request;

    public RequestAdapter(Request request) {
        this.request = request;
    }

    public Long getLong(String key) {
        Object value = this.request.getParam(key);
        return value == null ? null : Long.parseLong(value.toString());
    }

    public Boolean getBoolean(String key) {
        Object value = this.request.getParam(key);
        return value == null ? null : Boolean.parseBoolean(value.toString());
    }


    public Integer getInteger(String key) {
        Object value = this.request.getParam(key);
        return value == null ? null : Integer.parseInt(value.toString());
    }


    public Double getDouble(String key) {
        Object value = this.request.getParam(key);
        return value == null ? null : Double.parseDouble(value.toString());
    }

    public Float getFloat(String key) {
        Object value = this.request.getParam(key);
        return value == null ? null : Float.parseFloat(value.toString());
    }

    public Object getObject(String key) {
        return this.request.getParam(key);
    }

    public String getString(String key) {
        Object value = this.request.getParam(key);
        return value == null ? null : value.toString();
    }

    public String getCommand() {
        return this.request.getCommand();
    }

    @Override
    public void setCommand(String command) {
        this.request.setCommand(command);
    }

    @Override
    public void setParam(String paramName, Object paramObject) {
        this.request.setParam(paramName, paramObject);
    }

    @Override
    public Object getParam(String paramName) {
        return this.request.getParam(paramName);
    }

    @Override
    public Object getAttribute(String attrName) {
        return this.request.getAttribute(attrName);
    }

    @Override
    public void setAttribute(String attrName, Object value) {
        this.request.setAttribute(attrName, value);
    }

    @Override
    public Set<String> getParamNames() {
        return this.request.getParamNames();
    }
}
