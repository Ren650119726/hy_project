package com.mockuai.dts.core.service.action.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.mockuai.dts.common.TaskStatusEnum;
import com.mockuai.dts.common.api.action.DtsResponse;
import com.mockuai.dts.common.constant.ActionEnum;
import com.mockuai.dts.common.constant.ResponseCode;
import com.mockuai.dts.common.constant.TaskTypeEnum;
import com.mockuai.dts.common.domain.OrderExportQTO;
import com.mockuai.dts.common.util.TimeUtil;
import com.mockuai.dts.core.api.impl.OSSClientAPI;
import com.mockuai.dts.core.dao.ExportTaskDAO;
import com.mockuai.dts.core.domain.ExportTaskDO;
import com.mockuai.dts.core.exception.DtsException;
import com.mockuai.dts.core.manager.OrderExportManager;
import com.mockuai.dts.core.service.ExportServiceManager;
import com.mockuai.dts.core.service.OrderExportTask;
import com.mockuai.dts.core.service.RequestContext;
import com.mockuai.dts.core.service.action.Action;
import com.mockuai.dts.core.util.ResponseUtil;

/**
 * 订单导出
 * @author hzmk
 *
 */
@Service
public class ExportOrderAction implements Action {

    private static final Logger log = LoggerFactory.getLogger(ExportOrderAction.class);

    @Resource
    private OrderExportManager orderExportManager;

    @Resource
    private ExportServiceManager exportServiceManager;

    @Resource
    private ExportTaskDAO exportTaskDAO;

    @Resource
    private OSSClientAPI ossClientAPI;

    @Override
    public DtsResponse<Boolean> execute(RequestContext context) {
        OrderExportQTO qto = (OrderExportQTO)context.getRequest().getParam("orderExportQTO");
        String appKey = (String)context.getRequest().getParam("appKey");
        
        if(null==appKey){
        	 log.error("ExportOrderAction appkey is null");
        	 return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING);
        }
        

        if(qto == null) {
            return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING);
        }
        
        if(null == qto.getSellerId()){
        	return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING);
        }
        
        qto.setAppKey(appKey);

        ExportTaskDO exportTaskDO = new ExportTaskDO();
        exportTaskDO.setSellerId(qto.getSellerId());
        exportTaskDO.setTaskProcess(0);
        exportTaskDO.setTaskType(TaskTypeEnum.BIZ_ORDER_EXPORT_TYPE.getType());
        exportTaskDO.setOssBucketName(ossClientAPI.getBucketName());
        exportTaskDO.setAppKey(appKey);
        String ossKey = "order" + TimeUtil.formatTimeStamp(new Date()) + "_" + 11+".xls";
        exportTaskDO.setOssObjectKey(ossKey);

        long index = 0;
        try {
        	//新建任务记录
        	  exportTaskDO.setDeleteMark(0);
        	  exportTaskDO.setTaskStatus(TaskStatusEnum.RUNNING_TASK.getStatus());
            index = exportTaskDAO.addExportTask(exportTaskDO);
        } catch (DtsException e) {
        	log.error("db error",e);
            return new DtsResponse(ResponseCode.SYS_E_SERVICE_EXCEPTION);
        }

        if(index <= 0) {
            return new DtsResponse(ResponseCode.SYS_E_SERVICE_EXCEPTION);
        }
        exportTaskDO.setId(index);
        log.info(" OrderExportQTO : "+JSONObject.toJSONString(qto));
        try {
        	OrderExportTask task = new OrderExportTask(orderExportManager,exportTaskDAO);
        	task.setQuery(qto);
        	task.setExportTaskDO(exportTaskDO);
            exportServiceManager.submitOrderExportTask(task);
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
        return ActionEnum.EXPORT_ORDER.getActionName();
    }
}
