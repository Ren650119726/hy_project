package com.mockuai.mainweb.core.domain;

import java.util.Date;

/**
 * 
 * @author Administrator
 *
 */
public class PageGridMergeCellsDO extends GridBaseDO {

	private Long gridId;
	private Long gridContentId;
	private Integer cellNo;

	

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

	
	
	
}
