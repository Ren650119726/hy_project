package com.mockuai.dts.core.manager.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mockuai.dts.common.TaskStatusEnum;
import com.mockuai.dts.common.constant.Constants;
import com.mockuai.dts.common.constant.ResponseCode;
import com.mockuai.dts.common.domain.TranslogExportQTO;
import com.mockuai.dts.core.api.impl.OSSClientAPI;
import com.mockuai.dts.core.dao.ExportTaskDAO;
import com.mockuai.dts.core.domain.ExportTaskDO;
import com.mockuai.dts.core.exception.DtsException;
import com.mockuai.dts.core.manager.TranslogExportManager;
import com.mockuai.dts.core.util.FileUtil;
import com.mockuai.dts.core.util.OSSFileLinkUtil;
import com.mockuai.dts.core.util.PoiExcelUtil;
import com.mockuai.tradecenter.client.TradeSettlementClient;
import com.mockuai.tradecenter.common.api.Response;
import com.mockuai.tradecenter.common.domain.settlement.SellerTransLogDTO;
import com.mockuai.tradecenter.common.domain.settlement.SellerTransLogQTO;
import com.mockuai.tradecenter.common.enums.EnumInOutMoneyType;
import com.mockuai.tradecenter.common.enums.EnumPaymentMethod;
import com.mockuai.tradecenter.common.util.DateUtil;
import com.mockuai.tradecenter.common.util.MoneyUtil;
@Service
public class TranslogExportManagerImpl implements TranslogExportManager {
	private static final Logger log = LoggerFactory.getLogger(OrderExportManagerImpl.class);

	@Autowired
	private TradeSettlementClient tradeSettlementClient;

	@Resource
	private ExportTaskDAO exportTaskDAO;

	@Resource
	private OSSClientAPI ossClientAPI;

	@SuppressWarnings("rawtypes")
	private Response<List<SellerTransLogDTO>> queryTransLogDTOs(SellerTransLogQTO query, String appKey)
			throws DtsException {
		Response<List<SellerTransLogDTO>> response = tradeSettlementClient.queryTransLog(query, appKey);
		if (response.getCode() != Constants.SERVICE_PROCESS_SUCCESS) {
			int errorCode = Integer.valueOf(response.getCode());
			log.error("TranslogExportManagerImpl.queryTransLogDTOs error",
					response.getCode() + "," + response.getMessage());
			throw new DtsException(errorCode, response.getMessage());
		}
		return response;
	}

