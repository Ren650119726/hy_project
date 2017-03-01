package com.mockuai.itemcenter.common.domain.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yindingyu on 15/12/4.
 */
public class ItemPriceDTO implements Serializable {

    private ItemSkuDTO itemSkuDTO;

    private ItemDTO itemDTO;

    private List<ValueAddedServiceDTO> valueAddedServiceDTOList;

    public ItemSkuDTO getItemSkuDTO() {
        return itemSkuDTO;
    }

    public void setItemSkuDTO(ItemSkuDTO itemSkuDTO) {
        this.itemSkuDTO = itemSkuDTO;
    }

    public ItemDTO getItemDTO() {
        return itemDTO;
    }

    public void setItemDTO(ItemDTO itemDTO) {
        this.itemDTO = itemDTO;
    }

    public List<ValueAddedServiceDTO> getValueAddedServiceDTOList() {
        return valueAddedServiceDTOList;
    }

    public void setValueAddedServiceDTOList(List<ValueAddedServiceDTO> valueAddedServiceDTOList) {
        this.valueAddedServiceDTOList = valueAddedServiceDTOList;
    }
}
