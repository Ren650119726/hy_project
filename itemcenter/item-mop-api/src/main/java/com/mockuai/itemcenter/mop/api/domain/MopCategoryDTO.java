package com.mockuai.itemcenter.mop.api.domain;

import java.util.List;

/**
 * Created by zengzhangqiang on 5/26/15.
 */
public class MopCategoryDTO {
    private Long id;

    //与ID同意义，只为兼容不同接口
    private Long categoryId;

    private String cateName;
    private Integer cateLevel;
    private Long parentId;
    private Long topId;
    private Integer sort;
    private String imageUrl;
    private List<MopCategoryDTO> subCategorys;

    public Long getCategoryId() {
        return categoryId;
    }


    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<MopCategoryDTO> getSubCategorys() {
        return subCategorys;
    }

    public void setSubCategorys(List<MopCategoryDTO> subCategorys) {
        this.subCategorys = subCategorys;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }

    public Integer getCateLevel() {
        return cateLevel;
    }

    public void setCateLevel(Integer cateLevel) {
        this.cateLevel = cateLevel;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getTopId() {
        return topId;
    }

    public void setTopId(Long topId) {
        this.topId = topId;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}
