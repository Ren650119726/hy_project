package com.mockuai.distributioncenter.core.dao;

import java.util.List;

import com.mockuai.distributioncenter.common.domain.qto.SellerLevelApplyQTO;
import com.mockuai.distributioncenter.core.domain.SellerLevelApplyDO;


/**
 * Created by wgl on 16/7/20.
 */
public interface SellerLevelApplyDAO {

	
	List<SellerLevelApplyDO>  querySellerLevelApply(SellerLevelApplyQTO sellerLevelApplyQTO);
	
	int updateSellerLevelApply(SellerLevelApplyDO sellerLevelApplyDO);
	
	Long addSellerLevelApply(SellerLevelApplyDO sellerLevelApplyDO);
	
	SellerLevelApplyDO getSellerLevelApply(SellerLevelApplyDO sellerLevelApplyDO);
}
