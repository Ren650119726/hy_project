package com.mockuai.mainweb.core.service.action.page;

import com.alibaba.fastjson.JSONObject;
import com.mockuai.mainweb.common.api.action.MainWebResponse;
import com.mockuai.mainweb.common.api.action.Request;
import com.mockuai.mainweb.common.constant.ActionEnum;
import com.mockuai.mainweb.common.constant.ResponseCode;
import com.mockuai.mainweb.common.domain.dto.IndexPageDTO;
import com.mockuai.mainweb.core.exception.MainWebException;
import com.mockuai.mainweb.core.manager.PageManager;
import com.mockuai.mainweb.core.service.action.RequestContext;
import com.mockuai.mainweb.core.service.action.TransAction;
import com.mockuai.mainweb.core.util.ResponseUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2016/9/20.
 */
@Service
public class AddPageAction extends TransAction {
    private static final Logger log = LoggerFactory.getLogger(AddPageAction.class);
    @Resource
    private PageManager pageManager;



    @Override
    protected MainWebResponse doTransaction(RequestContext context) throws MainWebException {
        Request request = context.getRequest();
        MainWebResponse response = null;
        if(request.getParam("indexPageDTO")==null){
            log.error("indexPageDTO is null");
            return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING.getCode(),"indexPageDTO is null");
        }
        IndexPageDTO indexPageDTO = (IndexPageDTO) request.getParam("indexPageDTO");
        
        log.info(" @@@@@@@@@ indexPageDTO : "+JSONObject.toJSONString(indexPageDTO));
        
        pageManager.addPage(indexPageDTO);
        pageManager.addBizPage(indexPageDTO);

        return  ResponseUtil.getSuccessResponse(indexPageDTO);
    }



    @Override
    public String getName() {
        return ActionEnum.ADD_PAGE.getActionName();
    }
}
