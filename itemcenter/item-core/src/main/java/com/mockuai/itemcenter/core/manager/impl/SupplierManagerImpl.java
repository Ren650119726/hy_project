package com.mockuai.itemcenter.core.manager.impl;

import com.google.common.collect.Lists;
import com.mockuai.itemcenter.common.domain.dto.SupplierDTO;
import com.mockuai.itemcenter.common.domain.qto.SupplierQTO;
import com.mockuai.itemcenter.core.dao.SupplierDAO;
import com.mockuai.itemcenter.core.domain.SupplierDO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.SupplierManager;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * Created by yindingyu on 16/3/21.
 */
@Service
public class SupplierManagerImpl implements SupplierManager {

    @Resource
    private SupplierDAO supplierDAO;

    @Override
    public Long addSupplier(SupplierDTO supplierDTO) throws ItemException {

        SupplierDO supplierDO = new SupplierDO();
        BeanUtils.copyProperties(supplierDTO, supplierDO);

        return supplierDAO.addSupplier(supplierDO);
    }

    @Override
    public Long updateSupplier(SupplierDTO supplierDTO) throws ItemException {
        SupplierDO supplierDO = new SupplierDO();
        BeanUtils.copyProperties(supplierDTO, supplierDO);

        return supplierDAO.updateSupplier(supplierDO);
    }

    @Override
    public Long deleteSupplier(Long id, Long sellerId, String bizCode) throws ItemException {
        SupplierDO supplierDO = new SupplierDO();
        supplierDO.setId(id);
        supplierDO.setBizCode(bizCode);
        supplierDO.setSellerId(sellerId);

        return supplierDAO.deleteSupplier(supplierDO);
    }

    @Override
    public SupplierDTO getSupplier(Long id, Long sellerId, String bizCode) throws ItemException {
        SupplierDO query = new SupplierDO();
        query.setBizCode(bizCode);
        query.setSellerId(sellerId);
        query.setId(id);

        SupplierDO supplierDO = supplierDAO.getSupplier(query);

        SupplierDTO supplierDTO = new SupplierDTO();

        BeanUtils.copyProperties(supplierDO, supplierDTO);

        return supplierDTO;
    }

    @Override
    public List<SupplierDTO> querySupplier(SupplierQTO supplierQTO) throws ItemException {

        List<SupplierDO> supplierDOList = supplierDAO.querySupplier(supplierQTO);

        if (supplierDOList.size() == 0) {
            return Collections.EMPTY_LIST;
        }

        List<SupplierDTO> supplierDTOList = Lists.newArrayListWithCapacity(supplierDOList.size());

        for (SupplierDO supplierDO : supplierDOList) {

            SupplierDTO supplierDTO = new SupplierDTO();

            BeanUtils.copyProperties(supplierDO, supplierDTO);

            supplierDTOList.add(supplierDTO);
        }

        return supplierDTOList;
    }
}
