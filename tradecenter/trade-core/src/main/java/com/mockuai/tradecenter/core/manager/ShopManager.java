package com.mockuai.tradecenter.core.manager;

import java.util.List;

import com.mockuai.itemcenter.common.domain.dto.CommissionUnitDTO;
import com.mockuai.shopcenter.domain.dto.ShopDTO;
import com.mockuai.shopcenter.domain.qto.ShopQTO;
import com.mockuai.tradecenter.core.exception.TradeException;
/**
 * 
 * @author hzmk
 *
 */
public interface ShopManager {
	
	public ShopDTO getShopDTO(Long sellerId, String appKey)throws TradeException;
	
	public List<ShopDTO> queryShop(ShopQTO query,String appKey)throws TradeException;
	
	/**
	 * 获取订单佣金
	 * @param commissionUnitDTOList
	 * @param appKey
	 * @return
	 */
	public Long getOrderCommission(List<CommissionUnitDTO> commissionUnitDTOList,String appKey)throws TradeException;
}
