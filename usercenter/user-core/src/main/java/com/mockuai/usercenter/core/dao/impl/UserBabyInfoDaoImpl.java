//package com.mockuai.usercenter.core.dao.impl;
//
//import java.util.List;
//
//import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
//import org.springframework.stereotype.Service;
//
//import com.mockuai.usercenter.core.dao.UserBabyInfoDao;
//import com.mockuai.usercenter.core.domain.UserBabyInfoDO;
//
//@Service
//public class UserBabyInfoDaoImpl extends SqlMapClientDaoSupport implements
//		UserBabyInfoDao {
//
//	@Override
//	public Long addBabyInfo(UserBabyInfoDO babyInfoDo) {
//		// TODO Auto-generated method stub
//
//		Long id = (Long) getSqlMapClientTemplate().insert(
//				"user_baby_info.insert", babyInfoDo);
//		return id;
//	}
//
//	@Override
//	public UserBabyInfoDO getBabyInfoById(Long id) {
//		// TODO Auto-generated method stub
//
//		UserBabyInfoDO userBabyInfoDO = (UserBabyInfoDO) getSqlMapClientTemplate()
//				.queryForObject("user_baby_info.selectBabyById", id);
//		return userBabyInfoDO;
//	}
//
//	@Override
//	public int getBabyCountByUserId(Long userId) {
//		// TODO Auto-generated method stub
//		int count = (Integer) getSqlMapClientTemplate().queryForObject(
//				"user_baby_info.countByUserId", userId);
//		return count;
//	}
//
//	@Override
//	public int deleteBabyInfo(Long userId, Long id) {
//		UserBabyInfoDO key = new UserBabyInfoDO();
//		key.setId(id);
//		key.setUserId(userId);
//		int count = getSqlMapClientTemplate().update(
//				"user_baby_info.deleteBaby", key);
//		return count;
//	}
//
//	@Override
//	public int updateBabyInfo(UserBabyInfoDO userBabyInfoDo) {
//		// TODO Auto-generated method stub
//		int count = getSqlMapClientTemplate().update(
//				"user_baby_info.updateBaby", userBabyInfoDo);
//
//		return count;
//	}
//
//	@Override
//	public List<UserBabyInfoDO> queryBabyInfo(Long userId) {
//		// TODO Auto-generated method stub
//		List<UserBabyInfoDO> babys = getSqlMapClientTemplate().queryForList(
//				"user_baby_info.getBabys", userId);
//		return babys;
//	}
//
//	@Override
//	public UserBabyInfoDO getDefBabyInfo(Long userId) {
//		// TODO Auto-generated method stub
//
//		return (UserBabyInfoDO) getSqlMapClientTemplate().queryForObject(
//				"user_baby_info.getDefBaby", userId);
//	}
//
//	@Override
//	public int setNonDef(Long id) {
//		// TODO Auto-generated method stub
//
//		return getSqlMapClientTemplate().update("user_baby_info.updateNonDef",
//				id);
//	}
//
//	@Override
//	public int deleteUserAllBaby(Long userId) {
//		// TODO Auto-generated method stub
//
//		int result = getSqlMapClientTemplate().update(
//				"user_baby_info.deleteUserBaby", userId);
//
//		return result;
//	}
//
//	@Override
//	public int restoreUserAllBaby(Long userId) {
//		// TODO Auto-generated method stub
//		int result = getSqlMapClientTemplate().update(
//				"user_baby_info.restoreUserBaby", userId);
//
//		return result;
//	}
//}
