package com.mockuai.usercenter.core.service.action.useropeninfo;

import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.Request;
import com.mockuai.usercenter.common.api.UserResponse;
import com.mockuai.usercenter.common.constant.ResponseCode;
import com.mockuai.usercenter.common.dto.UserOpenInfoDTO;
import com.mockuai.usercenter.common.qto.UserOpenInfoQTO;
import com.mockuai.usercenter.core.exception.UserException;
import com.mockuai.usercenter.core.manager.UserOpenInfoManager;
import com.mockuai.usercenter.core.service.RequestContext;
import com.mockuai.usercenter.core.service.action.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yeliming on 16/5/13.
 */
@Controller
public class QueryUserOpenInfoAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(QueryUserOpenInfoAction.class);

    @Resource
    private UserOpenInfoManager userOpenInfoManager;

    @Override
    public UserResponse execute(RequestContext context) throws UserException {
        Request request = context.getRequest();
        UserOpenInfoQTO userOpenInfoQTO = (UserOpenInfoQTO) request.getParam("userOpenInfoQTO");

        String bizCode = (String) context.get("bizCode");

        if (userOpenInfoQTO == null) {
            log.error("userOpenInfoQTO is null");
            return new UserResponse(ResponseCode.P_PARAM_NULL, "userOpenInfoQTO不能为空");
        }

        userOpenInfoQTO.setBizCode(bizCode);
        List<UserOpenInfoDTO> userOpenInfoDTOList = this.userOpenInfoManager.queryUserOpenInfo(userOpenInfoQTO);

        Long count = this.userOpenInfoManager.getTotalCount(userOpenInfoQTO);

        return new UserResponse(userOpenInfoDTOList, count);

    }

    @Override
    public String getName() {
        return ActionEnum.QUERY_USER_OPEN_INFO.getActionName();
    }
}
