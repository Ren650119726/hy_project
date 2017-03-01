package com.mockuai.marketingcenter.core.service.action.activity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.mockuai.marketingcenter.common.api.MarketingResponse;
import com.mockuai.marketingcenter.common.constant.ActionEnum;
import com.mockuai.marketingcenter.common.constant.ResponseCode;
import com.mockuai.marketingcenter.common.domain.dto.MarketActivityDTO;
import com.mockuai.marketingcenter.core.domain.MarketActivityDO;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.marketingcenter.core.manager.MarketActivityManager;
import com.mockuai.marketingcenter.core.service.RequestContext;
import com.mockuai.marketingcenter.core.service.action.TransAction;
import com.mockuai.marketingcenter.core.util.MarketingUtils;
import com.mockuai.marketingcenter.core.util.ModelUtil;

/**
 * Created by edgar.zr on 7/15/2016.
 */
@Controller
public class UpdateMarketActivityAction extends TransAction {

	@Autowired
	private MarketActivityManager marketActivityManager;

	@SuppressWarnings("rawtypes")
	@Override
	protected MarketingResponse doTransaction(RequestContext context) throws MarketingException {
		MarketActivityDTO marketActivityDTO = (MarketActivityDTO) context.getRequest().getParam("marketActivityDTO");
		
		if(null == marketActivityDTO){
			return new MarketingResponse(ResponseCode.PARAMETER_NULL,"marketActivityDTO数据为空");
		}
		
		//校验活动是否已经存在
		MarketActivityDO marketActivityDO = marketActivityManager.getActivity(marketActivityDTO.getId(), null);
		
		if(null == marketActivityDO){
			return new MarketingResponse(ResponseCode.BIZ_E_MARKET_ACTIVITY_NOT_EXIST,"对应活动内容不存在");
		}
		
		//发布状态(0未发布1已发布)
		if(null != marketActivityDO.getPublishStatus() && "1".equals(marketActivityDO.getPublishStatus())){
			return new MarketingResponse(ResponseCode.BIZ_E_ACTIVITY_PUBLISH_STATUS,"活动已发布不可修改");
		}

		marketActivityManager.updateActivity(ModelUtil.genMarketActivityDO(marketActivityDTO));

		return MarketingUtils.getSuccessResponse(true);
	}

	@Override
	public String getName() {
		return ActionEnum.UPDATE_MARKET_ACTIVITY.getActionName();
	}
}