package com.mockuai.mainweb.core.util;

import com.mockuai.mainweb.common.constant.ResponseCode;
import com.mockuai.mainweb.core.exception.MainWebException;

public class ExceptionUtil {

	public static MainWebException getException(ResponseCode responseCode,
                                                String message) {
		return new MainWebException(responseCode, message);
	}

	public static MainWebException getException(ResponseCode responseCode) {
		return new MainWebException(responseCode,responseCode.getComment());
	}
}
