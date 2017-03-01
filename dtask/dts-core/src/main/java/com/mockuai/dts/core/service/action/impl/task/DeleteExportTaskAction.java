package com.mockuai.dts.core.service.action.impl.task;

import com.mockuai.dts.common.api.action.DtsResponse;
import com.mockuai.dts.common.constant.ActionEnum;
import com.mockuai.dts.common.constant.ResponseCode;
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
public class DeleteExportTaskAction implements Action {

    @Resource
    private ExportTaskManager exportTaskManager;

    @Override
    public DtsResponse execute(RequestContext context) {
        DtsResponse response = null;
        Long sellerId = (Long)context.getRequest().getParam("sellerId");
        Long id = (Long)context.getRequest().getParam("id");
        if(id == null || sellerId == null) {
            return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING);
        }
        Integer count = null;
        try {
            count = exportTaskManager.deleteExportTask(id, sellerId);
        } catch (DtsException e) {
            response = ResponseUtil.getErrorResponse(e.getCode(), e.getMessage());
            return response;
        }
        response = ResponseUtil.getSuccessResponse(count);
        return response;
    }

    @Override
    public String getName() {
        return ActionEnum.DELETE_EXPORT_TASK.getActionName();
    }
}
