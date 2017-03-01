package com.mockuai.distributioncenter.core.service.action.sellerconfig;

import com.mockuai.distributioncenter.common.api.DistributionResponse;
import com.mockuai.distributioncenter.common.api.Request;
import com.mockuai.distributioncenter.common.constant.ActionEnum;
import com.mockuai.distributioncenter.common.constant.ResponseCode;
import com.mockuai.distributioncenter.common.domain.dto.SellerConfigDTO;
import com.mockuai.distributioncenter.common.domain.qto.SellerConfigQTO;
import com.mockuai.distributioncenter.core.exception.DistributionException;
import com.mockuai.distributioncenter.core.manager.SellerConfigManager;
import com.mockuai.distributioncenter.core.service.RequestContext;
import com.mockuai.distributioncenter.core.service.action.Action;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yeliming on 16/5/24.
 */
@Controller
public class QuerySellerConfigAction implements Action {
    @Resource
    private SellerConfigManager sellerConfigManager;

    @Override
    public DistributionResponse execute(RequestContext context) throws DistributionException {
        Request request = context.getRequest();
        SellerConfigQTO sellerConfigQTO = (SellerConfigQTO) request.getParam("sellerConfigQTO");
        if (sellerConfigQTO == null) {
            throw new DistributionException(ResponseCode.PARAMETER_NULL, "sellerConfigQTO is null");
        }

        List<SellerConfigDTO> sellerConfigDTOs = this.sellerConfigManager.querySellerConfig(sellerConfigQTO);
        return new DistributionResponse(sellerConfigDTOs);
    }

    @Override
    public String getName() {
        return ActionEnum.QUERY_SELLER_CONFIG.getActionName();
    }
}
