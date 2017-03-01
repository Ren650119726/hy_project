package com.mockuai.itemcenter.core.dao.impl;

import com.mockuai.itemcenter.common.domain.qto.SupplierQTO;
import com.mockuai.itemcenter.core.dao.SupplierDAO;
import com.mockuai.itemcenter.core.domain.SupplierDO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

@Repository
public class SupplierDAOImpl extends SqlMapClientDaoSupport implements SupplierDAO {


    @Override
    public Long addSupplier(SupplierDO supplierDO) {
        return (Long) getSqlMapClientTemplate().insert("supplier.addSupplier", supplierDO);
    }

    @Override
    public Long updateSupplier(SupplierDO supplierDO) {
        return Long.valueOf(getSqlMapClientTemplate().update("supplier.updateSupplier", supplierDO));
    }

    @Override
    public Long deleteSupplier(SupplierDO supplierDO) {
        return Long.valueOf(getSqlMapClientTemplate().update("supplier.deleteSupplier", supplierDO));
    }

    @Override
    public SupplierDO getSupplier(SupplierDO supplierDO) {
        return (SupplierDO) getSqlMapClientTemplate().queryForObject("supplier.addSupplier", supplierDO);
    }

    @Override
    public List<SupplierDO> querySupplier(SupplierQTO supplierQTO) {

        if (supplierQTO.getNeedPaging() != null && supplierQTO.getNeedPaging()) {

            Long count = (Long) getSqlMapClientTemplate().queryForObject("supplier.countSupplier", supplierQTO);

            if (count > 0) {
                supplierQTO.setTotalCount(count.intValue());
                return getSqlMapClientTemplate().queryForList("supplier.querySupplier", supplierQTO);
            } else {
                return Collections.EMPTY_LIST;
            }
        }

        return getSqlMapClientTemplate().queryForList("supplier.querySupplier", supplierQTO);
    }


}