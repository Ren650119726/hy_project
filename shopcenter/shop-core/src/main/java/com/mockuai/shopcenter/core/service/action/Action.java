package com.mockuai.shopcenter.core.service.action;

import com.mockuai.shopcenter.api.ShopResponse;
import com.mockuai.shopcenter.core.exception.ShopException;
import com.mockuai.shopcenter.core.service.RequestContext;
import org.springframework.stereotype.Service;

/**
 * 操作对像基类
 * 
 * @author wujin.zzq
 *
 */
@Service
public interface Action {

	public ShopResponse execute(RequestContext context) throws ShopException;

	public String getName();
}
