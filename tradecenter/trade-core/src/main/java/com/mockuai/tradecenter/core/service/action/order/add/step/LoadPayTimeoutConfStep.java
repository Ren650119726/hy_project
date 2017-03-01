package com.mockuai.tradecenter.core.service.action.order.add.step;

import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemSkuDTO;
import com.mockuai.tradecenter.common.api.TradeResponse;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.common.domain.OrderDTO;
import com.mockuai.tradecenter.common.domain.OrderItemDTO;
import com.mockuai.tradecenter.core.dao.TradeConfigDAO;
import com.mockuai.tradecenter.core.domain.OrderItemDO;
import com.mockuai.tradecenter.core.domain.TradeConfigDO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.ItemManager;
import com.mockuai.tradecenter.core.manager.TradeConfigManager;
import com.mockuai.tradecenter.core.service.ResponseUtils;
import com.mockuai.tradecenter.core.util.TradeUtil;

import java.util.*;

/**
 * Created by zengzhangqiang on 5/20/16.
 */
public class LoadPayTimeoutConfStep extends TradeBaseStep {
    @Override
    public StepName getName() {
        return StepName.LOAD_PAY_TIMEOUT_CONF_STEP;
    }

    @Override
    public TradeResponse execute() {
        TradeConfigManager tradeConfigManager = (TradeConfigManager) this.getBean("tradeConfigManager");
        String bizCode = (String) this.getAttr("bizCode");

        //默认支付超时时间设置为10分钟
        int payTimeout = 10;
        try {
            //
            TradeConfigDO tradeConfigDO = tradeConfigManager.getTradeConfig(bizCode, "cancel_timeout_minutes");
            if (tradeConfigDO != null) {
                payTimeout = Integer.valueOf(tradeConfigDO.getAttrValue());
            }
        } catch (TradeException e) {
            logger.error("error to get tradeConf", e);
        } catch (Exception e) {
            logger.error("error to get tradeConf", e);
        }

        this.setAttr("payTimeout", payTimeout*60);

        return ResponseUtils.getSuccessResponse();
    }
}
