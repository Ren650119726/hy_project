package com.mockuai.itemcenter.core.service.action.hotname;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 *   删除热搜词
 * @author huangsiqian
 * @version 2016年9月19日 下午6:14:11 
 */
@Service
public class DeleteHotNameAction extends TransAction{
	private static final Logger log = LoggerFactory.getLogger(AddHotNameAction.class);
	@Resource
	private HotNameManager hotNameManager;
	@Override
	public String getName() {
		return ActionEnum. DELETE_HOTNAME.getActionName();
	}

	@Override
	protected ItemResponse doTransaction(RequestContext context)
			throws ItemException {
		ItemResponse response = null;
		ItemRequest request = context.getRequest();
		HotNameDTO hotNameDTO  = (HotNameDTO) request.getParam("hotNameDTO");
		hotNameDTO.setDeleteMark(1);
		try{
		Boolean flag = hotNameManager.updateHotName(hotNameDTO);
		return ResponseUtil.getSuccessResponse(flag);
		}catch(ItemException e){
			response = ResponseUtil.getErrorResponse(e.getCode(), e.getMessage());
			log.error("do action:" + request.getCommand() + " occur Exception:" + e.getMessage(), e);
			return response;
		}
	}

}
