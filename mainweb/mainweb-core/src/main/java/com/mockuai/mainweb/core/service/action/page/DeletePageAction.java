package com.mockuai.mainweb.core.service.action.page;

import com.mockuai.mainweb.common.api.action.MainWebResponse;
import com.mockuai.mainweb.common.api.action.Request;
import com.mockuai.mainweb.common.constant.ActionEnum;
import com.mockuai.mainweb.common.constant.ResponseCode;
import com.mockuai.mainweb.core.exception.MainWebException;
import com.mockuai.mainweb.core.manager.*;
import com.mockuai.mainweb.core.manager.publish.PublishIndexPageManager;
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
public class DeletePageAction extends TransAction {
    private static final Logger log = LoggerFactory.getLogger(DeletePageAction.class);
    @Resource
    private PageManager pageManager;
    @Resource
    private PublishIndexPageManager publishIndexPageManager;
    @Resource
    private PublishTabManager publishTabManager;

    @Override
    protected MainWebResponse doTransaction(RequestContext context) throws MainWebException {
        Request request = context.getRequest();
        MainWebResponse<Boolean> response = null;
        Long id = (Long) request.getParam("id");
        if( id ==null){
            log.error("id is null");
            return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING.getCode(),"id is null");
        }

         try {
             pageManager.deletePage(id);
             pageManager.deleteBizPage(id);
             //删除发布页面
             publishIndexPageManager.deletePage(id);
             publishIndexPageManager.deleteBizPage(id);
             //再次生成头部tab页标题
             publishTabManager.generateTab();
         }catch (MainWebException e){
             log.error("",e);
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
        return ActionEnum.DELETE_PAGE.getActionName();
    }
}
