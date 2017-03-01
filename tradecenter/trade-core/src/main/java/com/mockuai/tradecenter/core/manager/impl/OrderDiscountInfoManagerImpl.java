package com.mockuai.tradecenter.core.manager.impl;

import com.mockuai.itemcenter.common.domain.dto.ItemSkuDTO;
import com.mockuai.marketingcenter.common.domain.dto.*;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.common.domain.*;
import com.mockuai.tradecenter.core.dao.OrderDiscountInfoDAO;
import com.mockuai.tradecenter.core.domain.OrderDiscountInfoDO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.BaseService;
import com.mockuai.tradecenter.core.manager.ItemManager;
import com.mockuai.tradecenter.core.manager.MarketingManager;
import com.mockuai.tradecenter.core.manager.OrderDiscountInfoManager;
import com.mockuai.virtualwealthcenter.common.domain.dto.WealthAccountDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by zengzhangqiang on 5/24/15.
 */
public class OrderDiscountInfoManagerImpl extends BaseService implements OrderDiscountInfoManager {
	

    private static final Logger log = LoggerFactory.getLogger(OrderDiscountInfoManagerImpl.class);

	@Resource
	private OrderDiscountInfoDAO orderDiscountInfoDAO;

	@Resource
	private MarketingManager marketingManager;

	@Resource
	private ItemManager itemManager;

	public Long addOrderDiscountInfo(OrderDiscountInfoDO orderDiscountInfoDO) throws TradeException {
		try {
			Long orderDiscountInfoId = this.orderDiscountInfoDAO.addOrderDiscountInfo(orderDiscountInfoDO);
			return orderDiscountInfoId;
		} catch (Exception e) {
			throw new TradeException(ResponseCode.SYS_E_DATABASE_ERROR, e);
		}
	}

	public List<OrderDiscountInfoDO> queryOrderDiscountInfo(Long orderId, Long userId) throws TradeException {
		try {
			List<OrderDiscountInfoDO> discountInfoDOs = this.orderDiscountInfoDAO.queryOrderDiscountInfo(orderId,
					userId);
			return discountInfoDOs;
		} catch (Exception e) {
			throw new TradeException(ResponseCode.SYS_E_DATABASE_ERROR, e);
		}

	}

	private SettlementInfo getSettlementInfo(Long userId, List<OrderItemDTO> orderItems, Long consigneeId,String appkey)
			throws TradeException {
		SettlementInfo settlementInfo = marketingManager.getSettlementInfo(userId, orderItems, consigneeId,appkey);
		return settlementInfo;
	}

	// 代表优惠券
	private List<DiscountInfo> getDiscountInfos(SettlementInfo settlementInfo) throws TradeException {
		if (settlementInfo == null) {
			return Collections.EMPTY_LIST;
		}
		// 营销活动优惠信息处理
		List<DiscountInfo> discountInfos = settlementInfo.getDiscountInfoList();
		return discountInfos;
	}

	private List<OrderDiscountInfoDO> processDirectDiscount(boolean hasSubOrder, List<DiscountInfo> discountInfos)
			throws TradeException {

		List<OrderDiscountInfoDO> orderDiscountInfoDOs = new ArrayList<OrderDiscountInfoDO>();

			// 营销活动优惠信息处理
			if (discountInfos != null && discountInfos.isEmpty() == false) {
				for (DiscountInfo discountInfo : discountInfos) {
					MarketActivityDTO marketActivityDTO = discountInfo.getActivity();
					
					 if(discountInfo.getActivity().getScope()==3){
						 if (marketActivityDTO.getToolCode().equals("ReachMultipleReduceTool")) { // 代表满减
								
								OrderDiscountInfoDO orderDiscountInfoDO = new OrderDiscountInfoDO();
								orderDiscountInfoDO.setDiscountType(1);
								orderDiscountInfoDO.setDiscountCode(marketActivityDTO.getToolCode());
								orderDiscountInfoDO.setDiscountAmount(discountInfo.getDiscountAmount());
								orderDiscountInfoDO.setDiscountDesc("满减送");
								orderDiscountInfoDOs.add(orderDiscountInfoDO);
							}
					 }
					

				}

			}

		// TODO 本次订单所满足的优惠信息校验

		return orderDiscountInfoDOs;
	}

