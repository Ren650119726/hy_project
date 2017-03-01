package com.mockuai.appcenter.core.service.action.impl;

import com.mockuai.appcenter.common.api.AppResponse;
import com.mockuai.appcenter.common.api.BaseRequest;
import com.mockuai.appcenter.common.api.Request;
import com.mockuai.appcenter.common.constant.ActionEnum;
import com.mockuai.appcenter.common.constant.AppTypeEnum;
import com.mockuai.appcenter.common.constant.BizPropertyKey;
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
 * Created by zengzhangqiang on 9/21/15.
 */
public class GetBizInfoByDomain implements Action {
    @Resource
    private BizInfoManager bizInfoManager;
    @Resource
    private BizPropertyManager bizPropertyManager;
    @Resource
    private AppInfoManager appInfoManager;
    @Resource
    private RequestDispatcher requestDispatcher;

    @Override
    public AppResponse execute(RequestContext context) {
        String domainName = (String)context.getRequest().getParam("domainName");

        //TODO 入参检查
        if(StringUtils.isBlank(domainName)){
            return new AppResponse(ResponseCode.PARAM_E_PARAM_MISSING, "domainName is null");
        }

        try{
            AppInfoDO appInfoDO = appInfoManager.getAppInfoByDomain(domainName);
            if(appInfoDO == null){
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

    private BizPropertyDTO genBizProperty(String bizCode, String pKey, String pValue){
        BizPropertyDTO bizPropertyDTO = new BizPropertyDTO();
        bizPropertyDTO.setBizCode(bizCode);
        bizPropertyDTO.setpKey(pKey);
        bizPropertyDTO.setValue(pValue);
        bizPropertyDTO.setValueType(1);//TODO 重构成枚举常量

        return bizPropertyDTO;
    }

    @Override
    public String getName() {
        return ActionEnum.GET_BIZ_INFO_BY_DOMAIN.getActionName();
    }
}
