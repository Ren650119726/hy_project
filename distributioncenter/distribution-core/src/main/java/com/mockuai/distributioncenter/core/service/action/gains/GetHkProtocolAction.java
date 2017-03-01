package com.mockuai.distributioncenter.core.service.action.gains;

import com.mockuai.distributioncenter.common.api.DistributionResponse;
import com.mockuai.distributioncenter.common.constant.ActionEnum;
import com.mockuai.distributioncenter.common.constant.ResponseCode;
import com.mockuai.distributioncenter.common.domain.dto.HkProtocolDTO;
import com.mockuai.distributioncenter.common.domain.dto.HkProtocolRecordDTO;
import com.mockuai.distributioncenter.common.domain.qto.HkProtocolQTO;
import com.mockuai.distributioncenter.core.api.RequestAdapter;
import com.mockuai.distributioncenter.core.exception.DistributionException;
import com.mockuai.distributioncenter.core.manager.HkProtocolManager;
import com.mockuai.distributioncenter.core.service.RequestContext;
import com.mockuai.distributioncenter.core.service.action.TransAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lizg on 2016/10/24.
 */
@Service
public class GetHkProtocolAction extends TransAction {

    private static final Logger log = LoggerFactory.getLogger(GetHkProtocolAction.class);

    @Autowired
    private HkProtocolManager hkProtocolManager;

    @Override
    protected DistributionResponse doTransaction(RequestContext context) throws DistributionException {
        RequestAdapter request = context.getRequestAdapter();
        HkProtocolQTO hkProtocolQTO = (HkProtocolQTO)request.getParam("hkProtocolQTO");
        log.info("[{}] proModel:{}",hkProtocolQTO.getProModel());
        try {
            List<HkProtocolDTO> hkProtocolDTOList =  hkProtocolManager.getByProModel(hkProtocolQTO);
            return new DistributionResponse(hkProtocolDTOList);

        } catch (DistributionException e) {
            throw new DistributionException(ResponseCode.UPDATE_DB_ERROR, "数据返回出错");
        }
    }

    @Override
    public String getName() {
        return ActionEnum.GET_HK_PROTOCOL.getActionName();
    }
}
