package com.mockuai.mainweb.common.domain.dto;

import java.util.Date;

/**
 * Created by Administrator on 2016/10/10.
 */
public class PageSeckillDTO extends BaseDTO{
    private Long id ;

    private Long seckillId ;

    private Long subSeckillId ;

    private String seckillTitle;

    private Date startTime;

    private Date endTime;

    private Long pageId ;

    private Long blockId ;

    private String imageUrl ;

    private String targetUrl ;

    private Date gmtCreated;

    private Date gmtModified;

    private Integer deleteMark;

    public Long getBlockId() {
        return blockId;
    }

    public void setBlockId(Long blockId) {
        this.blockId = blockId;
    }

    public Integer getDeleteMark() {
        return deleteMark;
    }

    public void setDeleteMark(Integer deleteMark) {
        this.deleteMark = deleteMark;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Long getPageId() {
        return pageId;
    }

    public void setPageId(Long pageId) {
        this.pageId = pageId;
    }

    public Long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(Long seckillId) {
        this.seckillId = seckillId;
    }

    public Long getSubSeckillId() {
        return subSeckillId;
    }

    public void setSubSeckillId(Long subSeckillId) {
        this.subSeckillId = subSeckillId;
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getSeckillTitle() {
        return seckillTitle;
    }

    public void setSeckillTitle(String seckillTitle) {
        this.seckillTitle = seckillTitle;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
}
