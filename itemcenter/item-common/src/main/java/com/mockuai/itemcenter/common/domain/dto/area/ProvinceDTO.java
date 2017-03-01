package com.mockuai.itemcenter.common.domain.dto.area;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yindingyu on 15/10/19.
 */
public class ProvinceDTO implements Serializable {

    private String provinceId;
    private String provinceName;
    private List<CityDTO> cities;

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public List<CityDTO> getCities() {
        return cities;
    }

    public void setCities(List<CityDTO> cities) {
        this.cities = cities;
    }
}
