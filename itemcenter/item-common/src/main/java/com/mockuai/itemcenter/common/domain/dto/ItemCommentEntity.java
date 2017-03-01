package com.mockuai.itemcenter.common.domain.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by luliang on 15/8/3.
 */
public class ItemCommentEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private String itemOrderId;
    private List<ItemCommentDTO> itemCommentDTOList = new ArrayList<ItemCommentDTO>();

    public String getItemOrderId() {
        return itemOrderId;
    }

    public void setItemOrderId(String itemOrderId) {
        this.itemOrderId = itemOrderId;
    }

    public List<ItemCommentDTO> getItemCommentDTOList() {
        return itemCommentDTOList;
    }

    public void setItemCommentDTOList(List<ItemCommentDTO> itemCommentDTOList) {
        this.itemCommentDTOList = itemCommentDTOList;
    }

    public boolean sameRecord(ItemCommentEntity itemCommentEntity) {
        if(itemCommentEntity != null) {

        }
        return false;
    }
}
