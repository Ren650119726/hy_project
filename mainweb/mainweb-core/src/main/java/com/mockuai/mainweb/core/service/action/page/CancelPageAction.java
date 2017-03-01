package com.mockuai.mainweb.core.service.action.page;

import com.mockuai.mainweb.common.api.action.MainWebResponse;
import com.mockuai.mainweb.common.constant.ActionEnum;
import com.mockuai.mainweb.common.constant.ResponseCode;
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

/**撤销消页面
 * Created by huangsiqian on 2016/11/25.
 */
@Service
public class CancelPageAction extends TransAction {
    private static final Logger log = LoggerFactory.getLogger(CancelPageAction.class);

    @Resource
    private PageManager pageManager;
    @Resource
    private PublishTabManager publishTabManager;
    @Override
    public String getName() {
        return ActionEnum.CANCEL_PAGE.getActionName();
    }

    @Override
    protected MainWebResponse doTransaction(RequestContext context)  {
        Long id = (Long) context.getRequest().getParam("id");
        if(id==null){
            log.info("cancel pageId is null");
            return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING.getCode(),"请输入要撤销页面的id");
        }

        try{
            pageManager.cancelPage(id);
            //再次生成头部tab页标题
            publishTabManager.generateTab();
        }catch  (MainWebException e){
            log.error("",e);
            return  ResponseUtil.getErrorResponse(e.getCode(),e.getMessage());
        }

        return ResponseUtil.getSuccessResponse();
    }
}
