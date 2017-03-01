package com.mockuai.usercenter.core.service.action.trade;

import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.Request;
import com.mockuai.usercenter.common.api.Response;
import com.mockuai.usercenter.common.api.UserResponse;
import com.mockuai.usercenter.common.constant.ResponseCode;
import com.mockuai.usercenter.common.dto.TradeRecordDTO;
import com.mockuai.usercenter.core.domain.TradeRecordDO;
import com.mockuai.usercenter.core.exception.UserException;
import com.mockuai.usercenter.core.manager.TradeRecordManager;
import com.mockuai.usercenter.core.service.RequestContext;
import com.mockuai.usercenter.core.service.action.Action;
import com.mockuai.usercenter.core.util.ModelUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by duke on 15/9/29.
 */
@Service
public class UpdateTradeRecordByUserIdAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(UpdateTradeRecordByUserIdAction.class);

    @Resource
    TradeRecordManager tradeRecordManager;

    @Override
    public UserResponse execute(RequestContext context) throws UserException {
        log.info("Enter Action : {}", getName());

        Request request = context.getRequest();
        Long userId = (Long) request.getParam("userId");
        TradeRecordDTO tradeRecordDTO = (TradeRecordDTO) request.getParam("tradeRecordDTO");

        if (userId == null) {
            log.error("user id is null");
            throw new UserException(ResponseCode.P_PARAM_NULL);
        }

        if (tradeRecordDTO == null) {
            log.error("tradeReocrdDTO is null");
            throw new UserException(ResponseCode.P_PARAM_NULL);
        }

        TradeRecordDO tradeRecordDO = new TradeRecordDO();
        BeanUtils.copyProperties(tradeRecordDTO, tradeRecordDO);
        tradeRecordManager.updateByUserId(userId, tradeRecordDO);
        log.info("Exit Action : {}", getName());
        return new UserResponse(true);
    }

    @Override
    public String getName() {
        return ActionEnum.UPDATE_TRADE_RECORD_BY_USER_ID.getActionName();
    }
}
