//package com.mockuai.usercenter.core.dao.impl;
//
//import com.mockuai.usercenter.common.qto.UserExtraInfoQTO;
//import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
//import org.springframework.stereotype.Service;
//
//import com.mockuai.usercenter.common.dto.UserExtraInfoDTO;
//import com.mockuai.usercenter.core.dao.UserExtraInfoDao;
//import com.mockuai.usercenter.core.domain.UserExtraInfoDO;
//
//import java.util.List;
//
//@Service
//public class UserExtraInfoDaoImpl extends SqlMapClientDaoSupport implements
//		UserExtraInfoDao {
//	@Override
//	public Long addUserExtraInfo(UserExtraInfoDO userExtraDo) {
//
//		Long id = (Long) getSqlMapClientTemplate().insert(
//				"user_extra_info.insert", userExtraDo);
//		return id;
//	}
//
//	@Override
//	public UserExtraInfoDO getUserExtraInfoById(Long id) {
//
//		UserExtraInfoDO key = new UserExtraInfoDO();
//		key.setId(id);
//		return (UserExtraInfoDO) getSqlMapClientTemplate().queryForObject(
//				"user_extra_info.selectById", key);
//	}
//
//	@Override
//	public UserExtraInfoDO getUserExtraInfoByUserId(Long userId) {
//		UserExtraInfoDO key = new UserExtraInfoDO();
//		key.setUserId(userId);
//
//		return (UserExtraInfoDO) getSqlMapClientTemplate().queryForObject(
//				"user_extra_info.selectByUserId", key);
//	}
//
//	@Override
//	public int updateUserExtraInfo(UserExtraInfoDO userExtraDo) {
//		int result = getSqlMapClientTemplate().update("user_extra_info.update",
//				userExtraDo);
//		return result;
//	}
//
//	@Override
//	public int delUserExtraInfoByUserId(Long userId) {
//		int result = getSqlMapClientTemplate().update("user_extra_info.delete",
//				userId);
//		return result;
//	}
//
//	@Override
//	public int restoreUserExtraInfoByUserId(Long userId) {
//		int result = getSqlMapClientTemplate().update(
//				"user_extra_info.restore", userId);
//		return result;
//	}
//
//	@Override
//	public UserExtraInfoDTO getInfoByOpenIdAndAuthType(
//			UserExtraInfoDTO userExtraInfoDto) {
//		return (UserExtraInfoDTO) getSqlMapClientTemplate().queryForObject(
//				"user_extra_info.getByOpenIdAndType", userExtraInfoDto);
//	}
//
//	@Override
//	public List<UserExtraInfoDO> queryUserExtraInfo(UserExtraInfoQTO extraInfoQTO) {
//		return (List<UserExtraInfoDO>)getSqlMapClientTemplate().queryForList(
//				"user_extra_info.queryUserExtraInfo", extraInfoQTO);
//	}
//}
