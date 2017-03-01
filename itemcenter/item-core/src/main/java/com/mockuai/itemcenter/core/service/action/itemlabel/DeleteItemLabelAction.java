package com.mockuai.itemcenter.core.service.action.itemlabel;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.qto.RItemLabelQTO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.ItemLabelManager;
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
public class DeleteItemLabelAction extends TransAction{

    @Resource
    private ItemLabelManager itemLabelManager;



    @Override
    protected ItemResponse doTransaction(RequestContext context) throws ItemException {

        ItemRequest request = context.getRequest();

        String bizCode = (String)context.get("bizCode");

        if(request.getObject("itemLabelId")==null){
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING,"itemLabelId不能为空");
        }

        Long itemLabelId = request.getLong("itemLabelId");

        Long sellerId = request.getLong("sellerId");

        if(request.getObject("sellerId")==null){
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING,"sellerId不能为空");
        }
        RItemLabelQTO labelQTO = new RItemLabelQTO();
        labelQTO.setLabelId(itemLabelId);

        if(itemLabelManager.countRItemLabel(labelQTO) >0 ){
            throw ExceptionUtil.getException(ResponseCode.SYS_E_SERVICE_EXCEPTION,"已有商品关联该服务标签");
        }


        Long result = itemLabelManager.deleteItemLabel(itemLabelId,sellerId,bizCode);

        return ResponseUtil.getSuccessResponse(result);

    }



    @Override
    public String getName() {
        return ActionEnum.DELETE_ITEM_LABEL.getActionName();
    }
}
