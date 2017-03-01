package com.mockuai.virtualwealthcenter.core.service.action;

import com.mockuai.appcenter.common.domain.AppInfoDTO;
import com.mockuai.tradecenter.common.domain.OrderDTO;
import com.mockuai.tradecenter.common.domain.OrderQTO;
import com.mockuai.virtualwealthcenter.common.api.VirtualWealthResponse;
import com.mockuai.virtualwealthcenter.common.constant.ActionEnum;
import com.mockuai.virtualwealthcenter.common.constant.GrantedWealthStatus;
import com.mockuai.virtualwealthcenter.common.constant.ResponseCode;
import com.mockuai.virtualwealthcenter.common.constant.WealthType;
import com.mockuai.virtualwealthcenter.common.constant.WealthUseStatus;
import com.mockuai.virtualwealthcenter.common.domain.dto.WithdrawalsItemDTO;
import com.mockuai.virtualwealthcenter.common.domain.dto.mop.MopWealthLogDTO;
import com.mockuai.virtualwealthcenter.common.domain.qto.GrantedWealthQTO;
import com.mockuai.virtualwealthcenter.common.domain.qto.UsedWealthQTO;
import com.mockuai.virtualwealthcenter.common.domain.qto.WithdrawalsItemQTO;
import com.mockuai.virtualwealthcenter.core.domain.GrantedWealthDO;
import com.mockuai.virtualwealthcenter.core.domain.UsedWealthDO;
import com.mockuai.virtualwealthcenter.core.domain.WealthAccountDO;
import com.mockuai.virtualwealthcenter.core.exception.VirtualWealthException;
import com.mockuai.virtualwealthcenter.core.manager.GrantedWealthManager;
import com.mockuai.virtualwealthcenter.core.manager.TradeManager;
import com.mockuai.virtualwealthcenter.core.manager.UsedWealthManager;
import com.mockuai.virtualwealthcenter.core.manager.WealthAccountManager;
import com.mockuai.virtualwealthcenter.core.manager.WithdrawalsItemManager;
import com.mockuai.virtualwealthcenter.core.service.RequestContext;
import com.mockuai.virtualwealthcenter.core.util.JsonUtil;
import com.mockuai.virtualwealthcenter.core.util.VirtualWealthPreconditions;
import com.mockuai.virtualwealthcenter.core.util.VirtualWealthUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by edgar.zr on 5/16/2016.
 */
@Service
public class ListRecordOfWealthAction extends TransAction {

    private static final Logger LOGGER = LoggerFactory.getLogger(ListRecordOfWealthAction.class);

    @Autowired
    private GrantedWealthManager grantedWealthManager;
    @Autowired
    private UsedWealthManager usedWealthManager;
    @Autowired
    private WealthAccountManager wealthAccountManager;
    @Autowired
    private WithdrawalsItemManager withdrawalsItemManager;
    @Autowired
    private TradeManager tradeManager;

