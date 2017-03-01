package com.mockuai.tradecenter.core.manager;

import com.mockuai.deliverycenter.common.dto.express.DeliveryInfoDTO;
import com.mockuai.deliverycenter.common.dto.fee.RegionDTO;
import com.mockuai.tradecenter.common.domain.OrderDeliveryInfoDTO;
import com.mockuai.tradecenter.core.exception.ServiceException;
import com.mockuai.tradecenter.core.exception.TradeException;

import java.util.List;

public interface DeliveryManager {
	
	/**
	 * 根据地址判断是否支持货到付款
	 * @return
	 */
	public boolean isCodSupported(long regionId);
	
	/** 
	 * 根据yon
	 * @param addressId
	 * @param userId
	 * @return
	 */
	public long getDeliveryFee(Long addressId, Long weight);
	
	/**
	 * 调用物流平台写入物流单信息
	 * @param deliveryInfoDTO
	 * @return
	 */
	public Long addDeliveryInfo(OrderDeliveryInfoDTO orderDeliveryInfoDTO,String appkey) throws TradeException;
	
	public boolean batchAddDeliveryInfo(List<OrderDeliveryInfoDTO> list,String appkey) throws TradeException;

	/**
	 * 查询订单物流信息
	 * @param orderId
	 * @param userId
	 * @return
	 * @throws TradeException
	 */
	public List<OrderDeliveryInfoDTO> queryDeliveryInfo(long orderId, long userId,String appkey) throws TradeException;

	public List<RegionDTO> queryRegion(List<String> regionCodes,String appkey) throws TradeException;
	
	public Boolean deliveryGoodsByPickup(Long orderId,Long userId,String pickupCode)throws TradeException;

}