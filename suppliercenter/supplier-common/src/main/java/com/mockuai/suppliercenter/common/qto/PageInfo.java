package com.mockuai.suppliercenter.common.qto;

public class PageInfo {

    private int totalPage; //
    private int totalCount; //
    private int currentPage; // 分页页码
    private int pageSize; // 分页长度
    private int offset;//
    private Boolean needPaging;

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

    public Boolean getNeedPaging() {
        return needPaging;
    }

    public void setNeedPaging(Boolean needPaging) {
        this.needPaging = needPaging;
    }


}
