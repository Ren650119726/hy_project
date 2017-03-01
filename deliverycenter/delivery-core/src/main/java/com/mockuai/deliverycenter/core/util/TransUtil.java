package com.mockuai.deliverycenter.core.util;

import org.springframework.beans.BeanUtils;

import com.mockuai.deliverycenter.common.constant.RetCodeEnum;
import com.mockuai.deliverycenter.common.dto.BaseDTO;
import com.mockuai.deliverycenter.common.qto.BaseQTO;
import com.mockuai.deliverycenter.core.domain.BaseDo;
import com.mockuai.deliverycenter.core.exception.DeliveryException;

public class TransUtil {
	private static final String UNDERLINE = "_";
	
	public static BaseDo trans2Do(BaseDTO dto, BaseDo domain)
			throws DeliveryException {
		if (dto == null) {
			return null;
		}
		checkNull(domain);
		BeanUtils.copyProperties(dto, domain);
		return domain;
	}

	public static BaseDTO trans2Dto(BaseDTO dto, BaseDo domain)
			throws DeliveryException {
		if (domain == null) {
			return null;
		}
		checkNull(dto);
		BeanUtils.copyProperties(domain, dto);
		return dto;
	}

	public static BaseQTO trans2Qto(BaseDTO dto, BaseQTO qto)
			throws DeliveryException {
		if (dto == null) {
			return null;
		}
		checkNull(qto);
		BeanUtils.copyProperties(dto, qto);
		return qto;
	}

	public static String genUid(Long id,Long userId){
		if(id ==null || userId ==null){
			return null;
		}
		return String.valueOf(userId) + UNDERLINE  + String.valueOf(id);
	}
	
	private static void checkNull(Object target) throws DeliveryException {
		if (target == null) {
			throw new DeliveryException(RetCodeEnum.PARAMETER_NULL.getCode(),
					"trans target is null");
		}

	}
}
