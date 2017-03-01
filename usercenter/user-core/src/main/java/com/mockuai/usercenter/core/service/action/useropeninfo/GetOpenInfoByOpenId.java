package com.mockuai.usercenter.core.service.action.useropeninfo;

import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.UserResponse;
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
public class GetOpenInfoByOpenId implements Action {

    @Resource
    private UserOpenInfoManager userOpenInfoManager;

    @Override
    public UserResponse execute(RequestContext context) throws UserException {
        UserRequest userRequest = context.getRequest();
        String bizCode = (String)context.get("bizCode");
        String openId = (String)userRequest.getParam("openId");
        String appId = (String)userRequest.getParam("appId");
        UserOpenInfoDO userOpenInfoDO = userOpenInfoManager.getOpenInfoByOpenId(openId, appId, bizCode);
        return new UserResponse(ModelUtil.convertToUserOpenInfoDTO(userOpenInfoDO));
    }

    @Override
    public String getName() {
        return ActionEnum.GET_OPEN_INFO_BY_OPEN_ID.getActionName();
    }

}
