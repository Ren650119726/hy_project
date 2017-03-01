package com.mockuai.distributioncenter.common.api;

import com.mockuai.distributioncenter.common.constant.ResponseCode;

/**
 * Created by duke on 15/10/28.
 */
public class DistributionResponse<T> implements Response<T> {

    private T module;
    private int code;
    private String message;
    private long totalCount;

    public DistributionResponse(int code) {
        this.code = code;
    }

    public DistributionResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public DistributionResponse(ResponseCode responseCode) {
        this(responseCode.getCode(), responseCode.getMessage());
    }

    public DistributionResponse(ResponseCode responseCode, String message) {
        this.code = responseCode.getCode();
        this.message = message;
    }

    public DistributionResponse(T module) {
        this(ResponseCode.SUCCESS);
        this.module = module;
    }

    public DistributionResponse(T module, long totalCount) {
        this(ResponseCode.SUCCESS);
        this.module = module;
        this.totalCount = totalCount;
    }

    public T getModule() {
        return this.module;
    }

    public int getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    public long getTotalCount() {
        return this.totalCount;
    }

    public Boolean isSuccess() {
        return ResponseCode.SUCCESS.getCode() == this.code;
    }

    public void setModule(T module) {
        this.module = module;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }
}
