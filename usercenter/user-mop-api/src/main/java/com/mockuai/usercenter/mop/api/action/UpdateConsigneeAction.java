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

public class UpdateConsigneeAction extends BaseAction{

	public MopResponse execute(Request request) {
		String src = (String)request.getParam("consignee");
		String appKey = (String)request.getParam("app_key");
		MopConsigneeDTO paramDto = JsonUtil.parseJson(src, MopConsigneeDTO.class);

		UserConsigneeDTO dto = new UserConsigneeDTO();
		dto.setCountryCode(paramDto.getCountryCode());
		dto.setProvinceCode(paramDto.getProvinceCode());
		dto.setCityCode(paramDto.getCityCode());
		dto.setAreaCode(paramDto.getAreaCode());
		dto.setTownCode(paramDto.getTownCode());
		dto.setAddress(paramDto.getAddress());
		dto.setMobile(paramDto.getMobile());
		dto.setConsignee(paramDto.getConsignee());
		dto.setIdCardNo(paramDto.getIdCardNo());
		if(paramDto.getIsDefault() != null){
			dto.setIsDefault(paramDto.getIsDefault()? 1:0);//该字段允许不传
		}

		ConsigneeUidDTO uidDto = MopApiUtil.parseConsigneeUid(paramDto.getConsigneeUid());
		dto.setUserId(uidDto.getUserId());
		dto.setId(uidDto.getConsigneeId());
		
		com.mockuai.usercenter.common.api.Request userReq = new BaseRequest();
		userReq.setParam("consigneeDTO", dto);
		userReq.setParam("appKey", appKey);
		userReq.setCommand(ActionEnum.UPDATE_CONSIGNEE.getActionName());
		Response<UserConsigneeDTO> userResp = this.getUserDispatchService().execute(userReq);
		return MopApiUtil.transferResp(userResp);
	}

	public String getName() {
		return "/user/consignee/update";
	}

	public ActionAuthLevel getAuthLevel() {
		return ActionAuthLevel.AUTH_LOGIN;
	}

	public HttpMethodLimit getMethodLimit() {
		return HttpMethodLimit.ONLY_POST;
	}
	
}