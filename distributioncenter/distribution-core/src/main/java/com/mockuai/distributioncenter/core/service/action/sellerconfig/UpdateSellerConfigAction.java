package com.mockuai.distributioncenter.core.service.action.sellerconfig;

import com.mockuai.distributioncenter.common.api.DistributionResponse;
import com.mockuai.distributioncenter.common.api.Request;
import com.mockuai.distributioncenter.common.constant.ActionEnum;
import com.mockuai.distributioncenter.common.constant.ResponseCode;
import com.mockuai.distributioncenter.common.domain.dto.SellerConfigDTO;
import com.mockuai.distributioncenter.core.exception.DistributionException;
import com.mockuai.distributioncenter.core.manager.SellerConfigManager;
import com.mockuai.distributioncenter.core.service.RequestContext;
import com.mockuai.distributioncenter.core.service.action.TransAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yeliming on 16/5/18.
 */
@Controller
public class UpdateSellerConfigAction extends TransAction {
    private static final Logger log = LoggerFactory.getLogger(UpdateSellerConfigAction.class);

    @Resource
    private SellerConfigManager sellerConfigManager;

    @Override
    protected DistributionResponse doTransaction(RequestContext context) throws DistributionException {
        Request request = context.getRequest();
        List<SellerConfigDTO> sellerConfigDTOs = (List<SellerConfigDTO>) request.getParam("sellerConfigDTOs");

        if (sellerConfigDTOs == null) {
            log.error("sellerConfigDTOs is null");
            throw new DistributionException(ResponseCode.PARAMETER_NULL, "sellerConfigDTOs is null");
        }

        for (SellerConfigDTO sellerConfigDTO : sellerConfigDTOs) {
            this.sellerConfigManager.updateSellerConfig(sellerConfigDTO);
        }
        return new DistributionResponse(true);

    }

    @Override
    public String getName() {
        return ActionEnum.UPDATE_SELLER_CONFIG.getActionName();
    }
}
