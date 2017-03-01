package com.mockuai.mainweb.mop.api.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mockuai.common.uils.staticpage.SerialPageEnum;
import com.mockuai.common.uils.staticpage.StaticPage;
import com.mockuai.mainweb.common.api.action.BaseRequest;
import com.mockuai.mainweb.common.api.action.Response;
import com.mockuai.mainweb.common.constant.ActionEnum;
import com.mockuai.mainweb.common.constant.ResponseCode;
import com.mockuai.mainweb.common.domain.dto.IndexPageDTO;
import com.mockuai.mainweb.common.domain.dto.PublishPageDTO;
import com.mockuai.mainweb.common.util.JsonUtil;
import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.constant.MopRespCode;
import com.mockuai.mop.common.constant.ResponseFormat;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.mop.common.service.action.Request;

/**
 * Created by Administrator on 2016/9/23.
 */
public class GetPageByIdAction extends BaseAction {

	private static final Logger log = LoggerFactory.getLogger(GetPageByIdAction.class);

    private final static String OUTPUT_PREFIX = "{\"code\":10000,\"msg\":\"success\",\"data\":{\"pagename\":\"sss\",\"component\":xxx}}";

    private final static String TPL = "xxx";

    private final static String NAM = "sss";

    private StaticPage staticPage;

    private final static String environment  = "online";


    public GetPageByIdAction(){
        staticPage = new StaticPage();
        staticPage.setEnvironment(environment);
    }



    public MopResponse execute(Request request) {

        String pageId = (String) request.getParam("page_id");
        Long id = Long.parseLong(pageId);
        String appKey = (String)request.getParam("app_key");

        //需要 mop和maincenter 共享磁盘 或者在同一个服务器
        try {
            log.info(" ####### id :{} ",id);
            String responseStr = staticPage.getFileContent(SerialPageEnum.MAIN,id);
            com.mockuai.mainweb.common.api.action.Request  pageReq = new BaseRequest();
            pageReq.setParam("pageId",id);
            pageReq.setParam("appKey",appKey);
            pageReq.setCommand(ActionEnum.GET_PAGE.getActionName());

//            log.info(" ####### responseStr :{} ",JsonUtil.toJson(responseStr));
            Response<IndexPageDTO> response = getMainWebService().execute(pageReq);
            String name =null ;
            if (response.getCode() == ResponseCode.SUCCESS.getCode()) {
                IndexPageDTO indexPageDTO = response.getModule();
                 name = indexPageDTO.getName();
            }
            responseStr = OUTPUT_PREFIX.replace(TPL,responseStr);

            responseStr = responseStr.replace(NAM,name);

//            log.info(" ####### responseStr :{} ",JsonUtil.toJson(responseStr));
            return new MopResponse<String>(responseStr);
        }catch (Exception e){
            return new MopResponse(MopRespCode.S_E_SERVICE_ERROR,"mopApi 以 pageId 查询首页失败");

        }

    }
    @Override
    public ResponseFormat getResponseFormat() {

        return ResponseFormat.CUSTOMIZE;
    }

    public String getName() {
        return "/mainweb/page/get";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.NO_AUTH;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.NO_LIMIT;
    }
}
