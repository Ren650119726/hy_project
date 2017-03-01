package com.mockuai.marketingcenter.core.exception;

import com.mockuai.marketingcenter.common.constant.ResponseCode;

public class MarketingException extends Exception {

    private static final long serialVersionUID = -2453984659537756900L;
    private int code;

    public MarketingException(String message) {
        super(message);
    }

    public MarketingException(Throwable cause) {
        super(cause);
    }

    public MarketingException(String message, Throwable cause) {
        super(message, cause);
    }

    public MarketingException(int code, String message) {
        super(message);
        this.code = code;
    }

    public MarketingException(ResponseCode responseCode) {
        super(responseCode.getMessage());
        this.code = responseCode.getCode();
    }

    public MarketingException(ResponseCode responseCode, String message) {
        super(message);
        this.code = responseCode.getCode();
    }

    public int getCode() {
        return this.code;
    }
}