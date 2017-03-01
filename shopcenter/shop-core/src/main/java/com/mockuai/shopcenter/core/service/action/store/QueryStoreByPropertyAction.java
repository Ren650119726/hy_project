package com.mockuai.shopcenter.core.service.action.store;

import com.google.common.base.Strings;
import com.mockuai.shopcenter.api.ShopResponse;
import com.mockuai.shopcenter.constant.ActionEnum;
import com.mockuai.shopcenter.constant.PropertyConsts;
import com.mockuai.shopcenter.constant.ResponseCode;
import com.mockuai.shopcenter.core.exception.ShopException;
import com.mockuai.shopcenter.core.manager.StoreManager;
import com.mockuai.shopcenter.core.manager.StorePropertyManager;
import com.mockuai.shopcenter.core.service.RequestContext;
import com.mockuai.shopcenter.core.service.ShopRequest;
import com.mockuai.shopcenter.core.service.action.TransAction;
import com.mockuai.shopcenter.core.util.ExceptionUtil;
import com.mockuai.shopcenter.core.util.ResponseUtil;
import com.mockuai.shopcenter.domain.dto.StoreDTO;
import com.mockuai.shopcenter.domain.qto.StoreQTO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yindingyu on 15/11/3.
 */
@Service
public class QueryStoreByPropertyAction extends TransAction{

    @Resource
    private StoreManager storeManager;

    @Resource
    private StorePropertyManager storePropertyManager;

    @Override
    protected ShopResponse doTransaction(RequestContext context) throws ShopException {
        String bizCode = (String) context.get("bizCode");

        ShopRequest request = context.getRequest();

        Long sellerId = request.getLong("sellerId");

        String key = request.getString("key");

        String value = request.getString("value");

        if(sellerId==null){
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING,"sellerId不能为空");
        }

        if(Strings.isNullOrEmpty("key")){
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING,"key不能为空");
        }

        if(Strings.isNullOrEmpty("value")){
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING,"value不能为空");
        }




        List<Long> storeIds = storePropertyManager.queryStoreIdsByProperty(key,value,sellerId,bizCode);

        return ResponseUtil.getSuccessResponse(storeIds);
    }


    @Override
    public String getName() {
        return ActionEnum.QUERY_STORE_IDS_BY_PROPERTY.getActionName();
    }
}
