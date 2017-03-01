package com.mockuai.virtualwealthcenter.core.component.impl;

import com.mockuai.virtualwealthcenter.core.component.Component;
import com.mockuai.virtualwealthcenter.core.component.ComponentHelper;
import com.mockuai.virtualwealthcenter.core.exception.VirtualWealthException;
import com.mockuai.virtualwealthcenter.core.util.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.mockuai.virtualwealthcenter.common.constant.ComponentType.VALIDATE_SETTLEMENT_OF_SECKILL;

/**
 * Created by edgar.zr on 2/02/2016.
 */
@Service
public class ValidateSettlementOfSeckill implements Component {

    private static final Logger LOGGER = LoggerFactory.getLogger(ValidateSettlementOfSeckill.class);

    @Autowired
    private ComponentHelper componentHelper;

    public static Context wrapParams() {
        Context context = new Context();
//        context.setParam("consigneeId", consigneeId);
//        context.setParam("appKey", appKey);
        context.setParam("component", VALIDATE_SETTLEMENT_OF_SECKILL);
        return context;
    }

    @Override
    public void init() {

    }

    @Override
    public Void execute(Context context) throws VirtualWealthException {
        return null;
    }

    @Override
    public String getComponentCode() {
        return VALIDATE_SETTLEMENT_OF_SECKILL.getCode();
    }
}