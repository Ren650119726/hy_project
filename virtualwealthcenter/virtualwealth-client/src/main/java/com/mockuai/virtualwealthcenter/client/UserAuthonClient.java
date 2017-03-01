package com.mockuai.virtualwealthcenter.client;


import java.util.List;

import com.mockuai.virtualwealthcenter.common.api.Response;
import com.mockuai.virtualwealthcenter.common.domain.dto.mop.MopUserAuthonAppDTO;
import com.mockuai.virtualwealthcenter.common.domain.dto.mop.MopUserAuthonAppQTO;

public interface UserAuthonClient {

	//根据用户ID查询用户信息
	Response<MopUserAuthonAppDTO> findWithdrawalsItem(Long userId,String appKey);
	
	//根据身份证查询用户信息
	Response<MopUserAuthonAppDTO> findWithdrawalsItemByPersonalId(String authonPersonalid,String appKey);
	
	//根据用户id的集合查询用户信息
	Response<List<MopUserAuthonAppDTO>> ListFindWithdrawalsItemByUserId(List<Long> userIdList,String appKey);
	
	//根据QTO查询用户信息
	Response<List<MopUserAuthonAppDTO>> ListFindWithdrawalsItemByQto(MopUserAuthonAppQTO userQto,String appKey);
	
}
