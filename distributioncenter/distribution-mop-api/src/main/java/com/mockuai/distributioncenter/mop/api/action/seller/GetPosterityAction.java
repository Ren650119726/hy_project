package com.mockuai.distributioncenter.mop.api.action.seller;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.mockuai.distributioncenter.common.api.BaseRequest;
import com.mockuai.distributioncenter.common.api.Response;
import com.mockuai.distributioncenter.common.constant.ActionEnum;
import com.mockuai.distributioncenter.common.domain.dto.SellerDTO;
import com.mockuai.distributioncenter.common.domain.qto.SellerQTO;
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
 * Created by duke on 16/5/18.
 */
public class GetPosterityAction extends BaseAction {
    private static final Logger log = LoggerFactory.getLogger(GetPosterityAction.class);

    public MopResponse execute(Request request) {
        Long userId = (Long) request.getAttribute("user_id");
        String appKey = (String) request.getParam("app_key");
        String levelIdStr = (String) request.getParam("level_id");
        String offsetStr = (String) request.getParam("offset");
        String countStr = (String) request.getParam("count");
        String realName = (String) request.getParam("real_name");

        if (userId == null) {
            log.error("userId is null");
            return new MopResponse(MopRespCode.P_E_PARAM_ISNULL, "userId is null");
        }

        Long levelId = null;
        if (!StringUtils.isBlank(levelIdStr)) {
            levelId = Long.parseLong(levelIdStr);
        }

        Long offset = 0L;
        if (offsetStr != null) {
            offset = Long.parseLong(offsetStr);
        }

        Long count = 25L;
        if (countStr != null) {
            count = Long.parseLong(countStr);
        }

        SellerQTO sellerQTO = new SellerQTO();
        sellerQTO.setParentId(userId);
        sellerQTO.setLevelId(levelId);
        sellerQTO.setRealName(realName);
        sellerQTO.setOffset(offset);
        sellerQTO.setCount(count.intValue());
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setParam("sellerQTO", sellerQTO);
        baseRequest.setParam("appKey", appKey);
        baseRequest.setCommand(ActionEnum.GET_POSTERITY_SELLER.getActionName());
        Response<List<SellerDTO>> response = getDistributionService().execute(baseRequest);
        if (!response.isSuccess()) {
            log.error("get posterity error, errMsg: {}", response.getMessage());
            return new MopResponse(MopRespCode.S_E_SERVICE_ERROR, response.getMessage());
        }

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("seller_list", MopApiUtil.getMopSellerDTOs(response.getModule()));
        map.put("total_count", response.getTotalCount());
        return new MopResponse(map);
    }

    public String getName() {
        return "/dist/seller/posterity/list";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.AUTH_LOGIN;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_GET;
    }
}
