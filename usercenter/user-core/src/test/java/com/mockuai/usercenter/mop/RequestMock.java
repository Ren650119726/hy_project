/*package com.mockuai.usercenter.mop;

import com.mockuai.mop.common.service.action.Request;

import java.util.HashMap;
import java.util.Map;

*//**
 * Created by zengzhangqiang on 5/17/15.
 *//*
public class RequestMock implements Request{
    private Map<String, String> paramMap;
    private Map<String, Object> attributeMap;

    public RequestMock(){
        paramMap = new HashMap<String, String>();
        attributeMap = new HashMap<String, Object>();
    }

    @Override
    public Object getParam(String paramName) {
        return paramMap.get(paramName);
    }

    public void setParam(String name ,String value){
        paramMap.put(name, value);
    }

    @Override
    public byte[] getPostData() {
        return new byte[0];
    }

    @Override
    public Object getAttribute(String attributeName) {
        return attributeMap.get(attributeName);
    }

    public void setAttribute(String attributeName, Object attributeValue){
        attributeMap.put(attributeName, attributeValue);
    }

    @Override
    public Map getParamMap() throws RuntimeException{
        //TODO implement
        throw new RuntimeException("please implement this method!");
    }

    @Override
    public String getRequestInputStream() {
        return null;
    }
}
*/