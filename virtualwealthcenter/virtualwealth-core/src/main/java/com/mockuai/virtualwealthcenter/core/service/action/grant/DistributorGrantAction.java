package com.mockuai.virtualwealthcenter.core.service.action.grant;

import com.mockuai.appcenter.common.domain.AppInfoDTO;
import com.mockuai.virtualwealthcenter.common.api.VirtualWealthResponse;
import com.mockuai.virtualwealthcenter.common.constant.*;
import com.mockuai.virtualwealthcenter.common.domain.dto.GrantedWealthDTO;
import com.mockuai.virtualwealthcenter.common.domain.dto.VirtualWealthDTO;
import com.mockuai.virtualwealthcenter.core.domain.WealthAccountDO;
import com.mockuai.virtualwealthcenter.core.exception.VirtualWealthException;
import com.mockuai.virtualwealthcenter.core.manager.GrantedWealthManager;
import com.mockuai.virtualwealthcenter.core.manager.VirtualWealthManager;
import com.mockuai.virtualwealthcenter.core.manager.WealthAccountManager;
import com.mockuai.virtualwealthcenter.core.service.RequestContext;
import com.mockuai.virtualwealthcenter.core.service.action.TransAction;
import com.mockuai.virtualwealthcenter.core.util.ModelUtil;
import com.mockuai.virtualwealthcenter.core.util.VirtualWealthPreconditions;
import com.mockuai.virtualwealthcenter.core.util.VirtualWealthUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 分佣发放 嗨币 余额
 * 发放途径: 发放来源 7–>开店 8–>销售 9–>团队销售 10->余额退款
 * <p/>
 * Created by edgar.zr on 5/13/2016.
 */
@Service
public class DistributorGrantAction extends TransAction {

    private static final Logger LOGGER = LoggerFactory.getLogger(DistributorGrantAction.class);

    @Autowired
    private GrantedWealthManager grantedWealthManager;
    @Autowired
    private WealthAccountManager wealthAccountManager;
    @Autowired
    private VirtualWealthManager virtualWealthManager;

