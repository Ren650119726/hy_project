package com.mockuai.virtualwealthcenter.core.manager.impl;

import com.mockuai.virtualwealthcenter.common.constant.ResponseCode;
import com.mockuai.virtualwealthcenter.common.domain.dto.GrantRuleDTO;
import com.mockuai.virtualwealthcenter.common.domain.dto.GrantedWealthDTO;
import com.mockuai.virtualwealthcenter.common.domain.qto.GrantedWealthQTO;
import com.mockuai.virtualwealthcenter.core.dao.GrantedWealthDAO;
import com.mockuai.virtualwealthcenter.core.domain.GrantedWealthDO;
import com.mockuai.virtualwealthcenter.core.domain.WealthAccountDO;
import com.mockuai.virtualwealthcenter.core.exception.VirtualWealthException;
import com.mockuai.virtualwealthcenter.core.manager.GrantRuleManager;
import com.mockuai.virtualwealthcenter.core.manager.GrantedWealthManager;
import com.mockuai.virtualwealthcenter.core.manager.VirtualWealthManager;
import com.mockuai.virtualwealthcenter.core.manager.WealthAccountManager;
import com.mockuai.virtualwealthcenter.core.message.producer.BaseProducer;
import com.mockuai.virtualwealthcenter.core.rule.RuleExecutor;
import com.mockuai.virtualwealthcenter.core.util.Context;
import com.mockuai.virtualwealthcenter.core.util.JsonUtil;
import com.mockuai.virtualwealthcenter.core.util.ModelUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zengzhangqiang on 5/25/15.
 */
