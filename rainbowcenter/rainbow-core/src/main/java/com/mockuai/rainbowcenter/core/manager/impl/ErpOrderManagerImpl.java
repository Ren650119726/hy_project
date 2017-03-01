package com.mockuai.rainbowcenter.core.manager.impl;

import com.mockuai.rainbowcenter.common.dto.ErpOrderDTO;
import com.mockuai.rainbowcenter.core.dao.ErpOrderDAO;
import com.mockuai.rainbowcenter.core.domain.ErpOrderDO;
import com.mockuai.rainbowcenter.core.exception.RainbowException;
import com.mockuai.rainbowcenter.core.manager.ErpOrderManager;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by lizg on 2016/6/15.
 */
@Service
public class ErpOrderManagerImpl implements ErpOrderManager{

    @Resource
    private ErpOrderDAO erpOrderDAO;

    @Override
    public ErpOrderDTO addErpOrder(ErpOrderDTO erpOrderDTO) throws RainbowException {

        ErpOrderDO erpOrderDO = new ErpOrderDO();
        BeanUtils.copyProperties(erpOrderDTO, erpOrderDO);
        Long id = erpOrderDAO.addErpOrder(erpOrderDO);
        erpOrderDTO.setId(id);
        return erpOrderDTO;
    }

    @Override
    public ErpOrderDTO getGyerpCode(String orderId) throws RainbowException {
        ErpOrderDO query = new ErpOrderDO();
        query.setOrderId(orderId);
        ErpOrderDO erpOrderDO = erpOrderDAO.getGyerpCode(query);
        ErpOrderDTO result = new ErpOrderDTO();
        BeanUtils.copyProperties(erpOrderDO, result);
        return result;
    }
}
