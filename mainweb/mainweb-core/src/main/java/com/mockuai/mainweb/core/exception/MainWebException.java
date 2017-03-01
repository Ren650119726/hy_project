package com.mockuai.mainweb.core.exception;

import com.mockuai.mainweb.common.constant.ResponseCode;

/**
 * Created by luliang on 15/7/2.
 */
public class MainWebException extends Exception {

    private int code;

    private String message;
    private ResponseCode responseCode;

    public MainWebException() {
        super();
        this.code = ResponseCode.SYS_E_DEFAULT_ERROR.getCode();
    }

    public MainWebException(String message) {
        super(message);
        this.code = ResponseCode.SYS_E_DEFAULT_ERROR.getCode();
    }

    public MainWebException(String message, Throwable cause) {
        super(message, cause);
        this.code = ResponseCode.SYS_E_DEFAULT_ERROR.getCode();
    }

    public MainWebException(Throwable cause) {
        super(cause);
        this.code = ResponseCode.SYS_E_DEFAULT_ERROR.getCode();
    }

    public MainWebException(int code, String message) {
        super(message);
        this.code = code;
    }

    public MainWebException(ResponseCode responseCode, Throwable cause) {
        super(responseCode.getComment(), cause);
        this.code = responseCode.getCode();
        this.responseCode = responseCode;
    }

    public MainWebException(ResponseCode responseCode, String message) {
        this.message = message;
        this.code = responseCode.getCode();
        this.responseCode = responseCode;
    }



    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ResponseCode getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(ResponseCode responseCode) {
        this.responseCode = responseCode;
    }
}
