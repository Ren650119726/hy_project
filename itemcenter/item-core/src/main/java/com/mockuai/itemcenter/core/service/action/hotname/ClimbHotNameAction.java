package com.mockuai.itemcenter.core.service.action.hotname;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.HotNameDTO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.HotNameManager;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.service.action.TransAction;
import com.mockuai.itemcenter.core.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 *   修改序号
 * @author huangsiqian
 * @version 2016年9月19日 下午6:27:44 
 */
@Service
public class ClimbHotNameAction extends TransAction{
	private static final Logger log = LoggerFactory.getLogger(AddHotNameAction.class);
	@Resource
	private HotNameManager hotNameManager;

	@Override
	public String getName() {
		return ActionEnum.MODIFILED_SORT_HOTNAME.getActionName();
	}

	@Override
	protected ItemResponse doTransaction(RequestContext context)
			throws ItemException {
		ItemResponse response = null;
		ItemRequest request = context.getRequest();
		Long id = (Long)request.getParam("id");
		String climb = request.getString("climb");

		HotNameDTO  currentDTO= hotNameManager.getHotNameById(id);
		if(currentDTO == null){
			return ResponseUtil.getErrorResponse(ResponseCode.SYS_E_SERVICE_EXCEPTION,"传入id不存在");
		}
		HotNameDTO turnDTO =   hotNameManager.queryClimbObj(climb,currentDTO.getIndexSort());
		Long currentIndexSort = currentDTO.getIndexSort();
		Long turnIndexSort = turnDTO.getIndexSort();

		currentDTO.setIndexSort(turnIndexSort);
		turnDTO.setIndexSort(currentIndexSort);

		try{

			Boolean flag1 = hotNameManager.updateHotName(currentDTO);
			Boolean flag2 = hotNameManager.updateHotName(turnDTO);
			if( !( flag1 && flag2) ){
				ResponseUtil.getErrorResponse(ResponseCode.SYS_E_SERVICE_EXCEPTION,"更新数据不存在");
			}
			return ResponseUtil.getSuccessResponse();
		}catch(ItemException e){
			response = ResponseUtil.getErrorResponse(e.getCode(), e.getMessage());
			log.error("do action:" + request.getCommand() + " occur Exception:" + e.getMessage(), e);
			return response;
		}
	}

}
