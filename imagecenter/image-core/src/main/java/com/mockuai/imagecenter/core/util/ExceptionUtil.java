package com.mockuai.imagecenter.core.util;

import com.mockuai.imagecenter.common.constant.ResponseCode;
import com.mockuai.imagecenter.core.exception.ImageException;

public class ExceptionUtil {

	public static ImageException getException(ResponseCode responseCode,
			String message) {
		return new ImageException(responseCode, message);
	}

	public static ImageException getException(ResponseCode responseCode) {
		return new ImageException(responseCode,responseCode.getComment());
	}
}
