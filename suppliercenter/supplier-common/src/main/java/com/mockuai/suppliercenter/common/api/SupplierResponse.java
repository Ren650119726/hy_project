package com.mockuai.suppliercenter.common.api;

import com.mockuai.suppliercenter.common.constant.ResponseCode;
import org.apache.commons.lang.StringUtils;

import java.util.Collection;

public class SupplierResponse<T> implements Response<T> {
    private static final long serialVersionUID = 8295766534182699773L;
    private final int code;
    private final String message;
    private T module;
    private long totalCount = 0;

    public SupplierResponse(ResponseCode responseCode) {
        this.code = responseCode.getValue();
        this.message = responseCode.getDesc();
    }

    public SupplierResponse(ResponseCode responseCode, String message) {
        this.code = responseCode.getValue();
        if (StringUtils.isBlank(message)) {
            this.message = responseCode.getDesc();
        } else {
            this.message = message;
        }

    }


    public SupplierResponse(T module) {
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

    public SupplierResponse(T module, Long totalCount) {
        this.module = module;
        this.code = ResponseCode.REQUEST_SUCCESS.getValue();
        this.message = ResponseCode.REQUEST_SUCCESS.getDesc();
        this.totalCount = totalCount;
    }

    public SupplierResponse(ResponseCode responseCode, String message, T module) {
        this.module = module;
        this.code = responseCode.getValue();
        if (StringUtils.isBlank(message)) {
            this.message = responseCode.getDesc();
        } else {
            this.message = message;
        }

    }

    public T getModule() {
        return module;
    }

    public void setModule(T module) {
        this.module = module;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
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
