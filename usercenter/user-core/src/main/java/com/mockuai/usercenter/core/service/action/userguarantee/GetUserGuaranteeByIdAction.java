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
public class GetUserGuaranteeByIdAction extends TransAction {
    private static final Logger log = LoggerFactory.getLogger(GetUserGuaranteeByIdAction.class);

    @Resource
    private UserGuaranteeManager userGuaranteeManager;

    protected UserResponse doTransaction(RequestContext context) throws UserException {
        log.info("Enter Action [{}]", this.getName());
        UserRequest userRequest = context.getRequest();
        Long id = (Long) userRequest.getParam("id");
        String bizCode = (String) context.get("bizCode");

        //TODO:入参检测
        if(bizCode == null){
            log.error("bizCode is null");
            throw new UserException(ResponseCode.P_PARAM_NULL,"bizCode is null");
        }
        if(id == null){
            log.error("id is null");
            throw new UserException(ResponseCode.P_PARAM_NULL,"id is null");
        }

        UserGuaranteeDTO userGuaranteeDTO = this.userGuaranteeManager.getUserGuaranteeById(id);

        return new UserResponse(userGuaranteeDTO);
    }

    @Override
    public String getName() {
        return ActionEnum.GET_USER_GUARANTEE_BY_ID.getActionName();
    }
}
