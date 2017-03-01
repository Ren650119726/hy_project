package com.mockuai.suppliercenter.core.exception;

import com.mockuai.suppliercenter.common.constant.ResponseCode;

public class SupplierException extends Exception {
    private static final long serialVersionUID = 4065133016321980497L;

    private ResponseCode responseCode;

    private Long i;

    public SupplierException() {
        super();
        responseCode = ResponseCode.REQUEST_SUCCESS;
    }

    public SupplierException(Throwable e) {

    }

    public SupplierException(ResponseCode responseCode) {
        super();
        this.responseCode = responseCode;
    }

    public SupplierException(ResponseCode responseCode, String message) {
        super(message);
        this.responseCode = responseCode;
    }

    public SupplierException(ResponseCode responseCode, String message, Long i) {

        super(message);
        this.responseCode = responseCode;
        this.i = i;
    }

    public ResponseCode getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(ResponseCode responseCode) {
        this.responseCode = responseCode;
    }

    public Long getI() {
        return i;
    }

    public void setI(Long i) {
        this.i = i;
    }

}
