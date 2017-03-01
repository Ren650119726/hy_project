package com.mockuai.headsinglecenter.client.impl;

import javax.annotation.Resource;

import com.mockuai.headsinglecenter.client.HeadSingleSubClient;
import com.mockuai.headsinglecenter.common.api.BaseRequest;
import com.mockuai.headsinglecenter.common.api.HeadSingleService;
import com.mockuai.headsinglecenter.common.api.Request;
import com.mockuai.headsinglecenter.common.api.Response;
import com.mockuai.headsinglecenter.common.constant.ActionEnum;
import com.mockuai.headsinglecenter.common.domain.dto.HeadSingleSubDTO;


/**
 * Created by csy on 12/2/15.
 */
public class HeadSingleSubClientImpl implements HeadSingleSubClient {
	@Resource
	private HeadSingleService headSingleService;
	
	/**
	 * 查询首单立减信息
	 * 
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Response<HeadSingleSubDTO> queryHeadSingleSub(Long id, String appKey) {
		Request request = new BaseRequest();
        request.setParam("id", id);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.QUERY_HEADSINGLE_SUB.getActionName());
        Response<HeadSingleSubDTO> response = headSingleService.execute(request);
        return response;
	}
	
	/**
	 * 新增首单立减信息
	 * 
	 * @author csy
	 * @Date 2016-07-19
	 */
	@SuppressWarnings("unchecked")
	public Response<HeadSingleSubDTO> addHeadSingleSub(HeadSingleSubDTO headSingleSubDTO, String appKey) {
		Request request = new BaseRequest();
        request.setParam("headSingleSubDTO", headSingleSubDTO);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.ADD_HEADSINGLE_SUB.getActionName());
        Response<HeadSingleSubDTO> response = headSingleService.execute(request);
        return response;
	}
	
	/**
	 * 修改首单立减信息
	 * 
	 * @author csy
	 * @Date 2016-07-20
	 */
	@SuppressWarnings("unchecked")
	public Response<HeadSingleSubDTO> modifyHeadSingleSub(HeadSingleSubDTO headSingleSubDTO, String appKey) {
		Request request = new BaseRequest();
        request.setParam("headSingleSubDTO", headSingleSubDTO);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.MODIFY_HEADSINGLE_SUB.getActionName());
        Response<HeadSingleSubDTO> response = headSingleService.execute(request);
        return response;
	}
	
	/**
	 * 查询首单立减信息
	 * 
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Response<HeadSingleSubDTO> queryHeadSingleSubById(Long id, String appKey) {
		Request request = new BaseRequest();
        request.setParam("id", id);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.QUERY_HEADSINGLE_SUBBYID.getActionName());
        Response<HeadSingleSubDTO> response = headSingleService.execute(request);
        return response;
	}
}