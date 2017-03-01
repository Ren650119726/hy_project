package com.mockuai.toolscenter.core.manager;

import com.mockuai.toolscenter.common.domain.UserBehaviourDTO;
import com.mockuai.toolscenter.core.exception.ToolsException;

/**
 * Created by zengzhangqiang on 6/12/15.
 */
public interface UserBehaviourManager {
    public void addUserBehaviour(UserBehaviourDTO userBehaviourDTO) throws ToolsException;

}
