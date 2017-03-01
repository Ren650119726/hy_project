package com.mockuai.usercenter.core.manager.remote;


import com.mockuai.usercenter.common.constant.ResponseCode;
import com.mockuai.usercenter.common.dto.UserConsigneeDTO;
import com.mockuai.usercenter.core.exception.UserException;

public interface AddressCheckManager {

	/**
	 * 用户收货地址校验
	 * */
	public ResponseCode addressCheck(UserConsigneeDTO userConsigneeDto) throws UserException;
}
