//package com.mockuai.dts.core.service;
//
//import com.mockuai.dts.common.domain.MemberExportQTO;
//import com.mockuai.dts.core.domain.ExportTaskDO;
//import com.mockuai.dts.core.exception.DtsException;
//import com.mockuai.dts.core.manager.MemberExportManager;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
///**
// * Created by duke on 15/12/7.
// */
//public class MemberExportTask implements Runnable {
//    private static final Logger log = LoggerFactory.getLogger(MemberExportTask.class);
//
//    private MemberExportManager memberExportManager;
//
//    private MemberExportQTO memberExportQTO;
//
//    private ExportTaskDO exportTaskDO;
//
//    @Override
//    public void run() {
//        try {
//            memberExportManager.exportMembers(memberExportQTO, exportTaskDO);
//        } catch (DtsException e) {
//            log.error("export member error: {}", e.getMessage());
//        }
//    }
//
//    public void setMemberExportManager(MemberExportManager memberExportManager) {
//        this.memberExportManager = memberExportManager;
//    }
//
//    public void setMemberExportQTO(MemberExportQTO memberExportQTO) {
//        this.memberExportQTO = memberExportQTO;
//    }
//
//    public void setExportTaskDO(ExportTaskDO exportTaskDO) {
//        this.exportTaskDO = exportTaskDO;
//    }
//}
