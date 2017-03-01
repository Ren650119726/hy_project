package com.mockuai.mainweb.common.domain.dto.publish;

/**
 * Created by Administrator on 2016/9/22.
 */
public class ProductListHeaderDTO {

    private String categoryTitle;

    private String subCategoryTitle;

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
}
