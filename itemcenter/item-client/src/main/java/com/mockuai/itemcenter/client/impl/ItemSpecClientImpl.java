package com.mockuai.itemcenter.client.impl; /**
 * create by 冠生
 *
 * @date #{DATE}
 **/

import com.mockuai.itemcenter.client.ItemSpecClient;
import com.mockuai.itemcenter.common.api.*;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.domain.dto.SpecDTO;
import com.mockuai.itemcenter.common.domain.qto.SpecQTO;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by guansheng on 2016/8/30.
 */
public class ItemSpecClientImpl implements ItemSpecClient {

    @Resource
    private ItemService itemService;


    public Response<List<SpecDTO>> querySpec(SpecQTO specQTO, String appKey) {
        Request request = new BaseRequest();
        request.setParam("specQTO",specQTO);
        request.setParam("appKey",appKey);
        request.setCommand(ActionEnum.QUERY_SPEC.getActionName());
        return itemService.execute(request);
    }

    public Response<SpecDTO> getSpec(Long id, String appKey) {
        Request request = new BaseRequest();
        request.setParam("id",id);
        request.setParam("appKey",appKey);
        request.setCommand(ActionEnum.GET_SPEC.getActionName());
        return itemService.execute(request);
    }

    public Response<Void> updateSpec(SpecDTO specDTO, String appKey) {
        Request request = new BaseRequest();
        request.setParam("specDTO",specDTO);
        request.setParam("appKey",appKey);
        request.setCommand(ActionEnum.UPDATE_SPEC.getActionName());
        return itemService.execute(request);

    }

    public Response<Long> addSpec(SpecDTO specDTO, String appKey) {
        Request request = new BaseRequest();
        request.setParam("specDTO",specDTO);
        request.setParam("appKey",appKey);
        request.setCommand(ActionEnum.ADD_SPEC.getActionName());
        return itemService.execute(request);
    }

    public Response<Void> deleteSpec(Long id, String appKey) {
        Request request = new BaseRequest();
        request.setParam("id",id);
        request.setParam("appKey",appKey);
        request.setCommand(ActionEnum.DELETE_SPEC.getActionName());
        return itemService.execute(request);
    }
}
