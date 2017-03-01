package com.mockuai.usercenter.mop;

import com.mockuai.mop.common.service.action.Action;
import com.mockuai.usercenter.common.api.UserDispatchService;
import com.mockuai.usercenter.mop.api.action.UserActionLoader;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zengzhangqiang on 5/17/15.
 */

public class ActionHolderMock {
    private Map<String,Action> actionMap;
    @Resource
    private UserDispatchService userDispatchService;
    private UserActionLoader userActionLoader;

    @PostConstruct
    public void init(){
        actionMap = new HashMap<String, Action>();
        userActionLoader = new UserActionLoader();
        userActionLoader.setUserDispatchService(userDispatchService);
        List<Action> actionList = userActionLoader.load();

        for(Action action: actionList){
            actionMap.put(action.getName(), action);
        }
    }

    public Action getAction(String actionName){
        return actionMap.get(actionName);
    }
}
