package com.mockuai.usercenter.client;

import com.mockuai.usercenter.common.api.Response;
import com.mockuai.usercenter.common.constant.ResponseCode;
import com.mockuai.usercenter.common.dto.UserAuthInfoDTO;
import com.mockuai.usercenter.common.qto.UserAuthInfoQTO;

import java.util.List;

public interface UserAuthClient {
	/**
	 * 保存用户实名认证信息
	 */
	Response<Long> addUserAuthInfo(UserAuthInfoDTO authInfoDTO, String appKey);

	/**
	 * 获取用户的实名认证信息
	 */
	Response<UserAuthInfoDTO> getAuthInfoByUserId(Long userId, String appKey);

	/**
	 * 查询用户认证信息
	 * @param userAuthInfoQTO
	 * @param appKey
	 * @return
	 */
	Response<List<UserAuthInfoDTO>> queryUserAuthInfo(UserAuthInfoQTO userAuthInfoQTO, String appKey);

	/**
	 * 用户实名认证通过
	 */
	Response<Void> passUserAuth(Long userAuthId, Long userId, String remark, String appKey);

	/**
	 * 用户实名认证不通过
	 */
	Response<Void> rejectUserAuth(Long userAuthId, Long UserId, String remark, String appKey);

	/**
	 * 更新用户认证信息
	 * */
	Response<ResponseCode> updateUserAuth(UserAuthInfoDTO userAuthInfoDTO, String appKey);

	/**
	 * 通过实名,等待交纳交纳保证金
     */
	Response<Void> passWaitForGuarantee(Long userAuthId, Long userId, Long guaranteeAmount, String appKey);

}
