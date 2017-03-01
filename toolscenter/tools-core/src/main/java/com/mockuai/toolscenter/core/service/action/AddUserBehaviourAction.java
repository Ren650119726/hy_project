package com.mockuai.toolscenter.core.service.action; /**
 * create by 冠生
 *
 * @date #{DATE}
 **/

import com.mockuai.toolscenter.common.api.ToolsResponse;
import com.mockuai.toolscenter.common.constant.ActionEnum;
import com.mockuai.toolscenter.common.constant.ResponseCode;
import com.mockuai.toolscenter.common.domain.UserBehaviourDTO;
import com.mockuai.toolscenter.core.exception.ToolsException;
import com.mockuai.toolscenter.core.manager.UserBehaviourManager;
import com.mockuai.toolscenter.core.manager.impl.UserBehaviourManagerImpl;
import com.mockuai.toolscenter.core.service.RequestContext;
import com.mockuai.toolscenter.core.service.ResponseUtils;
import com.mockuai.toolscenter.core.service.ToolsRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by guansheng on 2016/10/18.
 */
public class AddUserBehaviourAction implements  Action {
    private Logger logger = LoggerFactory.getLogger(UserBehaviourManagerImpl.class);

    @Autowired
    private UserBehaviourManager userBehaviourManager;

    @Override
    public ToolsResponse execute(RequestContext context) throws ToolsException {
        final ToolsRequest request = context.getRequest();
        UserBehaviourDTO userBehaviourDTO = (UserBehaviourDTO) request.getObject("userBehaviourDTO");
        userBehaviourManager.addUserBehaviour(userBehaviourDTO);
        return ResponseUtils.getSuccessResponse(ResponseCode.RESPONSE_SUCCESS);
    }

    @Override
    public String getName() {
        return ActionEnum.ADD_USER_BEHAVIOUR.getActionName();
    }
}
