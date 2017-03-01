package com.mockuai.tradecenter.core.dao;

import java.util.List;

import com.mockuai.tradecenter.common.domain.settlement.SellerMoneyQTO;
import com.mockuai.tradecenter.core.domain.SellerMoneyDO;


public interface SellerMoneyDAO {
	
	public Long addSellerMoney(SellerMoneyDO sellerMoney);
	
	public Long updateSellerMoney(SellerMoneyDO sellerMoney);
	
	public SellerMoneyDO getSellerMoneyBySellerId(Long sellerId);
	
	public List<SellerMoneyDO> querySellerMoney(SellerMoneyQTO query);
	
	public SellerMoneyDO getSellerMoney(String bizCode,Integer shopType);
}
