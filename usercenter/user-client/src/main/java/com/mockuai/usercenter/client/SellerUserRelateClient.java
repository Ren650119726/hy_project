package com.mockuai.usercenter.client;

import java.util.List;

import com.mockuai.usercenter.common.api.Response;
import com.mockuai.usercenter.common.dto.SellerUserRelateDTO;
import com.mockuai.usercenter.common.qto.SellerUserQTO;

public interface SellerUserRelateClient {

	Response<Boolean> addSellerUserRelate(Long userId, Long sellerId, Long orderId,String tradeType, Long orderAmt, String appKey);
	
	Response<List<SellerUserRelateDTO>> querySellerUserRelate(SellerUserQTO query,String appKey);

}
