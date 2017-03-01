package com.mockuai.marketingcenter.core.service.action.barter;

import com.mockuai.marketingcenter.common.api.MarketingResponse;
import com.mockuai.marketingcenter.common.constant.ActionEnum;
import com.mockuai.marketingcenter.common.constant.ActivityScope;
import com.mockuai.marketingcenter.common.constant.MarketLevel;
import com.mockuai.marketingcenter.common.constant.ResponseCode;
import com.mockuai.marketingcenter.common.constant.ToolType;
import com.mockuai.marketingcenter.common.domain.dto.MarketActivityDTO;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.marketingcenter.core.manager.MarketActivityManager;
import com.mockuai.marketingcenter.core.service.RequestContext;
import com.mockuai.marketingcenter.core.service.action.TransAction;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 创建换购
 * 店铺级别
 * <p/>
 * Created by edgar.zr on 12/2/15.
 */
@Service
public class AddBarterAction extends TransAction {

    @Autowired
    private MarketActivityManager marketActivityManager;

    @Override
    protected MarketingResponse doTransaction(RequestContext context) throws MarketingException {

        MarketActivityDTO marketActivityDTO = (MarketActivityDTO) context.getRequest().getParam("marketActivityDTO");
        String bizCode = (String) context.get("bizCode");

        //入参校验
        if (marketActivityDTO == null) {
            return new MarketingResponse(ResponseCode.PARAMETER_NULL, "marketActivityDTO is null");
        }

        marketActivityDTO.setBizCode(bizCode);

        //工具 code 不能为空
        if (StringUtils.isBlank(marketActivityDTO.getToolCode())) {
            return new MarketingResponse(ResponseCode.PARAMETER_NULL, "tool code cannot be empty");
        }

        if (!marketActivityDTO.getToolCode().equals(ToolType.BARTER_TOOL.getCode())) {
            return new MarketingResponse(ResponseCode.PARAMETER_ERROR, "the type of tool code is invalid");
        }

        //活动名称不能为空
        if (StringUtils.isBlank(marketActivityDTO.getActivityName())) {
            return new MarketingResponse(ResponseCode.PARAMETER_NULL, "the activity name cannot be empty");
        }

        if (StringUtils.isBlank(marketActivityDTO.getActivityContent())) {
            return new MarketingResponse(ResponseCode.PARAMETER_NULL, "the activity content is empty");
        }

        //活动创建者id不能为空
        if (marketActivityDTO.getCreatorId() == null) {
            return new MarketingResponse(ResponseCode.PARAMETER_NULL, "the creator of activity cannot be null");
        }

        //活动范围默认值设置
        if (marketActivityDTO.getScope() == null) {
            //默认设为全店活动
            marketActivityDTO.setScope(ActivityScope.SCOPE_SHOP.getValue());
        } else {
            //营销活动范围有效性校验
            if (ActivityScope.getScopeByValue(marketActivityDTO.getScope()) == null) {
                return new MarketingResponse(ResponseCode.BIZ_E_ACTIVITY_SCOPE_INVALID);
            }

            //如果是单品活动，则营销活动单品列表不能为空
            if (marketActivityDTO.getScope().intValue() == ActivityScope.SCOPE_ITEM.getValue()) {
                if (marketActivityDTO.getActivityItemList() == null
                        || marketActivityDTO.getActivityItemList().isEmpty()) {
                    return new MarketingResponse(ResponseCode.PARAM_E_ACTIVITY_ITEM_CANNOT_BE_NULL);
                }
            }
        }

        //活动时间校验，活动时间不能为空
        if (marketActivityDTO.getStartTime() == null || marketActivityDTO.getEndTime() == null) {
            return new MarketingResponse(ResponseCode.PARAM_E_ACTIVITY_TIME_CANNOT_BE_NULL);
        }

        // 活动时间有效性验证,只要判断结束时间是否在开始时间之前
        if (marketActivityDTO.getStartTime().after(marketActivityDTO.getEndTime())) {
            return new MarketingResponse<Long>(ResponseCode.PARAM_E_ACTIVITY_TIME_INVALID);
        }

        // 不需要券
        marketActivityDTO.setCouponMark(0);
        //活动创建者类型设置默认值
        marketActivityDTO.setCreatorType(1);
        marketActivityDTO.setLevel(MarketLevel.SHOP_LEVEL.getValue());

        marketActivityManager.addActivity(marketActivityDTO);

        return new MarketingResponse(marketActivityDTO.getId());
    }

    @Override
    public String getName() {
        return ActionEnum.ADD_BARTER.getActionName();
    }
}