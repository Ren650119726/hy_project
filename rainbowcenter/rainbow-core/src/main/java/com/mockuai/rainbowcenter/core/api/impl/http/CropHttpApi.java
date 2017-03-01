package com.mockuai.rainbowcenter.core.api.impl.http;


import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mockuai.rainbowcenter.common.api.BaseRequest;
import com.mockuai.rainbowcenter.common.api.Request;
import com.mockuai.rainbowcenter.common.api.Response;
import com.mockuai.rainbowcenter.common.util.JsonUtil;
import com.mockuai.rainbowcenter.core.api.impl.RequestAdapter;
import com.mockuai.rainbowcenter.core.service.RequestDispatcher;

/**
 * Created by zhoujunjie on 2016/3/8
 */
@Controller
public class CropHttpApi {
    @Resource
    private RequestDispatcher requestDispatcher;
    
    @RequestMapping(value = "/crop/execute", produces = {"application/json;charset=UTF-8"})
    public void cropExecute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Request rainbowReq = new BaseRequest();
        Map<String,String> httpParams = request.getParameterMap();
        if(httpParams == null){
            //TODO error handle
        }
        for(Map.Entry<String,String> param: httpParams.entrySet()){
            rainbowReq.setParam(param.getKey(), param.getValue());
        }
        String method = request.getParameter("method");
        rainbowReq.setCommand(method);
        Response rainbowResp = requestDispatcher.dispatch(new RequestAdapter(rainbowReq));
        //TODO response handle
        if(rainbowResp.isSuccess()){
            //透传底层返回的response
            writeResp(response, genJsonResponse(rainbowResp.getModule()));      
        }else{
            //TODO error handle
            writeResp(response,genJsonResponse(rainbowResp.getMessage()));   
        }
    }
    
    private void writeResp(HttpServletResponse response, String resp) throws Exception{
        response.setContentType("text/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(resp);
    }

    private <T> String genJsonResponse(T data){
        return JsonUtil.toJson(data);
    }
}
