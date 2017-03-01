package com.mockuai.tradecenter.core.dao;

import java.util.List;

import com.mockuai.tradecenter.common.domain.settlement.SellerTransLogQTO;
import com.mockuai.tradecenter.core.domain.SellerTransLogDO;

public interface SellerTransLogDAO {
	
	public Long addSellerTransLog(SellerTransLogDO transLog);
	
	public List<SellerTransLogDO> querySellerTransLog(SellerTransLogQTO query);
	
	public Long getQueryCount(SellerTransLogQTO query);
	
	public SellerTransLogDO getTranslogByWithdrawId(Long withdrawId);
	
	public void updateById(SellerTransLogDO translogDO);
	
	public SellerTransLogDO getTransLogByOrderIdAndType(String osn,String type);
	
	public SellerTransLogDO getTransLogByOrderSnAndSkuId(String orderSn,Long skuId);
	
	public SellerTransLogDO getSingleTransLog(String orderSN,String type,int shopType);

}