public class GrantedWealthManagerImpl implements GrantedWealthManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(GrantedWealthManagerImpl.class);

    @Autowired
    private GrantedWealthDAO grantedWealthDAO;
    @Autowired
    private GrantRuleManager grantRuleManager;
    @Autowired
    private WealthAccountManager wealthAccountManager;
    @Autowired
    private VirtualWealthManager virtualWealthManager;
    @Autowired
    private RuleExecutor ruleExecutor;
    @Autowired
    private BaseProducer producer;

    @Override
    public Long addGrantedWealth(GrantedWealthDO grantedWealthDO) throws VirtualWealthException {
        try {
            long grantedWealthId = grantedWealthDAO.addGrantedWealth(grantedWealthDO);
            GrantedWealthDTO grantedWealthDTO = ModelUtil.genGrantedWealthDTO(grantedWealthDO);
            grantedWealthDTO.setReceiverIdList(new ArrayList<Long>());
            grantedWealthDTO.getReceiverIdList().add(grantedWealthDO.getReceiverId());
//            if (grantedWealthDO.getWealthType().intValue() == WealthType.VIRTUAL_WEALTH.getValue())
//                producer.send(RMQMessageType.VIRTUAL_WEALTH_MONEY.getTopic(), RMQMessageType.VIRTUAL_WEALTH_MONEY.getTag(), String.valueOf(grantedWealthId), grantedWealthDTO);
//            else if (grantedWealthDO.getWealthType().intValue() == WealthType.CREDIT.getValue())
//                producer.send(RMQMessageType.VIRTUAL_WEALTH_CREDIT.getTopic(), RMQMessageType.VIRTUAL_WEALTH_CREDIT.getTag(), String.valueOf(grantedWealthId), grantedWealthDTO);
            return grantedWealthId;
        } catch (Exception e) {
            LOGGER.error("failed when adding granted wealth, grantedWealthDO : {}",
                    JsonUtil.toJson(grantedWealthDO), e);
            throw new VirtualWealthException(ResponseCode.DB_OP_ERROR);
        }
    }

    @Override
    public Long addGrantedWealths(List<GrantedWealthDO> grantedWealthDOs) throws VirtualWealthException {
        try {
            Long count = grantedWealthDAO.addGrantedWealths(grantedWealthDOs);
            return count;
        } catch (Exception e) {
            LOGGER.error("error to addGrantedWealths, grantedWealthDOs : {}",
                    JsonUtil.toJson(grantedWealthDOs), e);
            throw new VirtualWealthException(ResponseCode.DB_OP_ERROR);
        }
    }

    @Override
    public GrantedWealthDTO getGrantedWealth(GrantedWealthDO grantedWealthDO) throws VirtualWealthException {
        try {
            GrantedWealthDO dbGrantedWealthDO = grantedWealthDAO.getGrantedWealth(grantedWealthDO);
            if (dbGrantedWealthDO == null) {
                LOGGER.error("error to getGrantedWealth, the grantedWealth does not exist, grantedWealthDO : {}",
                        JsonUtil.toJson(grantedWealthDO));
                throw new VirtualWealthException(ResponseCode.GRANTED_WEALTH_NOT_EXIST);
            }
            return ModelUtil.genGrantedWealthDTO(dbGrantedWealthDO);
        } catch (VirtualWealthException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error("error to getGrantedWealth, grantedWealthDO : {}",
                    JsonUtil.toJson(grantedWealthDO), e);
            throw new VirtualWealthException(ResponseCode.DB_OP_ERROR);
        }
    }

    @Override
    public Long grantWealthByGrantRule(GrantedWealthDTO grantedWealthDTO) throws VirtualWealthException {

        try {
            GrantRuleDTO grantRuleDTO;

            if (grantedWealthDTO.getOwnerId() != null) { // 团购绑定的发放规则
                grantRuleDTO = grantRuleManager.getGrantRuleByOwnerId(grantedWealthDTO.getBizCode(), grantedWealthDTO.getOwnerId(), grantedWealthDTO.getWealthType(), grantedWealthDTO.getSourceType());
            } else { // 后台开启的发放规则
                grantRuleDTO = grantRuleManager.getGrantRuleStatusOn(grantedWealthDTO.getBizCode(), grantedWealthDTO.getWealthType(), grantedWealthDTO.getSourceType());
            }

            Context context = new Context();
            context.setParam("amount", grantedWealthDTO.getBaseAmount());
            context.setParam("grantRuleDTO", grantRuleDTO);

            grantedWealthDTO.setAmount(ruleExecutor.execute(context));

            // 没有达到发放要求
            if (grantedWealthDTO.getAmount().longValue() == 0) {
                LOGGER.info("there is not fitting grant rule, grantedWealthDTO : {}", JsonUtil.toJson(grantedWealthDTO));
                return -1L;
            }
            grantedWealthDTO.setRuleSnapshot((String) context.getParam("ruleSnapshot"));
            grantVirtualWealthToReceivers(grantedWealthDTO);

            return grantedWealthDTO.getAmount();
        } catch (Exception e) {

            LOGGER.error("failed when grantWealthByGrantRule, grantedWealthDTO : {}",
                    JsonUtil.toJson(grantedWealthDTO), e);
            throw new VirtualWealthException(ResponseCode.DB_OP_ERROR);
        }
    }

    /**
     * 批量为用户发放积分
     *
     * @param grantedWealthDTO
     */
    private void grantVirtualWealthToReceivers(GrantedWealthDTO grantedWealthDTO) throws VirtualWealthException {

        grantedWealthDTO.setGrantedTime(new Date());
        GrantedWealthDTO doGranted;
        GrantedWealthDO grantedWealthDO;
        WealthAccountDO wealthAccountDO;
        // FIXME 更改为批量处理
        for (Long receiverId : grantedWealthDTO.getReceiverIdList()) {
            doGranted = new GrantedWealthDTO();
            BeanUtils.copyProperties(grantedWealthDTO, doGranted);
            doGranted.setReceiverId(receiverId);
            doGranted.setReceiverIdList(new ArrayList<Long>());
            doGranted.getReceiverIdList().add(receiverId);
            grantedWealthDO = ModelUtil.genGrantedWealthDO(doGranted);
            grantedWealthDO.setRuleSnapshot(grantedWealthDTO.getRuleSnapshot());
            try {
                long grantedWealthId = grantedWealthDAO.addGrantedWealth(grantedWealthDO);
                wealthAccountDO = wealthAccountManager.getWealthAccount(grantedWealthDO.getReceiverId(), grantedWealthDO.getWealthType(), grantedWealthDTO.getBizCode());
                if (wealthAccountDO == null) {
                    wealthAccountDO = new WealthAccountDO();
                    wealthAccountDO.setBizCode(grantedWealthDO.getBizCode());
                    wealthAccountDO.setWealthType(grantedWealthDO.getWealthType());
                    wealthAccountDO.setUserId(grantedWealthDO.getReceiverId());
                    Long wealthAccountId = wealthAccountManager.addWealthAccount(wealthAccountDO);
                    wealthAccountDO.setId(wealthAccountId);
                }
                // 增加用户账户虚拟财富
                wealthAccountManager.increaseAccountBalance(wealthAccountDO.getId(), grantedWealthDO.getReceiverId(), grantedWealthDO.getAmount());
                // 增加发放总量记录
                virtualWealthManager.increaseGrantedVirtualWealth(grantedWealthDTO.getWealthId(), grantedWealthDTO.getAmount());

//                if (grantedWealthDTO.getWealthType().intValue() == WealthType.VIRTUAL_WEALTH.getValue())
//                    producer.send(RMQMessageType.VIRTUAL_WEALTH_MONEY.getTopic(), RMQMessageType.VIRTUAL_WEALTH_MONEY.getTag(), String.valueOf(grantedWealthId), doGranted);
//                else if (grantedWealthDTO.getWealthType().intValue() == WealthType.CREDIT.getValue())
//                    producer.send(RMQMessageType.VIRTUAL_WEALTH_CREDIT.getTopic(), RMQMessageType.VIRTUAL_WEALTH_CREDIT.getTag(), String.valueOf(grantedWealthId), doGranted);

            } catch (VirtualWealthException e) {
                LOGGER.error("error to grantVirtualWealthToReceivers, grantedWealthDO : {}", JsonUtil.toJson(grantedWealthDO));
            }
        }
    }

    @Override
    public List<GrantedWealthDO> queryGrantedWealth(GrantedWealthQTO grantedWealthQTO) throws VirtualWealthException {
        try {
            List<GrantedWealthDO> grantedWealthDOs = grantedWealthDAO.queryGrantedWealth(grantedWealthQTO);
            return grantedWealthDOs;
        } catch (Exception e) {
            LOGGER.error("failed when querying granted wealth, grantedWealthQTO : {}",
                    JsonUtil.toJson(grantedWealthQTO), e);
            throw new VirtualWealthException(ResponseCode.DB_OP_ERROR);
        }
    }

    @Override
    public void decreaseAmount(List<GrantedWealthDO> grantedWealthDOs) throws VirtualWealthException {
        try {
            int optCount = grantedWealthDAO.decreaseAmount(grantedWealthDOs);
            if (optCount != grantedWealthDOs.size()) {
                LOGGER.error("error to decreaseAmount, grantedWealthDOs : {}", JsonUtil.toJson(grantedWealthDOs));
                throw new VirtualWealthException(ResponseCode.BIZ_E_DECREASE_GRANTED_WEALTH);
            }
        } catch (VirtualWealthException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error("error to decreaseAmount, grantedWealthDOs : {}", JsonUtil.toJson(grantedWealthDOs), e);
        }
    }

    @Override
    public int updateGrantedWealth(GrantedWealthDO grantedWealthDO) throws VirtualWealthException {
        try {
            int opCount = grantedWealthDAO.updateGrantedWealth(grantedWealthDO);
            return opCount;
        } catch (Exception e) {
            LOGGER.error("error to updateGrantedWealth, grantedWealthDO : {}",
                    JsonUtil.toJson(grantedWealthDO), e);
            throw new VirtualWealthException(ResponseCode.DB_OP_ERROR);
        }
    }

    @Override
    public int batchUpdateStatus(List<GrantedWealthDO> grantedWealthDOs) throws VirtualWealthException {
        try {
            int opCount = grantedWealthDAO.batchUpdateStatus(grantedWealthDOs);
            return opCount;
        } catch (Exception e) {
            LOGGER.error("error to batchUpdateStatus, grantedWealthDOs : {}",
                    JsonUtil.toJson(grantedWealthDOs), e);
            throw new VirtualWealthException(ResponseCode.DB_OP_ERROR);
        }
    }

	@Override
	public List<GrantedWealthDO> findCustomerGrantedPageList(
			GrantedWealthQTO grantedWealthQTO) throws VirtualWealthException {
		try {
			return  grantedWealthDAO.findCustomerGrantedPageList(grantedWealthQTO);
        } catch (Exception e) {
            LOGGER.error("error to batchUpdateStatus, grantedWealthDOs : {}",
                    JsonUtil.toJson(grantedWealthQTO), e);
            throw new VirtualWealthException(ResponseCode.DB_OP_ERROR);
        }
	}
}