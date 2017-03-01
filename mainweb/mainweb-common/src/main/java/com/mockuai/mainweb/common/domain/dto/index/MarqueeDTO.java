package com.mockuai.mainweb.common.domain.dto.index;

import java.util.List;

/**
 * Created by edgar.zr on 3/28/2016.
 */
public class MarqueeDTO {
    private String label;
    private List<IndexItemDTO> itemList;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<IndexItemDTO> getItemList() {
        return itemList;
    }

    public void setItemList(List<IndexItemDTO> itemList) {
        this.itemList = itemList;
    }
}