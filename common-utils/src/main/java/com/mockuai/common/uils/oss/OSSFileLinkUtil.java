package com.mockuai.common.uils.oss;

/**
 * Created by luliang on 15/7/31.
 */
public class OSSFileLinkUtil {

    /**
     * 产生链接;
     *  http:// <你的bucket名字>.oss.aliyuncs.com/<你的object名字>
     * 改成 .haiyn.com 这个地址走CDN
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
