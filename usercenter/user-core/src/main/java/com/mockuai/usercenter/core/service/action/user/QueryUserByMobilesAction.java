package com.mockuai.usercenter.core.service.action.user;

import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.Request;
import com.mockuai.usercenter.common.api.UserResponse;
import com.mockuai.usercenter.common.constant.ResponseCode;
import com.mockuai.usercenter.common.dto.UserDTO;
import com.mockuai.usercenter.core.exception.UserException;
import com.mockuai.usercenter.core.manager.UserManager;
import com.mockuai.usercenter.core.service.RequestContext;
import com.mockuai.usercenter.core.service.action.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by duke on 15/12/22.
 */
@Service
public class QueryUserByMobilesAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(QueryUserByMobilesAction.class);

    @Resource
    private UserManager userManager;

    @Override
    public UserResponse execute(RequestContext context) throws UserException {
        Request request = context.getRequest();
        List<String> mobiles = (List<String>)request.getParam("mobiles");
        String bizCode = (String) context.get("bizCode");

        if (mobiles == null) {
            log.error("mobiles is null, bizCode: {}", bizCode);
            throw new UserException(ResponseCode.P_PARAM_NULL, "mobiles is null");
        }

        List<UserDTO> userDTOs = userManager.queryByMobiles(mobiles, bizCode);

        return new UserResponse<List<UserDTO>>(userDTOs);
    }

    @Override
    public String getName() {
        return ActionEnum.QUERY_USER_BY_MOBILES.getActionName();
    }
}
