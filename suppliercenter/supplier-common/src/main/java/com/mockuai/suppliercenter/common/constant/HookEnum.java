package com.mockuai.suppliercenter.common.constant;

/**
 * Created by lizg on 16/9/24.
 */
public enum HookEnum {

    STOCK_CHANGE_HOOK("stockChangeHook"),

    BATCH_STOCK_CHANGE_HOOK("batchStockChangeHook");


    private String hookName;

    private HookEnum(String hookName) {
        this.hookName = hookName;
    }

    public static HookEnum getHookEnum(String hookName) {
        for (HookEnum ae : HookEnum.values()) {
            if (ae.hookName.equals(hookName)) {
                return ae;
            }
        }
        return null;
    }

    public String getHookName() {
        return hookName;
    }
}
