package com.mockuai.usercenter.core.util;

import org.springframework.beans.BeanUtils;

import com.mockuai.usercenter.common.constant.ResponseCode;
import com.mockuai.usercenter.common.dto.BaseDTO;
import com.mockuai.usercenter.core.domain.BaseDO;
import com.mockuai.usercenter.core.exception.UserException;

public class TranUtil {

	public static BaseDO trans2Do(BaseDTO baseDto, BaseDO baseDo)
			throws UserException {
		checkNull(baseDo, baseDto);
		BeanUtils.copyProperties(baseDto, baseDo);
		return baseDo;
	}

	public static BaseDTO trans2Dto(BaseDO baseDo, BaseDTO baseDto)
			throws UserException {
		checkNull(baseDo, baseDto);
		BeanUtils.copyProperties(baseDo, baseDto);
		return baseDto;
	}

	public static void checkNull(Object obj1, Object obj2) throws UserException {
		if (null == obj1) {
			throw new UserException(ResponseCode.P_PARAM_NULL,
					"trans resoure is null");
		}
		if (null == obj2) {
			throw new UserException(ResponseCode.P_PARAM_NULL,
					"trans resoure is null");
		}
	}
}
