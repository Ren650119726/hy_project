package com.mockuai.virtualwealthcenter.core.component;

import com.mockuai.virtualwealthcenter.common.constant.ComponentType;
import com.mockuai.virtualwealthcenter.common.constant.ResponseCode;
import com.mockuai.virtualwealthcenter.core.exception.VirtualWealthException;
import com.mockuai.virtualwealthcenter.core.util.Context;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by edgar.zr on 1/12/16.
 */
@Service
public class ComponentHelper {

    @Autowired
    private ComponentHolder componentHolder;

    public <T> T execute(Context context) throws VirtualWealthException {

        Component component = componentHolder.getComponent(((ComponentType) context.getParam("component")).getCode());
        if (component == null)
            throw new VirtualWealthException(ResponseCode.COMPONENT_NOT_EXIST);

        return component.execute(context);
    }
}