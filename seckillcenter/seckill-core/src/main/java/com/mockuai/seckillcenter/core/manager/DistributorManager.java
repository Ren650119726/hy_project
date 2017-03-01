package com.mockuai.seckillcenter.core.manager;

import com.mockuai.distributioncenter.common.domain.dto.DistShopDTO;
import com.mockuai.distributioncenter.common.domain.dto.DistShopForMopDTO;
import com.mockuai.seckillcenter.core.exception.SeckillException;

/**
 * Created by edgar.zr on 5/23/2016.
 */
public interface DistributorManager {

	DistShopDTO getShop(Long sellerId, String appKey) throws SeckillException;

	DistShopForMopDTO getShopForMopBySellerId(Long sellerId, String appKey) throws SeckillException;
}