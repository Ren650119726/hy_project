package com.mockuai.itemcenter.common.domain.dto.area;

import java.io.Serializable;

/**
 * Created by yindingyu on 15/10/19.
 */
public class CityDTO implements Serializable {

    private String cityId;
    private String cityName;



    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
