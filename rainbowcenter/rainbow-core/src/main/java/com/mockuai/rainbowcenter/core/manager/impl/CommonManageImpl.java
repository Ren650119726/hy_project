package com.mockuai.rainbowcenter.core.manager.impl;

import com.mockuai.rainbowcenter.common.constant.ERPOrderStatus;
import com.mockuai.rainbowcenter.core.manager.CommonManage;
import com.mockuai.tradecenter.common.domain.OrderDTO;
import com.mockuai.tradecenter.common.enums.EnumPaymentMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by lizg on 2016/7/16.
 */
public class CommonManageImpl implements CommonManage {

    private static final Logger logger = LoggerFactory.getLogger(CommonManageImpl.class);


    @Override
    public String paymentType(OrderDTO orderDTO) {

        String payTypeCode ="";
        logger.info("[{}] pay ment id:{}",orderDTO.getPaymentId());

        //支付宝
        if (EnumPaymentMethod.ALI_PAY_FOR_APP.getCode().equals(orderDTO.getPaymentId() + "") || EnumPaymentMethod.ALI_PAY_FOR_WAP.getCode().equals(orderDTO.getPaymentId() + "")) {
            payTypeCode = ERPOrderStatus.ALIPAY.getValue();

            //微信支付
        } else if (EnumPaymentMethod.WX_PAY_FOR_APP.getCode().equals(orderDTO.getPaymentId() + "") || EnumPaymentMethod.WX_PAY_FOR_WAP.getCode().equals(orderDTO.getPaymentId() + "")) {
            payTypeCode = ERPOrderStatus.WXPAY.getValue();

            //银联支付
        } else if (EnumPaymentMethod.UNION_PAY_FOR_APP.getCode().equals(orderDTO.getPaymentId() + "") || EnumPaymentMethod.UNION_PAY_FOR_WAP.getCode().equals(orderDTO.getPaymentId() + "")) {
            payTypeCode = ERPOrderStatus.YLPAY.getValue();

        } else if (EnumPaymentMethod.ACCOUNT_BALANCE_PAY.getCode().equals(orderDTO.getPaymentId() + "")) {
            payTypeCode = ERPOrderStatus.YEPAY.getValue();
        } else if (EnumPaymentMethod.HI_COIN_PAY.getCode().equals(orderDTO.getPaymentId() + "")) {
            payTypeCode = ERPOrderStatus.HBPAY.getValue();
        } else {
             logger.info("pay type not exist,paymentid:{}", orderDTO.getPaymentId());
             payTypeCode = ERPOrderStatus.ALIPAY.getValue();
        }
        return payTypeCode;
    }
}
