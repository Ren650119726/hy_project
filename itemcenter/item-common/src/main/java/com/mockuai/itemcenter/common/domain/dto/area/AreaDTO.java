package com.mockuai.itemcenter.common.domain.dto.area;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yindingyu on 15/10/12.
 */
public class AreaDTO implements Serializable,Comparable {

    private String areaName;
    private List<ProvinceDTO> provinces;

    public AreaDTO(String areaName) {
        this.areaName = areaName;
        provinces = new ArrayList<ProvinceDTO>();
    }

    public AreaDTO() {
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public List<ProvinceDTO> getProvinces() {
        return provinces;
    }

    public void setProvinces(List<ProvinceDTO> provinces) {
        this.provinces = provinces;
    }

    public int compareTo(Object o) {

        if(o instanceof AreaDTO){
            AreaDTO areaDTO = (AreaDTO)o;
            return areaName.compareTo(areaDTO.getAreaName());
        }

        return 1;
    }
}
