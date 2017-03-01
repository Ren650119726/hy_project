package com.mockuai.distributioncenter.common.constant;

/**
 * Created by duke on 16/5/16.
 */
public enum DistributionStatus {
    // 下单成功，待分拥
    UNDER_DISTRIBUTION(0),
    // 支付成功，分拥冻结
    FROZEN_DISTRIBUTION(1),
    // 退款成功，分拥取消
    CANCEL_SUCCESS_DISTRIBUTION(2),
    // 订单发货，待收货状态
    FINISHING_DISTRIBUTION(3),
    // 订单关闭，分拥完成
    FINISHED_DISTRIBUTION(4),
    // 订单取消，分拥取消
    CANCEL_DISTRIBUTION(5)
    ;
    private int status;

    DistributionStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
