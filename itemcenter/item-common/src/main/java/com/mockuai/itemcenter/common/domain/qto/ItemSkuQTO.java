package com.mockuai.itemcenter.common.domain.qto;

import com.mockuai.itemcenter.common.page.PageInfo;

import java.util.List;

/**
 * ItemSKU QTO
 * 
 * @author chen.huang
 * @date 2015年1月21日
 */
public class ItemSkuQTO extends PageInfo {
	private Integer deleteMark;

	private Long id;

	private Integer needImage;
    private Boolean isNeedQueryStock =true;
    private Integer isNeedQueryRemoveSku  ;


    public Integer getNeedImage() {
        return needImage;
    }

    public void setNeedImage(Integer needImage) {
        this.needImage = needImage;
    }

    private List<Long> idList;

    private List<Long> itemIdList;

    public List<Long> getItemIdList() {
        return itemIdList;
    }

    public void setItemIdList(List<Long> itemIdList) {
        this.itemIdList = itemIdList;
    }

    private Long itemId; // 商品ID

	private Long sellerId;// 卖家ID
	
	private String barCode;//条形码

	private String bizCode;

	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public Integer getDeleteMark() {
		return deleteMark;
	}

	public void setDeleteMark(Integer isDeleted) {
		this.deleteMark = isDeleted;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

	public List<Long> getIdList() {
		return idList;
	}

	public void setIdList(List<Long> idList) {
		this.idList = idList;
	}

	public String getBizCode() {
		return bizCode;
	}

	public void setBizCode(String bizCode) {
		this.bizCode = bizCode;
	}

    public Boolean getNeedQueryStock() {
        return isNeedQueryStock;
    }

    public void setNeedQueryStock(Boolean needQueryStock) {
        isNeedQueryStock = needQueryStock;
    }

    public Integer getIsNeedQueryRemoveSku() {
        return isNeedQueryRemoveSku;
    }

    public void setIsNeedQueryRemoveSku(Integer isNeedQueryRemoveSku) {
        this.isNeedQueryRemoveSku = isNeedQueryRemoveSku;
    }
}