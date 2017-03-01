package com.mockuai.distributioncenter.core.service.action.seller;

import com.mockuai.distributioncenter.common.api.DistributionResponse;
import com.mockuai.distributioncenter.common.api.Request;
import com.mockuai.distributioncenter.common.constant.ActionEnum;
import com.mockuai.distributioncenter.common.constant.ResponseCode;
import com.mockuai.distributioncenter.common.domain.dto.SellerDTO;
import com.mockuai.distributioncenter.common.domain.dto.TeamSummaryDTO;
import com.mockuai.distributioncenter.common.domain.qto.SellerRelationshipQTO;
import com.mockuai.distributioncenter.core.exception.DistributionException;
import com.mockuai.distributioncenter.core.manager.SellerManager;
import com.mockuai.distributioncenter.core.manager.SellerRelationshipManager;
import com.mockuai.distributioncenter.core.service.RequestContext;
import com.mockuai.distributioncenter.core.service.action.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * Created by duke on 16/5/19.
 */
@Service
public class GetTeamSummaryAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(GetTeamSummaryAction.class);

    @Autowired
    private SellerManager sellerManager;

    @Autowired
    private SellerRelationshipManager sellerRelationshipManager;

    @Override
    public DistributionResponse execute(RequestContext context) throws DistributionException {
        Request request = context.getRequest();
        Long userId = (Long) request.getParam("userId");

        if (userId == null) {
            log.error("userId is null");
            throw new DistributionException(ResponseCode.PARAMETER_NULL, "userId is null");
        }

        TeamSummaryDTO teamSummaryDTO = new TeamSummaryDTO();

        // 获得直接下级人数
        SellerDTO sellerDTO = sellerManager.getByUserId(userId);
        teamSummaryDTO.setLevelOneCount(sellerDTO.getDirectCount());

        // 获得团队总人数
        teamSummaryDTO.setTotalCount(sellerDTO.getGroupCount());

        // 获得二级人数
        SellerRelationshipQTO relationshipQTO = new SellerRelationshipQTO();
        relationshipQTO.setParentIds(Collections.singletonList(userId));
        List<Long> userIds = sellerRelationshipManager.queryPosterityUserIds(relationshipQTO);
        List<SellerDTO> sellerDTOs = sellerManager.queryByUserIds(userIds);
        Long count = 0L;
        for (SellerDTO seller : sellerDTOs) {
            count += seller.getDirectCount();
        }
        teamSummaryDTO.setLevelTwoCount(count);

        // 获得三级人数
        relationshipQTO = new SellerRelationshipQTO();
        relationshipQTO.setParentIds(userIds);
        userIds = sellerRelationshipManager.queryPosterityUserIds(relationshipQTO);
        sellerDTOs = sellerManager.queryByUserIds(userIds);
        count = 0L;
        for (SellerDTO seller : sellerDTOs) {
            count += seller.getDirectCount();
        }
        teamSummaryDTO.setLevelThreeCount(count);
        return new DistributionResponse(teamSummaryDTO);
    }

    @Override
    public String getName() {
        return ActionEnum.GET_TEAM_SUMMARY.getActionName();
    }
}
