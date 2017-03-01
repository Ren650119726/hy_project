package com.mockuai.rainbowcenter.core.api.impl.http;

import com.mockuai.rainbowcenter.common.api.BaseRequest;
import com.mockuai.rainbowcenter.common.api.Request;
import com.mockuai.rainbowcenter.common.api.Response;
import com.mockuai.rainbowcenter.common.constant.ActionEnum;
import com.mockuai.rainbowcenter.common.util.JsonUtil;
import com.mockuai.rainbowcenter.core.api.impl.RequestAdapter;
import com.mockuai.rainbowcenter.core.service.RequestDispatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by zengzhangqiang on 9/21/15.
 */
@Controller
public class EdbHttpApi {
    private static final Logger log = LoggerFactory.getLogger(EdbHttpApi.class);
    @Resource
    private RequestDispatcher requestDispatcher;

    @RequestMapping(value = "/edb/router.rest", produces = {"application/json;charset=UTF-8"})
    public void getBizByDomain(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String method = request.getParameter("method");

        Request rainbowReq = new BaseRequest();
        //透传所有e店宝参数
        Map<String, String[]> httpParams = request.getParameterMap();
        if (httpParams == null) {
            //TODO error handle
        }

        for (Map.Entry<String, String[]> param : httpParams.entrySet()) {
            String key = param.getKey();
            String value = param.getValue()[0];
            log.info(" [ key:{}, value:{} ] ", key, value);
            rainbowReq.setParam(key, value);

        }

        rainbowReq.setCommand(ActionEnum.EDB_API.getActionName());

        Response rainbowResp = requestDispatcher.dispatch(new RequestAdapter(rainbowReq));

        //TODO response handle
        if (rainbowResp.isSuccess()) {
            //透传底层返回的response
            writeResp(response, genJsonResponse(rainbowResp.getModule()));
        } else {
            //TODO error handle
            writeResp(response, genJsonResponse(rainbowResp.getMessage()));
        }
    }

    private void writeResp(HttpServletResponse response, String resp) throws Exception {
        response.setContentType("text/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(resp);
    }

    private <T> String genJsonResponse(T data) {
        return JsonUtil.toJson(data);
    }
}
