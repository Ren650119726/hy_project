package com.mockuai.distributioncenter.core.exception;

import com.mockuai.distributioncenter.common.constant.ResponseCode;

/**
 * Created by duke on 15/10/28.
 */
public class DistributionException extends Exception {
    private int code;

    public DistributionException(String message) {
        super(message);
    }

    public DistributionException(Throwable cause) {
        super(cause);
    }

    public DistributionException(String message, Throwable cause) {
        super(message, cause);
    }

    public DistributionException(int code, String message) {
        super(message);
        this.code = code;
    }

    public DistributionException(ResponseCode responseCode) {
        super(responseCode.getMessage());
        this.code = responseCode.getCode();
    }

    public DistributionException(ResponseCode responseCode, String message) {
        super(message);
        this.code = responseCode.getCode();
    }

    public int getCode() {
        return this.code;
    }
}
