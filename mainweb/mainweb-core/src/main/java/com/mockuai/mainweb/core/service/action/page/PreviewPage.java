package com.mockuai.mainweb.core.service.action.page;

import com.mockuai.mainweb.common.api.action.MainWebResponse;
import com.mockuai.mainweb.common.constant.ActionEnum;
import com.mockuai.mainweb.common.domain.dto.PublishPageDTO;
import com.mockuai.mainweb.core.exception.MainWebException;
import com.mockuai.mainweb.core.manager.PageManager;
import com.mockuai.mainweb.core.service.action.Action;
import com.mockuai.mainweb.core.service.action.RequestContext;
import com.mockuai.mainweb.core.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2016/11/26.
 */
@Service
public class PreviewPage implements Action {
    private static final Logger log = LoggerFactory.getLogger(PreviewPage.class);
    @Resource
    private PageManager pageManager;
    @Override
    public MainWebResponse execute(RequestContext context) throws MainWebException {

        try {
            //从mop层传入的参数为id
            Long pageId = (Long) context.getRequest().getParam("pageId");
            String  appKey = (String)context.getRequest().getParam("appKey");
            PublishPageDTO publishPageDTO = pageManager.generatePageJson(pageId, appKey);

            String content = publishPageDTO.getContent();
            log.info("previewPageMsg:{},pageid:{}",content,pageId);
            return ResponseUtil.getSuccessResponse(publishPageDTO);

        }catch (MainWebException e){
            log.error("查询tab主页名字失败",e);
            return ResponseUtil.getErrorResponse(e.getCode(),e.getMessage());
        }
    }

    @Override
    public String getName() {
        return ActionEnum.PREVIEW_PAGE.getActionName();
    }
}
