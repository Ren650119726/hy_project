package com.mockuai.toolscenter.client;


import com.mockuai.toolscenter.common.domain.UserBehaviourDTO;

/**
 * Created by jiguansheng on 6/8/15.
 */
public interface ToolsClient {

    void addUserBehaviour(UserBehaviourDTO userBehaviourDTO, String appKey);


}
