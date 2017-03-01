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
import com.mockuai.usercenter.mop.api.domain.MopConsigneeDTO;
import com.mockuai.usercenter.mop.api.util.JsonUtil;
import com.mockuai.usercenter.mop.api.util.MopApiUtil;

/**
 * Created by duke on 15/11/6.
 */
public class AddConsigneeByMapAction extends BaseAction {

    public MopResponse execute(Request request) {
        Long userId = (Long)request.getAttribute("user_id");
        String src = (String)request.getParam("consignee");
        String appKey = (String)request.getParam("app_key");
        MopConsigneeDTO consignee = JsonUtil.parseJson(src, MopConsigneeDTO.class);

        UserConsigneeDTO dto = new UserConsigneeDTO();
        dto.setCity(consignee.getCity());
        dto.setProvince(consignee.getProvince());
        dto.setLatitude(consignee.getLatitude());
        dto.setLongitude(consignee.getLongitude());
        dto.setAddress(consignee.getAddress());
        dto.setConsignee(consignee.getConsignee());
        dto.setMobile(consignee.getMobile());
        dto.setArea(consignee.getArea());
        dto.setTown(consignee.getTown());
        if(consignee.getIsDefault() != null){
            dto.setIsDefault(consignee.getIsDefault()? 1:0);//该字段允许不传
        }
        dto.setUserId(userId);

        BaseRequest userReq = new BaseRequest();
        userReq.setParam("consigneeDTO", dto);
        userReq.setParam("appKey", appKey);
        userReq.setCommand(ActionEnum.ADD_CONSIGNEE_BY_MAP.getActionName());
        Response<UserConsigneeDTO> userResp = this.getUserDispatchService().execute(userReq);
        // 底层返回ConsigneeDTO对象 返回给app调用方 只需要 uid
        if(userResp.getCode() != ResponseCode.REQUEST_SUCCESS.getValue() || userResp.getModule() ==null){
            return new MopResponse(userResp.getCode(),userResp.getMessage());
        }else{
            UserConsigneeDTO returnDto = userResp.getModule();
            String uid = MopApiUtil.genUid(returnDto.getUserId(), userResp.getModule().getId());
            MopConsigneeDTO mopDto =new MopConsigneeDTO();
            mopDto.setConsigneeUid(uid);
            return new MopResponse(mopDto);
        }
    }

    public String getName() {
        return "/user/consignee/map/add";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.AUTH_LOGIN;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_POST;
    }
}
