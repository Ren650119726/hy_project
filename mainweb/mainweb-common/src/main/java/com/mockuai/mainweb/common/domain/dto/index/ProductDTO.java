package com.mockuai.mainweb.common.domain.dto.index;

import java.util.List;

/**
 * Created by edgar.zr on 3/28/2016.
 */
public class ProductDTO {
    private Integer productType;
    private List<IndexProductDTO> productList;

    public Integer getProductType() {
        return productType;
    }

    public void setProductType(Integer productType) {
        this.productType = productType;
    }

    public List<IndexProductDTO> getProductList() {
        return productList;
    }

    public void setProductList(List<IndexProductDTO> productList) {
        this.productList = productList;
    }
}