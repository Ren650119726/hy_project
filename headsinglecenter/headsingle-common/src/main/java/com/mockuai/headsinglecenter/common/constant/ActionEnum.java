package com.mockuai.headsinglecenter.common.constant;

/**
 * Created by edgar.zr on 12/4/15.
 */
public enum ActionEnum {
	/**
	 * mq支付成功后消息接收
	 */
	PAY_SUCCESS_LISTENER("paySuccessListener"),
	
	/**
	 * mq下单成功后消息接收
	 */
	ORDER_UNPAID_SUCCESS_LISTENER("orderUnpaidSuccessListener"),
	
	/**
	 * mq取消订单消息接收
	 */
	ORDER_CANCEL_SUCCESS_LISTENER("orderCancelSuccessListener"),
	
	/**
	 * 查询是否符合首单立减
	 */
	QUERY_HEADSINGLE_JUDGE_USER("queryJudgeHeadSingleUser"),
	
	/**
	 * 查询订单信息
	 */
	QUERY_HEADSINGLE_INFO("queryHeadSingleInfo"),
	
	/**
	 * 新增首单立减
	 */
	ADD_HEADSINGLE_SUB("addHeadSingleSub"),
	
	/**
	 *修改首单立减
	 */
	MODIFY_HEADSINGLE_SUB("modifyHeadSingleSub"),
	
	/**
     * 查询首单立减根据id
     */
    QUERY_HEADSINGLE_SUBBYID("queryHeadSingleSubById"),

    /**
     * 查询首单立减
     */
    QUERY_HEADSINGLE_SUB("queryHeadSingleSub");

    private String actionName;

    ActionEnum(String actionName) {
        this.actionName = actionName;
    }

    public static ActionEnum getActionEnum(String actionName) {
        for (ActionEnum ae : values()) {
            if (ae.actionName.equals(actionName)) {
                return ae;
            }
        }
        return null;
    }

    public String getActionName() {
        return this.actionName;
    }
}