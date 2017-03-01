package com.mockuai.usercenter.core.manager;

import com.mockuai.usercenter.common.qto.UserAuthInfoQTO;
import com.mockuai.usercenter.core.domain.UserAuthInfoDO;
import org.springframework.stereotype.Service;

import com.mockuai.usercenter.common.dto.UserAuthInfoDTO;
import com.mockuai.usercenter.core.exception.UserException;

import java.util.List;

@Service
public interface UserAuthInfoManager {

	/**
	 * 添加普通用户的实名认证信息
	 * */
	public Long addUserAuthInfo(UserAuthInfoDO authInfoDO)
			throws UserException;

	/**
	 * 根据id查询指定的扩展信息
	 * */
	public UserAuthInfoDO getAuthInfoById(Long id) throws UserException;

	/**
	 * 查询认证信息
	 * @param userAuthInfoQTO
	 * @return
	 */
	public List<UserAuthInfoDO> queryAuthInfo(UserAuthInfoQTO userAuthInfoQTO) throws UserException;

	/**
	 * 查询指定查询条件下的认证数据总数
	 * */
	Long getTotalCount(UserAuthInfoQTO userAuthInfoQTO) throws UserException;

	/**
	 * 根据user_id查询指定用户的扩展信息
	 * */
	public UserAuthInfoDO getAuthInfoByUserId(Long userId, Integer authType, String bizCode) throws UserException;

	/**
	 * 买家实名认证通过 ，将status改为1
	 * */
	public int passUserAuth(Long userAuthId, Long userId, String remark) throws UserException;

	/**
	 * 买家实名认证失败 ，将status改为2
	 * */
	public int rejectUserAuth(Long userAuthId, Long userId, String remark) throws UserException;

	/**
	 * 跟新买家的实名认证信息，更新成功将审核状态重置为默认状态
	 * */
	public int updateUserAuthInfo(UserAuthInfoDO authInfoDO) throws UserException;

	/***
	 * 删除指定用户的认证消息
	 * 
	 * @param userId
	 * @return
	 */
	public int deleteUserAuth(Long userId) throws UserException;

	/**
	 * 将删除的用户认证信息还原
	 * 
	 * @param userId
	 * @return
	 */
	public int restoreUserAuth(Long userId) throws UserException;

	Boolean passWaitForGuarantee(UserAuthInfoDTO userAuthInfoDTO) throws UserException;
}
