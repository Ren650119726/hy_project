package com.mockuai.usercenter.mop.api.action;

import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.mop.common.service.action.Request;
import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.BaseRequest;
import com.mockuai.usercenter.common.api.Response;
import com.mockuai.usercenter.common.dto.UserConsigneeDTO;
import com.mockuai.usercenter.mop.api.domain.ConsigneeUidDTO;
import com.mockuai.usercenter.mop.api.domain.MopConsigneeDTO;
import com.mockuai.usercenter.mop.api.util.JsonUtil;
import com.mockuai.usercenter.mop.api.util.MopApiUtil;

/**
 * Created by duke on 15/11/10.
 */
public class UpdateConsigneeByMapAction extends BaseAction {

    public MopResponse execute(Request request) {
        String src = (String) request.getParam("consignee");
        String appKey = (String) request.getParam("app_key");
        MopConsigneeDTO consigneeDTO = JsonUtil.parseJson(src, MopConsigneeDTO.class);

        UserConsigneeDTO userConsigneeDTO = new UserConsigneeDTO();
        userConsigneeDTO.setCity(consigneeDTO.getCity());
        userConsigneeDTO.setProvince(consigneeDTO.getProvince());
        userConsigneeDTO.setArea(consigneeDTO.getArea());
        userConsigneeDTO.setTown(consigneeDTO.getTown());
        userConsigneeDTO.setLatitude(consigneeDTO.getLatitude());
        userConsigneeDTO.setLongitude(consigneeDTO.getLongitude());
        userConsigneeDTO.setAddress(consigneeDTO.getAddress());
        userConsigneeDTO.setConsignee(consigneeDTO.getConsignee());
        userConsigneeDTO.setMobile(consigneeDTO.getMobile());
        if (consigneeDTO.getIsDefault() != null) {
            userConsigneeDTO.setIsDefault(consigneeDTO.getIsDefault()? 1 : 0);
        }
        ConsigneeUidDTO uidDTO = MopApiUtil.parseConsigneeUid(consigneeDTO.getConsigneeUid());
        userConsigneeDTO.setUserId(uidDTO.getUserId());
        userConsigneeDTO.setId(uidDTO.getConsigneeId());

        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setParam("consigneeDTO", userConsigneeDTO);
        baseRequest.setParam("appKey", appKey);
        baseRequest.setCommand(ActionEnum.UPDATE_CONSIGNEE_BY_MAP.getActionName());
        Response<UserConsigneeDTO> response = getUserDispatchService().execute(baseRequest);
        return MopApiUtil.transferResp(response);
    }

    public String getName() {
        return "/user/consignee/map/update";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.AUTH_LOGIN;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_POST;
    }
}
