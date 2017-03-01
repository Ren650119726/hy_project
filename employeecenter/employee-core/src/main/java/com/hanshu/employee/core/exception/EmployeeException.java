package com.hanshu.employee.core.exception;

import com.hanshu.employee.common.constant.ResponseCode;

public class EmployeeException extends Exception {
    private static final long serialVersionUID = 4065133016321980497L;

    private ResponseCode responseCode;

    public EmployeeException() {
        super();
        responseCode = ResponseCode.REQUEST_SUCCESS;
    }

    public EmployeeException(Throwable e) {

    }

    public EmployeeException(ResponseCode responseCode) {
        super();
        this.responseCode = responseCode;
    }

    public EmployeeException(ResponseCode responseCode, String message) {
        super(message);
        this.responseCode = responseCode;
    }

    public ResponseCode getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(ResponseCode responseCode) {
        this.responseCode = responseCode;
    }

}
