package com.mockuai.mainweb.common.domain.dto;

import java.util.Date;

/**
 * Created by Administrator on 2016/9/20.
 */
public class PublishPageDTO extends  BaseDTO {
    private  Long id;
    private String name;
    private Long PageId;
    private String content ;
    private Date gmtCreated;
    private Date gmtModified;
    private Integer deleteMark;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPageId() {
        return PageId;
    }

    public void setPageId(Long pageId) {
        PageId = pageId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getGmtCreated() {
        return gmtCreated;
    }

    public void setGmtCreated(Date gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Integer getDeleteMark() {
        return deleteMark;
    }

    public void setDeleteMark(Integer deleteMark) {
        this.deleteMark = deleteMark;
    }
}
