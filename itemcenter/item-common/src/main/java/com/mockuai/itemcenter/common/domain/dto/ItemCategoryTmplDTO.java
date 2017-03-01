package com.mockuai.itemcenter.common.domain.dto;

import java.io.Serializable;
import java.util.List;

public class ItemCategoryTmplDTO implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -341891852449525564L;

    private Long id;

    private String cateName; // 类目名称

    private Integer cateLevel;// 类目层级

    private Long parentId; // 父ID

    private Integer sort;

    private Long topId; // 所属一级类目

    private String imageUrl;

    public Long getItemCount() {
        return itemCount;
    }

    public void setItemCount(Long itemCount) {
        this.itemCount = itemCount;
    }

    private Long itemCount;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    private List<ItemCategoryTmplDTO> subCategorys;

    public List<ItemCategoryTmplDTO> getSubCategorys() {
        return subCategorys;
    }

    public void setSubCategorys(List<ItemCategoryTmplDTO> subCategorys) {
        this.subCategorys = subCategorys;
    }

    /**
     * 商品属性模板列表
     */
    private List<ItemPropertyTmplDTO> itemPropertyTmplList;

    /**
     * sku属性模板列表
     */
    private List<SkuPropertyTmplDTO> skuPropertyTmplList;

    @Override
    public String toString() {
        return "ItemCategoryDTO [id=" + id + ", cateName=" + cateName
                + ", cateLevel=" + cateLevel + ", parentId=" + parentId
                + ", topId=" + topId + "]";
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

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Long getTopId() {
        return topId;
    }

    public void setTopId(Long topId) {
        this.topId = topId;
    }

    public List<ItemPropertyTmplDTO> getItemPropertyTmplList() {
        return itemPropertyTmplList;
    }

    public void setItemPropertyTmplList(List<ItemPropertyTmplDTO> itemPropertyTmplList) {
        this.itemPropertyTmplList = itemPropertyTmplList;
    }

    public List<SkuPropertyTmplDTO> getSkuPropertyTmplList() {
        return skuPropertyTmplList;
    }

    public void setSkuPropertyTmplList(List<SkuPropertyTmplDTO> skuPropertyTmplList) {
        this.skuPropertyTmplList = skuPropertyTmplList;
    }
}