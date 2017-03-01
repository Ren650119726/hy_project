package com.mockuai.distributioncenter.core.service.action.sellerupgrade;

import com.mockuai.distributioncenter.common.api.DistributionResponse;
import com.mockuai.distributioncenter.common.api.Request;
import com.mockuai.distributioncenter.common.constant.ActionEnum;
import com.mockuai.distributioncenter.common.constant.ResponseCode;
import com.mockuai.distributioncenter.common.domain.dto.SellerUpgradeDTO;
import com.mockuai.distributioncenter.core.exception.DistributionException;
import com.mockuai.distributioncenter.core.manager.SellerUpgradeManager;
import com.mockuai.distributioncenter.core.service.RequestContext;
import com.mockuai.distributioncenter.core.service.action.TransAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

/**
 * Created by yeliming on 16/5/18.
 */
@Controller
public class AddSellerUpgradeAction extends TransAction {
    private static final Logger log = LoggerFactory.getLogger(AddSellerUpgradeAction.class);

    @Resource
    private SellerUpgradeManager sellerUpgradeManager;

    @Override
    protected DistributionResponse doTransaction(RequestContext context) throws DistributionException {
        Request request = context.getRequest();
        SellerUpgradeDTO sellerUpgradeDTO = (SellerUpgradeDTO) request.getParam("sellerUpgradeDTO");

        if(sellerUpgradeDTO == null){
            log.error("sellerUpgradeDTO is null");
            throw new DistributionException(ResponseCode.PARAMETER_NULL,"sellerUpgradeDTO is null");
        }

        Long id = this.sellerUpgradeManager.addSellerUpgrade(sellerUpgradeDTO);
        return new DistributionResponse(id);
    }

    @Override
    public String getName() {
        return ActionEnum.ADD_SELLER_UPGRADE.getActionName();
    }
}
