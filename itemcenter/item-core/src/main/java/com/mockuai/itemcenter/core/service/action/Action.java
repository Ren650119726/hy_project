package com.mockuai.itemcenter.core.service.action;

import com.mockuai.itemcenter.core.exception.ItemException;
import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.core.service.RequestContext;

/**
 * 操作对像基类
 * 
 * @author wujin.zzq
 *
 */
@Service
public interface Action {


	@SuppressWarnings("rawtypes")
	public ItemResponse execute(RequestContext context) throws ItemException;

	public String getName();
}
