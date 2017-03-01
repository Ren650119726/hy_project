package com.hanshu.employee.common.api;

import com.hanshu.employee.common.constant.ResponseCode;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;

public class EmployeeResponse<T> implements Response<T> {
    private static final long serialVersionUID = 8295766534182699773L;

    private T module;
    private final int code;
    private final String message;
    private long totalCount = 0;

    public EmployeeResponse(ResponseCode responseCode) {
        this.code = responseCode.getValue();
        this.message = responseCode.getDesc();
    }

    public EmployeeResponse(ResponseCode responseCode, String message) {
        this.code = responseCode.getValue();
        if (StringUtils.isBlank(message)) {
            this.message = responseCode.getDesc();
        } else {
            this.message = message;
        }

    }


    public EmployeeResponse(T module) {
        this.module = module;
        if (module != null) {
            if (module instanceof Collection) {
                totalCount = ((Collection) module).size();
            } else {
                totalCount = 1;
            }
        }
        this.code = ResponseCode.REQUEST_SUCCESS.getValue();
        this.message = ResponseCode.REQUEST_SUCCESS.getDesc();
    }

    public EmployeeResponse(T module, Long totalCount) {
        this.module = module;
        this.code = ResponseCode.REQUEST_SUCCESS.getValue();
        this.message = ResponseCode.REQUEST_SUCCESS.getDesc();
        this.totalCount = totalCount;
    }

    public T getModule() {
        return module;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public void setModule(T module) {
        this.module = module;
    }

    public boolean isSuccess() {
        return this.code == ResponseCode.REQUEST_SUCCESS.getValue();
    }

    public int getCode() {
        // TODO Auto-generated method stub
        return code;
    }

    public String getMessage() {
        // TODO Auto-generated method stub
        return message;
    }

}