	private List<OrderDiscountInfoDO> processDiscount(boolean hasSubOrder, OrderDTO orderDTO,
			List<DiscountInfo> discountInfos, SettlementInfo settlementInfo) throws TradeException {

		List<OrderDiscountInfoDO> orderDiscountInfoDOs = new ArrayList<OrderDiscountInfoDO>();

		if (!hasSubOrder) {
			// 营销活动优惠信息处理
			if (discountInfos != null && discountInfos.isEmpty() == false) {
				Map<Long, GrantedCouponDTO> availableCouponMap = new HashMap<Long, GrantedCouponDTO>();
				for (DiscountInfo discountInfo : discountInfos) {
					MarketActivityDTO marketActivityDTO = discountInfo.getActivity();

					// if(marketActivityDTO.getCouponMark() != 0){//需要优惠券的活动
					if (marketActivityDTO.getToolCode().equals("SYS_MARKET_TOOL_000001")) {

						List<GrantedCouponDTO> availableCouponList = discountInfo.getAvailableCoupons();
						if (availableCouponList != null) {
							for (GrantedCouponDTO grantedCouponDTO : availableCouponList) {
								availableCouponMap.put(grantedCouponDTO.getId(), grantedCouponDTO);
							}
						}
					}

				}

				// 优惠券合法性校验
				List<UsedCouponDTO> usedCouponDTOs = orderDTO.getUsedCouponDTOs();
				if (usedCouponDTOs != null) {
					for (UsedCouponDTO usedCouponDTO : usedCouponDTOs) {// 用户希望使用的优惠券不在可用优惠券列表中
						if (availableCouponMap.containsKey(usedCouponDTO.getCouponId()) == false) {
							throw new TradeException(ResponseCode.BIZ_E_SPECIFIED_COUPON_UNAVAILABLE);
						}
					}
				}

				// 来放优惠券
				if (usedCouponDTOs != null) {

					long totalSave = 0L;
					int matchCount = 0;
					for (UsedCouponDTO usedCouponDTO : usedCouponDTOs) {
						if (availableCouponMap.containsKey(usedCouponDTO.getCouponId())) {
							GrantedCouponDTO grantedCouponDTO = availableCouponMap.get(usedCouponDTO.getCouponId());
							totalSave += grantedCouponDTO.getDiscountAmount();
							matchCount++;
						}
					}

					if (matchCount > 0) {// 如果匹配优惠券数大于1，则添加一条优惠记录
						OrderDiscountInfoDO orderDiscountInfoDO = new OrderDiscountInfoDO();
						orderDiscountInfoDO.setDiscountType(1);
						orderDiscountInfoDO.setDiscountCode("SYS_MARKET_TOOL_000001");// TODO
																						// 先写死
						orderDiscountInfoDO.setDiscountAmount(totalSave);
						orderDiscountInfoDO.setDiscountDesc("优惠券");
						orderDiscountInfoDOs.add(orderDiscountInfoDO);
					}
				}

			}
		}

		// 虚拟账户使用信息处理
		List<UsedWealthDTO> usedWealthDTOs = orderDTO.getUsedWealthDTOs();
		List<WealthAccountDTO> wealthAccountDTOs = settlementInfo.getWealthAccountList();
		Map<Long, WealthAccountDTO> availableWealthAccountMap = new HashMap<Long, WealthAccountDTO>();
		if (wealthAccountDTOs != null) {
			for (WealthAccountDTO wealthAccountDTO : wealthAccountDTOs) {
				availableWealthAccountMap.put(wealthAccountDTO.getId(), wealthAccountDTO);
			}
		}

		if (usedWealthDTOs != null) {
			for (UsedWealthDTO usedWealthDTO : usedWealthDTOs) {
				if (availableWealthAccountMap.containsKey(usedWealthDTO.getWealthAccountId())) {
					WealthAccountDTO wealthAccountDTO = availableWealthAccountMap
							.get(usedWealthDTO.getWealthAccountId());

					// 账户余额小于用户希望使用的额度，则提示余额不足
					if (wealthAccountDTO.getAmount().longValue() < usedWealthDTO.getAmount().longValue()) {
						throw new TradeException(ResponseCode.BIZ_E_SPECIFIED_WEALTH_BALANCE_NOT_ENOUGH);
					}

					OrderDiscountInfoDO orderDiscountInfoDO = new OrderDiscountInfoDO();
					orderDiscountInfoDO.setDiscountType(2);
					orderDiscountInfoDO.setDiscountCode("" + wealthAccountDTO.getWealthType());
					orderDiscountInfoDO.setDiscountAmount(usedWealthDTO.getAmount());
					orderDiscountInfoDO.setDiscountDesc("虚拟账户");
					orderDiscountInfoDOs.add(orderDiscountInfoDO);
				} else {// 用户希望使用的虚拟账户不在可用虚拟账户列表中
					throw new TradeException(ResponseCode.BIZ_E_SPECIFIED_WEALTH_UNAVAILABLE);
				}
			}
		}


		return orderDiscountInfoDOs;
	}

