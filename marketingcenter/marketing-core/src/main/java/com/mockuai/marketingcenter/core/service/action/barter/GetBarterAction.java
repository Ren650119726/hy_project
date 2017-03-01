package com.mockuai.marketingcenter.core.service.action.barter;

import com.mockuai.marketingcenter.common.api.MarketingResponse;
import com.mockuai.marketingcenter.common.constant.ActionEnum;
import com.mockuai.marketingcenter.common.constant.ActivityScope;
import com.mockuai.marketingcenter.common.constant.ResponseCode;
import com.mockuai.marketingcenter.common.domain.dto.MarketActivityDTO;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.marketingcenter.core.manager.ActivityItemManager;
import com.mockuai.marketingcenter.core.manager.MarketActivityManager;
import com.mockuai.marketingcenter.core.manager.PropertyManager;
import com.mockuai.marketingcenter.core.service.RequestContext;
import com.mockuai.marketingcenter.core.service.action.TransAction;
import com.mockuai.marketingcenter.core.util.ModelUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * Created by edgar.zr on 12/2/15.
 */
@Service
public class GetBarterAction extends TransAction {

    @Autowired
    private MarketActivityManager marketActivityManager;

    @Autowired
    private ActivityItemManager activityItemManager;

    @Autowired
    private PropertyManager propertyManager;

    @Override
    protected MarketingResponse doTransaction(RequestContext context) throws MarketingException {

        Long activityId = (Long) context.getRequest().getParam("activityId");
        Long creatorId = (Long) context.getRequest().getParam("creatorId");
        String bizCode = (String) context.get("bizCode");

        if (activityId == null) {
            return new MarketingResponse(ResponseCode.PARAMETER_NULL, "activityId is null");
        }

        if (creatorId == null) {
            return new MarketingResponse(ResponseCode.PARAMETER_NULL, "creatorId is null");
        }

        if (StringUtils.isBlank(bizCode)) {
            return new MarketingResponse(ResponseCode.PARAMETER_NULL, "bizCode is null");
        }

        MarketActivityDTO activityDTO = ModelUtil.genMarketActivityDTO(marketActivityManager.getActivity(activityId, bizCode));
        if (ActivityScope.SCOPE_ITEM.getValue() == activityDTO.getScope().intValue()) {
            activityDTO.setActivityItemList(ModelUtil.genActivityItemDTOList(activityItemManager.queryActivityItem(activityId, creatorId, bizCode)));
        }
        propertyManager.fillUpMarketWithProperty(Arrays.asList(activityDTO), bizCode);

        return new MarketingResponse(activityDTO);
    }

    @Override
    public String getName() {
        return ActionEnum.GET_BARTER.getActionName();
    }
}