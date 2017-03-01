package com.mockuai.virtualwealthcenter.core.service.action;

import com.mockuai.virtualwealthcenter.common.api.VirtualWealthResponse;
import com.mockuai.virtualwealthcenter.common.constant.ActionEnum;
import com.mockuai.virtualwealthcenter.common.constant.GrantedWealthStatus;
import com.mockuai.virtualwealthcenter.common.constant.WealthType;
import com.mockuai.virtualwealthcenter.common.domain.dto.TotalWealthAccountDTO;
import com.mockuai.virtualwealthcenter.common.domain.qto.GrantedWealthQTO;
import com.mockuai.virtualwealthcenter.common.domain.qto.WealthAccountQTO;
import com.mockuai.virtualwealthcenter.core.domain.GrantedWealthDO;
import com.mockuai.virtualwealthcenter.core.domain.WealthAccountDO;
import com.mockuai.virtualwealthcenter.core.exception.VirtualWealthException;
import com.mockuai.virtualwealthcenter.core.manager.GrantedWealthManager;
import com.mockuai.virtualwealthcenter.core.manager.WealthAccountManager;
import com.mockuai.virtualwealthcenter.core.service.RequestContext;
import com.mockuai.virtualwealthcenter.core.util.DateUtils;
import com.mockuai.virtualwealthcenter.core.util.ModelUtil;
import com.mockuai.virtualwealthcenter.core.util.VirtualWealthPreconditions;
import com.mockuai.virtualwealthcenter.core.util.VirtualWealthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by edgar.zr on 5/18/2016.
 */
@Service
public class QueryTotalVirtualWealthCombineAction extends TransAction {

    @Autowired
    private WealthAccountManager wealthAccountManager;
    @Autowired
    private GrantedWealthManager grantedWealthManager;

    @Override
    protected VirtualWealthResponse doTransaction(RequestContext context) throws VirtualWealthException {
        Long userId = (Long) context.getRequest().getParam("userId");

        VirtualWealthPreconditions.checkNotNull(userId, "userId");

        WealthAccountQTO wealthAccountQTO = new WealthAccountQTO();
        wealthAccountQTO.setUserId(userId);
        List<WealthAccountDO> wealthAccountDOs = wealthAccountManager.queryWealthAccount(wealthAccountQTO);

        // 总累计余额
        WealthAccountDO virtualWealth = null;
        WealthAccountDO hiCoin = null;
        for (WealthAccountDO wealthAccountDO : wealthAccountDOs) {
            if (wealthAccountDO.getWealthType().intValue() == WealthType.VIRTUAL_WEALTH.getValue()) {
                virtualWealth = wealthAccountDO;
            }
            if (wealthAccountDO.getWealthType().intValue() == WealthType.HI_COIN.getValue()) {
                hiCoin = wealthAccountDO;
            }
        }
        if (virtualWealth == null) {
            virtualWealth = new WealthAccountDO();
            virtualWealth.setWealthType(WealthType.VIRTUAL_WEALTH.getValue());
            virtualWealth.setUserId(userId);
            virtualWealth.setTotal(0L);
        }
        if (hiCoin == null) {
            hiCoin = new WealthAccountDO();
            hiCoin.setWealthType(WealthType.HI_COIN.getValue());
            hiCoin.setUserId(userId);
            hiCoin.setAmount(0L);
            hiCoin.setTransitionAmount(0L);
        }

        GrantedWealthQTO grantedWealthQTO = new GrantedWealthQTO();
        grantedWealthQTO.setReceiverId(userId);
        grantedWealthQTO.setCurrentDay(DateUtils.startOfDay(new Date()));
//        grantedWealthQTO.setTotalCombine(1);

        List<GrantedWealthDO> grantedWealthDOs = grantedWealthManager.queryGrantedWealth(grantedWealthQTO);
        virtualWealth.setAmount(0L);
        virtualWealth.setTransitionAmount(0L);

        for (GrantedWealthDO grantedWealthDO : grantedWealthDOs) {
            if (grantedWealthDO.getWealthType().intValue() == WealthType.VIRTUAL_WEALTH.getValue()) {
                if (grantedWealthDO.getStatus().intValue() == GrantedWealthStatus.FROZEN.getValue()) {
                    virtualWealth.setTransitionAmount(virtualWealth.getTransitionAmount() + grantedWealthDO.getAmount());
                } else if (grantedWealthDO.getStatus().intValue() == GrantedWealthStatus.TRANSFERRED.getValue()) {
                    virtualWealth.setAmount(virtualWealth.getAmount() + grantedWealthDO.getAmount());
                }
            }
        }

        TotalWealthAccountDTO totalWealthAccountDTO = new TotalWealthAccountDTO();
        totalWealthAccountDTO.setVirtualWealth(ModelUtil.genWealthAccountDTO(virtualWealth));
        totalWealthAccountDTO.setHiCoin(ModelUtil.genWealthAccountDTO(hiCoin));

        return VirtualWealthUtils.getSuccessResponse(totalWealthAccountDTO);
    }

    @Override
    public String getName() {
        return ActionEnum.QUERY_TOTAL_VIRTUAL_WEALTH_COMBINE.getActionName();
    }
}