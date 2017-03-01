//package com.mockuai.dts.core.manager.impl;
//
//import com.mockuai.customer.client.CustomerClient;
//import com.mockuai.customer.common.api.CustomerResponse;
//import com.mockuai.customer.common.dto.MemberDTO;
//import com.mockuai.customer.common.qto.MemberQTO;
//import com.mockuai.dts.common.TaskStatusEnum;
//import com.mockuai.dts.common.constant.ResponseCode;
//import com.mockuai.dts.common.domain.MemberExportQTO;
//import com.mockuai.dts.core.api.impl.OSSClientAPI;
//import com.mockuai.dts.core.dao.ExportTaskDAO;
//import com.mockuai.dts.core.domain.ExportTaskDO;
//import com.mockuai.dts.core.exception.DtsException;
//import com.mockuai.dts.core.manager.MemberExportManager;
//import com.mockuai.dts.core.util.FileUtil;
//import com.mockuai.dts.core.util.OSSFileLinkUtil;
//import com.mockuai.dts.core.util.PoiExcelUtil;
//import com.mockuai.tradecenter.common.util.MoneyUtil;
//import org.apache.poi.hssf.usermodel.*;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.BeanUtils;
//
//import javax.annotation.Resource;
//import java.io.File;
//import java.io.IOException;
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.util.*;
//
///**
// * Created by duke on 15/12/7.
// */
//public class MemberExportManagerImpl implements MemberExportManager {
//    private static final Logger log = LoggerFactory.getLogger(MemberExportManagerImpl.class);
//
//    @Resource
//    private ExportTaskDAO exportTaskDAO;
//
//    @Resource
//    private OSSClientAPI ossClientAPI;
//
//    @Resource
//    private CustomerClient customerClient;
//
//    @Override
//    public boolean exportMembers(MemberExportQTO memberExportQTO, ExportTaskDO exportTaskDO) throws DtsException {
//        String tmpFileName = exportTaskDO.getOssObjectKey();
//        log.info("tmpFileName：" + FileUtil.getTmpFilePath(tmpFileName));
//        File file = new File(FileUtil.getTmpFilePath(tmpFileName));
//        long offset = 0L;
//        int count = 500;
//
//        LinkedHashMap<String, String> hederMap = new LinkedHashMap<String, String>();
//        //需要自动单元格宽度的列
//        List<Integer> autoSizeColumns = new ArrayList<Integer>();
//        hederMap.put("0", "用户");
//        hederMap.put("1", "手机号");
//        hederMap.put("2", "会员等级");
//        hederMap.put("3", "累计消费");
//        hederMap.put("4", "累计订单");
//        hederMap.put("5", "累计经验值");
//        hederMap.put("6", "注册时间");
//        hederMap.put("7", "标签");
//
//        for (int i = 2; i < 8; i++) {
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
//        HSSFSheet sheet = PoiExcelUtil.createSheet(workbook, tmpFileName + "_" + 1, 10);
//        //设置正文字体
//        HSSFFont fontContext = PoiExcelUtil.createFont(workbook, "宋体", HSSFFont.BOLDWEIGHT_NORMAL, (short) 11);
//        //生成并设置正文样式(无边框)
//        HSSFCellStyle styleContext = PoiExcelUtil.createCellStyle(workbook, HSSFFont.BOLDWEIGHT_NORMAL, fontContext);
//        //生成并设置正文样式(用于金额)
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
//        int rowCount = 0;
//        int sheetPage = 1;
//
//        while (true) {
//            try {
//                // 500条一页;
//                MemberQTO memberQTO = new MemberQTO();
//                memberQTO.setSellerId(0L);  // 导出平台级别的会员
//                memberQTO.setLabelIdList(memberExportQTO.getLabelIdList());
//                memberQTO.setLevelId(memberExportQTO.getLevelId());
//                memberQTO.setUserName(memberExportQTO.getUserName());
//                memberQTO.setCount(count);
//                memberQTO.setOffset(offset);
//                CustomerResponse<List<MemberDTO>> response = customerClient.queryMember(memberQTO, exportTaskDO.getAppKey());
//                if (!response.isSuccess()) {
//                    throw new DtsException(response.getCode(), response.getMessage());
//                }
//                List<MemberDTO> memberDTOs = response.getModule();
//
//                DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                for (MemberDTO memberDTO : memberDTOs) {
//                    HSSFRow workingRow = PoiExcelUtil.createRow(sheet, currentRow, rowContextHeight);
//
//                    PoiExcelUtil.createCell(workingRow, 0, styleContext, memberDTO.getUserName());
//                    PoiExcelUtil.createCell(workingRow, 1, styleContext, memberDTO.getMobile());
//                    PoiExcelUtil.createCell(workingRow, 2, styleContext, memberDTO.getLevelName());
//                    PoiExcelUtil.createCell(workingRow, 3, styleMoney, MoneyUtil.getMoneyStr(memberDTO.getTotalAmount()));
//                    PoiExcelUtil.createCell(workingRow, 4, styleContext, memberDTO.getTotalOrder() + "");
//                    PoiExcelUtil.createCell(workingRow, 5, styleContext, memberDTO.getTotalExperience() + "");
//                    PoiExcelUtil.createCell(workingRow, 6, styleContext, df.format(memberDTO.getGmtCreated()) + "");
//                    PoiExcelUtil.createCell(workingRow, 7, styleContext, memberDTO.getLabelNames() + "");
//                    currentRow++;
//                    rowCount++;
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
//                offset += memberDTOs.size();
//                if (memberDTOs.size() < 500) {
//                    // 最后一页可以退出了;
//                    break;
//                }
//
//            } catch (Exception e) {
//                FileUtil.destroyFile(tmpFileName);
//                log.error("export member error", e);
//                throw new DtsException(ResponseCode.SYS_E_SERVICE_EXCEPTION, e.getMessage());
//            }
//            for (Integer i : autoSizeColumns) {
//                sheet.autoSizeColumn(i);
//            }
//
//            if (rowCount >= 50000) {
//                // 如果记录超过了50000，则新创建一个sheet
//                // 生成一个表格
//                sheetPage++;
//                sheet = PoiExcelUtil.createSheet(workbook, tmpFileName + "_" + sheetPage, 10);
//                //标题行
//                titleRow = PoiExcelUtil.createRow(sheet, rowTitleIndex, rowContextHeight);
//
//                //写入文件头部
//                for (Iterator headerIterator = hederMap.entrySet().iterator(); headerIterator.hasNext(); ) {
//                    Map.Entry<String, String> propertyEntry = (Map.Entry<String, String>) headerIterator.next();
//                    PoiExcelUtil.createCell(titleRow, Integer.parseInt(propertyEntry.getKey()), styleContext, propertyEntry.getValue());
//                }
//                rowCount = 0;
//                currentRow = 1;
//            }
//        }
//
//        try {
//            PoiExcelUtil.writeWorkbook(workbook, file);
//        } catch (IOException e) {
//            log.error("export members writeWorkbook error", e);
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
