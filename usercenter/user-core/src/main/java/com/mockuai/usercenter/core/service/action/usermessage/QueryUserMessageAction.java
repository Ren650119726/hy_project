package com.mockuai.usercenter.core.service.action.usermessage;

import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.UserResponse;
import com.mockuai.usercenter.common.constant.ResponseCode;
import com.mockuai.usercenter.common.dto.UserMessageDTO;
import com.mockuai.usercenter.common.qto.UserMessageQTO;
import com.mockuai.usercenter.core.exception.UserException;
import com.mockuai.usercenter.core.manager.UserMessageManager;
import com.mockuai.usercenter.core.service.RequestContext;
import com.mockuai.usercenter.core.service.UserRequest;
import com.mockuai.usercenter.core.service.action.Action;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 获取消息列表
 */
@Service
public class QueryUserMessageAction implements Action {

    private final Long DEFAULT_PAGE = 1L;

    private final Integer DEFAULT_PAGE_SIZE = 100;

    @Resource
    private UserMessageManager userMessageManager;

    @Override
    public UserResponse execute(RequestContext context) throws UserException {
        UserRequest request = context.getRequest();
        String bizCode = (String) context.get("bizCode");

        try {
            if (request.getParam("userMessageQTO") == null) {
                return new UserResponse(ResponseCode.P_PARAM_NULL, "userMessageQTO is null");
            }

            UserMessageQTO userMessageQto = (UserMessageQTO) request.getParam("userMessageQTO");
            userMessageQto.setBizCode(bizCode);
            if (userMessageQto.getPageSize() == null || userMessageQto.getPageSize().intValue() > DEFAULT_PAGE_SIZE) {
                userMessageQto.setPageSize(DEFAULT_PAGE_SIZE);
            }
            if (userMessageQto.getPageNum() == null) {
                userMessageQto.setPageNum(DEFAULT_PAGE);
            }
            Long startRow = (userMessageQto.getPageNum().longValue() - 1) * (userMessageQto.getPageSize().intValue());
            userMessageQto.setStartRow(startRow);

            List<UserMessageDTO> messages = userMessageManager.queryUserMessage(userMessageQto);
            Long total = userMessageManager.getTotalCount(userMessageQto);
            return new UserResponse(messages, total);
        } catch (UserException e) {
            return new UserResponse(e.getResponseCode(), e.getMessage());
        }
    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return ActionEnum.QUERY_USER_MESSAGE.getActionName();
    }

}
