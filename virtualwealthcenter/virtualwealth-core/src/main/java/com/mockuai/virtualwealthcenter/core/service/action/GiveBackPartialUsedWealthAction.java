package com.mockuai.virtualwealthcenter.core.service.action;

import com.mockuai.appcenter.common.domain.AppInfoDTO;
import com.mockuai.virtualwealthcenter.common.api.BaseRequest;
import com.mockuai.virtualwealthcenter.common.api.Response;
import com.mockuai.virtualwealthcenter.common.api.VirtualWealthResponse;
import com.mockuai.virtualwealthcenter.common.api.VirtualWealthService;
import com.mockuai.virtualwealthcenter.common.constant.ActionEnum;
import com.mockuai.virtualwealthcenter.common.constant.ResponseCode;
import com.mockuai.virtualwealthcenter.common.constant.SourceType;
import com.mockuai.virtualwealthcenter.common.constant.WealthType;
import com.mockuai.virtualwealthcenter.common.constant.WealthUseStatus;
import com.mockuai.virtualwealthcenter.common.domain.dto.GrantedWealthDTO;
import com.mockuai.virtualwealthcenter.core.domain.UsedWealthDO;
import com.mockuai.virtualwealthcenter.core.domain.WealthAccountDO;
import com.mockuai.virtualwealthcenter.core.exception.VirtualWealthException;
import com.mockuai.virtualwealthcenter.core.manager.TradeManager;
import com.mockuai.virtualwealthcenter.core.manager.UsedWealthManager;
import com.mockuai.virtualwealthcenter.core.manager.WealthAccountManager;
import com.mockuai.virtualwealthcenter.core.service.RequestContext;
import com.mockuai.virtualwealthcenter.core.util.JsonUtil;
import com.mockuai.virtualwealthcenter.core.util.ResponseUtil;
import com.mockuai.virtualwealthcenter.core.util.VirtualWealthPreconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 回滚部分虚拟财富
 * 订单中只退部分商品，根据需求退还部分的虚拟财富
 * 不退嗨币
 * <p/>
 * 余额退款,会对应创建一条发放记录.余额的交易记录会将此作为一个收入记录
 * <p/>
 * Created by edgar.zr on 11/3/15.
 */
@Service
public class GiveBackPartialUsedWealthAction extends TransAction {

    private static final Logger LOGGER = LoggerFactory.getLogger(GiveBackPartialUsedWealthAction.class);

    @Autowired
    private UsedWealthManager usedWealthManager;
    @Autowired
    private WealthAccountManager wealthAccountManager;
    @Autowired
    private VirtualWealthService virtualWealthService;
    @Autowired
    private TradeManager tradeManager;

