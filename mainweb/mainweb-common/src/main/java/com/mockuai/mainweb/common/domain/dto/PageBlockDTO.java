package com.mockuai.mainweb.common.domain.dto;

import java.util.List;

/**
 * Created by Administrator on 2016/9/19.
 */
public class PageBlockDTO<T>  extends  BaseDTO{
    private Long id;
    private Long pageId;
    private Integer blockType; //1 轮播   2  商品列表 3 tms 5 格子块 4 秒杀 6图片块
    private Integer blockOrder;
    private Long height;
    private String blockTitle;
    private Long subCategoryId;
    private String tmsName;




//    private List<ElementDTO> elementList;

    private List<T> elementList;




    public List<T> getElementList() {
        return elementList;
    }

    public void setElementList(List<T> elementList) {
        this.elementList = elementList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPageId() {
        return pageId;
    }

    public void setPageId(Long pageId) {
        this.pageId = pageId;
    }

    public Integer getBlockType() {
        return blockType;
    }

    public void setBlockType(Integer blockType) {
        this.blockType = blockType;
    }

    public Integer getBlockOrder() {
        return blockOrder;
    }

    public void setBlockOrder(Integer blockOrder) {
        this.blockOrder = blockOrder;
    }

    public Long getHeight() {
        return height;
    }

    public void setHeight(Long height) {
        this.height = height;
    }

    public String getBlockTitle() {
        return blockTitle;
    }

    public void setBlockTitle(String blockTitle) {
        this.blockTitle = blockTitle;
    }

    public Long getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(Long subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public String getTmsName() {
        return tmsName;
    }

    public void setTmsName(String tmsName) {
        this.tmsName = tmsName;
    }

//    public List<ElementDTO> getElementList() {
//        return elementList;
//    }
//
//    public void setElementList(List<ElementDTO> elementList) {
//        this.elementList = elementList;
//    }



}
