package com.mockuai.marketingcenter.mop.api.action;

import com.mockuai.marketingcenter.common.api.BaseRequest;
import com.mockuai.marketingcenter.common.api.Request;
import com.mockuai.marketingcenter.common.api.Response;
import com.mockuai.marketingcenter.common.domain.dto.ActivityCouponDTO;
import com.mockuai.marketingcenter.common.domain.dto.mop.ActivityCouponUidDTO;
import com.mockuai.marketingcenter.common.util.MopApiUtil;
import com.mockuai.marketingcenter.mop.api.util.JsonUtil;
import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.constant.MopRespCode;
import com.mockuai.mop.common.service.action.MopResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.mockuai.marketingcenter.common.constant.ActionEnum.GET_ACTIVITY_COUPON_LIST_BY_IDS;

/**
 * Created by edgar.zr on 3/03/2016.
 */
public class GetActivityCouponListByIds extends BaseAction {

    public MopResponse execute(com.mockuai.mop.common.service.action.Request request) {

        String activityCouponUidListStr = (String) request.getParam("activity_coupon_uid_list");
        String appKey = (String) request.getParam("app_key");

        Request marketReq = new BaseRequest();

        marketReq.setCommand(GET_ACTIVITY_COUPON_LIST_BY_IDS.getActionName());
        List<String> activityCouponUidStrList;
        try {
            activityCouponUidStrList = JsonUtil.parseJson(activityCouponUidListStr, List.class);
        } catch (Exception e) {
            return new MopResponse(MopRespCode.P_E_PARAM_IS_INVALID, "activity_coupon_uid_list is invalid");
        }
        List<ActivityCouponUidDTO> activityCouponUidList = MopApiUtil.parseActivityCouponUidList(activityCouponUidStrList);
        List<Long> activityCouponIdList = new ArrayList<Long>();

        for (ActivityCouponUidDTO activityCouponUidDTO : activityCouponUidList) {
            activityCouponIdList.add(activityCouponUidDTO.getActivityCouponId());
        }

        marketReq.setParam("activityCouponIdList", activityCouponIdList);
        marketReq.setParam("appKey", appKey);

        Response marketResp = getMarketingService().execute(marketReq);

        MopResponse response;
        if (marketResp.isSuccess()) {
            List<ActivityCouponDTO> activityCouponDTOs = (List<ActivityCouponDTO>) marketResp.getModule();
            Map data = new HashMap();
            data.put("activity_coupon_list", MopApiUtil.genMopActivityCouponDTOList(activityCouponDTOs));
            response = new MopResponse(data);
        } else {
            return new MopResponse(marketResp.getResCode(), marketResp.getMessage());
        }

        return response;

    }

    public String getName() {
        return "/marketing/activity_coupon/list";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.NO_AUTH;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_GET;
    }
}
