package com.mockuai.usercenter.core.service.action.user;

import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.UserResponse;
import com.mockuai.usercenter.common.constant.ResponseCode;
import com.mockuai.usercenter.common.dto.UserDTO;
import com.mockuai.usercenter.common.qto.UserQTO;
import com.mockuai.usercenter.core.exception.UserException;
import com.mockuai.usercenter.core.manager.UserManager;
import com.mockuai.usercenter.core.service.RequestContext;
import com.mockuai.usercenter.core.service.UserRequest;
import com.mockuai.usercenter.core.service.action.Action;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by yeliming on 16/1/19.
 */
@Service
public class TotalValidUsersAction implements Action {
    @Resource
    private UserManager userManager;

    @Override
    public UserResponse execute(RequestContext context) throws UserException {
        UserRequest userRequest = context.getRequest();

        Date start = (Date) userRequest.getParam("start");
        Date end = (Date) userRequest.getParam("end");
        Long offset = (Long) userRequest.getParam("offset");
        Integer count = (Integer)userRequest.getParam("count");

        if(start == null){
            throw new UserException(ResponseCode.P_PARAM_NULL,"start time is null");
        }
        if(end == null){
            throw new UserException(ResponseCode.P_PARAM_NULL,"end time is null");
        }

        UserQTO userQTO = new UserQTO();
        userQTO.setStartTime(start);
        userQTO.setEndTime(end);
        userQTO.setOffset(offset);
        userQTO.setCount(count);
        List<UserDTO> validUsers = this.userManager.totalValidUsers(userQTO);
        long total = this.userManager.getTotalValidCount(userQTO);

        return new UserResponse(validUsers,total);
    }

    @Override
    public String getName() {
        return ActionEnum.TOTAL_VALID_USERS.getActionName();
    }
}
