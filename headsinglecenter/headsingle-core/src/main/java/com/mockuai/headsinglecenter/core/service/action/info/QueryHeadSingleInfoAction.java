package com.mockuai.headsinglecenter.core.service.action.info;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mockuai.headsinglecenter.common.api.HeadSingleResponse;
import com.mockuai.headsinglecenter.common.constant.ActionEnum;
import com.mockuai.headsinglecenter.core.exception.HeadSingleException;
import com.mockuai.headsinglecenter.core.manager.HeadSingleInfoManager;
import com.mockuai.headsinglecenter.core.service.RequestContext;
import com.mockuai.headsinglecenter.core.service.action.Action;
import com.mockuai.headsinglecenter.core.util.HeadSingleUtils;
/**
 * 
 */
@Service
public class QueryHeadSingleInfoAction implements Action {
	private static final Logger log = LoggerFactory.getLogger(QueryHeadSingleInfoAction.class);

	@Resource
    private HeadSingleInfoManager headSingleInfoManager;
    
    @SuppressWarnings("rawtypes")
	public HeadSingleResponse execute(RequestContext context) throws HeadSingleException {
    	String terminalType = (String) context.getRequest().getParam("terminalType");
    	Date payTimeStart = (Date) context.getRequest().getParam("payTimeStart");
    	Date payTimeEnd = (Date) context.getRequest().getParam("payTimeEnd");
    	
    	if(StringUtils.isBlank(terminalType)){
    		log.error("terminalType is null by query head single info");
    	}
    	
    	if(null == payTimeStart){
    		log.error("payTimeStart is null by query head single info");
    	}

    	if(null == payTimeEnd){
    		log.error("payTimeEnd is null by query head single info");
    	}
    	
    	List<Long>  orderIdList = headSingleInfoManager.queryOrderIdList(terminalType, payTimeStart, payTimeEnd);

        return HeadSingleUtils.getSuccessResponse(orderIdList);
    }

    public String getName() {
        return ActionEnum.QUERY_HEADSINGLE_INFO.getActionName();
    }
}