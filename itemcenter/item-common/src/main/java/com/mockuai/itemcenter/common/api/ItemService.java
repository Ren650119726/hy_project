package com.mockuai.itemcenter.common.api;

import com.mockuai.itemcenter.common.domain.dto.ItemDTO;

/**
 * @author zhangqiang.zeng
 */
public interface ItemService {
	@SuppressWarnings("unchecked")
	public Response execute(Request request);

	public Response addItem(ItemDTO itemDTO);
}
