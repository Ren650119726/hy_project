package com.mockuai.usercenter.core.service.action.datamigrate;

import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.UserResponse;
import com.mockuai.usercenter.common.constant.ResponseCode;
import com.mockuai.usercenter.common.dto.UserDTO;
import com.mockuai.usercenter.common.dto.datamigrate.MigrateUserDTO;
import com.mockuai.usercenter.common.dto.datamigrate.MigrateUserOpenInfoDTO;
import com.mockuai.usercenter.core.domain.UserDO;
import com.mockuai.usercenter.core.domain.UserOpenInfoDO;
import com.mockuai.usercenter.core.exception.UserException;
import com.mockuai.usercenter.core.manager.DataMigrateManager;
import com.mockuai.usercenter.core.service.RequestContext;
import com.mockuai.usercenter.core.service.UserRequest;
import com.mockuai.usercenter.core.service.action.Action;
import com.mockuai.usercenter.core.util.ModelUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by zengzhangqiang on 6/16/15.
 */
@Service
public class GetUserByOrigin implements Action {

    @Resource
    private DataMigrateManager dataMigrateManager;

    @Override
    public UserResponse execute(RequestContext context) throws UserException {

        UserRequest userRequest = context.getRequest();
        Long originUserId = (Long)userRequest.getParam("originUserId");

        if(originUserId == null){
            return new UserResponse(ResponseCode.P_PARAM_NULL, "originUserId is null");
        }

        //TODO 当有多个应用接入时，这里查询的时候需要基于bizCode做隔离

        try{
            UserDO userDO = dataMigrateManager.getUserByOrigin(originUserId);
            MigrateUserDTO migrateUserDTO = ModelUtil.convertToMigrateUserDTO(userDO);
            return new UserResponse(migrateUserDTO);
        }catch(UserException e){
            return new UserResponse(e.getResponseCode(), e.getMessage());
        }
    }

    @Override
    public String getName() {
        return ActionEnum.GET_USER_BY_ORIGIN.getActionName();
    }
}
