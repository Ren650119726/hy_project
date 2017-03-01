package com.mockuai.mainweb.common.domain.dto; /**
 * create by 冠生
 *
 * @date #{DATE}
 **/

/**
 * Created by hy on 2016/9/19.
 */
public class ElementDTO  extends  BaseDTO{

    private Long id ;
    private Integer sortIndex;

    private Long blockId;

    private Long pageId;




    public Integer getSortIndex() {
        return sortIndex;
    }

    public void setSortIndex(Integer sortIndex) {
        this.sortIndex = sortIndex;
    }

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
}
