package com.mockuai.distributioncenter.mop.api.action.seller;

import com.mockuai.distributioncenter.common.api.BaseRequest;
import com.mockuai.distributioncenter.common.api.Response;
import com.mockuai.distributioncenter.common.constant.ActionEnum;
import com.mockuai.distributioncenter.common.domain.dto.SellerDTO;
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

/**
 * Created by duke on 16/5/18.
 */
public class GetInviterAction extends BaseAction {
    private static final Logger log = LoggerFactory.getLogger(GetInviterAction.class);

    public MopResponse execute(Request request) {
        String appKey = (String) request.getParam("app_key");
        String inviterCode = (String) request.getParam("inviter_code");

        if (inviterCode == null) {
            log.error("inviterCode is null");
            return new MopResponse(MopRespCode.P_E_PARAM_ISNULL, "inviterCode is null");
        }

        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setParam("inviterCode", inviterCode);
        baseRequest.setParam("appKey", appKey);
        baseRequest.setCommand(ActionEnum.GET_SELLER_BY_INVITER_CODE.getActionName());
        Response<SellerDTO> response = getDistributionService().execute(baseRequest);
        if (!response.isSuccess()) {
            log.error("get inviter error, errMsg: {}", response.getMessage());
            return new MopResponse(MopRespCode.S_E_SERVICE_ERROR, response.getMessage());
        }
        MopSellerDTO mopSellerDTO = MopApiUtil.getMopSellerDTO(response.getModule());
        return new MopResponse(mopSellerDTO);
    }

    public String getName() {
        return "/dist/inviter/get";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.NO_AUTH;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_GET;
    }
}
