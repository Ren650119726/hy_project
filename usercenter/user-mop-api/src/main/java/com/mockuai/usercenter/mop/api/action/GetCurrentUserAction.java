package com.mockuai.usercenter.mop.api.action;

import com.mockuai.customer.common.dto.MemberDTO;
import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.constant.MopRespCode;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.mop.common.service.action.Request;
import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.BaseRequest;
import com.mockuai.usercenter.common.api.UserResponse;
import com.mockuai.usercenter.common.dto.UserDTO;
import com.mockuai.usercenter.mop.api.domain.MopUserDTO;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.BeanUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by duke on 15/12/17.
 */
public class GetCurrentUserAction extends BaseAction {

    public MopResponse execute(Request request) {
        Long userId = (Long)request.getAttribute("user_id");
        String appKey = (String)request.getParam("app_key");

        try{
            BaseRequest baseRequest = new BaseRequest();
            baseRequest.setParam("userId", userId);
            baseRequest.setParam("appKey", appKey);
            baseRequest.setCommand(ActionEnum.GET_USER_BY_ID.getActionName());
            UserResponse<UserDTO> response = getUserDispatchService().execute(baseRequest);

            if(!response.isSuccess()) {
                return new MopResponse(response.getCode(), response.getMessage());
            } else {
                baseRequest.setCommand(ActionEnum.GET_CUSTOMER_BY_USER_ID.getActionName());
                UserResponse<MemberDTO> userResponse = getUserDispatchService().execute(baseRequest);
                if (response.isSuccess()) {
                    Map<String, Object> data = new HashMap<String, Object>();                    
                    MopUserDTO mopUserDTO = genMopUserDTO(response.getModule());
                    
                    if (userResponse.getModule() != null) {
                        mopUserDTO.setLevelName(userResponse.getModule().getLevelName());
                    } else {
                        mopUserDTO.setLevelName("");
                    }
                    
                    //判断手机号
                    if(mopUserDTO.getMobile().contains("wx")){
                    	mopUserDTO.setMobile("");
                    }
                    
                    //判断密码
                    if(mopUserDTO.getPassword().equals(DigestUtils.md5Hex(DigestUtils.md5Hex("wx_123_wx")))){
                    	mopUserDTO.setPassword("");
                    }
                    
                    data.put("user", mopUserDTO);
                    return new MopResponse(data);
                } else {
                    return new MopResponse(userResponse.getCode(), userResponse.getMessage());
                }
            }
        } catch (Exception e) {
            return new MopResponse(MopRespCode.S_E_SERVICE_ERROR);
        }
    }

    public MopUserDTO genMopUserDTO(UserDTO userDTO){
        MopUserDTO mopUserDTO = new MopUserDTO();
        BeanUtils.copyProperties(userDTO, mopUserDTO);
        mopUserDTO.setMobile(userDTO.getMobile());
        mopUserDTO.setUserName(userDTO.getName());
        mopUserDTO.setInvitationCode(userDTO.getInvitationCode());
        return mopUserDTO;
    }


    public String getName() {
        return "/user/current_user/get";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.AUTH_LOGIN;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_GET;
    }
}
