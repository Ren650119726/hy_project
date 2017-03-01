package com.mockuai.dts.common;

/**
 * Created by luliang on 15/7/31.
 */
public enum TaskStatusEnum {

    NEW_TASK(1, "新建"),

    RUNNING_TASK(2, "运行"),

    COMPLETE_TASK(3, "成功"),

    FAILED_TASK(4, "失败"),

    INVALID_TASK(5, "失效");

    private int status;
    private String name;

    private TaskStatusEnum(int status, String name) {
        this.status = status;
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
