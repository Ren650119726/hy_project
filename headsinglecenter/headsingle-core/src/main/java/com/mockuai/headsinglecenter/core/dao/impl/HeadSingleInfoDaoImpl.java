package com.mockuai.headsinglecenter.core.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Service;

import com.mockuai.headsinglecenter.common.domain.qto.HeadSingleInfoQTO;
import com.mockuai.headsinglecenter.core.dao.HeadSingleInfoDAO;
import com.mockuai.headsinglecenter.core.domain.HeadSingleInfoDO;

/**
 * Created by edgar.zr on 12/4/15.
 */
@Service
public class HeadSingleInfoDaoImpl extends SqlMapClientDaoSupport implements HeadSingleInfoDAO {

	@SuppressWarnings("unchecked")
	@Override
	public List<HeadSingleInfoDO> queryHeadSingleInfos(Integer terminalType, Date payTimeStart, Date payTimeEnd) {					
		HeadSingleInfoQTO headSingleInfoQTO = new HeadSingleInfoQTO();
		
		if(null != terminalType){
			headSingleInfoQTO.setTerminalType(terminalType.intValue());
		}
		
		headSingleInfoQTO.setPayTimeStart(payTimeStart);
		headSingleInfoQTO.setPayTimeEnd(payTimeEnd);
		
		return getSqlMapClientTemplate().queryForList("headsingle_info.queryHeadSingleInfos", headSingleInfoQTO);
	}

	@Override
	public HeadSingleInfoDO queryInfoDtoByOrderId(Long orderId) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("orderId", orderId);
		HeadSingleInfoDO headSingleInfoDO = (HeadSingleInfoDO) getSqlMapClientTemplate().queryForObject("headsingle_info.queryInfoDtoByOrderId",map);
		return headSingleInfoDO;
	}

	@Override
	public Long addHeadSingleInfo(HeadSingleInfoDO headSingleInfoDO) {
		Long hsInfoId = (Long) getSqlMapClientTemplate().insert("headsingle_info.addHeadSingleInfo",headSingleInfoDO);
		return hsInfoId;
	}
}