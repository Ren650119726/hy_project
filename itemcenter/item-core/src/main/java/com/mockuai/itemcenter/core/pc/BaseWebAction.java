package com.mockuai.itemcenter.core.pc;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Strings;
import com.mockuai.appcenter.common.util.JsonUtil;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.util.ExceptionUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by yindingyu on 15/11/23.
 */
public class BaseWebAction {

    protected String getAppkey(HttpServletRequest request) throws ItemException {

        String appKey = request.getParameter("appKey");

        if(Strings.isNullOrEmpty(appKey)){
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING,"appKey不能为空");
        }

        return appKey;
    }

    protected String getParameter(HttpServletRequest request,String name) throws ItemException {

        String value = request.getParameter(name);

        if(Strings.isNullOrEmpty(name)){
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING,name+"不能为空");
        }

        return value;
    }

    protected  <T> T getParameter(HttpServletRequest request,String name,Class<T> clazz) throws ItemException {

        String value = request.getParameter(name);

        if(Strings.isNullOrEmpty(name)){
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING,name+"不能为空");
        }

        try {

            T t = JsonUtil.parseJson(value, clazz);

            return t;

        }catch (Exception e){
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_INVALID,name+"格式错误");
        }

    }

    protected Long getLong(HttpServletRequest request,String name) throws ItemException{
        return  getParameter(request,name,Long.class);
    }

    protected Integer getInteger(HttpServletRequest request,String name) throws ItemException{
        return  getParameter(request,name,Integer.class);
    }

    protected Boolean getBoolean(HttpServletRequest request,String name) throws ItemException{
        return  getParameter(request,name,Boolean.class);
    }

}
