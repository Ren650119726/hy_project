package com.mockuai.appcenter.core.service.action.impl;

import com.mockuai.appcenter.common.api.AppResponse;
import com.mockuai.appcenter.common.api.BaseRequest;
import com.mockuai.appcenter.common.api.Request;
import com.mockuai.appcenter.common.api.Response;
import com.mockuai.appcenter.common.constant.ActionEnum;
import com.mockuai.appcenter.common.constant.ResponseCode;
import com.mockuai.appcenter.common.domain.BizInfoDTO;
import com.mockuai.appcenter.common.domain.BizPropertyDTO;
import com.mockuai.appcenter.core.api.impl.RequestAdapter;
import com.mockuai.appcenter.core.domain.AppInfoDO;
import com.mockuai.appcenter.core.domain.BizInfoDO;
import com.mockuai.appcenter.core.domain.BizPropertyDO;
import com.mockuai.appcenter.core.exception.AppException;
import com.mockuai.appcenter.core.manager.AppInfoManager;
import com.mockuai.appcenter.core.manager.BizInfoManager;
import com.mockuai.appcenter.core.manager.BizPropertyManager;
import com.mockuai.appcenter.core.service.AppRequest;
import com.mockuai.appcenter.core.service.RequestContext;
import com.mockuai.appcenter.core.service.RequestDispatcher;
import com.mockuai.appcenter.core.service.action.Action;
import com.mockuai.appcenter.core.util.ModelUtil;
import org.apache.commons.lang.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zengzhangqiang on 6/8/15.
 */
public class GetBizInfoByAppKey implements Action{

    @Resource
    private BizInfoManager bizInfoManager;
    @Resource
    private AppInfoManager appInfoManager;
    @Resource
    private BizPropertyManager bizPropertyManager;
    @Resource
    private RequestDispatcher requestDispatcher;

    @Override
    public AppResponse execute(RequestContext context) {
        String appKey = (String)context.getRequest().getParam("appKey");

        //TODO 入参检查

        if(StringUtils.isBlank(appKey)){
            return new AppResponse(ResponseCode.PARAM_E_PARAM_MISSING, "appKey is null");
        }

        try{
            AppInfoDO appInfoDO = appInfoManager.getAppInfo(appKey);
            if(appInfoDO == null){//appInfo不存在的话，则直接返回错误
                return new AppResponse(ResponseCode.BIZ_E_APP_INFO_NOT_EXIST);
            }

            //调用getBizInfo接口，将bizInfo的获取委托给getBizInfo接口
            Request appRequest = new BaseRequest();
            appRequest.setParam("bizCode", appInfoDO.getBizCode());
            appRequest.setCommand(ActionEnum.GET_BIZ_INFO.getActionName());
            AppResponse<BizInfoDTO> bizResp = requestDispatcher.dispatch(new RequestAdapter(appRequest));

            return bizResp;
        }catch(AppException e){
            //TODO error handle
            return new AppResponse(e.getResponseCode(), e.getMessage());
        }
    }

    @Override
    public String getName() {
        return ActionEnum.GET_BIZ_INFO_BY_APP_KEY.getActionName();
    }
}
