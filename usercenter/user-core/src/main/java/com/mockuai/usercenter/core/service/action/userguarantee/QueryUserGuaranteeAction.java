package com.mockuai.usercenter.core.service.action.userguarantee;

import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.UserResponse;
import com.mockuai.usercenter.common.constant.ResponseCode;
import com.mockuai.usercenter.common.dto.UserGuaranteeDTO;
import com.mockuai.usercenter.common.qto.UserGuaranteeQTO;
import com.mockuai.usercenter.core.exception.UserException;
import com.mockuai.usercenter.core.manager.UserGuaranteeManager;
import com.mockuai.usercenter.core.service.RequestContext;
import com.mockuai.usercenter.core.service.UserRequest;
import com.mockuai.usercenter.core.service.action.TransAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yeliming on 16/1/23.
 */
@Service
public class QueryUserGuaranteeAction extends TransAction {
    private static final Logger log = LoggerFactory.getLogger(QueryUserGuaranteeAction.class);

    @Resource
    private UserGuaranteeManager userGuaranteeManager;

    protected UserResponse doTransaction(RequestContext context) throws UserException {
        log.info("Enter Action [{}]", this.getName());
        UserRequest userRequest = context.getRequest();
        UserGuaranteeQTO userGuaranteeQTO = (UserGuaranteeQTO) userRequest.getParam("userGuaranteeQTO");
        String bizCode = (String) context.get("bizCode");

        //TODO:入参检测
        if (bizCode == null) {
            log.error("bizCode is null");
            return new UserResponse(ResponseCode.P_PARAM_NULL, "bizCode is null");
        }
        userGuaranteeQTO.setBizCode(bizCode);

        if(userGuaranteeQTO == null){
            log.error("userGuaranteeQTO is null, bizCode = {}", bizCode);
            return new UserResponse(ResponseCode.P_PARAM_NULL, "userGuaranteeQTO is null");
        }

        userGuaranteeQTO.setBizCode(bizCode);
        List<UserGuaranteeDTO> userGuaranteeDTOs = this.userGuaranteeManager.queryUserGuarantee(userGuaranteeQTO);
        Long total = this.userGuaranteeManager.totalUserGuarantee(userGuaranteeQTO);

        return new UserResponse(userGuaranteeDTOs, total);
    }

    @Override
    public String getName() {
        return ActionEnum.QUERY_USER_GUARANTEE.getActionName();
    }
}
