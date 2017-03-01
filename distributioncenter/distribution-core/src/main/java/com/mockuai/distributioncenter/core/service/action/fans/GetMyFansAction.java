package com.mockuai.distributioncenter.core.service.action.fans;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mockuai.distributioncenter.common.api.DistributionResponse;
import com.mockuai.distributioncenter.common.constant.ActionEnum;
import com.mockuai.distributioncenter.common.constant.ResponseCode;
import com.mockuai.distributioncenter.common.domain.dto.MyFansDTO;
import com.mockuai.distributioncenter.common.domain.qto.MyFansQTO;
import com.mockuai.distributioncenter.core.api.RequestAdapter;
import com.mockuai.distributioncenter.core.exception.DistributionException;
import com.mockuai.distributioncenter.core.manager.MyFansManager;
import com.mockuai.distributioncenter.core.service.RequestContext;
import com.mockuai.distributioncenter.core.service.action.Action;


/**
 * Created by lizg on 2016/9/1.
 */
@Service
public class GetMyFansAction implements Action {

    private static final Logger log = LoggerFactory.getLogger(GetMyFansAction.class);


    @Autowired
    private MyFansManager myFansManager;

    @Override
    public DistributionResponse execute(RequestContext context) throws DistributionException {
        RequestAdapter request = context.getRequestAdapter();

        String appKey = (String) request.getParam("appKey");

        MyFansQTO myFansQTO = (MyFansQTO)request.getParam("myFansQTO");
        log.info("user_id:{}",myFansQTO.getUserId());
        log.info("fans_id{}",myFansQTO.getFansId());
        if (appKey == null) {
            log.error("appKey is null");
            throw new DistributionException(ResponseCode.PARAMETER_NULL, "appKey is null");
        }

        List<MyFansDTO> myFansDTO = myFansManager.getMyFans(myFansQTO,appKey);

        Long totalCount = myFansManager.totalCount(myFansQTO,appKey);

        return new DistributionResponse(myFansDTO,totalCount);
    }

    @Override
    public String getName() {
        return ActionEnum.GET_MY_FANS.getActionName();
    }
}
