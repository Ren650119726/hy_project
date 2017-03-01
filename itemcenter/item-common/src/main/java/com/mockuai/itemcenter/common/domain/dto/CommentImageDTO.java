package com.mockuai.itemcenter.common.domain.dto;

import java.io.Serializable;

/**
 * Created by zengzhangqiang on 7/17/15.
 */
public class CommentImageDTO implements Serializable{
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
}
