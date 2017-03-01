package com.mockuai.appcenter.core.service.action.impl;

import com.mockuai.appcenter.common.api.AppResponse;
import com.mockuai.appcenter.common.constant.ActionEnum;
import com.mockuai.appcenter.common.constant.ResponseCode;
import com.mockuai.appcenter.core.domain.AppInfoDO;
import com.mockuai.appcenter.core.exception.AppException;
import com.mockuai.appcenter.core.manager.AppInfoManager;
import com.mockuai.appcenter.core.manager.AppPropertyManager;
import com.mockuai.appcenter.core.manager.BizPropertyManager;
import com.mockuai.appcenter.core.service.RequestContext;
import com.mockuai.appcenter.core.service.action.Action;
import org.apache.commons.lang.StringUtils;

import javax.annotation.Resource;

/**
 * Created by zengzhangqiang on 6/8/15.
 */
public class DeleteAppProperty implements Action{
    @Resource
    private AppPropertyManager appPropertyManager;

    @Resource
    private AppInfoManager appInfoManager;

    @Override
    public AppResponse execute(RequestContext context) {
        String appKey = (String)context.getRequest().getParam("appKey");
        String pKey = (String)context.getRequest().getParam("pKey");

        //TODO 入参检查

        if(StringUtils.isBlank(appKey)){
            return new AppResponse(ResponseCode.PARAM_E_PARAM_MISSING, "appKey is null");
        }

        if(StringUtils.isBlank(pKey)){
            return new AppResponse(ResponseCode.PARAM_E_PARAM_MISSING, "pKey is null");
        }


        try{

            AppInfoDO appInfoDO = appInfoManager.getAppInfo(appKey);

            int opNum = appPropertyManager.deleteAppProperty(appInfoDO.getId(), pKey);

            if(opNum == 1){
                return new AppResponse(ResponseCode.RESPONSE_SUCCESS);
            }else{
                //TODO error handle
                return new AppResponse(ResponseCode.SYS_E_DEFAULT_ERROR);
            }

        }catch(AppException e){
            //TODO error handle
            return new AppResponse(e.getResponseCode(), e.getMessage());
        }
    }

    @Override
    public String getName() {
        return ActionEnum.DELETE_APP_PROPERTY.getActionName();
    }
}
