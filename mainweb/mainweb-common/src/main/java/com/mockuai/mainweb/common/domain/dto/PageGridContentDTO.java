package com.mockuai.mainweb.common.domain.dto;

import java.util.Date;
import java.util.List;

/**
 * 
 * @author Administrator
 *
 */
public class PageGridContentDTO extends ElementDTO {

	private Long gridId;
	private Integer leftTopX;
	private Integer leftTopY;
	private Integer bottomRightX;
	private Integer bottomRightY;
	private String imageUrl;
	private String targetUrl;
	private Integer deleteMark;
	
	private List<Integer> ids;
	
	
	public List<Integer> getIds() {
		return ids;
	}
	public void setIds(List<Integer> ids) {
		this.ids = ids;
	}
	public Long getGridId() {
		return gridId;
	}
	public void setGridId(Long gridId) {
		this.gridId = gridId;
	}
	public Integer getLeftTopX() {
		return leftTopX;
	}
	public void setLeftTopX(Integer leftTopX) {
		this.leftTopX = leftTopX;
	}
	public Integer getLeftTopY() {
		return leftTopY;
	}
	public void setLeftTopY(Integer leftTopY) {
		this.leftTopY = leftTopY;
	}
	public Integer getBottomRightX() {
		return bottomRightX;
	}
	public void setBottomRightX(Integer bottomRightX) {
		this.bottomRightX = bottomRightX;
	}
	public Integer getBottomRightY() {
		return bottomRightY;
	}
	public void setBottomRightY(Integer bottomRightY) {
		this.bottomRightY = bottomRightY;
	}	
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getTargetUrl() {
		return targetUrl;
	}
	public void setTargetUrl(String targetUrl) {
		this.targetUrl = targetUrl;
	}
	public Integer getDeleteMark() {
		return deleteMark;
	}
	public void setDeleteMark(Integer deleteMark) {
		this.deleteMark = deleteMark;
	}
	
}
