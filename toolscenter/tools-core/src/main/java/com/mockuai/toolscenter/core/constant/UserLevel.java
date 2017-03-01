package com.mockuai.toolscenter.core.constant;

/**
 * Created by zengzhangqiang on 4/6/16.
 */
public enum UserLevel {
    //系统管理员
    ADMIN(1),
    //业务管理员
    MANAGER(2),
    //开发人员
    DEVELOPER(3);

    private int value;
    private UserLevel(int value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static UserLevel getUserLevel(int value){
        for(UserLevel userLevel: UserLevel.values()){
            if(userLevel.getValue() == value){
                return userLevel;
            }
        }

        return null;
    }
}
