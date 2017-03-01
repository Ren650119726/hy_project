package com.mockuai.tradecenter.common.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 商品评论DTO
 */
public class ItemCommentDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4649789727937027645L;
	
	private Long id;

	private Long userId; // 用户ID

	private Long orderId;// 订单ID

	private Long itemId; // 商品ID

	private Long sellerId; //卖家ID

	private String title;// 评论标题

	private String content;// 评论内容

	private Integer score; // 用户打分
	
	private Date commentTime;
	
	private OrderDTO orderDTO;
	
	private OrderConsigneeDTO orderConsigneeDTO;
	
	private String commentTimes;

	private OrderItemDTO orderItemDTO;
	
	private Long replyUserId; // 回复者用户ID

	private String replyContent;// 回复内容
	
	private Long skuId;
	
	List<CommentImageDTO> commentImageDTOList;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getReplyUserId() {
		return replyUserId;
	}

	public void setReplyUserId(Long replyUserId) {
		this.replyUserId = replyUserId;
	}

	public String getReplyContent() {
		return replyContent;
	}

	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}

	public Date getCommentTime() {
		return commentTime;
	}

	public void setCommentTime(Date commentTime) {
		this.commentTime = commentTime;
	}

	public OrderDTO getOrderDTO() {
		return orderDTO;
	}

	public void setOrderDTO(OrderDTO orderDTO) {
		this.orderDTO = orderDTO;
	}

	public OrderConsigneeDTO getOrderConsigneeDTO() {
		return orderConsigneeDTO;
	}

	public void setOrderConsigneeDTO(OrderConsigneeDTO orderConsigneeDTO) {
		this.orderConsigneeDTO = orderConsigneeDTO;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
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

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public String getCommentTimes() {
		return commentTimes;
	}

	public void setCommentTimes(String commentTimes) {
		this.commentTimes = commentTimes;
	}

	public OrderItemDTO getOrderItemDTO() {
		return orderItemDTO;
	}

	public void setOrderItemDTO(OrderItemDTO orderItemDTO) {
		this.orderItemDTO = orderItemDTO;
	}

	public Long getSkuId() {
		return skuId;
	}

	public void setSkuId(Long skuId) {
		this.skuId = skuId;
	}

	public List<CommentImageDTO> getCommentImageDTOList() {
		return commentImageDTOList;
	}

	public void setCommentImageDTOList(List<CommentImageDTO> commentImageDTOList) {
		this.commentImageDTOList = commentImageDTOList;
	}

	
	
	
	
	
}