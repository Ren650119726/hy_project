package com.mockuai.headsinglecenter.core.exception;

import com.mockuai.headsinglecenter.common.constant.ResponseCode;

public class HeadSingleException extends Exception {

    private static final long serialVersionUID = -2453984659537756900L;
    private int code;

    public HeadSingleException(String message) {
        super(message);
    }

    public HeadSingleException(Throwable cause) {
        super(cause);
    }

    public HeadSingleException(String message, Throwable cause) {
        super(message, cause);
    }

    public HeadSingleException(int code, String message) {
        super(message);
        this.code = code;
    }

    public HeadSingleException(ResponseCode responseCode) {
        super(responseCode.getMessage());
        this.code = responseCode.getCode();
    }

    public HeadSingleException(ResponseCode responseCode, String message) {
        super(message);
        this.code = responseCode.getCode();
    }

    public int getCode() {
        return this.code;
    }
}