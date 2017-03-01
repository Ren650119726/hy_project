package com.mockuai.usercenter.client.impl;

import com.mockuai.usercenter.client.TradeRecordClient;
import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.BaseRequest;
import com.mockuai.usercenter.common.api.Request;
import com.mockuai.usercenter.common.api.Response;
import com.mockuai.usercenter.common.api.UserDispatchService;
import com.mockuai.usercenter.common.dto.TradeRecordDTO;
import com.mockuai.usercenter.common.qto.TradeRecordQTO;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by duke on 15/9/22.
 */
public class TradeRecordClientImpl implements TradeRecordClient {
    @Resource
    UserDispatchService userDispatchService;

    public Response<Long> addTradeRecord(TradeRecordDTO tradeRecordDTO, String appKey) {
        Request request = new BaseRequest();
        request.setParam("tradeRecordDTO", tradeRecordDTO);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.ADD_TRADE_RECORD.getActionName());
        return userDispatchService.execute(request);
    }

    public Response<TradeRecordDTO> queryTradeRecordByUserId(Long userId, String appKey) {
        Request request = new BaseRequest();
        request.setParam("userId", userId);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.QUERY_TRADE_RECORD_BY_USER_ID.getActionName());
        return userDispatchService.execute(request);
    }

    public Response<List<TradeRecordDTO>> query(TradeRecordQTO tradeRecordQTO, String appKey) {
        Request request = new BaseRequest();
        request.setParam("appKey", appKey);
        request.setParam("tradeRecordQTO", tradeRecordQTO);
        request.setCommand(ActionEnum.QUERY_TRADE_RECORD.getActionName());
        return userDispatchService.execute(request);
    }

    public Response<List<TradeRecordDTO>> queryAll(String appKey) {
        Request request = new BaseRequest();
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.QUERY_ALL_TRADE_RECORD.getActionName());
        return userDispatchService.execute(request);
    }

    public Response<Boolean> updateByUserId(Long userId, TradeRecordDTO tradeRecordDTO, String appKey) {
        Request request = new BaseRequest();
        request.setParam("appKey", appKey);
        request.setParam("userId", userId);
        request.setParam("tradeRecordDTO", tradeRecordDTO);
        request.setCommand(ActionEnum.UPDATE_TRADE_RECORD_BY_USER_ID.getActionName());
        return userDispatchService.execute(request);
    }
}
