//package com.mockuai.dts.core.service.action.impl;
//
//import com.mockuai.dts.common.TaskStatusEnum;
//import com.mockuai.dts.common.api.action.DtsResponse;
//import com.mockuai.dts.common.constant.ActionEnum;
//import com.mockuai.dts.common.constant.ResponseCode;
//import com.mockuai.dts.common.constant.TaskTypeEnum;
//import com.mockuai.dts.common.domain.MemberDataMigrateDTO;
//import com.mockuai.dts.core.dao.ExportTaskDAO;
//import com.mockuai.dts.core.domain.ExportTaskDO;
//import com.mockuai.dts.core.exception.DtsException;
//import com.mockuai.dts.core.manager.MemberDataMigrateManager;
//import com.mockuai.dts.core.service.ExportServiceManager;
//import com.mockuai.dts.core.service.MemberDataMigrateTask;
//import com.mockuai.dts.core.service.RequestContext;
//import com.mockuai.dts.core.service.action.Action;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.Resource;
//
//
///**
// * Created by duke on 15/12/30.
// */
//@Service
//public class MemberDataMigrateAction implements Action {
//    private static final Logger log = LoggerFactory.getLogger(MemberDataMigrateAction.class);
//
//    @Resource
//    private MemberDataMigrateManager memberDataMigrateManager;
//
//    @Resource
//    private ExportServiceManager exportServiceManager;
//
//    @Resource
//    private ExportTaskDAO exportTaskDAO;
//
//    @Override
//    public DtsResponse execute(RequestContext context) {
//        MemberDataMigrateDTO memberDataMigrateDTO =
//                (MemberDataMigrateDTO) context.getRequest().getParam("memberDataMigrateDTO");
//        String appKey = (String) context.getRequest().getParam("appKey");
//
//        if (memberDataMigrateDTO == null) {
//            log.error("memberDataMigrateDTO is null");
//            return new DtsResponse(ResponseCode.PARAM_E_INVALID, "memberDataMigrateDTO is null");
//        }
//
//        ExportTaskDO exportTaskDO = new ExportTaskDO();
//        exportTaskDO.setSellerId(0L);
//        exportTaskDO.setTaskProcess(0);
//        exportTaskDO.setTaskType(TaskTypeEnum.LABEL_EXPORT_TYPE.getType());
//        exportTaskDO.setOssBucketName("");
//        exportTaskDO.setOssObjectKey("");
//        exportTaskDO.setAppKey(appKey);
//
//        long index;
//        try {
//            exportTaskDO.setDeleteMark(0);
//            exportTaskDO.setTaskStatus(TaskStatusEnum.RUNNING_TASK.getStatus());
//            index = exportTaskDAO.addExportTask(exportTaskDO);
//        } catch (DtsException e) {
//            log.error("db error", e);
//            return new DtsResponse(ResponseCode.SYS_E_SERVICE_EXCEPTION);
//        }
//
//        if (index <= 0) {
//            return new DtsResponse(ResponseCode.SYS_E_SERVICE_EXCEPTION);
//        }
//
//        exportTaskDO.setId(index);
//
//        try {
//            MemberDataMigrateTask memberDataMigrateTask = new MemberDataMigrateTask();
//            memberDataMigrateTask.setMemberDataMigrateManager(memberDataMigrateManager);
//            memberDataMigrateTask.setExportTaskDO(exportTaskDO);
//            memberDataMigrateTask.setMemberDateMigrateDTO(memberDataMigrateDTO);
//            exportServiceManager.submitMemberDataMigrateTask(memberDataMigrateTask);
//        } catch (Exception e) {
//            log.error("submit task exception:", e);
//            return new DtsResponse(ResponseCode.SYS_E_SERVICE_EXCEPTION);
//        }
//        DtsResponse response = new DtsResponse(ResponseCode.SUCCESS);
//        response.setModule(true);
//        return response;
//    }
//
//    @Override
//    public String getName() {
//        return ActionEnum.MEMBER_DATA_MIGRATE_TASK.getActionName();
//    }
//}
