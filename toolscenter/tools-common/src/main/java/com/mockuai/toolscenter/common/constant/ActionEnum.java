package com.mockuai.toolscenter.common.constant;


/**
 * *********************************订单相关操作接口*******************************************
 */
public enum ActionEnum {
    ADD_USER_BEHAVIOUR("addUserBehaviourAction")

    ;

    private String actionName;

    private ActionEnum(String actionName) {
        this.actionName = actionName;
    }

    public static ActionEnum getActionEnum(String actionName) {
        for (ActionEnum ae : ActionEnum.values()) {
            if (ae.actionName.equals(actionName)) {
                return ae;
            }
        }
        return null;
    }

    public String getActionName() {
        return actionName;
    }
}

