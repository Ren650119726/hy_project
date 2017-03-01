package com.mockuai.distributioncenter.core.service.action.seller;

import com.mockuai.distributioncenter.common.api.DistributionResponse;
import com.mockuai.distributioncenter.common.api.Request;
import com.mockuai.distributioncenter.common.constant.ActionEnum;
import com.mockuai.distributioncenter.common.constant.ResponseCode;
import com.mockuai.distributioncenter.common.domain.dto.SellerConfigDTO;
import com.mockuai.distributioncenter.common.domain.dto.SellerDTO;
import com.mockuai.distributioncenter.common.domain.qto.SellerConfigQTO;
import com.mockuai.distributioncenter.common.domain.qto.SellerQTO;
import com.mockuai.distributioncenter.common.domain.qto.SellerRelationshipQTO;
import com.mockuai.distributioncenter.core.exception.DistributionException;
import com.mockuai.distributioncenter.core.manager.SellerConfigManager;
import com.mockuai.distributioncenter.core.manager.SellerManager;
import com.mockuai.distributioncenter.core.manager.SellerRelationshipManager;
import com.mockuai.distributioncenter.core.manager.VirtualWealthManager;
import com.mockuai.distributioncenter.core.service.RequestContext;
import com.mockuai.distributioncenter.core.service.action.TransAction;
import com.mockuai.virtualwealthcenter.common.constant.WealthType;
import com.mockuai.virtualwealthcenter.common.domain.dto.WealthAccountDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by duke on 15/10/21.
 */
@Service
public class GetPosteritySellerAction extends TransAction {
    private static final Logger log = LoggerFactory.getLogger(GetPosteritySellerAction.class);

    @Autowired
    private SellerManager sellerManager;

    @Autowired
    private SellerRelationshipManager sellerRelationshipManager;

    @Autowired
    private SellerConfigManager sellerConfigManager;

    @Autowired
    private VirtualWealthManager virtualWealthManager;

    @Override
    public DistributionResponse doTransaction(RequestContext context) throws DistributionException {
        Request request = context.getRequest();
        SellerQTO sellerQTO = (SellerQTO) request.getParam("sellerQTO");
        String appKey = (String) context.get("appKey");

        if (sellerQTO == null) {
            log.error("sellerQTO is null");
            throw new DistributionException(ResponseCode.PARAMETER_NULL);
        }

        SellerRelationshipQTO relationshipQTO = new SellerRelationshipQTO();
        relationshipQTO.setParentIds(Collections.singletonList(sellerQTO.getParentId()));
        relationshipQTO.setStatus(1);
        List<Long> userIds = sellerRelationshipManager.queryPosterityUserIds(relationshipQTO);

        List<SellerDTO> sellerDTOs;
        Long totalCount;
        if (!userIds.isEmpty()) {
            sellerQTO.setUserIds(userIds);
            sellerDTOs = sellerManager.query(sellerQTO);
            totalCount = sellerManager.totalCount(sellerQTO);

            // 获得等级信息
            List<SellerConfigDTO> sellerConfigDTOs = sellerConfigManager.querySellerConfig(new SellerConfigQTO());
            Map<Long, String> levelMap = new HashMap<Long, String>();
            for (SellerConfigDTO configDTO : sellerConfigDTOs) {
                levelMap.put(configDTO.getId(), configDTO.getLevelName());
            }

            for (SellerDTO seller : sellerDTOs) {
                seller.setLevelName(levelMap.get(seller.getLevelId()));
            }

            userIds = new ArrayList<Long>();
            for (SellerDTO sellerDTO : sellerDTOs) {
                userIds.add(sellerDTO.getUserId());
            }

            if (!userIds.isEmpty()) {
                // 获得邀请人数
                List<Map<String, Long>> totalCountMap = sellerRelationshipManager.queryTotalCountByUserIds(userIds);
                Map<Long, Long> countMap = new HashMap<Long, Long>();
                for (Map<String, Long> map : totalCountMap) {
                    countMap.put(map.get("userId"), map.get("count"));
                }
                for (SellerDTO sellerDTO : sellerDTOs) {
                    sellerDTO.setInviterCount(countMap.get(sellerDTO.getUserId()));
                }
                // 获得总销售额
                List<WealthAccountDTO> wealthAccountDTOs =
                        virtualWealthManager.queryTotalVirtualWealth(userIds, WealthType.VIRTUAL_WEALTH.getValue(), appKey);
                Map<Long, WealthAccountDTO> map = new HashMap<Long, WealthAccountDTO>();

                for (WealthAccountDTO wealthAccountDTO : wealthAccountDTOs) {
                    map.put(wealthAccountDTO.getUserId(), wealthAccountDTO);
                }
                for (SellerDTO sellerDTO : sellerDTOs) {
                    WealthAccountDTO wealthAccountDTO = map.get(sellerDTO.getUserId());
                    if (wealthAccountDTO != null) {
                        sellerDTO.setInCome(wealthAccountDTO.getTotal());
                    } else {
                        sellerDTO.setInCome(0L);
                    }
                }
            }
        } else {
            sellerDTOs = Collections.EMPTY_LIST;
            totalCount = 0L;
        }

        return new DistributionResponse(sellerDTOs, totalCount);
    }

    @Override
    public String getName() {
        return ActionEnum.GET_POSTERITY_SELLER.getActionName();
    }
}
