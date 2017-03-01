package com.mockuai.appcenter.core.service.action.impl;

import com.mockuai.appcenter.common.api.AppResponse;
import com.mockuai.appcenter.common.constant.ActionEnum;
import com.mockuai.appcenter.common.constant.ResponseCode;
import com.mockuai.appcenter.common.constant.ValueTypeEnum;
import com.mockuai.appcenter.core.domain.BizPropertyDO;
import com.mockuai.appcenter.core.exception.AppException;
import com.mockuai.appcenter.core.manager.BizPropertyManager;
import com.mockuai.appcenter.core.manager.CacheManager;
import com.mockuai.appcenter.core.service.RequestContext;
import com.mockuai.appcenter.core.service.action.Action;
import com.mockuai.appcenter.core.util.CacheHelper;
import org.apache.commons.lang.StringUtils;

import javax.annotation.Resource;

/**
 * Created by zengzhangqiang on 6/8/15.
 */
public class UpdateBizProperty implements Action{
    @Resource
    private BizPropertyManager bizPropertyManager;
    @Resource
    private CacheManager cacheManager;

    @Override
    public AppResponse execute(RequestContext context) {
        String bizCode = (String)context.getRequest().getParam("bizCode");
        String pKey = (String)context.getRequest().getParam("pKey");
        String value = (String)context.getRequest().getParam("value");
        ValueTypeEnum valueTypeEnum = (ValueTypeEnum)context.getRequest().getParam("valueType");

        //TODO 入参检查

        if(StringUtils.isBlank(bizCode)){
            return new AppResponse(ResponseCode.PARAM_E_PARAM_MISSING, "bizCode is null");
        }

        if(StringUtils.isBlank(pKey)){
            return new AppResponse(ResponseCode.PARAM_E_PARAM_MISSING, "pKey is null");
        }

        try{
            BizPropertyDO bizPropertyDO = bizPropertyManager.getBizProperty(bizCode, pKey);

            if(bizPropertyDO == null){
                return new AppResponse(ResponseCode.BIZ_E_BIZ_PROPERTY_NOT_EXIST,
                        "bizProperty is not exist");
            }

            bizPropertyDO.setValue(value);
            bizPropertyDO.setValueType(valueTypeEnum.getValue());
            int opNum = bizPropertyManager.updateBizProperty(bizPropertyDO);

            //清空bizInfo缓存
            cacheManager.delete(CacheHelper.genBizCacheKeyByBizCode(bizCode));

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
        return ActionEnum.UPDATE_BIZ_PROPERTY.getActionName();
    }
}
