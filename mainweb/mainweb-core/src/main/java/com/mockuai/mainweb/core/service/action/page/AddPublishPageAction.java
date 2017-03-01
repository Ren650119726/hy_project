package com.mockuai.mainweb.core.service.action.page;

import com.mockuai.mainweb.common.api.action.MainWebResponse;
import com.mockuai.mainweb.common.api.action.Request;
import com.mockuai.mainweb.common.constant.ActionEnum;
import com.mockuai.mainweb.common.constant.ResponseCode;
import com.mockuai.mainweb.common.domain.dto.IndexPageDTO;
import com.mockuai.mainweb.common.domain.dto.PublishPageDTO;
import com.mockuai.mainweb.core.exception.MainWebException;
import com.mockuai.mainweb.core.manager.PageManager;
import com.mockuai.mainweb.core.manager.PublishTabManager;
import com.mockuai.mainweb.core.service.action.RequestContext;
import com.mockuai.mainweb.core.service.action.TransAction;
import com.mockuai.mainweb.core.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/*
*
 * Created by Administrator on 2016/9/20.
 
*/

@Service
public class AddPublishPageAction extends TransAction {
    private static final Logger log = LoggerFactory.getLogger(AddPublishPageAction.class);


    @Resource
    private PublishTabManager publishTabManager;

    @Resource
    private PageManager pageManager;


    private PublishPageDTO publishPage(Long pageId, String appKey) throws MainWebException {
        try {

            PublishPageDTO publishPageDTO = pageManager.generatePageJson(pageId, appKey);
            String content = publishPageDTO.getContent();
            //序列化页面
            log.info("静态化文件:{}", pageId);
            IndexPageDTO indexPageDTO = new IndexPageDTO();
            indexPageDTO.setId(pageId);
            indexPageDTO.setPublishStatus(1);
            pageManager.updatePage(indexPageDTO);
            //staticPage.serialPage(SerialPageEnum.MAIN,pageId,content);
            log.info("静态化文件结束:{}", pageId);
            //对tab表进行操作//// TODO: 2016/9/25
            publishTabManager.generateTab();
            return publishPageDTO;

            //返回的对象要不要再封装//// TODO: 2016/9/23

        } catch (MainWebException e) {
            log.error("发布首页失败，AddPublishPageAction", e);
            throw e;
        }
    }


    @Override
    protected MainWebResponse doTransaction(RequestContext context) throws MainWebException {

        Request request = context.getRequest();
//        MainWebResponse response = null;
        if (request.getParam("pageId") == null) {
            log.error("pageId is null");
            return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING.getCode(), "pageId is null");
        }
        Long pageId = (Long) request.getParam("pageId");
        String appKey = (String) request.getParam("appKey");
        try {
            PublishPageDTO publishPageDTO = publishPage(pageId, appKey);
            return ResponseUtil.getSuccessResponse(publishPageDTO);
        } catch (MainWebException e) {
            return ResponseUtil.getErrorResponse(e.getCode(), e.getMessage());
        }


    }

    @Override
    public String getName() {
        return ActionEnum.ADD_PUBLISH_PAGE.getActionName();
    }


}

