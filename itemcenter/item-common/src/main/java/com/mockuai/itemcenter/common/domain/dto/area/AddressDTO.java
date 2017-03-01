package com.mockuai.itemcenter.common.domain.dto.area;

import java.io.Serializable;

/**
 * Created by yindingyu on 15/10/20.
 */
public class AddressDTO implements Serializable {

    private String provinceCode;
    private String cityCode;

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }
}
