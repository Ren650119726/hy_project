package com.mockuai.distributioncenter.core.service.action.fans;

import com.mockuai.distributioncenter.common.api.DistributionResponse;
import com.mockuai.distributioncenter.common.api.Request;
import com.mockuai.distributioncenter.common.constant.ActionEnum;
import com.mockuai.distributioncenter.common.constant.ResponseCode;
import com.mockuai.distributioncenter.common.domain.dto.FansDistDTO;
import com.mockuai.distributioncenter.common.domain.dto.MyFansDTO;
import com.mockuai.distributioncenter.common.domain.qto.FansDistQTO;
import com.mockuai.distributioncenter.common.domain.qto.MyFansQTO;
import com.mockuai.distributioncenter.core.api.RequestAdapter;
import com.mockuai.distributioncenter.core.exception.DistributionException;
import com.mockuai.distributioncenter.core.manager.MyFansManager;
import com.mockuai.distributioncenter.core.service.RequestContext;
import com.mockuai.distributioncenter.core.service.action.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Created by hsq
 */
@Service
public class QueryFansAndDistAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(QueryFansAndDistAction.class);

    @Autowired
    private MyFansManager myFansManager;

    @Override
    public DistributionResponse execute(RequestContext context) throws DistributionException {
        RequestAdapter request = context.getRequestAdapter();

        String appKey = (String) request.getParam("appKey");

        FansDistQTO fansDistQTO = (FansDistQTO) request.getParam("fansDistQTO");


        if (fansDistQTO == null) {
            log.error("fansDistQTO is null");
            throw new DistributionException(ResponseCode.PARAMETER_NULL, "fansDistQTO is null");
        }

        if (appKey == null) {
            log.error("appKey is null");
            throw new DistributionException(ResponseCode.PARAMETER_NULL, "appKey is null");
        }

        List<FansDistDTO> fansDistDTOS = myFansManager.queryDistListFromFans(fansDistQTO,appKey);
        Long totalCount = myFansManager.totalCountByUserId(fansDistQTO.getInviterId(), appKey);

        return new DistributionResponse(fansDistDTOS,totalCount);
    }

    @Override
    public String getName() {
        return ActionEnum.QUERY_FANS_AND_DIST.getActionName();
    }
}
