package com.mockuai.mainweb.common.domain.dto.index;

import java.util.List;

/**
 * Created by edgar.zr on 5/25/2016.
 */
public class IndexHorizontalScrollDTO {
    private List<IndexProductDTO> productList;

    public List<IndexProductDTO> getProductList() {
        return productList;
    }

    public void setProductList(List<IndexProductDTO> productList) {
        this.productList = productList;
    }
}