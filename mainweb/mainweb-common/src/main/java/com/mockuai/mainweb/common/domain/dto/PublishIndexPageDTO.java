package com.mockuai.mainweb.common.domain.dto;

import java.util.List;

/**
 * Created by Administrator on 2016/9/19.
 *  增加 删除 查询
 */
public class PublishIndexPageDTO extends  BaseDTO{

    private  Long id;
    private  String name;
    private Integer publishStatus;
    private  Integer pageOrder;
    private  Long categoryId;
    private Long subCategoryId;
    private  Long blockCount;
    private  Long blockPadding;
    private List<PageBlockDTO> allPageBlockList;
    private List<PageBlockDTO<ImageElementDTO>> imgPageBlockList;
    private List<PageBlockDTO<ItemListElementDTO>> itemPageBlockList;
    private List<PageBlockDTO> tmsPageBlockList;
    private Long indexPageId;
    public Integer getPublishStatus() {
        return publishStatus;
    }

    public void setPublishStatus(Integer publishStatus) {
        this.publishStatus = publishStatus;
    }

    public List<PageBlockDTO> getTmsPageBlockList() {
        return tmsPageBlockList;
    }

    public void setTmsPageBlockList(List<PageBlockDTO> tmsPageBlockList) {
        this.tmsPageBlockList = tmsPageBlockList;
    }

    public List<PageBlockDTO<ImageElementDTO>> getImgPageBlockList() {
        return imgPageBlockList;
    }

    public void setImgPageBlockList(List<PageBlockDTO<ImageElementDTO>> imgPageBlockList) {
        this.imgPageBlockList = imgPageBlockList;
    }

    public List<PageBlockDTO<ItemListElementDTO>> getItemPageBlockList() {
        return itemPageBlockList;
    }

    public void setItemPageBlockList(List<PageBlockDTO<ItemListElementDTO>> itemPageBlockList) {
        this.itemPageBlockList = itemPageBlockList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPageOrder() {
        return pageOrder;
    }

    public void setPageOrder(Integer pageOrder) {
        this.pageOrder = pageOrder;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(Long subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public Long getBlockCount() {
        return blockCount;
    }

    public void setBlockCount(Long blockCount) {
        this.blockCount = blockCount;
    }

    public Long getBlockPadding() {
        return blockPadding;
    }

    public void setBlockPadding(Long blockPadding) {
        this.blockPadding = blockPadding;
    }

    public List<PageBlockDTO> getAllPageBlockList() {
        return allPageBlockList;
    }

    public void setAllPageBlockList(List<PageBlockDTO> allPageBlockList) {
        this.allPageBlockList = allPageBlockList;
    }

    public Long getIndexPageId() {
        return indexPageId;
    }

    public void setIndexPageId(Long indexPageId) {
        this.indexPageId = indexPageId;
    }
}
