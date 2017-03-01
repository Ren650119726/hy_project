package com.mockuai.appcenter.core.service.action.impl;

import com.mockuai.appcenter.common.api.AppResponse;
import com.mockuai.appcenter.common.constant.ActionEnum;
import com.mockuai.appcenter.common.constant.AppTypeEnum;
import com.mockuai.appcenter.common.constant.ResponseCode;
import com.mockuai.appcenter.common.domain.AppInfoDTO;
import com.mockuai.appcenter.common.domain.AppPropertyDTO;
import com.mockuai.appcenter.common.util.BizUtil;
import com.mockuai.appcenter.common.util.JsonUtil;
import com.mockuai.appcenter.core.domain.AppInfoDO;
import com.mockuai.appcenter.core.domain.BizInfoDO;
import com.mockuai.appcenter.core.exception.AppException;
import com.mockuai.appcenter.core.manager.AppInfoManager;
import com.mockuai.appcenter.core.manager.AppPropertyManager;
import com.mockuai.appcenter.core.manager.BizInfoManager;
import com.mockuai.appcenter.core.service.RequestContext;
import com.mockuai.appcenter.core.service.action.Action;
import com.mockuai.appcenter.core.util.ModelUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by zengzhangqiang on 6/7/15.
 */
public class AddAppInfo implements Action{

    @Resource
    private AppInfoManager appInfoManager;
    @Resource
    private AppPropertyManager appPropertyManager;
    @Resource
    private BizInfoManager bizInfoManager;

    @Override
    public AppResponse execute(RequestContext context) {
        AppInfoDTO appInfoDTO = (AppInfoDTO)context.getRequest().getParam("appInfoDTO");

        //TODO 入参检查

        if(appInfoDTO == null){
            return new AppResponse(ResponseCode.PARAM_E_PARAM_MISSING, "appInfoDTO is null");
        }

        if(StringUtils.isBlank(appInfoDTO.getBizCode())){
            return new AppResponse(ResponseCode.PARAM_E_PARAM_MISSING, "bizCode is null");
        }

        //应用类型检查
        if(AppTypeEnum.getAppType(appInfoDTO.getAppType()) == null){
            return new AppResponse(ResponseCode.PARAM_E_PARAM_INVALID, "appType is invalid");
        }


        try{
            //判断所属bizInfo是否有效
            String bizCode = appInfoDTO.getBizCode();
            BizInfoDO bizInfoDO = bizInfoManager.getBizInfo(bizCode);
            if(bizInfoDO == null){
                return new AppResponse(ResponseCode.BIZ_E_BIZ_INFO_NOT_EXIST, "bizCode is invalid");
            }

            AppInfoDO appInfoDO = ModelUtil.convertToAppInfoDO(appInfoDTO);

            //生成appKey和appPwd
            String appKey = genAppKey(appInfoDTO.getBizCode());
            String appPwd = genAppPwd(appKey);
            appInfoDO.setAppKey(appKey);
            appInfoDO.setAppPwd(appPwd);
            appInfoDO.setNamespaceId(0);//默认全部设置命名空间id为0，放到默认分类下去；后续需要扩展再重构这里的逻辑

            //如果应用名为空，则使用系统生成的应用名
            if(StringUtils.isBlank(appInfoDO.getAppName())){
                appInfoDO.setAppName(BizUtil.genAppName(bizInfoDO.getBizName(),
                        AppTypeEnum.getAppType(appInfoDO.getAppType())));
            }

            Long appInfoId = appInfoManager.addAppInfo(appInfoDO);

            Map<String, AppPropertyDTO> appPropertyDTOMap = appInfoDTO.getAppPropertyMap();
            if(appPropertyDTOMap != null){
                //TODO 优化成批量插入
                for(AppPropertyDTO appPropertyDTO: appPropertyDTOMap.values()){
                    appPropertyManager.addAppProperty(ModelUtil.convertToAppPropertyDO(appPropertyDTO));
                }
            }

            appInfoDTO.setId(appInfoId);
            appInfoDTO.setAppKey(appKey);
            appInfoDTO.setAppPwd(appPwd);

            return new AppResponse(appInfoDTO);
        }catch(AppException e){
            //TODO error handle
            return new AppResponse(e.getResponseCode(), e.getMessage());
        }

    }

    @Override
    public String getName() {
        return ActionEnum.ADD_APP_INFO.getActionName();
    }

    private String genAppKey(String bizCode){
        if(StringUtils.isBlank(bizCode)){
            return null;
        }
        String appKey = DigestUtils.md5Hex(bizCode+"_"+System.currentTimeMillis());
        return appKey;
    }

    private String genAppPwd(String appKey){
        if(StringUtils.isBlank(appKey)){
            return null;
        }
        String appPwd = DigestUtils.md5Hex(appKey+"_"+System.currentTimeMillis());
        return appPwd;

    }

    static class ModelTest{
        private String testName;
        private Object extend;

        public String getTestName() {
            return testName;
        }

        public void setTestName(String testName) {
            this.testName = testName;
        }

        public Object getExtend() {
            return extend;
        }

        public void setExtend(Object extend) {
            this.extend = extend;
        }
    }

    public static void main(String[] args){
        AddAppInfo addAppInfo = new AddAppInfo();
        String appKey = addAppInfo.genAppKey("123");
        String appPwd = addAppInfo.genAppPwd(appKey);
        System.out.println("appKey:"+appKey);
        System.out.println("appPwd:"+appPwd);

    }
}
