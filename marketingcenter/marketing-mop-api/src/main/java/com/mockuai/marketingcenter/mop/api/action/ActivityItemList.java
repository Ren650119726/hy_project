package com.mockuai.marketingcenter.mop.api.action;

import com.mockuai.marketingcenter.common.api.BaseRequest;
import com.mockuai.marketingcenter.common.api.Request;
import com.mockuai.marketingcenter.common.api.Response;
import com.mockuai.marketingcenter.common.domain.dto.ActivityItemDTO;
import com.mockuai.marketingcenter.common.domain.dto.mop.ActivityUidDTO;
import com.mockuai.marketingcenter.common.util.MopApiUtil;
import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.service.action.MopResponse;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.mockuai.marketingcenter.common.constant.ActionEnum.ACTIVITY_ITEM_LIST;

/**
 * Created by edgar.zr on 1/20/16.
 */
public class ActivityItemList extends BaseAction {

    public MopResponse execute(com.mockuai.mop.common.service.action.Request request) {
        String activityUid = (String) request.getParam("activity_uid");
        String offsetStr = (String) request.getParam("offset");
        String countStr = (String) request.getParam("count");
        String appKey = (String) request.getParam("app_key");

        Integer offset = 0;
        Integer count = 100;
        Long activityId = null;
        try {
            if (StringUtils.isNotBlank(offsetStr))
                offset = Integer.valueOf(offsetStr);
            if (StringUtils.isNotBlank(countStr))
                count = Integer.valueOf(countStr);
        } catch (Exception e) {
            return new MopResponse("偏移量数据非法");
        }

        try {
            ActivityUidDTO activityUidDTO = MopApiUtil.parseActivityUid(activityUid);
            if (activityUidDTO != null)
                activityId = activityUidDTO.getActivityId();
        } catch (Exception e) {
            return new MopResponse("activity_uid 非法");
        }

        Request marketingRequest = new BaseRequest();
        marketingRequest.setCommand(ACTIVITY_ITEM_LIST.getActionName());

        marketingRequest.setParam("activityId", activityId);
        marketingRequest.setParam("offset", offset);
        marketingRequest.setParam("count", count);
        marketingRequest.setParam("appKey", appKey);

        Response<List<ActivityItemDTO>> response = getMarketingService().execute(marketingRequest);
        MopResponse mopResponse;
        if (response.isSuccess()) {
            Map data = new HashMap();
            data.put("item_uid_list", MopApiUtil.genItemUidList(response.getModule()));
            data.put("total_count", response.getTotalCount());
            mopResponse = new MopResponse(data);
        } else {
            return new MopResponse(response.getResCode(), response.getMessage());
        }

        return mopResponse;
    }

    public String getName() {
        return "/marketing/activity/item/list";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.NO_AUTH;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_GET;
    }
}