package com.mockuai.usercenter.core.dao.impl;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Service;

import com.mockuai.usercenter.core.dao.UserBrowseLogDAO;
import com.mockuai.usercenter.core.domain.UserBrowseLogDO;

@Service
public class UserBrowseLogDaoImpl extends SqlMapClientDaoSupport implements UserBrowseLogDAO {
	
	@Override
	public Long addUserBrowseLog(UserBrowseLogDO userBrowsLogDO) {
		Long browseLogId = (Long) getSqlMapClientTemplate().queryForObject("user_browse_log.addUserBrowseLog", userBrowsLogDO);
        return browseLogId;
	}

	@Override
	public UserBrowseLogDO getBrowseLogByID(Long browseLogId) {
		UserBrowseLogDO userBrowsLogDO = (UserBrowseLogDO) getSqlMapClientTemplate().queryForObject("user_browse_log.getBrowseLogByID", browseLogId);
        return userBrowsLogDO;
	}

	@Override
	public UserBrowseLogDO getBrowseLogByUserId(Long userId) {
		UserBrowseLogDO userBrowsLogDO = (UserBrowseLogDO) getSqlMapClientTemplate().queryForObject("user_browse_log.getBrowseLogByUserId", userId);
        return userBrowsLogDO;
	}

}
