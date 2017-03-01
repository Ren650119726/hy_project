package com.mockuai.mainweb.mop.api.action;

import com.mockuai.mainweb.common.api.action.BaseRequest;
import com.mockuai.mainweb.common.api.action.Response;
import com.mockuai.mainweb.common.constant.ActionEnum;
import com.mockuai.mainweb.common.constant.ResponseCode;
import com.mockuai.mainweb.common.domain.dto.PublishPageDTO;
import com.mockuai.mainweb.mop.api.action.util.JsonUtil;
import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.constant.ResponseFormat;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.mop.common.service.action.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by Administrator on 2016/11/28.
 */
public class PreveiwByIdAction extends BaseAction{
    private static final Logger LOGGER = LoggerFactory.getLogger(PreveiwByIdAction.class);
    private final static String OUTPUT_PREFIX = "{\"code\":10000,\"msg\":\"success\",\"data\":{\"pagename\":\"sss\",\"component\":xxx}}";

    private final static String TPL = "xxx";

    private final static String NAM = "sss";
    @Override
    public MopResponse execute(Request request) {
        String pageId = (String) request.getParam("page_id");
        Long id = Long.parseLong(pageId);
        String appKey = (String)request.getParam("app_key");
        LOGGER.error("getInId:{}",id);
        com.mockuai.mainweb.common.api.action.Request  pageReq = new BaseRequest();
        pageReq.setParam("pageId",id);
        pageReq.setParam("appKey",appKey);
        pageReq.setCommand(ActionEnum.PREVIEW_PAGE.getActionName());

        Response<PublishPageDTO> response = (Response<PublishPageDTO>) getMainWebService().execute(pageReq);

        if (response.getCode() == ResponseCode.SUCCESS.getCode()) {
            PublishPageDTO publishPageDTO = response.getModule();
            String content = publishPageDTO.getContent();
            String name = publishPageDTO.getName();
            String responseStr = OUTPUT_PREFIX.replace(TPL,content);

            responseStr = responseStr.replace(NAM,name);

            LOGGER.info("return MSG:{}",responseStr);
            return new MopResponse<String>(responseStr);
        }else {
            return new MopResponse<String>(ResponseCode.SYS_E_SERVICE_EXCEPTION.getCode(),"首页names 查询失败");
        }

    }
    @Override
    public ResponseFormat getResponseFormat() {

        return ResponseFormat.CUSTOMIZE;
    }

    @Override
    public String getName() {
        return "/mainweb/page/preview";
    }

    @Override
    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.NO_AUTH;    }

    @Override
    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.NO_LIMIT;
    }
}
