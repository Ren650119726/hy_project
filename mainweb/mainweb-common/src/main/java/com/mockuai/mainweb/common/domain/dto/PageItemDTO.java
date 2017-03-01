package com.mockuai.mainweb.common.domain.dto; /**
 * create by 冠生
 * @date #{DATE}
 **/
/**
 * Created by hy on 2016/9/19.
 */
public class PageItemDTO  extends  BaseDTO{

    private Long id;

    private Long pageItemCategoryId;
    private Long pageId;
    private Long ItemId;
    private String itemName ;
    private String iconUrl;
    private Integer itemOrder;
    private Long marketPrice ;
    private Long sellerId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPageItemCategoryId() {
        return pageItemCategoryId;
    }

    public void setPageItemCategoryId(Long pageItemCategoryId) {
        this.pageItemCategoryId = pageItemCategoryId;
    }

    public Long getPageId() {
        return pageId;
    }

    public void setPageId(Long pageId) {
        this.pageId = pageId;
    }

    public Long getItemId() {
        return ItemId;
    }

    public void setItemId(Long itemId) {
        ItemId = itemId;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public Integer getItemOrder() {
        return itemOrder;
    }

    public void setItemOrder(Integer itemOrder) {
        this.itemOrder = itemOrder;
    }

    public Long getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(Long marketPrice) {
        this.marketPrice = marketPrice;
    }
}
