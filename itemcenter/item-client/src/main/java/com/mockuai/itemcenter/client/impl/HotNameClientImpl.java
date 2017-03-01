package com.mockuai.itemcenter.client.impl;

import java.util.List;

import javax.annotation.Resource;

import com.mockuai.itemcenter.client.HotNameClient;
import com.mockuai.itemcenter.common.api.BaseRequest;
import com.mockuai.itemcenter.common.api.ItemService;
import com.mockuai.itemcenter.common.api.Request;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.domain.dto.HotNameDTO;

/**
 *   
 * @author huangsiqian
 * @version 2016年9月21日 上午11:00:38 
 */

public class HotNameClientImpl implements HotNameClient {

	@Resource
	private ItemService itemService;

	public Response<List<HotNameDTO>> getHotNameList(HotNameDTO hotNameDTO,
			String appKey) {
		Request request = new BaseRequest();
		request.setParam("hotNameDTO", hotNameDTO);
		request.setParam("appKey", appKey);
		request.setCommand(ActionEnum.HOTNAME_LIST.getActionName());
		return  itemService.execute(request);
	}

	public Response<Boolean> updateHotName(HotNameDTO hotNameDTO, String appKey) {
		Request request = new BaseRequest();
		request.setParam("hotNameDTO", hotNameDTO);
		request.setParam("appKey", appKey);
		request.setCommand(ActionEnum. DELETE_HOTNAME.getActionName());
		return  itemService.execute(request);
	}

	public Response<Boolean> insertHotName(HotNameDTO hotNameDTO, String appKey) {
		Request request = new BaseRequest();
		request.setParam("hotNameDTO", hotNameDTO);
		request.setParam("appKey", appKey);
		request.setCommand(ActionEnum.ADD_HOTNAME.getActionName());
		return  itemService.execute(request);
	}

	public Response<Boolean> modifySerialNumber(Long id,String climb,
			String appKey) {
		Request request = new BaseRequest();
		request.setParam("id", id);
		request.setParam("climb", climb);
		request.setParam("appKey",appKey);
		request.setCommand(ActionEnum. MODIFILED_SORT_HOTNAME.getActionName());
		return  itemService.execute(request);
	}



}
