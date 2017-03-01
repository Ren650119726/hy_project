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

public class AddConsigneeAction extends BaseAction{

	public MopResponse execute(Request request) {
        Long userId = (Long)request.getAttribute("user_id");
		String src = (String)request.getParam("consignee");
		String appKey = (String)request.getParam("app_key");
		MopConsigneeDTO consignee = JsonUtil.parseJson(src, MopConsigneeDTO.class);

		UserConsigneeDTO dto = new UserConsigneeDTO();
		dto.setCountryCode(consignee.getCountryCode());
		dto.setProvinceCode(consignee.getProvinceCode());
		dto.setCityCode(consignee.getCityCode());
		dto.setAreaCode(consignee.getAreaCode());
		dto.setTownCode(consignee.getTownCode());
		dto.setAddress(consignee.getAddress());
		dto.setConsignee(consignee.getConsignee());
		dto.setMobile(consignee.getMobile());
		dto.setIdCardNo(consignee.getIdCardNo());
		if(consignee.getIsDefault() != null){
			dto.setIsDefault(consignee.getIsDefault()? 1:0);//该字段允许不传
		}
		dto.setUserId(userId);
		
		com.mockuai.usercenter.common.api.Request userReq = new BaseRequest();
		userReq.setParam("consigneeDTO", dto);
		userReq.setParam("appKey", appKey);
		userReq.setCommand(ActionEnum.ADD_CONSIGNEE.getActionName());
		Response<UserConsigneeDTO> userResp = this.getUserDispatchService().execute(userReq);
		// 底层返回ConsigneeDTO对象 返回给app调用方 只需要 uid
		if(userResp.getCode() != ResponseCode.REQUEST_SUCCESS.getValue() || userResp.getModule() ==null){
			return new MopResponse(userResp.getCode(),userResp.getMessage());
		}else{
			UserConsigneeDTO returnDto = userResp.getModule();
			String uid = MopApiUtil.genUid(returnDto.getUserId(),userResp.getModule().getId());
			MopConsigneeDTO mopDto =new MopConsigneeDTO();
			mopDto.setConsigneeUid(uid);
			return new MopResponse(mopDto);
		}
	}

	public String getName() {
		// TODO Auto-generated method stub
		return "/user/consignee/add";
	}

	public ActionAuthLevel getAuthLevel() {
		// TODO Auto-generated method stub
		return ActionAuthLevel.AUTH_LOGIN;
	}

	/**
	 * FIXME 这里由于之前暴露出去的时候是NO_LIMIT,导致现在洋东西android客户端使用了post方式，
	 * FIXME 所以这里只能保持NO_LIMIT
	 * @return
	 */
	public HttpMethodLimit getMethodLimit() {
		return HttpMethodLimit.NO_LIMIT;
	}
	
	//测试解析json
	public static void main(String args[]){
		String s = "{id=1,consignee_uid=\"11-1\",country_id=5,province_id=3,city_id=7,area_id=1,town_id=9,address=\"ajfldfjd\",mobile=\"8888\",consignee=\"8888\",is_default=true}";
		MopConsigneeDTO consignee = JsonUtil.parseJson(s, MopConsigneeDTO.class);
		System.out.println(consignee.getIsDefault());
		//{user_id=11,country=5,province=3,city=7,area=1,town=9,is_default=false,consignee="8888",address="8888",mobile_no="15000138777",phone_no="0793-5888888"}
	}
}
