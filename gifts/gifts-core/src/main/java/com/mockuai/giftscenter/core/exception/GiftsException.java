package com.mockuai.giftscenter.core.exception;

import com.mockuai.giftscenter.common.constant.ResponseCode;

public class GiftsException extends Exception {

    private static final long serialVersionUID = -2453984659537756900L;
    private int code;

    public GiftsException(String message) {
        super(message);
    }

    public GiftsException(Throwable cause) {
        super(cause);
    }

    public GiftsException(String message, Throwable cause) {
        super(message, cause);
    }

    public GiftsException(int code, String message) {
        super(message);
        this.code = code;
    }

    public GiftsException(ResponseCode responseCode) {
        super(responseCode.getMessage());
        this.code = responseCode.getCode();
    }

    public GiftsException(ResponseCode responseCode, String message) {
        super(message);
        this.code = responseCode.getCode();
    }

    public int getCode() {
        return this.code;
    }
}