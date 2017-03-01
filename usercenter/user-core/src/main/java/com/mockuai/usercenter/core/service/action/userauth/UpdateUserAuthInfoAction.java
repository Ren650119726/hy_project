package com.mockuai.usercenter.core.service.action.userauth;

import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.UserResponse;
import com.mockuai.usercenter.common.constant.ResponseCode;
import com.mockuai.usercenter.common.dto.UserAuthInfoDTO;
import com.mockuai.usercenter.core.domain.EnterpriseAuthExtendDO;
import com.mockuai.usercenter.core.domain.UserAuthInfoDO;
import com.mockuai.usercenter.core.exception.UserException;
import com.mockuai.usercenter.core.manager.EnterpriseAuthExtendManager;
import com.mockuai.usercenter.core.manager.UserAuthInfoManager;
import com.mockuai.usercenter.core.service.RequestContext;
import com.mockuai.usercenter.core.service.UserRequest;
import com.mockuai.usercenter.core.service.action.TransAction;
import com.mockuai.usercenter.core.util.ModelUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by duke on 15/8/21.
 */
@Service
public class UpdateUserAuthInfoAction extends TransAction {
    @Resource
    private UserAuthInfoManager userAuthInfoManager;

    @Resource
    private EnterpriseAuthExtendManager enterpriseAuthExtendManager;

    @Override
    protected UserResponse doTransaction(RequestContext context) throws UserException {
        UserRequest request = context.getRequest();
        UserAuthInfoDTO userAuthInfoDTO = (UserAuthInfoDTO) request.getParam("userAuthInfoDTO");
        String bizCode = (String) context.get("bizCode");

        if (userAuthInfoDTO == null) {
            return new UserResponse(ResponseCode.P_PARAM_NULL, "authInfoDTO is null");
        }

        try {
            userAuthInfoDTO.setBizCode(bizCode);
            UserAuthInfoDO infoDO = ModelUtil.convertToUserAuthInfoDO(userAuthInfoDTO);
            userAuthInfoManager.updateUserAuthInfo(infoDO);

            // 更新企业的认证信息
            if (userAuthInfoDTO.getEnterpriseAuthExtendDTO() != null) {
                EnterpriseAuthExtendDO enterpriseAuthExtendDO = ModelUtil.convertToEnterpriseAuthExtendDO(
                        userAuthInfoDTO.getEnterpriseAuthExtendDTO());
                enterpriseAuthExtendManager.updateEnterpriseAuthExtend(enterpriseAuthExtendDO);
            }
            return new UserResponse(ResponseCode.REQUEST_SUCCESS);
        } catch (UserException err) {
            return new UserResponse(err.getResponseCode(), err.getMessage());
        }
    }

    @Override
    public String getName() {
        return ActionEnum.UPDATE_USER_AUTH_INFO.getActionName();
    }
}
