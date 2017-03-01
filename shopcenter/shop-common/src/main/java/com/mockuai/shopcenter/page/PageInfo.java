package com.mockuai.shopcenter.page;

import java.io.Serializable;

/**
 * 分页公共类
 * 
 * @author chen.huang
 * @date 2015年1月21日
 */
public class PageInfo implements Serializable{
	private int totalPage; // 总页数
	private int totalCount; // 总记录数
	private int currentPage; // 当前页
	private int pageSize; // 每页的数量
	private int offset;// 跳过的记录数
	private Boolean needPaging;// 是否需要分页

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public void setOffsetAndTotalPage() {
		totalPage = (int) Math.ceil((double) totalCount / pageSize);
		setTotalPage(totalPage);

		if(offset<=0) {
			if (currentPage <= 1) {
				currentPage = 1;
//			offset = 0;
			} else if (currentPage > totalPage) {
				currentPage = totalPage;
				offset = (currentPage - 1) * pageSize;
			} else {
				offset = (currentPage - 1) * pageSize;
			}
		}
	}

	public Boolean getNeedPaging() {
		return needPaging;
	}

	public void setNeedPaging(Boolean needPaging) {
		this.needPaging = needPaging;
	}

}
