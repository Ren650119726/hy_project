package com.mockuai.shopcenter.core.service.action.store;

import com.mockuai.shopcenter.api.ShopResponse;
import com.mockuai.shopcenter.constant.ActionEnum;
import com.mockuai.shopcenter.constant.PropertyConsts;
import com.mockuai.shopcenter.constant.ResponseCode;
import com.mockuai.shopcenter.core.exception.ShopException;
import com.mockuai.shopcenter.core.manager.ShopPropertyManager;
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
public class GetStoreAction extends TransAction{

    @Resource
    private StoreManager storeManager;

    @Resource
    private ShopPropertyManager shopPropertyManager;

    @Override
    protected ShopResponse doTransaction(RequestContext context) throws ShopException {

        String bizCode = (String) context.get("bizCode");

        ShopRequest request = context.getRequest();

        Long id = request.getLong("id");
        Long seller_id  = request.getLong("sellerId");

        if(id==null){
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING,"id不能为空");
        }
        if(seller_id==null){
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING,"sellerId不能为空");
        }


        /**
         * 获取门店配送开关状态
         */
        int supportDelivery = 0;

        try {

            String value = shopPropertyManager.getProperty(PropertyConsts.SUPPORT_DELIVERY, seller_id, bizCode);

            if("1".equals(value)){
                supportDelivery =1;
            }

        }catch (ShopException e){
            if(e.getCode()!=ResponseCode.BASE_PARAM_E_RECORD_NOT_EXIST.getCode()){
                throw e;
            }
        }

        /**
         * 获取门店自提开关状态
         */
        int supportPickUp = 0;

        try {

            String value = shopPropertyManager.getProperty(PropertyConsts.SUPPORT_PICK_UP, seller_id, bizCode);

            if("1".equals(value)){
                supportPickUp =1;
            }

        }catch (ShopException e){
            if(e.getCode()!=ResponseCode.BASE_PARAM_E_RECORD_NOT_EXIST.getCode()){
                throw e;
            }
        }


        StoreDTO storeDTO = storeManager.getStore(id,seller_id,bizCode);


        if(storeDTO==null){
            throw ExceptionUtil.getException(ResponseCode.BASE_PARAM_E_RECORD_NOT_EXIST,"查询的记录不存在");
        }

        if(supportDelivery==0) {
            storeDTO.setSupportDelivery(0);
        }

        if(supportPickUp==0) {
            storeDTO.setSupportPickUp(0);
        }

        return ResponseUtil.getSuccessResponse(storeDTO);
    }

    @Override
    public String getName() {
        return ActionEnum.GET_STORE.getActionName();
    }
}
