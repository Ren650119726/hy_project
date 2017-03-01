package com.mockuai.distributioncenter.mop.api.action.fans;

import com.mockuai.distributioncenter.common.api.BaseRequest;
import com.mockuai.distributioncenter.common.api.Response;
import com.mockuai.distributioncenter.common.constant.ActionEnum;
import com.mockuai.distributioncenter.common.domain.dto.MyFansDTO;
import com.mockuai.distributioncenter.common.domain.qto.MyFansQTO;
import com.mockuai.distributioncenter.mop.api.action.BaseAction;
import com.mockuai.distributioncenter.mop.api.util.MopApiUtil;
import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.constant.MopRespCode;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.mop.common.service.action.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by lizg on 2016/9/1.
 */
public class MyFansAction extends BaseAction {

    private static final Logger log = LoggerFactory.getLogger(MyFansAction.class);


    public MopResponse execute(Request request) {
        Long userId = Long.valueOf((String) request.getParam("user_id"));
        Long fansId = null;
        //一级粉丝不传fans_id
        if((String)request.getParam("fans_id") != null){
            fansId = Long.valueOf((String) request.getParam("fans_id"));
        }
        String sort = (String) request.getParam("sort");
        String updown = (String) request.getParam("updown");
        String appKey = (String) request.getParam("app_key");
        String offsetStr = (String) request.getParam("offset");
        String countStr = (String) request.getParam("count");

        if (null == userId) {
            log.error("userId is null");
            return new MopResponse(MopRespCode.P_E_PARAM_ISNULL, "userId is null");
        }

        if (null == appKey) {
            log.error("appKey is null");
            return new MopResponse(MopRespCode.P_E_PARAM_ISNULL, "appKey is null");
        }
        if (null == offsetStr) {
            log.error("offset is null");
            return new MopResponse(MopRespCode.P_E_PARAM_ISNULL, "offset is null");
        }
        if (null == countStr) {
            log.error("count is null");
            return new MopResponse(MopRespCode.P_E_PARAM_ISNULL, "count is null");
        }
        if (Integer.parseInt(offsetStr) < 0) {
            log.error("offset input error");
            return new MopResponse(MopRespCode.P_E_PARAM_ISNULL, "offset input error");
        }
        if (Integer.parseInt(countStr) < 0) {
            log.error("count input error");
            return new MopResponse(MopRespCode.P_E_PARAM_ISNULL, "count input error");
        }
        if (!"1".equals(sort) && !"2".equals(sort)) {
            log.error("sort input error");
            return new MopResponse(MopRespCode.P_E_PARAM_ISNULL, "sort input error");
        }
        if (!"1".equals(updown) && !"2".equals(updown)) {
            log.error("updown input error");
            return new MopResponse(MopRespCode.P_E_PARAM_ISNULL, "updown input error");
        }

        Long offset = Long.parseLong(offsetStr);

        Long count = Long.parseLong(countStr);

        MyFansQTO myFansQTO = new MyFansQTO();
        myFansQTO.setUserId(userId);
        myFansQTO.setFansId(fansId);
        myFansQTO.setSort(Integer.parseInt(sort));
        myFansQTO.setUpdown(Integer.parseInt(updown));
        myFansQTO.setOffset(offset);
        myFansQTO.setCount(count == null ? null : count.intValue());
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setParam("appKey", appKey);
        baseRequest.setParam("myFansQTO", myFansQTO);
        baseRequest.setCommand(ActionEnum.GET_MY_FANS.getActionName());
        Response<List<MyFansDTO>> response = getDistributionService().execute(baseRequest);

        if (!response.isSuccess()) {
            log.error("get fans by userId error, msg: {}", userId, response.getMessage());
            return new MopResponse(MopRespCode.S_E_SERVICE_ERROR, response.getMessage());
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("myFans", MopApiUtil.getMyFansDTOs(response.getModule()));
        map.put("total_count", response.getTotalCount());
        return new MopResponse(map);
    }

    public String getName() {
        return "/fans/list";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.AUTH_LOGIN;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_GET;
    }
}