	private List<OrderItemDTO> getGiftToOrderItems(
			List<com.mockuai.marketingcenter.common.domain.dto.MarketItemDTO> giftList, String appKey)
					throws TradeException {

		if (null != giftList && giftList.size() > 0) {

			List<OrderItemDTO> giftlist = new ArrayList<OrderItemDTO>();

			for (MarketItemDTO marketitemDTO : giftList) {
				List<Long> skuIds = new ArrayList<Long>();
				skuIds.add(marketitemDTO.getItemSkuId());

				List<ItemSkuDTO> itemSkus = this.itemManager.queryItemSku(skuIds, marketitemDTO.getSellerId(),
						appKey);
				if (itemSkus == null | itemSkus.size() == 0) {
					log.error("itemSku is null : " + skuIds + "," + marketitemDTO.getSellerId());
					throw new TradeException(ResponseCode.BIZ_E_RECORD_NOT_EXIST, "itemSku doesn't exist");
				}
				ItemSkuDTO sku = itemSkus.get(0);
				OrderItemDTO orderItemDTO = new OrderItemDTO();
				orderItemDTO.setItemId(marketitemDTO.getItemId());
				orderItemDTO.setSellerId(marketitemDTO.getSellerId());
				orderItemDTO.setItemSkuId(marketitemDTO.getItemSkuId());
				orderItemDTO.setItemName(marketitemDTO.getItemName());
				orderItemDTO.setItemImageUrl(marketitemDTO.getIconUrl());
				orderItemDTO.setItemSkuDesc(sku.getSkuCode());
				orderItemDTO.setUnitPrice(0L);
				if (null != marketitemDTO.getNumber()) {
					orderItemDTO.setNumber(marketitemDTO.getNumber());
				} else {
					orderItemDTO.setNumber(1);
				}

				giftlist.add(orderItemDTO);
			}

			return giftlist;
		}
		return null;
	}

	@Override
	public List<OrderDiscountInfoDO> getSettlementDiscountInfo(boolean hasSubOrder, OrderDTO orderDTO,
			List<OrderItemDTO> giftList, String appkey) throws TradeException {
		// TODO Auto-generated method stub
		SettlementInfo settlement = getSettlementInfo(orderDTO.getUserId(), orderDTO.getOrderItems(),orderDTO.getOrderConsigneeDTO().getConsigneeId(), appkey);

		List<DiscountInfo> discountInfos = getDiscountInfos(settlement);
		//优惠券
		List<DiscountInfo> directDiscountList = settlement.getDirectDiscountList();
		
		List<OrderDiscountInfoDO> orderDiscountInfoDOs = new ArrayList<OrderDiscountInfoDO>();

		orderDiscountInfoDOs.addAll(processDiscount(hasSubOrder, orderDTO, discountInfos, settlement));

		orderDiscountInfoDOs.addAll(processDirectDiscount(hasSubOrder, directDiscountList));
		
		List<OrderItemDTO> genGiftList = getGiftToOrderItems(settlement.getGiftList(), appkey);
		if(null!=genGiftList)
			giftList.addAll(genGiftList);

		return orderDiscountInfoDOs;
	}
	
	
	@Override
	public OrderDiscountInfoDO genMemberDiscount(SettlementInfo settlementInfo, long userId,String bizCode) throws TradeException {
		printIntoService(log,userId+"genMemberDiscount",settlementInfo,"");
		long memberDiscountAmt = settlementInfo.getMemberDiscountAmount();
		if(memberDiscountAmt>0){
			OrderDiscountInfoDO orderDiscountDO = new OrderDiscountInfoDO();
			orderDiscountDO.setBizCode(bizCode);
			orderDiscountDO.setUserId(userId);
			orderDiscountDO.setDiscountType(3);
			orderDiscountDO.setDiscountAmount(memberDiscountAmt);
			orderDiscountDO.setDiscountCode("3");
			orderDiscountDO.setDiscountDesc("会员折扣");
			return orderDiscountDO;
		}
		return null;
	}
    @Override
    public StatisticsActivityInfoDTO queryActivityOrder(OrderDiscountInfoDO orderDiscountInfoDO){
       return orderDiscountInfoDAO.queryActivityOrder(orderDiscountInfoDO);
    }
    @Override
    public List<SaleRankDTO> querySaleRank(Long activityId){
        return orderDiscountInfoDAO.querySaleRank(activityId);
    }


}
