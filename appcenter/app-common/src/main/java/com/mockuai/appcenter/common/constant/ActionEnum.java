package com.mockuai.appcenter.common.constant;


/**
 * *********************************订单相关操作接口*******************************************
 */
public enum ActionEnum {
    ADD_APP_INFO("addAppInfo"),
    ADD_BIZ_INFO("addBizInfo"),
    GET_APP_INFO("getAppInfo"),
    GET_APP_INFO_BY_DOMAIN("getAppInfoByDomain"),
    GET_APP_INFO_BY_TYPE("getAppInfoByType"),
    GET_BIZ_INFO("getBizInfo"),
    QUERY_BIZ_INFO("queryBizInfo"),
    GET_BIZ_INFO_BY_APP_KEY("getBizInfoByAppKey"),
    GET_BIZ_INFO_BY_DOMAIN("getBizInfoByDomain"),
    ADD_APP_PROPERTY("addAppProperty"),
    ADD_BIZ_PROPERTY("addBizProperty"),
    DELETE_APP_PROPERTY("deleteAppProperty"),
    DELETE_BIZ_PROPERTY("deleteBizProperty"),
    UPDATE_BIZ_PROPERTY("updateBizProperty"),
    UPDATE_APP_INFO("updateAppInfo"),
    UPDATE_BIZ_INFO("updateBizInfo")
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

