package com.mockuai.shopcenter.core.manager.impl;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.mockuai.shopcenter.constant.ResponseCode;
import com.mockuai.shopcenter.core.dao.StorePropertyDAO;
import com.mockuai.shopcenter.core.domain.StorePropertyDO;
import com.mockuai.shopcenter.core.exception.ShopException;
import com.mockuai.shopcenter.core.manager.StorePropertyManager;
import com.mockuai.shopcenter.core.util.ExceptionUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by yindingyu on 15/11/3.
 */

@Service
public class StorePropertyManagerImpl implements StorePropertyManager {

    @Resource
    private StorePropertyDAO storePropertyDAO;

    @Override
    public String getProperty(String key, Long sellerId,Long storeId, String bizCode) throws ShopException{

        StorePropertyDO query = new StorePropertyDO();
        query.setpKey(key);
        query.setBizCode(bizCode);
        query.setSellerId(sellerId);
        query.setStoreId(storeId);

        StorePropertyDO storePropertyDO = storePropertyDAO.getStoreProperty(query);

        if(storePropertyDO==null|| Strings.isNullOrEmpty(storePropertyDO.getValue())){
            throw ExceptionUtil.getException(ResponseCode.BASE_PARAM_E_RECORD_NOT_EXIST,"查询不到对应的配置项");
        }

        return storePropertyDO.getValue();
    }

    @Override
    public Integer setProperty(String key, String value, Long sellerId,Long storeId, String bizCode) throws ShopException {

        StorePropertyDO query = new StorePropertyDO();
        query.setpKey(key);
        query.setBizCode(bizCode);
        query.setSellerId(sellerId);
        query.setStoreId(storeId);

        StorePropertyDO storePropertyDO = storePropertyDAO.getStoreProperty(query);

        query.setValue(value);

        if(storePropertyDO==null|| Strings.isNullOrEmpty(storePropertyDO.getValue())){
            Long newId = storePropertyDAO.addStoreProperty(query);

            if(newId!=null){
                return 1;
            }
        }else{
            Long result = storePropertyDAO.updateStoreProperty(query);
            if(result>0L){
                return 1;
            }
        }

        return 0;

    }

    @Override
    public Map<String, String> getProperties(List<String> keys, Long sellerId,Long storeId, String bizCode)  throws ShopException{

        return storePropertyDAO.queryStorePropertiesMap(keys, sellerId, storeId, bizCode);
    }

    @Override
    public List<Long> queryStoreIdsByProperty(String key, String value, Long sellerId, String bizCode) throws ShopException {

        StorePropertyDO query = new StorePropertyDO();
        query.setpKey(key);
        query.setValue(value);
        query.setBizCode(bizCode);
        query.setSellerId(sellerId);

        List<StorePropertyDO> storePropertyDOList = storePropertyDAO.queryStoreProperties(query);

        List<Long> result = Lists.newArrayList();

        for(StorePropertyDO storePropertyDO: storePropertyDOList){
            result.add(storePropertyDO.getStoreId());
        }

        return  result;
    }

    @Override
    public Integer batchDeleteProperty(Long sellerId, String key, String value, String bizCode) throws ShopException {

        StorePropertyDO query = new StorePropertyDO();
        query.setSellerId(sellerId);
        query.setpKey(key);
        query.setValue(value);
        query.setBizCode(bizCode);

        Integer result = storePropertyDAO.deleteStoreProperties(query);

        return result;
    }

    @Override
    public Integer batchSetProperty(List<Long> storeIds, Long sellerId, String key, String value, String bizCode) throws ShopException {

        Integer result = storePropertyDAO.batchInsertProperty(storeIds, sellerId, key, value, bizCode);

        return result;
    }

}
