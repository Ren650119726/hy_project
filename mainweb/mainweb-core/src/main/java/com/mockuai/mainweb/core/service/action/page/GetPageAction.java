package com.mockuai.mainweb.core.service.action.page;

import com.mockuai.mainweb.common.api.action.MainWebResponse;
import com.mockuai.mainweb.common.api.action.Request;
import com.mockuai.mainweb.common.constant.ActionEnum;
import com.mockuai.mainweb.common.domain.dto.IndexPageDTO;
import com.mockuai.mainweb.core.exception.MainWebException;
import com.mockuai.mainweb.core.manager.*;
import com.mockuai.mainweb.core.service.action.Action;
import com.mockuai.mainweb.core.service.action.RequestContext;
import com.mockuai.mainweb.core.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2016/9/20.
 */
@Service
public class GetPageAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(GetPageAction.class);
    @Resource
    private PageManager pageManager;


   public IndexPageDTO getPage(IndexPageDTO indexPageDTO,String appKey) throws MainWebException {

           return pageManager.getPage(indexPageDTO, appKey);
   }


    @Override
    public MainWebResponse execute(RequestContext context) throws MainWebException {
        Request request = context.getRequest();

        String appKey = (String) request.getParam("appKey");


        Long pageId = (Long) request.getParam("pageId");
        try {
            IndexPageDTO indexPageDTO =  new IndexPageDTO();
            indexPageDTO.setId(pageId);
            indexPageDTO =  getPage(indexPageDTO,appKey);

            MainWebResponse<IndexPageDTO>  response = ResponseUtil.getSuccessResponse(indexPageDTO);
            return response;
        }catch (MainWebException e){
            log.error("",e);
           return ResponseUtil.getErrorResponse(e.getCode(),e.getMessage());
        }

    }




    @Override
    public String getName() {
        return ActionEnum.GET_PAGE.getActionName();
    }
}
