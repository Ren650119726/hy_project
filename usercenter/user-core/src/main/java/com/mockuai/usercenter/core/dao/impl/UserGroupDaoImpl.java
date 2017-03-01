//package com.mockuai.usercenter.core.dao.impl;
//
//import java.util.List;
//
//import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
//import org.springframework.stereotype.Service;
//
//import com.mockuai.usercenter.core.dao.UserGroupDao;
//import com.mockuai.usercenter.core.domain.UserGroupDO;
//
//@Service
//public class UserGroupDaoImpl extends SqlMapClientDaoSupport implements
//		UserGroupDao {
//
//	@Override
//	public UserGroupDO compareMinIntegral(Integer minIntegral) {
//		// TODO Auto-generated method stub
//		UserGroupDO userGroupDo = (UserGroupDO) getSqlMapClientTemplate()
//				.queryForObject("user_group.compare", minIntegral);
//		return userGroupDo;
//	}
//
//	@Override
//	public Integer addUserGroup(UserGroupDO userGroupDo) {
//		// TODO Auto-generated method stub
//
//		int result = (Integer) getSqlMapClientTemplate().insert(
//				"user_group.insert", userGroupDo);
//
//		return result;
//	}
//
//	@Override
//	public UserGroupDO getUserGroup(Integer id) {
//		// TODO Auto-generated method stub
//		UserGroupDO userGroupDo = (UserGroupDO) getSqlMapClientTemplate()
//				.queryForObject("user_group.select", id);
//		return userGroupDo;
//	}
//
//	@Override
//	public int delUserGroup(Integer id) {
//		// TODO Auto-generated method stub
//		int result = getSqlMapClientTemplate().update("user_group.delete", id);
//		return result;
//	}
//
//	@Override
//	public UserGroupDO getUserGroupByNo(Integer groupNo) {
//		// TODO Auto-generated method stub
//
//		return (UserGroupDO) getSqlMapClientTemplate().queryForObject(
//				"user_group.selectByNo", groupNo);
//	}
//
//	@Override
//	public Long getMaxIntegralValue() {
//		// TODO Auto-generated method stub
//		Long result = (Long) getSqlMapClientTemplate().queryForObject(
//				"user_group.selectMax");
//
//		return result;
//	}
//
//	@Override
//	public Integer getMaxGroupNo() {
//		// TODO Auto-generated method stub
//
//		Integer result = (Integer) getSqlMapClientTemplate().queryForObject(
//				"user_group.selectMaxNo");
//		return result;
//	}
//
//	@Override
//	public int updateUserGroup(UserGroupDO userGroupDo) {
//		// TODO Auto-generated method stub
//
//		Integer result = getSqlMapClientTemplate().update("user_group.update",
//				userGroupDo);
//		return result;
//	}
//
//	@Override
//	public UserGroupDO getMiniDiffer(Integer id) {
//		UserGroupDO userGroupDo = (UserGroupDO) getSqlMapClientTemplate()
//				.queryForObject("user_group.miniDiffer", id);
//		return userGroupDo;
//	}
//
//	@Override
//	public List<UserGroupDO> queryUserGroup() {
//		// TODO Auto-generated method stub
//
//		return getSqlMapClientTemplate().queryForList("user_group.query");
//	}
//
//	@Override
//	public UserGroupDO getUserGroupByIntegral(Long max_integral) {
//		// TODO Auto-generated method stub
//		return (UserGroupDO) getSqlMapClientTemplate().queryForObject(
//				"user_group.selectByIntegral", max_integral);
//	}
//
//	@Override
//	public UserGroupDO getNextUserGroup(Integer groupNo) {
//
//		return (UserGroupDO) getSqlMapClientTemplate().queryForObject(
//				"user_group.nextGroup", groupNo);
//	}
//
//	@Override
//	public UserGroupDO getUpUserGroup(Integer groupNo) {
//		// TODO Auto-generated method stub
//
//		return (UserGroupDO) getSqlMapClientTemplate().queryForObject(
//				"user_group.upGroup", groupNo);
//	}
//}
