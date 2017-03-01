package com.mockuai.usercenter.core.service.action.userguarantee;

import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.UserResponse;
import com.mockuai.usercenter.common.constant.GuaranteeTypeEnum;
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
public class AddUserGuaranteeAction extends TransAction {
    private static final Logger log = LoggerFactory.getLogger(AddUserGuaranteeAction.class);

    @Resource
    private UserGuaranteeManager userGuaranteeManager;

    @Override
    protected UserResponse doTransaction(RequestContext context) throws UserException {
        log.info("Enter Action [{}]", this.getName());
        UserRequest userRequest = context.getRequest();
        UserGuaranteeDTO userGuaranteeDTO = (UserGuaranteeDTO) userRequest.getParam("userGuaranteeDTO");
        String bizCode = (String) context.get("bizCode");

        //入参检测
        if (bizCode == null) {
            log.error("bizCode is null");
            return new UserResponse(ResponseCode.P_PARAM_NULL, "bizCode is null");
        }

        if (userGuaranteeDTO == null) {
            log.error("userGuaranteeDTO is null, " + userGuaranteeDTO.toString());
            return new UserResponse(ResponseCode.P_PARAM_NULL, "userGuaranteeDTO is null");
        }

        //入参校验
        if (userGuaranteeDTO.getGuaranteeAmount() < 0) {
            log.error("guarantee amount is less than 0, " + userGuaranteeDTO.toString());
            return new UserResponse(ResponseCode.P_PARAM_ERROR, "guarantee amount is less than 0");
        }

        //补齐参数
        userGuaranteeDTO.setBizCode(bizCode);

        if (userGuaranteeDTO.getType() == null) {
            userGuaranteeDTO.setType(GuaranteeTypeEnum.SHOPGUARANTEE.getValue());
        }

        userGuaranteeDTO = this.userGuaranteeManager.addUserGuarantee(userGuaranteeDTO);

        return new UserResponse(userGuaranteeDTO);
    }

    @Override
    public String getName() {
        return ActionEnum.ADD_USER_GUARANTEE.getActionName();
    }
}
