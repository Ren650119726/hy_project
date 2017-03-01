//package com.mockuai.dts.core.manager.impl;
//
//import com.mockuai.distributioncenter.client.DistributionClient;
//import com.mockuai.distributioncenter.common.api.Response;
//import com.mockuai.distributioncenter.common.domain.dto.WithdrawRecordDTO;
//import com.mockuai.distributioncenter.common.domain.qto.WithdrawRecordQTO;
//import com.mockuai.dts.common.TaskStatusEnum;
//import com.mockuai.dts.common.constant.ResponseCode;
//import com.mockuai.dts.common.domain.DistributionWithdrawRecordExportQTO;
//import com.mockuai.dts.core.api.impl.OSSClientAPI;
//import com.mockuai.dts.core.dao.ExportTaskDAO;
//import com.mockuai.dts.core.domain.ExportTaskDO;
//import com.mockuai.dts.core.exception.DtsException;
//import com.mockuai.dts.core.manager.DistributionWithdrawRecordExportManager;
//import com.mockuai.dts.core.util.FileUtil;
//import com.mockuai.dts.core.util.PoiExcelUtil;
//import org.apache.poi.hssf.usermodel.*;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import javax.annotation.Resource;
//import java.io.File;
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.util.*;
//
///**
// * Created by lotmac on 16/3/10.
// */
//public class DistributionWithdrawRecordExportManagerImpl implements DistributionWithdrawRecordExportManager {
//    private static final Logger log = LoggerFactory.getLogger(DistributionWithdrawRecordExportManagerImpl.class);
//
//    @Resource
//    private ExportTaskDAO exportTaskDAO;
//
//    @Resource
//    private DistributionClient distributionClient;
//
//    @Resource
//    private OSSClientAPI ossClientAPI;
//
//    @Override
//    public boolean exportDistributionWithdrawRecord(DistributionWithdrawRecordExportQTO distributionWithdrawRecordExportQTO, ExportTaskDO exportTaskDO) throws DtsException {
//        String tmpFileName = exportTaskDO.getOssObjectKey();
//        log.info("tmpFileName：" + FileUtil.getTmpFilePath(tmpFileName));
//        File file = new File(FileUtil.getTmpFilePath(tmpFileName));
//        long offset = 0L;
//        int count = 500;
//
//        LinkedHashMap<String, String> hederMap = new LinkedHashMap<String, String>();
//        //需要自动单元格宽度的列
//        List<Integer> autoSizeColumns = new ArrayList<Integer>();
//        hederMap.put("0", "申请单号");
//        hederMap.put("1", "分销商账号");
//        hederMap.put("2", "提现金额");
//        hederMap.put("3", "提现账号(支付宝)");
//        hederMap.put("4", "提现状态");
//        hederMap.put("5", "支付金额");
//        hederMap.put("6", "操作时间");
//        hederMap.put("7", "备注");
//
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
//
//        int currentRow = 1; //excel要写入的当前行
//
//        while (true) {
//            try {
//                // 500条一页;
//                WithdrawRecordQTO withdrawRecordQTO = new WithdrawRecordQTO();
//                withdrawRecordQTO.setBizCode(distributionWithdrawRecordExportQTO.getBizCode());
//                withdrawRecordQTO.setGmtWithdrawStart(distributionWithdrawRecordExportQTO.getStartTime());
//                withdrawRecordQTO.setGmtWithdrawEnd(distributionWithdrawRecordExportQTO.getEndTime());
//                Response<List<WithdrawRecordDTO>> response =
//                        distributionClient.queryWithdrawRecords(withdrawRecordQTO, exportTaskDO.getAppKey());
//                if (!response.isSuccess()) {
//                    throw new DtsException(response.getCode(), response.getMessage());
//                }
//
//                List<WithdrawRecordDTO> withdrawRecordDTOs = response.getModule();
//
//                DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//
//                for (WithdrawRecordDTO withdrawRecordDTO : withdrawRecordDTOs) {
//                    HSSFRow workingRow = PoiExcelUtil.createRow(sheet, currentRow, rowContextHeight);
//                    PoiExcelUtil.createCell(workingRow, 0, styleContext, withdrawRecordDTO.getWithdrawNo());
//                    PoiExcelUtil.createCell(workingRow, 1, styleContext, withdrawRecordDTO.getDistUsername());
//                    PoiExcelUtil.createCell(workingRow, 2, styleMoney, withdrawRecordDTO.getApplyAmount()/100);
//                    PoiExcelUtil.createCell(workingRow, 3, styleContext, withdrawRecordDTO.getWithdrawAccount());
//                    String status = "";
//                    if(withdrawRecordDTO.getWithdrawStatus() == 1){
//                        status = "待提现";
//                    }else if(withdrawRecordDTO.getWithdrawStatus() == 2){
//                        status = "已提现";
//                    }else if(withdrawRecordDTO.getWithdrawStatus() == 3){
//                        status = "拒绝提现";
//                    }
//                    PoiExcelUtil.createCell(workingRow, 4, styleContext, status);
//                    PoiExcelUtil.createCell(workingRow, 5, styleMoney, withdrawRecordDTO.getRealAmount()/100);
//                    PoiExcelUtil.createCell(workingRow, 6, styleContext, df.format(withdrawRecordDTO.getGmtCreated()));
//                    PoiExcelUtil.createCell(workingRow, 7, styleContext, withdrawRecordDTO.getRemark());
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
//                offset += withdrawRecordDTOs.size();
//                if (withdrawRecordDTOs.size() < 500) {
//                    // 最后一页可以退出了;
//                    break;
//                }
//            } catch (Exception e) {
//                FileUtil.destroyFile(tmpFileName);
//                log.error("export distribution withdraw record error", e);
//                throw new DtsException(ResponseCode.SYS_E_SERVICE_EXCEPTION, e.getMessage());
//            }
//
//            for (Integer i : autoSizeColumns) {
//                sheet.autoSizeColumn(i);
//                int width = sheet.getColumnWidth(i);
//                //宽度加长
//                sheet.setColumnWidth(i, width + 1000);
//            }
//        }
//
//
//
//
//        return false;
//    }
//}
