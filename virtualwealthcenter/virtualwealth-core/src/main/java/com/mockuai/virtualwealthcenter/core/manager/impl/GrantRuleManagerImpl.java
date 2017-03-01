package com.mockuai.virtualwealthcenter.core.manager.impl;

import com.mockuai.virtualwealthcenter.common.constant.GrantRuleStatus;
import com.mockuai.virtualwealthcenter.common.constant.GrantRuleType;
import com.mockuai.virtualwealthcenter.common.constant.ResponseCode;
import com.mockuai.virtualwealthcenter.common.constant.SourceType;
import com.mockuai.virtualwealthcenter.common.constant.WealthType;
import com.mockuai.virtualwealthcenter.common.domain.dto.GrantRuleDTO;
import com.mockuai.virtualwealthcenter.common.domain.dto.RuleModuleDTO;
import com.mockuai.virtualwealthcenter.common.domain.qto.GrantRuleQTO;
import com.mockuai.virtualwealthcenter.core.dao.GrantRuleDAO;
import com.mockuai.virtualwealthcenter.core.domain.GrantRuleDO;
import com.mockuai.virtualwealthcenter.core.exception.VirtualWealthException;
import com.mockuai.virtualwealthcenter.core.manager.GrantRuleManager;
import com.mockuai.virtualwealthcenter.core.manager.RuleModuleManager;
import com.mockuai.virtualwealthcenter.core.util.JsonUtil;
import com.mockuai.virtualwealthcenter.core.util.ModelUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by edgar.zr on 11/11/15.
 */
