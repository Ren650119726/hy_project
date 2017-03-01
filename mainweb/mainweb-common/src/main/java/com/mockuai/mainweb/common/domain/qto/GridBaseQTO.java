package com.mockuai.mainweb.common.domain.qto;

public class GridBaseQTO extends BaseQTO{

	private Long blockId; 
    private Long pageId;
    private Integer deleteMark;
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
	public Integer getDeleteMark() {
		return deleteMark;
	}
	public void setDeleteMark(Integer deleteMark) {
		this.deleteMark = deleteMark;
	}
    
    
	
}
