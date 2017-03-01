package com.mockuai.usercenter.core.service.action.datamigrate;

import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.UserResponse;
import com.mockuai.usercenter.core.domain.UserOpenInfoDO;
import com.mockuai.usercenter.core.exception.UserException;
import com.mockuai.usercenter.core.manager.DataMigrateManager;
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
public class GetMigrateUserOpenInfo implements Action {

    @Resource
    private DataMigrateManager dataMigrateManager;

    @Override
    public UserResponse execute(RequestContext context) throws UserException {
        UserRequest userRequest = context.getRequest();
        //TODO bizCode先写死，上线前务必改掉
        String bizCode = "ydx";
        Integer openType = (Integer)userRequest.getParam("openType");
        String openUid = (String)userRequest.getParam("openUid");
        UserOpenInfoDO userOpenInfoDO = dataMigrateManager.getUserOpenInfo(openType, openUid);
        return new UserResponse(ModelUtil.convertToUserOpenInfoDTO(userOpenInfoDO));
    }

    @Override
    public String getName() {
        return ActionEnum.GET_MIGRATE_USER_OPEN_INFO.getActionName();
    }

}
