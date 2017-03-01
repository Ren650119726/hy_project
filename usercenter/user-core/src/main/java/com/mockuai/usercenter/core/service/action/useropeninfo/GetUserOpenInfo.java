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
public class GetUserOpenInfo implements Action {

    @Resource
    private UserOpenInfoManager userOpenInfoManager;

    @SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
    public UserResponse execute(RequestContext context) throws UserException {
        UserRequest userRequest = context.getRequest();
        Integer openType = (Integer)userRequest.getParam("openType");
        String openUid = (String)userRequest.getParam("openUid");
        UserOpenInfoDO userOpenInfoDO = userOpenInfoManager.getUserOpenInfo(openType, openUid);
        return new UserResponse(ModelUtil.convertToUserOpenInfoDTO(userOpenInfoDO));
    }

    @Override
    public String getName() {
        return ActionEnum.GET_USER_OPEN_INFO.getActionName();
    }

}
