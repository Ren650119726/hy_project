package com.mockuai.mainweb.core.domain;

import java.util.Date;

/**
 * Created by Administrator on 2016/9/19.
 * 增加删除查询
 */
public class PublishPageDO {
    private  Long id;
    private Long PageId;
    private String content ;
    private Date gmtCreated;
    private Date gmtModified;
    private Integer deleteMark;


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
