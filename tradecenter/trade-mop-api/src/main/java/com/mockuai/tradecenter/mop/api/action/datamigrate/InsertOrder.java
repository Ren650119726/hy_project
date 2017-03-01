package com.mockuai.tradecenter.mop.api.action.datamigrate;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.constant.MopRespCode;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.tradecenter.common.api.BaseRequest;
import com.mockuai.tradecenter.common.api.Response;
import com.mockuai.tradecenter.common.constant.ActionEnum;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.common.domain.*;
import com.mockuai.tradecenter.common.domain.datamigrate.MigrateOrderDTO;
import com.mockuai.tradecenter.common.util.ModelUtil;
import com.mockuai.tradecenter.mop.api.action.BaseAction;
import com.mockuai.tradecenter.mop.api.domain.MopOrderDTO;
import com.mockuai.tradecenter.mop.api.domain.MopOrderItemDTO;
import com.mockuai.tradecenter.mop.api.util.JsonUtil;
import com.mockuai.tradecenter.mop.api.util.MopApiUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InsertOrder extends BaseAction {
    public MopResponse execute(com.mockuai.mop.common.service.action.Request request) {
        String migrateOrderStr = (String) request.getParam("migrate_order");
        Long userId = (Long) request.getAttribute("user_id");
        String appKey = (String)request.getParam("app_key");

        if(StringUtils.isBlank(migrateOrderStr)){
            return new MopResponse(MopRespCode.P_E_PARAM_ISNULL, "migrateOrderStr is null");
        }

        MigrateOrderDTO migrateOrderDTO = JsonUtil.parseJson(migrateOrderStr, MigrateOrderDTO.class);



        com.mockuai.tradecenter.common.api.Request tradeReq = new BaseRequest();
        tradeReq.setCommand(ActionEnum.INSERT_ORDER.getActionName());

        tradeReq.setParam("migrateOrderDTO", migrateOrderDTO);
        tradeReq.setParam("appKey", appKey);
        Response tradeResp = getTradeService().execute(tradeReq);
        MopResponse response = null;
        if (tradeResp.isSuccess()) {
            Long orderId = (Long) tradeResp.getModule();
            Map data = new HashMap();
            data.put("order_id", orderId);
            response = new MopResponse(data);
        } else {
            response = new MopResponse(tradeResp.getCode(), tradeResp.getMessage());;
        }

        return response;
    }


    public String getName() {
        return "/data_migrate/trade/order/insert";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.AUTH_LOGIN;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_POST;
    }
}
