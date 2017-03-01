package com.mockuai.usercenter.core.service.action.user;

import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.Request;
import com.mockuai.usercenter.common.api.UserResponse;
import com.mockuai.usercenter.common.dto.UserDTO;
import com.mockuai.usercenter.common.qto.UserQTO;
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
 * Created by duke on 15/9/8.
 */
@Service
public class QueryNormalAndOldUser implements Action {
    private final static Logger log = LoggerFactory.getLogger(QueryNormalAndOldUser.class);

    @Resource
    private UserManager userManager;

    @Override
    public UserResponse execute(RequestContext context) throws UserException {
        log.info("enter action : {}", getName());
        Request request = context.getRequest();
        UserQTO userQTO = (UserQTO) request.getParam("userQTO");
        String bizCode = (String) context.get("bizCode");

        userQTO.setBizCode(bizCode);
        List<UserDTO> userDTOList = userManager.queryNormalAndOldUser(userQTO);
        Long totalCount = userManager.getNormalAndOldTotalCount(userQTO);

        log.info("exit action : {}", getName());
        return new UserResponse(userDTOList, totalCount);
    }

    @Override
    public String getName() {
        return ActionEnum.QUERY_NORMAL_OLD_USER.getActionName();
    }
}
