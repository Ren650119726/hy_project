package com.mockuai.mainweb.core.service.action.page;

import com.mockuai.mainweb.common.api.action.MainWebResponse;
import com.mockuai.mainweb.common.api.action.Request;
import com.mockuai.mainweb.common.constant.ActionEnum;
import com.mockuai.mainweb.common.constant.ResponseCode;
import com.mockuai.mainweb.common.domain.dto.IndexPageDTO;
import com.mockuai.mainweb.core.exception.MainWebException;
import com.mockuai.mainweb.core.manager.PageManager;
import com.mockuai.mainweb.core.manager.PublishPageManager;
import com.mockuai.mainweb.core.manager.PublishTabManager;
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
public class DeleteThenPublishAction extends TransAction {
    private static final Logger log = LoggerFactory.getLogger(DeleteThenPublishAction.class);
    @Resource
    private PageManager pageManager;
    @Resource
    private PublishTabManager publishTabManager;
    @Resource
    private PublishPageManager publishPageManager;


    @Override
    protected MainWebResponse doTransaction(RequestContext context) throws MainWebException {
        Request request = context.getRequest();
        MainWebResponse<Boolean> response = null;
        Long pageId = (Long) request.getParam("pageId");
        if( pageId ==null){
            log.error("pageId is null");
            return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING.getCode(),"pageId is null");
        }

         try {
             IndexPageDTO pageDTO =  pageManager.getPage(pageId);
             pageManager.deletePage(pageId);
             pageManager.deleteBizPage(pageId);
             //页面未发布
             if(pageDTO.getPublishStatus() == null || pageDTO.getPublishStatus() != 1){
                 return ResponseUtil.getSuccessResponse();
             }
//             publishPageManager.deletePublishPage(pageId);
             //
             publishTabManager.generateTab();


         }catch (MainWebException e){
             log.error("删除并且发布失败",e);
            return  ResponseUtil.getErrorResponse(e.getCode(),e.getMessage());
         }
        return ResponseUtil.getSuccessResponse();
    }


//        pageGalleryManager.deletePageGallery(id);
//        pageBlockManager.deletePageBlock(id);
//        pageItemCategoryManager.deletePageItemCategory(id);
//        pageItemManager.deletePageItem(id);



    @Override
    public String getName() {
        return ActionEnum.DELETE_THEN_PUBLISH.getActionName();
    }
}
