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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by duke on 15/11/5.
 */
@Service
public class QueryUserFromIdListAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(QueryUserFromIdListAction.class);

    @Resource
    private UserManager userManager;

    @Override
    public UserResponse execute(RequestContext context) throws UserException {
        log.info("Enter Action [{}]", getName());
        Request request = context.getRequest();
        List<Long> idList = (List<Long>)request.getParam("idList");
        String bizCode = (String) context.get("bizCode");
        if (idList == null) {
            log.error("idList is null, bizCode: {}", bizCode);
            throw new UserException(ResponseCode.P_PARAM_NULL);
        }
        List<UserDTO> userDTOs;
        if (idList.isEmpty()) {
            userDTOs = new ArrayList<UserDTO>();
        } else {
            userDTOs = userManager.queryFromIdList(idList);
        }

        log.info("Exit Action [{}]", getName());
        return new UserResponse(userDTOs);
    }

    @Override
    public String getName() {
        return ActionEnum.QUERY_USER_FROM_ID_LIST.getActionName();
    }
}
