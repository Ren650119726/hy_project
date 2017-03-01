package com.mockuai.suppliercenter.core.util;

import com.google.common.base.Strings;
import com.mockuai.suppliercenter.common.api.SupplierResponse;
import com.mockuai.suppliercenter.common.constant.ResponseCode;
import com.mockuai.suppliercenter.common.dto.StoreDTO;
import com.mockuai.suppliercenter.common.dto.StoreItemSkuDTO;
import com.mockuai.suppliercenter.common.dto.SupplierDTO;
import com.mockuai.suppliercenter.core.exception.SupplierException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 供应商工具类
 */
public class SupplierUtil {

    private static final String UNDERLINE = "_";


    /**
     * 检验供应商的注册信息是否合法
     */
    public static ResponseCode supplierInfoIsLegal(SupplierDTO supplierDTO)
            throws SupplierException {
        String name = supplierDTO.getName();
        String contacts = supplierDTO.getContacts();
        String phone = supplierDTO.getPhone();
        String address = supplierDTO.getAddress();


        if (name == null || name.trim() == null) {
            new SupplierResponse(ResponseCode.P_PARAM_NULL, "name is null");

        }
        if (name != null) {
            checkLenth(name, 200);
        }


        if (contacts == null || contacts.trim() == null) {
            new SupplierResponse(ResponseCode.P_PARAM_NULL, "contacts is null");

        }
        if (contacts != null) {
            checkLenth(contacts, 20);
        }

        if (phone == null || phone.trim() == null) {
            new SupplierResponse(ResponseCode.P_PARAM_NULL, "phone is null");

        }
        if (phone != null) {
            checkLenth(phone, 20);
        }

        if (address == null || address.trim() == null) {
            new SupplierResponse(ResponseCode.P_PARAM_NULL, "address is null");

        }
        if (address != null) {
            checkLenth(address, 200);
        }

        return null;
    }

    public static ResponseCode storeInfoIsLegal(StoreDTO storeDTO)
            throws SupplierException {
        String name = storeDTO.getName();
        String address = storeDTO.getAddress();
        Long supplierId = storeDTO.getSupplierId();

        if (name == null || name.trim() == null) {
            new SupplierResponse(ResponseCode.P_PARAM_NULL, "name is null");

        }
        if (name != null) {
            checkLenth(name, 50);
        }
        if (supplierId == null) {
            new SupplierResponse(ResponseCode.P_PARAM_NULL, "supplierId is null");
        }

        if (address == null || address.trim() == null) {
            new SupplierResponse(ResponseCode.P_PARAM_NULL, "address is null");

        }
        if (address != null) {
            checkLenth(address, 200);
        }
        if (name != null) {
            checkLenth(name, 50);
        }
        return null;

    }

    public static SupplierResponse checkSn(String sn) throws SupplierException {

        String regEx = "[\\u4e00-\\u9fa5]";

        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(sn);
        if (m.find()) {
            throw new SupplierException(ResponseCode.B_SUPPLIER_FORMAT_ERROR,
                    "sn format error");

        } else {
            int length = 0;
            char[] sns = sn.toCharArray();
            for (char c : sns) {
                length = length + 1;
            }
            if (length > 10) {
                throw new SupplierException(ResponseCode.P_PARAM_ERROR,
                        "sn length error");
            }
        }

        return null;
    }


    /**
     * 获取允许包含中文字符的字符串的长度 中文字符为两个长度，英文字符为一个长度
     */
    public static SupplierResponse checkName(String name) throws SupplierException {
        if (null == name) {
            throw new SupplierException(ResponseCode.P_PARAM_NULL, "name is null");
        }

        if (null == name.trim()) {
            throw new SupplierException(ResponseCode.P_PARAM_NULL, "name is null");
        }

        int length = 0;
        char[] names = name.toCharArray();
        for (char ch : names) {
            if (ch >= 19968 && ch <= 171941) {
                length = length + 2;
            } else {
                length = length + 1;
            }
        }
        // 用户名用中英文、数字、下划线，长度为4-16位(包含中文时为2-8位)
        if (length > 16 || length < 4) {
            throw new SupplierException(ResponseCode.P_PARAM_ERROR,
                    "username length error");
        }
        return null;
    }


    /**
     * 获取允许包含中文字符的字符串的长度 中文字符为两个长度，英文字符为一个长度
     */
    public static String checkLenth(String str, int i) throws SupplierException {
        if (null == str || null == str.trim()) {
            return "1";
        }


        int length = 0;
        char[] strs = str.toCharArray();
        for (char ch : strs) {
            if (ch >= 19968 && ch <= 171941) {
                length = length + 2;
            } else {
                length = length + 1;
            }
        }
        // 用户名用中英文、数字、下划线，长度为4-16位(包含中文时为2-8位)
        if (length > i)
            return "2";
        else
            return "";
    }


    /**
     * 生成uid
     *
     * @param userId
     * @param id
     * @return
     */
    public static String generateUid(Long userId, Long id) {
        return String.valueOf(userId) + UNDERLINE + String.valueOf(id);
    }


    public static void main(String[] args) {
        /*
         * String str = "454_fd63@sinadsf.com"; System.out.println(str
		 * .matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$"));
		 * String name = "叶正磊012"; char[] names = name.toCharArray();
		 * System.out.println(names[2]); char a = '叶'; System.out.println((int)
		 * a); System.out.println(getStringLength("叶正磊1fds"));
		 */

//		try {
//			ResponseCode responseCode = checkPwd("111111");
//			System.out.println("responseCode:"+responseCode);
//		} catch (SupplierException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
        int length = 0;

        String sn = "88---df";

        String regEx = "[\\u4e00-\\u9fa5]";
        // String str = "中文fd我是中国人as ";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(sn);
        if (m.find()) {
            System.out.println("有汉字");

        } else {
            char[] sns = sn.toCharArray();
            for (char c : sns) {
                length = length + 1;
            }
            if (length > 10) {
                System.out.println("长度大于10");
            }
        }


    }


}
