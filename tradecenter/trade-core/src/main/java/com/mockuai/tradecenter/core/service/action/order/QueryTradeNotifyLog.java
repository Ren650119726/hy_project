package com.mockuai.tradecenter.core.service.action.order; /**
 * create by 冠生
 *
 * @date #{DATE}
 **/

import com.mockuai.tradecenter.common.api.Request;
import com.mockuai.tradecenter.common.api.TradeResponse;
import com.mockuai.tradecenter.common.constant.ActionEnum;
import com.mockuai.tradecenter.common.domain.TradeNotifyLogDTO;
import com.mockuai.tradecenter.common.domain.TradeNotifyLogQTO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.TradeNotifyLogManager;
import com.mockuai.tradecenter.core.service.RequestContext;
import com.mockuai.tradecenter.core.service.ResponseUtils;
import com.mockuai.tradecenter.core.service.action.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Created by guansheng on 2016/7/13
 * 查询第三方资金流水
 */
@Service
public class QueryTradeNotifyLog implements Action {

    @Autowired
    private TradeNotifyLogManager tradeNotifyLogManager;

    @Override
    public TradeResponse execute(RequestContext context) throws TradeException {
        Request request = context.getRequest();
        TradeNotifyLogQTO  tradeNotifyLogQTO = (TradeNotifyLogQTO) request.getParam("tradeNotifyLogQTO");
        try{
            List<TradeNotifyLogDTO> tradeNotifyLogDTO =  tradeNotifyLogManager.queryTradeNotifyLog(tradeNotifyLogQTO);
            return ResponseUtils.getSuccessResponse(tradeNotifyLogDTO);
        }catch ( TradeException e){
            return ResponseUtils.getFailResponse(e.getResponseCode(),e.getMessage());
        }

    }

    @Override
    public String getName() {
        return ActionEnum.QUERY_TRADE_NOTIFY_LOG.getActionName();
    }
}
