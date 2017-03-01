package com.mockuai.itemcenter.core.domain;

import java.util.Date;

/**
 * Created by zengzhangqiang on 7/17/15.
 */
public class CommentImageDO {
    private Long id;
    /**
     * 接入企业标志
     */
    private String bizCode;
    /**
     * 商品评论id
     */
    private Long itemCommentId;
    /**
     * 所评论的商品所属卖家ID
     */
    private Long sellerId;
    /**
     * 评论用户id
     */
    private Long userId;
    /**
     * 评论图片url
     */
    private String imageUrl;
    /**
     * 逻辑删除标志
     */
    private Integer deleteMark;
    private Date gmtCreated;
    private Date gmtModified;

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

    public Long getItemCommentId() {
        return itemCommentId;
    }

    public void setItemCommentId(Long itemCommentId) {
        this.itemCommentId = itemCommentId;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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
}
