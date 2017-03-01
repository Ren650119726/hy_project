package com.mockuai.itemcenter.common.domain.qto;

import com.mockuai.itemcenter.common.page.PageInfo;

import java.io.Serializable;

/**
 * 商品参数
 */
public class SpecQTO extends PageInfo implements Serializable{

    private Long id;


    private String specName;

    private String excludeSpecName;

    private Integer sortOrder;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSpecName() {
        return specName;
    }

    public void setSpecName(String specName) {
        this.specName = specName;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getExcludeSpecName() {
        return excludeSpecName;
    }

    public void setExcludeSpecName(String excludeSpecName) {
        this.excludeSpecName = excludeSpecName;
    }
}