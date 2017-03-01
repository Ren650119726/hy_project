package com.mockuai.itemcenter.core.service.action.supplier;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.SupplierDTO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.SupplierManager;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.service.action.TransAction;
import com.mockuai.itemcenter.core.util.ExceptionUtil;
import com.mockuai.itemcenter.core.util.ResponseUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by yindingyu on 15/12/7.
 */
@Service
public class GetSupplierAction extends TransAction {

    @Resource
    private SupplierManager supplierManager;

    @Override
    protected ItemResponse doTransaction(RequestContext context) throws ItemException {

        ItemRequest request = context.getRequest();

        String bizCode = (String) context.get("bizCode");

        if (request.getLong("id") == null) {
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "id不能为空");
        }

        if (request.getLong("supplierId") == null) {
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "supplierId不能为空");
        }

        Long id = request.getLong("id");

        Long supplierId = request.getLong("supplierId");


        SupplierDTO supplierDTO = supplierManager.getSupplier(id, supplierId, bizCode);

        return ResponseUtil.getSuccessResponse(supplierDTO);

    }


    @Override
    public String getName() {
        return ActionEnum.GET_SUPPLIER.getActionName();
    }
}
