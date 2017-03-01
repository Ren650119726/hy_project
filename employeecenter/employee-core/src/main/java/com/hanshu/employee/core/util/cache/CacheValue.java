package com.hanshu.employee.core.util.cache;

/**
 * Created by edgar.zr on 10/23/15.
 */
public class CacheValue {

    private int deadline;
    private Object value;

    public void setDeadline(int deadline) {
        this.deadline = deadline;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public boolean isValidate() {
        int current = (int) (System.currentTimeMillis() / 1000);
        return current <= deadline;
    }
}