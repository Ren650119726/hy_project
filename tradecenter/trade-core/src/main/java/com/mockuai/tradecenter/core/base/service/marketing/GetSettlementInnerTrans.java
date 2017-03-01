//package com.mockuai.tradecenter.core.base.service.marketing;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import javax.annotation.Resource;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Service;
//
//import com.alibaba.fastjson.JSONObject;
//import com.mockuai.itemcenter.common.domain.dto.ItemSkuDTO;
//import com.mockuai.marketingcenter.common.constant.MarketLevel;
//import com.mockuai.marketingcenter.common.domain.dto.ActivityItemDTO;
//import com.mockuai.marketingcenter.common.domain.dto.DiscountInfo;
//import com.mockuai.marketingcenter.common.domain.dto.GrantedCouponDTO;
//import com.mockuai.marketingcenter.common.domain.dto.MarketActivityDTO;
//import com.mockuai.marketingcenter.common.domain.dto.MarketItemDTO;
//import com.mockuai.marketingcenter.common.domain.dto.SettlementInfo;
//import com.mockuai.virtualwealthcenter.common.domain.dto.WealthAccountDTO;
//import com.mockuai.tradecenter.common.constant.ResponseCode;
//import com.mockuai.tradecenter.common.domain.OrderItemDTO;
//import com.mockuai.tradecenter.common.domain.UsedCouponDTO;
//import com.mockuai.tradecenter.common.domain.UsedWealthDTO;
//import com.mockuai.tradecenter.common.util.MoneyUtil;
//import com.mockuai.tradecenter.core.base.TradeInnerOper;
//import com.mockuai.tradecenter.core.base.request.InnerRequest;
//import com.mockuai.tradecenter.core.base.result.SettlementResponse;
//import com.mockuai.tradecenter.core.base.result.TradeOperResult;
//import com.mockuai.tradecenter.core.domain.OrderDiscountInfoDO;
//import com.mockuai.tradecenter.core.exception.TradeException;
//import com.mockuai.tradecenter.core.manager.ItemManager;
//import com.mockuai.tradecenter.core.manager.MarketingManager;
//import com.mockuai.tradecenter.core.manager.OrderDiscountInfoManager;
//
//@Service
//public class GetSettlementInnerTrans implements TradeInnerOper {
//	private static final Logger log = LoggerFactory.getLogger(GetSettlementInnerTrans.class);
//
//	@Resource
//	private MarketingManager marketingManager;
//
//	@Resource
//	private ItemManager itemManager;
//
//	@Resource
//	private OrderDiscountInfoManager orderDiscountInfoManager;
//
//	@Override
//	public TradeOperResult doTransaction(InnerRequest req) throws TradeException {
//
//
//		Long consigneeId = req.getSettlementRequest().getConsigneeId();
//
//		SettlementInfo settlement = getSettlementInfo(req.getUserId(), req.getSettlementRequest().getOrderItems(),
//				consigneeId, req.getAppKey());
//
//		Long deliveryFee = settlement.getDeliveryFee();
//
//		List<OrderDiscountInfoDO> orderDiscountInfoDOs = null;
//
//		List<DiscountInfo> discountInfos = getDiscountInfos(settlement);
//
//		List<DiscountInfo> directDiscountList = settlement.getDirectDiscountList();
//
//		List<OrderDiscountInfoDO> directOrderDiscountDOs = null;
//		Map<Long,ActivityItemDTO> activityItemMap = new HashMap<Long,ActivityItemDTO>();
//		// 代表满减送
//		directOrderDiscountDOs = processDirectDiscount(directDiscountList,activityItemMap);
//		// 代表优惠券 和虚拟货币
//		orderDiscountInfoDOs = processDiscount(req.getSettlementRequest().getUsedCouponDTOs(),
//				req.getSettlementRequest().getUsedWealthDTOs(),discountInfos, settlement);
//
//		orderDiscountInfoDOs.addAll(directOrderDiscountDOs);
//
//		//TODO 拍卖保证金、营销商品没有会员折扣
////		OrderDiscountInfoDO memberDiscount = orderDiscountInfoManager.genMemberDiscount(settlement, req.getUserId(), req.getBizCode());
////		if(null!=memberDiscount)
////			orderDiscountInfoDOs.add(memberDiscount);
//
//		List<OrderItemDTO> giftList = getGiftToOrderItems(settlement.getGiftList(), req.getAppKey());
//
//		 TradeOperResult tradeOperResult = new TradeOperResult();
//	        SettlementResponse settlementResponse = new SettlementResponse();
//	        settlementResponse.setOrderDiscountInfoDOList(orderDiscountInfoDOs);
//	        settlementResponse.setDeliveryFee(deliveryFee);
//	        settlementResponse.setActivityItemMap(activityItemMap);
//	        settlementResponse.setGiftOrderItemDTOList(giftList);
//	        tradeOperResult.setSettlementResponse(settlementResponse);
//	        tradeOperResult.setSuccess(true);
//			return tradeOperResult;
//	}
//
//	private SettlementInfo getSettlementInfo(Long userId, List<OrderItemDTO> orderItems, Long consigneeId,
//			String appkey) throws TradeException {
//		SettlementInfo settlementInfo = null;
//		if (null == consigneeId) {
//			settlementInfo = marketingManager.getSettlementInfo(userId, orderItems, null, appkey);
//		} else {
//			settlementInfo = marketingManager.getSettlementInfo(userId, orderItems, consigneeId, appkey);
//		}
//
//		return settlementInfo;
//	}
//
//	private List<DiscountInfo> getDiscountInfos(SettlementInfo settlementInfo) throws TradeException {
//		if (settlementInfo == null) {
//			return Collections.EMPTY_LIST;
//		}
//		// 营销活动优惠信息处理
//		List<DiscountInfo> discountInfos = settlementInfo.getDiscountInfoList();
//		return discountInfos;
//	}
//
//	/**
//	 * 处理订单优惠信息，并生成订单对象(代表满减送)
//	 *
//	 * @param orderDTO
//	 * @param itemMap
//	 * @param itemSkuMap
//	 * @return
//	 */
//	private List<OrderDiscountInfoDO> processDirectDiscount(
//			List<DiscountInfo> discountInfos,
//			Map<Long,ActivityItemDTO> activityItemMap) throws TradeException {
//
//		List<OrderDiscountInfoDO> orderDiscountInfoDOs = new ArrayList<OrderDiscountInfoDO>();
//
//			// 营销活动优惠信息处理
//			if (discountInfos != null && discountInfos.isEmpty() == false) {
//				for (DiscountInfo discountInfo : discountInfos) {
//					MarketActivityDTO marketActivityDTO = discountInfo.getActivity();
//
//					if (marketActivityDTO.getToolCode().equals("ReachMultipleReduceTool")) { // 代表满减
//						// if(marketActivityDTO.getCouponMark() ==
//						// 0){//不需要优惠券的活动
//						OrderDiscountInfoDO orderDiscountInfoDO = new OrderDiscountInfoDO();
//						orderDiscountInfoDO.setDiscountType(1);
//						orderDiscountInfoDO.setDiscountCode(marketActivityDTO.getToolCode());
//						orderDiscountInfoDO.setDiscountAmount(discountInfo.getDiscountAmount());
//						orderDiscountInfoDO.setDiscountDesc("满减送");
//						orderDiscountInfoDOs.add(orderDiscountInfoDO);
//					}
//
//					if(marketActivityDTO.getToolCode().equals("BarterTool")){
//						List<ActivityItemDTO> activityItemDTOList = marketActivityDTO.getTargetItemList();
//						if(null!=activityItemDTOList){
//							for(ActivityItemDTO activityItemDTO:activityItemDTOList){
//								activityItemDTO.setUnitPrice(discountInfo.getDiscountAmount());
//								activityItemMap.put(activityItemDTO.getItemSkuId(), activityItemDTO);
//							}
//						}
//					}
//
//				}
//
//			}
//
//		// TODO 本次订单所满足的优惠信息校验
//
//		return orderDiscountInfoDOs;
//	}
//
//	/**
//	 * 处理订单优惠信息，并生成订单对象
//	 *
//	 * @param orderDTO
//	 * @param itemMap
//	 * @param itemSkuMap
//	 * @return
//	 */
//	private List<OrderDiscountInfoDO> processDiscount(
//			List<UsedCouponDTO> usedCouponDTOs,
//			List<UsedWealthDTO> usedWealthDTOs,
//			List<DiscountInfo> discountInfos,
//			SettlementInfo settlementInfo) throws TradeException {
//
//		List<OrderDiscountInfoDO> orderDiscountInfoDOs = new ArrayList<OrderDiscountInfoDO>();
//
//			// 营销活动优惠信息处理
//			if (discountInfos != null && discountInfos.isEmpty() == false) {
//				Map<Long, GrantedCouponDTO> availableCouponMap = new HashMap<Long, GrantedCouponDTO>();
//				Map<Long,Integer> couponMallMarkMap = new HashMap<Long, Integer>();
//				for (DiscountInfo discountInfo : discountInfos) {
//					MarketActivityDTO marketActivityDTO = discountInfo.getActivity();
//
//					if (marketActivityDTO.getToolCode().equals("SYS_MARKET_TOOL_000001")) {
//
//						List<GrantedCouponDTO> availableCouponList = discountInfo.getAvailableCoupons();
//						if (availableCouponList != null) {
//							for (GrantedCouponDTO grantedCouponDTO : availableCouponList) {
//								availableCouponMap.put(grantedCouponDTO.getId(), grantedCouponDTO);
//
//								Integer mallMark = 0;
//								if(marketActivityDTO.getLevel().intValue()==MarketLevel.BIZ_LEVEL.getValue()){
//									mallMark = 1;
//								}
//
//								couponMallMarkMap.put(grantedCouponDTO.getId(), mallMark);
//
//							}
//						}
//					}
//
//				}
//
//				log.info("availableCouponMap :"+JSONObject.toJSONString(availableCouponMap));
//				log.info("usedCouponDTOs :"+JSONObject.toJSONString(usedCouponDTOs));
//				// 优惠券合法性校验
//				if (usedCouponDTOs != null) {
//					for (UsedCouponDTO usedCouponDTO : usedCouponDTOs) {// 用户希望使用的优惠券不在可用优惠券列表中
//						if (availableCouponMap.containsKey(usedCouponDTO.getCouponId()) == false) {
//							throw new TradeException(ResponseCode.BIZ_E_SPECIFIED_COUPON_UNAVAILABLE);
//						}
//					}
//				}
//
//				// 来放优惠券  只能用一张
//				if (usedCouponDTOs != null) {
//
//					long totalSave = 0L;
//					int matchCount = 0;
//					int mallDiscountMark = 0 ;
//					for (UsedCouponDTO usedCouponDTO : usedCouponDTOs) {
//						if (availableCouponMap.containsKey(usedCouponDTO.getCouponId())) {
//							GrantedCouponDTO grantedCouponDTO = availableCouponMap.get(usedCouponDTO.getCouponId());
//							totalSave += grantedCouponDTO.getDiscountAmount();
//							matchCount++;
//						}
//
//						mallDiscountMark = couponMallMarkMap.get(usedCouponDTO.getCouponId());
//
//					}
//
//					if (matchCount > 0) {// 如果匹配优惠券数大于1，则添加一条优惠记录
//						OrderDiscountInfoDO orderDiscountInfoDO = new OrderDiscountInfoDO();
//						orderDiscountInfoDO.setDiscountType(1);
//						orderDiscountInfoDO.setDiscountCode("SYS_MARKET_TOOL_000001");// TODO
//																						// 先写死
//						orderDiscountInfoDO.setDiscountAmount(totalSave);
//						orderDiscountInfoDO.setDiscountDesc("优惠券");
//
//						orderDiscountInfoDO.setMallMark(mallDiscountMark);
//
//						orderDiscountInfoDOs.add(orderDiscountInfoDO);
//					}
//				}
//
//			}
//
//		// 虚拟账户使用信息处理
//		List<WealthAccountDTO> wealthAccountDTOs = settlementInfo.getWealthAccountList();
//		Map<Long, WealthAccountDTO> availableWealthAccountMap = new HashMap<Long, WealthAccountDTO>();
//		if (wealthAccountDTOs != null) {
//			for (WealthAccountDTO wealthAccountDTO : wealthAccountDTOs) {
//				availableWealthAccountMap.put(wealthAccountDTO.getId(), wealthAccountDTO);
//			}
//		}
//
//		if (usedWealthDTOs != null) {
//			for (UsedWealthDTO usedWealthDTO : usedWealthDTOs) {
//				if (availableWealthAccountMap.containsKey(usedWealthDTO.getWealthAccountId())) {
//					WealthAccountDTO wealthAccountDTO = availableWealthAccountMap
//							.get(usedWealthDTO.getWealthAccountId());
//
//					// 账户余额小于用户希望使用的额度，则提示余额不足
//					if (wealthAccountDTO.getAmount().longValue() < usedWealthDTO.getAmount().longValue()) {
//						throw new TradeException(ResponseCode.BIZ_E_SPECIFIED_WEALTH_BALANCE_NOT_ENOUGH);
//					}
//					OrderDiscountInfoDO orderDiscountInfoDO = new OrderDiscountInfoDO();
//					if (wealthAccountDTO.getWealthType() == 2 && null != usedWealthDTO.getAmount()) {
//						usedWealthDTO.setPoint(usedWealthDTO.getAmount());
//						Double exchangeRate = wealthAccountDTO.getExchangeRate();
//						String exchangeAmount = MoneyUtil.getFormatMoney(
//								MoneyUtil.mul(1d, MoneyUtil.mul(usedWealthDTO.getAmount() + "", exchangeRate + "")),
//								"##0");
//						usedWealthDTO.setWealthType(wealthAccountDTO.getWealthType());
//						orderDiscountInfoDO.setDiscountAmount(Long.parseLong(exchangeAmount));
//						orderDiscountInfoDO.setPoint(usedWealthDTO.getPoint());
//					} else if (wealthAccountDTO.getWealthType() == 1) {
//						orderDiscountInfoDO.setDiscountAmount(usedWealthDTO.getAmount());
//					}
//
//					orderDiscountInfoDO.setDiscountType(2);
//					orderDiscountInfoDO.setDiscountCode("" + wealthAccountDTO.getWealthType());
//
//					orderDiscountInfoDO.setDiscountDesc("虚拟账户");
//
//					orderDiscountInfoDOs.add(orderDiscountInfoDO);
//				} else {// 用户希望使用的虚拟账户不在可用虚拟账户列表中
//					throw new TradeException(ResponseCode.BIZ_E_SPECIFIED_WEALTH_UNAVAILABLE);
//				}
//			}
//		}
//
//		// TODO 本次订单所满足的优惠信息校验
//
//		return orderDiscountInfoDOs;
//	}
//
//	private List<OrderItemDTO> getGiftToOrderItems(
//			List<com.mockuai.marketingcenter.common.domain.dto.MarketItemDTO> giftList, String appKey)
//					throws TradeException {
//
//		if (null != giftList && giftList.size() > 0) {
//
//			List<OrderItemDTO> giftlist = new ArrayList<OrderItemDTO>();
//
//			for (MarketItemDTO marketitemDTO : giftList) {
//				List<Long> skuIds = new ArrayList<Long>();
//				skuIds.add(marketitemDTO.getItemSkuId());
//
//				List<ItemSkuDTO> itemSkus = itemManager.queryItemSku(skuIds, marketitemDTO.getSellerId(),
//						appKey);
//				if (itemSkus == null | itemSkus.size() == 0) {
//					log.error("itemSku is null : " + skuIds + "," + marketitemDTO.getSellerId());
//					throw new TradeException(ResponseCode.BIZ_E_RECORD_NOT_EXIST, "itemSku doesn't exist");
//				}
//				ItemSkuDTO sku = itemSkus.get(0);
//				OrderItemDTO orderItemDTO = new OrderItemDTO();
//				orderItemDTO.setItemId(marketitemDTO.getItemId());
//				orderItemDTO.setSellerId(marketitemDTO.getSellerId());
//				orderItemDTO.setItemSkuId(marketitemDTO.getItemSkuId());
//				orderItemDTO.setItemName(marketitemDTO.getItemName());
//				orderItemDTO.setItemImageUrl(marketitemDTO.getIconUrl());
//				orderItemDTO.setItemSkuDesc(sku.getSkuCode());
//				orderItemDTO.setUnitPrice(0L);
//				if (null != marketitemDTO.getNumber()) {
//					orderItemDTO.setNumber(marketitemDTO.getNumber());
//				} else {
//					orderItemDTO.setNumber(1);
//				}
//
//				giftlist.add(orderItemDTO);
//			}
//
//			return giftlist;
//		}
//		return null;
//	}
//
//}
