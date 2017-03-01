package com.hanshu.employee.common.action;

public enum ActionEnum {

    /*********************************
     * 管理员
     *************************************************/
    ADD_EMPLOYEE("addEmployee"),
    UPDATE_EMPLOYEE("updateEmployee"),
    DELETE_EMPLOYEE("deleteEmployee"),
    QUERY_EMPLOYEE("queryEmployee"),
    GET_EMPLOYEE_BY_ID("getEmployeeById"),
    EMPLOYEE_LOGIN("employeeLogin"),
    GEN_EMPLOYEE_OP_LOG("genEmployeeOpLog"),
    QUERY_EMPLOYEE_OP_LOG("queryEmployeeOpLog"),
    UPDATE_EMPLOYEE_STATUS("updateEmployeeStatus"),
    UPDATE_PASSWORD("updatePassword"),
    /***********************************
     * 菜单
     ******************************************************/
    ADD_MENU("addMenu"),
    UPDATE_MENU("updateMenu"),
    DELETE_MENU("deleteMenu"),
    QUERY_MENU("queryMenu"),
    GET_MENU_BY_ID("getMenuById"),
    GET_PARENT_MENU_BY_URL("getParentMenuByUrl"),
    GET_MENU_BY_URL("getMenuByUrl"),
    /***********************************
     * 角色
     ******************************************************/
    ADD_USER_ROLE("addUserRole"),
    UPDATE_USER_ROLE("updateUserRole"),
    DELETE_USER_ROLE("deleteUserRole"),
    QUERY_USER_ROLE("queryUserRole"),
    GET_USER_ROLE_BY_ID("getUserRoleById"),
    TOTAL_ROLE_GROUP_COUNT("totalRoleGroupCount"),

    /**
     * 查询user open info,微信登录信息等
     */
    QUERY_USER_OPEN_INFO("queryUserOpenInfo");


    private String actionName;

    ActionEnum(String actionName) {
        this.actionName = actionName;
    }

    public String getActionName() {
        return actionName;
    }
}
