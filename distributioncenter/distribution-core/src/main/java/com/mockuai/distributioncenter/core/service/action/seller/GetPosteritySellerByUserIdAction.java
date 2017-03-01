package com.mockuai.distributioncenter.core.service.action.seller;

import com.mockuai.distributioncenter.common.api.DistributionResponse;
import com.mockuai.distributioncenter.common.api.Request;
import com.mockuai.distributioncenter.common.constant.ActionEnum;
import com.mockuai.distributioncenter.common.constant.ResponseCode;
import com.mockuai.distributioncenter.common.domain.dto.SellerConfigDTO;
import com.mockuai.distributioncenter.common.domain.dto.SellerDTO;
import com.mockuai.distributioncenter.common.domain.dto.SellerRelationshipDTO;
import com.mockuai.distributioncenter.common.domain.qto.SellerConfigQTO;
import com.mockuai.distributioncenter.common.domain.qto.SellerRelationshipQTO;
import com.mockuai.distributioncenter.core.exception.DistributionException;
import com.mockuai.distributioncenter.core.manager.SellerConfigManager;
import com.mockuai.distributioncenter.core.manager.SellerManager;
import com.mockuai.distributioncenter.core.manager.SellerRelationshipManager;
import com.mockuai.distributioncenter.core.service.RequestContext;
import com.mockuai.distributioncenter.core.service.action.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by duke on 16/5/19.
 */
@Service
public class GetPosteritySellerByUserIdAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(GetPosteritySellerByUserIdAction.class);

    @Autowired
    private SellerManager sellerManager;

    @Autowired
    private SellerRelationshipManager sellerRelationshipManager;

    @Autowired
    private SellerConfigManager sellerConfigManager;

    @Override
    public DistributionResponse execute(RequestContext context) throws DistributionException {
        Request request = context.getRequest();
        Long userId = (Long) request.getParam("userId");

        if (userId == null) {
            log.error("userId is null");
            throw new DistributionException(ResponseCode.PARAMETER_NULL, "userId is null");
        }

        SellerRelationshipQTO relationshipQTO = new SellerRelationshipQTO();
        relationshipQTO.setParentIds(Collections.singletonList(userId));
        relationshipQTO.setStatus(1);
        List<Long> userIds = sellerRelationshipManager.queryPosterityUserIds(relationshipQTO);
        List<SellerDTO> sellerDTOs = sellerManager.queryByUserIds(userIds);
        List<SellerConfigDTO> configDTOs = sellerConfigManager.querySellerConfig(new SellerConfigQTO());
        Map<Long, String> levelMap = new HashMap<Long, String>();
        for (SellerConfigDTO configDTO : configDTOs) {
            levelMap.put(configDTO.getId(), configDTO.getLevelName());
        }
        for (SellerDTO seller : sellerDTOs) {
            seller.setLevelName(levelMap.get(seller.getLevelId()));
        }

        return new DistributionResponse(sellerDTOs,sellerDTOs.size());
    }

    @Override
    public String getName() {
        return ActionEnum.GET_POSTERITY_BY_USER_ID.getActionName();
    }
}
