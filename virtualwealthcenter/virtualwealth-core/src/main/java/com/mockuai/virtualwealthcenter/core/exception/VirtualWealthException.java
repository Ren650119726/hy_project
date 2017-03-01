package com.mockuai.virtualwealthcenter.core.exception;

import com.mockuai.virtualwealthcenter.common.constant.ResponseCode;

public class VirtualWealthException extends Exception {

    private static final long serialVersionUID = -2453984659537756900L;
    private int code;

    public VirtualWealthException(String message) {
        super(message);
    }

    public VirtualWealthException(Throwable cause) {
        super(cause);
    }

    public VirtualWealthException(String message, Throwable cause) {
        super(message, cause);
    }

    public VirtualWealthException(int code, String message) {
        super(message);
        this.code = code;
    }

    public VirtualWealthException(ResponseCode responseCode) {
        super(responseCode.getMessage());
        this.code = responseCode.getCode();
    }

    public VirtualWealthException(ResponseCode responseCode, String message) {
        super(message);
        this.code = responseCode.getCode();
    }

    public int getCode() {
        return this.code;
    }
}