package com.mockuai.appcenter.core.service.action.impl;

import com.mockuai.appcenter.common.api.AppResponse;
import com.mockuai.appcenter.common.constant.ActionEnum;
import com.mockuai.appcenter.common.constant.ResponseCode;
import com.mockuai.appcenter.common.domain.AppInfoDTO;
import com.mockuai.appcenter.common.domain.AppPropertyDTO;
import com.mockuai.appcenter.core.domain.AppInfoDO;
import com.mockuai.appcenter.core.domain.AppPropertyDO;
import com.mockuai.appcenter.core.exception.AppException;
import com.mockuai.appcenter.core.manager.AppInfoManager;
import com.mockuai.appcenter.core.manager.AppPropertyManager;
import com.mockuai.appcenter.core.service.RequestContext;
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
public class GetAppInfoByType implements Action{
    @Resource
    private AppInfoManager appInfoManager;
    @Resource
    private AppPropertyManager appPropertyManager;

    @Override
    public AppResponse execute(RequestContext context) {
        String bizCode = (String)context.getRequest().getParam("bizCode");
        Integer appType = (Integer)context.getRequest().getParam("appType");

        //TODO 入参检查

        if(StringUtils.isBlank(bizCode)){
            return new AppResponse(ResponseCode.PARAM_E_PARAM_MISSING, "bizCode is null");
        }

        if(appType == null){
            return new AppResponse(ResponseCode.PARAM_E_PARAM_MISSING, "appType is null");
        }


        try{
            AppInfoDO appInfoDO = appInfoManager.getAppInfoByType(bizCode, appType);

            if(appInfoDO == null){
                return new AppResponse(ResponseCode.BIZ_E_APP_INFO_NOT_EXIST);
            }

            List<AppPropertyDO> appPropertyDOs = appPropertyManager.getAppPropertyList(appInfoDO.getId());

            AppInfoDTO appInfoDTO = ModelUtil.convertToAppInfoDTO(appInfoDO);
            List<AppPropertyDTO> appPropertyDTOs = ModelUtil.convertToAppPropertyDTOList(appPropertyDOs);
            Map<String, AppPropertyDTO> appPropertyDTOMap = new HashMap<String, AppPropertyDTO>();
            if(appPropertyDTOs != null){
                for(AppPropertyDTO appPropertyDTO: appPropertyDTOs){
                    appPropertyDTOMap.put(appPropertyDTO.getpKey(), appPropertyDTO);
                }
            }
            appInfoDTO.setAppPropertyMap(appPropertyDTOMap);
            return new AppResponse(appInfoDTO);
        }catch(AppException e){
            //TODO error handle
            return new AppResponse(e.getResponseCode(), e.getMessage());
        }
    }

    @Override
    public String getName() {
        return ActionEnum.GET_APP_INFO_BY_TYPE.getActionName();
    }
}