    @Override
    protected VirtualWealthResponse doTransaction(RequestContext context) throws VirtualWealthException {
        Integer tradeType = (Integer) context.getRequest().getParam("tradeType");
        Integer wealthType = (Integer) context.getRequest().getParam("wealthType");
        Integer offset = (Integer) context.getRequest().getParam("offset");
        Integer count = (Integer) context.getRequest().getParam("count");
        Long userId = (Long) context.getRequest().getParam("userId");
        AppInfoDTO appInfoDTO = (AppInfoDTO) context.get("appInfo");

        VirtualWealthPreconditions.checkNotNull(tradeType, "tradeType");
        VirtualWealthPreconditions.checkNotNull(wealthType, "wealthType");
        if (offset == null) {
            offset = 0;
        }
        if (count == null || count > 200) {
            count = 20;
        }
        if (WealthType.getByValue(wealthType) == null) {
            return new VirtualWealthResponse(ResponseCode.PARAMETER_ERROR, "wealthType is invalid");
        }
        VirtualWealthPreconditions.checkNotNull(userId, "userId");
        WealthAccountDO wealthAccountDO = wealthAccountManager.getWealthAccount(userId, wealthType, appInfoDTO.getBizCode());

        List<MopWealthLogDTO> mopWealthLogDTOs = new ArrayList<>();
        MopWealthLogDTO mopWealthLogDTO;
        Map<String, Object> map = new HashMap<>();

        if (tradeType.intValue() == 0) {// 收入
            GrantedWealthQTO grantedWealthQTO = new GrantedWealthQTO();
            grantedWealthQTO.setBizCode(appInfoDTO.getBizCode());
            grantedWealthQTO.setWealthId(wealthAccountDO.getId());
            grantedWealthQTO.setOffset(offset);
            grantedWealthQTO.setCount(count);
            grantedWealthQTO.setNonZero(1);
            grantedWealthQTO.setNonStatus(GrantedWealthStatus.EXPIRED.getValue());
            List<GrantedWealthDO> grantedWealthDOs = grantedWealthManager.queryGrantedWealth(grantedWealthQTO);
            for (GrantedWealthDO grantedWealthDO : grantedWealthDOs) {
                mopWealthLogDTO = new MopWealthLogDTO();
                mopWealthLogDTO.setText(genText(grantedWealthDO));
                mopWealthLogDTO.setAmount(grantedWealthDO.getAmount());
                mopWealthLogDTO.setStatus(grantedWealthDO.getStatus());
                mopWealthLogDTO.setTime(DateFormatUtils.format(grantedWealthDO.getGrantedTime(), "yyyy-MM-dd HH:mm:ss"));
                mopWealthLogDTOs.add(mopWealthLogDTO);
            }
            map.put("totalCount", grantedWealthQTO.getTotalCount());
        } else if (tradeType.intValue() == 1) {// 支出
            List<Long> orderIds = new ArrayList<>();
            Map<Long, MopWealthLogDTO> orderIdKeyLog = new HashMap<>();
            UsedWealthQTO usedWealthQTO = new UsedWealthQTO();
            usedWealthQTO.setWealthAccountId(wealthAccountDO.getId());
            usedWealthQTO.setBizCode(appInfoDTO.getBizCode());
            usedWealthQTO.setStatus(WealthUseStatus.USED.getValue());
            usedWealthQTO.setOffset(offset);
            usedWealthQTO.setCount(count);
            List<UsedWealthDO> usedWealthDOs = usedWealthManager.queryUsedWealth(usedWealthQTO);
            for (UsedWealthDO usedWealthDO : usedWealthDOs) {
                mopWealthLogDTO = new MopWealthLogDTO();
                mopWealthLogDTO.setTime(DateFormatUtils.format(usedWealthDO.getGmtCreated(), "yyyy-MM-dd HH:mm:ss"));
                if (usedWealthDO.getOrderId() != null) {
                    orderIds.add(usedWealthDO.getOrderId());
                    orderIdKeyLog.put(usedWealthDO.getOrderId(), mopWealthLogDTO);
                }
                mopWealthLogDTO.setOrderSN(usedWealthDO.getOrderSN());
                mopWealthLogDTO.setAmount(usedWealthDO.getAmount());
                if (usedWealthDO.getOrderId() != null) {
                    mopWealthLogDTO.setText(wealthType.intValue() == WealthType.HI_COIN.getValue() ? "兑换" : "购物");
                } else {
                    mopWealthLogDTO.setText("嗨币过期");
                }
                mopWealthLogDTOs.add(mopWealthLogDTO);
            }
            OrderQTO orderQTO = new OrderQTO();
            orderQTO.setOrderIds(orderIds);
            try {
                if (orderIds.isEmpty() == false) {
                    List<OrderDTO> orderDTOs = tradeManager.queryOrder(orderQTO, appInfoDTO.getAppKey());
                    if (orderDTOs == null || orderDTOs.size() != orderIds.size()) {
                        LOGGER.error("the size of orderIds are not identical to orderDTOs, orderIds : {}",
                                JsonUtil.toJson(orderIds));
                    } else {
                        Map<Long, OrderDTO> orderIdKey = new HashMap<>();
                        for (OrderDTO orderDTO : orderDTOs) {
                            orderIdKey.put(orderDTO.getId(), orderDTO);
                        }
                        OrderDTO orderDTO;
                        for (Map.Entry<Long, MopWealthLogDTO> entry : orderIdKeyLog.entrySet()) {
                            orderDTO = orderIdKey.get(entry.getKey());
                            entry.getValue().setOrderUid(orderDTO.getSellerId() + "_" + orderDTO.getUserId() + "_" + orderDTO.getId());
                        }
                    }
                }
            } catch (VirtualWealthException e) {
                LOGGER.error("error to generate orderUid");
            }
            map.put("totalCount", usedWealthQTO.getTotalCount());
        } else if (tradeType.intValue() == 2) {// 提现
            WithdrawalsItemQTO withdrawalsItemQTO = new WithdrawalsItemQTO();
            withdrawalsItemQTO.setUserId(userId);
            withdrawalsItemQTO.setOffset(offset);
            withdrawalsItemQTO.setCount(count);
            List<WithdrawalsItemDTO> withdrawalsItemDTOs = withdrawalsItemManager.queryWithdrawalsItemDTO(withdrawalsItemQTO);
            for (WithdrawalsItemDTO withdrawalsItemDTO : withdrawalsItemDTOs) {
                mopWealthLogDTO = new MopWealthLogDTO();
                mopWealthLogDTO.setText("提现");
                mopWealthLogDTO.setAmount(withdrawalsItemDTO.getWithdrawalsAmount());
                mopWealthLogDTO.setStatus(withdrawalsItemDTO.getWithdrawalsStatus().intValue());
                mopWealthLogDTO.setWithdrawalsNumber(withdrawalsItemDTO.getWithdrawalsNumber());
                mopWealthLogDTO.setTime(
                        DateFormatUtils.format(withdrawalsItemDTO.getWithdrawalsTime(), "yyyy-MM-dd HH:mm:ss"));
                mopWealthLogDTO.setRefusalReason(withdrawalsItemDTO.getWithdrawalsRefuse());
                mopWealthLogDTOs.add(mopWealthLogDTO);
            }
            map.put("totalCount", withdrawalsItemQTO.getTotalCount());
        }
        map.put("data", mopWealthLogDTOs);
        return VirtualWealthUtils.getSuccessResponse(map);
    }

    public String genText(GrantedWealthDO grantedWealthDO) {

//        if (grantedWealthDO.getSourceType() == SourceType.SELL.getValue()
//                || grantedWealthDO.getSourceType() == SourceType.GROUP_SELL.getValue()) {
//            return "销售佣金:" + grantedWealthDO.getText();
//        } else if (grantedWealthDO.getSourceType() == SourceType.REFUND.getValue()) {
//            return grantedWealthDO.getText();
//        } else if (grantedWealthDO.getSourceType() == SourceType.SHOP.getValue()) {
//            return "开店佣金:" + grantedWealthDO.getText();
//        } else {
//            return "~";
//        }
        return grantedWealthDO.getText();
    }

    @Override
    public String getName() {
        return ActionEnum.LIST_RECORD_OF_WEALTH.getActionName();
    }
}