package com.mockuai.mainweb.common.domain.dto;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/9/19.
 *  增加 删除 查询
 */
public class IndexPageDTO  extends  BaseDTO{

    private  Long id;
    private  String name;
    private Integer type;
    private Integer publishStatus;
    private  Integer pageOrder;
    private  Long categoryId;
    private Long subCategoryId;
    private  Long blockCount;
    private  Long blockPadding;
    private Date gmtCreated;
    private Date gmtModified;
    private List<PageBlockDTO> allPageBlockList;
    private List<PageBlockDTO<ImageElementDTO>> imgPageBlockList;
    private List<PageBlockDTO<ItemListElementDTO>> itemPageBlockList;
    private List<PageBlockDTO> tmsPageBlockList;
    private List<PageBlockDTO<PageGridElementDTO>> gridPageBlockList;
    private List<PageBlockDTO<PagePictureDTO>> picturePageBlockList;

    public Date getGmtCreated() {
        return gmtCreated;
    }

    public void setGmtCreated(Date gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public List<PageBlockDTO<PageGridElementDTO>> getGridPageBlockList() {
		return gridPageBlockList;
	}

	public void setGridPageBlockList(
			List<PageBlockDTO<PageGridElementDTO>> gridPageBlockList) {
		this.gridPageBlockList = gridPageBlockList;
	}

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

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

    public List<PageBlockDTO<PagePictureDTO>> getPicturePageBlockList() {
        return picturePageBlockList;
    }

    public void setPicturePageBlockList(List<PageBlockDTO<PagePictureDTO>> picturePageBlockList) {
        this.picturePageBlockList = picturePageBlockList;
    }
}
