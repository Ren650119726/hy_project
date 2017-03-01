package com.mockuai.rainbowcenter.mop.api.action;

import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.constant.MopRespCode;
import com.mockuai.mop.common.constant.ResponseFormat;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.mop.common.service.action.Request;
import com.mockuai.rainbowcenter.common.api.BaseRequest;
import com.mockuai.rainbowcenter.common.api.Response;
import com.mockuai.rainbowcenter.common.constant.ActionEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by lizg on 2016/12/21.
 */
public class VersionDeploy extends BaseAction {

    private static final Logger log = LoggerFactory.getLogger(VersionDeploy.class);


    private final static String OUTPUT_PREFIX = "{\"code\":10000,\"msg\":\"success\",\"data\":xxx}";


    private final static String TPL = "xxx";


    public MopResponse execute(Request request) {

        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setCommand(ActionEnum.GET_VERSION_DEPLOY.getActionName());

        Response<String> response = getRainbowService().execute(baseRequest);

        if (!response.isSuccess()) {
            return new MopResponse(MopRespCode.S_E_SERVICE_ERROR, response.getMessage());
        }


        log.info("[{}] versionDeploy response :{}",response.getModule());

        String responseStr = OUTPUT_PREFIX.replace(TPL, response.getModule());

        return new MopResponse<String>(responseStr);
    }

    public String getName() {
        return "/version/deploy/get";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.NO_AUTH;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_GET;
    }


    /**
     * 用户自定义返回格式
     * @return
     */
    public ResponseFormat getResponseFormat() {
        return ResponseFormat.CUSTOMIZE;
    }
}
