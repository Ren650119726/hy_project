package com.mockuai.distributioncenter.core.service.action.seller;

import com.mockuai.distributioncenter.common.api.DistributionResponse;
import com.mockuai.distributioncenter.common.api.Request;
import com.mockuai.distributioncenter.common.constant.ActionEnum;
import com.mockuai.distributioncenter.common.constant.ResponseCode;
import com.mockuai.distributioncenter.common.domain.dto.SellerConfigDTO;
import com.mockuai.distributioncenter.common.domain.dto.SellerDTO;
import com.mockuai.distributioncenter.common.domain.dto.SellerRelationshipDTO;
import com.mockuai.distributioncenter.common.domain.qto.SellerConfigQTO;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by duke on 16/5/19.
 */
@Service
public class GetParentSellerByUserIdAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(GetParentSellerByUserIdAction.class);

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

        SellerRelationshipDTO relationshipDTO = sellerRelationshipManager.getByUserId(userId);
        SellerDTO sellerDTO = null;
        
        if(relationshipDTO == null){
        	return new DistributionResponse(sellerDTO);
        }
        
        if (relationshipDTO.getParentId() != 0) {
            sellerDTO = sellerManager.getByUserId(relationshipDTO.getParentId());
            List<SellerConfigDTO> configDTOs = sellerConfigManager.querySellerConfig(new SellerConfigQTO());
            Map<Long, String> levelMap = new HashMap<Long, String>();
            for (SellerConfigDTO configDTO : configDTOs) {
                levelMap.put(configDTO.getId(), configDTO.getLevelName());
            }
            sellerDTO.setLevelName(levelMap.get(sellerDTO.getLevelId()));
        }

        return new DistributionResponse(sellerDTO);
    }

    @Override
    public String getName() {
        return ActionEnum.GET_PARENT_SELLER_BY_USER_ID.getActionName();
    }
}
