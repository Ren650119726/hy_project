package com.mockuai.marketingcenter.core.engine.component;

import com.mockuai.marketingcenter.common.constant.ComponentType;
import com.mockuai.marketingcenter.common.constant.ResponseCode;
import com.mockuai.marketingcenter.core.util.Context;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by edgar.zr on 1/12/16.
 */
@Service
public class ComponentHelper {

    @Autowired
    private ComponentHolder componentHolder;

    public <T> T execute(Context context) throws MarketingException {

        Component component = componentHolder.getComponent(((ComponentType) context.getParam("component")).getCode());
        if (component == null)
            throw new MarketingException(ResponseCode.COMPONENT_NOT_EXIST);

        return component.execute(context);
    }
}