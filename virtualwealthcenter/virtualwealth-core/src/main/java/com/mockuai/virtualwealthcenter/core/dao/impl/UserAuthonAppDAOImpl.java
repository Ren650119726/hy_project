package com.mockuai.virtualwealthcenter.core.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.mockuai.virtualwealthcenter.common.domain.dto.boss.BossUserAuthonDTO;
import com.mockuai.virtualwealthcenter.common.domain.dto.mop.MopUserAuthonAppQTO;
import com.mockuai.virtualwealthcenter.common.domain.qto.UserAuthonQTO;
import com.mockuai.virtualwealthcenter.core.dao.UserAuthonAppDAO;
import com.mockuai.virtualwealthcenter.core.domain.UserAuthonAppDO;

public class UserAuthonAppDAOImpl extends SqlMapClientDaoSupport implements UserAuthonAppDAO{

	/**
	 * 实名认证
	 */
	@Override
	public Long addUserAuthon(UserAuthonAppDO userAuthonAppDO) {
		return ((Long) getSqlMapClientTemplate().insert("user_authon.addUserAuthon", userAuthonAppDO)).longValue();
	}

	/**
	 * 获取实名认证信息
	 */
	@Override
	public UserAuthonAppDO selectUserAuton(Long userId) {
		Map map = new HashMap();
		map.put("userId", userId);
		return (UserAuthonAppDO) getSqlMapClientTemplate().queryForObject("user_authon.selectUserAuton",map);
	}

	/**
	 * 判断账户是否绑定
	 */
	@Override
	public UserAuthonAppDO selectPersonalId(String authonPersonalid) {
		Map map = new HashMap();
		map.put("authonPersonalid", authonPersonalid);
		return (UserAuthonAppDO) getSqlMapClientTemplate().queryForObject("user_authon.selectPersonalId",map);
	}

	
	/**
	 * 通用查询
	 */
	@Override
	public List<UserAuthonAppDO> selectUserAuthonByQto(
			MopUserAuthonAppQTO mopUserAuthonAppQTO) {
		return (List<UserAuthonAppDO>)getSqlMapClientTemplate().queryForList("user_authon.selectUserAuthonByQto", mopUserAuthonAppQTO);
	}

	/**
	 * 后台改审核修改审核状态
	 *	 
	 */
	@Override
	public Integer modifyAuditStatus(UserAuthonAppDO userAuthonAppDO) {
		Long id=userAuthonAppDO.getId();
		Integer authonStatus=userAuthonAppDO.getAuthonStatus();
		String authonText=userAuthonAppDO.getAuthonText();
		Map map = new HashMap();
		map.put("id",id);
		map.put("authonStatus",authonStatus);
		map.put("authonText", authonText);
		Integer result = getSqlMapClientTemplate().update("user_authon.modifyAuditStatus",map);
		return result;
	}
	
	/**
	 * 查询所有等待审核信息
	 */
	@Override
	public List<UserAuthonAppDO> selectUserAuthonAll(
			UserAuthonQTO userAuthonQTO) {
		return (List<UserAuthonAppDO>)getSqlMapClientTemplate().queryForList("user_authon.selectUserAuthonAll", userAuthonQTO);
	}
	/**
	 * 查询所有等待审核信息总条数
	 */
	@Override
	public Long selectUserAuthonAllCount(UserAuthonQTO userAuthonQTO) {
		return (Long)getSqlMapClientTemplate().queryForObject("user_authon.selectUserAuthonAllCount", userAuthonQTO);
	}

	/**
	 * 根据主键查用户
	 */
	@Override
	public UserAuthonAppDO selectUserAuthonById(Long id) {
		Map map = new HashMap();
		map.put("id", id);
		return (UserAuthonAppDO) getSqlMapClientTemplate().queryForObject("user_authon.selectUserAutonById",map);
		
	}
	/**
	 * 根据用户id查审核状态
	 */

	@Override
	public UserAuthonAppDO selectAuditStatus(Long userId) {
		Map map = new HashMap();
		map.put("userId", userId);
		return (UserAuthonAppDO) getSqlMapClientTemplate().queryForObject("user_authon.selectAuditStatusByUserId",map);
	}
	/**
	 * 审核失败重新提交审核信息	  
	 */

	@Override
	public Long updateUserAuton(UserAuthonAppDO userAuthonAppDO) {
		Long userId=userAuthonAppDO.getUserId();
		String authonPersonalid=userAuthonAppDO.getAuthonPersonalid();
		String pictureFront=userAuthonAppDO.getPictureFront();
		String pictureBack=userAuthonAppDO.getPictureBack();
		String authonRealname=userAuthonAppDO.getAuthonRealname();
		Map map = new HashMap();
		map.put("userId",userId);
		map.put("pictureFront",pictureFront);
		map.put("pictureBack", pictureBack);
		map.put("authonPersonalid",authonPersonalid);
		map.put("authonRealname",authonRealname);
		Long result = (long) getSqlMapClientTemplate().update("user_authon.updateUserAuton",map);
		return result;
	}
	
	/**
	 * 用户更改用户名时更新到实名认证表中
	 */
	
	@Override
	public Long syncPhoneNumber(Long userId,String authonMobile) {
		Map map = new HashMap();
		map.put("userId", userId);
		map.put("authonMobile",authonMobile);
		Long result =  (long) getSqlMapClientTemplate().update("user_authon.updateUserName",map);
		return null;
	}

	
		
}
