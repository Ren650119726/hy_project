package com.mockuai.itemcenter.common.domain.dto;

import java.util.List;

/**
 * Created by yindingyu on 15/10/16.
 */
public class ItemSearchResultDTO {

    private Long count;
    private List<ItemSearchDTO> itemSearchDTOList;
    private List<SellerBrandDTO> sellerBrandDTOList;
    private List<ItemCategoryDTO> itemCategoryDTOList;

    public List<SellerBrandDTO> getSellerBrandDTOList() {
        return sellerBrandDTOList;
    }

    public void setSellerBrandDTOList(List<SellerBrandDTO> sellerBrandDTOList) {
        this.sellerBrandDTOList = sellerBrandDTOList;
    }

    public List<ItemCategoryDTO> getItemCategoryDTOList() {
        return itemCategoryDTOList;
    }

    public void setItemCategoryDTOList(List<ItemCategoryDTO> itemCategoryDTOList) {
        this.itemCategoryDTOList = itemCategoryDTOList;
    }

    public Long getCount() {
        return count;
    }

    public List<ItemSearchDTO> getItemSearchDTOList() {
        return itemSearchDTOList;
    }

    public void setItemSearchDTOList(List<ItemSearchDTO> itemSearchDTOList) {
        this.itemSearchDTOList = itemSearchDTOList;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
