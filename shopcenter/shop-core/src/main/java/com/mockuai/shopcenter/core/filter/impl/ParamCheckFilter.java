package com.mockuai.shopcenter.core.filter.impl;


import com.mockuai.shopcenter.api.ShopResponse;
import com.mockuai.shopcenter.core.exception.ShopException;
import com.mockuai.shopcenter.core.filter.Filter;
import com.mockuai.shopcenter.core.service.RequestContext;

public class ParamCheckFilter implements Filter {

	@Override
	public boolean isAccept(RequestContext ctx) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ShopResponse before(RequestContext ctx) throws ShopException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ShopResponse after(RequestContext ctx) throws ShopException {
		// TODO Auto-generated method stub
		return null;
	}
}
