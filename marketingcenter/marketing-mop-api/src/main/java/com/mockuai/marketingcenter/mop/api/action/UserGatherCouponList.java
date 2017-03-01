package com.mockuai.marketingcenter.mop.api.action;

import com.mockuai.marketingcenter.common.api.BaseRequest;
import com.mockuai.marketingcenter.common.api.Request;
import com.mockuai.marketingcenter.common.api.Response;
import com.mockuai.marketingcenter.common.constant.UserCouponStatus;
import com.mockuai.marketingcenter.common.domain.dto.GrantedCouponGatherDTO;
import com.mockuai.marketingcenter.common.domain.qto.GrantedCouponQTO;
import com.mockuai.marketingcenter.common.util.MopApiUtil;
import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.service.action.MopResponse;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.mockuai.marketingcenter.common.constant.ActionEnum.QUERY_USER_GATHER_COUPON;

/**
 * Created by zengzhangqiang on 5/26/15.
 */
public class UserGatherCouponList extends BaseAction {
    public MopResponse execute(com.mockuai.mop.common.service.action.Request request) {
        Long userId = (Long) request.getAttribute("user_id");
        String statusStr = (String) request.getParam("status");
        String appKey = (String) request.getParam("app_key");

        Request marketReq = new BaseRequest();
        marketReq.setCommand(QUERY_USER_GATHER_COUPON.getActionName());
        GrantedCouponQTO grantedCouponQTO = new GrantedCouponQTO();
        grantedCouponQTO.setReceiverId(userId);

        if (StringUtils.isNotBlank(statusStr)) {
            grantedCouponQTO.setStatus(Integer.valueOf(statusStr));
        } else {
            //默认查询未使用优惠券
            grantedCouponQTO.setStatus(UserCouponStatus.UN_USE.getValue());
        }

        marketReq.setParam("grantedCouponQTO", grantedCouponQTO);
        marketReq.setParam("appKey", appKey);
        Response<List<GrantedCouponGatherDTO>> marketResp = getMarketingService().execute(marketReq);
        MopResponse response;
        if (marketResp.isSuccess()) {
            Map data = new HashMap();
            data.put("gather_coupon_list", MopApiUtil.genMopGrantedCouponGatherDTOList(marketResp.getModule()));
            data.put("total_count", marketResp.getTotalCount());
            response = new MopResponse(data);
        } else {
            return new MopResponse(marketResp.getResCode(), marketResp.getMessage());
        }

        return response;
    }

    public String getName() {
        return "/marketing/user/gather_coupon/list";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.AUTH_LOGIN;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_GET;
    }
}