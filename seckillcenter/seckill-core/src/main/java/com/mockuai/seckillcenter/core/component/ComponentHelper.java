package com.mockuai.seckillcenter.core.component;

import com.mockuai.seckillcenter.common.constant.ComponentType;
import com.mockuai.seckillcenter.common.constant.ResponseCode;
import com.mockuai.seckillcenter.core.exception.SeckillException;
import com.mockuai.seckillcenter.core.util.Context;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by edgar.zr on 1/12/16.
 */
@Service
public class ComponentHelper {

    @Autowired
    private ComponentHolder componentHolder;

    public <T> T execute(Context context) throws SeckillException {

        Component component = componentHolder.getComponent(((ComponentType) context.getParam("component")).getCode());
        if (component == null)
            throw new SeckillException(ResponseCode.COMPONENT_NOT_EXIST);

        return component.execute(context);
    }
}