package com.mockuai.virtualwealthcenter.mop.api.action;

import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.constant.MopRespCode;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.virtualwealthcenter.common.api.BaseRequest;
import com.mockuai.virtualwealthcenter.common.api.Request;
import com.mockuai.virtualwealthcenter.common.api.Response;
import com.mockuai.virtualwealthcenter.common.domain.dto.WealthAccountDTO;
import com.mockuai.virtualwealthcenter.common.util.MopApiUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.mockuai.virtualwealthcenter.common.constant.ActionEnum.QUERY_WEALTH_ACCOUNT_BATCH;

/**
 * Created by edgar.zr on 12/21/15.
 */
public class ListWealthAccountBatch extends BaseAction {

    private static final Logger LOGGER = LoggerFactory.getLogger(GrantVirtualWealth.class.getName());
    private static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public MopResponse execute(com.mockuai.mop.common.service.action.Request request) {
        String typeStr = (String) request.getParam("wealth_type");
        String offsetStr = (String) request.getParam("offset");
        String countStr = (String) request.getParam("count");
        String startTimeStr = (String) request.getParam("start_mtime");
        String endTimeStr = (String) request.getParam("end_mtime");
        String appKey = (String) request.getParam("app_key");

        Integer type = null;
        Integer offset;
        Integer count;
        Long startTime = null;
        Long endTime = null;
        Date date;
        try {
            if (StringUtils.isNotBlank(typeStr)) {
                int typeValue = Integer.valueOf(typeStr);
                if (typeValue > 0) {
                    type = typeValue;
                }
            }
            offset = Integer.valueOf(offsetStr);
            count = Integer.valueOf(countStr);
            SimpleDateFormat format = new SimpleDateFormat(DATETIME_FORMAT);

            if (StringUtils.isNotBlank(startTimeStr)) {
                date = format.parse(startTimeStr);
                startTime = date.getTime();
            }
            if (StringUtils.isNotBlank(endTimeStr)) {
                date = format.parse(endTimeStr);
                endTime = date.getTime();
            }
        } catch (Exception e) {
            LOGGER.error("error of parsing, offsetStr : {}, countStr : {}, typeStr : {}",
                    offsetStr, countStr, typeStr, e);
            return new MopResponse(MopRespCode.P_E_PARAM_FORMAT_INVALID);
        }

        Request marketReq = new BaseRequest();
        marketReq.setCommand(QUERY_WEALTH_ACCOUNT_BATCH.getActionName());
        marketReq.setParam("wealthType", type);
        marketReq.setParam("offset", offset);
        marketReq.setParam("count", count);
        marketReq.setParam("startTime", startTime);
        marketReq.setParam("endTime", endTime);
        marketReq.setParam("appKey", appKey);
        Response marketResp = getVirtualWealthService().execute(marketReq);

        MopResponse response;
        if (marketResp.isSuccess()) {
            Map data = new HashMap();
            List<WealthAccountDTO> wealthAccountDTOs = (List<WealthAccountDTO>) marketResp.getModule();
            data.put("wealth_account_list", MopApiUtil.genMopWealthAccountDTOList(wealthAccountDTOs));
            data.put("total_count", marketResp.getTotalCount());
            response = new MopResponse(data);
        } else {
            return new MopResponse(marketResp.getResCode(), marketResp.getMessage());
        }

        return response;
    }

    public String getName() {
        return "/marketing/wealth_account/all/list";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.AUTH_LOGIN;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_GET;
    }
}