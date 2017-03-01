//package com.mockuai.dts.core.service;
//
//import com.mockuai.dts.common.domain.LabelExportQTO;
//import com.mockuai.dts.core.domain.ExportTaskDO;
//import com.mockuai.dts.core.exception.DtsException;
//import com.mockuai.dts.core.manager.LabelExportManager;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
///**
// * Created by duke on 15/12/7.
// */
//public class LabelExportTask implements Runnable {
//    private static final Logger log = LoggerFactory.getLogger(LabelExportTask.class);
//
//    private LabelExportManager labelExportManager;
//
//    private LabelExportQTO labelExportQTO;
//
//    private ExportTaskDO exportTaskDO;
//
//    @Override
//    public void run() {
//        try {
//            labelExportManager.exportLabels(labelExportQTO, exportTaskDO);
//        } catch (DtsException e) {
//            log.error("export label error: {}", e.getMessage());
//        }
//    }
//
//    public void setLabelExportManager(LabelExportManager labelExportManager) {
//        this.labelExportManager = labelExportManager;
//    }
//
//    public void setLabelExportQTO(LabelExportQTO labelExportQTO) {
//        this.labelExportQTO = labelExportQTO;
//    }
//
//    public void setExportTaskDO(ExportTaskDO exportTaskDO) {
//        this.exportTaskDO = exportTaskDO;
//    }
//}
