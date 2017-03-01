package com.mockuai.usercenter.mop.api.action;

import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.mop.common.service.action.Request;
import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.BaseRequest;
import com.mockuai.usercenter.common.api.Response;
import com.mockuai.usercenter.common.constant.ResponseCode;
import com.mockuai.usercenter.common.dto.UserConsigneeDTO;
import com.mockuai.usercenter.mop.api.domain.ConsigneeUidDTO;
import com.mockuai.usercenter.mop.api.domain.MopConsigneeDTO;
import com.mockuai.usercenter.mop.api.util.MarkUtil;
import com.mockuai.usercenter.mop.api.util.MopApiUtil;
import org.apache.commons.lang.StringUtils;

public class GetConsigneeAction extends BaseAction {

    public MopResponse execute(Request request) {
        String consigneeUid = (String) request.getParam("consignee_uid");
        String appKey = (String) request.getParam("app_key");
        ConsigneeUidDTO dto = MopApiUtil.parseConsigneeUid(consigneeUid);
        com.mockuai.usercenter.common.api.Request userReq = new BaseRequest();
        userReq.setParam("consigneeId", dto.getConsigneeId());
        userReq.setParam("userId", dto.getUserId());
        userReq.setParam("appKey", appKey);
        userReq.setCommand(ActionEnum.GET_CONSIGNEE_BY_ID.getActionName());

        Response<UserConsigneeDTO> userResp = this.getUserDispatchService().execute(userReq);
        if (userResp.getCode() != ResponseCode.REQUEST_SUCCESS.getValue()) {
            //TODO
            return new MopResponse(userResp.getCode(), userResp.getMessage());
            //return new MopResponse(MopRespCode.S_E_SERVICE_ERROR);
        }
        UserConsigneeDTO consignee = userResp.getModule();
        if (consignee != null && StringUtils.isNotBlank(consignee.getIdCardNo())) {
            MarkUtil.markFormat(consignee.getIdCardNo(), "*", 1);
        }

        MopConsigneeDTO returnDto = MopApiUtil.genMopConsignee(consignee);
        return new MopResponse(returnDto);
    }

    public String getName() {
        return "/user/consignee/get";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.AUTH_LOGIN;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_GET;
    }
}	

