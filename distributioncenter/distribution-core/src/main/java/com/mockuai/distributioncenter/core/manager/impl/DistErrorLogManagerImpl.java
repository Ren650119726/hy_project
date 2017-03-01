package com.mockuai.distributioncenter.core.manager.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mockuai.distributioncenter.core.dao.DistErrorLogDAO;
import com.mockuai.distributioncenter.core.domain.DistErrorLogDO;
import com.mockuai.distributioncenter.core.exception.DistributionException;
import com.mockuai.distributioncenter.core.manager.DistErrorLogManager;

/**
 * 
 * @Description 
 * @author linyue
 * @date 2016年12月22日 下午5:00:44
 */
@Service
public class DistErrorLogManagerImpl implements DistErrorLogManager {

	private static final Logger log = LoggerFactory.getLogger(DistErrorLogManagerImpl.class);
	
	@Resource
	private DistErrorLogDAO distErrorLogDAO;
	
	@Override
	public Integer addDistErrorLog(Long orderId, Long userId, String doType,
			String errorDesc) throws DistributionException {
		DistErrorLogDO distErrorLogDO = new DistErrorLogDO();
		try {
			int result = 0;
			distErrorLogDO.setOrderId(orderId);
			distErrorLogDO.setUserId(userId);
			distErrorLogDO.setDoType(doType);
			distErrorLogDO.setErrorDesc(errorDesc);
			result = distErrorLogDAO.addDistErrorLog(distErrorLogDO);

			return result;
		} catch (DistributionException e) {
			log.error(" addDistErrorLog error :{}",e.getMessage());
			throw new DistributionException(e);			
		}
		
	}

	
	
}
