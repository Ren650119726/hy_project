package com.mockuai.mainweb.common.domain.qto;

/**
 * Created by huangsiqian  on 2016/12/19.
 */
public class PagePictureQTO extends BaseQTO {
    private Long pageId;

    private Long blockId;

    private Integer deleteMark;

    public Long getPageId() {
        return pageId;
    }

    public void setPageId(Long pageId) {
        this.pageId = pageId;
    }

    public Long getBlockId() {
        return blockId;
    }

    public void setBlockId(Long blockId) {
        this.blockId = blockId;
    }

    public Integer getDeleteMark() {
        return deleteMark;
    }

    public void setDeleteMark(Integer deleteMark) {
        this.deleteMark = deleteMark;
    }
}
