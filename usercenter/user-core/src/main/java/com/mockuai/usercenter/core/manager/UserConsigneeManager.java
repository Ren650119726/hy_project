package com.mockuai.usercenter.core.manager;

import java.util.List;

import com.mockuai.usercenter.common.dto.UserConsigneeDTO;
import com.mockuai.usercenter.common.qto.UserConsigneeQTO;
import com.mockuai.usercenter.core.exception.UserException;

public interface UserConsigneeManager {

	UserConsigneeDTO addConsignee(UserConsigneeDTO userConsigneeDto) throws UserException;

	/**
	 * 删除指定的收货地址
	 * */
	int deleteConsignee(Long userId, Long consigneeId) throws UserException;

	/**
	 * 修改指定的收货地址
	 * */
	int updateConsignee(UserConsigneeDTO userConsigneeDto) throws UserException;

	/**
	 * 将指定的收货地址设为默认
	 * */
	int setDefConsignee(Long userId, Long consigneeId) throws UserException;

	/**
	 * 将指定用户所有的收货地址修改为非默认
	 * */
	int updateUserDefaultConsignee(Long userId) throws UserException;

	/**
	 * 指定用户的收货地址总数
	 * */
	int getConsigneeCountByUserId(Long userId) throws UserException;

	/**
	 * 查询指定用户的收货地址列表
	 * */
	List<UserConsigneeDTO> queryConsignee(Long userId) throws UserException;

	/**
	 * 删除指定用户所有的收货地址
	 * */
	int deleteUserConsignee(Long userId) throws UserException;

	/**
	 * 将删除指定用户的收货地址还原
	 * */
	int restoreUserConsignee(Long userId) throws UserException;

	/**
	 * 
	 * @param userId
	 * @param consigneeId
	 * @return
	 */
	UserConsigneeDTO getConsigneeById(Long userId, Long consigneeId)
			throws UserException;

	Boolean increaseConsigneeUseCount(Long consigneeId, Long userId, String bizCode) throws UserException;

	List<UserConsigneeDTO> queryAllConsignee(UserConsigneeQTO userConsigneeQTO);

	Long totalAllCount(UserConsigneeQTO userConsigneeQTO);
}
