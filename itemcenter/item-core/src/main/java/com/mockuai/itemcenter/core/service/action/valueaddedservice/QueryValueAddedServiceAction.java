package com.mockuai.itemcenter.core.service.action.valueaddedservice;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.ValueAddedServiceTypeDTO;
import com.mockuai.itemcenter.common.domain.qto.ValueAddedServiceTypeQTO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.ItemManager;
import com.mockuai.itemcenter.core.manager.ValueAddedServiceManager;
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
public class QueryValueAddedServiceAction extends TransAction{

    @Resource
    private ValueAddedServiceManager valueAddedServiceManager;

    @Resource
    private ItemManager itemManager;

    @Override
    protected ItemResponse doTransaction(RequestContext context) throws ItemException {

        String bizCode = (String)context.get("bizCode");
        ItemRequest request = context.getRequest();

        if(request.getObject("valueAddedServiceTypeQTO")==null){
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING,"valueAddedServiceTypeQTO不能为空");
        }

        ValueAddedServiceTypeQTO serviceTypeQTO = request.getObject("valueAddedServiceTypeQTO",ValueAddedServiceTypeQTO.class);


        serviceTypeQTO.setBizCode(bizCode);

        List<ValueAddedServiceTypeDTO> serviceTypeDTOList = valueAddedServiceManager.queryValueAddedService(serviceTypeQTO);


        return ResponseUtil.getSuccessResponse(serviceTypeDTOList,serviceTypeQTO.getTotalCount());

    }



    @Override
    public String getName() {
        return ActionEnum.QUERY_VALUE_ADDED_SERVICE.getActionName();
    }
}
