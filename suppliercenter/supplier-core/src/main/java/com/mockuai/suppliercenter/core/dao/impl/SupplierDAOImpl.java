package com.mockuai.suppliercenter.core.dao.impl;

import com.mockuai.suppliercenter.common.qto.SupplierQTO;
import com.mockuai.suppliercenter.core.dao.SupplierDAO;
import com.mockuai.suppliercenter.core.domain.SupplierDO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierDAOImpl extends SqlMapClientDaoSupport implements
        SupplierDAO {

    public Long addSuppler(SupplierDO supplierDO) {

        Long supplierId = (Long) getSqlMapClientTemplate().insert(
                "supplier.insert", supplierDO);
        return supplierId;
    }

    public Long countSupplierByName(SupplierDO supplierDO) {
        // TODO Auto-generated method stub
        Long res = (Long) getSqlMapClientTemplate()
                .queryForObject("supplier.selectSupplierByName", supplierDO);
        return res;
    }

    public SupplierDO getSupplierById(Long id) {
        // TODO Auto-generated method stub
        SupplierDO supplierDO = (SupplierDO) getSqlMapClientTemplate()
                .queryForObject("supplier.selectById", id);
        return supplierDO;

    }

    public List<SupplierDO> querySupplier(SupplierQTO supplierQto) {

        List<SupplierDO> suppliers = getSqlMapClientTemplate().queryForList(
                "supplier.querySupplier", supplierQto);

        return suppliers;
    }

    public Long getTotalCount(SupplierQTO supplierQto) {
        Long suppliers = (Long) getSqlMapClientTemplate().queryForObject(
                "supplier.totalCount", supplierQto);

        return suppliers;

    }

    public int updateSupplier(SupplierDO supplierDO) {
        // TODO Auto-generated method stub
        int result = getSqlMapClientTemplate().update(
                "supplier.updateSupplier", supplierDO);
        return result;
    }


}
