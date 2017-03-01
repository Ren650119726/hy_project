package com.mockuai.distributioncenter.mop.api.action.seller;

import com.mockuai.distributioncenter.common.api.BaseRequest;
import com.mockuai.distributioncenter.common.api.Response;
import com.mockuai.distributioncenter.common.constant.ActionEnum;
import com.mockuai.distributioncenter.common.domain.dto.HkProtocolRecordDTO;
import com.mockuai.distributioncenter.mop.api.action.BaseAction;
import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.constant.MopRespCode;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.mop.common.service.action.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by lizg on 2016/10/25.
 */
public class AddHkProtocolRecordAction extends BaseAction {

    private static final Logger log = LoggerFactory.getLogger(HkProtocolRecordAction.class);


    public MopResponse execute(Request request) {
        String userId = (String)request.getParam("user_id");
        String appKey = (String) request.getParam("app_key");

        if (userId == null) {
            return new MopResponse(MopRespCode.P_E_PARAM_ISNULL, "userId is null");
        }
        String protocolIds = (String)request.getParam("protocolIds");
        if (null == protocolIds) {
            log.error("protocolIds is null");
            return new MopResponse(MopRespCode.P_E_PARAM_ISNULL, "protocolIds is null");
        }
        log.info("[{}] protocolIds:{}",protocolIds);
        HkProtocolRecordDTO hkProtocolRecordDTO  = new HkProtocolRecordDTO();
        hkProtocolRecordDTO.setUserId(Long.parseLong(userId));
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setParam("hkProtocolRecordDTO", hkProtocolRecordDTO);
        baseRequest.setParam("protocolIds", protocolIds);
        baseRequest.setParam("appKey", appKey);
        baseRequest.setCommand(ActionEnum.ADD_HK_PROTOCOL.getActionName());
        Response<Boolean> response = getDistributionService().execute(baseRequest);
        if (!response.isSuccess()) {
            log.error("create hkProtocol error, err: {}", response.getMessage());
            return new MopResponse(MopRespCode.S_E_SERVICE_ERROR, response.getMessage());
        }
        return new MopResponse(response.getModule());
    }

    public String getName() {
        return "/add/haike/protocol";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.NO_AUTH;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_GET;
    }
}
