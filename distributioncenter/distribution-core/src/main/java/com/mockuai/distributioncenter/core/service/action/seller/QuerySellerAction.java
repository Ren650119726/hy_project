package com.mockuai.distributioncenter.core.service.action.seller;

import com.mockuai.distributioncenter.common.api.DistributionResponse;
import com.mockuai.distributioncenter.common.api.Request;
import com.mockuai.distributioncenter.common.constant.ActionEnum;
import com.mockuai.distributioncenter.common.constant.ResponseCode;
import com.mockuai.distributioncenter.common.domain.dto.SellerConfigDTO;
import com.mockuai.distributioncenter.common.domain.dto.SellerDTO;
import com.mockuai.distributioncenter.common.domain.qto.SellerConfigQTO;
import com.mockuai.distributioncenter.common.domain.qto.SellerQTO;
import com.mockuai.distributioncenter.core.exception.DistributionException;
import com.mockuai.distributioncenter.core.manager.SellerConfigManager;
import com.mockuai.distributioncenter.core.manager.SellerManager;
import com.mockuai.distributioncenter.core.service.RequestContext;
import com.mockuai.distributioncenter.core.service.action.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by duke on 16/5/19.
 */
@Service
public class QuerySellerAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(QuerySellerAction.class);

    @Resource
    private SellerManager sellerManager;

    @Resource
    private SellerConfigManager sellerConfigManager;

    @Override
    public DistributionResponse execute(RequestContext context) throws DistributionException {
        Request request = context.getRequest();
        SellerQTO sellerQTO = (SellerQTO) request.getParam("sellerQTO");

        if (sellerQTO == null) {
            log.error("sellerQTO is null");
            throw new DistributionException(ResponseCode.SERVICE_EXCEPTION, "sellerQTO is null");
        }

        List<SellerDTO> sellerDTOs = sellerManager.query(sellerQTO);
        Set<Long> levelIdSet = new HashSet<Long>();
        for (SellerDTO sellerDTO : sellerDTOs) {
            levelIdSet.add(sellerDTO.getLevelId());
        }
        SellerConfigQTO sellerConfigQTO = new SellerConfigQTO();
        sellerConfigQTO.setLevelIds(new ArrayList<Long>(levelIdSet));
        List<SellerConfigDTO> sellerConfigDTOs = sellerConfigManager.querySellerConfig(sellerConfigQTO);
        Map<Long, SellerConfigDTO> filterMap = new HashMap<Long, SellerConfigDTO>();
        for (SellerConfigDTO configDTO : sellerConfigDTOs) {
            filterMap.put(configDTO.getId(), configDTO);
        }
        for (SellerDTO sellerDTO : sellerDTOs) {
            SellerConfigDTO configDTO = filterMap.get(sellerDTO.getLevelId());
            if (configDTO != null) {
                sellerDTO.setLevelName(configDTO.getLevelName());
                sellerDTO.setLevel(configDTO.getLevel());
            }
        }

        return new DistributionResponse(sellerDTOs,sellerQTO.getTotalCount());
    }

    @Override
    public String getName() {
        return ActionEnum.QUERY_SELLER.getActionName();
    }
}
