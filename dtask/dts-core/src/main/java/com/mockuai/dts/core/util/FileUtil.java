package com.mockuai.dts.core.util;


import java.io.*;
import java.util.List;

import com.mockuai.itemcenter.common.domain.dto.ItemSkuDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.tradecenter.common.domain.OrderDTO;

/**
 * Created by luliang on 15/7/1.
 */
public class FileUtil {

    private static final Logger log = LoggerFactory.getLogger(FileUtil.class);

    public static void writeItems(String fileName, List<ItemDTO> itemDTOs, List<ItemSkuDTO> itemSkuDTOs) throws IOException {
        // Linux系统下的临时目录创建;
        String filePath = "/tmp/" + fileName;
        File file = new File(filePath);
        try {
            ExcelUtil.wirteExcel(file, "商品导出列表", itemDTOs,itemSkuDTOs);
        } catch (Exception e) {
            System.currentTimeMillis();
        }
    }


    /**
     * 删除临时文件;
     *
     * @param fileName
     */
    public static void destroyFile(String fileName) {
        String filePath = "/tmp/" + fileName;
        File file = new File(filePath);
        if (file.exists()) {
            // 不存在就创建文件;
            file.delete();
        }
    }

    public static String getTmpFilePath(String fileName) {
        return "/tmp/" + fileName;
    }

    public static String writeTmpFile(byte[] fileData, String tmpFileName) {
        String tmpFilePath = getTmpFilePath(tmpFileName);
        try {
            FileOutputStream fos = new FileOutputStream(tmpFilePath);
            fos.write(fileData);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tmpFilePath;
    }

}
