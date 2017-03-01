package com.mockuai.tradecenter.core.service.action.order; /**
 * create by 冠生
 *
 * @date #{DATE}
 **/

import com.mockuai.tradecenter.common.api.Request;
import com.mockuai.tradecenter.common.api.TradeResponse;
import com.mockuai.tradecenter.common.constant.ActionEnum;
import com.mockuai.tradecenter.common.domain.SaleRankDTO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.OrderDiscountInfoManager;
import com.mockuai.tradecenter.core.service.RequestContext;
import com.mockuai.tradecenter.core.service.ResponseUtils;
import com.mockuai.tradecenter.core.service.action.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by guansheng on 2016/7/21.
 */
@Service
public class QuerySaleRank implements Action {
    @Autowired
    private OrderDiscountInfoManager orderDiscountInfoManager;

    @Override
    public TradeResponse execute(RequestContext context) throws TradeException {
        Request request =  context.getRequest();
        long activityId = (long) request.getParam("activityId");
        List<SaleRankDTO> saleRankDTOList =  orderDiscountInfoManager.querySaleRank(activityId);
        return   ResponseUtils.getSuccessResponse(saleRankDTOList);
    }

    @Override
    public String getName() {
        return ActionEnum.QUERY_SALE_RANK.getActionName();
    }
}
