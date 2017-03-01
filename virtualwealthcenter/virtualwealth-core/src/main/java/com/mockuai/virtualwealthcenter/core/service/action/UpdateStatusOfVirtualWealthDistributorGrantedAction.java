package com.mockuai.virtualwealthcenter.core.service.action;

import com.mockuai.virtualwealthcenter.common.api.VirtualWealthResponse;
import com.mockuai.virtualwealthcenter.common.constant.ActionEnum;
import com.mockuai.virtualwealthcenter.common.constant.GrantedWealthStatus;
import com.mockuai.virtualwealthcenter.common.constant.ResponseCode;
import com.mockuai.virtualwealthcenter.common.domain.qto.GrantedWealthQTO;
import com.mockuai.virtualwealthcenter.core.domain.GrantedWealthDO;
import com.mockuai.virtualwealthcenter.core.exception.VirtualWealthException;
import com.mockuai.virtualwealthcenter.core.manager.GrantedWealthManager;
import com.mockuai.virtualwealthcenter.core.manager.WealthAccountManager;
import com.mockuai.virtualwealthcenter.core.service.RequestContext;
import com.mockuai.virtualwealthcenter.core.util.JsonUtil;
import com.mockuai.virtualwealthcenter.core.util.VirtualWealthPreconditions;
import com.mockuai.virtualwealthcenter.core.util.VirtualWealthUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 更新嗨币状态或者余额,分佣
 * <p/>
 * Created by edgar.zr on 5/13/2016.
 */
@Service
public class UpdateStatusOfVirtualWealthDistributorGrantedAction extends TransAction {

    private static final Logger LOGGER = LoggerFactory.getLogger(UpdateStatusOfVirtualWealthDistributorGrantedAction.class);

    @Autowired
    private GrantedWealthManager grantedWealthManager;
    @Autowired
    private WealthAccountManager wealthAccountManager;

    @Override
    protected VirtualWealthResponse doTransaction(RequestContext context) throws VirtualWealthException {
        Long orderId = (Long) context.getRequest().getParam("orderId");
        Long skuId = (Long) context.getRequest().getParam("skuId");
        Integer sourceType = (Integer) context.getRequest().getParam("sourceType");
        Integer status = (Integer) context.getRequest().getParam("status");

        VirtualWealthPreconditions.checkNotNull(orderId, "orderId");
        VirtualWealthPreconditions.checkNotNull(sourceType, "sourceType");
        VirtualWealthPreconditions.checkNotNull(status, "status");
        LOGGER.info("orderId : {}, skuId : {}, sourceType : {}, status : {}",
                orderId, skuId, sourceType, status);

        GrantedWealthQTO grantedWealthQTO = new GrantedWealthQTO();
        grantedWealthQTO.setOrderId(orderId);
        grantedWealthQTO.setSourceType(sourceType);
        grantedWealthQTO.setSkuId(skuId);
        grantedWealthQTO.setStatus(GrantedWealthStatus.FROZEN.getValue());
        List<GrantedWealthDO> grantedWealthDOs = grantedWealthManager.queryGrantedWealth(grantedWealthQTO);
        LOGGER.info("grantedWealthDOs : {}", JsonUtil.toJson(grantedWealthDOs));
        if (grantedWealthDOs.isEmpty()) {
            LOGGER.error("empty grantedWealthDOs to update status of grant of distributor, orderId : {}, sourceType : {}, status : {}",
                    orderId, sourceType, status);
            return VirtualWealthUtils.getSuccessResponse();
        }

        // 更新发放记录状态
        List<GrantedWealthDO> toUpdate = new ArrayList<>();
        GrantedWealthDO temp;
        for (GrantedWealthDO grantedWealthDO : grantedWealthDOs) {
            temp = new GrantedWealthDO();
            temp.setId(grantedWealthDO.getId());
            temp.setStatus(status);
            toUpdate.add(temp);
        }
        int opCount = grantedWealthManager.batchUpdateStatus(toUpdate);

        if (opCount != grantedWealthDOs.size()) {
            LOGGER.error("error to update grantedWealth, grantedWealthDOs : {}", JsonUtil.toJson(toUpdate));
            throw new VirtualWealthException(ResponseCode.BIZ_E_UPDATE_GRANTED_WEALTH_FAILED);
        }

        // 减少冻结的虚拟财富
        for (GrantedWealthDO grantedWealthDO : grantedWealthDOs) {
            opCount = wealthAccountManager.increaseFrozenBalance(grantedWealthDO.getWealthId(), -grantedWealthDO.getAmount());
            if (opCount != 1) {
                LOGGER.error("error to increaseFrozenBalance, wealthId : {}, amount : {}",
                        grantedWealthDO.getWealthId(), grantedWealthDO.getAmount());
                throw new VirtualWealthException(ResponseCode.BIZ_E_UPDATE_FROZEN_BALANCE_FAILED);
            }
        }
        if (status.intValue() == GrantedWealthStatus.TRANSFERRED.getValue()) {// 正常订单
            for (GrantedWealthDO grantedWealthDO : grantedWealthDOs) {
                // 增加可用的虚拟财富
                opCount = wealthAccountManager.increaseAccountBalance(grantedWealthDO.getWealthId(),
                        grantedWealthDO.getReceiverId(), grantedWealthDO.getAmount());
                if (opCount != 1) {
                    LOGGER.error("error to increaseAccountBalance, wealthId : {}, receiverId : {}, amount : {}",
                            grantedWealthDO.getWealthId(), grantedWealthDO.getReceiverId(), grantedWealthDO.getAmount());
                    throw new VirtualWealthException(ResponseCode.BIZ_E_UPDATE_FROZEN_BALANCE_FAILED);
                }
            }
        } else if (status.intValue() == GrantedWealthStatus.CANCEL.getValue()) { // 取消订单,总累计中的冻结虚拟财富需要去除
            for (GrantedWealthDO grantedWealthDO : grantedWealthDOs) {
                opCount = wealthAccountManager.increaseTotalBalance(grantedWealthDO.getWealthId(), -grantedWealthDO.getAmount());
                if (opCount != 1) {
                    LOGGER.error("error to decrease total balance, wealthAccountId : {}, total : {}",
                            grantedWealthDO.getWealthId(), grantedWealthDO.getAmount());
                    return new VirtualWealthResponse(ResponseCode.BIZ_E_UPDATE_TOTAL_BALANCE_FAILED);
                }
            }
        }
        return VirtualWealthUtils.getSuccessResponse();
    }

    @Override
    public String getName() {
        return ActionEnum.UPDATE_STATUS_OF_VIRTUAL_WEALTH_DISTRIBUTOR_GRANTED.getActionName();
    }
}