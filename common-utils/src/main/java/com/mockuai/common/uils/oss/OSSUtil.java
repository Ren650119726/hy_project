package com.mockuai.common.uils.oss;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * OSS操作工具
 * Created by luliang on 15/7/1.
 */
public class OSSUtil {

    private static final Logger logger = LoggerFactory.getLogger(OSSUtil.class);

    /**
     * 上传Object,小文件;
     * @param ossClient
     * @param bucketName
     * @param key
     * @param filePath
     * @throws FileNotFoundException
     */
    public static void putObject(OSSClient ossClient, String bucketName, String key, String filePath) throws IOException {

        // 获取指定文件的输入流
        File file = new File(filePath);
        InputStream content = new FileInputStream(file);
        // 创建上传Object的Metadata
        ObjectMetadata meta = new ObjectMetadata();
        // 必须设置ContentLength
        meta.setContentLength(file.length());
        // 上传Object.
        PutObjectResult result = ossClient.putObject(bucketName, key, content, meta);
        // 打印ETag
        if(logger.isInfoEnabled()) {
            logger.info(result.getETag());
        }
        content.close();
    }

    /**
     * 大文件上传;
     * @param bucketName
     * @param key
     * @param filePath
     * @throws IOException
     */
    public static void multipartUpload(OSSClient ossClient, String bucketName, String key, String filePath) throws IOException {
        InitiateMultipartUploadRequest initiateMultipartUploadRequest = new InitiateMultipartUploadRequest(bucketName, key);
        InitiateMultipartUploadResult initiateMultipartUploadResult = ossClient.initiateMultipartUpload(initiateMultipartUploadRequest);
        // 打印UploadId
        if(logger.isInfoEnabled()) {
            logger.info("UploadId: " + initiateMultipartUploadResult.getUploadId());
        }

        // 设置每块为 5M
        final int partSize = 1024 * 1024 * 5;
        File partFile = new File(filePath);
        // 计算分块数目
        int partCount = (int) (partFile.length() / partSize);
        if (partFile.length() % partSize != 0){
            partCount++;
        }
        // 新建一个List保存每个分块上传后的ETag和PartNumber
        List<PartETag> partETags = new ArrayList<PartETag>();
        for(int i = 0; i < partCount; i++){
            // 获取文件流
            FileInputStream fis = new FileInputStream(partFile);
            // 跳到每个分块的开头
            long skipBytes = partSize * i;
            fis.skip(skipBytes);
            // 计算每个分块的大小
            long size = partSize < partFile.length() - skipBytes ?
                    partSize : partFile.length() - skipBytes;
            // 创建UploadPartRequest，上传分块
            UploadPartRequest uploadPartRequest = new UploadPartRequest();
            uploadPartRequest.setBucketName(bucketName);
            uploadPartRequest.setKey(key);
            uploadPartRequest.setUploadId(initiateMultipartUploadResult.getUploadId());
            uploadPartRequest.setInputStream(fis);
            uploadPartRequest.setPartSize(size);
            uploadPartRequest.setPartNumber(i + 1);
            UploadPartResult uploadPartResult = ossClient.uploadPart(uploadPartRequest);
            // 将返回的PartETag保存到List中。
            partETags.add(uploadPartResult.getPartETag());
            // 关闭文件
            fis.close();
        }
    }

    /**
     * 列出所有Object;
     * @param bucketName
     */
    public static ObjectListing listObjects(OSSClient ossClient, String bucketName) {

        // 获取指定bucket下的所有Object信息
        ObjectListing listing = ossClient.listObjects(bucketName);
        // 遍历所有Object
        for (OSSObjectSummary objectSummary : listing.getObjectSummaries()) {
            if(logger.isInfoEnabled()) {
                logger.info(objectSummary.getKey());
            }
        }
        return listing;
    }

    public static OSSObject getObject(OSSClient ossClient, String bucketName, String key) throws IOException {
        // 获取Object，返回结果为OSSObject对象
        OSSObject object = ossClient.getObject(bucketName, key);
//        // 获取Object的输入流
//        InputStream objectContent = object.getObjectContent();
//        // TODO 处理;
//
//        objectContent.close();
        return object;
    }

}
