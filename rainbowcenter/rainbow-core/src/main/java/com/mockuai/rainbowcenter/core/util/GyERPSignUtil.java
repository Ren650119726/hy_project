package com.mockuai.rainbowcenter.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;

/**
 * Created by lizg on 2016/6/2.
 * 签名
 */
public class GyERPSignUtil {

    private static final Logger logger = LoggerFactory.getLogger(GyERPSignUtil.class);


    public static String sign(String json,String secret){
        StringBuilder enValue = new StringBuilder();
        //前后加上secret
        enValue.append(secret);
        enValue.append(json);
        enValue.append(secret);
        logger.info("append secret: " + enValue.toString());
        // 使用MD5加密(32位大写)
        byte[] bytes = encryptMD5(enValue.toString());
        return byte2hex(bytes);
    }

    private static byte[] encryptMD5(String data) {
        byte[] bytes = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            bytes = md.digest(data.getBytes("UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bytes;
    }

    private static String byte2hex(byte[] bytes) {
        StringBuilder sign = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() == 1) {
                sign.append("0");
            }
            sign.append(hex.toUpperCase());
        }
        return sign.toString();
    }
}
