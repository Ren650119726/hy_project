package com.mockuai.distributioncenter.core.manager;

import com.mockuai.deliverycenter.common.dto.express.DeliveryInfoDTO;
import com.mockuai.deliverycenter.common.qto.express.DeliveryInfoQTO;
import com.mockuai.distributioncenter.core.exception.DistributionException;

import java.util.List;

/**
 * Created by duke on 16/6/1.
 */
public interface DeliveryInfoManager {
    /**
     * 获得物流信息
     * */
    List<DeliveryInfoDTO> queryDeliveryInfo(DeliveryInfoQTO deliveryInfoQTO, String appKey) throws DistributionException;
}
