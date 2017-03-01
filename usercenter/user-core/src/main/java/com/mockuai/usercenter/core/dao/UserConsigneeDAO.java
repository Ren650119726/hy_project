package com.mockuai.usercenter.core.dao;

import java.util.List;

import com.mockuai.usercenter.common.qto.UserConsigneeQTO;
import com.mockuai.usercenter.core.domain.UserConsigneeDO;

public interface UserConsigneeDAO {

	/**
	 * 获取指定用户的收货地址总数
	 * */
	int getConsigneeCountByUserId(Long userId);

	Long addConsigee(UserConsigneeDO userConsigneeDo);

	/** 根据收货地址id，获取指定的收货地址 */
	UserConsigneeDO getConsigneeById(Long userId, Long id);

	/**
	 * 删除指定的收货地址
	 * */
	int deleteConsignee(Long userId, Long consigneeId);

	/**
	 * 修改指定的收货地址
	 * */
	int updateConsigee(UserConsigneeDO userConsigneeDo);

	/**
	 * 将指定用户的所有收货地址修改为非默认
	 * */
	int updateUserDefaultConsignee(Long userId);

	int setDefConsignee(Long userId, Long consigneeId);

	List<UserConsigneeDO> queryConsignee(Long userId);

	/**
	 * 删除指定用户的所有收货地址
	 * */
	int deleteUserConsignee(Long userId);

	/**
	 * 将用户所有的收货地址还原
	 * 
	 * @param userId
	 * @return
	 */
	int restoreUserConsignee(Long userId);

	int increaseConsigneeUseCount(Long consigneeId, Long userId, String bizCode);

	List<UserConsigneeDO> queryAllConsignee(UserConsigneeQTO userConsigneeQTO);

	Long totalAllCount(UserConsigneeQTO userConsigneeQTO);
}
