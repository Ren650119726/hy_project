package com.mockuai.rainbowcenter.common.qto;

public class QueryPage extends BaseQTO {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4933734686656210397L;
	private Long pageNum;
	private Integer pageSize;
	private Long totalCount;
	
	//updated by cwr
	private Long startRow;

	public Long getStartRow() {
		return startRow;
	}

	public void setStartRow(Long startRow) {
		this.startRow = startRow;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}

	public Long getPageNum() {
		return pageNum;
	}

	public void setPageNum(Long pageNum) {
		this.pageNum = pageNum;
	}

}

