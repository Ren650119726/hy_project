package com.mockuai.itemcenter.core.manager;

import com.mockuai.itemcenter.core.message.msg.PaySuccessMsg;

/**
 * Created by csy
 */
public interface ItemSalesSkuCountManager {
	Long updateItemSalesSkuCount(PaySuccessMsg paySuccessMsg, String salesParam);
}
