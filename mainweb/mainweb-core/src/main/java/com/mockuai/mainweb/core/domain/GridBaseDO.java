package com.mockuai.mainweb.core.domain;

import java.util.Date;

public class GridBaseDO {
	private Long id;
	private Long blockId;
	private Long pageId;
	private Date gmtCreated;
	private Date gmtModified;
	private Integer deleteMark;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getBlockId() {
		return blockId;
	}
	public void setBlockId(Long blockId) {
		this.blockId = blockId;
	}
	public Long getPageId() {
		return pageId;
	}
	public void setPageId(Long pageId) {
		this.pageId = pageId;
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
