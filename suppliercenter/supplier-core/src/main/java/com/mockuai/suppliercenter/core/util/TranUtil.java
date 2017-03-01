package com.mockuai.suppliercenter.core.util;

import com.mockuai.suppliercenter.common.constant.ResponseCode;
import com.mockuai.suppliercenter.common.dto.BaseDTO;
import com.mockuai.suppliercenter.core.domain.BaseDO;
import com.mockuai.suppliercenter.core.exception.SupplierException;
import org.springframework.beans.BeanUtils;

public class TranUtil {

    public static BaseDO trans2Do(BaseDTO baseDto, BaseDO baseDo)
            throws SupplierException {
        checkNull(baseDo, baseDto);
        BeanUtils.copyProperties(baseDto, baseDo);
        return baseDo;
    }

    public static BaseDTO trans2Dto(BaseDO baseDo, BaseDTO baseDto)
            throws SupplierException {
        checkNull(baseDo, baseDto);
        BeanUtils.copyProperties(baseDo, baseDto);
        return baseDto;
    }

    public static void checkNull(Object obj1, Object obj2) throws SupplierException {
        if (null == obj1) {
            throw new SupplierException(ResponseCode.P_PARAM_NULL,
                    "trans resoure is null");
        }
        if (null == obj2) {
            throw new SupplierException(ResponseCode.P_PARAM_NULL,
                    "trans resoure is null");
        }
    }
}
