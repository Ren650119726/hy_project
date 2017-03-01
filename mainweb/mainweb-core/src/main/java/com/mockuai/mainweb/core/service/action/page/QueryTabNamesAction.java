package com.mockuai.mainweb.core.service.action.page;

import com.mockuai.mainweb.common.api.action.MainWebResponse;
import com.mockuai.mainweb.common.api.action.Request;
import com.mockuai.mainweb.common.constant.ActionEnum;
import com.mockuai.mainweb.common.domain.dto.*;
import com.mockuai.mainweb.core.exception.MainWebException;
import com.mockuai.mainweb.core.manager.*;
import com.mockuai.mainweb.core.service.action.Action;
import com.mockuai.mainweb.core.service.action.RequestContext;
import com.mockuai.mainweb.core.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2016/9/20.
 */
@Service
public class QueryTabNamesAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(QueryTabNamesAction.class);


    @Resource
    private PublishTabManager publishTabManager;

    @Override
    public MainWebResponse execute(RequestContext context) throws MainWebException {
        Request request = context.getRequest();

        try {
//            List<IndexPageDTO> pageDTOs = pageManager.queryPageNameList();
            List<PublishTabDTO> publishPageDTOs = publishTabManager.queryPageNameList();


            MainWebResponse<String>  response = ResponseUtil.getSuccessResponse(publishPageDTOs.get(0).getContent());
            return response;
        }catch (MainWebException e){
            log.error("查询tab主页名字失败",e);
            return ResponseUtil.getErrorResponse(e.getCode(),e.getMessage());
        }

    }



    @Override
    public String getName() {
        return ActionEnum.QUERY_TAB_NAMES.getActionName();
    }
}
