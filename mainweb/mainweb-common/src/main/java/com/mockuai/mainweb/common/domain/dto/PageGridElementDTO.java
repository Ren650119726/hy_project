package com.mockuai.mainweb.common.domain.dto;

import java.util.Date;
import java.util.List;

/**
 * 
 * @author Administrator
 *
 */
public class PageGridElementDTO extends ElementDTO {

    private Integer gridRow;
    private Integer gridColumn;
    private List<PageGridContentDTO> gridList;
	private Integer deleteMark;
        
	

	public List<PageGridContentDTO> getGridList() {
		return gridList;
	}
	public void setGridList(List<PageGridContentDTO> gridList) {
		this.gridList = gridList;
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
