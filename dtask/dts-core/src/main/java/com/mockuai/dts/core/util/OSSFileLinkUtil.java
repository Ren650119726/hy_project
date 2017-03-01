package com.mockuai.dts.core.util;

/**
 * Created by luliang on 15/7/31.
 */
public class OSSFileLinkUtil {

    /**
     * 产生链接;
     *  http:// <你的bucket名字>.oss.aliyuncs.com/<你的object名字>
     * @param ossBucketName
     * @param ossObjectKey
     * @return
     */
    public static String generateFileLink(String ossBucketName, String ossObjectKey) {
        StringBuilder sb = new StringBuilder();
        sb.append("http://").append(ossBucketName).append(".oss.aliyuncs.com/").append(ossObjectKey);
        return sb.toString();
    }
}
