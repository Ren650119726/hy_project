package com.mockuai.distributioncenter.core.manager;

import com.mockuai.distributioncenter.core.exception.DistributionException;

/**
 * 
 * @Description 
 * @author linyue
 * @date 2016年12月22日 下午4:58:10
 */
public interface DistErrorLogManager {
	
	Integer addDistErrorLog(Long orderId,Long userId,String doType,String errorDesc) throws DistributionException;
	
}
