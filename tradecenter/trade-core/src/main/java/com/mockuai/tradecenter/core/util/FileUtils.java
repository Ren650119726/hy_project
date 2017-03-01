package com.mockuai.tradecenter.core.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import org.apache.commons.lang3.StringUtils;

/**
 * 文件上传工具类
 * 
 * @author liuchao
 * @version $Id: FileUtil.java,v 0.1 2012-8-9 下午1:39:50 liuchao Exp $
 */
public class FileUtils {

	/**
	 * 文件重命名
	 * 
	 * @param fileName
	 *            绝对路径原始文件名
	 * @param newfileName
	 *            绝对路径新文件名
	 * @return
	 */
	public static boolean renameFile(String fileName, String newfileName) {
		try {
			File file = new File(fileName);
			if (!file.exists()) {
				return false;
			}
			File newFile = new File(newfileName);
			if (newFile.getName().equals(file.getName())) {
				return true;
			}
			if (file.renameTo(newFile)) {
				file.delete();
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 文件删除
	 * 
	 * @param fileName
	 *            绝对根路径文件名
	 * @return
	 */
	public static boolean delFile(String fileName) {
		try {
			File file = new File(fileName);
			if (!file.exists()) {
				return false;
			}
			if (!file.isFile()) {
				return false;
			}
			file.delete();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 文件写入
	 * 
	 * @param input
	 * @param filePath
	 * @return
	 */
	public static boolean writeFile(InputStream input, String filePath) {
		try {
			if (null == input) {
				return false;
			}
			File file = new File(filePath);
			if (file.exists()) {
				return true;
			}
			FileOutputStream output = new FileOutputStream(filePath);
			ByteArrayOutputStream data = new ByteArrayOutputStream();
			int num = 0;
			byte[] buffer = new byte[1024];
			while ((num = input.read(buffer)) > 0) {
				data.write(buffer, 0, num);
				output.write(buffer);
			}
			output.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	
	
	/**
	 * 获取文件基本名称不包括后缀
	 * 
	 * @param file
	 * @return
	 */
	public static String getFileNameBase(String file) {
		if (file == null) {
			return "";
		}
		int point = file.lastIndexOf(".");
		if (point < 0) {
			return "";
		}
		return (file.substring(0, point));
	}

	/**
	 * 获取文件后缀
	 * 
	 * @param file
	 * @return
	 */
	public static String getFileSuffix(String file) {
		if (file == null) {
			return "";
		}
		int point = file.lastIndexOf(".");
		if (point < 0) {
			return "";
		}
		return (file.substring(point + 1, file.length()));
	}

	/**
	 * 生成路径
	 * 
	 * @param path
	 */
	public static void mkdir(String path) {
		File dirPath = new File(path);
		if (!dirPath.exists()) {
			dirPath.mkdirs();
		}
	}

	        
	public static void main(String args[]) {
		// String filePath = "E:\\Z\\java\\test2.txt";
		// String newfilePath = "E:\\Z\\java\\test3.txt";
		// FileUtils.renameFile(filePath, newfilePath);
		try {
//			File file = new File("/Users/hzmk/Downloads/unipay3/wechat/wap/wechatpay-mockuai_demo-wap.zip");
//			upZipFile(file,
//					"/Users/hzmk/Downloads/unipay3/wechat/wap");
			
//			unzip("/Users/hzmk/Downloads/unipay3/wechat/wap/wechatpay-mockuai_demo-wap.zip",
//					"/Users/hzmk/Downloads/unipay3/wechat/wap",
//					true);
			
//			unzipFile("/Users/hzmk/Downloads/unipay3/wechat/wap/wechatpay-mockuai_demo-wap.zip",
//					"/Users/hzmk/Downloads/unipay3/wechat/wap");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
