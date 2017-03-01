package com.mockuai.dts.client.impl;

import com.mockuai.dts.client.ItemExportClient;
import com.mockuai.dts.common.api.DtsService;
import com.mockuai.dts.common.api.action.DtsRequest;
import com.mockuai.dts.common.api.action.Request;
import com.mockuai.dts.common.api.action.Response;
import com.mockuai.dts.common.constant.ActionEnum;
import com.mockuai.dts.common.domain.ExportTaskDTO;
import com.mockuai.dts.common.domain.ExportTaskQTO;
import com.mockuai.dts.common.domain.ItemExportQTO;
import com.mockuai.itemcenter.common.domain.qto.ItemQTO;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by luliang on 15/7/2.
 */
public class ItemExportClientImpl implements ItemExportClient {

    @Resource
    private DtsService dtsService;

    public Response<Boolean> exportItems(ItemExportQTO itemExportQTO, String appKey) {
        Request request = new DtsRequest();
        request.setParam("itemExportQTO", itemExportQTO);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.EXPORT_ITEM.getActionName());
        return dtsService.execute(request);
    }

    // 查询任务列表;
    public Response<List<ExportTaskDTO>> queryExportTask(ExportTaskQTO exportTaskQTO, String appKey) {
        Request request = new DtsRequest();
        request.setParam("exportTaskQTO", exportTaskQTO);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.QUERY_EXPORT_TASK.getActionName());
        return dtsService.execute(request);
    }

    // 删除任务列表;
    public Response<Integer> deleteExportTask(Long id, Long sellerId, String appKey) {
        Request request = new DtsRequest();
        request.setParam("sellerId", sellerId);
        request.setParam("id", id);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.DELETE_EXPORT_TASK.getActionName());
        return dtsService.execute(request);
    }

}
