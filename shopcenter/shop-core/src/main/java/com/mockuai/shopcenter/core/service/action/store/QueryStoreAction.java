package com.mockuai.shopcenter.core.service.action.store;

import com.google.common.base.Strings;
import com.mockuai.shopcenter.api.Response;
import com.mockuai.shopcenter.api.ShopResponse;
import com.mockuai.shopcenter.constant.ActionEnum;
import com.mockuai.shopcenter.constant.PropertyConsts;
import com.mockuai.shopcenter.constant.ResponseCode;
import com.mockuai.shopcenter.core.exception.ShopException;
import com.mockuai.shopcenter.core.manager.ShopPropertyManager;
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
import java.util.Collections;
import java.util.List;

/**
 * Created by yindingyu on 15/11/3.
 */
@Service
public class QueryStoreAction extends TransAction {

    @Resource
    private StoreManager storeManager;

    @Resource
    private ShopPropertyManager shopPropertyManager;

    @Resource
    private StorePropertyManager storePropertyManager;

    @Override
    protected ShopResponse doTransaction(RequestContext context) throws ShopException {
        String bizCode = (String) context.get("bizCode");

        ShopRequest request = context.getRequest();

        StoreQTO storeQTO = (StoreQTO) request.getObject("storeQTO");

        if (storeQTO == null) {
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "StoreQTO不能为空");
        }

        storeQTO.setBizCode(bizCode);


        if (request.getParam("supportRecovery") != null && "1".equals(request.getParam("supportRecovery"))) {

            if (storeQTO.getSellerId() == null) {
                throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "sellerId不能为空");
            }

            String value = shopPropertyManager.getProperty(PropertyConsts.SUPPORT_STORE_RECOVERY, storeQTO.getSellerId(), bizCode);

            if (value.equals("2")) {   //如果是部分门店支持，则只查询部分门店

                List<Long> storeIds = storePropertyManager.queryStoreIdsByProperty(PropertyConsts.SUPPORT_STORE_RECOVERY, "1", storeQTO.getSellerId(), storeQTO.getBizCode());

                if (storeIds == null || storeIds.size() < 1) {
                    return ResponseUtil.getSuccessResponse(Collections.EMPTY_LIST, 0L);
                }

                storeQTO.setIdList(storeIds);
            }

        } else if (request.getParam("supportRepair") != null && "1".equals(request.getParam("supportRepair"))) {

            if (storeQTO.getSellerId() == null) {
                throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "sellerId不能为空");
            }

            String value = shopPropertyManager.getProperty(PropertyConsts.SUPPORT_STORE_REPAIR, storeQTO.getSellerId(), bizCode);

            if (value.equals("2")) {     //如果是部分门店支持，则只查询部分门店

                List<Long> storeIds = storePropertyManager.queryStoreIdsByProperty(PropertyConsts.SUPPORT_STORE_REPAIR, "1", storeQTO.getSellerId(), storeQTO.getBizCode());

                if (storeIds == null || storeIds.size() < 1) {
                    return ResponseUtil.getSuccessResponse(Collections.EMPTY_LIST, 0L);
                }

                storeQTO.setIdList(storeIds);
            }
        }


        List<StoreDTO> storeDTOs = storeManager.queryStore(storeQTO);

        return ResponseUtil.getSuccessResponse(storeDTOs, storeQTO.getTotalCount());
    }


    @Override
    public String getName() {
        return ActionEnum.QUERY_STORE.getActionName();
    }
}
