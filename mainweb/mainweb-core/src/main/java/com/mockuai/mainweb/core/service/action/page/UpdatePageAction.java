package com.mockuai.mainweb.core.service.action.page;

import com.mockuai.mainweb.common.api.action.MainWebResponse;
import com.mockuai.mainweb.common.api.action.Request;
import com.mockuai.mainweb.common.constant.ActionEnum;
import com.mockuai.mainweb.common.domain.dto.IndexPageDTO;
import com.mockuai.mainweb.core.exception.MainWebException;
import com.mockuai.mainweb.core.manager.PageManager;
import com.mockuai.mainweb.core.service.action.RequestContext;
import com.mockuai.mainweb.core.service.action.TransAction;
import com.mockuai.mainweb.core.util.JsonUtil;
import com.mockuai.mainweb.core.util.ResponseUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2016/9/20.
 */
@Service
public class UpdatePageAction extends TransAction {
    private static final Logger log = LoggerFactory.getLogger(UpdatePageAction.class);
    @Resource
    private PageManager pageManager;

    @Override
    protected MainWebResponse doTransaction(RequestContext context) throws MainWebException {
        Request request = context.getRequest();

        IndexPageDTO indexPageDTO = (IndexPageDTO) request.getParam("indexPageDTO");
        
        log.info(" ######### indexPageDTO :{} ",JsonUtil.toJson(indexPageDTO));
        
        try {
            pageManager.updatePage(indexPageDTO);
            pageManager.deleteBizPage(indexPageDTO.getId());
            pageManager.addBizPage(indexPageDTO);
            return ResponseUtil.getSuccessResponse();
        }catch (MainWebException  e){
            log.error("",e);
            return ResponseUtil.getErrorResponse(e.getCode(),e.getMessage());
        }

    }



    @Override
    public String getName() {
        return ActionEnum.UPDATE_PAGE.getActionName();
    }
}
