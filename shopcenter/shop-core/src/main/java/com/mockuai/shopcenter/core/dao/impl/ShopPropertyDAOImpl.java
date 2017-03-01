package com.mockuai.shopcenter.core.dao.impl;

import com.google.common.collect.Maps;
import com.mockuai.shopcenter.core.dao.ShopPropertyDAO;
import com.mockuai.shopcenter.core.domain.ShopPropertyDO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by yindingyu on 15/11/3.
 */
@Repository
public class ShopPropertyDAOImpl extends SqlMapClientDaoSupport implements ShopPropertyDAO{

    @Override
    public ShopPropertyDO getShopProperty(ShopPropertyDO query) {

        return (ShopPropertyDO) getSqlMapClientTemplate().queryForObject("ShopProperty.getShopProperty",query);
    }

    @Override
    public Long addShopProperty(ShopPropertyDO query) {
        if(query.getValueType()==null){
            query.setValueType(2);
        }
        return (Long) getSqlMapClientTemplate().insert("ShopProperty.insertShopProperty",query);
    }

    @Override
    public Long updateShopProperty(ShopPropertyDO query) {
        if(query.getValueType()==null){
            query.setValueType(2);
        }
        return Long.valueOf(getSqlMapClientTemplate().update("ShopProperty.updateShopProperty",query));
    }

    @Override
    public Map<String, String> queryShopProperties(List<String> keys, Long sellerId, String bizCode) {

        Map<String,String> props = Maps.newHashMap();

        for(String key : keys){

            ShopPropertyDO query = new ShopPropertyDO();
            query.setBizCode(bizCode);
            query.setSellerId(sellerId);
            query.setpKey(key);
            ShopPropertyDO shopProperty = getShopProperty(query);

            if(shopProperty!=null) {
                props.put(key, shopProperty.getValue());
            }
        }

        return props;
    }

}
