package com.mockuai.distributioncenter.core.service.action.sellerupgrade;

import com.mockuai.distributioncenter.common.api.DistributionResponse;
import com.mockuai.distributioncenter.common.api.Request;
import com.mockuai.distributioncenter.common.constant.ActionEnum;
import com.mockuai.distributioncenter.common.constant.ResponseCode;
import com.mockuai.distributioncenter.common.domain.dto.SellerUpgradeDTO;
import com.mockuai.distributioncenter.common.domain.qto.SellerUpgradeQTO;
import com.mockuai.distributioncenter.core.exception.DistributionException;
import com.mockuai.distributioncenter.core.manager.SellerUpgradeManager;
import com.mockuai.distributioncenter.core.service.RequestContext;
import com.mockuai.distributioncenter.core.service.action.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yeliming on 16/5/18.
 */
@Controller
public class QuerySellerUpgradeAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(QuerySellerUpgradeAction.class);

    @Resource
    private SellerUpgradeManager sellerUpgradeManager;

    @Override
    public DistributionResponse execute(RequestContext context) throws DistributionException {
        Request request = context.getRequest();
        SellerUpgradeQTO sellerUpgradeQTO = (SellerUpgradeQTO) request.getParam("sellerUpgradeQTO");

        if (sellerUpgradeQTO == null) {
            log.error("sellerUpgradeQTO is null");
            throw new DistributionException(ResponseCode.PARAMETER_NULL, "sellerUpgradeQTO is null");
        }

        List<SellerUpgradeDTO> sellerUpgradeDTOList = this.sellerUpgradeManager.querySellerUpgrade(sellerUpgradeQTO);

        return new DistributionResponse(sellerUpgradeDTOList);

    }

    @Override
    public String getName() {
        return ActionEnum.QUERY_SELLER_UPGRADE.getActionName();
    }
}
