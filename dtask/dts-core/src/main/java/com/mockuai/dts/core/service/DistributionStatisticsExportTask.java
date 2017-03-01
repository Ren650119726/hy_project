//package com.mockuai.dts.core.service;
//
//
//import com.mockuai.dts.common.domain.DistributionStatisticsExportQTO;
//import com.mockuai.dts.core.domain.ExportTaskDO;
//import com.mockuai.dts.core.exception.DtsException;
//import com.mockuai.dts.core.manager.DistributionStatisticsExportManager;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
///**
// * Created by duke on 15/12/26.
// */
//public class DistributionStatisticsExportTask implements Runnable {
//    private static final Logger log = LoggerFactory.getLogger(DistributionStatisticsExportTask.class);
//
//    private DistributionStatisticsExportManager distributionStatisticsExportManager;
//
//    private DistributionStatisticsExportQTO distributionStatisticsExportQTO;
//
//    private ExportTaskDO exportTaskDO;
//
//    @Override
//    public void run() {
//        try {
//            distributionStatisticsExportManager.exportDistributionStatistics(distributionStatisticsExportQTO, exportTaskDO);
//        } catch (DtsException e) {
//            log.error("export distribution statistics error: {}", e.getMessage());
//        }
//    }
//
//    public void setDistributionStatisticsExportManager(DistributionStatisticsExportManager distributionStatisticsExportManager) {
//        this.distributionStatisticsExportManager = distributionStatisticsExportManager;
//    }
//
//    public void setDistributionStatisticsExportQTO(DistributionStatisticsExportQTO distributionStatisticsExportQTO) {
//        this.distributionStatisticsExportQTO = distributionStatisticsExportQTO;
//    }
//
//    public void setExportTaskDO(ExportTaskDO exportTaskDO) {
//        this.exportTaskDO = exportTaskDO;
//    }
//}
