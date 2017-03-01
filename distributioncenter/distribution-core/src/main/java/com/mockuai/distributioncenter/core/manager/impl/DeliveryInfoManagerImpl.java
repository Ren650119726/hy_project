package com.mockuai.distributioncenter.core.manager.impl;

import com.mockuai.deliverycenter.client.DeliveryInfoClient;
import com.mockuai.deliverycenter.common.api.Response;
import com.mockuai.deliverycenter.common.dto.express.DeliveryInfoDTO;
import com.mockuai.deliverycenter.common.qto.express.DeliveryInfoQTO;
import com.mockuai.distributioncenter.common.constant.ResponseCode;
import com.mockuai.distributioncenter.core.exception.DistributionException;
import com.mockuai.distributioncenter.core.manager.DeliveryInfoManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by duke on 16/6/1.
 */
@Service
public class DeliveryInfoManagerImpl implements DeliveryInfoManager {
    private static final Logger log = LoggerFactory.getLogger(DeliveryInfoManagerImpl.class);

    @Autowired
    private DeliveryInfoClient deliveryInfoClient;

    @Override
    public List<DeliveryInfoDTO> queryDeliveryInfo(DeliveryInfoQTO deliveryInfoQTO, String appKey) throws DistributionException {
        try {
            Response<List<DeliveryInfoDTO>> response = deliveryInfoClient.queryDeliveryInfo(deliveryInfoQTO, appKey);
            if (response.isSuccess()) {
                return response.getModule();
            } else {
                log.error("query delivery info error, errMsg: {}", response.getMessage());
                throw new DistributionException(ResponseCode.INVOKE_SERVICE_EXCEPTION);
            }
        } catch (Exception e) {
            log.info("invoke delivery info occur exception, msg: {}", e.getMessage());
            return null;
        }
    }
}
