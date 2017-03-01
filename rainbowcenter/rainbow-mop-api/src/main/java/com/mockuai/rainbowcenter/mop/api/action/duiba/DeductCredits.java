package com.mockuai.rainbowcenter.mop.api.action.duiba;

import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.constant.MopRespCode;
import com.mockuai.mop.common.constant.ResponseFormat;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.mop.common.service.action.Request;
import com.mockuai.rainbowcenter.common.api.BaseRequest;
import com.mockuai.rainbowcenter.common.api.Response;
import com.mockuai.rainbowcenter.common.constant.ActionEnum;
import com.mockuai.rainbowcenter.common.dto.DistDeductDTO;
import com.mockuai.rainbowcenter.mop.api.action.BaseAction;
import com.mockuai.rainbowcenter.mop.api.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Created by lizg on 2016/7/16.
 */
public class DeductCredits extends BaseAction {

    private static final Logger log = LoggerFactory.getLogger(DeductCredits.class);

    private static String APP_KEY = "4WmLRUCPSZEqfape98w68TVA49ez";


    public MopResponse execute(Request request) {

        String uid = (String) request.getParam("uid");
        String credits = (String) request.getParam("credits");  //本次兑换扣除的积分
        String appKey = (String) request.getParam("appKey");
        String timestamp = (String) request.getParam("timestamp"); //兑换时间戳
        String description = (String) request.getParam("description");
        String orderNum = (String) request.getParam("orderNum");  //兑吧订单号
        String type = (String) request.getParam("type");  //兑换类型
        String facePrice = (String) request.getParam("facePrice"); //兑换商品的市场价值，单位是分
        String actualPrice = (String) request.getParam("actualPrice");//此次兑换实际扣除开发者账户费用，单位为分
        String ip = (String) request.getParam("ip");
        String waitAudit = (String) request.getParam("waitAudit"); //是否需要审核
        String params = (String) request.getParam("params");  //详情参数

        String descript = null,param = null;
        try {
             descript = URLDecoder.decode(description, "UTF-8");
             param = URLDecoder.decode(params, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        log.info("[{}] appKey:{}",appKey);
        log.info("[{}] timestamp:{}",timestamp);
        log.info("[{}] descript:{}",descript);
        log.info("[{}] params:{}",param);

        if (!APP_KEY.equals(appKey)) {
            return new MopResponse(MopRespCode.P_E_PARAM_ISNULL, "appKey不匹配");
        }

        if(timestamp ==null){
            return new MopResponse(MopRespCode.P_E_PARAM_ISNULL, "请求中没有带时间戳");
        }
        if (uid == null) {
            log.error("uid is null");
            return new MopResponse(MopRespCode.P_E_PARAM_ISNULL, "uid is null");
        }
        if (credits == null) {
            log.error("credits is null");
            return new MopResponse(MopRespCode.P_E_PARAM_ISNULL, "credits IS NULL");
        }
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setParam("uid", uid);
        baseRequest.setParam("credits", credits);
        baseRequest.setParam("appKey", appKey);
        baseRequest.setParam("timestamp", timestamp);
        baseRequest.setParam("description", descript);
        baseRequest.setParam("orderNum", orderNum);
        baseRequest.setParam("type", type);
        baseRequest.setParam("facePrice", facePrice);
        baseRequest.setParam("actualPrice", actualPrice);
        baseRequest.setParam("ip", ip);
        baseRequest.setParam("waitAudit", waitAudit);
        baseRequest.setParam("params", param);
        baseRequest.setCommand(ActionEnum.DUIBA_DEDUCT_CREDITS.getActionName());

        Response<DistDeductDTO> response = getRainbowService().execute(baseRequest);
        if (!response.isSuccess()) {
            log.error("duiba deduct credits error, uid: {},credits:{}, msg: {}", uid, credits, response.getMessage());
            return new MopResponse(MopRespCode.S_E_SERVICE_ERROR, response.getMessage());
        }
        return new MopResponse(JsonUtil.toJson(response.getModule()));
    }


    public String getName() {

        return "/duiba/deduct/credits";
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
