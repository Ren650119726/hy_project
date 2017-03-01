package com.mockuai.marketingcenter.mop.api.action;

import com.google.gson.reflect.TypeToken;
import com.mockuai.marketingcenter.common.api.BaseRequest;
import com.mockuai.marketingcenter.common.api.Response;
import com.mockuai.marketingcenter.common.domain.dto.DiscountInfo;
import com.mockuai.marketingcenter.common.domain.dto.mop.ParamMarketItemDTO;
import com.mockuai.marketingcenter.common.util.MopApiUtil;
import com.mockuai.marketingcenter.mop.api.util.JsonUtil;
import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.mop.common.service.action.Request;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.mockuai.marketingcenter.common.constant.ActionEnum.ACTIVITY_COUPON_AVAILABLE;

/**
 * Created by edgar.zr on 11/5/15.
 * <p/>
 * 判断指定优惠券是否能在指定订单中使用
 */
public class ActivityCouponAvailable extends BaseAction {

    public MopResponse execute(Request request) {
        String orderItemListStr = (String) request.getParam("order_item_list");
        String couponUid = (String) request.getParam("coupon_uid");
        Long userId = (Long) request.getAttribute("user_id");
        String appKey = (String) request.getParam("app_key");

        java.lang.reflect.Type type = new TypeToken<List<ParamMarketItemDTO>>() {
        }.getType();
        List<ParamMarketItemDTO> paramMarketItemDTOs = JsonUtil.parseJson(orderItemListStr, type);

        com.mockuai.marketingcenter.common.api.Request marketReq = new BaseRequest();
        marketReq.setCommand(ACTIVITY_COUPON_AVAILABLE.getActionName());
        marketReq.setParam("itemList", MopApiUtil.genMarketingItemDTOList(paramMarketItemDTOs));
        marketReq.setParam("couponId", MopApiUtil.parseCouponId(couponUid));
        marketReq.setParam("userId", userId);
        marketReq.setParam("appKey", appKey);
        Response marketResp = getMarketingService().execute(marketReq);

        MopResponse response;
        if (marketResp.isSuccess()) {
            DiscountInfo discountInfo = (DiscountInfo) marketResp.getModule();
            Map<String, Object> data = new HashMap<String, Object>();
            if (discountInfo != null) {
                data.put("discount_info", MopApiUtil.genMopDiscountInfo(discountInfo));
            } else {
                data.put("discount_info", null);
            }
            response = new MopResponse(data);
        } else {
            return new MopResponse(marketResp.getResCode(), marketResp.getMessage());
        }
        return response;
    }

    public String getName() {
        return "/marketing/activity_coupon/is_available";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.AUTH_LOGIN;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_POST;
    }
}