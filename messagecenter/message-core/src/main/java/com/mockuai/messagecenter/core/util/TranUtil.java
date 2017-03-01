package com.mockuai.messagecenter.core.util;

import org.springframework.beans.BeanUtils;

import com.mockuai.messagecenter.common.constant.ResponseCode;
import com.mockuai.messagecenter.common.dto.BaseDTO;
import com.mockuai.messagecenter.core.domain.BaseDO;
import com.mockuai.messagecenter.core.exception.MessageException;

public class TranUtil {

	public static BaseDO trans2Do(BaseDTO baseDto, BaseDO baseDo)
			throws MessageException {
		checkNull(baseDo, baseDto);
		BeanUtils.copyProperties(baseDto, baseDo);
		return baseDo;
	}

	public static BaseDTO trans2Dto(BaseDO baseDo, BaseDTO baseDto)
			throws MessageException {
		checkNull(baseDo, baseDto);
		BeanUtils.copyProperties(baseDo, baseDto);
		return baseDto;
	}

	public static void checkNull(Object obj1, Object obj2) throws MessageException {
		if (null == obj1) {
			throw new MessageException(ResponseCode.P_PARAM_NULL,
					"trans resoure is null");
		}
		if (null == obj2) {
			throw new MessageException(ResponseCode.P_PARAM_NULL,
					"trans resoure is null");
		}
	}
}
