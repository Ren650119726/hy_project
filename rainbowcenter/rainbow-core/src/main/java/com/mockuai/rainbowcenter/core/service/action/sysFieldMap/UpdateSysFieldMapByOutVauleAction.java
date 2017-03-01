package com.mockuai.rainbowcenter.core.service.action.sysFieldMap;

import com.mockuai.rainbowcenter.common.api.RainbowResponse;
import com.mockuai.rainbowcenter.common.api.Request;
import com.mockuai.rainbowcenter.common.constant.ActionEnum;
import com.mockuai.rainbowcenter.common.dto.SysFieldMapDTO;
import com.mockuai.rainbowcenter.core.exception.RainbowException;
import com.mockuai.rainbowcenter.core.manager.SysFieldMapManager;
import com.mockuai.rainbowcenter.core.service.RequestContext;
import com.mockuai.rainbowcenter.core.service.action.Action;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

/**
 * Created by yeliming on 16/4/14.
 */
@Controller
public class UpdateSysFieldMapByOutVauleAction implements Action {
    @Resource
    private SysFieldMapManager sysFieldMapManager;

    @Override
    public RainbowResponse execute(RequestContext context) {
        Request request = context.getRequest();
        SysFieldMapDTO sysFieldMapDTO = (SysFieldMapDTO) request.getParam("sysFieldMapDTO");
        Integer result;
        try {
            result = this.sysFieldMapManager.updateSysFieldMapByOutValue(sysFieldMapDTO);
        } catch (RainbowException e) {
            return new RainbowResponse(e.getResponseCode(), e.getMessage());
        }
        return new RainbowResponse(result);

    }

    @Override
    public String getName() {
        return ActionEnum.UPDATE_SYSFIELDMAP_BY_OUTVALUE.getActionName();
    }
}
