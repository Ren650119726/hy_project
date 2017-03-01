package com.mockuai.distributioncenter.core.service.action.sellerlevelapply;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

import com.mockuai.distributioncenter.common.api.DistributionResponse;
import com.mockuai.distributioncenter.common.api.Request;
import com.mockuai.distributioncenter.common.constant.ActionEnum;
import com.mockuai.distributioncenter.common.constant.ResponseCode;
import com.mockuai.distributioncenter.common.domain.dto.SellerDTO;
import com.mockuai.distributioncenter.common.domain.dto.SellerLevelApplyDTO;
import com.mockuai.distributioncenter.core.domain.SellerDO;
import com.mockuai.distributioncenter.core.exception.DistributionException;
import com.mockuai.distributioncenter.core.manager.SellerLevelApplyManager;
import com.mockuai.distributioncenter.core.manager.SellerManager;
import com.mockuai.distributioncenter.core.service.RequestContext;
import com.mockuai.distributioncenter.core.service.action.Action;

/**
 * Created by yeliming on 16/5/24.
 */
@Controller
public class GetSellerLevelApplyAction implements Action {
    @Resource
    private SellerLevelApplyManager sellerLevelApplyManager; 
    @Resource
    private SellerManager sellerManager;
    @Override
    public DistributionResponse execute(RequestContext context) throws DistributionException {
        Request request = context.getRequest();
        SellerLevelApplyDTO sellerLevelApplyDTO = (SellerLevelApplyDTO) request.getParam("sellerLevelApplyDTO");
        if (sellerLevelApplyDTO == null) {
            throw new DistributionException(ResponseCode.PARAMETER_NULL, "sellerLevelApplyDTO is null");
        }
         sellerLevelApplyDTO.setStatus(0);
         SellerLevelApplyDTO  is = sellerLevelApplyManager.getSellerLevelApply(sellerLevelApplyDTO);
         Boolean upgrade = true;
         Boolean downgrade = true;
         if(is != null){
        	 upgrade = false;
        	 downgrade = false;
         }else{
        	SellerDTO sellerDTO = sellerManager.getByUserId(sellerLevelApplyDTO.getApplicantId());
        	if(sellerDTO.getLevelId() == 3){
        		upgrade = false;
        	}
        	if(sellerDTO.getLevelId() == 1){
        		downgrade = false;
        	}
         }
         Map<String, Boolean> map = new HashMap<String, Boolean>();
         map.put("upgrade", upgrade);
         map.put("downgrade", downgrade);
        return new DistributionResponse(map);
    }

    @Override
    public String getName() {
        return ActionEnum.GET_SELLER_LEVEL_APPLY.getActionName();
    }
}
