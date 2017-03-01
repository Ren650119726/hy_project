package com.mockuai.usercenter.core.manager.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.mockuai.usercenter.common.constant.ResponseCode;
import com.mockuai.usercenter.common.dto.UserBrowseLogDTO;
import com.mockuai.usercenter.common.dto.UserDTO;
import com.mockuai.usercenter.core.dao.UserBrowseLogDAO;
import com.mockuai.usercenter.core.domain.UserBrowseLogDO;
import com.mockuai.usercenter.core.exception.UserException;
import com.mockuai.usercenter.core.manager.UserBrowseLogManager;
import com.mockuai.usercenter.core.manager.UserManager;
@Service
public class UserBrowseLogManagerImpl implements UserBrowseLogManager {
	private static final Logger log = LoggerFactory.getLogger(UserBrowseLogManagerImpl.class);
	
	@Resource
	private UserManager userManager;
	@Resource
	private UserBrowseLogDAO userBrowsLogDAO;
	
	/**
	 * 添加用户访问记录
	 * 
	 * @author csy
	 * @Date 2016-05-20
	 */
	@Override
	public UserBrowseLogDTO addUserBrowseLog(Long userId, String nickName, Long storeId, Long goodsId) throws UserException {
		if(null == userId){
			throw new UserException(ResponseCode.P_PARAM_NULL,"添加用户访问记录信息不可为空");
		}
		
		if(null == nickName){
			throw new UserException(ResponseCode.P_PARAM_NULL,"添加用户访问记录昵称不可为空");
		}
		
		if(null == storeId){
			throw new UserException(ResponseCode.P_PARAM_NULL,"添加用户访问记录店铺信息不可为空");
		}
		
		if(null == goodsId){
			throw new UserException(ResponseCode.P_PARAM_NULL,"添加用户访问记录商品信息不可为空");
		}
		
		//获取卖家信息
		UserDTO userDto = userManager.getUserById(userId);
		if(null == userDto){
			throw new UserException(ResponseCode.B_SELECT_ERROR, "访问卖家链接失效或不存在");
		}
		
		UserBrowseLogDO userBrowsLogDO = new UserBrowseLogDO();
		
		userBrowsLogDO.setUserId(userId);
		userBrowsLogDO.setNickName(nickName);
		userBrowsLogDO.setStoreId(storeId);
		userBrowsLogDO.setGoodsId(goodsId);
		
		//添加用户访问商品信息
		Long browseLogId = userBrowsLogDAO.addUserBrowseLog(userBrowsLogDO);
		
		//根据用户访问记录id查询访问记录信息
		UserBrowseLogDTO userBrowseLogDTO = getBrowseLogByID(browseLogId);
		
		return userBrowseLogDTO;
	}
	
	/**
	 * 根据访问记录id查询记录信息
	 * 
	 * @author csy
	 * @Date 2016-05-20
	 * @param browseLogId
	 * @return
	 * @throws UserException 
	 */
	public UserBrowseLogDTO getBrowseLogByID(Long browseLogId) throws UserException {
		if(null == browseLogId){
			log.error("browseLogId is null when getBrowseLogByID");
			throw new UserException(ResponseCode.B_SELECT_ERROR, "查询用户访问信息传参记录id为空");
		}
		
		UserBrowseLogDTO userBrowseLogDTO= null;
		UserBrowseLogDO userBrowseLogDO = userBrowsLogDAO.getBrowseLogByID(browseLogId);  
        
        // 将do转换为dto
        if (null != userBrowseLogDO) {
        	userBrowseLogDTO = new UserBrowseLogDTO();
            BeanUtils.copyProperties(userBrowseLogDO, userBrowseLogDTO);
        }
        
        return userBrowseLogDTO;
	}
	
	/**
	 * 根据用户id查询用户访问记录
	 *  
	 * @author csy
	 * @Date 2015-05-21
	 * @param userId
	 */
	@Override
	public UserBrowseLogDTO getBrowseLogByUserId(Long userId) throws UserException {
		if(null == userId){
			log.error("userId is null when getBrowseLogByID");
			throw new UserException(ResponseCode.B_SELECT_ERROR, "查询用户访问信息传参记录id为空");
		}
		
		UserBrowseLogDTO userBrowseLogDTO= null;
		UserBrowseLogDO userBrowseLogDO = userBrowsLogDAO.getBrowseLogByUserId(userId);  
        
        // 将do转换为dto
        if (null != userBrowseLogDO) {
        	userBrowseLogDTO = new UserBrowseLogDTO();
            BeanUtils.copyProperties(userBrowseLogDO, userBrowseLogDTO);
        }
        
        return userBrowseLogDTO;
	}
}