	@Override
	public boolean export(TranslogExportQTO query, String appkey, ExportTaskDO exportTaskDO) throws DtsException {
		// 文件名;
		String tmpFileName = exportTaskDO.getOssObjectKey();
		log.info("tmpFileName：" + FileUtil.getTmpFilePath(tmpFileName));
		File file = new File(FileUtil.getTmpFilePath(tmpFileName));
		int offset = 0;
		int count = 500;

		LinkedHashMap<String, String> hederMap = new LinkedHashMap<String, String>();
		// 需要自动单元格宽度的列
		List<Integer> autoSizeColumns = new ArrayList<Integer>();
		hederMap.put("0", "时间");
		hederMap.put("1", "类型");
		hederMap.put("2", "收入");
		hederMap.put("3", "支出");
		hederMap.put("4", "余额");
		hederMap.put("5", "支付渠道");

		for (int i = 2; i < 7; i++) {
			autoSizeColumns.add(i);
		}

		// 变量设置
		int rowTitleIndex = 0; // 标题行索引
		int rowContextHeight = 14; // 正文行高

		// 声明一个工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 生成一个表格
		HSSFSheet sheet = PoiExcelUtil.createSheet(workbook, tmpFileName, 10);
		// 设置正文字体
		HSSFFont fontContext = PoiExcelUtil.createFont(workbook, "宋体", HSSFFont.BOLDWEIGHT_NORMAL, (short) 11);
		// 生成并设置正文样式(无边框)
		HSSFCellStyle styleContext = PoiExcelUtil.createCellStyle(workbook, HSSFFont.BOLDWEIGHT_NORMAL, fontContext);
		// 生成并设置正文样式(用于金额)
		HSSFCellStyle styleMoney = PoiExcelUtil.createMoneyCellStyle(workbook, HSSFFont.BOLDWEIGHT_NORMAL, fontContext);
		// 标题行
		HSSFRow titleRow = PoiExcelUtil.createRow(sheet, rowTitleIndex, rowContextHeight);

		// 写入文件头部
		for (Iterator headerIterator = hederMap.entrySet().iterator(); headerIterator.hasNext();) {
			Entry<String, String> propertyEntry = (Entry<String, String>) headerIterator.next();
			PoiExcelUtil.createCell(titleRow, Integer.parseInt(propertyEntry.getKey()), styleContext,
					propertyEntry.getValue());
		}

		int currentRow = 1; // excel要写入的当前行
		while (true) {
			SellerTransLogQTO sellerTransLogQuery = new SellerTransLogQTO();
			
				
			BeanUtils.copyProperties(query, sellerTransLogQuery);
			if(null!=query.getShopType()&&query.getShopType()==3)
				sellerTransLogQuery.setShopType(3);
			sellerTransLogQuery.setCount(count);
			sellerTransLogQuery.setOffset(offset);
			Response<List<SellerTransLogDTO>> response = queryTransLogDTOs(sellerTransLogQuery, appkey);
			for (SellerTransLogDTO translog : response.getModule()) {
				HSSFRow workingRow = PoiExcelUtil.createRow(sheet, currentRow, rowContextHeight);

				PoiExcelUtil.createCell(workingRow, 0, styleContext, DateUtil.getFormatDate(translog.getGmtCreated(), "yyyy-MM-dd  HH:mm:ss"));
				PoiExcelUtil.createCell(workingRow, 1, styleContext, EnumInOutMoneyType.toMap().get(translog.getType()));
				PoiExcelUtil.createCell(workingRow, 2, styleMoney, MoneyUtil.getMoneyStr(translog.getFundInAmount()));
				PoiExcelUtil.createCell(workingRow, 3, styleMoney, MoneyUtil.getMoneyStr(translog.getFundOutAmount()));
				PoiExcelUtil.createCell(workingRow, 4, styleMoney, MoneyUtil.getMoneyStr(translog.getLastAmount()));
				if(0==translog.getPaymentId()){
            		PoiExcelUtil.createCell(workingRow, 5, styleMoney,"优惠折扣");
				}else{
					PoiExcelUtil.createCell(workingRow, 5, styleMoney, EnumPaymentMethod.getByCode(translog.getPaymentId()+"").getDescription());
				}
				currentRow++;
			}

			long total = response.getTotalCount();
			// 更新进度;
			int process = 0;
			if (total == 0) {
				process = 0;
			} else {
				process = (int) (offset / total) * 100 / 2;
			}

			exportTaskDO.setTaskProcess(process);
			exportTaskDO.setTaskStatus(TaskStatusEnum.RUNNING_TASK.getStatus());
			exportTaskDAO.updateTask(exportTaskDO);

			offset += response.getModule().size();
			if (response.getModule().size() < count) {
				// 最后一页可以退出了;
				break;
			}
		}

		try {
			PoiExcelUtil.writeWorkbook(workbook, file);
		} catch (IOException e) {
			log.error("OrderExportManagerImpl exportOrders writeWorkbook error", e);
			FileUtil.destroyFile(tmpFileName);
			throw new DtsException(ResponseCode.SYS_E_SERVICE_EXCEPTION, e.getCause());
		}
		// 上传到OSS;
		ossClientAPI.uploadFile(tmpFileName, FileUtil.getTmpFilePath(tmpFileName));
		// 完成;
		exportTaskDO.setTaskProcess(100);
		exportTaskDO.setTaskStatus(TaskStatusEnum.COMPLETE_TASK.getStatus());
		exportTaskDO.setFileLink(
				OSSFileLinkUtil.generateFileLink(exportTaskDO.getOssBucketName(), exportTaskDO.getOssObjectKey()));
		exportTaskDAO.updateTask(exportTaskDO);
		// 上传完之后删除;
		FileUtil.destroyFile(tmpFileName);
		return true;

	}

}
