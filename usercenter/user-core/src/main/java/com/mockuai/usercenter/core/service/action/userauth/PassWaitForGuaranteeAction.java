package com.mockuai.usercenter.core.service.action.userauth;

import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.UserResponse;
import com.mockuai.usercenter.common.constant.GuaranteeTypeEnum;
import com.mockuai.usercenter.common.constant.ResponseCode;
import com.mockuai.usercenter.common.constant.UserAuthStatus;
import com.mockuai.usercenter.common.dto.UserAuthInfoDTO;
import com.mockuai.usercenter.common.dto.UserGuaranteeDTO;
import com.mockuai.usercenter.core.exception.UserException;
import com.mockuai.usercenter.core.manager.UserAuthInfoManager;
import com.mockuai.usercenter.core.manager.UserGuaranteeManager;
import com.mockuai.usercenter.core.service.RequestContext;
import com.mockuai.usercenter.core.service.UserRequest;
import com.mockuai.usercenter.core.service.action.TransAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 用户认证通过后,等待交纳保证金
 */
@Service
public class PassWaitForGuaranteeAction extends TransAction {
    private static final Logger log = LoggerFactory.getLogger(PassWaitForGuaranteeAction.class);


    @Resource
    private UserAuthInfoManager userAuthInfoManager;
    @Resource
    private UserGuaranteeManager userGuaranteeManager;

    @Override
    protected UserResponse doTransaction(RequestContext context) throws UserException {
        UserRequest userRequest = context.getRequest();
        Long userAuthId = (Long) userRequest.getParam("userAuthId");
        Long userId = (Long) userRequest.getParam("userId");
        Long guaranteeAmount = (Long)userRequest.getParam("guaranteeAmount");
//        String remark = (String) userRequest.getParam("remark");// 备注
        String bizCode = (String) context.get("bizCode");


        //入参检测
        if (userAuthId == null) {
            log.error("userAuthId is null");
            throw new UserException(ResponseCode.P_PARAM_NULL, "userAuthId is null");
        }
        if (userId == null) {
            log.error("userId is null");
            throw new UserException(ResponseCode.P_PARAM_NULL, "userId is null");
        }

        //更改认证状态位
        UserAuthInfoDTO userAuthInfoDTO = new UserAuthInfoDTO();
        userAuthInfoDTO.setId(userAuthId);
        userAuthInfoDTO.setUserId(userId);
//        userAuthInfoDTO.setRemark(remark);
        userAuthInfoDTO.setStatus(UserAuthStatus.WAIT_GUARANTEE.getValue());//等待交纳保证金状态
        userAuthInfoManager.passWaitForGuarantee(userAuthInfoDTO);
        //添加保证金记录
        UserGuaranteeDTO userGuaranteeDTO = new UserGuaranteeDTO();
        userGuaranteeDTO.setUserId(userId);
        userGuaranteeDTO.setGuaranteeAmount(guaranteeAmount);
        userGuaranteeDTO.setBizCode(bizCode);
        userGuaranteeDTO.setType(GuaranteeTypeEnum.SHOPGUARANTEE.getValue());
        userGuaranteeManager.addUserGuarantee(userGuaranteeDTO);

        return new UserResponse(true);
    }

    @Override
    public String getName() {
        return ActionEnum.WAIT_FOR_GUARANTEE.getActionName();
    }

}
