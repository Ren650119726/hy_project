package com.mockuai.tradecenter.common.domain;

import java.io.Serializable;

public class CommentImageDTO
  implements Serializable
{
  /**
	 * 
	 */
	private static final long serialVersionUID = 5762391170415535005L;
	private Long id;
  private String bizCode;
  private Long itemCommentId;
  private Long sellerId;
  private Long userId;
  private String imageUrl;

  public Long getId()
  {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getBizCode() {
    return this.bizCode;
  }

  public void setBizCode(String bizCode) {
    this.bizCode = bizCode;
  }

  public Long getItemCommentId() {
    return this.itemCommentId;
  }

  public void setItemCommentId(Long itemCommentId) {
    this.itemCommentId = itemCommentId;
  }

  public Long getSellerId() {
    return this.sellerId;
  }

  public void setSellerId(Long sellerId) {
    this.sellerId = sellerId;
  }

  public Long getUserId() {
    return this.userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public String getImageUrl() {
    return this.imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }
}