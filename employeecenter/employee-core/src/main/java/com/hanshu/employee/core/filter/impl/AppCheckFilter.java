package com.hanshu.employee.core.filter.impl;

import com.mockuai.appcenter.common.domain.AppInfoDTO;
import com.hanshu.employee.common.api.EmployeeResponse;
import com.hanshu.employee.common.constant.ResponseCode;
import com.hanshu.employee.core.exception.EmployeeException;
import com.hanshu.employee.core.filter.Filter;
import com.hanshu.employee.core.manager.AppManager;
import com.hanshu.employee.core.service.RequestContext;
import org.apache.commons.lang.StringUtils;


public class AppCheckFilter implements Filter {

    @Override
    public boolean isAccept(RequestContext ctx) {
        return true;
    }

    @Override
    public EmployeeResponse before(RequestContext ctx) throws EmployeeException {
        String appKey = (String) ctx.getRequest().getParam("appKey");
        if (StringUtils.isBlank(appKey)) {
            return new EmployeeResponse(ResponseCode.P_PARAM_NULL, "appKey is null");
        }

        AppManager appManager = (AppManager) ctx.getAppContext().getBean("appManager");
        AppInfoDTO appInfoDTO = appManager.getAppInfo(appKey);
        if (appInfoDTO == null) {
            return new EmployeeResponse(ResponseCode.B_APP_NOT_EXIST, "appKey is invalid");
        }

        //将app所属的bizCode塞到context中
        ctx.put("bizCode", appInfoDTO.getBizCode());
        ctx.put("appType", appInfoDTO.getAppType());
        ctx.put("appKey", appInfoDTO.getAppKey());

        return new EmployeeResponse(ResponseCode.REQUEST_SUCCESS);
    }

    @Override
    public EmployeeResponse after(RequestContext ctx) throws EmployeeException {
        return new EmployeeResponse(ResponseCode.REQUEST_SUCCESS);
    }
}
