package com.mockuai.usercenter.core.service.action.trade;

import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.Request;
import com.mockuai.usercenter.common.api.UserResponse;
import com.mockuai.usercenter.common.constant.ResponseCode;
import com.mockuai.usercenter.common.dto.TradeRecordDTO;
import com.mockuai.usercenter.common.qto.TradeRecordQTO;
import com.mockuai.usercenter.core.exception.UserException;
import com.mockuai.usercenter.core.manager.TradeRecordManager;
import com.mockuai.usercenter.core.service.RequestContext;
import com.mockuai.usercenter.core.service.action.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by duke on 15/9/30.
 */
@Service
public class QueryTradeRecordAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(QueryTradeRecordAction.class);

    @Resource
    TradeRecordManager tradeRecordManager;

    @Override
    public UserResponse execute(RequestContext context) throws UserException {
        log.info("Enter Action [{}]", getName());
        Request request = context.getRequest();
        TradeRecordQTO tradeRecordQTO = (TradeRecordQTO) request.getParam("tradeRecordQTO");

        if (tradeRecordQTO == null) {
            log.error("tradeRecordQTO is null");
            throw new UserException(ResponseCode.P_PARAM_NULL);
        }

        List<TradeRecordDTO> tradeRecordDTOList = tradeRecordManager.query(tradeRecordQTO);
        Long totalCount = tradeRecordManager.totalCount(tradeRecordQTO);

        log.info("Exit Action [{}]", getName());
        return new UserResponse(tradeRecordDTOList, totalCount);
    }

    @Override
    public String getName() {
        return ActionEnum.QUERY_TRADE_RECORD.getActionName();
    }
}
