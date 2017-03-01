package com.mockuai.usercenter.core.service.action.useropeninfo;

import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.UserResponse;
import com.mockuai.usercenter.common.constant.ResponseCode;
import com.mockuai.usercenter.core.domain.UserOpenInfoDO;
import com.mockuai.usercenter.core.exception.UserException;
import com.mockuai.usercenter.core.manager.UserOpenInfoManager;
import com.mockuai.usercenter.core.service.RequestContext;
import com.mockuai.usercenter.core.service.UserRequest;
import com.mockuai.usercenter.core.service.action.Action;
import com.mockuai.usercenter.core.util.ModelUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by zengzhangqiang on 7/6/15.
 */
@Service
public class ReleaseUserOpenInfo implements Action {
    private static final Logger log = LoggerFactory.getLogger(ReleaseUserOpenInfo.class);

    @Resource
    private UserOpenInfoManager userOpenInfoManager;

    @Override
    public UserResponse execute(RequestContext context) throws UserException {
        String bizCode = (String)context.get("bizCode");
        Integer openType = (Integer)context.getRequest().getParam("openType");
        Long userId = (Long)context.getRequest().getParam("userId");

        //查询指定第三方账号信息
        UserOpenInfoDO userOpenInfoDO = userOpenInfoManager.getUserOpenInfoByUserId(openType, userId, bizCode);
        if(userOpenInfoDO == null){
            return new UserResponse(ResponseCode.B_OPEN_ACCOUNT_NOT_EXIST);
        }

        //删除指定的第三方账号信息
        int opNum = userOpenInfoManager.deleteUserOpenInfo(userOpenInfoDO.getId(), userOpenInfoDO.getUserId());

        if(opNum != 1){
            //TODO error handle
            log.error("error to delete userOpenInfo");
            return new UserResponse(ResponseCode.SYS_E_SERVICE_EXCEPTION);
        }

        return new UserResponse(ResponseCode.REQUEST_SUCCESS);

    }

    @Override
    public String getName() {
        return ActionEnum.RELEASE_USER_OPEN_INFO.getActionName();
    }

}

