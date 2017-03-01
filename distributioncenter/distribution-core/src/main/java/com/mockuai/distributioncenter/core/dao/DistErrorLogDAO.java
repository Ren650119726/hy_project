package com.mockuai.distributioncenter.core.dao;

import java.sql.SQLException;

import com.mockuai.distributioncenter.core.domain.DistErrorLogDO;
import com.mockuai.distributioncenter.core.exception.DistributionException;

public interface DistErrorLogDAO {

	/**
	 * 分佣异常记录dao
	 * @Description 
	 * @author linyue
	 * @param distErrorLogDO
	 * @return
	 */
	public Integer addDistErrorLog(DistErrorLogDO distErrorLogDO) throws DistributionException;
	
}
