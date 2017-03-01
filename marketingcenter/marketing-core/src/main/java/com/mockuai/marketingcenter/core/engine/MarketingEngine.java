package com.mockuai.marketingcenter.core.engine;

import com.alibaba.dubbo.common.json.JSONObject;
import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.mockuai.marketingcenter.common.constant.CommonItemEnum;
import com.mockuai.marketingcenter.common.constant.ToolType;
import com.mockuai.marketingcenter.common.domain.dto.DiscountInfo;
import com.mockuai.marketingcenter.common.domain.dto.MarketItemDTO;
import com.mockuai.marketingcenter.common.domain.dto.SettlementInfo;
import com.mockuai.marketingcenter.core.engine.activity.ActivityEngine;
import com.mockuai.marketingcenter.core.engine.component.ComponentHelper;
import com.mockuai.marketingcenter.core.engine.component.impl.ItemTotalPrice;
import com.mockuai.marketingcenter.core.engine.component.impl.LinkActivityWithItemList;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.marketingcenter.core.util.Context;
import com.mockuai.marketingcenter.core.util.JsonUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class MarketingEngine implements ApplicationContextAware {

	private static final Logger LOGGER = LoggerFactory.getLogger(MarketingEngine.class.getName());

	@Autowired
	private ActivityEngine activityEngine;
	@Autowired
	private ComponentHelper componentHelper;
	private ApplicationContext applicationContext;

	/**
	 * 计算指定商品满足的优惠信息列表
	 *
	 * @param settlementInfo
	 * @param itemList
	 * @param userId
	 * @param bizCode
	 * @param appKey
	 * @return
	 * @throws MarketingException
	 */
	public void execute(SettlementInfo settlementInfo,
	                    List<MarketItemDTO> itemList,
	                    Long userId,
	                    String bizCode,
	                    String appKey,List<DiscountInfo> limitDiscountList) throws MarketingException {

		//入参检查
		if ((itemList == null) || (itemList.isEmpty())) {
			return;
		}

		Context context = new Context();
		context.setAttribute("grantedCouponManager", applicationContext.getBean("grantedCouponManager"));
		context.setParam("userId", userId);
		context.setParam("bizCode", bizCode);
		context.setParam("appKey", appKey);

		List<DiscountInfo> discountInfos = new ArrayList<>();
		List<DiscountInfo> validDiscountInfos = new ArrayList<>();

		componentHelper.execute(LinkActivityWithItemList.wrapParams(itemList,
				bizCode, userId, discountInfos, appKey));

		for (Iterator<DiscountInfo> iterator = discountInfos.iterator(); iterator.hasNext(); ) {
			if (iterator.next().getActivity().getToolCode().equals(ToolType.BARTER_TOOL.getCode())) {
				iterator.remove();
			}
		}

		DiscountInfo tempDiscountInfo;
		// timeLimit > 0 表示有限时购 or 满减送存在
		int timeLimit = 0;
		if (!settlementInfo.getDirectDiscountList().isEmpty() || !discountInfos.isEmpty() && discountInfos.get(0).getActivity().getToolCode().equals(ToolType.COMPOSITE_TOOL.getCode())) {
			timeLimit = 1;
		}
		// 1:无门槛 2:有门槛 3:有门槛+无门槛  优惠券排序方案条件
		// 计算每个活动下的优惠活动 
		// TODO 如果存在有限时购商品优惠/满减送优惠,优惠券只能是 commonItem = 1 的优惠券
		for (DiscountInfo discountInfo : discountInfos) {			
			context.setParam("discountInfo", discountInfo);
			
			// 根据是否为 单品优惠活动 计算相应的优惠门槛
			if (discountInfo.getItemList().isEmpty()) {
				// 每次单独计算,有可能商品的价格由于满减送被减掉
				context.setParam("itemTotalPrice", componentHelper.execute(ItemTotalPrice.wrapParams(itemList)));
				context.setParam("itemList", itemList);				
			} else {
				context.setParam("itemTotalPrice", componentHelper.execute(ItemTotalPrice.wrapParams(discountInfo.getItemList())));				
			}
			if (timeLimit > 0 && discountInfo.getActivity().getCommonItem().intValue() == CommonItemEnum.COMMON_ITEM.getValue()) {
				continue;
			}
			tempDiscountInfo = activityEngine.execute(context);
			if (tempDiscountInfo == null) {
				continue;
			}
			validDiscountInfos.add(tempDiscountInfo);
			// 分为有券优惠活动和无券优惠活动
			if (discountInfo.getActivity().getCouponMark().intValue() == 1) { //优惠券
				// TODO 限时购商品参与的结算，只要限时购有一个活动不能使用优惠券整单结算都不能使用
				if(CollectionUtils.isNotEmpty(limitDiscountList)){
					for(int i=0;i< limitDiscountList.size();i++){
						if(limitDiscountList.get(i).getActivity().getCouponMark()==0){
							break;
						}
						if(i==limitDiscountList.size()-1 ){
							settlementInfo.getDiscountInfoList().add(discountInfo);
						}
					}
				}else{
					settlementInfo.getDiscountInfoList().add(discountInfo);
				}
			} else {// 满减送
				settlementInfo.getDirectDiscountList().add(discountInfo);
				settlementInfo.setDiscountAmount(settlementInfo.getDiscountAmount() + discountInfo.getDiscountAmount());
				settlementInfo.setSavedPostage(settlementInfo.getSavedPostage() + discountInfo.getSavedPostage());
				settlementInfo.getGiftList().addAll(discountInfo.getGiftList());
				settlementInfo.setFreePostage(settlementInfo.isFreePostage() || discountInfo.isFreePostage());
			}		
		}		

		if (settlementInfo.getDiscountInfoList().isEmpty()) {
			return;
		}
		
		
	}

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
}