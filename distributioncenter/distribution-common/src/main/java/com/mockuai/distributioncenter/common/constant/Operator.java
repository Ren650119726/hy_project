package com.mockuai.distributioncenter.common.constant;

/**
 * Created by duke on 16/5/20.
 */
public enum Operator {
    // 加
    ADD(0),
    // 减
    SUB(1)
    ;
    private int op;

    Operator(int op) {
        this.op = op;
    }

    public int getOp() {
        return op;
    }
}
