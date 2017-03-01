package com.mockuai.mainweb.common.domain.dto;

import java.util.Date;

/**
 * 
 * @author Administrator
 *
 */
public class PageGridDTO extends BaseDTO {
    private Long id;
    private Long blockId; 
    private Long pageId;
    private Integer gridRow;
    private Integer gridColumn;
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
	public Integer getGridRow() {
		return gridRow;
	}
	public void setGridRow(Integer gridRow) {
		this.gridRow = gridRow;
	}
	public Integer getGridColumn() {
		return gridColumn;
	}
	public void setGridColumn(Integer gridColumn) {
		this.gridColumn = gridColumn;
	}
	public Integer getDeleteMark() {
		return deleteMark;
	}
	public void setDeleteMark(Integer deleteMark) {
		this.deleteMark = deleteMark;
	}

    
    
    
}
