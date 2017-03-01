//package com.mockuai.dts.core.service;
//
//import com.mockuai.dts.common.domain.MemberDataMigrateDTO;
//import com.mockuai.dts.core.domain.ExportTaskDO;
//import com.mockuai.dts.core.exception.DtsException;
//import com.mockuai.dts.core.manager.MemberDataMigrateManager;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
///**
// * Created by duke on 15/12/30.
// */
//public class MemberDataMigrateTask implements Runnable {
//    private static final Logger log = LoggerFactory.getLogger(MemberDataMigrateTask.class);
//
//    private MemberDataMigrateDTO memberDateMigrateDTO;
//
//    private MemberDataMigrateManager memberDataMigrateManager;
//
//    private ExportTaskDO exportTaskDO;
//
//    @Override
//    public void run() {
//        try {
//            memberDataMigrateManager.dataMigrate(memberDateMigrateDTO, exportTaskDO);
//        } catch (DtsException e) {
//            log.error("member data migrate error: {}", e.getMessage());
//        }
//    }
//
//    public void setMemberDateMigrateDTO(MemberDataMigrateDTO memberDateMigrateDTO) {
//        this.memberDateMigrateDTO = memberDateMigrateDTO;
//    }
//
//    public void setMemberDataMigrateManager(MemberDataMigrateManager memberDataMigrateManager) {
//        this.memberDataMigrateManager = memberDataMigrateManager;
//    }
//
//    public void setExportTaskDO(ExportTaskDO exportTaskDO) {
//        this.exportTaskDO = exportTaskDO;
//    }
//}
