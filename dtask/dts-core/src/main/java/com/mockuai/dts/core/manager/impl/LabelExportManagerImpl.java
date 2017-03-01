//package com.mockuai.dts.core.manager.impl;
//
//import com.mockuai.customer.client.CustomerClient;
//import com.mockuai.customer.common.api.CustomerResponse;
//import com.mockuai.customer.common.dto.LabelDTO;
//import com.mockuai.customer.common.qto.LabelQTO;
//import com.mockuai.dts.common.TaskStatusEnum;
//import com.mockuai.dts.common.constant.ResponseCode;
//import com.mockuai.dts.common.domain.LabelExportQTO;
//import com.mockuai.dts.core.api.impl.OSSClientAPI;
//import com.mockuai.dts.core.dao.ExportTaskDAO;
//import com.mockuai.dts.core.domain.ExportTaskDO;
//import com.mockuai.dts.core.exception.DtsException;
//import com.mockuai.dts.core.manager.LabelExportManager;
//import com.mockuai.dts.core.util.FileUtil;
//import com.mockuai.dts.core.util.OSSFileLinkUtil;
//import com.mockuai.dts.core.util.PoiExcelUtil;
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
//public class LabelExportManagerImpl implements LabelExportManager {
//    private static final Logger log = LoggerFactory.getLogger(LabelExportManagerImpl.class);
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
//    public boolean exportLabels(LabelExportQTO labelExportQTO, ExportTaskDO exportTaskDO) throws DtsException {
//        String tmpFileName = exportTaskDO.getOssObjectKey();
//        log.info("tmpFileName：" + FileUtil.getTmpFilePath(tmpFileName));
//        File file = new File(FileUtil.getTmpFilePath(tmpFileName));
//        long offset = 0L;
//        int count = 500;
//
//        LinkedHashMap<String, String> hederMap = new LinkedHashMap<String, String>();
//        //需要自动单元格宽度的列
//        List<Integer> autoSizeColumns = new ArrayList<Integer>();
//        hederMap.put("0", "标签名称");
//        hederMap.put("1", "标签说明");
//        hederMap.put("2", "用户数");
//        hederMap.put("3", "用户占比");
//        hederMap.put("4", "创建时间");
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
//                LabelQTO labelQTO = new LabelQTO();
//                labelQTO.setName(labelExportQTO.getName());
//                labelQTO.setCount(count);
//                labelQTO.setOffset(offset);
//                CustomerResponse<List<LabelDTO>> response = customerClient.queryLabel(labelQTO, exportTaskDO.getAppKey());
//                if (!response.isSuccess()) {
//                    throw new DtsException(response.getCode(), response.getMessage());
//                }
//                List<LabelDTO> labelDTOs = response.getModule();
//                DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//
//                for (LabelDTO labelDTO : labelDTOs) {
//                    HSSFRow workingRow = PoiExcelUtil.createRow(sheet, currentRow, rowContextHeight);
//
//                    PoiExcelUtil.createCell(workingRow, 0, styleContext, labelDTO.getName());
//                    PoiExcelUtil.createCell(workingRow, 1, styleContext, labelDTO.getDesc());
//                    PoiExcelUtil.createCell(workingRow, 2, styleContext, labelDTO.getCount() + "");
//                    PoiExcelUtil.createCell(workingRow, 3, styleContext, labelDTO.getRatio() * 100 + "%");
//                    PoiExcelUtil.createCell(workingRow, 4, styleContext, df.format(labelDTO.getGmtCreated()) + "");
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
//                offset += labelDTOs.size();
//                if (labelDTOs.size() < 500) {
//                    // 最后一页可以退出了;
//                    break;
//                }
//
//
//            } catch (Exception e) {
//                FileUtil.destroyFile(tmpFileName);
//                log.error("export label error", e);
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
//            log.error("exportLabels writeWorkbook error", e);
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
