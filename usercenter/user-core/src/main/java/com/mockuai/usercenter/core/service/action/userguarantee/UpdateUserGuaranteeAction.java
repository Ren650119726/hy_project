package com.mockuai.usercenter.core.service.action.userguarantee;

import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.UserResponse;
import com.mockuai.usercenter.common.constant.ResponseCode;
import com.mockuai.usercenter.common.dto.UserGuaranteeDTO;
import com.mockuai.usercenter.core.exception.UserException;
import com.mockuai.usercenter.core.manager.UserGuaranteeManager;
import com.mockuai.usercenter.core.service.RequestContext;
import com.mockuai.usercenter.core.service.UserRequest;
import com.mockuai.usercenter.core.service.action.TransAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by yeliming on 16/1/23.
 */
@Service
public class UpdateUserGuaranteeAction extends TransAction {
    private static final Logger log = LoggerFactory.getLogger(UpdateUserGuaranteeAction.class);

    @Resource
    private UserGuaranteeManager userGuaranteeManager;

    @Override
    protected UserResponse doTransaction(RequestContext context) throws UserException {
        log.info("Enter Action [{}]", this.getName());
        UserRequest userRequest = context.getRequest();
        UserGuaranteeDTO userGuaranteeDTO = (UserGuaranteeDTO) userRequest.getParam("userGuaranteeDTO");
        String bizCode = (String)context.get("bizCode");

        //入参校验
        if(bizCode == null){
            log.error("bizCode is null");
            return new UserResponse(ResponseCode.P_PARAM_NULL, "bizCode is null");
        }
        userGuaranteeDTO.setBizCode(bizCode);
        if(userGuaranteeDTO == null){
            log.error("userGuaranteeDTO is null, bizCode = {}", bizCode);
            return new UserResponse(ResponseCode.P_PARAM_NULL, "userGuaranteeDTO is null");
        }
        if(userGuaranteeDTO.getId() == null){
            log.error("id is null");
            return new UserResponse(ResponseCode.P_PARAM_NULL, "id is null");
        }

        this.userGuaranteeManager.updateUserGuarantee(userGuaranteeDTO);
        return new UserResponse(true);
    }

    @Override
    public String getName() {
        return ActionEnum.UPDATE_USER_GUARANTEE.getActionName();
    }
}
