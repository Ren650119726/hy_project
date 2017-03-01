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
import org.springframework.stereotype.Service;

import com.mockuai.dts.common.TaskStatusEnum;
import com.mockuai.dts.common.constant.Constants;
import com.mockuai.dts.common.constant.ResponseCode;
import com.mockuai.dts.common.domain.SellerUserExportQTO;
import com.mockuai.dts.core.api.impl.OSSClientAPI;
import com.mockuai.dts.core.dao.ExportTaskDAO;
import com.mockuai.dts.core.domain.ExportTaskDO;
import com.mockuai.dts.core.exception.DtsException;
import com.mockuai.dts.core.manager.SellerUserExportManager;
import com.mockuai.dts.core.util.FileUtil;
import com.mockuai.dts.core.util.OSSFileLinkUtil;
import com.mockuai.dts.core.util.PoiExcelUtil;
import com.mockuai.tradecenter.common.util.DateUtil;
import com.mockuai.tradecenter.common.util.MoneyUtil;
import com.mockuai.usercenter.client.SellerUserRelateClient;
import com.mockuai.usercenter.common.api.Response;
import com.mockuai.usercenter.common.dto.SellerUserRelateDTO;
import com.mockuai.usercenter.common.qto.SellerUserQTO;
@Service
public class SellerUserExportManagerImpl implements SellerUserExportManager{

	private static final Logger log = LoggerFactory.getLogger(SellerUserExportManagerImpl.class);
	
	@Resource
	private SellerUserRelateClient sellerUserRelateClient;
	
	@Resource
    private ExportTaskDAO exportTaskDAO;
	
	@Resource
    private OSSClientAPI ossClientAPI;


	public void setSellerUserRelateClient(SellerUserRelateClient sellerUserRelateClient) {
		this.sellerUserRelateClient = sellerUserRelateClient;
	}



	private Response getDatas(SellerUserExportQTO exportQuery, String appKey) throws DtsException {
		SellerUserQTO query = modelConvert(exportQuery);

		com.mockuai.usercenter.common.api.Response<List<SellerUserRelateDTO>> response =
				sellerUserRelateClient.querySellerUserRelate(query, appKey);
		if(response.getCode() != Constants.SERVICE_PROCESS_SUCCESS){
            int errorCode = Integer.valueOf(response.getCode());
            throw new DtsException(errorCode,response.getMessage());
        }
        return response;
	}
	
	private class SellerUserTemp {
		private Long userId;
		
		private Long sellerId;
		
		private String userName;
		
		private String mobile;
		
		private String relateStatus;
		
		private String keyword;
		
		

		public String getKeyword() {
			return keyword;
		}


		public String getRelateStatus() {
			return relateStatus;
		}

		public void setRelateStatus(String relateStatus) {
			this.relateStatus = relateStatus;
		}

		public String getUserName() {
			return userName;
		}

		public void setUserName(String userName) {
			this.userName = userName;
		}

		public String getMobile() {
			return mobile;
		}

		public void setMobile(String mobile) {
			this.mobile = mobile;
		}

		public Long getUserId() {
			return userId;
		}

		public void setUserId(Long userId) {
			this.userId = userId;
		}

		public Long getSellerId() {
			return sellerId;
		}

		public void setSellerId(Long sellerId) {
			this.sellerId = sellerId;
		}

		public void setKeyword(String keyword) {
			this.keyword = keyword;
		}
	}
	
	
	private SellerUserQTO modelConvert(SellerUserExportQTO exportQuery){
		SellerUserQTO query = new SellerUserQTO();
		SellerUserTemp temp = new SellerUserTemp();
		BeanUtils.copyProperties(exportQuery, temp);
		BeanUtils.copyProperties(temp, query);
		query.setOffset(new Integer(exportQuery.getOffset()).longValue());
		query.setPageSize(exportQuery.getPageSize());
		return query;
	}


