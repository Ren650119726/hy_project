package com.mockuai.usercenter.client;

import com.mockuai.usercenter.common.api.Response;
import com.mockuai.usercenter.common.dto.*;
public interface UserBrowseLogClient {
	/**
	 * 根据用户id查询访问记录信息
	 * 
	 * @author csy
	 * @param appKey 
	 * @Date 2016-05-21
	 */
    Response<UserBrowseLogDTO> getBrowseLogByUserId(Long userId, String appKey);
}
