package util;

import com.mockuai.shopcenter.api.BaseRequest;
import com.mockuai.shopcenter.api.Request;
import com.mockuai.shopcenter.constant.ActionEnum;

/**
 * Created by yindingyu on 16/1/28.
 */
public class RequestFactory {


    public static Request newRequest() {
        Request request = new BaseRequest();
        request.setParam("appKey", AppKeyEnum.MOCKUAI_DEMO.getAppKey());

        return request;
    }

    public static Request newRequest(AppKeyEnum appKeyEnum) {
        Request request = new BaseRequest();
        request.setParam("appKey", appKeyEnum.getAppKey());
        return request;
    }

    public static Request newRequest(AppKeyEnum appKeyEnum, ActionEnum actionEnum) {
        Request request = new BaseRequest();
        request.setParam("appKey", appKeyEnum.getAppKey());
        request.setCommand(actionEnum.getActionName());
        return request;
    }

    public static Request newRequest(AppKeyEnum appKeyEnum, ActionEnum actionEnum, Long sellerId, Long userId) {
        Request request = new BaseRequest();
        request.setParam("appKey", appKeyEnum.getAppKey());
        request.setCommand(actionEnum.getActionName());
        request.setParam("sellerId", sellerId);
        request.setParam("supplierId", sellerId);
        request.setParam("userId", userId);
        return request;
    }


}
