package com.mockuai.distributioncenter.mop.api.action.seller;

import com.mockuai.distributioncenter.common.api.BaseRequest;
import com.mockuai.distributioncenter.common.api.Response;
import com.mockuai.distributioncenter.common.constant.ActionEnum;
import com.mockuai.distributioncenter.common.domain.dto.HkProtocolDTO;
import com.mockuai.distributioncenter.common.domain.qto.HkProtocolQTO;
import com.mockuai.distributioncenter.mop.api.action.BaseAction;
import com.mockuai.distributioncenter.mop.api.domain.MopHkProtocolDTO;
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
 * Created by lizg on 2016/10/24.
 */
public class GetHkPosterityAction extends BaseAction {

    private static final Logger log = LoggerFactory.getLogger(GetHkPosterityAction.class);

    @Override
    public MopResponse execute(Request request) {
       // Long userId = (Long) request.getAttribute("user_id");
        String userId = (String)request.getParam("user_id");
        String appKey = (String) request.getParam("app_key");
        String proModel = (String) request.getParam("pro_model");

        if (null == userId) {
            return new MopResponse(MopRespCode.P_E_PARAM_ISNULL, "userId is null");
        }

        if (null == proModel) {
            return new MopResponse(MopRespCode.P_E_PARAM_ISNULL, "proModel is null");
        }
        HkProtocolQTO hkProtocolQTO = new HkProtocolQTO();
        hkProtocolQTO.setProModel(Integer.parseInt(proModel));
        hkProtocolQTO.setUserId(Long.parseLong(userId));
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setParam("hkProtocolQTO", hkProtocolQTO);
        baseRequest.setParam("appKey", appKey);
        baseRequest.setCommand(ActionEnum.QUERY_HK_PROTOCOL.getActionName());
        Response<List<HkProtocolDTO>> response = getDistributionService().execute(baseRequest);
        if (!response.isSuccess()) {
            log.error("get posterity error, errMsg: {}", response.getMessage());
            return new MopResponse(MopRespCode.S_E_SERVICE_ERROR, response.getMessage());
        }
        log.info("[{}] response modele :{}",response.getModule());
        Map<String, Object> map = new HashMap<String, Object>();
        if (null == response.getModule()) {
            List<MopHkProtocolDTO> hkProtocolDTOs = null;
            map.put("posterity_list", hkProtocolDTOs);
            return new MopResponse(map);
        }else {
            map.put("posterity_list", MopApiUtil.getMopHkPosterityDTOs(response.getModule()));
            return new MopResponse(map);
        }
    }

    @Override
    public String getName() {
        return "/get/protocol";
    }

    @Override
    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.NO_AUTH;
    }

    @Override
    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_GET;
    }
}
