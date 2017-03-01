package com.mockuai.dts.core.service.action.impl;

import com.mockuai.dts.common.TaskStatusEnum;
import com.mockuai.dts.common.api.action.DtsResponse;
import com.mockuai.dts.common.constant.ActionEnum;
import com.mockuai.dts.common.constant.ResponseCode;
import com.mockuai.dts.common.constant.TaskTypeEnum;
import com.mockuai.dts.common.domain.ItemExportQTO;
import com.mockuai.dts.common.util.TimeUtil;
import com.mockuai.dts.core.api.impl.OSSClientAPI;
import com.mockuai.dts.core.dao.ExportTaskDAO;
import com.mockuai.dts.core.domain.ExportTaskDO;
import com.mockuai.dts.core.exception.DtsException;
import com.mockuai.dts.core.manager.ItemExportManager;
import com.mockuai.dts.core.service.ExportTask;
import com.mockuai.dts.core.service.ExportServiceManager;
import com.mockuai.dts.core.service.RequestContext;
import com.mockuai.dts.core.service.action.Action;
import com.mockuai.dts.core.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.UUID;

/**
 * 商品导出;
 * Created by luliang on 15/7/1.
 */
@Service
public class ExportItemAction implements Action {

    private static final Logger log = LoggerFactory.getLogger(ExportItemAction.class);

    @Resource
    private ItemExportManager itemExportManager;

    @Resource
    private ExportServiceManager exportServiceManager;

    @Resource
    private ExportTaskDAO exportTaskDAO;

    @Resource
    private OSSClientAPI ossClientAPI;

    @Override
    public DtsResponse<Boolean> execute(RequestContext context) {
        ItemExportQTO itemExportQTO = (ItemExportQTO)context.getRequest().getParam("itemExportQTO");
        String appKey = (String)context.getRequest().getParam("appKey");

        if(itemExportQTO == null) {
            return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING);
        }

        ExportTask exportCallBack = new ExportTask(itemExportQTO, itemExportManager);
        ExportTaskDO exportTaskDO = new ExportTaskDO();
        exportTaskDO.setSellerId(itemExportQTO.getSellerId());
        exportTaskDO.setTaskProcess(0);
        exportTaskDO.setTaskType(TaskTypeEnum.ITEM_EXPORT_TYPE.getType());
        exportTaskDO.setOssBucketName(ossClientAPI.getBucketName());
        exportTaskDO.setAppKey(appKey);
        String ossKey = "item" + TimeUtil.formatTimeStamp(new Date()) + "_" + itemExportQTO.getSellerId() + ".xls";
        exportTaskDO.setOssObjectKey(ossKey);

        long index = 0;
        try {
            exportTaskDO.setDeleteMark(0);
            exportTaskDO.setTaskStatus(TaskStatusEnum.RUNNING_TASK.getStatus());
            index = exportTaskDAO.addExportTask(exportTaskDO);
        } catch (DtsException e) {
            return new DtsResponse(ResponseCode.SYS_E_SERVICE_EXCEPTION);
        }

        if(index <= 0) {
            return new DtsResponse(ResponseCode.SYS_E_SERVICE_EXCEPTION);
        }
        exportTaskDO.setId(index);

        exportCallBack.setExportTaskDO(exportTaskDO);
        exportCallBack.setExportTaskDAO(exportTaskDAO);

//        try {
//            itemExportManager.exportItems(itemExportQTO, exportTaskDO);
//        } catch (DtsException e) {
//            e.printStackTrace();
//        }
        try {
            exportServiceManager.submitExportTask(exportCallBack);
        } catch (Exception e) {
            log.error("submit task exception:", e);
            return new DtsResponse(ResponseCode.SYS_E_SERVICE_EXCEPTION);
        }
        DtsResponse response = new DtsResponse(ResponseCode.SUCCESS);
        response.setModule(true);
        return response;
    }

    @Override
    public String getName() {
        return ActionEnum.EXPORT_ITEM.getActionName();
    }
}
