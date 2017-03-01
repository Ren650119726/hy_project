package com.mockuai.virtualwealthcenter.core.manager.impl;

import com.mockuai.virtualwealthcenter.common.constant.GrantRuleType;
import com.mockuai.virtualwealthcenter.common.constant.ResponseCode;
import com.mockuai.virtualwealthcenter.common.domain.dto.GrantRuleDTO;
import com.mockuai.virtualwealthcenter.common.domain.dto.RuleModuleDTO;
import com.mockuai.virtualwealthcenter.core.dao.RuleModuleDAO;
import com.mockuai.virtualwealthcenter.core.domain.RuleModuleDO;
import com.mockuai.virtualwealthcenter.core.exception.VirtualWealthException;
import com.mockuai.virtualwealthcenter.core.manager.RuleModuleManager;
import com.mockuai.virtualwealthcenter.core.util.JsonUtil;
import com.mockuai.virtualwealthcenter.core.util.ModelUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by edgar.zr on 11/11/15.
 */
public class RuleModuleManagerImpl implements RuleModuleManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(RuleModuleManagerImpl.class.getName());

    @Autowired
    private RuleModuleDAO ruleModuleDAO;

    @Override
    public void batchAddRuleModule(GrantRuleDTO grantRuleDTO) throws VirtualWealthException {
        for (RuleModuleDTO ruleModuleDTO : grantRuleDTO.getRuleModuleDTOs()) {
            ruleModuleDTO.setGrantRuleId(grantRuleDTO.getId());
            ruleModuleDTO.setCreatorId(grantRuleDTO.getCreatorId());
            ruleModuleDTO.setBizCode(grantRuleDTO.getBizCode());
            // 发放规则参数验证
            if (grantRuleDTO.getRuleType().intValue() == GrantRuleType.HALF_CLOSED_L_INTERVAL.getValue() &&
                    (ruleModuleDTO.getParamA() == null || ruleModuleDTO.getParamB() == null || ruleModuleDTO.getAmount() == null)
                    || grantRuleDTO.getRuleType().intValue() == GrantRuleType.AMOUNT_RATIO.getValue() && ruleModuleDTO.getRatio() == null) {
                throw new VirtualWealthException(ResponseCode.PARAMETER_ERROR, "the parameter according to ruleType is invalid");
            }
        }
        try {
            List<RuleModuleDO> ruleModuleDOs = ModelUtil.genRuleModuleDOList(grantRuleDTO.getRuleModuleDTOs());
            ruleModuleDAO.batchAddRuleModule(ruleModuleDOs);
        } catch (Exception e) {
            LOGGER.error("error to batchAddRuleModule, ruleModulesDTOList : {}", JsonUtil.toJson(grantRuleDTO.getRuleModuleDTOs()), e);
            throw new VirtualWealthException(ResponseCode.DB_OP_ERROR);
        }
    }

    @Override
    public void batchUpdateRuleModule(List<RuleModuleDTO> ruleModuleDTOs) throws VirtualWealthException {
        if (ruleModuleDTOs == null || ruleModuleDTOs.isEmpty()) {
            return;
        }
        try {
            List<RuleModuleDTO> toAddRuleModuleDTOs = new ArrayList<RuleModuleDTO>();
            List<RuleModuleDO> toUpdateModuleDOs = new ArrayList<RuleModuleDO>();
            RuleModuleDO ruleModuleDO;
            for (RuleModuleDTO ruleModule : ruleModuleDTOs) {
                // 新创建
                if (ruleModule.getId() == null) {
                    toAddRuleModuleDTOs.add(ruleModule);
                } else {
                    ruleModuleDO = new RuleModuleDO();
                    ruleModuleDO.setId(ruleModule.getId());
                    ruleModule.setParamA(ruleModule.getParamA());
                    ruleModule.setParamB(ruleModule.getParamB());
                    ruleModule.setAmount(ruleModule.getAmount());
                    ruleModule.setRatio(ruleModule.getRatio());
                    toUpdateModuleDOs.add(ruleModuleDO);
                }
            }
//            batchAddRuleModule(toAddRuleModuleDTOs);
            if (toUpdateModuleDOs.isEmpty()) {
                return;
            }
//        } catch (VirtualWealthException e) {
//            throw e;
        } catch (Exception e) {
            LOGGER.error("error to batchUpdateRuleModule, ruleModuleDTOs : {}", JsonUtil.toJson(ruleModuleDTOs), e);
            throw new VirtualWealthException(ResponseCode.DB_OP_ERROR);
        }
    }

    @Override
    public List<RuleModuleDTO> getRuleModuleListByGrantRuleId(String bizCode, Long grantRuleId, Long creatorId) throws VirtualWealthException {

        try {
            RuleModuleDO toGetRuleModuleDO = new RuleModuleDO();
            toGetRuleModuleDO.setGrantRuleId(grantRuleId);
            toGetRuleModuleDO.setBizCode(bizCode);
            List<RuleModuleDO> ruleModuleDOs = ruleModuleDAO.getRuleModule(toGetRuleModuleDO);
            return ModelUtil.genRuleModuleDTOList(ruleModuleDOs);
        } catch (Exception e) {
            LOGGER.error("error to getRuleModuleListByGrantRuleId, grantRuleId : {}, creatorId : {} bizCode : {}",
                    grantRuleId, creatorId, bizCode, e);
            throw new VirtualWealthException(ResponseCode.DB_OP_ERROR);
        }
    }

    @Override
    public void deleteRuleModuleByGrantRuleId(String bizCode, Long grantRuleId, Long creatorId) throws VirtualWealthException {
        try {
            RuleModuleDO ruleModuleDO = new RuleModuleDO();
            ruleModuleDO.setBizCode(bizCode);
            ruleModuleDO.setGrantRuleId(grantRuleId);
            ruleModuleDAO.deleteRuleModuleByGrantRuleId(ruleModuleDO);
        } catch (Exception e) {
            LOGGER.error("error to deleteRuleModuleByGrantRuleId, bizCode : {}, grantRuleId : {}, creatorId : {}",
                    bizCode, grantRuleId, creatorId, e);
            throw new VirtualWealthException(ResponseCode.DB_OP_ERROR);
        }
    }
}