package com.mockuai.mainweb.common.domain.dto.publish;

import com.mockuai.mainweb.common.domain.dto.PageItemDTO;

import java.util.List;

/**
 * Created by Administrator on 2016/9/23.
 */
public class PublishProductDTO {
    private Integer productType;

    private List<ProductJsonDTO> productList;


    public List<ProductJsonDTO> getProductList() {
        return productList;
    }

    public void setProductList(List<ProductJsonDTO> productList) {
        this.productList = productList;
    }

    public Integer getProductType() {
        return productType;
    }

    public void setProductType(Integer productType) {
        this.productType = productType;
    }
}
