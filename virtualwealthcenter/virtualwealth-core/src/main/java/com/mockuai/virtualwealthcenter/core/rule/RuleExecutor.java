package com.mockuai.virtualwealthcenter.core.rule;

import com.mockuai.virtualwealthcenter.common.constant.GrantRuleType;
import com.mockuai.virtualwealthcenter.common.domain.dto.GrantRuleDTO;
import com.mockuai.virtualwealthcenter.common.domain.dto.RuleModuleDTO;
import com.mockuai.virtualwealthcenter.core.exception.VirtualWealthException;
import com.mockuai.virtualwealthcenter.core.rule.module.Module;
import com.mockuai.virtualwealthcenter.core.rule.module.impl.HalfClosedIntervalModule;
import com.mockuai.virtualwealthcenter.core.rule.module.impl.RatioModule;
import com.mockuai.virtualwealthcenter.core.rule.module.impl.SingleReachModule;
import com.mockuai.virtualwealthcenter.core.util.Context;
import com.mockuai.virtualwealthcenter.core.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by edgar.zr on 11/13/15.
 */
@Service
public class RuleExecutor {

    private static final Logger LOGGER = LoggerFactory.getLogger(RuleExecutor.class);

    public Long execute(Context context) {

        Long amount = (Long) context.getParam("amount");
        GrantRuleDTO grantRuleDTO = (GrantRuleDTO) context.getParam("grantRuleDTO");

        List<Module> modules = new ArrayList<Module>();
        // 记录规则数据, 采用 python 中 tuple 结构表示
        List<List<Long>> tupleList = new ArrayList<List<Long>>();
        List<Long> tuple;

        // 分装为 module
        if (grantRuleDTO.getRuleType().intValue() == GrantRuleType.HALF_CLOSED_L_INTERVAL.getValue()) {
            for (RuleModuleDTO ruleModuleDTO : grantRuleDTO.getRuleModuleDTOs()) {
                HalfClosedIntervalModule module = new HalfClosedIntervalModule();
                module.setLeft(ruleModuleDTO.getParamA());
                module.setRight(ruleModuleDTO.getParamB());
                module.setCredit(ruleModuleDTO.getAmount());
                modules.add(module);
                tuple = new ArrayList<Long>();
                tuple.add(module.getLeft());
                tuple.add(module.getRight());
                tuple.add(module.getCredit());
                tupleList.add(tuple);
            }
        } else if (grantRuleDTO.getRuleType().intValue() == GrantRuleType.AMOUNT_RATIO.getValue()) {
            RatioModule module = new RatioModule();
            module.setRatio(grantRuleDTO.getRuleModuleDTOs().get(0).getRatio());
            modules.add(module);
            tuple = new ArrayList<Long>();
            tuple.add((long) module.getRatio());
            tupleList.add(tuple);
        } else if (grantRuleDTO.getRuleType().intValue() == GrantRuleType.SINGLE_REACH.getValue()) {
            for (RuleModuleDTO ruleModuleDTO : grantRuleDTO.getRuleModuleDTOs()) {
                SingleReachModule module = new SingleReachModule();
                module.setValue(ruleModuleDTO.getParamA());
                module.setVirtualWealth(ruleModuleDTO.getAmount());
                modules.add(module);
                tuple = new ArrayList<Long>();
                tuple.add(module.getValue());
                tuple.add(module.getVirtualWealth());
                tupleList.add(tuple);
            }
        }

        Map<Object, Object> snapshot = new HashMap<Object, Object>();
        snapshot.put("id", grantRuleDTO.getId());
        snapshot.put("type", grantRuleDTO.getRuleType());
        snapshot.put("rules", tupleList);
        snapshot.put("base", amount);
        context.setParam("ruleSnapshot", JsonUtil.toJson(snapshot));

        return grant(amount, modules);
    }

    public Long grant(Long amount, List<Module> modules) {
        Long result = 0l;
        for (Module module : modules) {
            try {
                result = module.execute(amount);
                if (result != -1) {
                    break;
                }
            } catch (VirtualWealthException e) {
                LOGGER.error("", e);
            } catch (Exception e) {
                LOGGER.error("", e);
            }
        }
        return result != -1 ? result : 0;
    }
}