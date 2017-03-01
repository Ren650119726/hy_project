package com.mockuai.headsinglecenter.core.service.action.user;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mockuai.headsinglecenter.common.api.HeadSingleResponse;
import com.mockuai.headsinglecenter.common.constant.ActionEnum;
import com.mockuai.headsinglecenter.common.constant.ResponseCode;
import com.mockuai.headsinglecenter.common.domain.dto.HeadSingleSubDTO;
import com.mockuai.headsinglecenter.core.exception.HeadSingleException;
import com.mockuai.headsinglecenter.core.manager.HeadSingleUserManager;
import com.mockuai.headsinglecenter.core.service.RequestContext;
import com.mockuai.headsinglecenter.core.service.action.Action;
import com.mockuai.headsinglecenter.core.util.HeadSingleUtils;
import com.mockuai.marketingcenter.common.domain.dto.MarketItemDTO;
/**
 * 
 */
@Service
public class JudgeHeadSingleUserAction implements Action {
	@Resource
    private HeadSingleUserManager headSingleUserManager;
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public HeadSingleResponse execute(RequestContext context) throws HeadSingleException {
    	Long userId = (Long) context.getRequest().getParam("userId");
    	String appKey = (String) context.getRequest().getParam("appKey");
    	List<MarketItemDTO> marketItems = (List<MarketItemDTO>) context.getRequest().getParam("marketItemList");
    	
    	if(null == userId){
    		return new HeadSingleResponse(ResponseCode.QUERY_HEADSINGLE_USER_NULL, "查询首单立减userId不可为空");
    	}
    	
    	if(null == appKey){
    		return new HeadSingleResponse(ResponseCode.QUERY_HEADSINGLE_USER_NULL, "查询首单立减appKey不可为空");
    	}
    	
    	if(null == marketItems || marketItems.isEmpty()){
    		return new HeadSingleResponse(ResponseCode.QUERY_HEADSINGLE_USER_NULL, "查询首单立减marketItems不可为空");
    	}
    	
    	HeadSingleSubDTO headSingleSubDTO = headSingleUserManager.queryJudgeHeadSingleUser(userId, marketItems, appKey);

        return HeadSingleUtils.getSuccessResponse(headSingleSubDTO);
    }

    public String getName() {
        return ActionEnum.QUERY_HEADSINGLE_JUDGE_USER.getActionName();
    }
}