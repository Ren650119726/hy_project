package com.mockuai.itemcenter.client.impl;

import com.mockuai.itemcenter.client.FreightClient;
import com.mockuai.itemcenter.common.api.BaseRequest;
import com.mockuai.itemcenter.common.api.ItemService;
import com.mockuai.itemcenter.common.api.Request;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.domain.dto.area.AddressDTO;
import com.mockuai.itemcenter.common.domain.dto.area.AreaDTO;
import com.mockuai.itemcenter.common.domain.dto.FreightTemplateDTO;
import com.mockuai.itemcenter.common.domain.qto.FreightTemplateQTO;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by yindingyu on 15/10/12.
 */
public class FreightClientImpl implements FreightClient{

    @Resource
    private ItemService itemService;

    public Response<Long> calculateFreight(Map<Long, Integer> itemNums,Long userId, Long consigneeId, String appKey) {
        Request request = new BaseRequest();

        request.setParam("itemNums",itemNums);
        request.setParam("userId",userId);
        request.setParam("consigneeId",consigneeId);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.CALCULATE_FREIGHT_ACTION.getActionName());
        return  itemService.execute(request);
    }
//
//    public Response<Long> calculateFeight(Map<Long, Integer> itemNums,AddressDTO addressDTO,String appKey) {
//        Request request = new BaseRequest();
//
//        request.setParam("itemNums",itemNums);
//        request.setParam("addressDTO",addressDTO);
//        request.setParam("appKey", appKey);
//        request.setCommand(ActionEnum.CALCULATE_FREIGHT_ACTION.getActionName());
//        return  itemService.execute(request);
//    }

    public Response<Long> addFreightTemplate(FreightTemplateDTO freightTemplateDTO, String appKey) {
        Request request = new BaseRequest();

        request.setParam("freightTemplateDTO",freightTemplateDTO);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.ADD_FREIGHT_TEMPLATE_ACTION.getActionName());
        return  itemService.execute(request);
    }

    public Response<Boolean> updateFreightTemplate(FreightTemplateDTO freightTemplateDTO, String appKey) {
        Request request = new BaseRequest();

        request.setParam("freightTemplateDTO",freightTemplateDTO);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.UPDATE_FREIGHT_TEMPLATE_ACTION.getActionName());
        return  itemService.execute(request);
    }

    public Response<FreightTemplateDTO> getFreightTemplate(long templateId, long sellerId, String appKey) {
        Request request = new BaseRequest();

        request.setParam("templateId",templateId);
        request.setParam("sellerId",sellerId);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.GET_FREIGHT_TEMPLATE_ACTION.getActionName());
        return  itemService.execute(request);
    }

    public Response<Boolean> deleteFreightTemplate(long templateId, long sellerId, String appKey) {
        Request request = new BaseRequest();

        request.setParam("templateId",templateId);
        request.setParam("sellerId",sellerId);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.DELETE_FREIGHT_TEMPLATE_ACTION.getActionName());
        return  itemService.execute(request);
    }

    public Response<List<FreightTemplateDTO>> queryFreightTemplate(FreightTemplateQTO freightTemplateQTO, String appKey) {

        Request request = new BaseRequest();
        request.setParam("freightTemplateQTO",freightTemplateQTO);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.QUERY_FREIGHT_TEMPLATE_ACTION.getActionName());
        return  itemService.execute(request);
    }

    public Response<Long> copyFreightTemplate(long templateId, long sellerId, String appKey) {
        Request request = new BaseRequest();

        request.setParam("templateId",templateId);
        request.setParam("sellerId",sellerId);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.COPY_FREIGHT_TEMPLATE_ACTION.getActionName());
        return  itemService.execute(request);
    }

    public Response<List<AreaDTO>> queryAreas(String appkey) {
        Request request = new BaseRequest();
        request.setParam("appKey",appkey);

        request.setCommand(ActionEnum.QUERY_AREAS_ACTION.getActionName());
        return  itemService.execute(request);
    }
}
