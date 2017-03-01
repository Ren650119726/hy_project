package com.mockuai.distributioncenter.mop.api.action.seller;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.mockuai.distributioncenter.common.api.BaseRequest;
import com.mockuai.distributioncenter.common.api.Response;
import com.mockuai.distributioncenter.common.constant.ActionEnum;
import com.mockuai.distributioncenter.common.domain.dto.SellerConfigDTO;
import com.mockuai.distributioncenter.common.domain.dto.SellerDTO;
import com.mockuai.distributioncenter.common.domain.qto.SellerConfigQTO;
import com.mockuai.distributioncenter.mop.api.action.BaseAction;
import com.mockuai.distributioncenter.mop.api.domain.MopSellerDTO;
import com.mockuai.distributioncenter.mop.api.util.MopApiUtil;
import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.constant.MopRespCode;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.mop.common.service.action.Request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by duke on 16/5/18.
 */
public class GetSellerAction extends BaseAction {
    private static final Logger log = LoggerFactory.getLogger(GetSellerAction.class);

    public MopResponse execute(Request request) {
    	String userIdStr = (String) request.getParam("user_id");
        String appKey = (String) request.getParam("app_key");
        if (StringUtils.isBlank(userIdStr)) {
            return new MopResponse(MopRespCode.P_E_PARAM_ISNULL, "userId is null");
        }

        Long userId = Long.parseLong(userIdStr);

        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setParam("userId", userId);
        baseRequest.setParam("appKey", appKey);
        baseRequest.setCommand(ActionEnum.GET_SELLER_AND_INVITER_BY_USER_ID.getActionName());
        Response<SellerDTO> response = getDistributionService().execute(baseRequest);
        if (!response.isSuccess()) {
            log.error("get seller error, err: {}", response.getMessage());
            return new MopResponse(MopRespCode.S_E_SERVICE_ERROR, response.getMessage());
        }

        SellerDTO sellerDTO = response.getModule();
        MopSellerDTO mopSellerDTO = MopApiUtil.getMopSellerDTO(sellerDTO);

        baseRequest = new BaseRequest();
        baseRequest.setParam("sellerConfigQTO", new SellerConfigQTO());
        baseRequest.setParam("appKey", appKey);
        baseRequest.setCommand(ActionEnum.QUERY_SELLER_CONFIG.getActionName());
        Response<List<SellerConfigDTO>> listResponse = getDistributionService().execute(baseRequest);
        if (!response.isSuccess()) {
            log.error("get seller config error, err: {}", listResponse.getMessage());
            return new MopResponse(MopRespCode.S_E_SERVICE_ERROR, listResponse.getMessage());
        }

        mopSellerDTO.setLevelList(listResponse.getModule());

        return new MopResponse(mopSellerDTO);
    }

    public String getName() {
        return "/dist/seller/get";
    }
    
    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.AUTH_LOGIN;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_GET;
    }
}
