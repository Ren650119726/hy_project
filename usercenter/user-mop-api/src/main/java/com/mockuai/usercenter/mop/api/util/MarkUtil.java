package com.mockuai.usercenter.mop.api.util;


import org.apache.commons.lang.StringUtils;

/**
 * Created by yeliming on 16/6/2.
 * 身份证掩码处理
 */
public class MarkUtil {
    public static String markFormat(String src, String mark, int startAt) {
        String src1 = src.substring(0, startAt);
        String src2 = StringUtils.repeat(mark, src.length() - startAt * 2);
        String src3 = src.substring(src.length() - startAt);
        StringBuilder sb = new StringBuilder();
        sb.append(src1).append(src2).append(src3);
        return sb.toString();
    }

    public static void main(String... args) {
        System.out.println(MarkUtil.markFormat("33252219890228915X", "*", 1));
    }
}