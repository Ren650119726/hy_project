package com.mockuai.itemcenter.core.service.action.valueaddedservice;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.*;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.*;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.service.action.TransAction;
import com.mockuai.itemcenter.core.util.ExceptionUtil;
import com.mockuai.itemcenter.core.util.ResponseUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yindingyu on 15/12/7.
 */
@Service
public class AddValueAddedServiceAction extends TransAction{

    @Resource
    private ValueAddedServiceManager valueAddedServiceManager;

    @Resource
    private ItemManager itemManager;

    @Override
    protected ItemResponse doTransaction(RequestContext context) throws ItemException {

        ItemRequest request = context.getRequest();

        String bizCode = (String)context.get("bizCode");

        if(request.getObject("valueAddedServiceTypeDTO")==null){
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING,"valueAddedServiceTypeDTO不能为空");
        }

        ValueAddedServiceTypeDTO serviceDTO = request.getObject("valueAddedServiceTypeDTO",ValueAddedServiceTypeDTO.class);

        serviceDTO.setBizCode(bizCode);

        Long result = valueAddedServiceManager.addValueAddedService(serviceDTO);


        String appKey = request.getString("appKey");


        return ResponseUtil.getSuccessResponse(result);

    }



    @Override
    public String getName() {
        return ActionEnum.ADD_VALUE_ADDED_SERVICE.getActionName();
    }
}
