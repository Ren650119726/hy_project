package com.mockuai.usercenter.core.dao;

import com.mockuai.usercenter.common.qto.UserAuthInfoQTO;
import org.springframework.stereotype.Service;

import com.mockuai.usercenter.core.domain.UserAuthInfoDO;

import java.util.List;

@Service
public interface UserAuthInfoDAO {

	Long addAuthInfo(UserAuthInfoDO userAuthInfoDo);

	/**
	 * 根据id查询指定的认证信息
	 * */
	UserAuthInfoDO getAuthInfoById(Long id);

	/**
	 * 查询用户认证信息
	 * @param userAuthInfoQTO
	 * @return
	 */
	List<UserAuthInfoDO> queryUserAuthInfo(UserAuthInfoQTO userAuthInfoQTO);

	/**
	 * 获得用户认证信息的总量
	 * */
	Long queryUserAuthInfoTotalCount(UserAuthInfoQTO userAuthInfoQTO);

	/**
	 * 根据身份证号查询认证信息
	 * @param idCardNo
	 * @return
	 */
	UserAuthInfoDO getAuthInfoByIdCardNo(String idCardNo, Integer type, String bizCode);

	/**
	 * 根据user_id查询指定用户的认证信息
	 * */
	UserAuthInfoDO getAuthInfoByUserId(Long userId, Integer authType, String bizCode);

	/**
	 * 用户实名认证通过
	 * */
	int passUserAuth(Long userAuthId, Long userId, String remark);

	/**
	 * 用户实名认证不通过
	 * */
	int rejectUserAuth(Long userAuthId, Long userId, String remark);

	/**
	 * 修改用户的实名认证信息
	 * */
	int updateUserAuthInfo(UserAuthInfoDO authInfoDo);

	/**
	 * 删除指定用户的实名认证消息
	 * */
	int deleteUserAuth(Long userId);

	int restoreUserAuth(Long userId);

	/**
	 * 更新用户实名信息,把status更改为等待交纳保证金状态
	 * @param userAuthInfoDO
	 * @return
     */
	Integer passWaitForGuarantee(UserAuthInfoDO userAuthInfoDO);
}
