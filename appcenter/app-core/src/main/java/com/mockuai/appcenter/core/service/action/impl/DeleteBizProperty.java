package com.mockuai.appcenter.core.service.action.impl;

import com.mockuai.appcenter.common.api.AppResponse;
import com.mockuai.appcenter.common.constant.ActionEnum;
import com.mockuai.appcenter.common.constant.ResponseCode;
import com.mockuai.appcenter.common.domain.BizPropertyDTO;
import com.mockuai.appcenter.core.exception.AppException;
import com.mockuai.appcenter.core.manager.BizPropertyManager;
import com.mockuai.appcenter.core.service.RequestContext;
import com.mockuai.appcenter.core.service.action.Action;
import com.mockuai.appcenter.core.util.ModelUtil;
import org.apache.commons.lang.StringUtils;

import javax.annotation.Resource;

/**
 * Created by zengzhangqiang on 6/8/15.
 */
public class DeleteBizProperty implements Action{
    @Resource
    private BizPropertyManager bizPropertyManager;

    @Override
    public AppResponse execute(RequestContext context) {
        String bizCode = (String)context.getRequest().getParam("bizCode");
        String pKey = (String)context.getRequest().getParam("pKey");

        //TODO 入参检查

        if(StringUtils.isBlank(bizCode)){
            return new AppResponse(ResponseCode.PARAM_E_PARAM_MISSING, "bizCode is null");
        }

        if(StringUtils.isBlank(pKey)){
            return new AppResponse(ResponseCode.PARAM_E_PARAM_MISSING, "pKey is null");
        }

        try{
            int opNum = bizPropertyManager.deleteBizProperty(bizCode, pKey);

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
        return ActionEnum.DELETE_BIZ_PROPERTY.getActionName();
    }
}
