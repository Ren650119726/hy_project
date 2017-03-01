package com.mockuai.mainweb.common.domain.dto.index;

import java.util.List;

/**
 * Created by edgar.zr on 3/28/2016.
 */
public class CardDTO {
    private String type;
    private String needBorder;
    private List<IndexItemDTO> itemList;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNeedBorder() {
        return needBorder;
    }

    public void setNeedBorder(String needBorder) {
        this.needBorder = needBorder;
    }

    public List<IndexItemDTO> getItemList() {
        return itemList;
    }

    public void setItemList(List<IndexItemDTO> itemList) {
        this.itemList = itemList;
    }
}