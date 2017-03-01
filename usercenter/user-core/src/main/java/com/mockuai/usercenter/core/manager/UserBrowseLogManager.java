package com.mockuai.usercenter.core.manager;

import com.mockuai.usercenter.common.dto.UserBrowseLogDTO;
import com.mockuai.usercenter.core.exception.UserException;

import org.springframework.stereotype.Service;

@Service
public interface UserBrowseLogManager {    
	/**
	 * 添加用户访问记录
	 * 
	 * @param userId
	 * @param nickName
	 * @param storeId
	 * @param goodsId
	 */
	UserBrowseLogDTO addUserBrowseLog(Long userId, String nickName, Long storeId, Long goodsId) throws UserException;
	
	/**
	 * 根据browseLogId查询用户访问记录
	 * 
	 * @param browseLogId
	 * @return
	 * @throws UserException 
	 */
	UserBrowseLogDTO getBrowseLogByID(Long browseLogId) throws UserException;
	
	/**
	 * 根据用户id查询访问记录信息
	 * 
	 * @author csy
	 * @Date 2016-05-21
	 */
	UserBrowseLogDTO getBrowseLogByUserId(Long userId) throws UserException;
}
