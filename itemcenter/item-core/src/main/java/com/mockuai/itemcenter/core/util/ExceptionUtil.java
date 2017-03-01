package com.mockuai.itemcenter.core.util;

import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.core.exception.ItemException;

public class ExceptionUtil {

	public static ItemException getException(ResponseCode responseCode,
			String message) {
		return new ItemException(responseCode, message);
	}
}
