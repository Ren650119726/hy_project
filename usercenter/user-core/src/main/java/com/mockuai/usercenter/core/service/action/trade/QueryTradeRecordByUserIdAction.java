package com.mockuai.usercenter.core.service.action.trade;

import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.Request;
import com.mockuai.usercenter.common.api.UserResponse;
import com.mockuai.usercenter.common.constant.ResponseCode;
import com.mockuai.usercenter.common.dto.TradeRecordDTO;
import com.mockuai.usercenter.core.exception.UserException;
import com.mockuai.usercenter.core.manager.TradeRecordManager;
import com.mockuai.usercenter.core.service.RequestContext;
import com.mockuai.usercenter.core.service.action.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by duke on 15/9/22.
 */
@Service
public class QueryTradeRecordByUserIdAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(QueryTradeRecordByUserIdAction.class);

    @Resource
    private TradeRecordManager tradeRecordManager;

    @Override
    public UserResponse execute(RequestContext context) throws UserException {
        log.info("Enter Action [{}]", getName());

        Request request = context.getRequest();
        Long userId = (Long) request.getParam("userId");

        if (userId == null) {
            log.error("userId is null");
            throw new UserException(ResponseCode.P_PARAM_NULL, "userId为空");
        }

        TradeRecordDTO tradeRecordDTO = tradeRecordManager.queryTradeRecordByUserId(userId);

        log.info("Exit Action [{}]", getName());
        return new UserResponse(tradeRecordDTO);
    }

    @Override
    public String getName() {
        return ActionEnum.QUERY_TRADE_RECORD_BY_USER_ID.getActionName();
    }
}
