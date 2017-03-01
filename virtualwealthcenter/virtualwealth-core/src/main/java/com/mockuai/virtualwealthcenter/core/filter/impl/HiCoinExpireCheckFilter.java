package com.mockuai.virtualwealthcenter.core.filter.impl;

import com.mockuai.virtualwealthcenter.common.api.VirtualWealthResponse;
import com.mockuai.virtualwealthcenter.common.constant.*;
import com.mockuai.virtualwealthcenter.common.domain.dto.WealthAccountDTO;
import com.mockuai.virtualwealthcenter.common.domain.qto.GrantedWealthQTO;
import com.mockuai.virtualwealthcenter.core.domain.GrantedWealthDO;
import com.mockuai.virtualwealthcenter.core.domain.UsedWealthDO;
import com.mockuai.virtualwealthcenter.core.exception.VirtualWealthException;
import com.mockuai.virtualwealthcenter.core.filter.Filter;
import com.mockuai.virtualwealthcenter.core.manager.GrantedWealthManager;
import com.mockuai.virtualwealthcenter.core.manager.UsedWealthManager;
import com.mockuai.virtualwealthcenter.core.manager.WealthAccountManager;
import com.mockuai.virtualwealthcenter.core.service.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by edgar.zr on 5/16/2016.
 */
public class HiCoinExpireCheckFilter implements Filter {

    private static final String[] actionList = {
            ActionEnum.GET_HI_COIN.getActionName(),
            ActionEnum.QUERY_WEALTH_ACCOUNT.getActionName()
    };
    private ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(16, 32, 30L * 60 * 1000, TimeUnit.MILLISECONDS,
            new LinkedBlockingDeque<Runnable>(1024));
    ;

    @Override
    public boolean isAccept(RequestContext ctx) {
        return true;
    }

    @Override
    public VirtualWealthResponse before(RequestContext ctx) throws VirtualWealthException {
        return new VirtualWealthResponse(ResponseCode.SUCCESS);
    }

    @Override
    public VirtualWealthResponse after(RequestContext ctx) throws VirtualWealthException {
        VirtualWealthResponse response = ctx.getResponse();
        WealthAccountManager wealthAccountManager = (WealthAccountManager) ctx.getAppContext().getBean("wealthAccountManager");
        GrantedWealthManager grantedWealthManager = (GrantedWealthManager) ctx.getAppContext().getBean("grantedWealthManager");
        UsedWealthManager usedWealthManager = (UsedWealthManager) ctx.getAppContext().getBean("usedWealthManager");
        if (response.isSuccess()) {
            if (ActionEnum.GET_HI_COIN.getActionName().equals(ctx.getRequest().getCommand())) {
                WealthAccountDTO wealthAccountDTO = (WealthAccountDTO) ctx.getResponse().getModule();
                threadPoolExecutor.submit(
                        new HiCoinExpireRunner(wealthAccountManager,
                                grantedWealthManager,
                                usedWealthManager,
                                Arrays.asList(wealthAccountDTO)));
                return new VirtualWealthResponse(ResponseCode.SUCCESS);
            }
            if (ActionEnum.QUERY_WEALTH_ACCOUNT.getActionName().equals(ctx.getRequest().getCommand())) {
                List<WealthAccountDTO> wealthAccountDTOs = (List<WealthAccountDTO>) ctx.getResponse().getModule();
                threadPoolExecutor.submit(
                        new HiCoinExpireRunner(wealthAccountManager,
                                grantedWealthManager,
                                usedWealthManager,
                                wealthAccountDTOs));
                return new VirtualWealthResponse(ResponseCode.SUCCESS);
            }
        }

        return new VirtualWealthResponse(ResponseCode.SUCCESS);
    }

    private class HiCoinExpireRunner implements Runnable {

        private final Logger LOGGER = LoggerFactory.getLogger(HiCoinExpireRunner.class);

        private WealthAccountManager wealthAccountManager;
        private GrantedWealthManager grantedWealthManager;
        private UsedWealthManager usedWealthManager;
        private List<WealthAccountDTO> wealthAccountDTOs;

        public HiCoinExpireRunner(WealthAccountManager wealthAccountManager,
                                  GrantedWealthManager grantedWealthManager,
                                  UsedWealthManager usedWealthManager,
                                  List<WealthAccountDTO> wealthAccountDTOs) {
            this.wealthAccountManager = wealthAccountManager;
            this.grantedWealthManager = grantedWealthManager;
            this.usedWealthManager = usedWealthManager;
            this.wealthAccountDTOs = wealthAccountDTOs;
        }

        @Override
        public void run() {
            if (wealthAccountDTOs == null || wealthAccountDTOs.isEmpty()) {
                return;
            }
            Long hiWealthAccountId = null;
            for (WealthAccountDTO wealthAccountDTO : wealthAccountDTOs) {
                if (wealthAccountDTO.getWealthType().intValue() == WealthType.HI_COIN.getValue()) {
                    hiWealthAccountId = wealthAccountDTO.getId();
                    break;
                }
            }
            if (hiWealthAccountId == null) {
                return;
            }
            GrantedWealthQTO grantedWealthQTO = new GrantedWealthQTO();
            grantedWealthQTO.setWealthId(hiWealthAccountId);
            grantedWealthQTO.setStatus(GrantedWealthStatus.TRANSFERRED.getValue());
            grantedWealthQTO.setExpire(1);
            try {
                List<GrantedWealthDO> grantedWealthDOs = grantedWealthManager.queryGrantedWealth(grantedWealthQTO);
                List<GrantedWealthDO> toUpdate = new ArrayList<>();
                GrantedWealthDO tGrantedWealthDO;
                long toDecrease = 0L;
                // 标记过期状态
                for (GrantedWealthDO grantedWealthDO : grantedWealthDOs) {
                    tGrantedWealthDO = new GrantedWealthDO();
                    tGrantedWealthDO.setId(grantedWealthDO.getId());
                    tGrantedWealthDO.setStatus(GrantedWealthStatus.EXPIRED.getValue());
                    toUpdate.add(tGrantedWealthDO);
                    toDecrease += grantedWealthDO.getAmount() - grantedWealthDO.getUsedAmount();
                }
                if (!toUpdate.isEmpty()) {
                    grantedWealthManager.batchUpdateStatus(toUpdate);

                    // 总量减掉
                    int optCount = wealthAccountManager.decreaseAccountBalance(hiWealthAccountId,
                            wealthAccountDTOs.get(0).getUserId(), toDecrease);

                    if (optCount != 1) {
                        LOGGER.error("error to decrease amount of wealth account, wealthAccountId : {}, userId : {}, amount : {}",
                                hiWealthAccountId, wealthAccountDTOs.get(0).getUserId(), toDecrease);
                        throw new VirtualWealthException(ResponseCode.SERVICE_EXCEPTION);
                    }

                    // 生成过期记录
                    UsedWealthDO usedWealthDO = new UsedWealthDO();
                    usedWealthDO.setStatus(WealthUseStatus.USED.getValue());
                    usedWealthDO.setWealthAccountId(hiWealthAccountId);
                    usedWealthDO.setAmount(toDecrease);
                    usedWealthDO.setBizCode(wealthAccountDTOs.get(0).getBizCode());
                    usedWealthDO.setUserId(wealthAccountDTOs.get(0).getUserId());
                    usedWealthManager.addUsedWealth(usedWealthDO);
                }
            } catch (VirtualWealthException e) {
                LOGGER.error("", e);
            }
        }
    }
}