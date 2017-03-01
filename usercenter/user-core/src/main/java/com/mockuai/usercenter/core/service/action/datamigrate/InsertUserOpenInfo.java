package com.mockuai.usercenter.core.service.action.datamigrate;

import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.UserResponse;
import com.mockuai.usercenter.common.dto.UserDTO;
import com.mockuai.usercenter.common.dto.UserOpenInfoDTO;
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
public class InsertUserOpenInfo implements Action {

    @Resource
    private DataMigrateManager dataMigrateManager;

    @Override
    public UserResponse execute(RequestContext context) throws UserException {

        UserRequest userRequest = context.getRequest();
        MigrateUserOpenInfoDTO migrateUserOpenInfoDTO =
                (MigrateUserOpenInfoDTO) userRequest.getParam("migrateUserOpenInfoDTO");
        //TODO 这里bizCode先写死为yangdongxi
        migrateUserOpenInfoDTO.setBizCode("yangdongxi");
        try{
            UserOpenInfoDO userOpenInfoDO = ModelUtil.convertToUserOpenInfoDO(migrateUserOpenInfoDTO);
            long userOpenInfoId = dataMigrateManager.insertUserOpenInfo(userOpenInfoDO);
            return new UserResponse(userOpenInfoId);
        }catch(UserException e){
            return new UserResponse(e.getResponseCode(), e.getMessage());
        }
    }

    @Override
    public String getName() {
        return ActionEnum.INSERT_USER_OPEN_INFO.getActionName();
    }
}
