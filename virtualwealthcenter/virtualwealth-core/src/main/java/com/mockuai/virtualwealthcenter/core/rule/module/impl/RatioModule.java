package com.mockuai.virtualwealthcenter.core.rule.module.impl;

import com.mockuai.virtualwealthcenter.core.exception.VirtualWealthException;
import com.mockuai.virtualwealthcenter.core.rule.module.Module;
import org.springframework.stereotype.Service;

/**
 * Created by edgar.zr on 11/13/15.
 * 按订单商品总价的百分比赠送积分，总价是以 元 为单位
 */
@Service
public class RatioModule implements Module {
    private int ratio;

    @Override
    public Long execute(Long amount) throws VirtualWealthException {

        return (ratio * amount.longValue()) / 10000;
    }

    public int getRatio() {
        return ratio;
    }

    public void setRatio(int ratio) {
        this.ratio = ratio;
    }
}