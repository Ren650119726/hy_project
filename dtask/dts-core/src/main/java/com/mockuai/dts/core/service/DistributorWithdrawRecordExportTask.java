//package com.mockuai.dts.core.service;
//
//import com.mockuai.dts.common.domain.DistributionWithdrawRecordExportQTO;
//import com.mockuai.dts.core.domain.ExportTaskDO;
//import com.mockuai.dts.core.exception.DtsException;
//import com.mockuai.dts.core.manager.DistributionWithdrawRecordExportManager;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
///**
// * Created by lotmac on 16/3/10.
// */
//public class DistributorWithdrawRecordExportTask implements Runnable {
//    private static final Logger log = LoggerFactory.getLogger(DistributorWithdrawRecordExportTask.class);
//
//    private DistributionWithdrawRecordExportManager distributionWithdrawRecordExportManager;
//
//    private DistributionWithdrawRecordExportQTO distributionWithdrawRecordExportQTO;
//
//    private ExportTaskDO exportTaskDO;
//
//    @Override
//    public void run() {
//        try {
//            distributionWithdrawRecordExportManager.exportDistributionWithdrawRecord(distributionWithdrawRecordExportQTO, exportTaskDO);
//        } catch (DtsException e) {
//            log.error("export distributor withdraw record error, errMsg: {}", e.getMessage());
//        }
//    }
//
//    public void setDistributionWithdrawRecordExportManager(DistributionWithdrawRecordExportManager distributionWithdrawRecordExportManager) {
//        this.distributionWithdrawRecordExportManager = distributionWithdrawRecordExportManager;
//    }
//
//    public void setDistributionWithdrawRecordExportQTO(DistributionWithdrawRecordExportQTO distributionWithdrawRecordExportQTO) {
//        this.distributionWithdrawRecordExportQTO = distributionWithdrawRecordExportQTO;
//    }
//
//    public void setExportTaskDO(ExportTaskDO exportTaskDO) {
//        this.exportTaskDO = exportTaskDO;
//    }
//}
