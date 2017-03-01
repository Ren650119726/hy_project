package com.mockuai.shopcenter.core.dao.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mockuai.shopcenter.core.dao.StorePropertyDAO;
import com.mockuai.shopcenter.core.domain.StorePropertyDO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import javax.mail.Store;
import java.util.List;
import java.util.Map;

/**
 * Created by yindingyu on 15/11/3.
 */
@Repository
public class StorePropertyDAOImpl extends SqlMapClientDaoSupport implements StorePropertyDAO{

    @Override
    public StorePropertyDO getStoreProperty(StorePropertyDO query) {

        return (StorePropertyDO) getSqlMapClientTemplate().queryForObject("StoreProperty.getStoreProperty", query);
    }

    @Override
    public Long addStoreProperty(StorePropertyDO query) {
        if(query.getValueType()==null){
            query.setValueType(2);
        }
        return (Long) getSqlMapClientTemplate().insert("StoreProperty.insertStoreProperty",query);
    }

    @Override
    public Long updateStoreProperty(StorePropertyDO query) {
        if(query.getValueType()==null){
            query.setValueType(2);
        }
        return Long.valueOf(getSqlMapClientTemplate().update("StoreProperty.updateStoreProperty",query));
    }

    @Override
    public List<StorePropertyDO> queryStoreProperties(StorePropertyDO query) {
        List<StorePropertyDO> list =  getSqlMapClientTemplate().queryForList("StoreProperty.queryStoreProperties",query);
        return list;
    }

    @Override
    public Map<String, String> queryStorePropertiesMap(List<String> keys, Long sellerId,Long storeId, String bizCode) {

        Map<String,String> props = Maps.newHashMap();

        for(String key : keys){

            StorePropertyDO query = new StorePropertyDO();
            query.setBizCode(bizCode);
            query.setSellerId(sellerId);
            query.setStoreId(storeId);
            query.setpKey(key);
            StorePropertyDO storeProperty = getStoreProperty(query);

            if(storeProperty!=null) {
                props.put(key, storeProperty.getValue());
            }
        }

        return props;
    }



    @Override
    public Integer batchInsertProperty(List<Long> storeIds, Long sellerId, String key, String value, String bizCode) {

        List<StorePropertyDO> list = Lists.newArrayList();
        for(Long storeId : storeIds){
            StorePropertyDO storePropertyDO = new StorePropertyDO();
            storePropertyDO.setStoreId(storeId);
            storePropertyDO.setSellerId(sellerId);
            storePropertyDO.setBizCode(bizCode);
            storePropertyDO.setpKey(key);
            storePropertyDO.setValue(value);
            storePropertyDO.setValueType(2);
            list.add(storePropertyDO);
        }

         getSqlMapClientTemplate().insert("StoreProperty.batchInsertProperty",list);

        return 1;
    }

    @Override
    public Integer deleteStoreProperties(StorePropertyDO query) {
        return getSqlMapClientTemplate().delete("StoreProperty.deleteStorePropertyPhysically", query);
    }

}
