package com.mockuai.distributioncenter.core.service.action.gains;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mockuai.distributioncenter.common.api.DistributionResponse;
import com.mockuai.distributioncenter.common.constant.ActionEnum;
import com.mockuai.distributioncenter.common.constant.ResponseCode;
import com.mockuai.distributioncenter.common.domain.dto.HkProtocolRecordDTO;
import com.mockuai.distributioncenter.core.api.RequestAdapter;
import com.mockuai.distributioncenter.core.exception.DistributionException;
import com.mockuai.distributioncenter.core.manager.HkProtocolRecordManager;
import com.mockuai.distributioncenter.core.service.RequestContext;
import com.mockuai.distributioncenter.core.service.action.TransAction;

/**
 * Created by lizg on 2016/9/2.
 */
@Service
public class GetProtocolAction extends TransAction{

    private static final Logger logger = LoggerFactory.getLogger(GetProtocolAction.class);

    @Autowired
    private HkProtocolRecordManager hkProtocolRecordManager; 

    @Override
    protected DistributionResponse doTransaction(RequestContext context) throws DistributionException {
        RequestAdapter request = context.getRequestAdapter();
        HkProtocolRecordDTO hkProtocolRecordDTO =  (HkProtocolRecordDTO) request.getParam("HkProtocolRecordDTO");
        try {
        	HkProtocolRecordDTO hkProtocolRecord = hkProtocolRecordManager.get(hkProtocolRecordDTO);
            return new DistributionResponse(hkProtocolRecord);
        } catch (DistributionException e) {
            throw new DistributionException(ResponseCode.UPDATE_DB_ERROR, "数据返回出错");
        }
    }

    @Override
    public String getName() {
        return ActionEnum.GET_PROTOCOL.getActionName();
    }
}
