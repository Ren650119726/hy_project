package com.mockuai.tradecenter.core.manager;

import java.util.List;
import java.util.Map;

import com.mockuai.marketingcenter.common.domain.dto.DiscountInfo;
import com.mockuai.marketingcenter.common.domain.dto.MarketItemDTO;
import com.mockuai.marketingcenter.common.domain.dto.MultiSettlementInfo;
import com.mockuai.marketingcenter.common.domain.dto.SettlementInfo;
import com.mockuai.tradecenter.common.domain.OrderItemDTO;
import com.mockuai.tradecenter.common.domain.UsedWealthDTO;
import com.mockuai.tradecenter.core.domain.CartItemDO;
import com.mockuai.tradecenter.core.exception.TradeException;

/**
 * Created by zengzhangqiang on 5/29/15.
 */
public interface MarketingManager {
    public SettlementInfo getSettlementInfo(
            long userId, List<OrderItemDTO> orderItemDTOs,Long consigneeId, String appKey) throws TradeException;
    
    public SettlementInfo getSettlementInfoMar(long userId, List<MarketItemDTO> marketingItemDTOs, Long consigneeId,
			String appKey) throws TradeException;
    
    public boolean preUseUserCoupon(
            long userId, List<Long> userCouponIdList, long orderId, String appKey) throws TradeException;

    public boolean useUserCoupon(long userId, long orderId, String appKey) throws TradeException;

    public boolean releaseUsedCoupon(long userId, long orderId, String appKey) throws TradeException;
    
    //批量预使用优惠券
    public boolean preUseUserCouponBatch(Long userId,Map<Long, List<Long>> orderIdKeyUserCouponIdList,String appkey)throws TradeException;
    //批量使用优惠券
    public boolean useUserCouponBatch(Long userId,List<Long> orderIds, String appKey)throws TradeException;

//    public Map<Long,List<DiscountInfo>> getCartDiscountInfoBatch(Map<Long, List<MarketItemDTO>> sellerKeyItems, String appKey)throws TradeException;
 
//    public MultiSettlementInfo getMultiSettlementInfo(Long userId,Long consigneeId, Map<Long,List<OrderItemDTO>> sellerIdItemsMap, 
//    		Map<Long,Integer> sellerDeliveryIdMap,
//    		String appKey)throws TradeException;
    
    //批量释放优惠券 （预使用）
    public Boolean releaseMultiUsedCoupon(Long userId,Map<Long, List<Long>> orderIdKeyUserCouponIdList,String appkey)throws TradeException;

//    public Map<Long, List<DiscountInfo>> getSupplierCartDiscountInfo(List<MarketItemDTO> marketItemDTOs,String appKey)throws TradeException;

     List<DiscountInfo> getCartDiscountInfo(List<MarketItemDTO> marketItemDTOList,String appKey ,Long userId) throws TradeException ;

    }
