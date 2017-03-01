//package com.mockuai.dts.core.service.action.impl;
//
//import com.mockuai.dts.common.TaskStatusEnum;
//import com.mockuai.dts.common.api.action.DtsResponse;
//import com.mockuai.dts.common.constant.ActionEnum;
//import com.mockuai.dts.common.constant.ResponseCode;
//import com.mockuai.dts.common.constant.TaskTypeEnum;
//import com.mockuai.dts.common.domain.MemberExportQTO;
//import com.mockuai.dts.common.util.TimeUtil;
//import com.mockuai.dts.core.api.impl.OSSClientAPI;
//import com.mockuai.dts.core.dao.ExportTaskDAO;
//import com.mockuai.dts.core.domain.ExportTaskDO;
//import com.mockuai.dts.core.exception.DtsException;
//import com.mockuai.dts.core.manager.MemberExportManager;
//import com.mockuai.dts.core.service.ExportServiceManager;
//import com.mockuai.dts.core.service.MemberExportTask;
//import com.mockuai.dts.core.service.RequestContext;
//import com.mockuai.dts.core.service.action.Action;
//import com.mockuai.dts.core.util.ResponseUtil;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.Resource;
//import java.util.Date;
//
///**
// * Created by duke on 15/12/7.
// */
//@Service
//public class ExportMemberAction implements Action {
//    private static final Logger log = LoggerFactory.getLogger(ExportMemberAction.class);
//
//    @Resource
//    private MemberExportManager memberExportManager;
//
//    @Resource
//    private ExportServiceManager exportServiceManager;
//
//    @Resource
//    private ExportTaskDAO exportTaskDAO;
//
//    @Resource
//    private OSSClientAPI ossClientAPI;
//
//    @Override
//    public DtsResponse execute(RequestContext context) {
//        MemberExportQTO memberExportQTO = (MemberExportQTO) context.getRequest().getParam("memberExportQTO");
//        String appKey = (String) context.getRequest().getParam("appKey");
//
//        if (memberExportQTO == null) {
//            log.error("memberExportQTO is null");
//            return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING);
//        }
//
//        if (appKey == null) {
//            log.error("appKey is null");
//            return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING);
//        }
//
//        ExportTaskDO exportTaskDO = new ExportTaskDO();
//        exportTaskDO.setSellerId(memberExportQTO.getSellerId());
//        exportTaskDO.setAppKey(appKey);
//        exportTaskDO.setTaskProcess(0);
//        exportTaskDO.setTaskType(TaskTypeEnum.MEMBER_EXPORT_TYPE.getType());
//        exportTaskDO.setOssBucketName(ossClientAPI.getBucketName());
//        String ossKey = "member_" + TimeUtil.formatTimeStamp(new Date()) + ".xls";
//        exportTaskDO.setOssObjectKey(ossKey);
//
//        long index;
//        try {
//            exportTaskDO.setDeleteMark(0);
//            exportTaskDO.setTaskStatus(TaskStatusEnum.RUNNING_TASK.getStatus());
//            index = exportTaskDAO.addExportTask(exportTaskDO);
//        } catch (DtsException e) {
//            log.error("add member export task failed, errMsg: {}", e.getMessage());
//            return ResponseUtil.getErrorResponse(e.getCode(), e.getMessage());
//        }
//
//        if (index <= 0) {
//            return ResponseUtil.getErrorResponse(ResponseCode.SYS_E_DB_INSERT);
//        }
//
//        exportTaskDO.setId(index);
//
//        try {
//            MemberExportTask memberExportTask = new MemberExportTask();
//            memberExportTask.setMemberExportManager(memberExportManager);
//            memberExportTask.setExportTaskDO(exportTaskDO);
//            memberExportTask.setMemberExportQTO(memberExportQTO);
//            exportServiceManager.submitMemberExportTask(memberExportTask);
//        } catch (Exception e) {
//            log.error("submit member export task exception:{}", e.getMessage());
//            return ResponseUtil.getErrorResponse(ResponseCode.B_E_SUBMIT_TASK_FAILED);
//        }
//        return ResponseUtil.getSuccessResponse(true);
//    }
//
//    @Override
//    public String getName() {
//        return ActionEnum.MEMBER_EXPORT_TASK.getActionName();
//    }
//}
