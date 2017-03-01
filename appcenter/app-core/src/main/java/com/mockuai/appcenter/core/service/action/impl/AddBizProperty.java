package com.mockuai.appcenter.core.service.action.impl;

import com.mockuai.appcenter.common.api.AppResponse;
import com.mockuai.appcenter.common.constant.ActionEnum;
import com.mockuai.appcenter.common.constant.ResponseCode;
import com.mockuai.appcenter.common.domain.AppPropertyDTO;
import com.mockuai.appcenter.common.domain.BizPropertyDTO;
import com.mockuai.appcenter.core.exception.AppException;
import com.mockuai.appcenter.core.manager.AppPropertyManager;
import com.mockuai.appcenter.core.manager.BizPropertyManager;
import com.mockuai.appcenter.core.manager.CacheManager;
import com.mockuai.appcenter.core.service.RequestContext;
import com.mockuai.appcenter.core.service.action.Action;
import com.mockuai.appcenter.core.util.CacheHelper;
import com.mockuai.appcenter.core.util.ModelUtil;

import javax.annotation.Resource;

/**
 * Created by zengzhangqiang on 6/8/15.
 */
public class AddBizProperty implements Action{

    @Resource
    private BizPropertyManager bizPropertyManager;

    @Resource
    private CacheManager cacheManager;

    @Override
    public AppResponse execute(RequestContext context) {
        BizPropertyDTO bizPropertyDTO = (BizPropertyDTO)context.getRequest().getParam("bizPropertyDTO");

        //TODO 入参检查

        if(bizPropertyDTO == null){
            return new AppResponse(ResponseCode.PARAM_E_PARAM_MISSING, "bizPropertyDTO is null");
        }

        try{
            //新增bizProperty
            Long bizPropertyId = bizPropertyManager.addBizProperty(ModelUtil.convertToBizPropertyDO(bizPropertyDTO));

            //清空bizInfo缓存
            cacheManager.delete(CacheHelper.genBizCacheKeyByBizCode(bizPropertyDTO.getBizCode()));

            bizPropertyDTO.setId(bizPropertyId);

            return new AppResponse(bizPropertyDTO);
        }catch(AppException e){
            //TODO error handle
            return new AppResponse(e.getResponseCode(), e.getMessage());
        }
    }

    @Override
    public String getName() {
        return ActionEnum.ADD_BIZ_PROPERTY.getActionName();
    }
}
