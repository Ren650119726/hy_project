package com.mockuai.itemcenter.mop.api.domain;

import java.util.List;

/**
 * Created by zengzhangqiang on 4/29/15.
 */
public class MopItemCommentDTO {
    private String commentUid;
    private long userId;
    private String userName;
    private String orderUid;
    private String itemUid;
    private String skuUid;
    private String skuCode;
    private long sellerId;
    private String title;
    private String content;
    private int score;
    private String commentTime;
    private String replyContent;
    private long replyUserId;
    private String replyTime;
    private List<MopCommentImageDTO> commentImageList;

    public String getCommentUid() {
        return commentUid;
    }

    public void setCommentUid(String commentUid) {
        this.commentUid = commentUid;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getOrderUid() {
        return orderUid;
    }

    public void setOrderUid(String orderUid) {
        this.orderUid = orderUid;
    }

    public String getSkuUid() {
        return skuUid;
    }

    public void setSkuUid(String skuUid) {
        this.skuUid = skuUid;
    }

    public String getItemUid() {
        return itemUid;
    }

    public void setItemUid(String itemUid) {
        this.itemUid = itemUid;
    }

    public long getSellerId() {
        return sellerId;
    }

    public void setSellerId(long sellerId) {
        this.sellerId = sellerId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(String commentTime) {
        this.commentTime = commentTime;
    }

    public String getReplyContent() {
        return replyContent;
    }

    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }

    public long getReplyUserId() {
        return replyUserId;
    }

    public void setReplyUserId(long replyUserId) {
        this.replyUserId = replyUserId;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public String getReplyTime() {
        return replyTime;
    }

    public void setReplyTime(String replyTime) {
        this.replyTime = replyTime;
    }

    public List<MopCommentImageDTO> getCommentImageList() {
        return commentImageList;
    }

    public void setCommentImageList(List<MopCommentImageDTO> commentImageList) {
        this.commentImageList = commentImageList;
    }
}
