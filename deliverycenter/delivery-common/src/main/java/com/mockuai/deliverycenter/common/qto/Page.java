package com.mockuai.deliverycenter.common.qto;


/**
 * 分页对象
 * 
 * @author dong.dong
 *
 */
public class Page {

	private int pageSize = 20;
	private int totalPage;
	private int currentPage = 1;
	private long startRow = 0;
	private long endRow = 1;
	private int pageMax = 10;
	private int pageMax2 = 5;
	private long totalRow = 0;

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getPageMax() {
		return pageMax;
	}

	public void setPageMax(int pageMax) {
		this.pageMax = pageMax;
	}

	public int getPageMax2() {
		return pageMax2;
	}

	public void setPageMax2(int pageMax2) {
		this.pageMax2 = pageMax2;
	}

	public long getStartRow() {
		return startRow;
	}

	public void setStartRow(long startRow) {
		this.startRow = startRow;
	}

	public long getEndRow() {
		return endRow;
	}

	public void setEndRow(long endRow) {
		this.endRow = endRow;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public long getTotalRow() {
		return totalRow;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public void setTotalRow(long totalRow) {
		this.totalRow = totalRow;
	}

	public int getStartPage() {
		if (totalPage <= pageMax) {
			return 1;
		}
		if (currentPage + pageMax2 <= pageMax) {
			return 1;
		}

		if (currentPage + pageMax2 >= totalPage) {
			return totalPage - pageMax + 1;
		}
		return currentPage + pageMax2 - pageMax + 1;
	}

	public int getEndPage() {
		if (totalPage <= pageMax) {
			return totalPage;
		}
		if (currentPage + pageMax2 <= pageMax) {
			return pageMax;
		}

		if (currentPage + pageMax2 >= totalPage) {
			return totalPage;
		}
		return currentPage + pageMax2;
	}

	public int getLastPage() {
		return (this.currentPage < 2) ? 1 : this.currentPage - 1;
	}

	public int getNextPage() {
		return (this.currentPage >= this.totalPage) ? this.totalPage
				: this.currentPage + 1;
	}

	public void fillData() {
		if (pageSize < 1) {
			this.pageSize = 1;
		}
		if (totalRow > 0) {
			if (totalRow > pageSize) {
				long mod = totalRow % pageSize;
				if (mod != 0) {
					totalPage = (int) ((totalRow / pageSize) + 1);
				} else {
					totalPage = (int) (totalRow / pageSize);
				}
			} else {
				totalPage = 1;
			}
		}
		if (currentPage < 1) {
			this.currentPage = 1;
		}
		if (totalPage > 0 && currentPage > totalPage) {
			this.currentPage = totalPage;
		}
		this.startRow = (this.currentPage - 1) * this.pageSize;
		this.endRow = this.currentPage * this.pageSize;
	}

	public void initPageParameter(Long totalRow, String currentPageNo, int size) {
		if (isNotBlank(currentPageNo)) {
			currentPageNo = currentPageNo.length() < 6 ? currentPageNo
					: currentPageNo.substring(0, 5);// 最多99999页数
			setCurrentPage(Integer.parseInt(currentPageNo));
		}
		setTotalRow(totalRow);
		setPageSize(size);
		fillData();
	}

	private boolean isNotBlank(String str) {
		int length;

		if ((str == null) || ((length = str.length()) == 0)) {
			return false;
		}

		for (int i = 0; i < length; i++) {
			if (!Character.isWhitespace(str.charAt(i))) {
				return true;
			}
		}

		return false;
	}
}
