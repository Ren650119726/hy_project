/*
package com.mockuai.mainweb.core.service.action.page;

import com.mockuai.mainweb.common.api.action.MainWebResponse;
import com.mockuai.mainweb.common.api.action.Request;
import com.mockuai.mainweb.common.constant.ActionEnum;
import com.mockuai.mainweb.common.constant.ResponseCode;
import com.mockuai.mainweb.common.domain.dto.IndexPageDTO;
import com.mockuai.mainweb.common.domain.dto.PublishIndexPageDTO;
import com.mockuai.mainweb.core.exception.MainWebException;
import com.mockuai.mainweb.core.manager.PageManager;
import com.mockuai.mainweb.core.manager.PublishTabManager;
import com.mockuai.mainweb.core.manager.publish.PublishIndexPageManager;
import com.mockuai.mainweb.core.service.action.RequestContext;
import com.mockuai.mainweb.core.service.action.TransAction;
import com.mockuai.mainweb.core.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

*/
/**
 * Created by Administrator on 2016/9/20.
 * 发布页面 仅仅只是将页面复制到发布几张表
 *//*

@Service
public class AddPublishPageAction extends TransAction {
    private static final Logger log = LoggerFactory.getLogger(AddPublishPageAction.class);
    @Resource
    private PublishIndexPageManager publishIndexPageManager;
    @Resource
    private PageManager pageManager;

    @Resource
    private PublishTabManager publishTabManager;



    @Override
    protected MainWebResponse doTransaction(RequestContext context) throws MainWebException {

        Request request = context.getRequest();
//        MainWebResponse response = null;
        Long pageId = (Long) request.getParam("pageId");
        String appkey = (String) request.getParam("appKey");
        if (pageId== null) {
            log.error("pageId is null");
            return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING.getCode(), "pageId is null");
        }
        //删除发布页面

        publishIndexPageManager.deletePage(pageId);
        publishIndexPageManager.deleteBizPage(pageId);
        IndexPageDTO indexPageDTO =  new IndexPageDTO();
        indexPageDTO.setId(pageId);


        indexPageDTO = pageManager.getPage(indexPageDTO,appkey);
        PublishIndexPageDTO publishIndexPageDTO = new PublishIndexPageDTO();
        BeanUtils.copyProperties(indexPageDTO,publishIndexPageDTO);
        publishIndexPageDTO.setId(null);
        publishIndexPageDTO.setIndexPageId(pageId);
        publishIndexPageManager.addPage(publishIndexPageDTO);
        publishIndexPageManager.addBizPage(publishIndexPageDTO);
        indexPageDTO.setPublishStatus(1);
        pageManager.updatePage(indexPageDTO);
        publishTabManager.generateTab();

        return ResponseUtil.getSuccessResponse();
    }

    @Override
    public String getName () {
        return ActionEnum.ADD_PUBLISH_PAGE.getActionName();
    }


}

*/
