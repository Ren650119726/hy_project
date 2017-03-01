package com.mockuai.dts.common.domain;

import java.io.Serializable;

/**
 * 任务
 * Created by luliang on 15/7/23.
 */
public class ExportTaskDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String bizCode;
    private Long sellerId;
    private Integer taskType;
    private Integer taskProcess;
    private Integer taskStatus;
    private String fileLink;
    private String createTime;
    private String ossBucketName;
    private String ossObjectKey;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBizCode() {
        return bizCode;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
    }

    public long getSellerId() {
        return sellerId;
    }

    public void setSellerId(long sellerId) {
        this.sellerId = sellerId;
    }

    public int getTaskType() {
        return taskType;
    }

    public void setTaskType(int taskType) {
        this.taskType = taskType;
    }

    public int getTaskProcess() {
        return taskProcess;
    }

    public void setTaskProcess(int taskProcess) {
        this.taskProcess = taskProcess;
    }

    public String getFileLink() {
        return fileLink;
    }

    public void setFileLink(String fileLink) {
        this.fileLink = fileLink;
    }

    public int getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(int taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getOssBucketName() {
        return ossBucketName;
    }

    public void setOssBucketName(String ossBucketName) {
        this.ossBucketName = ossBucketName;
    }

    public String getOssObjectKey() {
        return ossObjectKey;
    }

    public void setOssObjectKey(String ossObjectKey) {
        this.ossObjectKey = ossObjectKey;
    }
}
