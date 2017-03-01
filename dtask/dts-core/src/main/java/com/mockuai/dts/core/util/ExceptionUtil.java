package com.mockuai.dts.core.util;

import com.mockuai.dts.common.constant.ResponseCode;
import com.mockuai.dts.core.exception.DtsException;

public class ExceptionUtil {

	public static DtsException getException(ResponseCode responseCode,
			String message) {
		return new DtsException(responseCode, message);
	}
}
