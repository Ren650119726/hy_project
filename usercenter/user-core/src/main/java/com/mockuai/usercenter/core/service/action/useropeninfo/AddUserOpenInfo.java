package com.mockuai.usercenter.core.service.action.useropeninfo;

import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.UserResponse;
import com.mockuai.usercenter.common.constant.ResponseCode;
import com.mockuai.usercenter.common.dto.UserOpenInfoDTO;
import com.mockuai.usercenter.core.domain.UserOpenInfoDO;
import com.mockuai.usercenter.core.exception.UserException;
import com.mockuai.usercenter.core.manager.UserOpenInfoManager;
import com.mockuai.usercenter.core.service.RequestContext;
import com.mockuai.usercenter.core.service.UserRequest;
import com.mockuai.usercenter.core.service.action.Action;
import com.mockuai.usercenter.core.util.ModelUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by zengzhangqiang on 6/4/15.
 */
@Service
public class AddUserOpenInfo implements Action {

    @Resource
    private UserOpenInfoManager userOpenInfoManager;

    @Override
    public UserResponse execute(RequestContext context) throws UserException {
        String bizCode = (String)context.get("bizCode");

        UserRequest userRequest = context.getRequest();
        UserOpenInfoDTO userOpenInfoDTO = (UserOpenInfoDTO)userRequest.getParam("userOpenInfoDTO");
        if(userOpenInfoDTO == null){
            return new UserResponse(ResponseCode.P_PARAM_NULL, "userOpenInfoDTO is null");
        }
        UserOpenInfoDO userOpenInfoDO = ModelUtil.convertToUserOpenInfoDO(userOpenInfoDTO);
        long userOpenInfoId = userOpenInfoManager.addUserOpenInfo(userOpenInfoDO);
        userOpenInfoDTO.setId(userOpenInfoId);
        return new UserResponse(userOpenInfoDTO);
    }

    @Override
    public String getName() {
        return ActionEnum.ADD_USER_OPEN_INFO.getActionName();
    }

}
