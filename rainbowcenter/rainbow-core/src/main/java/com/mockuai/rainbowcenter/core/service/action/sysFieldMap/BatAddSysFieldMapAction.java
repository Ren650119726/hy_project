package com.mockuai.rainbowcenter.core.service.action.sysFieldMap;

import com.mockuai.rainbowcenter.common.api.RainbowResponse;
import com.mockuai.rainbowcenter.common.api.Request;
import com.mockuai.rainbowcenter.common.constant.ActionEnum;
import com.mockuai.rainbowcenter.core.manager.SysFieldMapManager;
import com.mockuai.rainbowcenter.core.service.RequestContext;
import com.mockuai.rainbowcenter.core.service.action.Action;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

/**
 * Created by yeliming on 16/4/14.
 */
@Controller
public class BatAddSysFieldMapAction implements Action {
    @Resource
    private SysFieldMapManager sysFieldMapManager;

    @Override
    public RainbowResponse execute(RequestContext context) {
        Request request = context.getRequest();
        return null;
    }

    @Override
    public String getName() {
        return ActionEnum.BATCH_ADD_SYSFIELDMAP.getActionName();
    }
}