public class GrantRuleManagerImpl implements GrantRuleManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(GrantRuleManagerImpl.class.getName());

    @Autowired
    private GrantRuleDAO grantRuleDAO;

    @Autowired
    private RuleModuleManager ruleModuleManager;

    @Override
    public void addGrantRule(GrantRuleDTO grantRuleDTO) throws VirtualWealthException {
        try {
            if (grantRuleDTO.getStatus() == null) {
                grantRuleDTO.setStatus(GrantRuleStatus.OFF.getValue());
            }
            GrantRuleDO grantRuleDO = ModelUtil.genGrantRuleDO(grantRuleDTO);
            Long id = grantRuleDAO.addGrantRule(grantRuleDO);
            if (grantRuleDO.getRuleType().intValue() == GrantRuleType.SINGLE_REACH.getValue())
                Collections.sort(grantRuleDTO.getRuleModuleDTOs(), new SingleReachRuleModuleComparator());
            grantRuleDTO.setId(id);
            ruleModuleManager.batchAddRuleModule(grantRuleDTO);
        } catch (VirtualWealthException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error("error to addGrantRule, grantRuleDTO : {}", JsonUtil.toJson(grantRuleDTO), e);
            throw new VirtualWealthException(ResponseCode.DB_OP_ERROR);
        }
    }

    @Override
    public GrantRuleDTO getGrantRule(String bizCode, Long grantRuleId, Long creatorId) throws VirtualWealthException {
        GrantRuleDO grantRuleDO = new GrantRuleDO();
        grantRuleDO.setId(grantRuleId);
        grantRuleDO.setBizCode(bizCode);

        return getGrantRule(grantRuleDO);
    }

    /**
     * 查询发放规则
     *
     * @param grantRuleDO
     * @return
     * @throws VirtualWealthException
     */
    private GrantRuleDTO getGrantRule(GrantRuleDO grantRuleDO) throws VirtualWealthException {

        try {
            GrantRuleDO dbGrantRuleDO = grantRuleDAO.getGrantRule(grantRuleDO);
            if (dbGrantRuleDO == null) {
                LOGGER.error("error to getGrantRule, grantRuleDO : {}",
                        JsonUtil.toJson(grantRuleDO));
                throw new VirtualWealthException(ResponseCode.BIZ_E_THE_GRANT_RULE_DOES_NOT_EXIST);
            }
            GrantRuleDTO grantRuleDTO = ModelUtil.genGrantRuleDTO(dbGrantRuleDO);
            List<RuleModuleDTO> ruleModuleDTOs = ruleModuleManager.getRuleModuleListByGrantRuleId(grantRuleDTO.getBizCode(), grantRuleDTO.getId(), grantRuleDTO.getCreatorId());
            grantRuleDTO.setRuleModuleDTOs(ruleModuleDTOs);
            return grantRuleDTO;
        } catch (VirtualWealthException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error("error to getGrantRule, grantRuleDO : {}",
                    JsonUtil.toJson(grantRuleDO), e);
            throw new VirtualWealthException(ResponseCode.SERVICE_EXCEPTION);
        }
    }

    @Override
    public GrantRuleDTO getGrantRuleStatusOn(String bizCode, Integer wealthType, Integer sourceType) throws VirtualWealthException {
        GrantRuleDO grantRuleDO = new GrantRuleDO();
        grantRuleDO.setBizCode(bizCode);
        grantRuleDO.setWealthType(wealthType);
        grantRuleDO.setSourceType(sourceType);
        grantRuleDO.setStatus(GrantRuleStatus.ON.getValue());
        // 只有集合类型的发放规则才能查询，否则可能会查出多条发放规则
        if (SourceType.getByValue(sourceType).getType() != 1) {
            throw new VirtualWealthException(ResponseCode.BIZ_E_THE_GRANT_RULE_DOES_NOT_EXIST);
        }
        return getGrantRule(grantRuleDO);
    }

    @Override
    public GrantRuleDTO getGrantRuleByOwnerId(String bizCode, Long ownerId, Integer wealthType, Integer sourceType) throws VirtualWealthException {
        GrantRuleDO grantRuleDO = new GrantRuleDO();
        grantRuleDO.setBizCode(bizCode);
        grantRuleDO.setOwnerId(ownerId);
        grantRuleDO.setWealthType(wealthType);
        grantRuleDO.setSourceType(sourceType);

        return getGrantRule(grantRuleDO);
    }

    @Override
    public List<GrantRuleDTO> queryGrantRule(GrantRuleQTO grantRuleQTO) throws VirtualWealthException {
        try {
            List<GrantRuleDO> grantRuleDOs = grantRuleDAO.queryGrantRule(grantRuleQTO);
            return ModelUtil.genGrantRuleDTOList(grantRuleDOs);
        } catch (Exception e) {
            LOGGER.error("error to queryGrantRule, grantRuleQTO : {}", JsonUtil.toJson(grantRuleQTO), e);
            throw new VirtualWealthException(ResponseCode.DB_OP_ERROR);
        }
    }

    @Override
    public void deleteGrantRule(Long grantRuleId, String bizCode, Long creatorId) throws VirtualWealthException {
        try {
            GrantRuleDO grantRuleDO = new GrantRuleDO();
            grantRuleDO.setId(grantRuleId);
            grantRuleDO.setBizCode(bizCode);
            int opNum = grantRuleDAO.deleteGrantRule(grantRuleDO);
            if (opNum != 1) {
                LOGGER.error("error tp deleteGrantRule, grantRuleId : {}, bizCode : {}, creatorId : {}", grantRuleId, bizCode, creatorId);
                throw new VirtualWealthException(ResponseCode.SERVICE_EXCEPTION);
            }
        } catch (VirtualWealthException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error("error to deleteGrantRule, grantRuleId : {}, creatorId : {}", grantRuleId, creatorId, e);
            throw new VirtualWealthException(ResponseCode.DB_OP_ERROR);
        }
    }

    @Override
    public void updateGrantRule(GrantRuleDTO grantRuleDTO) throws VirtualWealthException {
        try {
            GrantRuleDO grantRuleDO = new GrantRuleDO();
            grantRuleDO.setBizCode(grantRuleDTO.getBizCode());
            grantRuleDO.setName(grantRuleDTO.getName());
            grantRuleDO.setId(grantRuleDTO.getId());
            grantRuleDAO.updateGrantRule(grantRuleDO);
            // 没有需求关联到发放规则上，因此更新策略，直接物理删除原来相关的发放规则然后再插入新的发放规则
            ruleModuleManager.deleteRuleModuleByGrantRuleId(grantRuleDTO.getBizCode(), grantRuleDTO.getId(), grantRuleDTO.getCreatorId());
            ruleModuleManager.batchAddRuleModule(grantRuleDTO);
        } catch (VirtualWealthException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error("error to updateGrantRule, grantRuleDTO : {}", JsonUtil.toJson(grantRuleDTO), e);
            throw new VirtualWealthException(ResponseCode.DB_OP_ERROR);
        }
    }

    @Override
    public void switchGrantRule(String bizCode, Long grantRuleId, Long creatorId, Integer status) throws VirtualWealthException {

        GrantRuleDO grantRuleDO = new GrantRuleDO();
        grantRuleDO.setId(grantRuleId);
        grantRuleDO.setBizCode(bizCode);

        try {

            // 开启发送规则需要保证开启后同时只有一个发送规则是开启状态
            if (status.intValue() == GrantRuleStatus.ON.getValue()) {
                GrantRuleDO grantRuleDODB = grantRuleDAO.getGrantRule(grantRuleDO);
                if (grantRuleDODB == null) {
                    throw new VirtualWealthException(ResponseCode.BIZ_E_THE_GRANT_RULE_DOES_NOT_EXIST);
                }
                if (grantRuleDODB.getStatus().intValue() == GrantRuleStatus.ON.getValue()) return;

                GrantRuleQTO grantRuleQTO = new GrantRuleQTO();
                grantRuleQTO.setWealthType(grantRuleDODB.getWealthType());
                grantRuleQTO.setSourceType(grantRuleDODB.getSourceType());
                grantRuleQTO.setBizCode(grantRuleDODB.getBizCode());
                List<GrantRuleDO> grantRuleDOsDB = grantRuleDAO.queryGrantRule(grantRuleQTO);
                for (GrantRuleDO ruleDO : grantRuleDOsDB) {
                    if (ruleDO.getStatus().intValue() == GrantRuleStatus.ON.getValue()) {
                        throw new VirtualWealthException(ResponseCode.BIZ_E_ONLY_ONE_GRANT_RULE_IS_ON);
                    }
                }
            }

            grantRuleDO = new GrantRuleDO();
            grantRuleDO.setId(grantRuleId);
            grantRuleDO.setBizCode(bizCode);
            grantRuleDO.setStatus(status);
            int opNum = grantRuleDAO.updateGrantRule(grantRuleDO);
            if (opNum != 1) {
                LOGGER.error("error to switchGrantRule, grantRuleId : {}, creatorId : {}, bizCode : {}, status : {}",
                        grantRuleId, creatorId, bizCode, status);
                throw new VirtualWealthException(ResponseCode.SERVICE_EXCEPTION);
            }
        } catch (VirtualWealthException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error("error to switchGrantRule, grantRuleId : {}, creatorId : {}, bizCode : {}, status : {}",
                    grantRuleId, creatorId, bizCode, status, e);
            throw new VirtualWealthException(ResponseCode.DB_OP_ERROR);
        }
    }

    @Override
    public List<GrantRuleDTO> fakeGrantRule(Integer wealthType, Integer sourceType) throws VirtualWealthException {
        GrantRuleDTO grantRuleDTO = new GrantRuleDTO();
        grantRuleDTO.setSourceType(sourceType);
        grantRuleDTO.setWealthType(wealthType);
        List<RuleModuleDTO> ruleModuleDTOs = new ArrayList<RuleModuleDTO>();
        RuleModuleDTO ruleModuleDTO;

        // 积分，订单完成后发放积分,默认按比例发放
        if (wealthType.intValue() == WealthType.CREDIT.getValue() && sourceType.intValue() == SourceType.ORDER_PAY.getValue()) {
            grantRuleDTO.setRuleType(GrantRuleType.AMOUNT_RATIO.getValue());
            ruleModuleDTO = new RuleModuleDTO();
            ruleModuleDTO.setRatio(0);
            ruleModuleDTOs.add(ruleModuleDTO);
            grantRuleDTO.setRuleModuleDTOs(ruleModuleDTOs);
        }

        return new ArrayList<GrantRuleDTO>(Arrays.asList(grantRuleDTO));
    }

    private class SingleReachRuleModuleComparator implements Comparator<RuleModuleDTO> {

        @Override
        public int compare(RuleModuleDTO o1, RuleModuleDTO o2) {
            if (o1.getParamA() > o2.getParamA().longValue()) return -1;
            if (o1.getParamA() < o2.getParamA().longValue()) return 1;
            return 0;
        }
    }
}