	@Override
	public boolean exportSellerUserRelateData(SellerUserExportQTO query, ExportTaskDO exportTaskDO) throws DtsException {
		
		// 文件名;
        String tmpFileName = exportTaskDO.getOssObjectKey();
        log.info("tmpFileName："+FileUtil.getTmpFilePath(tmpFileName));
        File file = new File(FileUtil.getTmpFilePath(tmpFileName));
			int offset = 0;
	        
	        LinkedHashMap<String, String> hederMap = new LinkedHashMap<String, String>();
	         //需要自动单元格宽度的列
	 		 List<Integer> autoSizeColumns = new ArrayList<Integer>();
	         hederMap.put("0", "用户名");
	         hederMap.put("1", "手机号");
	         hederMap.put("2", "加入时间");
	         hederMap.put("3", "成交订单数");
	         hederMap.put("4", "成交额");
	         
	         for(int i=2; i<9; i++) {
	         	autoSizeColumns.add(i);
	         }
	         
	         	//变量设置
		    	int rowTitleIndex = 0;					//标题行索引
		    	int rowContextHeight = 14;				//正文行高
		    	
		    	// 声明一个工作薄
				HSSFWorkbook workbook = new HSSFWorkbook();
				// 生成一个表格
				HSSFSheet sheet = PoiExcelUtil.createSheet(workbook, tmpFileName, 10);
				//设置正文字体
				HSSFFont fontContext = PoiExcelUtil.createFont(workbook, "宋体", HSSFFont.BOLDWEIGHT_NORMAL, (short)11);		
				//生成并设置正文样式(无边框)
				HSSFCellStyle styleContext = PoiExcelUtil.createCellStyle(workbook, HSSFFont.BOLDWEIGHT_NORMAL, fontContext);
				//生成并设置正文样式(用于金额)
				HSSFCellStyle styleMoney = PoiExcelUtil.createMoneyCellStyle(workbook, HSSFFont.BOLDWEIGHT_NORMAL, fontContext);
				//标题行
				HSSFRow titleRow = PoiExcelUtil.createRow(sheet, rowTitleIndex, rowContextHeight);

	         //写入文件头部
	         for (Iterator headerIterator = hederMap.entrySet().iterator(); headerIterator.hasNext();) {
	             Entry<String,String> propertyEntry = (Entry<String, String>) headerIterator.next();
	             PoiExcelUtil.createCell(titleRow, Integer.parseInt(propertyEntry.getKey()), styleContext, propertyEntry.getValue());
	         }
	         
	        
            int currentRow = 1; //excel要写入的当前行
   			
			 while (true) {
		            try{
		  			  	//写入文件内容
		            	query.setPageSize(500);
		              
		            	query.setOffset(offset);
		            	
		            	Response response = getDatas(query, query.getAppKey());
		            	
		                List<SellerUserRelateDTO> dataList = (List<SellerUserRelateDTO>) response.getModule();
		                
		            	for(SellerUserRelateDTO o : dataList){
		               	    	int startRow = currentRow; //excel要写入的当前行
			               		HSSFRow workingRow = PoiExcelUtil.createRow(sheet, currentRow, rowContextHeight);
			                	PoiExcelUtil.createCell(workingRow, 0, styleContext,o.getUserName()+"");
			                	PoiExcelUtil.createCell(workingRow, 1, styleContext,o.getMobile()+"");
			                	PoiExcelUtil.createCell(workingRow, 2, styleMoney,DateUtil.getFormatDate(o.getRelateTimes(),"yyyy-MM-dd HH:mm:ss.S"));
			                	PoiExcelUtil.createCell(workingRow, 3, styleMoney, o.getFinishedOrderNum()+"");
			                	PoiExcelUtil.createCell(workingRow, 4, styleMoney, MoneyUtil.getMoneyStr(o.getFinishedOrderAmount()));
		               			currentRow++;
		            	}
		                
	            	   long total = response.getTotalCount();
		                // 更新进度;
		                int process = 0;
		                if(total == 0) {
		                    process = 0;
		                } else {
		                    process = (int)(offset / total) * 100 / 2;
		                }

		                exportTaskDO.setTaskProcess(process);
		                exportTaskDO.setTaskStatus(TaskStatusEnum.RUNNING_TASK.getStatus());
		                exportTaskDAO.updateTask(exportTaskDO);
			                
		                offset += dataList.size();
		                if(dataList.size() < 500) {
		                    // 最后一页可以退出了;
		                    break;
		                }

		            }catch(Exception e){
		                FileUtil.destroyFile(tmpFileName);
		                log.error("export oders error",e);
		                throw new DtsException(ResponseCode.SYS_E_SERVICE_EXCEPTION, e.getCause());
		            }
		            for(Integer i : autoSizeColumns) {
		       			sheet.autoSizeColumn(i);
		       			int width = sheet.getColumnWidth(i);
		       			//宽度加长
		       			sheet.setColumnWidth(i, width+1000);
		       		}
		            
		            
           
			 }
			
    		  try {
				PoiExcelUtil.writeWorkbook(workbook, file);
			} catch (IOException e) {
				 log.error("OrderExportManagerImpl exportOrders writeWorkbook error",e);
				 FileUtil.destroyFile(tmpFileName);
	             throw new DtsException(ResponseCode.SYS_E_SERVICE_EXCEPTION, e.getCause());
			}
			 // 上传到OSS;
		        ossClientAPI.uploadFile(tmpFileName, FileUtil.getTmpFilePath(tmpFileName));
		        // 完成;
		        exportTaskDO.setTaskProcess(100);
		        exportTaskDO.setTaskStatus(TaskStatusEnum.COMPLETE_TASK.getStatus());
		        exportTaskDO.setFileLink(OSSFileLinkUtil.
		                generateFileLink(exportTaskDO.getOssBucketName(), exportTaskDO.getOssObjectKey()));
		        exportTaskDAO.updateTask(exportTaskDO);
		        // 上传完之后删除;
		        FileUtil.destroyFile(tmpFileName);
		        return true;
	}
}
