package com.mockuai.dts.client;

import com.mockuai.dts.common.api.action.Response;
import com.mockuai.dts.common.domain.ExportTaskDTO;
import com.mockuai.dts.common.domain.ExportTaskQTO;
import com.mockuai.dts.common.domain.ItemExportQTO;
import com.mockuai.itemcenter.common.domain.qto.ItemQTO;

import java.util.List;

/**
 * Created by luliang on 15/7/2.
 */
public interface ItemExportClient {

    public Response<Boolean> exportItems(ItemExportQTO itemExportQTO, String appKey);

    public Response<List<ExportTaskDTO>> queryExportTask(ExportTaskQTO exportTaskQTO, String appKey);


    public Response<Integer> deleteExportTask(Long id, Long sellerId, String appKey);
}
