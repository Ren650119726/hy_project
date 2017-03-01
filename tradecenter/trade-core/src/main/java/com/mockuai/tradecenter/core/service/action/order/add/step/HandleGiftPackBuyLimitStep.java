package com.mockuai.tradecenter.core.service.action.order.add.step;

import com.mockuai.distributioncenter.common.domain.dto.DistShopDTO;
import com.mockuai.tradecenter.client.OrderClient;
import com.mockuai.tradecenter.common.api.TradeResponse;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.common.domain.OrderDTO;
import com.mockuai.tradecenter.common.domain.OrderQTO;
import com.mockuai.tradecenter.common.enums.EnumOrderStatus;
import com.mockuai.tradecenter.common.enums.EnumOrderType;
import com.mockuai.tradecenter.core.domain.OrderDO;
import com.mockuai.tradecenter.core.domain.OrderItemDO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.DistributionManager;
import com.mockuai.tradecenter.core.manager.OrderManager;
import com.mockuai.tradecenter.core.service.ResponseUtils;
import com.mockuai.tradecenter.core.util.JsonUtil;

import java.util.*;

/**
 * 处理礼包订单唯一性限制：
 * （1）如果该用户有一个已经支付的礼包订单，那么直接禁止下单，返回错误提示
 * （2）如果该用户有一个未支付的礼包订单，则直接将之前未支付的礼包订单取消掉
 * Created by zengzhangqiang on 6/6/16.
 */
public class HandleGiftPackBuyLimitStep extends TradeBaseStep {

    @Override
    public StepName getName() {
        return StepName.HANDLE_GIFT_PACK_BUY_LIMIT_STEP;
    }

    @Override
    public TradeResponse execute() {
        OrderDO mainOrder = (OrderDO) this.getAttr("mainOrder");
        String appKey = (String) this.getAttr("appKey");

        //如果订单不是礼包订单，则直接跳过
        if (mainOrder.getType() != EnumOrderType.GIFT_PACK.getCode()) {
            return ResponseUtils.getSuccessResponse();
        }

        //查询礼包订单列表
        OrderManager orderManager = (OrderManager) this.getBean("orderManager");
        OrderQTO orderQTO = new OrderQTO();
        orderQTO.setUserId(mainOrder.getUserId());
        orderQTO.setType(EnumOrderType.GIFT_PACK.getCode());
        List<OrderDO> orderList = null;
        try{
        	// TODO 查询优化
            orderList= orderManager.queryUserOrders(orderQTO);
        }catch(TradeException e){
            logger.error("error to query user orders,orderQTO:{}", JsonUtil.toJson(orderQTO), e);
            return new TradeResponse(ResponseCode.SYS_E_SERVICE_EXCEPTION);
        }

        //如果原先没有礼包订单，则校验通过
        if (orderList == null || orderList.isEmpty()) {
            return ResponseUtils.getSuccessResponse();
        }

        //校验是否存在已支付成功的礼包订单
        for (OrderDO orderDO : orderList) {
            //如果之前有成功购买过开店礼包，则直接返回限购错误
            if (orderDO.getOrderStatus().intValue() >= Integer.valueOf(EnumOrderStatus.PAID.getCode()).intValue()) {
                return new TradeResponse(ResponseCode.BIZ_E_GIFT_PACK_ORDER_BUY_LIMIT);
            }

            //如果之前有下单未支付的礼包订单，则把未支付的礼包订单取消掉
            if (orderDO.getOrderStatus().intValue() == Integer.valueOf(EnumOrderStatus.UNPAID.getCode()).intValue()) {
                //FIXME 由于直接依赖自己的情况比较少见，所以暂时先直接使用orderClient，不额外封装manager
                OrderClient orderClient = (OrderClient) this.getBean("orderClient");
                //取消订单
                orderClient.cancelOrder(orderDO.getId(), orderDO.getUserId(),
                        "[AddOrder.HandleGiftPackBuyLimitStep]系统自动取消无效的礼包订单", appKey);
            }
        }

        return ResponseUtils.getSuccessResponse();
    }
}