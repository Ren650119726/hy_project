package com.mockuai.usercenter.core.dao.impl;

import com.mockuai.usercenter.common.qto.UserAuthInfoQTO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Service;

import com.mockuai.usercenter.core.dao.UserAuthInfoDAO;
import com.mockuai.usercenter.core.domain.UserAuthInfoDO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserAuthInfoDAOImpl extends SqlMapClientDaoSupport implements
		UserAuthInfoDAO {

	@Override
	public Long addAuthInfo(UserAuthInfoDO userAuthInfoDo) {
		// TODO Auto-generated method stub
		Long id = (Long) getSqlMapClientTemplate().insert(
				"UserAuthInfoDao.insert", userAuthInfoDo);
		return id;
	}

	@Override
	public UserAuthInfoDO getAuthInfoById(Long id) {
		// TODO Auto-generated method stub
		UserAuthInfoDO userAuthInfoDo = (UserAuthInfoDO) getSqlMapClientTemplate()
				.queryForObject("UserAuthInfoDao.selectById", id);
		return userAuthInfoDo;
	}

	public List<UserAuthInfoDO> queryUserAuthInfo(UserAuthInfoQTO userAuthInfoQTO) {
		List<UserAuthInfoDO> userAuthInfoDOs = getSqlMapClientTemplate().queryForList(
				"UserAuthInfoDao.queryUserAuthInfo", userAuthInfoQTO);
		return userAuthInfoDOs;
	}

	@Override
	public Long queryUserAuthInfoTotalCount(UserAuthInfoQTO userAuthInfoQTO) {
		return (Long)getSqlMapClientTemplate().queryForObject("UserAuthInfoDao.totalCount", userAuthInfoQTO);
	}

	@Override
	public UserAuthInfoDO getAuthInfoByIdCardNo(String idCardNo, Integer type, String bizCode) {
		UserAuthInfoDO authInfoDO = new UserAuthInfoDO();
		authInfoDO.setIdcardNo(idCardNo);
		authInfoDO.setType(type);
		authInfoDO.setBizCode(bizCode);
		UserAuthInfoDO userAuthInfoDo = (UserAuthInfoDO) getSqlMapClientTemplate()
				.queryForObject("UserAuthInfoDao.selectByIdCardNo", authInfoDO);
		return userAuthInfoDo;
	}

	@Override
	public UserAuthInfoDO getAuthInfoByUserId(Long userId, Integer authType, String bizCode) {
		UserAuthInfoDO userAuthInfoDO = new UserAuthInfoDO();
		userAuthInfoDO.setUserId(userId);
		userAuthInfoDO.setType(authType);
		userAuthInfoDO.setBizCode(bizCode);
		UserAuthInfoDO userAuthInfoDo = (UserAuthInfoDO) getSqlMapClientTemplate()
				.queryForObject("UserAuthInfoDao.selectByUserId", userAuthInfoDO);
		return userAuthInfoDo;
	}

	@Override
	public int passUserAuth(Long userAuthId, Long userId, String remark) {
		// TODO Auto-generated method stub
		UserAuthInfoDO key = new UserAuthInfoDO();
		key.setId(userAuthId);
		key.setUserId(userId);
		key.setRemark(remark);

		return getSqlMapClientTemplate().update("UserAuthInfoDao.pass", key);
	}

	@Override
	public int rejectUserAuth(Long userAuthId, Long userId, String remark) {
		// TODO Auto-generated method stub
		UserAuthInfoDO key = new UserAuthInfoDO();
		key.setId(userAuthId);
		key.setUserId(userId);
		key.setRemark(remark);

		return getSqlMapClientTemplate().update("UserAuthInfoDao.reject", key);
	}

	@Override
	public int updateUserAuthInfo(UserAuthInfoDO authInfoDo) {
		// TODO Auto-generated method stub

		return getSqlMapClientTemplate().update("UserAuthInfoDao.update",
				authInfoDo);
	}

	@Override
	public int deleteUserAuth(Long userId) {
		// TODO Auto-generated method stub

		return getSqlMapClientTemplate()
				.update("UserAuthInfoDao.delete", userId);
	}

	@Override
	public int restoreUserAuth(Long userId) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().update("UserAuthInfoDao.restore",
				userId);
	}

	@Override
	public Integer passWaitForGuarantee(UserAuthInfoDO userAuthInfoDO) {
		return this.getSqlMapClientTemplate().update("UserAuthInfoDao.waitGuarantee",userAuthInfoDO);
	}

}
