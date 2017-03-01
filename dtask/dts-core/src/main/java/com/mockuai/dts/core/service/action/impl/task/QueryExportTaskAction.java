package com.mockuai.dts.core.service.action.impl.task;

import com.mockuai.dts.common.api.action.DtsResponse;
import com.mockuai.dts.common.constant.ActionEnum;
import com.mockuai.dts.common.constant.ResponseCode;
import com.mockuai.dts.common.domain.ExportTaskDTO;
import com.mockuai.dts.common.domain.ExportTaskQTO;
import com.mockuai.dts.common.domain.ItemExportQTO;
import com.mockuai.dts.core.exception.DtsException;
import com.mockuai.dts.core.manager.ExportTaskManager;
import com.mockuai.dts.core.service.RequestContext;
import com.mockuai.dts.core.service.action.Action;
import com.mockuai.dts.core.util.ResponseUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by luliang on 15/7/23.
 */
@Service
public class QueryExportTaskAction implements Action {

    @Resource
    private ExportTaskManager exportTaskManager;

    @Override
    public DtsResponse execute(RequestContext context) {
        DtsResponse response = null;
        ExportTaskQTO exportTaskQTO = (ExportTaskQTO)context.getRequest().getParam("exportTaskQTO");
        if(exportTaskQTO == null) {
            return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING);
        }
        List<ExportTaskDTO> exportTaskDTOList = null;
        try {
            exportTaskQTO.setDeleteMark(0);
            exportTaskDTOList = exportTaskManager.queryExportTask(exportTaskQTO);
        } catch (DtsException e) {
            response = ResponseUtil.getErrorResponse(e.getCode(), e.getMessage());
            return response;
        }
        response = ResponseUtil.getSuccessResponse(exportTaskDTOList, exportTaskQTO.getTotalCount());
        return response;
    }

    @Override
    public String getName() {
        return ActionEnum.QUERY_EXPORT_TASK.getActionName();
    }
}
