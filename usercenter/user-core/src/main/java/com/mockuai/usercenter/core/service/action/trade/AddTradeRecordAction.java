package com.mockuai.usercenter.core.service.action.trade;

import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.UserResponse;
import com.mockuai.usercenter.common.constant.ResponseCode;
import com.mockuai.usercenter.common.dto.TradeRecordDTO;
import com.mockuai.usercenter.core.exception.UserException;
import com.mockuai.usercenter.core.manager.TradeRecordManager;
import com.mockuai.usercenter.core.service.RequestContext;
import com.mockuai.usercenter.core.service.UserRequest;
import com.mockuai.usercenter.core.service.action.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by duke on 15/9/22.
 */
@Service
public class AddTradeRecordAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(AddTradeRecordAction.class);

    @Resource
    private TradeRecordManager tradeRecordManager;

    @Override
    public UserResponse execute(RequestContext context) throws UserException {
        log.info("Enter Action [{}]", getName());
        UserRequest request = context.getRequest();
        TradeRecordDTO tradeRecordDTO = (TradeRecordDTO) request.getParam("tradeRecordDTO");

        if (tradeRecordDTO == null) {
            throw new UserException(ResponseCode.P_PARAM_NULL, "tradeRecordDTO为空");
        }

        TradeRecordDTO existTradeRecordDTO = tradeRecordManager.queryTradeRecordByUserId(tradeRecordDTO.getUserId());
        // 如果已经用了对应的记录，则使该记录失效
        if (existTradeRecordDTO != null) {
            tradeRecordManager.deleteByUserId(existTradeRecordDTO.getUserId());
        }
        Long id = tradeRecordManager.addTradeRecord(tradeRecordDTO);
        log.info("Exit Action [{}]", getName());
        return new UserResponse(id);
    }

    @Override
    public String getName() {
        return ActionEnum.ADD_TRADE_RECORD.getActionName();
    }
}
