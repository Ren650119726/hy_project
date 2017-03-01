package com.mockuai.distributioncenter.mop.api.action.summary;

import com.mockuai.distributioncenter.common.api.BaseRequest;
import com.mockuai.distributioncenter.common.api.Response;
import com.mockuai.distributioncenter.common.constant.ActionEnum;
import com.mockuai.distributioncenter.common.domain.dto.SummaryDTO;
import com.mockuai.distributioncenter.mop.api.action.BaseAction;
import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.constant.MopRespCode;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.mop.common.service.action.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by duke on 16/5/26.
 */
public class GetSummaryAction extends BaseAction {
    private static final Logger log = LoggerFactory.getLogger(GetSummaryAction.class);

    public MopResponse execute(Request request) {
        Long userId = (Long) request.getAttribute("user_id");
        String appKey = (String) request.getParam("app_key");
        String startTimeStr = (String) request.getParam("start_time");
        String endTimeStr = (String) request.getParam("end_time");

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        if (startTimeStr == null) {
            log.error("startTime is null");
            return new MopResponse(MopRespCode.P_E_PARAM_ISNULL, "start_time is null");
        }
        Date startTime = null;
        Date endTime = null;
        try {
            startTime = df.parse(startTimeStr);
        } catch (ParseException e) {
            log.error("parse start time error");
            return new MopResponse(MopRespCode.S_E_SERVICE_ERROR, "parse start time error");
        }

        try {
            endTime = df.parse(endTimeStr);
        } catch (ParseException e) {
            log.error("parse end time error");
            return new MopResponse(MopRespCode.S_E_SERVICE_ERROR, "parse end time error");
        }

        if (endTimeStr == null) {
            log.error("endTime is null");
            return new MopResponse(MopRespCode.P_E_PARAM_ISNULL, "end_time is null");
        }

        // 处理结束时间边界问题
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(endTime);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        endTime = calendar.getTime();

        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setParam("userId", userId);
        baseRequest.setParam("startTime", startTime);
        baseRequest.setParam("endTime", endTime);
        baseRequest.setParam("appKey", appKey);
        baseRequest.setCommand(ActionEnum.GET_SELLER_SUMMARY.getActionName());
        Response<List<SummaryDTO>> response = getDistributionService().execute(baseRequest);
        if (response.isSuccess()) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("summary_list", response.getModule());
            return new MopResponse(map);
        } else {
            log.error("get summary error, errMsg: {}", response.getMessage());
            return new MopResponse(response.getCode(), response.getMessage());
        }
    }

    public String getName() {
        return "/dist/summary/get";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.AUTH_LOGIN;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_GET;
    }
}
