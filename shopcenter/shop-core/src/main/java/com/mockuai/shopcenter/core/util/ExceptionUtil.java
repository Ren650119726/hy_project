package com.mockuai.shopcenter.core.util;

import com.mockuai.shopcenter.constant.ResponseCode;
import com.mockuai.shopcenter.core.exception.ShopException;

public class ExceptionUtil {

	public static ShopException getException(ResponseCode responseCode,
			String message) {
		return new ShopException(responseCode, message);
	}
}
