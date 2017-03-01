package com.mockuai.marketingcenter.core.manager.impl;

import com.mockuai.marketingcenter.common.constant.ResponseCode;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.marketingcenter.core.manager.UserManager;
import com.mockuai.marketingcenter.core.util.JsonUtil;
import com.mockuai.usercenter.client.UserClient;
import com.mockuai.usercenter.common.api.Response;
import com.mockuai.usercenter.common.dto.UserDTO;
import com.mockuai.usercenter.common.qto.UserQTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * Created by duke on 15/9/28.
 */
@Service
public class UserManagerImpl implements UserManager {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserManagerImpl.class.getName());

	@Resource
	private UserClient userClient;

	@Override
	public List<UserDTO> getUserByInvitationCode(String invitationCode, String appKey) throws MarketingException {
		try {
			UserQTO userQTO = new UserQTO();
			userQTO.setInvitationCode(invitationCode);
			Response<List<UserDTO>> response = userClient.queryUser(userQTO, appKey);
			if (response.isSuccess()) {
				return response.getModule();
			} else {
				LOGGER.error("error to getUserByInvitationCode, invitationCode : {}, appKey : {}, errCode : {}, errMsg : {}"
						, invitationCode, appKey, response.getCode(), response.getMessage());
				throw new MarketingException(response.getCode(), response.getMessage());
			}
		} catch (MarketingException e) {
			throw e;
		} catch (Exception e) {
			LOGGER.error("error to getUserByInvitationCode, invitationCode : {}, appKey : {}", invitationCode, appKey, e);
			throw new MarketingException(ResponseCode.SERVICE_EXCEPTION);
		}
	}

	@Override
	public List<UserDTO> queryUserByUserName(String userName, String appKey) throws MarketingException {

		try {
			UserQTO userQTO = new UserQTO();
			userQTO.setName(userName);
			Response<List<UserDTO>> response = userClient.queryUser(userQTO, appKey);
			if (response.isSuccess()) {
				return response.getModule();
			}
			LOGGER.error("error to queryUserByUserName, userName : {}, errCode : {}, errMsg : {}, appKey : {}",
					userName, response.getCode(), response.getMessage(), appKey);
			throw new MarketingException(ResponseCode.SERVICE_EXCEPTION, response.getMessage());
		} catch (MarketingException e) {
			throw e;
		} catch (Exception e) {
			LOGGER.error("failed when queryUserByUserName, userName : {}, appKey : {}", userName, appKey, e);
			throw new MarketingException(ResponseCode.SERVICE_EXCEPTION);
		}
	}

	@Override
	public List<UserDTO> queryByUserIdList(List<Long> userIds, String appKey) throws MarketingException {
		try {
			Response<List<UserDTO>> response = userClient.queryFromIdList(userIds, appKey);
			if (response.isSuccess()) {
				return response.getModule();
			}
			throw new MarketingException(ResponseCode.SERVICE_EXCEPTION, response.getMessage());
		} catch (Exception e) {
			LOGGER.error("failed when queryByUserIdList, userIdList : {}, appKey : {}", Arrays.deepToString(userIds.toArray()), appKey, e);
			throw new MarketingException(ResponseCode.SERVICE_EXCEPTION);
		}
	}

	@Override
	public List<UserDTO> queryUserByMobiles(List<String> mobiles, String appKey) throws MarketingException {
		try {
			Response<List<UserDTO>> response = userClient.queryUserByMobiles(mobiles, appKey);
			if (response.isSuccess()) {
				return response.getModule();
			}
			LOGGER.error("error to queryUserByMobiles, mobiles : {}, appKey : {}, errCode : {}, errMsg : {}",
					JsonUtil.toJson(mobiles), appKey, response.getCode(), response.getMessage());
			throw new MarketingException(ResponseCode.SERVICE_EXCEPTION, response.getMessage());
		} catch (MarketingException e) {
			throw e;
		} catch (Exception e) {
			LOGGER.error("error to queryUserByMobiles, mobiles : {}, appKey : {}",
					Arrays.deepToString(mobiles.toArray()), appKey, e);
			throw new MarketingException(ResponseCode.SERVICE_EXCEPTION);
		}
	}

	@Override
	public UserDTO getUserById(Long id, String appKey) throws MarketingException {
		Response<UserDTO> response = userClient.getUserById(id, appKey);
		if (response.isSuccess()) {
			return response.getModule();
		} else {
			LOGGER.error("get user error, userId: {}", id);
			throw new MarketingException(ResponseCode.SERVICE_EXCEPTION);
		}
	}

	@Override
	public UserDTO getUserByName(String name, String appKey) throws MarketingException {
		try {

			Response<UserDTO> response = userClient.getUserByName(name, appKey);
			if (response.isSuccess()) {
				return response.getModule();
			} else {
				LOGGER.error("get user error, name: {}, errCode : {}, errMsg : {}"
						, name, response.getCode(), response.getMessage());
				throw new MarketingException(ResponseCode.SERVICE_EXCEPTION);
			}
		} catch (MarketingException e) {
			throw e;
		} catch (Exception e) {
			LOGGER.error("get user error, name: {}, errCode : {}, errMsg : {}", name, e);
			throw new MarketingException(ResponseCode.SERVICE_EXCEPTION);
		}
	}

	@Override
	public UserDTO getUserByMobile(String mobile, String appKey) throws MarketingException {
		try {
			Response<UserDTO> response = userClient.getUserByMobile(mobile, appKey);
			if (response.isSuccess()) {
				return response.getModule();
			} else {
				LOGGER.error("error to getUserByMobile, mobile: {}, errCode : {}, errMsg : {}"
						, mobile, response.getCode(), response.getMessage());
				throw new MarketingException(response.getCode(), response.getMessage());
			}
		} catch (MarketingException e) {
			throw e;
		} catch (Exception e) {
			LOGGER.error("error to getUserByMobile, mobile: {}", mobile, e);
			throw new MarketingException(ResponseCode.SERVICE_EXCEPTION);
		}
	}
}