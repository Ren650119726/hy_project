package com.mockuai.usercenter.core.dao;

import org.springframework.stereotype.Service;

import com.mockuai.usercenter.core.domain.UserBrowseLogDO;

@Service
public interface UserBrowseLogDAO {
	
	/**
	 * 添加用户访问记录
	 * 
	 * @param userBrowsLogDO
	 * @return
	 */
	Long addUserBrowseLog(UserBrowseLogDO userBrowsLogDO);
	
	/**
	 * 根据用户访问记录id查询访问信息
	 * 
	 * @param browseLogId
	 * @return
	 */
	UserBrowseLogDO getBrowseLogByID(Long browseLogId);
	
	/**
	 * 根据用户id查询用户访问记录
	 * 
	 * @param userId
	 * @return
	 */
	UserBrowseLogDO getBrowseLogByUserId(Long userId);
}
