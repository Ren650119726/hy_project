package com.mockuai.giftscenter.mop.api.action;

import java.util.List;

import com.mockuai.giftscenter.common.api.BaseRequest;
import com.mockuai.giftscenter.common.api.Request;
import com.mockuai.giftscenter.common.api.Response;
import com.mockuai.giftscenter.common.constant.ActionEnum;
import com.mockuai.giftscenter.common.domain.dto.GiftsPacketDTO;
import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.service.action.MopResponse;

/**
 * Created by edgar.zr on 12/15/15.ce
 */
public class AppQueryGifts extends BaseAction {

    public MopResponse execute(com.mockuai.mop.common.service.action.Request request) {

        Request marketReq = new BaseRequest();
        String appKey = (String)request.getParam("app_key");
        marketReq.setCommand(ActionEnum.APP_QUERY_GIFTS.getActionName());
        marketReq.setParam("appKey", appKey);
        Response<List<GiftsPacketDTO>> marketResp = getGiftsService().execute(marketReq);
        MopResponse response = null;
        if (marketResp.isSuccess()) {
        	List<GiftsPacketDTO> result = marketResp.getModule();
            response = new MopResponse(result);
        } else {
            response = new MopResponse(marketResp.getResCode(), marketResp.getMessage());
        }
        return response;
    }

    public String getName() {
        return "/gifts/giftsList";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.NO_AUTH;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_GET;
    }
}