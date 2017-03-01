package com.mockuai.distributioncenter.core.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.mockuai.distributioncenter.core.dao.BaseDAO;
import com.mockuai.distributioncenter.core.dao.DistErrorLogDAO;
import com.mockuai.distributioncenter.core.domain.DistErrorLogDO;
import com.mockuai.distributioncenter.core.exception.DistributionException;
@Repository
public class DistErrorLogDAOImpl extends BaseDAO implements DistErrorLogDAO {
	
	private final static Logger log = LoggerFactory.getLogger(DistErrorLogDAOImpl.class);

	@Override
	public Integer addDistErrorLog(DistErrorLogDO distErrorLogDO) throws DistributionException {
		
		return (Integer)getSqlMapClientTemplate().insert("dist_error_log.addDistErrorLog", distErrorLogDO);
		
	}

	
	
}
