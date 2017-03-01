package com.mockuai.appcenter.core.service.action.impl;

import com.mockuai.appcenter.common.api.AppResponse;
import com.mockuai.appcenter.common.constant.ActionEnum;
import com.mockuai.appcenter.common.constant.ResponseCode;
import com.mockuai.appcenter.common.domain.AppInfoDTO;
import com.mockuai.appcenter.common.domain.AppPropertyDTO;
import com.mockuai.appcenter.common.domain.BizInfoDTO;
import com.mockuai.appcenter.common.domain.BizPropertyDTO;
import com.mockuai.appcenter.core.exception.AppException;
import com.mockuai.appcenter.core.manager.BizInfoManager;
import com.mockuai.appcenter.core.manager.BizPropertyManager;
import com.mockuai.appcenter.core.service.RequestContext;
import com.mockuai.appcenter.core.service.action.Action;
import com.mockuai.appcenter.core.util.ModelUtil;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by zengzhangqiang on 6/8/15.
 */
public class AddBizInfo implements Action{
    @Resource
    private BizInfoManager bizInfoManager;
    @Resource
    private BizPropertyManager bizPropertyManager;

    @Override
    public AppResponse execute(RequestContext context) {
        BizInfoDTO bizInfoDTO = (BizInfoDTO)context.getRequest().getParam("bizInfoDTO");

        if(bizInfoDTO == null){
            return new AppResponse(ResponseCode.PARAM_E_PARAM_MISSING, "bizInfoDTO is null");
        }

        //TODO 入参检查

        Map<String, BizPropertyDTO> bizPropertyDTOMap = bizInfoDTO.getBizPropertyMap();
        try{
            Long bizInfoId = bizInfoManager.addBizInfo(ModelUtil.convertToBizInfoDO(bizInfoDTO));

            //TODO 优化成批量插入
            if(bizPropertyDTOMap != null){
                for(BizPropertyDTO bizPropertyDTO: bizPropertyDTOMap.values()){
                    bizPropertyManager.addBizProperty(ModelUtil.convertToBizPropertyDO(bizPropertyDTO));
                }
            }

            bizInfoDTO.setId(bizInfoId);

            return new AppResponse(bizInfoDTO);
        }catch(AppException e){
            //TODO error handle
            return new AppResponse(e.getResponseCode(), e.getMessage());
        }

    }

    @Override
    public String getName() {
        return ActionEnum.ADD_BIZ_INFO.getActionName();
    }
}
