package com.mockuai.itemcenter.core.manager;

import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.message.msg.PaySuccessMsg;

/**
 * Created by csy
 */
public interface ItemSalesSpuCountManager {
	Long updateItemSalesSpuCount(PaySuccessMsg paySuccessMsg, String salesParam) throws ItemException;
}
