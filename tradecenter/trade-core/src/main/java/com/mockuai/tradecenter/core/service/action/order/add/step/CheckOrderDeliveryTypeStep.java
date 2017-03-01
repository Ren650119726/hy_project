package com.mockuai.tradecenter.core.service.action.order.add.step;

import com.mockuai.tradecenter.common.api.TradeResponse;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.common.domain.OrderDTO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.UserManager;
import com.mockuai.tradecenter.core.service.RequestContext;
import com.mockuai.tradecenter.core.service.ResponseUtils;
import com.mockuai.usercenter.common.dto.UserConsigneeDTO;

/**
 * 订单发货方式校验
 * Created by zengzhangqiang on 5/19/16.
 */
public class CheckOrderDeliveryTypeStep extends TradeBaseStep{
    @Override
    public StepName getName() {
        return StepName.CHECK_ORDER_DELIVERY_TYPE_STEP;
    }

    @Override
    public TradeResponse execute() {
        OrderDTO orderDTO = (OrderDTO) this.getParam("orderDTO");
        int deliveryId = orderDTO.getDeliveryId();
        long userId = orderDTO.getUserId(); // 用户id
        String appKey = (String) this.getAttr("appKey");

        //发货方式为快递配送或者门店配送时，需要验证收货地址的有效性
        if (deliveryId == 0 || deliveryId == 1 || deliveryId == 3) {
            //收货地址信息验证
            try {
                //用户地址id
                long consigneeId = orderDTO.getOrderConsigneeDTO().getConsigneeId();

                UserManager userManager = (UserManager) this.getBean("userManager");
                UserConsigneeDTO consigneeDTO = userManager.getUserConsignee(userId, consigneeId, appKey);

                if (consigneeDTO == null) {
                    return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_CONSIGNEE_INVALID);
                }

                //将查询到的收货人信息放入管道上下文，便于后续节点的处理
                this.setAttr("consigneeDTO", consigneeDTO);
            } catch (TradeException e) {
                logger.error(e.getResponseCode().getComment(), e);
                return ResponseUtils.getFailResponse(e.getResponseCode(), "getUserInfo error");
            }
        }

        //门店配送与门店自提的情况下
        if (deliveryId == 2 || deliveryId == 3){
            if(null == orderDTO.getOrderStoreDTO() || null == orderDTO.getOrderStoreDTO().getStoreId()){
                return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING, "storeId is null");
            }
        }

        return ResponseUtils.getSuccessResponse();
    }
}
