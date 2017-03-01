package com.mockuai.appcenter.core.service.action.impl;

import com.mockuai.appcenter.common.api.AppResponse;
import com.mockuai.appcenter.common.constant.ActionEnum;
import com.mockuai.appcenter.common.constant.AppTypeEnum;
import com.mockuai.appcenter.common.constant.ResponseCode;
import com.mockuai.appcenter.common.domain.AppInfoDTO;
import com.mockuai.appcenter.common.domain.AppPropertyDTO;
import com.mockuai.appcenter.common.util.BizUtil;
import com.mockuai.appcenter.core.domain.AppInfoDO;
import com.mockuai.appcenter.core.domain.BizInfoDO;
import com.mockuai.appcenter.core.exception.AppException;
import com.mockuai.appcenter.core.manager.AppInfoManager;
import com.mockuai.appcenter.core.manager.AppPropertyManager;
import com.mockuai.appcenter.core.manager.BizInfoManager;
import com.mockuai.appcenter.core.manager.CacheManager;
import com.mockuai.appcenter.core.service.RequestContext;
import com.mockuai.appcenter.core.service.action.Action;
import com.mockuai.appcenter.core.util.ModelUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by zengzhangqiang on 6/7/15.
 */
public class UpdateAppInfo implements Action{

    @Resource
    private AppInfoManager appInfoManager;
    @Resource
    private CacheManager cacheManager;

    @Override
    public AppResponse execute(RequestContext context) {
        AppInfoDTO appInfoDTO = (AppInfoDTO)context.getRequest().getParam("appInfoDTO");

        //TODO 入参检查

        if(appInfoDTO == null){
            return new AppResponse(ResponseCode.PARAM_E_PARAM_MISSING, "appInfoDTO is null");
        }

        try{
            //更新应用信息
            AppInfoDO appInfoDO = ModelUtil.convertToAppInfoDO(appInfoDTO);
            int opNum = appInfoManager.updateAppInfo(appInfoDO);

            //清空缓存
            cacheManager.delete(genAppCacheKeyByAppKey(appInfoDO.getAppKey()));
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

    private String genAppCacheKeyByAppKey(String appKey){
        if(appKey == null){
            return null;
        }

        return "appCache_"+appKey;
    }

    @Override
    public String getName() {
        return ActionEnum.UPDATE_APP_INFO.getActionName();
    }
}
