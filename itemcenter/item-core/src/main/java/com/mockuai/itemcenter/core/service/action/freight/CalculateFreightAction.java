package com.mockuai.itemcenter.core.service.action.freight;

import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.domain.dto.area.AddressDTO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.AddressManager;
import com.mockuai.itemcenter.core.manager.FreightManager;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.service.action.Action;
import com.mockuai.itemcenter.core.util.ResponseUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by yindingyu on 15/10/12.
 */
@Service
public class CalculateFreightAction implements Action{


    @Resource
    private AddressManager addressManager;

    @Resource
    private FreightManager freightManager;


    @Override
    public ItemResponse execute(RequestContext context) throws ItemException {

        String BizCode = (String) context.get("biz_Code");

        String appKey = (String) context.getRequest().getParam("appKey");


        ItemRequest request = context.getRequest();

        Long consigneeId = request.getLong("consigneeId");

        Long userId = request.getLong("userId");




        AddressDTO addressDTO = addressManager.getAddress(userId,consigneeId,appKey);
        Map<Long,Integer> itemNums = (Map<Long, Integer>) request.getObject("itemNums");


        try {
            Long result = freightManager.calculate(itemNums, addressDTO);

            return ResponseUtil.getSuccessResponse(result);
        }catch (ItemException e){
            return ResponseUtil.getErrorResponse(e.getCode(),e.getMessage());
        }catch (Exception e){
            return ResponseUtil.getErrorResponse(ResponseCode.SYS_E_DEFAULT_ERROR);
        }

    }

    @Override
    public String getName() {
        return ActionEnum.CALCULATE_FREIGHT_ACTION.getActionName();
    }



}
