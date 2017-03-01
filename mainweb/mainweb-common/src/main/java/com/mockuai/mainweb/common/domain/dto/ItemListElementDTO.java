package com.mockuai.mainweb.common.domain.dto; /**
 * create by 冠生
 *
 * @date #{DATE}
 **/

import java.util.List;

/**
 * Created by hy on 2016/9/19.
 */
public class ItemListElementDTO extends  ElementDTO {

   private String categoryTitle;

   private String subCategoryTitle;

    private List<PageItemDTO> productListList;


    public String getCategoryTitle() {
        return categoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    public String getSubCategoryTitle() {
        return subCategoryTitle;
    }

    public void setSubCategoryTitle(String subCategoryTitle) {
        this.subCategoryTitle = subCategoryTitle;
    }

    public List<PageItemDTO> getProductListList() {
        return productListList;
    }

    public void setProductListList(List<PageItemDTO> productListList) {
        this.productListList = productListList;
    }
}
