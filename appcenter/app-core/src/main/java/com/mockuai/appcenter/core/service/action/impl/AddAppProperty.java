package com.mockuai.appcenter.core.service.action.impl;

import com.mockuai.appcenter.common.api.AppResponse;
import com.mockuai.appcenter.common.constant.ActionEnum;
import com.mockuai.appcenter.common.constant.ResponseCode;
import com.mockuai.appcenter.common.domain.AppInfoDTO;
import com.mockuai.appcenter.common.domain.AppPropertyDTO;
import com.mockuai.appcenter.core.exception.AppException;
import com.mockuai.appcenter.core.manager.AppPropertyManager;
import com.mockuai.appcenter.core.service.RequestContext;
import com.mockuai.appcenter.core.service.action.Action;
import com.mockuai.appcenter.core.util.ModelUtil;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zengzhangqiang on 6/8/15.
 */
public class AddAppProperty implements Action{

    @Resource
    private AppPropertyManager appPropertyManager;

    @Override
    public AppResponse execute(RequestContext context) {
        AppPropertyDTO appPropertyDTO = (AppPropertyDTO)context.getRequest().getParam("appPropertyDTO");

        //TODO 入参检查

        if(appPropertyDTO == null){
            return new AppResponse(ResponseCode.PARAM_E_PARAM_MISSING, "appPropertyDTO is null");
        }

        try{
            Long appPropertyId = appPropertyManager.addAppProperty(ModelUtil.convertToAppPropertyDO(appPropertyDTO));

            appPropertyDTO.setId(appPropertyId);

            return new AppResponse(appPropertyDTO);
        }catch(AppException e){
            //TODO error handle
            return new AppResponse(e.getResponseCode(), e.getMessage());
        }
    }

    @Override
    public String getName() {
        return ActionEnum.ADD_APP_PROPERTY.getActionName();
    }
}
