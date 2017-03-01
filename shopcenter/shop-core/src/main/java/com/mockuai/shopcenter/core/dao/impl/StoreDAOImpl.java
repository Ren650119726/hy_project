package com.mockuai.shopcenter.core.dao.impl;

import com.mockuai.shopcenter.core.dao.StoreDAO;
import com.mockuai.shopcenter.core.domain.StoreDO;
import com.mockuai.shopcenter.core.exception.ShopException;
import com.mockuai.shopcenter.domain.qto.StoreQTO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yindingyu on 15/11/4.
 */
@Repository
public class StoreDAOImpl extends SqlMapClientDaoSupport implements StoreDAO {

    @Override
    public Long addStore(StoreDO storeDO) throws ShopException {
        return (Long) getSqlMapClientTemplate().insert("Store.addStore",storeDO);
    }

    @Override
    public Long UpdateStore(StoreDO storeDO) throws ShopException {
        return Long.valueOf(getSqlMapClientTemplate().update("Store.updateStore",storeDO));
    }

    @Override
    public StoreDO getStore(StoreDO query) throws ShopException {
        return (StoreDO) getSqlMapClientTemplate().queryForObject("Store.getStore",query);
    }

    @Override
    public Long deleteStore(StoreDO query) throws ShopException {
        return Long.valueOf(getSqlMapClientTemplate().delete("Store.deleteStore",query));
    }

    @Override
    public List<StoreDO> queryStore(StoreQTO storeQTO) throws ShopException {

        if(storeQTO.getPageSize()>0){

        }

        if (null != storeQTO.getNeedPaging() && storeQTO.getNeedPaging().booleanValue()) {
            Long totalCount = (Long) getSqlMapClientTemplate().queryForObject("Store.countStore",storeQTO);// 总记录数
            storeQTO.setTotalCount((int)totalCount.longValue());
            if (totalCount == 0) {
                return new ArrayList<StoreDO>();
            } else {
                storeQTO.setOffsetAndTotalPage();// 设置总页数和跳过的行数
            }
        }
        return getSqlMapClientTemplate().queryForList("Store.queryStore",storeQTO);
    }

    @Override
    public Long countQuery(StoreQTO storeQTO) {
        return (Long) getSqlMapClientTemplate().queryForObject("Store.countStore",storeQTO);
    }
}
