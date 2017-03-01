package com.mockuai.appcenter.core.service.action.impl;

import com.mockuai.appcenter.common.api.AppResponse;
import com.mockuai.appcenter.common.constant.ActionEnum;
import com.mockuai.appcenter.common.constant.ResponseCode;
import com.mockuai.appcenter.common.domain.BizInfoDTO;
import com.mockuai.appcenter.common.domain.BizPropertyDTO;
import com.mockuai.appcenter.core.domain.BizInfoDO;
import com.mockuai.appcenter.core.domain.BizPropertyDO;
import com.mockuai.appcenter.core.exception.AppException;
import com.mockuai.appcenter.core.manager.BizInfoManager;
import com.mockuai.appcenter.core.manager.BizPropertyManager;
import com.mockuai.appcenter.core.manager.CacheManager;
import com.mockuai.appcenter.core.service.RequestContext;
import com.mockuai.appcenter.core.service.action.Action;
import com.mockuai.appcenter.core.util.CacheHelper;
import com.mockuai.appcenter.core.util.ModelUtil;
import org.apache.commons.lang.StringUtils;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by zengzhangqiang on 6/8/15.
 */
public class UpdateBizInfo implements Action{
    @Resource
    private BizInfoManager bizInfoManager;
    @Resource
    private BizPropertyManager bizPropertyManager;
    @Resource
    private CacheManager cacheManager;

    @Override
    public AppResponse execute(RequestContext context) {
        BizInfoDTO bizInfoDTO = (BizInfoDTO)context.getRequest().getParam("bizInfoDTO");

        if(bizInfoDTO == null){
            return new AppResponse(ResponseCode.PARAM_E_PARAM_MISSING, "bizInfoDTO is null");
        }

        //TODO 入参检查
        Map<String, BizPropertyDTO> bizPropertyDTOMap = bizInfoDTO.getBizPropertyMap();
        try{
            //更新企业信息
            BizInfoDO bizInfoDO = ModelUtil.convertToBizInfoDO(bizInfoDTO);
            int opNum = bizInfoManager.updateBizInfo(bizInfoDO);
            //TODO 性能优化，重构成批量更新
            for(BizPropertyDTO bizPropertyDTO: bizPropertyDTOMap.values()){
                if(StringUtils.isBlank(bizPropertyDTO.getBizCode()) || StringUtils.isBlank(bizPropertyDTO.getpKey())){
                    continue;
                }

                if(StringUtils.isBlank(bizPropertyDTO.getValue())){
                    continue;
                }

                BizPropertyDO bizPropertyDO =
                        bizPropertyManager.getBizProperty(bizPropertyDTO.getBizCode(), bizPropertyDTO.getpKey());


                if(bizPropertyDO != null){
                    bizPropertyManager.updateBizProperty(ModelUtil.convertToBizPropertyDO(bizPropertyDTO));
                }else {
                    bizPropertyManager.addBizProperty(ModelUtil.convertToBizPropertyDO(bizPropertyDTO));
                }
            }

            //清空bizInfo缓存
            cacheManager.delete(CacheHelper.genBizCacheKeyByBizCode(bizInfoDO.getBizCode()));

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
        return ActionEnum.UPDATE_BIZ_INFO.getActionName();
    }
}
