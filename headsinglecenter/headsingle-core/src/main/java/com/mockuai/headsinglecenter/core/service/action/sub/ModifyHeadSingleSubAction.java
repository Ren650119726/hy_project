package com.mockuai.headsinglecenter.core.service.action.sub;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mockuai.headsinglecenter.common.api.HeadSingleResponse;
import com.mockuai.headsinglecenter.common.constant.ActionEnum;
import com.mockuai.headsinglecenter.common.constant.ResponseCode;
import com.mockuai.headsinglecenter.common.domain.dto.HeadSingleSubDTO;
import com.mockuai.headsinglecenter.core.exception.HeadSingleException;
import com.mockuai.headsinglecenter.core.manager.HeadSingleSubManager;
import com.mockuai.headsinglecenter.core.service.RequestContext;
import com.mockuai.headsinglecenter.core.service.action.Action;
import com.mockuai.headsinglecenter.core.util.HeadSingleUtils;
/**
 * 
 */
@Service
public class ModifyHeadSingleSubAction implements Action {
	@Resource
    private HeadSingleSubManager headSingleSubManager;
    
    @SuppressWarnings("rawtypes")
	public HeadSingleResponse execute(RequestContext context) throws HeadSingleException {
    	HeadSingleSubDTO headSubDTO = (HeadSingleSubDTO) context.getRequest().getParam("headSingleSubDTO");
    	
    	if(null == headSubDTO){
    		return new HeadSingleResponse(ResponseCode.ADD_HEADSINGLE_NULL, "修改首单立减信息为空");
    	}
    	
    	HeadSingleSubDTO headSingleSubDTO = headSingleSubManager.modifyHeadSingleSub(headSubDTO);

        return HeadSingleUtils.getSuccessResponse(headSingleSubDTO);
    }

    public String getName() {
        return ActionEnum.MODIFY_HEADSINGLE_SUB.getActionName();
    }
}