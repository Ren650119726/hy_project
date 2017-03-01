package com.mockuai.itemcenter.core.service.action.supplier;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.SupplierDTO;
import com.mockuai.itemcenter.common.domain.qto.SupplierQTO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.SupplierManager;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.service.action.TransAction;
import com.mockuai.itemcenter.core.util.ExceptionUtil;
import com.mockuai.itemcenter.core.util.ResponseUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yindingyu on 15/12/7.
 */
@Service
public class QuerySupplierAction extends TransAction {

    @Resource
    private SupplierManager supplierManager;

    @Override
    protected ItemResponse doTransaction(RequestContext context) throws ItemException {

        ItemRequest request = context.getRequest();

        String bizCode = (String) context.get("bizCode");

        if (request.getObject("supplierQTO") == null) {
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "supplierQTO不能为空");
        }

        SupplierQTO supplierQTO = (SupplierQTO) request.getParam("supplierQTO");

        List<SupplierDTO> supplierDTOList = supplierManager.querySupplier(supplierQTO);

        return ResponseUtil.getSuccessResponse(supplierDTOList, supplierQTO.getTotalCount());

    }


    @Override
    public String getName() {
        return ActionEnum.QUERY_SUPPLIER.getActionName();
    }
}
