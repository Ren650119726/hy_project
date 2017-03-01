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
public class DeleteStoreAction extends TransAction{

    @Resource
    private StoreManager storeManager;

    @Override
    protected ShopResponse doTransaction(RequestContext context) throws ShopException {

        String bizCode = (String) context.get("bizCode");

        ShopRequest request = context.getRequest();

        Long id = request.getLong("id");
        Long seller_id  = request.getLong("sellerId");

        if(id==null){
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "id不能为空");
        }
        if(seller_id==null){
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING,"sellerId不能为空");
        }

        StoreDTO storeDTO = storeManager.getStore(id,seller_id,bizCode);

        if(storeDTO==null){
            throw ExceptionUtil.getException(ResponseCode.BASE_PARAM_E_RECORD_NOT_EXIST,"查询的记录不存在");
        }

        long result = storeManager.deleteStore(id,seller_id,bizCode);

        if(result<1){
            throw ExceptionUtil.getException(ResponseCode.SYS_E_SERVICE_EXCEPTION,"删除记录失败，请重试");
        }

        return ResponseUtil.getSuccessResponse(storeDTO.getOwnerId());
    }

    @Override
    public String getName() {
        return ActionEnum.DELETE_STORE.getActionName();
    }
}
