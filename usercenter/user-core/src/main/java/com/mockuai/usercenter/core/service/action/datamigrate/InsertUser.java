package com.mockuai.usercenter.core.service.action.datamigrate;

import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.UserResponse;
import com.mockuai.usercenter.common.constant.ResponseCode;
import com.mockuai.usercenter.common.dto.UserDTO;
import com.mockuai.usercenter.common.dto.datamigrate.MigrateUserDTO;
import com.mockuai.usercenter.core.domain.UserDO;
import com.mockuai.usercenter.core.exception.UserException;
import com.mockuai.usercenter.core.manager.DataMigrateManager;
import com.mockuai.usercenter.core.manager.UserManager;
import com.mockuai.usercenter.core.service.RequestContext;
import com.mockuai.usercenter.core.service.UserRequest;
import com.mockuai.usercenter.core.service.action.Action;
import com.mockuai.usercenter.core.util.HttpUtil;
import com.mockuai.usercenter.core.util.ModelUtil;
import com.mockuai.usercenter.mop.api.util.JsonUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zengzhangqiang on 6/16/15.
 */
@Service
public class InsertUser implements Action {

    @Resource
    private DataMigrateManager dataMigrateManager;

    @Override
    public UserResponse execute(RequestContext context) throws UserException {

        UserRequest userRequest = context.getRequest();
        MigrateUserDTO migrateUserDTO = (MigrateUserDTO) userRequest.getParam("migrateUserDTO");
        //TODO 这里bizCode先写死为yangdongxi
        migrateUserDTO.setBizCode("yangdongxi");
        try{
            UserDO userDO = ModelUtil.convertToUserDO(migrateUserDTO);
            long userId = dataMigrateManager.insertUser(userDO);
            return new UserResponse(userId);
        }catch(UserException e){
            return new UserResponse(e.getResponseCode(), e.getMessage());
        }
    }

    @Override
    public String getName() {
        return ActionEnum.INSERT_USER.getActionName();
    }
}
