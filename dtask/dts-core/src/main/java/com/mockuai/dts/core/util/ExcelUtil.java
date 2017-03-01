package com.mockuai.dts.core.util;

import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemSkuDTO;
import com.mockuai.tradecenter.common.domain.OrderDTO;

import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.*;
import jxl.write.biff.RowsExceededException;
import org.apache.commons.lang.StringUtils;

import java.io.*;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;

/**
 * 数据转换成excel文件的工具; Created by luliang on 15/7/3.
 */
public class ExcelUtil {

    private static LinkedHashMap<String, String> itemConfigMap;

    private static LinkedHashMap<String,String> itemSkuConfigMap;

    private static Field fields2[] = ItemDTO.class.getDeclaredFields();

    private static Field fields[] = ItemSkuDTO.class.getDeclaredFields();

    private static Field orderDTOFields[] = OrderDTO.class.getDeclaredFields();

    /**
     * 从配置文件加载;
     *
     * @return
     */
    public static LinkedHashMap<String, String> loadItemFieldFromConfig() throws IOException {
        LinkedHashMap<String, String> itemFieldMap = new LinkedHashMap<String, String>();
        InputStream inputStream = ExcelUtil.class.getClassLoader().getResourceAsStream("itemField.properties");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        String tempString = null;
        while ((tempString = bufferedReader.readLine()) != null) {
            // 分离;
            String[] entry = StringUtils.split(tempString, "=");
            itemFieldMap.put(entry[0], entry[1]);
        }
        return itemFieldMap;
    }

    private static LinkedHashMap<String,String> loadItemSkuFieldFromConfig()  throws IOException {
        LinkedHashMap<String, String> itemFieldMap = new LinkedHashMap<String, String>();
        InputStream inputStream = ExcelUtil.class.getClassLoader().getResourceAsStream("itemSkuField.properties");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        String tempString = null;
        while ((tempString = bufferedReader.readLine()) != null) {
            // 分离;
            String[] entry = StringUtils.split(tempString, "=");
            itemFieldMap.put(entry[0], entry[1]);
        }
        return itemFieldMap;
    }


    public static void wirteExcel(File excelFile, String sheetName, List<ItemDTO> itemDTOList, List<ItemSkuDTO> itemSkuDTOs)
            throws IOException, RowsExceededException, WriteException, IllegalAccessException, BiffException {
        if (!excelFile.exists()) {
            excelFile.createNewFile();
            // 获取参数
            itemConfigMap = loadItemFieldFromConfig();
            itemSkuConfigMap = loadItemSkuFieldFromConfig();
            FileOutputStream os = new FileOutputStream(excelFile);
            // 创建工作区
            WritableWorkbook workbook = Workbook.createWorkbook(os);
            // 创建新的一页，sheet只能在工作簿中使用
            WritableSheet sheet = workbook.createSheet(sheetName, 0);

            int i = 0;
            for (Map.Entry<String, String> entry : itemConfigMap.entrySet()) {
                WritableCell cell = new Label(i, 0, entry.getValue());
                sheet.addCell(cell);
                i++;
            }

            for (Map.Entry<String, String> entry : itemSkuConfigMap.entrySet()) {
                WritableCell cell = new Label(i, 0, entry.getValue());
                sheet.addCell(cell);
                i++;
            }

            writeExcelData(sheet, itemDTOList,itemSkuDTOs);

            workbook.write();
            workbook.close();
            os.close();
        } else {
            Workbook workbook = Workbook.getWorkbook(excelFile);
            WritableWorkbook book = Workbook.createWorkbook(excelFile, workbook);
            WritableSheet sheet = book.getSheet(sheetName);
            writeExcelData(sheet, itemDTOList, itemSkuDTOs);
            book.write();
            book.close();
        }
    }




    private static void writeExcelData(WritableSheet sheet, List<ItemDTO> itemDTOList, List<ItemSkuDTO> itemSkuDTOs)
            throws IllegalAccessException, WriteException {


        //将itemDTO对应到itemSkuDTO
        Map<Long,ItemDTO> itemMaps = new HashMap<Long, ItemDTO>();

        for(ItemDTO itemDTO : itemDTOList){
            itemMaps.put(itemDTO.getId(),itemDTO);
        }




        int lineNum = sheet.getRows();
        for (ItemSkuDTO itemSkuDTO : itemSkuDTOs) {



            int i = 0;
            Iterator it = itemSkuConfigMap.entrySet().iterator();
            Iterator it2 = itemConfigMap.entrySet().iterator();


            while (it2.hasNext()) {
                Map.Entry<String,String> entry2 = (Map.Entry<String, String>) it2.next();
                // 通过反射方式获取一个对象的属性

                ItemDTO itemDTO = itemMaps.get(itemSkuDTO.getItemId());

                // 通过反射方式获取一个对象的属性
                for (Field field : fields2) {
                    if (field.getName().equals(entry2.getKey())) {
                        field.setAccessible(true);
                        if (field.get(itemDTO) != null) {
                            String    data = field.get(itemDTO).toString();
                            WritableCell cell = new Label(i, lineNum, data);
                            sheet.addCell(cell);
                            break;
                        } else {
                            WritableCell cell = new Label(i, lineNum, StringUtils.EMPTY);
                            sheet.addCell(cell);
                            break;
                        }
                    }
                }

                i++;
            }

            while (it.hasNext()) {
                Map.Entry<String,String> entry = (Map.Entry<String, String>) it.next();
                // 通过反射方式获取一个对象的属性

                for (Field field : fields) {
                    if (field.getName().equals(entry.getKey())) {
                        field.setAccessible(true);
                        if (field.get(itemSkuDTO) != null) {

                            String data = "";
                            if (field.getName().contains("Price") && field.getType().equals(Long.class) && entry.getValue().contains("单位元")) {

                                Long penny = (Long) field.get(itemSkuDTO);
                                BigDecimal yuan = new BigDecimal(penny).divide(new BigDecimal(100));
                                data = yuan.toPlainString();

                            } else {
                                data = field.get(itemSkuDTO).toString();
                            }
                            WritableCell cell = new Label(i, lineNum, data);
                            sheet.addCell(cell);
                            break;
                        } else {
                            WritableCell cell = new Label(i, lineNum, StringUtils.EMPTY);
                            sheet.addCell(cell);
                            break;
                        }
                    }
                }
                i++;
            }
            lineNum++;
        }
    }


}
