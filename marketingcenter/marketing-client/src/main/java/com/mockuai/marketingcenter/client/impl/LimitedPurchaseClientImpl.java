package com.mockuai.marketingcenter.client.impl;

import com.mockuai.marketingcenter.client.LimitedPurchaseClient;
import com.mockuai.marketingcenter.common.api.BaseRequest;
import com.mockuai.marketingcenter.common.api.MarketingService;
import com.mockuai.marketingcenter.common.api.Response;
import com.mockuai.marketingcenter.common.constant.ActionEnum;
import com.mockuai.marketingcenter.common.domain.dto.LimitedPurchaseGoodsDTO;
import com.mockuai.marketingcenter.common.domain.qto.TimePurchaseQTO;
import com.mockuai.marketingcenter.common.domain.qto.ViewSalesQTO;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by huangsiqian on 2016/10/10.
 */

public class LimitedPurchaseClientImpl implements LimitedPurchaseClient {
    @Resource
    private MarketingService marketingService;
    public Response<Boolean> addLimitedPurchase(TimePurchaseQTO timePurchaseQTO, String appKey) {
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setCommand(ActionEnum.ADD_TIME_PURCHASE.getActionName());
        baseRequest.setParam("timePurchaseQTO", timePurchaseQTO);
        baseRequest.setParam("appKey", appKey);
        Response<Boolean> response = (Response<Boolean>) marketingService.execute(baseRequest);
        return response;
    }

    public Response<List> limitedPurchaseList(TimePurchaseQTO timePurchaseQTO, String appKey) {
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setCommand(ActionEnum.TIME_PURCHASE_LIST.getActionName());
        baseRequest.setParam("timePurchaseQTO", timePurchaseQTO);
        baseRequest.setParam("appKey", appKey);
        Response<List> response = (Response<List>) marketingService.execute(baseRequest);
        return response;
    }

    public Response<Boolean> updateLimitedPurchase(TimePurchaseQTO timePurchaseQTO, String appKey) {
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setCommand(ActionEnum.UPDATE_TIME_PURCHASE.getActionName());
        baseRequest.setParam("timePurchaseQTO", timePurchaseQTO);
        baseRequest.setParam("appKey", appKey);
        Response<Boolean> response = (Response<Boolean>) marketingService.execute(baseRequest);
        return response;
    }

    public Response<TimePurchaseQTO> limitedPurchaseById(TimePurchaseQTO timePurchaseQTO , String appKey) {
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setCommand(ActionEnum.TIME_PURCHASE_BY_ID.getActionName());
        baseRequest.setParam("timePurchaseQTO", timePurchaseQTO);
        baseRequest.setParam("appKey", appKey);
        Response<TimePurchaseQTO> response = (Response<TimePurchaseQTO>) marketingService.execute(baseRequest);
        return response;
    }

    public Response<Boolean> startLimtedPurchaseAction(TimePurchaseQTO timePurchaseQTO , String appKey) {
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setCommand(ActionEnum.START_LIMITED_PURCHASE_ACTION.getActionName());
        baseRequest.setParam("timePurchaseQTO", timePurchaseQTO);
        baseRequest.setParam("appKey", appKey);
        Response<Boolean> response = (Response<Boolean>) marketingService.execute(baseRequest);
        return response;
    }

    public Response<Boolean> stopLimtedPurchaseAction(TimePurchaseQTO timePurchaseQTO, String appKey) {
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setCommand(ActionEnum.STOP_TIME_PURCHASE.getActionName());
        baseRequest.setParam("timePurchaseQTO", timePurchaseQTO);
        baseRequest.setParam("appKey", appKey);
        Response<Boolean> response = (Response<Boolean>) marketingService.execute(baseRequest);
        return response;
    }



    public Response<Boolean> deleteActivityGoods (LimitedPurchaseGoodsDTO activityGoodsDTO, String appKey) {
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setCommand(ActionEnum.DELETE_TIME_PURCHASE_GOODS.getActionName());
        baseRequest.setParam("activityGoodsDTO", activityGoodsDTO);
        baseRequest.setParam("appKey", appKey);
        Response<Boolean> response = (Response<Boolean>) marketingService.execute(baseRequest);
        return response;
    }

    public Response<Boolean> addActivityGoods(TimePurchaseQTO timePurchaseQTO,String appKey) {
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setCommand(ActionEnum.ADD_TIME_PURCHASE_GOODS.getActionName());
        baseRequest.setParam("timePurchaseQTO", timePurchaseQTO);
        baseRequest.setParam("appKey", appKey);
        Response<Boolean> response = (Response<Boolean>) marketingService.execute(baseRequest);
        return response;
    }

    public Response<List> viewSales(ViewSalesQTO viewSalesQTO, String appKey) {
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setCommand(ActionEnum.VIEW_SALES.getActionName());
        baseRequest.setParam("viewSalesQTO",viewSalesQTO);
        baseRequest.setParam("appKey",appKey);
        Response<List> response =(Response<List>)marketingService.execute(baseRequest);
        return response;
    }

    public Response<Boolean> updateLimitedPurchaseStatus(String appKey) {
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setCommand(ActionEnum.UP_DATE_LIMITED_PURCHASE_STATUS.getActionName());
        baseRequest.setParam("appKey",appKey);
        Response<Boolean> response =marketingService.execute(baseRequest);
        return response;
    }
}
