package com.mockuai.toolscenter.client.impl; /**
 * create by 冠生
 *
 * @date #{DATE}
 **/

import com.mockuai.toolscenter.client.ToolsClient;
import com.mockuai.toolscenter.common.api.BaseRequest;
import com.mockuai.toolscenter.common.api.Request;
import com.mockuai.toolscenter.common.api.ToolsService;
import com.mockuai.toolscenter.common.constant.ActionEnum;
import com.mockuai.toolscenter.common.domain.UserBehaviourDTO;

import javax.annotation.Resource;

/**
 * Created by guansheng on 2016/10/18.
 */
public class ToolsClientImpl implements ToolsClient {
    @Resource
    ToolsService toolsService;

    public void addUserBehaviour(UserBehaviourDTO userBehaviourDTO, String appKey) {
        Request baseRequest = new BaseRequest();
        baseRequest.setParam("userBehaviourDTO",userBehaviourDTO);
        baseRequest.setParam("appKey",appKey);
        baseRequest.setCommand(ActionEnum.ADD_USER_BEHAVIOUR.getActionName());
        toolsService.execute(baseRequest);
    }
}
