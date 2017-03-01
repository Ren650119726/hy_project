package com.mockuai.marketingcenter.core.manager;

import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.usercenter.common.dto.UserDTO;

import java.util.List;

/**
 * Created by duke on 15/9/28.
 */
public interface UserManager {
	/**
	 * 通过邀请码查询用户
	 */
	List<UserDTO> getUserByInvitationCode(String invitationCode, String appKey) throws MarketingException;

	/**
	 * 根据用户名查询用户列表
	 *
	 * @param userName
	 * @param appKey
	 * @return
	 * @throws MarketingException
	 */
	List<UserDTO> queryUserByUserName(String userName, String appKey) throws MarketingException;

	/**
	 * 根据用户 id 查询用户列表
	 *
	 * @param userIds
	 * @param appKey
	 * @return
	 * @throws MarketingException
	 */
	List<UserDTO> queryByUserIdList(List<Long> userIds, String appKey) throws MarketingException;

	/**
	 * @param mobiles
	 * @param appKey
	 * @return
	 * @throws MarketingException
	 */
	List<UserDTO> queryUserByMobiles(List<String> mobiles, String appKey) throws MarketingException;

	UserDTO getUserById(Long id, String appKey) throws MarketingException;

	UserDTO getUserByName(String name, String appKey) throws MarketingException;

	UserDTO getUserByMobile(String mobile, String appKey) throws MarketingException;
}