    @Override
    protected VirtualWealthResponse doTransaction(RequestContext context) throws VirtualWealthException {

        Long userId = (Long) context.getRequest().getParam("userId");
        Long orderId = (Long) context.getRequest().getParam("orderId");
        Long itemId = (Long) context.getRequest().getParam("itemId");
//        Long skuId = (Long) context.getRequest().getParam("skuId");
//        Long sellerId = (Long) context.getRequest().getParam("sellerId");
        // 需要退回的部分虚拟财富，目前未处理积分部分退款, <wealthType, amount>
        Map<Integer, Long> amounts = (Map<Integer, Long>) context.getRequest().getParam("amounts");
//        String itemName = (String) context.getRequest().getParam("itemName");
        AppInfoDTO appInfo = (AppInfoDTO) context.get("appInfo");

        //入参校验
        VirtualWealthPreconditions.checkNotNull(userId, "userId");
        VirtualWealthPreconditions.checkNotNull(orderId, "orderId");
        VirtualWealthPreconditions.checkNotNull(itemId, "itemId");
//        VirtualWealthPreconditions.checkNotNull(skuId, "skuId");
//        VirtualWealthPreconditions.checkNotNull(sellerId, "sellerId");
        VirtualWealthPreconditions.checkNotEmpty(amounts, "amounts");
//        VirtualWealthPreconditions.checkNotNull(itemName, "itemName");

        try {

            // 需要返回部分财富的记录
            List<UsedWealthDO> usedWealthDOs = new ArrayList<>();
            List<UsedWealthDO> givenBackUsedWealthDos;
            Long givenBackAmount;
            UsedWealthDO toGiveBackUsedWealth;
            GrantedWealthDTO grantedWealthDTO = null;

            for (Map.Entry<Integer, Long> amount : amounts.entrySet()) {

                WealthAccountDO wealthAccountDO = wealthAccountManager.getWealthAccount(userId, amount.getKey(), appInfo.getBizCode());
                if (wealthAccountDO == null) {
                    LOGGER.error("cannot found the wealth account, userId : {}, wealthType : {}", userId, amount.getKey());
                    return ResponseUtil.getResponse(ResponseCode.WEALTH_ACCOUNT_IS_NOT_FOUND);
                }

                UsedWealthDO usedWealthDO = usedWealthManager.getUsedWealthByWealthAccountId(wealthAccountDO.getId(), orderId);
                // 需要有完成支付的财富才能有退款的发生
                if (usedWealthDO.getStatus().intValue() != WealthUseStatus.USED.getValue()) {
                    LOGGER.error("cannot found the used wealth record, wealthAccountId : {}, orderId : {}",
                            wealthAccountDO.getId(), orderId);
                    return ResponseUtil.getResponse(ResponseCode.USED_WEALTH_NOT_EXISTS);
                }
                // 查询已经退还的财富
                givenBackUsedWealthDos = usedWealthManager.queryUsedWealthByParentId(usedWealthDO.getId());
                // 统计总共已经退掉的财富总值
                givenBackAmount = 0L;
                if (givenBackUsedWealthDos.size() > 0) {

                    for (UsedWealthDO usedWealth : givenBackUsedWealthDos) {
                        givenBackAmount += usedWealth.getAmount();
                    }
                }

                // 当前需要退还的财富总量超出了已使用的财富值
                if (amount.getValue().longValue() + givenBackAmount.longValue() > usedWealthDO.getAmount()) {
                    LOGGER.error("the amount of wealth to giving back is more than that of used wealth, used wealth : {}, given back : {}, wealthAccountId : {}, orderId : {}, usedWealthId : {}",
                            usedWealthDO.getAmount(), givenBackAmount, wealthAccountDO.getId(), orderId, usedWealthDO.getId());
                    return ResponseUtil.getResponse(ResponseCode.THE_GIVE_BACK_WEALTH_AMOUNT_EXCEED_THE_USED_WEALTH);
                }

                toGiveBackUsedWealth = new UsedWealthDO();
                toGiveBackUsedWealth.setBizCode(appInfo.getBizCode());
                toGiveBackUsedWealth.setWealthAccountId(wealthAccountDO.getId());
                toGiveBackUsedWealth.setUserId(userId);
                toGiveBackUsedWealth.setOrderId(orderId);
                toGiveBackUsedWealth.setStatus(WealthUseStatus.GIVE_BACK.getValue());
                toGiveBackUsedWealth.setDeleteMark(0);
                toGiveBackUsedWealth.setParentId(usedWealthDO.getId());
                toGiveBackUsedWealth.setItemId(itemId);
                toGiveBackUsedWealth.setAmount(amount.getValue());
                usedWealthDOs.add(toGiveBackUsedWealth);
                if (amount.getKey().intValue() == WealthType.VIRTUAL_WEALTH.getValue()) {// 将余额的退款记录转换为收入记录
                    grantedWealthDTO = new GrantedWealthDTO();
                    grantedWealthDTO.setWealthType(WealthType.VIRTUAL_WEALTH.getValue());
                    grantedWealthDTO.setAmount(amount.getValue());
                    grantedWealthDTO.setSourceType(SourceType.REFUND.getValue());
                    grantedWealthDTO.setReceiverId(userId);
                    grantedWealthDTO.setGranterId(0L);
                    grantedWealthDTO.setText("售后退款");
                    grantedWealthDTO.setItemId(itemId);
//                    grantedWealthDTO.setSkuId(skuId);
                }
            }

            usedWealthManager.giveBackUsedWealth(usedWealthDOs);

            if (grantedWealthDTO != null) {

//                OrderDTO orderDTO = tradeManager.getOrder(orderId, userId, appInfo.getAppKey());
//                for (OrderItemDTO orderItemDTO : orderDTO.getOrderItems()) {
//                    if (orderItemDTO.getItemId().longValue() == itemId.longValue()) {
//                        grantedWealthDTO.setText(orderItemDTO.getItemName());
//                        break;
//                    }
//                }
                BaseRequest baseRequest = new BaseRequest();
                baseRequest.setCommand(ActionEnum.DISTRIBUTOR_GRANT.getActionName());
                baseRequest.setParam("grantedWealthDTO", grantedWealthDTO);
                baseRequest.setParam("appKey", appInfo.getAppKey());
                Response<Boolean> response = virtualWealthService.execute(baseRequest);
                if (!response.isSuccess()) {
                    LOGGER.error("error to add incoming log, grantedWealthDTO : {}", JsonUtil.toJson(grantedWealthDTO));
                }
            }

            return new VirtualWealthResponse(ResponseCode.SUCCESS);
        } catch (VirtualWealthException e) {
            return new VirtualWealthResponse(e.getCode(), e.getMessage());
        }
    }

    @Override
    public String getName() {
        return ActionEnum.GIVE_BACK_PARTIAL_USED_WEALTH.getActionName();
    }
}