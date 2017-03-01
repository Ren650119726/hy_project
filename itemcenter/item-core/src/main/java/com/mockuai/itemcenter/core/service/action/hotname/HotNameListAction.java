package com.mockuai.itemcenter.core.service.action.hotname;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.domain.dto.HotNameDTO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.HotNameManager;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.service.action.TransAction;
import com.mockuai.itemcenter.core.util.ResponseUtil;

/**
 *   展示热搜词列表
 * @author huangsiqian
 * @version 2016年9月19日 下午6:33:45 
 */
@Service
public class HotNameListAction extends TransAction{
	
	@Resource
	private HotNameManager hotNameManager;
	@Override
	public String getName() {
		return ActionEnum.HOTNAME_LIST.getActionName();
		
	}

	@Override
	protected ItemResponse doTransaction(RequestContext context)
			throws ItemException {
		ItemResponse response = null;
		HotNameDTO hotNameDTO = new HotNameDTO();
		try{
				List<HotNameDTO> list= hotNameManager.getHotNameList(hotNameDTO);
				if(null!=list){
					response = ResponseUtil.getSuccessResponse(list);
				}else{
					response = ResponseUtil.getSuccessResponse(new ArrayList());
				}
		}catch(ItemException e){
			response = ResponseUtil.getErrorResponse(e.getCode(), e.getMessage());
		}
		return response;
	}

}
