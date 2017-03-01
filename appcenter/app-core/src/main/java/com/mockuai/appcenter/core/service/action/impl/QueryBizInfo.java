package com.mockuai.appcenter.core.service.action.impl;

import com.mockuai.appcenter.common.api.AppResponse;
import com.mockuai.appcenter.common.constant.ActionEnum;
import com.mockuai.appcenter.common.constant.AppTypeEnum;
import com.mockuai.appcenter.common.constant.BizPropertyKey;
import com.mockuai.appcenter.common.constant.ResponseCode;
import com.mockuai.appcenter.common.domain.BizInfoDTO;
import com.mockuai.appcenter.common.domain.BizInfoQTO;
import com.mockuai.appcenter.common.domain.BizPropertyDTO;
import com.mockuai.appcenter.core.domain.AppInfoDO;
import com.mockuai.appcenter.core.domain.BizInfoDO;
import com.mockuai.appcenter.core.domain.BizPropertyDO;
import com.mockuai.appcenter.core.exception.AppException;
import com.mockuai.appcenter.core.manager.AppInfoManager;
import com.mockuai.appcenter.core.manager.BizInfoManager;
import com.mockuai.appcenter.core.manager.BizPropertyManager;
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
public class QueryBizInfo implements Action{
    private static final Logger log = LoggerFactory.getLogger(QueryBizInfo.class);

    @Resource
    private BizInfoManager bizInfoManager;
    @Resource
    private AppInfoManager appInfoManager;
    @Resource
    private BizPropertyManager bizPropertyManager;

    @Override
    public AppResponse execute(RequestContext context) {
        Integer offset = (Integer)context.getRequest().getParam("offset");
        Integer count = (Integer)context.getRequest().getParam("count");
        String bizNameLike = (String)context.getRequest().getParam("bizNameLike");

        try{
            BizInfoQTO bizInfoQTO = new BizInfoQTO();

            if(offset == null){
                offset = 0;
            }

            if(count == null || count>100){
                count = 100;
            }

            bizInfoQTO.setOffset(offset);
            bizInfoQTO.setCount(count);
            bizInfoQTO.setBizNameLike(bizNameLike);
            List<BizInfoDO> bizInfoList = bizInfoManager.queryBizInfo(bizInfoQTO);
            long totalCount = bizInfoManager.queryBizInfoCount(bizInfoQTO);
            AppResponse appResponse = new AppResponse(bizInfoList);
            appResponse.setTotalCount(totalCount);
            return appResponse;
        }catch(AppException e){
            //TODO error handle
            return new AppResponse(e.getResponseCode(), e.getMessage());
        }
    }

    @Override
    public String getName() {
        return ActionEnum.QUERY_BIZ_INFO.getActionName();
    }
}
