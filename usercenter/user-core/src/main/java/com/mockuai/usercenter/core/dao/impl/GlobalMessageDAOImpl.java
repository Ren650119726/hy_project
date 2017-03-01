package com.mockuai.usercenter.core.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Service;

import com.mockuai.usercenter.common.qto.GlobalMessageQTO;
import com.mockuai.usercenter.core.dao.GlobalMessageDAO;
import com.mockuai.usercenter.core.domain.GlobalMessageDO;

@Service
public class GlobalMessageDAOImpl extends SqlMapClientDaoSupport implements GlobalMessageDAO {

	@Override
	public Long addGlobalMessage(GlobalMessageDO globalMessage) {
		return (Long)this.getSqlMapClientTemplate().insert("global_message.addGlobalMessage",globalMessage);
	}

	@Override
	public List<GlobalMessageDO> queryGlobalMessage(
			GlobalMessageQTO globalMessageQTO) {
		return (List<GlobalMessageDO>)this.getSqlMapClientTemplate().queryForList("global_message.queryGlobalMessage",globalMessageQTO);
	}
	
	public Long getTotalCount(GlobalMessageQTO globalMessageQTO){
		return (Long)this.getSqlMapClientTemplate().queryForObject("global_message.getTotalCount",globalMessageQTO);
	}
}
