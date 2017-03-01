package com.mockuai.dts.core.util;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFCellUtil;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.Region;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 * excel导出帮助类
 */
public class PoiExcelUtil {
	protected final static Log log = LogFactory.getLog(ExcelUtil.class);
	
	public static void writeWorkbook(HSSFWorkbook wb,File excelFile) throws IOException{
		if (!excelFile.exists()) {
			excelFile.createNewFile();
			FileOutputStream os = new FileOutputStream(excelFile);
			wb.write(os);
		}
			
	}
	
	/**
	 * 将HSSFWorkbook写入Excel文件 
	 * @param wb
	 */
	public static void writeWorkbook(HSSFWorkbook wb, HttpServletResponse response){  
		OutputStream fos = null;  
        try {          	
        	fos = response.getOutputStream();  
            wb.write(fos);  
        } catch (FileNotFoundException e) {  
            log.error(new StringBuffer("导出报表出现异常[").append(e.getMessage()).append("]").append(e.getCause()));  
        } catch (IOException e) {
            log.error(new StringBuffer("导出报表出现异常[").append(e.getMessage()).append("]").append(e.getCause()));  
        } finally{  
            try {  
                if(fos!=null){  
                    fos.close();  
                }  
            } catch (IOException e) {  
                log.error(new StringBuffer("[").append(e.getMessage()).append("]").append(e.getCause()));  
            }  
        }  
    }  
	
	/**
	 * 创建HSSFSheet工作簿 
	 * @param wb
	 * @param sheetName
	 * @return
	 */
	public static HSSFSheet createSheet(HSSFWorkbook wb,String sheetName, int ColumnWidth){  
        HSSFSheet sheet = wb.createSheet(sheetName);  
        sheet.setDefaultColumnWidth(ColumnWidth);   
        sheet.setGridsPrinted(true);  
        sheet.setDisplayGridlines(true);  
        return sheet;  
    } 
	
	/**
     * 创建字体
     * @param wb
     * @param boldweight
     * @param size
     * @return
     */
    public static HSSFFont createFont(HSSFWorkbook wb, String fontName, short boldweight, short size){  
    	HSSFFont font = wb.createFont();  
        font.setFontName(fontName);
        font.setBoldweight(boldweight); 
        font.setFontHeightInPoints(size);  
        return font;  
    }
    
    /**
	 * 创建CellStyle样式
	 * @param wb
	 * @param halign
	 * @param font
	 * @return
	 */
	public static HSSFCellStyle createCellStyle(HSSFWorkbook wb, short halign,
            Font font) {
		HSSFCellStyle cs = wb.createCellStyle();
		cs.setFillForegroundColor(HSSFColor.WHITE.index);
        cs.setAlignment(halign);
        cs.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cs.setFont(font);
        return cs;
    }
	
	/**
	 * 创建金额的CellStyle样式（保留2位小数）
	 * @author tanhl
	 * @param wb
	 * @param halign
	 * @param font
	 * @return
	 */
	public static HSSFCellStyle createMoneyCellStyle(HSSFWorkbook wb, short halign,
            Font font) {
		HSSFCellStyle cs = createCellStyle(wb, halign, font);
		cs.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
        return cs;
    }
	
	/**
	 * 创建默认的CellStyle样式
	 * @author tanhl
	 * @param wb
	 * @return
	 */
	public static HSSFCellStyle createDefaultCellStyle(HSSFWorkbook wb) {
		return wb.createCellStyle();
    }

	/**
	 * 创建带边框的CellStyle样式
	 * @param wb
	 * @param halign
	 * @param font
	 * @return
	 */
    public static HSSFCellStyle createBorderCellStyle(HSSFWorkbook wb, short halign, short border,
            Font font) {
    	HSSFCellStyle cs = wb.createCellStyle();
    	cs.setFillForegroundColor(HSSFColor.WHITE.index);
        cs.setAlignment(halign);
        cs.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cs.setFillPattern(CellStyle.SOLID_FOREGROUND);        
        cs.setBorderLeft(border);
        cs.setBorderRight(border);
        cs.setBorderTop(border);
        cs.setBorderBottom(border);
        cs.setFont(font);
        return cs;
    }
    
    public static HSSFCellStyle createBorderMoneyCellStyle(HSSFWorkbook wb, short halign, short border,
            Font font) {
    	HSSFCellStyle cs = createBorderCellStyle(wb, halign, border, font);
    	cs.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
        return cs;
    }
	
	/**
	 * 创建HSSFRow
	 * @param sheet
	 * @param rowNum
	 * @param height
	 * @return
	 */
	public static HSSFRow createRow(HSSFSheet sheet, int rowNum, int height) {
        HSSFRow row = sheet.createRow(rowNum);
        row.setHeightInPoints((short) height);  //设置行高
        return row;
    }
    
    /**
     * 创建CELL
     * @param row
     * @param cellNum
     * @param style
     * @return
     */
    public static HSSFCell createCell(HSSFRow row, int cellNum, CellStyle style, Object value) {
        HSSFCell cell = row.createCell(cellNum);
        if(style != null) {
        	cell.setCellStyle(style);	
        }
        if(value != null) {
        	//支持cell的不同类型
            if("java.lang.String".equals(value.getClass().getName())) {
            	cell.setCellValue((String)value);
            } else if("java.lang.Double".equals(value.getClass().getName())) {
            	cell.setCellValue((Double)value);
            }
        }
        return cell;
    }
    
    /**
     * 合并单元格
     * @param sheet
     * @param firstRow
     * @param lastRow
     * @param firstColumn
     * @param lastColumn
     * @return
     */
	public static int mergeCell(HSSFSheet sheet, int firstRow, int lastRow,
            int firstColumn, int lastColumn) {
        return sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow,
                firstColumn, lastColumn));
    }


    /**
     * 合并单元格
     * @param sheet 要合并单元格的excel 的sheet
     * @param firstRow  要合并列的开始行
     * @param lastRow  要合并列的结束行
     * @param firstColumn   要合并列的开始列
     * @param lastColumn  要合并列的结束列
     * @return
     */
    public static int mergeRegCell(HSSFSheet sheet,int firstRow,
                                   int firstColumn,
                                   int lastRow,
                                   int lastColumn) {
        return sheet.addMergedRegion(new Region((short)firstRow, (short)firstColumn,
                (short)(lastRow), (short)lastColumn));
    }

    
    /**
     * 设置合并单元格的边框样式  
     * @param sheet
     * @param ca
     * @param style
     */
    public static void setRegionStyle(HSSFSheet sheet, CellRangeAddress ca,CellStyle style) {    
        for (int i = ca.getFirstRow(); i <= ca.getLastRow(); i++) {    

            HSSFRow row = HSSFCellUtil.getRow(i, sheet);    
            for (int j = ca.getFirstColumn(); j <= ca.getLastColumn(); j++) {    
                HSSFCell cell = HSSFCellUtil.getCell(row, j);    
                cell.setCellStyle(style);    
            }    
        }    
    } 
    
} 