    @Override
    protected VirtualWealthResponse doTransaction(RequestContext context) throws VirtualWealthException {
        GrantedWealthDTO grantedWealthDTO = (GrantedWealthDTO) context.getRequest().getParam("grantedWealthDTO");
        AppInfoDTO appInfoDTO = (AppInfoDTO) context.get("appInfo");

        VirtualWealthPreconditions.checkNotNull(grantedWealthDTO, "grantedWealthDTO");
        grantedWealthDTO.setBizCode(appInfoDTO.getBizCode());
        VirtualWealthPreconditions.checkNotNull(grantedWealthDTO.getWealthType(), "wealthType");
        if (WealthType.getByValue(grantedWealthDTO.getWealthType()) == null) {
            throw new VirtualWealthException(ResponseCode.PARAMETER_ERROR, "wealthType 非法");
        }
        VirtualWealthPreconditions.checkNotNull(grantedWealthDTO.getSourceType(), "sourceType");
        if (SourceType.getByValue(grantedWealthDTO.getSourceType()) == null) {
            throw new VirtualWealthException(ResponseCode.PARAMETER_ERROR, "sourceType 非法");
        }
        VirtualWealthPreconditions.checkNotNull(grantedWealthDTO.getAmount(), "amount");
        VirtualWealthPreconditions.checkNotNull(grantedWealthDTO.getReceiverId(), "receiverId");
        VirtualWealthPreconditions.checkNotNull(grantedWealthDTO.getGranterId(), "granterId");
        VirtualWealthPreconditions.checkNotNull(grantedWealthDTO.getText(), "text");
        if ((grantedWealthDTO.getSourceType().intValue() == SourceType.SELL.getValue()
                || grantedWealthDTO.getSourceType().intValue() == SourceType.GROUP_SELL.getValue())) {
            VirtualWealthPreconditions.checkNotNull(grantedWealthDTO.getOrderId(), "orderId");
        }

        // 根据平台和财富类型
        VirtualWealthDTO virtualWealthDTO =
                virtualWealthManager.getVirtualWealth(appInfoDTO.getBizCode(), grantedWealthDTO.getGranterId(), grantedWealthDTO.getWealthType());

        //虚拟财富的业务状态校验，总量必须少于（已发放数量＋待发放数量）或者财富总量为无限
        if (virtualWealthDTO.getAmount() < virtualWealthDTO.getGrantedAmount() + grantedWealthDTO.getAmount()
                && virtualWealthDTO.getAmount().longValue() != -1) {
            return new VirtualWealthResponse(ResponseCode.VIRTUAL_WEALTH_NOT_ENOUGH);
        }

        //查询用户虚拟财富账户，如果账户不存在则创建
        WealthAccountDO wealthAccountDO =
                wealthAccountManager.getWealthAccount(grantedWealthDTO.getReceiverId(), grantedWealthDTO.getWealthType(), appInfoDTO.getBizCode());
        if (wealthAccountDO == null) {
            WealthAccountDO userWealthAccount = new WealthAccountDO();
            userWealthAccount.setBizCode(appInfoDTO.getBizCode());
            userWealthAccount.setWealthType(grantedWealthDTO.getWealthType());
            userWealthAccount.setUserId(grantedWealthDTO.getReceiverId());
            try {
                Long wealthAccountId = wealthAccountManager.addWealthAccount(userWealthAccount);
                userWealthAccount.setId(wealthAccountId);
                wealthAccountDO = userWealthAccount;
            } catch (VirtualWealthException e) {
                if (e.getCode() == ResponseCode.DB_OP_ERROR_OF_DUPLICATE_ENTRY.getCode()) {
                    wealthAccountDO =
                            wealthAccountManager.getWealthAccount(grantedWealthDTO.getReceiverId(), grantedWealthDTO.getWealthType(), appInfoDTO.getBizCode());
                }
            }
        }
        grantedWealthDTO.setWealthId(wealthAccountDO.getId());
        // 开店或退款, 虚拟财富直接到账
        if (grantedWealthDTO.getSourceType().intValue() == SourceType.SHOP.getValue() ||
                grantedWealthDTO.getSourceType().intValue() == SourceType.REFUND.getValue()) {

            grantedWealthDTO.setStatus(GrantedWealthStatus.TRANSFERRED.getValue());
            // 发放记录
            grantedWealthManager.addGrantedWealth(ModelUtil.genGrantedWealthDO(grantedWealthDTO));

            int opCount = wealthAccountManager.increaseAccountBalance(wealthAccountDO.getId(), wealthAccountDO.getUserId(), grantedWealthDTO.getAmount());
            if (opCount != 1) {
                LOGGER.error("error of increaseAccountBalance, wealthAccountId : {}, userId : {}, grantAmount : {}",
                        wealthAccountDO.getId(), wealthAccountDO.getUserId(), grantedWealthDTO.getAmount());
                return new VirtualWealthResponse(ResponseCode.SERVICE_EXCEPTION);
            }
        } else if (grantedWealthDTO.getSourceType().intValue() == SourceType.SELL.getValue() ||
                grantedWealthDTO.getSourceType().intValue() == SourceType.GROUP_SELL.getValue() ||
                grantedWealthDTO.getSourceType().intValue() == SourceType.SHARE_DIST.getValue()||
                		grantedWealthDTO.getSourceType().intValue() == SourceType.NOSHARE_DIST.getValue()||
        				grantedWealthDTO.getSourceType().intValue() == SourceType.PURCHASE_DIST.getValue()||
        				grantedWealthDTO.getSourceType().intValue() == SourceType.NOPURCHASE_DIST.getValue()) { // 销售或团队销售等待账单状态更改

            grantedWealthDTO.setStatus(GrantedWealthStatus.FROZEN.getValue());
            // 发放记录
            grantedWealthManager.addGrantedWealth(ModelUtil.genGrantedWealthDO(grantedWealthDTO));
            int opCount = wealthAccountManager.increaseFrozenBalance(wealthAccountDO.getId(), grantedWealthDTO.getAmount());
            if (opCount != 1) {
                LOGGER.error("error of increaseFrozenBalance, wealthAccountId : {}, grantAmount : {}",
                        wealthAccountDO.getId(), grantedWealthDTO.getAmount());
                return new VirtualWealthResponse(ResponseCode.SERVICE_EXCEPTION);
            }
        }
        if (grantedWealthDTO.getSourceType().intValue() != SourceType.REFUND.getValue()) {
            int optCount = wealthAccountManager.increaseTotalBalance(wealthAccountDO.getId(), grantedWealthDTO.getAmount());
            if (optCount != 1) {
                LOGGER.error("error to add total of wealthAccount, wealthAccountId : {}, total : {}",
                        wealthAccountDO.getId(), grantedWealthDTO.getAmount());
                return new VirtualWealthResponse(ResponseCode.SERVICE_EXCEPTION);
            }
        }

        return VirtualWealthUtils.getSuccessResponse();
    }

    @Override
    public String getName() {
        return ActionEnum.DISTRIBUTOR_GRANT.getActionName();
    }
}