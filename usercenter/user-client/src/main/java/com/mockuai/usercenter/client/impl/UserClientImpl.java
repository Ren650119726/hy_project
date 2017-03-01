package com.mockuai.usercenter.client.impl;

import com.mockuai.usercenter.client.UserClient;
import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.BaseRequest;
import com.mockuai.usercenter.common.api.Request;
import com.mockuai.usercenter.common.api.Response;
import com.mockuai.usercenter.common.api.UserDispatchService;
import com.mockuai.usercenter.common.dto.*;
import com.mockuai.usercenter.common.qto.UserConsigneeQTO;
import com.mockuai.usercenter.common.qto.UserQTO;

import javax.annotation.Resource;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class UserClientImpl implements UserClient {

    @Resource
    private UserDispatchService userDispatchService;

    public Response<Boolean> activeUser(Long userId, String appKey) {
        Request request = new BaseRequest();

        request.setParam("userId", userId);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.ACTIVE_USER.getActionName());
        Response<Boolean> response = userDispatchService.execute(request);
        return response;
    }

    public Response<Void> deletedUser(Long userId, String appKey) {
        Request request = new BaseRequest();
        request.setParam("userId", userId);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.DELETE_USER.getActionName());
        Response<Void> response = userDispatchService.execute(request);
        return response;
    }

    public Response<Boolean> freezeUser(Long userId, String appKey) {
        Request request = new BaseRequest();
        request.setParam("userId", userId);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.FREEZE_USER.getActionName());
        Response<Boolean> response = userDispatchService.execute(request);
        return response;
    }

    public Response<Boolean> thawUser(Long userId, String appKey) {
        Request request = new BaseRequest();
        request.setParam("userId", userId);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.THAW_USER.getActionName());
        Response<Boolean> response = userDispatchService.execute(request);
        return response;
    }

    public Response<UserDTO> getUserByEmail(String email, String appKey) {
        Request request = new BaseRequest();
        request.setParam("email", email);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.GET_USER_BY_EMAIL.getActionName());
        Response<UserDTO> response = userDispatchService.execute(request);
        return response;
    }

    public Response<UserDTO> getUserById(Long userId, String appKey) {
        Request request = new BaseRequest();
        request.setParam("userId", userId);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.GET_USER_BY_ID.getActionName());
        Response<UserDTO> response = userDispatchService.execute(request);
        return response;
    }

    public Response<UserDTO> getUserByMobile(String mobile, String appKey) {
        Request request = new BaseRequest();
        request.setParam("mobile", mobile);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.GET_USER_BY_MOBILE.getActionName());
        Response<UserDTO> response = userDispatchService.execute(request);
        return response;
    }

    public Response<UserDTO> getUserByName(String name, String appKey) {
        Request request = new BaseRequest();
        request.setParam("name", name);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.GET_USER_BY_NAME.getActionName());
        Response<UserDTO> response = userDispatchService.execute(request);
        return response;
    }

    public Response<UserDTO> addUser(UserDTO userDTO, String appKey) {
        Request request = new BaseRequest();
        request.setParam("userDTO", userDTO);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.ADD_USER.getActionName());
        Response<UserDTO> response = userDispatchService.execute(request);
        return response;
    }

    public Response<UserDTO> addOpenUser(UserDTO userDTO, String appKey) {
        Request request = new BaseRequest();
        request.setParam("userDTO", userDTO);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.ADD_OPEN_USER.getActionName());
        Response<UserDTO> response = userDispatchService.execute(request);
        return response;
    }

    public Response<Boolean> moveUserIntoRecycle(Long userId, String appKey) {
        Request request = new BaseRequest();
        request.setParam("userId", userId);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.MOVE_USER.getActionName());
        Response<Boolean> response = userDispatchService.execute(request);
        return response;
    }

    public Response<Boolean> restoreUser(Long userId, String appKey) {
        Request request = new BaseRequest();
        request.setParam("userId", userId);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.RESTORE_USER.getActionName());
        Response<Boolean> response = userDispatchService.execute(request);
        return response;
    }

    public Response<Boolean> setUserRole(Long userId, Byte role, String appKey) {
        Request request = new BaseRequest();
        request.setParam("userId", userId);
        request.setParam("role", role);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.SET_ROLE.getActionName());
        Response<Boolean> response = userDispatchService.execute(request);
        return response;
    }

    public Response<Boolean> updateEmail(Long userId, String email, String appKey) {
        Request request = new BaseRequest();
        request.setParam("userId", userId);
        request.setParam("email", email);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.UPDATE_EMAIL.getActionName());
        Response<Boolean> response = userDispatchService.execute(request);
        return response;
    }

    public Response<Boolean> updateUser(UserDTO userDTO, String appKey) {
        Request request = new BaseRequest();
        request.setParam("userDTO", userDTO);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.UPDATE_USER.getActionName());
        Response<Boolean> response = userDispatchService.execute(request);
        return response;
    }
    
    /**
     * 更新用户手机号
     * 
     * @author csy
     * @Date 2016-05-13
     * 
     * @param userId
     * @param mobile
     * @param verify_code
     * @param appKey
     * @return
     */
    @SuppressWarnings("unchecked")
	public Response<Boolean> updateMobile(Long userId, String mobile, String verify_code,String appKey) {
        Request request = new BaseRequest();

        request.setParam("userId", userId);
        request.setParam("mobile", mobile);
        request.setParam("verify_code", verify_code);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.UPDATE_MOBILE.getActionName());
        Response<Boolean> response = userDispatchService.execute(request);
        return response;
    }
    
    /**
     * 更新用户登录密码
     * 
     * @author csy
     * @Date 2016-05-12
     * 
     */
    @SuppressWarnings("unchecked")
	public Response<Void> updatePwd(Long userId, String newPwd,String mobile,String verify_code, String appKey) {
        Request request = new BaseRequest();
        request.setParam("userId", userId);
        request.setParam("newPwd", newPwd);
        request.setParam("mobile", mobile);
        request.setParam("verify_code", verify_code);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.UPDATE_PWD.getActionName());
        Response<Void> response = userDispatchService.execute(request);
        return response;
    }

    public Response<Boolean> updateHeadImg(Long userId, String headImg, String appKey) {
        Request request = new BaseRequest();
        request.setParam("userId", userId);
        request.setParam("headImg", headImg);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.UPDATE_HEADIMG.getActionName());
        Response<Boolean> response = userDispatchService.execute(request);
        return response;
    }

    public Response<Boolean> updateInvitationCode(Long userId, String invitationCode, String appKey) {
        Request request = new BaseRequest();
        request.setParam("userId", userId);
        request.setParam("invitationCode", invitationCode);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.UPDATE_INVITATION_CODE.getActionName());
        Response<Boolean> response = userDispatchService.execute(request);
        return response;
    }

    public Response<String> generateInvitationCode(String appKey) {
        Request request = new BaseRequest();
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.GENERATE_INVITATION_CODE.getActionName());
        Response<String> response = userDispatchService.execute(request);
        return response;
    }

    public Response<List<UserDTO>> queryUser(UserQTO userQTO, String appKey) {
        Request request = new BaseRequest();
        request.setParam("userQTO", userQTO);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.QUERY_USER.getActionName());
        Response<List<UserDTO>> response = userDispatchService.execute(request);
        return response;
    }

    public Response<List<UserDTO>> queryNormalAndOldUser(UserQTO userQTO, String appKey) {
        Request request = new BaseRequest();
        request.setParam("userQTO", userQTO);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.QUERY_NORMAL_OLD_USER.getActionName());
        Response<List<UserDTO>> response = userDispatchService.execute(request);
        return response;
    }

    public Response<UserDTO> apiUserLogin(UserInfoDTO userInfoDTO, String appKey) {
        Request request = new BaseRequest();
        request.setParam("userInfoDto", userInfoDTO);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.API_USER_LOGIN.getActionName());
        Response<UserDTO> response = userDispatchService.execute(request);
        return response;
    }

    public Response<UserDTO> userLogin(String loginName, String loginPwd, String appKey) {
        Request request = new BaseRequest();
        request.setParam("loginName", loginName);
        request.setParam("loginPwd", loginPwd);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.USER_LOGIN.getActionName());
        Response<UserDTO> response = userDispatchService.execute(request);
        return response;
    }

    public Response<UserOpenInfoDTO> getUserOpenInfo(Integer openType, String openUid, String appKey) {
        Request request = new BaseRequest();
        request.setParam("openType", openType);
        request.setParam("openUid", openUid);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.GET_USER_OPEN_INFO.getActionName());
        Response<UserOpenInfoDTO> response = userDispatchService.execute(request);
        return response;
    }

    public Response<UserOpenInfoDTO> getOpenInfoByOpenId(String openId, String appId, String appKey) {
        Request request = new BaseRequest();
        request.setParam("openId", openId);
        request.setParam("appId", appId);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.GET_OPEN_INFO_BY_OPEN_ID.getActionName());
        Response<UserOpenInfoDTO> response = userDispatchService.execute(request);
        return response;
    }

    public Response<UserOpenInfoDTO> addUserOpenInfo(UserOpenInfoDTO userOpenInfoDTO, String appKey) {
        Request request = new BaseRequest();
        request.setParam("userOpenInfoDTO", userOpenInfoDTO);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.ADD_USER_OPEN_INFO.getActionName());
        Response<UserOpenInfoDTO> response = userDispatchService.execute(request);
        return response;
    }

    public Response<Void> bindUserOpenInfo(Long openInfoId, Integer openType, String openUid,
                                           String mobile, String password, String invitationCode, String appKey) {
        Request request = new BaseRequest();
        request.setParam("openInfoId", openInfoId);
        request.setParam("openType", openType);
        request.setParam("openUid", openUid);
        request.setParam("mobile", mobile);
        request.setParam("password", password);
        request.setParam("invitationCode", invitationCode);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.BIND_USER_OPEN_INFO.getActionName());
        Response<Void> response = userDispatchService.execute(request);
        return response;
    }

    public Response<List<UserAccountDTO>> queryUserAccountByDevice(Date startTime, Date endTime, Integer appType, String appKey) {
        Request request = new BaseRequest();
        request.setParam("appType", appType);
        UserQTO userQTO = new UserQTO();
        userQTO.setStartTime(startTime);
        userQTO.setEndTime(endTime);
        request.setParam("userQTO", userQTO);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.QUERY_USER_BY_DEVICE.getActionName());
        Response<List<UserAccountDTO>> response = userDispatchService.execute(request);
        return response;
    }

    public Response<List<UserDTO>> queryFromIdList(List<Long> idList, String appKey) {
        Request request = new BaseRequest();
        request.setParam("idList", idList);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.QUERY_USER_FROM_ID_LIST.getActionName());
        Response<List<UserDTO>> response = userDispatchService.execute(request);
        return response;
    }

    public Response<UserOpenInfoDTO> getOpenInfoByUserId(Long userId, String appKey) {
        Request request = new BaseRequest();
        request.setParam("userId", userId);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.GET_USER_OPEN_INFO_BY_USER_ID.getActionName());
        Response<UserOpenInfoDTO> response = userDispatchService.execute(request);
        return response;
    }

    public Response<List<UserDTO>> queryUserByMobiles(List<String> mobiles, String appKey) {
        Request request = new BaseRequest();
        request.setParam("mobiles", mobiles);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.QUERY_USER_BY_MOBILES.getActionName());
        Response<List<UserDTO>> response = userDispatchService.execute(request);
        return response;
    }


    public Response<Boolean> updateRoleType(Long userId, Long newRoleType, String appKey) {
        Request request = new BaseRequest();
        request.setParam("userId", userId);
        request.setParam("roleType", newRoleType);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.UPDATE_ROLE_TYPE.getActionName());
        Response<Boolean> response = this.userDispatchService.execute(request);
        return response;
    }

    public Response<Boolean> updateUserType(Long userId, Integer newUserType, String appKey) {
        Request request = new BaseRequest();
        request.setParam("userId", userId);
        request.setParam("userType", newUserType);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.UPDATE_USER_TYPE.getActionName());
        Response<Boolean> response = this.userDispatchService.execute(request);
        return response;
    }

    public Response<List<UserDTO>> totalValidUsers(Date start, Date end, Long offset, Integer count, String appKey) {
        Request request = new BaseRequest();
        request.setParam("start",start);
        request.setParam("end",end);
        request.setParam("offset",offset);
        request.setParam("count",count);
        request.setParam("appKey",appKey);
        request.setCommand(ActionEnum.TOTAL_VALID_USERS.getActionName());
        Response<List<UserDTO>> response = this.userDispatchService.execute(request);
        return response;
    }

    public Response<Void> putSession(String sessionKey, Object sessionObject, String appKey) {
        Request request = new BaseRequest();
        request.setParam("sessionKey",sessionKey);
        request.setParam("sessionObject",sessionObject);
        request.setParam("appKey",appKey);
        request.setCommand(ActionEnum.PUT_SESSION.getActionName());
        Response<Void> response = this.userDispatchService.execute(request);
        return response;
    }

    public Response<Object> getSession(String sessionKey, String appKey) {
        Request request = new BaseRequest();
        request.setParam("sessionKey",sessionKey);
        request.setParam("appKey",appKey);
        request.setCommand(ActionEnum.GET_SESSION.getActionName());
        Response<Object> response = this.userDispatchService.execute(request);
        return response;
    }
    
    
	@SuppressWarnings("unchecked")
	public Response<UserDTO> getUserByLoginName(String loginName, String appKey) {
		Request request = new BaseRequest();
        request.setParam("loginName",loginName);
        request.setParam("appKey",appKey);
        request.setCommand(ActionEnum.USER_LOGIN.getActionName());
        Response<UserDTO> response = this.userDispatchService.execute(request);
        return response;
	}

	public Response<UserDTO> userRegister(Map<Object, Object> userDtoMap, String appKey) {
		Request userReq = new BaseRequest();
		userReq.setParam("mobile", userDtoMap.get("mobile"));
        userReq.setParam("verifyCode", userDtoMap.get("verifyCode"));
        userReq.setParam("password", userDtoMap.get("password"));
        userReq.setParam("invitationId", userDtoMap.get("invitationId"));
        userReq.setParam("registerFlag", userDtoMap.get("registerFlag"));
        userReq.setParam("appType", userDtoMap.get("appType"));
        userReq.setParam("inviterId", userDtoMap.get("inviterId"));
        userReq.setParam("appKey", appKey);        
        userReq.setCommand(ActionEnum.USER_REGISTER.getActionName());
        Response<UserDTO> response = this.userDispatchService.execute(userReq);
	    return response;
	}
	
	public Response<Boolean> UpdateUserLoginInfoMiss(Long userId, String mobile, String password, String verifyCode, String invitationCode,String appKey) {
        Request userReq = new BaseRequest();
        userReq.setParam("user_id", userId);
        userReq.setParam("mobile", mobile);
        userReq.setParam("password", password);        
        userReq.setParam("verifyCode", verifyCode);        
        userReq.setParam("invitationCode", invitationCode);        
        userReq.setParam("appKey", appKey);
        userReq.setCommand(ActionEnum.UPDATEUSERLOGININFOMISS.getActionName());
        Response<Boolean> response = this.userDispatchService.execute(userReq);
        return response;
    }
	
	public Response<Void> updateLastDistributorId(Long userId, Long sellerId, String appKey) {
		 Request userReq = new BaseRequest();
	     userReq.setParam("user_id", userId);
	     userReq.setParam("seller_id", sellerId);        
	     userReq.setParam("appKey", appKey);
	     userReq.setCommand(ActionEnum.UPDATELASTDISTRIBUTORID.getActionName());
	     Response<Void> response = this.userDispatchService.execute(userReq);
	     return response;
	}

	public Response<Void> updateInviterId(Long userId, Long inviterId, String appKey) {
		 Request userReq = new BaseRequest();
	     userReq.setParam("user_id", userId);
	     userReq.setParam("inviter_id", inviterId);        
	     userReq.setParam("appKey", appKey);
	     userReq.setCommand(ActionEnum.UPDATE_INVITER_ID.getActionName());
	     Response<Void> response = this.userDispatchService.execute(userReq);
	     return response;
	}


    /**
     * HSQ
     * @param userId
     * @param inviterId
     * @param appKey
     * @return
     */
    public Response<Void> addFansToInviterId(Long userId, Long inviterId, String appKey) {
        Request userReq = new BaseRequest();
        userReq.setParam("userId",userId);
        userReq.setParam("inviterId", inviterId);
        userReq.setParam("appKey", appKey);
        userReq.setCommand(ActionEnum.ADD_FANS_TO_INVITER_ID.getActionName());
        Response<Void> response = this.userDispatchService.execute(userReq);
        return response;
    }


    public Response<Long> validateInviterId(Long userId, Long inviterId, String appKey) {
        Request userReq = new BaseRequest();
        userReq.setParam("userId",userId);
        userReq.setParam("inviterId", inviterId);
        userReq.setParam("appKey", appKey);
        userReq.setCommand(ActionEnum.VALIDATE_INVITER_ID.getActionName());
        Response<Long> response = this.userDispatchService.execute(userReq);
        return response;
    }

    public Response<Void> checkUserPayPwd(Long userId, String payPwd, String appKey) {
		 Request userReq = new BaseRequest();
	     userReq.setParam("user_id", userId);
	     userReq.setParam("pay_pwd", payPwd);        
	     userReq.setParam("appKey", appKey);
	     userReq.setCommand(ActionEnum.CHECKUSEROLDPAYPWD.getActionName());
	     Response<Void> response = this.userDispatchService.execute(userReq);
	     return response;
	}

    public Response<List<UserConsigneeDTO>> queryAllConsignee(UserConsigneeQTO userConsigneeQTO, String appKey) {
        Request request = new BaseRequest();
        request.setParam("userConsigneeQTO",userConsigneeQTO);
        request.setParam("appKey",appKey);
        request.setCommand(ActionEnum.QUERY_ALL_CONSIGNEE.getActionName());
        Response<List<UserConsigneeDTO>> response = this.userDispatchService.execute(request);
        return response;
    }

	@SuppressWarnings("unchecked")
	public Response<List<UserDTO>> queryInviterListByUserId(UserQTO userQTO, String appKey) {
		Request request = new BaseRequest();
        request.setParam("userQTO",userQTO);
        request.setParam("appKey",appKey);
        request.setCommand(ActionEnum.QUERY_INVITERLISTBYUSERID.getActionName());
        Response<List<UserDTO>> response = this.userDispatchService.execute(request);
        return response;
	}

	@SuppressWarnings("unchecked")
	public Response<UserDTO> queryHiKeCondition(Long userId, String appKey) {
		Request request = new BaseRequest();
		request.setParam("user_id", userId);
		request.setParam("appKey", appKey);        
		request.setCommand(ActionEnum.QUERY_HIKECONDITION.getActionName());
        Response<UserDTO> response = this.userDispatchService.execute(request);
        return response;
	}
}
