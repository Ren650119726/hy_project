//package com.mockuai.dts.core.manager.impl;
//
//import com.mockuai.distributioncenter.client.DistributionClient;
//import com.mockuai.distributioncenter.client.MiniShopClient;
//import com.mockuai.distributioncenter.common.api.Response;
//import com.mockuai.distributioncenter.common.constant.SettleStatus;
//import com.mockuai.distributioncenter.common.domain.dto.DistributorStatisticDTO;
//import com.mockuai.distributioncenter.common.domain.dto.ExportStatisticDTO;
//import com.mockuai.distributioncenter.common.domain.qto.DistributorStatisticQTO;
//import com.mockuai.dts.common.TaskStatusEnum;
//import com.mockuai.dts.common.constant.ResponseCode;
//import com.mockuai.dts.common.domain.DistributionStatisticsExportQTO;
//import com.mockuai.dts.core.api.impl.OSSClientAPI;
//import com.mockuai.dts.core.dao.ExportTaskDAO;
//import com.mockuai.dts.core.domain.ExportTaskDO;
//import com.mockuai.dts.core.exception.DtsException;
//import com.mockuai.dts.core.manager.DistributionStatisticsExportManager;
//import com.mockuai.dts.core.util.FileUtil;
//import com.mockuai.dts.core.util.OSSFileLinkUtil;
//import com.mockuai.dts.core.util.PoiExcelUtil;
//import com.mockuai.tradecenter.common.util.MoneyUtil;
//import org.apache.poi.hssf.usermodel.*;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import javax.annotation.Resource;
//import java.io.File;
//import java.io.IOException;
//import java.util.*;
//
///**
// * Created by duke on 15/12/26.
// */
//public class DistributionStatisticsExportManagerImpl implements DistributionStatisticsExportManager {
//    private static final Logger log = LoggerFactory.getLogger(DistributionStatisticsExportManagerImpl.class);
//
//    @Resource
//    private ExportTaskDAO exportTaskDAO;
//
//    @Resource
//    private MiniShopClient miniShopClient;
//
//    @Resource
//    private OSSClientAPI ossClientAPI;
//
//    @Override
//    public boolean exportDistributionStatistics(
//            DistributionStatisticsExportQTO exportDistributionStatisticsQTO,
//            ExportTaskDO exportTaskDO)
//            throws DtsException {
//        String tmpFileName = exportTaskDO.getOssObjectKey();
//        log.info("tmpFileName：" + FileUtil.getTmpFilePath(tmpFileName));
//        File file = new File(FileUtil.getTmpFilePath(tmpFileName));
//        long offset = 0L;
//        int count = 500;
//
//        LinkedHashMap<String, String> hederMap = new LinkedHashMap<String, String>();
//        //需要自动单元格宽度的列
//        List<Integer> autoSizeColumns = new ArrayList<Integer>();
//        hederMap.put("0", "分销人");
//        hederMap.put("1", "本月待结算");
//        hederMap.put("2", "本月已结算");
//        hederMap.put("3", "累计待结算");
//        hederMap.put("4", "累计已结算");
//        hederMap.put("5", "累计提现金额");
//        hederMap.put("6", "账户余额（不包含待结算部分）");
//
//        for (int i = 2; i < 9; i++) {
//            autoSizeColumns.add(i);
//        }
//
//        //变量设置
//        int rowTitleIndex = 0;                    //标题行索引
//        int rowContextHeight = 14;                //正文行高
//
//        // 声明一个工作薄
//        HSSFWorkbook workbook = new HSSFWorkbook();
//        // 生成一个表格
//        HSSFSheet sheet = PoiExcelUtil.createSheet(workbook, tmpFileName, 10);
//        //设置正文字体
//        HSSFFont fontContext = PoiExcelUtil.createFont(workbook, "宋体", HSSFFont.BOLDWEIGHT_NORMAL, (short) 11);
//        //生成并设置正文样式(无边框)
//        HSSFCellStyle styleContext = PoiExcelUtil.createCellStyle(workbook, HSSFFont.BOLDWEIGHT_NORMAL, fontContext);
//
//        HSSFCellStyle styleMoney = PoiExcelUtil.createMoneyCellStyle(workbook, HSSFFont.BOLDWEIGHT_NORMAL, fontContext);
//        //标题行
//        HSSFRow titleRow = PoiExcelUtil.createRow(sheet, rowTitleIndex, rowContextHeight);
//
//        //写入文件头部
//        for (Iterator headerIterator = hederMap.entrySet().iterator(); headerIterator.hasNext(); ) {
//            Map.Entry<String, String> propertyEntry = (Map.Entry<String, String>) headerIterator.next();
//            PoiExcelUtil.createCell(titleRow, Integer.parseInt(propertyEntry.getKey()), styleContext, propertyEntry.getValue());
//        }
//
//        int currentRow = 1; //excel要写入的当前行
//
//        while (true) {
//            try {
//                // 500条一页;
//                DistributorStatisticQTO distributorStatisticQTO = new DistributorStatisticQTO();
//                distributorStatisticQTO.setGmtTradeStart(exportDistributionStatisticsQTO.getGmtTradeStart());
//                distributorStatisticQTO.setGmtTradeEnd(exportDistributionStatisticsQTO.getGmtTradeEnd());
//                distributorStatisticQTO.setDistType(exportDistributionStatisticsQTO.getDistType());
//                distributorStatisticQTO.setOffset(offset);
//                distributorStatisticQTO.setCount(count);
//                Response<List<ExportStatisticDTO>> response = miniShopClient.getExportStatistic(distributorStatisticQTO, exportTaskDO.getAppKey());
//                if (!response.isSuccess()) {
//                    throw new DtsException(response.getCode(), response.getMessage());
//                }
//                List<ExportStatisticDTO> exportStatisticDTOs = response.getModule();
//
//                // 对统计信息按照分拥人进行聚合
//                for (ExportStatisticDTO exportStatisticDTO : exportStatisticDTOs) {
//                    HSSFRow workingRow = PoiExcelUtil.createRow(sheet, currentRow, rowContextHeight);
//
//                    PoiExcelUtil.createCell(workingRow, 0, styleContext, exportStatisticDTO.getDistributorName());
//                    PoiExcelUtil.createCell(workingRow, 1, styleMoney, MoneyUtil.getMoneyStr(exportStatisticDTO.getSettlingAmount()));
//                    PoiExcelUtil.createCell(workingRow, 2, styleMoney, MoneyUtil.getMoneyStr(exportStatisticDTO.getSettledAmount()));
//                    PoiExcelUtil.createCell(workingRow, 3, styleMoney, MoneyUtil.getMoneyStr(exportStatisticDTO.getTotalSettlingAmount()));
//                    PoiExcelUtil.createCell(workingRow, 4, styleMoney, MoneyUtil.getMoneyStr(exportStatisticDTO.getTotalSettledAmount()));
//                    PoiExcelUtil.createCell(workingRow, 5, styleMoney, MoneyUtil.getMoneyStr(exportStatisticDTO.getTotalWithdrawedAmount()));
//                    PoiExcelUtil.createCell(workingRow, 6, styleMoney, MoneyUtil.getMoneyStr(exportStatisticDTO.getWithdrawableAmount()));
//
//                    currentRow++;
//                }
//
//                long total = response.getTotalCount();
//                // 更新进度;
//                int process;
//                if (total == 0) {
//                    process = 0;
//                } else {
//                    process = (int) (offset / total) * 100;
//                }
//
//                exportTaskDO.setTaskProcess(process);
//                exportTaskDO.setTaskStatus(TaskStatusEnum.RUNNING_TASK.getStatus());
//                exportTaskDAO.updateTask(exportTaskDO);
//
//                offset += exportStatisticDTOs.size();
//                if (exportStatisticDTOs.size() < 500) {
//                    // 最后一页可以退出了;
//                    break;
//                }
//
//
//            } catch (Exception e) {
//                FileUtil.destroyFile(tmpFileName);
//                log.error("export statistics error", e);
//                throw new DtsException(ResponseCode.SYS_E_SERVICE_EXCEPTION, e.getMessage());
//            }
//            for (Integer i : autoSizeColumns) {
//                sheet.autoSizeColumn(i);
//                int width = sheet.getColumnWidth(i);
//                //宽度加长
//                sheet.setColumnWidth(i, width + 1000);
//            }
//        }
//
//        try {
//            PoiExcelUtil.writeWorkbook(workbook, file);
//        } catch (IOException e) {
//            log.error("export distribution statistics writeWorkbook error", e);
//            FileUtil.destroyFile(tmpFileName);
//            throw new DtsException(ResponseCode.SYS_E_SERVICE_EXCEPTION, e.getCause());
//        }
//        // 上传到OSS;
//        ossClientAPI.uploadFile(tmpFileName, FileUtil.getTmpFilePath(tmpFileName));
//        // 完成;
//        exportTaskDO.setTaskProcess(100);
//        exportTaskDO.setTaskStatus(TaskStatusEnum.COMPLETE_TASK.getStatus());
//        exportTaskDO.setFileLink(OSSFileLinkUtil.
//                generateFileLink(exportTaskDO.getOssBucketName(), exportTaskDO.getOssObjectKey()));
//        exportTaskDAO.updateTask(exportTaskDO);
//        // 上传完之后删除;
//        FileUtil.destroyFile(tmpFileName);
//        return true;
//    }
//}
