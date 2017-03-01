package com.mockuai.distributioncenter.core.service.action.sellerupgrade;

import com.mockuai.distributioncenter.common.api.DistributionResponse;
import com.mockuai.distributioncenter.common.api.Request;
import com.mockuai.distributioncenter.common.constant.ActionEnum;
import com.mockuai.distributioncenter.common.constant.ResponseCode;
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
public class AgreeSellerUpgradeAction extends TransAction {
    private static final Logger log = LoggerFactory.getLogger(AgreeSellerUpgradeAction.class);

    @Resource
    private SellerUpgradeManager sellerUpgradeManager;

    @Override
    protected DistributionResponse doTransaction(RequestContext context) throws DistributionException {
        Request request = context.getRequest();
        Long id = (Long) request.getParam("id");
        String reason = (String) request.getParam("reason");

        if (id == null) {
            log.error("id is null");
            throw new DistributionException(ResponseCode.PARAMETER_NULL, "id is null");
        }

        this.sellerUpgradeManager.agreeSellerUpgrade(id, reason);
        //TODO 调用卖家等级升级接口


        return new DistributionResponse(ResponseCode.SUCCESS);

    }

    @Override
    public String getName() {
        return ActionEnum.AGREE_SELLER_UPGRADE.getActionName();
    }
}
