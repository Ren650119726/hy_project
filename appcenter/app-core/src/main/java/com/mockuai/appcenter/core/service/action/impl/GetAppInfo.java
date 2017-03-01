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
import com.mockuai.appcenter.core.manager.CacheManager;
import com.mockuai.appcenter.core.service.RequestContext;
import com.mockuai.appcenter.core.service.action.Action;
import com.mockuai.appcenter.core.util.ModelUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zengzhangqiang on 6/8/15.
 */
public class GetAppInfo implements Action{
    private static final Logger log = LoggerFactory.getLogger(GetAppInfo.class);

    @Resource
    private AppInfoManager appInfoManager;
    @Resource
    private AppPropertyManager appPropertyManager;
    @Resource
    private CacheManager cacheManager;

    @Override
    public AppResponse execute(RequestContext context) {
        String appKey = (String)context.getRequest().getParam("appKey");

        //TODO 入参检查

        if(StringUtils.isBlank(appKey)){
            return new AppResponse(ResponseCode.PARAM_E_PARAM_MISSING, "appKey is null");
        }

        //查询缓存，缓存命中则直接返回
        try{
            Object appCache = cacheManager.getAndTouch(genAppCacheKeyByAppKey(appKey), 60*60*24);
            if(appCache != null){
                return new AppResponse((AppInfoDTO)appCache);
            }
        }catch(Throwable t){
            log.error("error to get app cache!", t);
        }


        try{
            AppInfoDO appInfoDO = appInfoManager.getAppInfo(appKey);
            List<AppPropertyDO> appPropertyDOs = appPropertyManager.getAppPropertyList(appInfoDO.getId());

            if(appInfoDO == null){
                return new AppResponse(ResponseCode.BIZ_E_APP_INFO_NOT_EXIST);
            }

            AppInfoDTO appInfoDTO = ModelUtil.convertToAppInfoDTO(appInfoDO);
            List<AppPropertyDTO> appPropertyDTOs = ModelUtil.convertToAppPropertyDTOList(appPropertyDOs);
            Map<String, AppPropertyDTO> appPropertyDTOMap = new HashMap<String, AppPropertyDTO>();
            if(appPropertyDTOs != null){
                for(AppPropertyDTO appPropertyDTO: appPropertyDTOs){
                    appPropertyDTOMap.put(appPropertyDTO.getpKey(), appPropertyDTO);
                }
            }
            appInfoDTO.setAppPropertyMap(appPropertyDTOMap);

            //将appInfoDTO进行缓存
            cacheManager.set(genAppCacheKeyByAppKey(appKey), 60*60*24, appInfoDTO);

            return new AppResponse(appInfoDTO);
        }catch(AppException e){
            //TODO error handle
            return new AppResponse(e.getResponseCode(), e.getMessage());
        }
    }

    @Override
    public String getName() {
        return ActionEnum.GET_APP_INFO.getActionName();
    }

    private String genAppCacheKeyByAppKey(String appKey){
        if(appKey == null){
            return null;
        }

        return "appCache_"+appKey;
    }
}
