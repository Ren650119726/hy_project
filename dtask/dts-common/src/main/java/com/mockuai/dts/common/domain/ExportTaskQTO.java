package com.mockuai.dts.common.domain;

import com.mockuai.itemcenter.common.page.PageInfo;

import java.io.Serializable;

/**
 * Created by luliang on 15/7/24.
 */
public class ExportTaskQTO extends PageInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String bizCode;

    private Long sellerId;
    private Integer taskType;
    private Integer taskProcess;

    private Integer deleteMark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBizCode() {
        return bizCode;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public Integer getTaskType() {
        return taskType;
    }

    public void setTaskType(Integer taskType) {
        this.taskType = taskType;
    }

    public Integer getTaskProcess() {
        return taskProcess;
    }

    public void setTaskProcess(Integer taskProcess) {
        this.taskProcess = taskProcess;
    }

    public Integer getDeleteMark() {
        return deleteMark;
    }

    public void setDeleteMark(Integer deleteMark) {
        this.deleteMark = deleteMark;
    }
}
