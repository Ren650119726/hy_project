package com.mockuai.marketingcenter.core.service.action.discountinfo;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mockuai.marketingcenter.common.api.MarketingResponse;
import com.mockuai.marketingcenter.common.constant.ActionEnum;
import com.mockuai.marketingcenter.common.constant.ItemType;
import com.mockuai.marketingcenter.common.constant.ResponseCode;
import com.mockuai.marketingcenter.common.constant.ToolType;
import com.mockuai.marketingcenter.common.domain.dto.DiscountInfo;
import com.mockuai.marketingcenter.common.domain.dto.LimitedPurchaseDTO;
import com.mockuai.marketingcenter.common.domain.dto.MarketActivityDTO;
import com.mockuai.marketingcenter.common.domain.dto.MarketItemDTO;
import com.mockuai.marketingcenter.core.engine.component.ComponentHelper;
import com.mockuai.marketingcenter.core.engine.component.impl.LinkActivityWithItemList;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.marketingcenter.core.manager.LimitedPurchaseManager;
import com.mockuai.marketingcenter.core.manager.TimeLimitManager;
import com.mockuai.marketingcenter.core.service.RequestContext;
import com.mockuai.marketingcenter.core.service.action.TransAction;

/**
 * 查询购物车中商品关联的优惠信息(只关联满减送/限时购)
 * <p/>
 * Created by edgar.zr on 12/1/15.
 */
@Service
public class CartDiscountInfoListAction extends TransAction {
	private static final Logger LOGGER = LoggerFactory.getLogger(CartDiscountInfoListAction.class);

	@Autowired
	private ComponentHelper componentHelper;
	
	@Resource
	private LimitedPurchaseManager limitedPurchaseManager;
	
	@Autowired
	private TimeLimitManager timeLimitManager;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected MarketingResponse doTransaction(RequestContext context) throws MarketingException {
		List<MarketItemDTO> cartItemList = (List<MarketItemDTO>) context.getRequest().getParam("cartItemList");
		String bizCode = (String) context.get("bizCode");
		String appKey = (String) context.get("appKey");
		Long userId = (Long) context.getRequest().getParam("userId");
		LOGGER.info("get Into userId:{}",userId);
		List<DiscountInfo> discountInfos = new ArrayList<>();

		componentHelper.execute(LinkActivityWithItemList.wrapParams(cartItemList, bizCode, null, discountInfos, appKey));

		Iterator<DiscountInfo> iterator = discountInfos.iterator();
		DiscountInfo discountInfo;
		while (iterator.hasNext()) {
			discountInfo = iterator.next();
			if (!discountInfo.getActivity().getToolCode().equals(ToolType.COMPOSITE_TOOL.getCode())) {
				iterator.remove();
			}
			// 只给出父活动
			discountInfo.getActivity().setSubMarketActivityList(null);
		}

		//限时购的处理
		getLimitActivitys(cartItemList, userId, discountInfos);

		return new MarketingResponse(discountInfos);
	}
	
	/**
	 * 得到限时购活动数据
	 * 
	 * @author csy
	 * @Date 2016-10-13
	 * @return
	 * @throws MarketingException 
	 */
	private List<DiscountInfo> getLimitActivitys(List<MarketItemDTO> marketItemDTOs, Long userId, 
			List<DiscountInfo> discountInfos) throws MarketingException {		
		if(null == marketItemDTOs){
			return discountInfos;
		}
		LOGGER.info("limited get into userID:{}",userId);
		//判断商品是否存在限时购活动中(限时返回数据：1.itemtype为21 2.符合活动条件 3.List<MarketItemDTO>对应活动)
		Map<LimitedPurchaseDTO, List<MarketItemDTO>> limitActivityMap = limitedPurchaseManager.getIteminTimeLimit(marketItemDTOs, userId);
		
		if(null == limitActivityMap){
			return discountInfos;
		}
		
		// 符合的限时购活动		
		for (Map.Entry<LimitedPurchaseDTO, List<MarketItemDTO>> entry : limitActivityMap.entrySet()) {		
			DiscountInfo discountInfo = new DiscountInfo();
			MarketActivityDTO marketActivityDTO = new MarketActivityDTO();
			discountInfo.setActivity(marketActivityDTO);
			
			Map<String, Object> limitMap = timeLimitManager.limitActivityTag(entry.getKey());

			marketActivityDTO.setIcon(entry.getKey().getActivityTag());//角标
			marketActivityDTO.setId(entry.getKey().getId());//角标
			marketActivityDTO.setToolCode(ToolType.TIME_RANGE_DISCOUNT.getCode());
			marketActivityDTO.setActivityTag((String) limitMap.get("tag"));
			marketActivityDTO.setLimitTagStatus(limitMap.get("tagStatus").toString());
			marketActivityDTO.setLimitTagDate((Long) limitMap.get("tagDate"));
			marketActivityDTO.setId(entry.getKey().getId());//活动id
			//是否使用优惠券(0使用1不使用)
			if("0".equals(entry.getKey().getVoucherType().toString())){
				marketActivityDTO.setCouponMark(1);//营销使用优惠券状态(0不使用1使用)
			}else{
				marketActivityDTO.setCouponMark(0);
			}
			
			//判断限时购商品结算限购数量是否符合
			Boolean flag=timeLimitManager.judgeBuyNumGtLimitNum(entry.getValue());
			
			if(false == flag){
				throw new MarketingException(ResponseCode.PARAMETER_ERROR,"您已超过限购数，请活动结束后再购买");
			}
			
			discountInfo.setItemList(entry.getValue());
			discountInfos.add(discountInfo);
		}
		
		return discountInfos;
	}

	@Override
	public String getName() {
		return ActionEnum.CART_DISCOUNT_INFO_LIST.getActionName();
	}
}