package com.mockuai.shopcenter.core.manager.impl;

import com.google.common.base.Strings;
import com.mockuai.shopcenter.constant.ResponseCode;
import com.mockuai.shopcenter.core.dao.ShopPropertyDAO;
import com.mockuai.shopcenter.core.domain.ShopPropertyDO;
import com.mockuai.shopcenter.core.exception.ShopException;
import com.mockuai.shopcenter.core.manager.ShopPropertyManager;
import com.mockuai.shopcenter.core.util.ExceptionUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by yindingyu on 15/11/3.
 */

@Service
public class ShopPropertyManagerImpl implements ShopPropertyManager{

    @Resource
    private ShopPropertyDAO shopPropertyDAO;

    @Override
    public String getProperty(String key, Long sellerId, String bizCode) throws ShopException{

        ShopPropertyDO query = new ShopPropertyDO();
        query.setpKey(key);
        query.setBizCode(bizCode);
        query.setSellerId(sellerId);

        ShopPropertyDO shopPropertyDO = shopPropertyDAO.getShopProperty(query);

        if(shopPropertyDO==null|| Strings.isNullOrEmpty(shopPropertyDO.getValue())){
            throw ExceptionUtil.getException(ResponseCode.BASE_PARAM_E_RECORD_NOT_EXIST,"查询不到对应的配置项");
        }

        return shopPropertyDO.getValue();
    }

    @Override
    public Integer setProperty(String key, String value, Long sellerId, String bizCode) throws ShopException {

        ShopPropertyDO query = new ShopPropertyDO();
        query.setpKey(key);
        query.setBizCode(bizCode);
        query.setSellerId(sellerId);

        ShopPropertyDO shopPropertyDO = shopPropertyDAO.getShopProperty(query);

        query.setValue(value);

        if(shopPropertyDO==null|| Strings.isNullOrEmpty(shopPropertyDO.getValue())){
            Long newId = shopPropertyDAO.addShopProperty(query);

            if(newId!=null){
                return 1;
            }
        }else{
            Long result = shopPropertyDAO.updateShopProperty(query);
            if(result>0L){
                return 1;
            }
        }

        return 0;

    }

    @Override
    public Map<String, String> getProperties(List<String> keys, Long sellerId, String bizCode)  throws ShopException{


        return shopPropertyDAO.queryShopProperties(keys, sellerId, bizCode);
    }

    @Override
    public Integer setProperties(Map<String, String> props, Long sellerId, String bizCode)  throws ShopException{

        int result = 1;
        for(String key : props.keySet()){
            result *= setProperty(key,props.get(key),sellerId,bizCode);
        }

        return result;
    }
}
