package com.mockuai.mainweb.common.domain.dto;

import java.util.Date;

/**
 * 
 * @author Administrator
 *
 */
public class PageGridMergeCellsDTO extends ElementDTO {

	private Long gridId;
	private Long gridContentId;
	private Integer cellNo;
	private Integer deleteMark;
	

	public Long getGridId() {
		return gridId;
	}
	public void setGridId(Long gridId) {
		this.gridId = gridId;
	}
	public Long getGridContentId() {
		return gridContentId;
	}
	public void setGridContentId(Long gridContentId) {
		this.gridContentId = gridContentId;
	}
	public Integer getCellNo() {
		return cellNo;
	}
	public void setCellNo(Integer cellNo) {
		this.cellNo = cellNo;
	}
	public Integer getDeleteMark() {
		return deleteMark;
	}
	public void setDeleteMark(Integer deleteMark) {
		this.deleteMark = deleteMark;
	}
	
	
	
}
