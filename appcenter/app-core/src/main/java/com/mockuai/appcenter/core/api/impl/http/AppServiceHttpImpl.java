package com.mockuai.appcenter.core.api.impl.http;

import com.mockuai.appcenter.common.api.AppService;
import com.mockuai.appcenter.common.api.BaseRequest;
import com.mockuai.appcenter.common.api.Request;
import com.mockuai.appcenter.common.api.Response;
import com.mockuai.appcenter.common.constant.ActionEnum;
import com.mockuai.appcenter.common.constant.AppTypeEnum;
import com.mockuai.appcenter.common.constant.ResponseCode;
import com.mockuai.appcenter.common.domain.AppInfoDTO;
import com.mockuai.appcenter.common.domain.AppInfoQTO;
import com.mockuai.appcenter.common.domain.BizInfoDTO;
import com.mockuai.appcenter.common.util.JsonUtil;
import com.mockuai.appcenter.core.api.impl.RequestAdapter;
import com.mockuai.appcenter.core.domain.AppInfoDO;
import com.mockuai.appcenter.core.domain.BizInfoDO;
import com.mockuai.appcenter.core.manager.AppInfoManager;
import com.mockuai.appcenter.core.service.AppRequest;
import com.mockuai.appcenter.core.service.RequestDispatcher;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zengzhangqiang on 9/21/15.
 */
@Controller
public class AppServiceHttpImpl {
    @Resource
    private RequestDispatcher requestDispatcher;

    @RequestMapping(value = "/biz_info/get_by_domain.rest", produces = {"application/json;charset=UTF-8"})
    public void getBizByDomain(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String domainName = request.getParameter("domain_name");

        Request appRequest = new BaseRequest();
        appRequest.setParam("domainName", domainName);
        appRequest.setCommand(ActionEnum.GET_BIZ_INFO_BY_DOMAIN.getActionName());
        Response<BizInfoDTO> bizResp = requestDispatcher.dispatch(new RequestAdapter(appRequest));
        if(bizResp.isSuccess()){
            Map<String,Object> dataMap = new HashMap<String, Object>();
            dataMap.put("biz_info", bizResp.getModule());
            writeResp(response, genJsonResponse(bizResp.getCode(), bizResp.getMessage(), dataMap));
        }else{
            writeResp(response,genJsonResponse(bizResp.getCode(), bizResp.getMessage(), null));
        }
    }

    @RequestMapping(value = "/biz_info/list.rest", produces = {"application/json;charset=UTF-8"})
    public void queryBizInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String bizNameStr = request.getParameter("biz_name");
        String offsetStr = request.getParameter("offset");
        String countStr = request.getParameter("count");

        Request appRequest = new BaseRequest();
        appRequest.setParam("bizNameLike", bizNameStr);
        appRequest.setParam("offset", Integer.valueOf(offsetStr));
        appRequest.setParam("count", Integer.valueOf(countStr));
        appRequest.setCommand(ActionEnum.QUERY_BIZ_INFO.getActionName());
        Response<List<BizInfoDTO>> bizResp = requestDispatcher.dispatch(new RequestAdapter(appRequest));
        if(bizResp.isSuccess()){
            Map<String,Object> dataMap = new HashMap<String, Object>();
            dataMap.put("biz_info", bizResp.getModule());
            writeResp(response, genJsonResponse(bizResp.getCode(), bizResp.getMessage(), dataMap));
        }else{
            writeResp(response,genJsonResponse(bizResp.getCode(), bizResp.getMessage(), null));
        }
    }

    @RequestMapping(value = "/app_info/wap/get.rest", produces = {"application/json;charset=UTF-8"})
    public void getWapAppInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String bizCode = request.getParameter("biz_code");

        Request appRequest = new BaseRequest();
        appRequest.setParam("bizCode", bizCode);
        appRequest.setParam("appType", AppTypeEnum.APP_WAP.getValue());
        appRequest.setCommand(ActionEnum.GET_APP_INFO_BY_TYPE.getActionName());
        Response<AppInfoDTO> appResp = requestDispatcher.dispatch(new RequestAdapter(appRequest));
        if(appResp.isSuccess()){
            Map<String,Object> dataMap = new HashMap<String, Object>();
            dataMap.put("app_info", appResp.getModule());
            writeResp(response, genJsonResponse(appResp.getCode(), appResp.getMessage(), dataMap));
        }else{
            writeResp(response,genJsonResponse(appResp.getCode(), appResp.getMessage(), null));
        }
    }

    @RequestMapping(value = "/app_info/pc_mall/get.rest", produces = {"application/json;charset=UTF-8"})
    public void getPcMallAppInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String bizCode = request.getParameter("biz_code");

        Request appRequest = new BaseRequest();
        appRequest.setParam("bizCode", bizCode);
        appRequest.setParam("appType", AppTypeEnum.APP_PC_MALL.getValue());
        appRequest.setCommand(ActionEnum.GET_APP_INFO_BY_TYPE.getActionName());
        Response<AppInfoDTO> appResp = requestDispatcher.dispatch(new RequestAdapter(appRequest));
        if(appResp.isSuccess()){
            Map<String,Object> dataMap = new HashMap<String, Object>();
            dataMap.put("app_info", appResp.getModule());
            writeResp(response, genJsonResponse(appResp.getCode(), appResp.getMessage(), dataMap));
        }else{
            writeResp(response,genJsonResponse(appResp.getCode(), appResp.getMessage(), null));
        }
    }

    @RequestMapping(value = "/biz_info/get.rest", produces = {"application/json;charset=UTF-8"})
    public void getBizInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String bizCode = request.getParameter("biz_code");

        Request appRequest = new BaseRequest();
        appRequest.setParam("bizCode", bizCode);
        appRequest.setCommand(ActionEnum.GET_BIZ_INFO.getActionName());
        Response<BizInfoDTO> bizResp = requestDispatcher.dispatch(new RequestAdapter(appRequest));
        if(bizResp.isSuccess()){
            Map<String,Object> dataMap = new HashMap<String, Object>();
            bizResp.getModule().setBizPropertyMap(null);//FIXME 这里为确保数据安全性，移除bizPropertyMap
            dataMap.put("biz_info", bizResp.getModule());
            writeResp(response, genJsonResponse(bizResp.getCode(), bizResp.getMessage(), dataMap));
        }else{
            writeResp(response,genJsonResponse(bizResp.getCode(), bizResp.getMessage(), null));
        }
    }



    private void writeResp(HttpServletResponse response, String resp) throws Exception{
        response.setContentType("text/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(resp);
    }

    private <T> String genJsonResponse(int code, String msg, T data){
        AppHttpResponse appHttpResponse = new AppHttpResponse(code, msg, data);

        return JsonUtil.toJson(appHttpResponse);
    }

    public static class AppHttpResponse<T>{
        private int code;
        private String msg;
        private T data;

        public AppHttpResponse(int code, String msg, T data){
            this.code = code;
            this.msg = msg;
            this.data = data;
        }

        public int getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }

        public T getData() {
            return data;
        }
    }

}
