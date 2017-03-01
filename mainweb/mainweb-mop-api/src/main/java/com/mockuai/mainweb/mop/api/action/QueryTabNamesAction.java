package com.mockuai.mainweb.mop.api.action;

import com.mockuai.mainweb.common.api.action.BaseRequest;
import com.mockuai.mainweb.common.api.action.MainWebResponse;
import com.mockuai.mainweb.common.api.action.Response;
import com.mockuai.mainweb.common.constant.ActionEnum;
import com.mockuai.mainweb.common.constant.ResponseCode;
import com.mockuai.mainweb.common.domain.dto.IndexPageDTO;
import com.mockuai.mainweb.common.domain.dto.PublishPageDTO;
import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.constant.ResponseFormat;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.mop.common.service.action.Request;

import java.util.List;

/**
 * Created by Administrator on 2016/9/23.
 */
public class QueryTabNamesAction extends BaseAction {

    private final static String OUTPUT_PREFIX = "{\"code\":10000,\"msg\":\"success\",\"data\":{\"page_title_list\":xxx}}";
    private final static String TPL = "xxx";

    public MopResponse execute(Request request) {
        String appKey = (String) request.getParam("app_key");

//        String sellerId = (String) request.getParam("seller_id");

        com.mockuai.mainweb.common.api.action.Request request1 = new BaseRequest();

        request1.setCommand(ActionEnum.QUERY_TAB_NAMES.getActionName());

//        Response<List<PublishPageDTO>> response = (Response<List<PublishPageDTO>>) getMainWebService().execute(request1);
        Response<String> response = (Response<String>) getMainWebService().execute(request1);

        if (response.getCode() == ResponseCode.SUCCESS.getCode()) {

            String content = response.getModule();
            String responseStr = OUTPUT_PREFIX.replace(TPL,content);
            return new MopResponse<String>(responseStr);
//            return new MopResponse<String>(response.getModule());
        }else {
            return new MopResponse<String>(ResponseCode.SYS_E_SERVICE_EXCEPTION.getCode(),"首页names 查询失败");
        }
    }

    public String getName() {
        return "/mainweb/page/page_names";
    }


    @Override
    public ResponseFormat getResponseFormat() {
        return ResponseFormat.CUSTOMIZE;
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.NO_AUTH;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.NO_LIMIT;
    }
}
