package com.mockuai.mainweb.common.domain.qto;

import com.mockuai.mainweb.common.domain.dto.ImageElementDTO;
import com.mockuai.mainweb.common.domain.dto.ItemListElementDTO;
import com.mockuai.mainweb.common.domain.dto.PageBlockDTO;

import java.util.Date;
import java.util.List;

/**
 * Created by huangsiqian on 2016/11/24.
 */
public class PageQTO extends BaseQTO {
    private  Long id;
    private  String name;
    private  Integer type; //首页类型
    private  Integer pageOrder;
    private  Long categoryId;
    private Long subCategoryId;
    private  Long blockCount;
    private  Long blockPadding;
    private Date gmtModified;
    private Integer publishStatus;
    private List<PageBlockDTO<ImageElementDTO>> imgPageBlockList;
    private List<PageBlockDTO<ItemListElementDTO>> itemPageBlockList;
    private List<PageBlockDTO> tmsPageBlockList;

    public Integer getPublishStatus() {
        return publishStatus;
    }

    public void setPublishStatus(Integer publishStatus) {
        this.publishStatus = publishStatus;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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

    public List<PageBlockDTO> getTmsPageBlockList() {
        return tmsPageBlockList;
    }

    public void setTmsPageBlockList(List<PageBlockDTO> tmsPageBlockList) {
        this.tmsPageBlockList = tmsPageBlockList;
    }
}
