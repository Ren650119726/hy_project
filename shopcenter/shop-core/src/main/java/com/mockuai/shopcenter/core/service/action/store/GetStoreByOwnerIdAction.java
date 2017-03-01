package com.mockuai.shopcenter.core.service.action.store;

import com.mockuai.shopcenter.api.ShopResponse;
import com.mockuai.shopcenter.constant.ActionEnum;
import com.mockuai.shopcenter.constant.ResponseCode;
import com.mockuai.shopcenter.core.exception.ShopException;
import com.mockuai.shopcenter.core.manager.StoreManager;
import com.mockuai.shopcenter.core.service.RequestContext;
import com.mockuai.shopcenter.core.service.ShopRequest;
import com.mockuai.shopcenter.core.service.action.TransAction;
import com.mockuai.shopcenter.core.util.ExceptionUtil;
import com.mockuai.shopcenter.core.util.ResponseUtil;
import com.mockuai.shopcenter.domain.dto.StoreDTO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by yindingyu on 15/11/3.
 */
@Service
public class GetStoreByOwnerIdAction extends TransAction{

    @Resource
    private StoreManager storeManager;

    @Override
    protected ShopResponse doTransaction(RequestContext context) throws ShopException {
        String bizCode = (String) context.get("bizCode");

        ShopRequest request = context.getRequest();

        Long ownerId  = request.getLong("ownerId");

        if(ownerId==null){
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING,"ownerId不能为空");
        }

        StoreDTO storeDTO = storeManager.getStoreByOwnerId(ownerId,bizCode);

        if(storeDTO==null){
            throw ExceptionUtil.getException(ResponseCode.BASE_PARAM_E_RECORD_NOT_EXIST,"查询不到对应的门店");
        }

        return ResponseUtil.getSuccessResponse(storeDTO);

    }

    @Override
    public String getName() {
        return ActionEnum.GET_STORE_BY_OWNER_ID.getActionName();
    }
}
