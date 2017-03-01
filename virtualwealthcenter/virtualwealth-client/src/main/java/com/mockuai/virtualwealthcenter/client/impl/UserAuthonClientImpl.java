package com.mockuai.virtualwealthcenter.client.impl;

import java.util.List;

import javax.annotation.Resource;

import com.mockuai.virtualwealthcenter.client.UserAuthonClient;
import com.mockuai.virtualwealthcenter.common.api.BaseRequest;
import com.mockuai.virtualwealthcenter.common.api.Response;
import com.mockuai.virtualwealthcenter.common.api.VirtualWealthService;
import com.mockuai.virtualwealthcenter.common.constant.ActionEnum;
import com.mockuai.virtualwealthcenter.common.domain.dto.mop.MopUserAuthonAppDTO;
import com.mockuai.virtualwealthcenter.common.domain.dto.mop.MopUserAuthonAppQTO;

public class UserAuthonClientImpl implements UserAuthonClient{

	 @Resource
	 private VirtualWealthService virtualWealthService;
	
	
	public Response<MopUserAuthonAppDTO> findWithdrawalsItem(Long userId,String appKey) {
		BaseRequest baseRequest = new BaseRequest();
        baseRequest.setCommand(ActionEnum.USER_AUTHON_SEL.getActionName());
        baseRequest.setParam("userId",userId);
        baseRequest.setParam("appKey",appKey);

        return virtualWealthService.execute(baseRequest);
	}


	public Response<MopUserAuthonAppDTO> findWithdrawalsItemByPersonalId(
			String authonPersonalid, String appKey) {
		BaseRequest baseRequest = new BaseRequest();
        baseRequest.setCommand(ActionEnum.USER_AUTHON_PERSONALID_SEL.getActionName());
        baseRequest.setParam("authonPersonalid",authonPersonalid);
        baseRequest.setParam("appKey",appKey);
        return virtualWealthService.execute(baseRequest);
	}


	public Response<List<MopUserAuthonAppDTO>> ListFindWithdrawalsItemByUserId(
			List<Long> userIdList, String appKey) {
		BaseRequest baseRequest = new BaseRequest();
        baseRequest.setCommand(ActionEnum.USER_AUTHON_USERIDLIST_SEL.getActionName());
        baseRequest.setParam("userIdList",userIdList);
        baseRequest.setParam("appKey",appKey);
        return virtualWealthService.execute(baseRequest);
	}


	public Response<List<MopUserAuthonAppDTO>> ListFindWithdrawalsItemByQto(
			MopUserAuthonAppQTO userQto, String appKey) {
		BaseRequest baseRequest = new BaseRequest();
		baseRequest.setCommand(ActionEnum.USER_AUTHON_BYQTO_SEL.getActionName());
	    baseRequest.setParam("mopUserAuthonAppQTO",userQto);
	    baseRequest.setParam("appKey",appKey);
	    return virtualWealthService.execute(baseRequest);
	}

}
