package com.mockuai.itemcenter.core.service.action.itemspec;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.SpecDTO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.SpecManager;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.service.action.TransAction;
import com.mockuai.itemcenter.core.util.ExceptionUtil;
import com.mockuai.itemcenter.core.util.ResponseUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by guansheng on 15/12/7.
 */
@Service
public class GetSpecAction extends TransAction{


    @Resource
    private SpecManager specManager;

    @Override
    protected ItemResponse doTransaction(RequestContext context) throws ItemException {

        ItemRequest request = context.getRequest();

        Long id =  request.getLong("id");

        if(id==null){
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING,"id不能为空");
        }

        SpecDTO spec = specManager.getSpec(id);
        return ResponseUtil.getSuccessResponse(spec);

    }



    @Override
    public String getName() {
        return ActionEnum.GET_SPEC.getActionName();
    }
}
