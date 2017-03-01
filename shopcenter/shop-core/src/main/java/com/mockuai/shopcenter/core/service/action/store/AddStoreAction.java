package com.mockuai.shopcenter.core.service.action.store;

import com.google.common.base.Strings;
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
public class AddStoreAction extends TransAction{

    @Resource
    private StoreManager storeManager;


    @Override
    protected ShopResponse doTransaction(RequestContext context) throws ShopException {

        String bizCode = (String) context.get("bizCode");

        ShopRequest request = context.getRequest();

        StoreDTO storeDTO = (StoreDTO) request.getParam("storeDTO");

        if(storeDTO==null){
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING,"storeDTO不能为空");
        }

        //填充biz_Code
        storeDTO.setBizCode(bizCode);

        Long newId = storeManager.addStore(storeDTO);

        return ResponseUtil.getSuccessResponse(newId);

    }

    @Override
    public String getName() {
        return ActionEnum.ADD_STORE.getActionName();
    }
}
