package com.mockuai.itemcenter.core.message.msg;

import java.util.List;

import com.aliyun.openservices.ons.api.Message;
import com.mockuai.itemcenter.common.domain.dto.ItemSalesSkuCountDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemSalesSpuCountDTO;

/**
 * Created by csy
 */
public class PaySuccessMsg {
    private Message msg;
    private ItemSalesSkuCountDTO itemSalesSkuCountDTO;
    private ItemSalesSpuCountDTO itemSalesSpuCountDTO;
    private List<ItemSalesSkuCountDTO> itemSalesSkuCountDTOs;
    private List<ItemSalesSpuCountDTO> itemSalesSpuCountDTOs;
    private String appKey;

    public Message getMsg() {
        return msg;
    }

    public void setMsg(Message msg) {
        this.msg = msg;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

	public ItemSalesSkuCountDTO getItemSalesSkuCountDTO() {
		return itemSalesSkuCountDTO;
	}

	public void setItemSalesSkuCountDTO(ItemSalesSkuCountDTO itemSalesSkuCountDTO) {
		this.itemSalesSkuCountDTO = itemSalesSkuCountDTO;
	}

	public ItemSalesSpuCountDTO getItemSalesSpuCountDTO() {
		return itemSalesSpuCountDTO;
	}

	public void setItemSalesSpuCountDTO(ItemSalesSpuCountDTO itemSalesSpuCountDTO) {
		this.itemSalesSpuCountDTO = itemSalesSpuCountDTO;
	}

	public List<ItemSalesSkuCountDTO> getItemSalesSkuCountDTOs() {
		return itemSalesSkuCountDTOs;
	}

	public void setItemSalesSkuCountDTOs(List<ItemSalesSkuCountDTO> itemSalesSkuCountDTOs) {
		this.itemSalesSkuCountDTOs = itemSalesSkuCountDTOs;
	}

	public List<ItemSalesSpuCountDTO> getItemSalesSpuCountDTOs() {
		return itemSalesSpuCountDTOs;
	}

	public void setItemSalesSpuCountDTOs(List<ItemSalesSpuCountDTO> itemSalesSpuCountDTOs) {
		this.itemSalesSpuCountDTOs = itemSalesSpuCountDTOs;
	}
}
