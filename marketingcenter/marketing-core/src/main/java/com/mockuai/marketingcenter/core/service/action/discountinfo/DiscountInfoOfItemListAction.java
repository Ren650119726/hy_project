package com.mockuai.marketingcenter.core.service.action.discountinfo;

import com.mockuai.marketingcenter.common.api.MarketingResponse;
import com.mockuai.marketingcenter.common.constant.ActionEnum;
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
import com.mockuai.marketingcenter.core.util.JsonUtil;
import com.mockuai.marketingcenter.core.util.MarketPreconditions;
import com.mockuai.marketingcenter.core.util.MarketingUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 商品列表关联的优惠信息
 * <p/>
 * Created by edgar.zr on 7/19/2016.
 */
@Service
public class DiscountInfoOfItemListAction extends TransAction {

	private static final Logger LOGGER = LoggerFactory.getLogger(DiscountInfoOfItemListAction.class);

	@Autowired
	private TimeLimitManager timeLimitManager;
	
	@Autowired
	private ComponentHelper componentHelper;
	@Resource
	private LimitedPurchaseManager limitedPurchaseManager;	

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected MarketingResponse doTransaction(RequestContext context) throws MarketingException {
		List<MarketItemDTO> marketItemDTOs = (List<MarketItemDTO>) context.getRequest().getParam("marketItemDTOs");
		String appKey = (String) context.get("appKey");
		String bizCode = (String) context.get("bizCode");

		MarketPreconditions.checkNotNull(marketItemDTOs, "marketItemDTOs");

		List<DiscountInfo> discountInfos = new ArrayList<>();

		LOGGER.info("marketItemDTOs : {}", JsonUtil.toJson(marketItemDTOs));

		// 符合的满减送活动
		componentHelper.execute(LinkActivityWithItemList.wrapParams(marketItemDTOs, bizCode,
				null, discountInfos, appKey));
		
		Iterator<DiscountInfo> iterator = discountInfos.iterator();
		DiscountInfo discountInfo;	
		
		
		/*//排除限时购类型商品
		while (iterator.hasNext()) {
			discountInfo = iterator.next();
			if (!discountInfo.getActivity().getToolCode().equals(ToolType.TIME_RANGE_DISCOUNT.getCode())) {
				iterator.remove();
			}
		}*/
		
		while (iterator.hasNext()) {
			discountInfo = iterator.next();
			if (!discountInfo.getActivity().getToolCode().equals(ToolType.COMPOSITE_TOOL.getCode())) {
				iterator.remove();
			}
		}
		//限时购的处理
		getLimitActivitys(marketItemDTOs, discountInfos);
		//限时购的处理
		LOGGER.info("marketItemDTO discountInfos: {}", JsonUtil.toJson(discountInfos));

		return MarketingUtils.getSuccessResponse(discountInfos);
	}
	
	/**
	 * 得到限时购活动数据
	 * 
	 * @author csy
	 * @Date 2016-10-13
	 * @return
	 * @throws MarketingException 
	 */
	private List<DiscountInfo> getLimitActivitys(List<MarketItemDTO> marketItemDTOs, List<DiscountInfo> discountInfos) throws MarketingException {		
		if(null == marketItemDTOs){
			return discountInfos;
		}	
		
		//判断商品是否存在限时购活动中(限时返回数据：1.itemtype为21 2.符合活动条件 3.List<MarketItemDTO>对应活动)
		Map<LimitedPurchaseDTO, List<MarketItemDTO>> limitActivityMap = limitedPurchaseManager.getTimeLimitOfItem(marketItemDTOs);
		
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
			
			discountInfo.setItemList(entry.getValue());
			discountInfos.add(discountInfo);
		}
		
		return discountInfos;
	}
	
	

	@Override
	public String getName() {
		return ActionEnum.DISCOUNT_INF_OF_ITEM_LITE.getActionName();
	}
}