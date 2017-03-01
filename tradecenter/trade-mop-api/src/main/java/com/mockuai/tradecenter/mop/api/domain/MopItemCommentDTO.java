package com.mockuai.tradecenter.mop.api.domain;

import java.util.List;


/**
 * Created by zengzhangqiang on 4/29/15.
 */
public class MopItemCommentDTO {
    private long userId;
    private String orderUid;
    private String itemUid;
    private String title;
    private String content;
    private int score;
    private String skuUid;
    private List<MopCommentImageDTO> commentImageList;
    

    public String getSkuUid() {
		return skuUid;
	}

	public void setSkuUid(String skuUid) {
		this.skuUid = skuUid;
	}

	public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getOrderUid() {
        return orderUid;
    }

    public void setOrderUid(String orderUid) {
        this.orderUid = orderUid;
    }

    public String getItemUid() {
        return itemUid;
    }

    public void setItemUid(String itemUid) {
        this.itemUid = itemUid;
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

	public List<MopCommentImageDTO> getCommentImageList() {
		return commentImageList;
	}

	public void setCommentImageList(List<MopCommentImageDTO> commentImageList) {
		this.commentImageList = commentImageList;
	}
    
    
}
