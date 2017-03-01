package com.mockuai.distributioncenter.core.service.action.sellerlevelapply;

import com.mockuai.distributioncenter.common.api.DistributionResponse;
import com.mockuai.distributioncenter.common.api.Request;
import com.mockuai.distributioncenter.common.constant.ActionEnum;
import com.mockuai.distributioncenter.common.constant.ResponseCode;
import com.mockuai.distributioncenter.common.domain.dto.SellerConfigDTO;
import com.mockuai.distributioncenter.common.domain.dto.SellerLevelApplyDTO;
import com.mockuai.distributioncenter.common.domain.qto.SellerConfigQTO;
import com.mockuai.distributioncenter.common.domain.qto.SellerLevelApplyQTO;
import com.mockuai.distributioncenter.core.exception.DistributionException;
import com.mockuai.distributioncenter.core.manager.SellerConfigManager;
import com.mockuai.distributioncenter.core.manager.SellerLevelApplyManager;
import com.mockuai.distributioncenter.core.service.RequestContext;
import com.mockuai.distributioncenter.core.service.action.Action;

import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

import java.util.List;

/**
 * Created by yeliming on 16/5/24.
 */
@Controller
public class AddSellerLevelApplyAction implements Action {
    @Resource
    private SellerLevelApplyManager sellerLevelApplyManager; 

    @Override
    public DistributionResponse execute(RequestContext context) throws DistributionException {
        Request request = context.getRequest();
        SellerLevelApplyDTO sellerLevelApplyDTO = (SellerLevelApplyDTO) request.getParam("sellerLevelApplyDTO");
        if (sellerLevelApplyDTO == null) {
            throw new DistributionException(ResponseCode.PARAMETER_NULL, "sellerLevelApplyDTO is null");
        }
        SellerLevelApplyDTO sellerLevelApplyDTOs = new SellerLevelApplyDTO();
        sellerLevelApplyDTOs.setApplicantId(sellerLevelApplyDTO.getApplicantId());
        sellerLevelApplyDTOs.setStatus(0);
        SellerLevelApplyDTO sellerLevelApply = sellerLevelApplyManager.getSellerLevelApply(sellerLevelApplyDTOs);
        if(sellerLevelApply == null){
        	sellerLevelApplyManager.addSellerLevelApply(sellerLevelApplyDTO);
        }else{
        	throw new DistributionException("sellerLevelApply existence");
        }
        return new DistributionResponse(true);
    }

    @Override
    public String getName() {
        return ActionEnum.ADD_SELLER_LEVEL_APPLY.getActionName();
    }
}
