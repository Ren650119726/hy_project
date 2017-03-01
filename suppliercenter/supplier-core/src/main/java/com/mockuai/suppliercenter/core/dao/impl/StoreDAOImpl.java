package com.mockuai.suppliercenter.core.dao.impl;

import com.mockuai.suppliercenter.common.qto.StoreQTO;
import com.mockuai.suppliercenter.core.dao.StoreDAO;
import com.mockuai.suppliercenter.core.domain.StoreDO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StoreDAOImpl extends SqlMapClientDaoSupport implements StoreDAO {

    public Long addStore(StoreDO storeDO) {

        Long id = (Long) getSqlMapClientTemplate().insert("store.insert",
                storeDO);
        return id;
    }

    public int updateStore(StoreDO storeDO) {
        // TODO Auto-generated method stub
        int result = getSqlMapClientTemplate().update(
                "store.updateStore", storeDO);
        return result;
    }

    public int updateStoreSupplierName(StoreDO storeDO) {
        // TODO Auto-generated method stub
        int result = getSqlMapClientTemplate().update(
                "store.updateStoreSupplierName", storeDO);
        return result;
    }

    public int forbiddenStore(Long id) {
        // TODO Auto-generated method stub
        int result = getSqlMapClientTemplate().update(
                "store.forbiddenStore", id);
        return result;
    }

    public int enableStore(Long id) {
        // TODO Auto-generated method stub
        int result = getSqlMapClientTemplate().update(
                "store.enableStore", id);
        return result;
    }


    public int deleteStore(Long id) {
        // TODO Auto-generated method stub
        int result = getSqlMapClientTemplate().update(
                "store.deleteStore", id);
        return result;
    }

    @Override
    public int updateGoodsUseCAS(StoreDO storeDO) {
        return this.getSqlMapClientTemplate().update("store.updateGoodsUseCAS", storeDO);
    }


    public StoreDO getStoreById(Long id) {
        // TODO Auto-generated method stub
        StoreDO storeDO = (StoreDO) getSqlMapClientTemplate().queryForObject(
                "store.selectStoreById", id);
        return storeDO;
    }

    public List<StoreDO> queryStore(StoreQTO storeQTO) {

        List<StoreDO> stores = getSqlMapClientTemplate().queryForList(
                "store.queryStore", storeQTO);

        return stores;
    }

    public List<StoreDO> getStoreList(StoreQTO storeQTO) {

        List<StoreDO> stores = getSqlMapClientTemplate().queryForList(
                "store.getStoreByStatus", storeQTO);

        return stores;
    }

    public List<Long> getStoreIdList(StoreQTO storeQTO) {

        List<Long> idList = getSqlMapClientTemplate().queryForList(
                "store.getStoreIdListByStatus", storeQTO);

        return idList;
    }


    public Long getTotalCount(StoreQTO storeQTO) {
        Long num = (Long) getSqlMapClientTemplate().queryForObject(
                "store.totalCount", storeQTO);

        return num;

    }

    public List<StoreDO> queryStoreForOrder(StoreQTO storeQTO) {

        List<StoreDO> stores = getSqlMapClientTemplate().queryForList(
                "store.queryStoreForOrder", storeQTO);

        return stores;
    }

    public Long getTotalCountForOrder(StoreQTO storeQTO) {
        Long num = (Long) getSqlMapClientTemplate().queryForObject(
                "store.totalCountForOrder", storeQTO);

        return num;

    }

    public Long getSupplierTotalStore(Long supplierId) {
        Long num = (Long) getSqlMapClientTemplate().queryForObject(
                "store.getSupplierTotalStore", supplierId);

        return num;
    }

    public Long checkSupplierStoreName(String name, Long supplierId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", name);
        map.put("supplierId", supplierId);
        Long num = (Long) getSqlMapClientTemplate().queryForObject(
                "store.checkSupplierStoreName", map);

        return num;
    }

}
