package com.mockuai.virtualwealthcenter.core.util;

import com.mockuai.virtualwealthcenter.common.domain.dto.GrantRuleDTO;
import com.mockuai.virtualwealthcenter.common.domain.dto.GrantedWealthDTO;
import com.mockuai.virtualwealthcenter.common.domain.dto.RuleModuleDTO;
import com.mockuai.virtualwealthcenter.common.domain.dto.VirtualWealthDTO;
import com.mockuai.virtualwealthcenter.common.domain.dto.WealthAccountDTO;
import com.mockuai.virtualwealthcenter.common.domain.dto.WithdrawalsItemDTO;
import com.mockuai.virtualwealthcenter.core.domain.GrantRuleDO;
import com.mockuai.virtualwealthcenter.core.domain.GrantedWealthDO;
import com.mockuai.virtualwealthcenter.core.domain.RuleModuleDO;
import com.mockuai.virtualwealthcenter.core.domain.VirtualWealthDO;
import com.mockuai.virtualwealthcenter.core.domain.WealthAccountDO;
import com.mockuai.virtualwealthcenter.core.domain.WithdrawalsItemDO;
import org.springframework.beans.BeanUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ModelUtil {

    private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static WealthAccountDTO genWealthAccountDTO(WealthAccountDO wealthAccountDO,
                                                       VirtualWealthDO virtualWealthDO) {
        if (wealthAccountDO == null) {
            return null;
        }

        WealthAccountDTO wealthAccountDTO = new WealthAccountDTO();
        BeanUtils.copyProperties(wealthAccountDO, wealthAccountDTO);
        if (virtualWealthDO != null) {
            wealthAccountDTO.setExchangeRate(virtualWealthDO.getExchangeRate());
            wealthAccountDTO.setUpperLimit(virtualWealthDO.getUpperLimit());
        }
        return wealthAccountDTO;
    }


    public static WealthAccountDTO genWealthAccountDTO(WealthAccountDO wealthAccountDO) {
        if (wealthAccountDO == null) {
            return null;
        }

        WealthAccountDTO wealthAccountDTO = new WealthAccountDTO();
        BeanUtils.copyProperties(wealthAccountDO, wealthAccountDTO);
        return wealthAccountDTO;
    }


    public static List<WithdrawalsItemDTO> genWithdrawalsItemDTOList(List<WithdrawalsItemDO> withdrawalsItemDOs) {
        if (withdrawalsItemDOs == null) {
            return Collections.emptyList();
        }

        List<WithdrawalsItemDTO> withdrawalsItemDTOs = new ArrayList<>();
        for (WithdrawalsItemDO withdrawalsItemDO : withdrawalsItemDOs) {
            withdrawalsItemDTOs.add(genWithdrawalsItemDTO(withdrawalsItemDO));
        }
        return withdrawalsItemDTOs;
    }

    public static WithdrawalsItemDTO genWithdrawalsItemDTO(WithdrawalsItemDO withdrawalsItemDO) {
        if (withdrawalsItemDO == null)
            return null;
        WithdrawalsItemDTO withdrawalsItemDTO = new WithdrawalsItemDTO();
        BeanUtils.copyProperties(withdrawalsItemDO, withdrawalsItemDTO);
        return withdrawalsItemDTO;
    }
    public static WealthAccountDO genWealthAccountDO(WealthAccountDTO wealthAccountDTO) {
        if (wealthAccountDTO == null) {
            return null;
        }

        WealthAccountDO wealthAccountDO = new WealthAccountDO();
        BeanUtils.copyProperties(wealthAccountDTO, wealthAccountDO);
        return wealthAccountDO;
    }

    public static List<WealthAccountDTO> genWealthAccountDTOList(List<WealthAccountDO> wealthAccountDOs) {
        if (wealthAccountDOs == null) {
            return Collections.emptyList();
        }

        List<WealthAccountDTO> wealthAccountDTOs = new ArrayList<WealthAccountDTO>();
        for (WealthAccountDO wealthAccountDO : wealthAccountDOs) {
            wealthAccountDTOs.add(genWealthAccountDTO(wealthAccountDO));
        }
        return wealthAccountDTOs;
    }

    public static List<VirtualWealthDTO> genVirtualWealthDTOList(List<VirtualWealthDO> virtualWealthDOs) {
        if (virtualWealthDOs == null) {
            return Collections.emptyList();
        }

        List<VirtualWealthDTO> virtualWealthDTOs = new ArrayList<>();
        for (VirtualWealthDO virtualWealthDO : virtualWealthDOs) {
            virtualWealthDTOs.add(genVirtualWealthDTO(virtualWealthDO));
        }
        return virtualWealthDTOs;
    }

    public static VirtualWealthDTO genVirtualWealthDTO(VirtualWealthDO virtualWealthDO) {
        if (virtualWealthDO == null) {
            return null;
        }

        VirtualWealthDTO virtualWealthDTO = new VirtualWealthDTO();
        BeanUtils.copyProperties(virtualWealthDO, virtualWealthDTO);
        return virtualWealthDTO;
    }

    public static VirtualWealthDO genVirtualWealthDO(VirtualWealthDTO virtualWealthDTO) {
        if (virtualWealthDTO == null) {
            return null;
        }

        VirtualWealthDO virtualWealthDO = new VirtualWealthDO();
        BeanUtils.copyProperties(virtualWealthDTO, virtualWealthDO);
        return virtualWealthDO;
    }


    public static GrantRuleDO genGrantRuleDO(GrantRuleDTO grantRuleDTO) {
        if (grantRuleDTO == null) {
            return null;
        }
        GrantRuleDO grantRuleDO = new GrantRuleDO();
        BeanUtils.copyProperties(grantRuleDTO, grantRuleDO);
        return grantRuleDO;
    }

    public static GrantRuleDTO genGrantRuleDTO(GrantRuleDO grantRuleDO) {
        if (grantRuleDO == null) {
            return null;
        }
        GrantRuleDTO grantRuleDTO = new GrantRuleDTO();
        BeanUtils.copyProperties(grantRuleDO, grantRuleDTO);
        return grantRuleDTO;
    }

    public static List<GrantRuleDTO> genGrantRuleDTOList(List<GrantRuleDO> grantRuleDOs) {
        if (grantRuleDOs == null) {
            return Collections.emptyList();
        }
        List<GrantRuleDTO> grantRuleDTOs = new ArrayList<GrantRuleDTO>();
        GrantRuleDTO grantRuleDTO;
        for (GrantRuleDO grantRuleDO : grantRuleDOs) {
            grantRuleDTO = new GrantRuleDTO();
            BeanUtils.copyProperties(grantRuleDO, grantRuleDTO);
            grantRuleDTOs.add(grantRuleDTO);
        }
        return grantRuleDTOs;
    }

    public static List<RuleModuleDO> genRuleModuleDOList(List<RuleModuleDTO> ruleModuleDTOs) {
        if (ruleModuleDTOs == null) {
            return Collections.emptyList();
        }
        List<RuleModuleDO> ruleModuleDOs = new ArrayList<RuleModuleDO>();
        RuleModuleDO ruleModuleDO;
        for (RuleModuleDTO ruleModuleDTO : ruleModuleDTOs) {
            ruleModuleDO = new RuleModuleDO();
            BeanUtils.copyProperties(ruleModuleDTO, ruleModuleDO);
            ruleModuleDOs.add(ruleModuleDO);
        }
        return ruleModuleDOs;
    }

    public static List<RuleModuleDTO> genRuleModuleDTOList(List<RuleModuleDO> ruleModuleDOs) {
        if (ruleModuleDOs == null) {
            return Collections.emptyList();
        }
        List<RuleModuleDTO> ruleModuleDTOs = new ArrayList<RuleModuleDTO>();
        RuleModuleDTO ruleModuleDTO;
        for (RuleModuleDO ruleModuleDO : ruleModuleDOs) {
            ruleModuleDTO = new RuleModuleDTO();
            BeanUtils.copyProperties(ruleModuleDO, ruleModuleDTO);
            ruleModuleDTOs.add(ruleModuleDTO);
        }
        return ruleModuleDTOs;
    }

    public static GrantedWealthDO genGrantedWealthDO(GrantedWealthDTO grantedWealthDTO) {
        if (grantedWealthDTO == null) {
            return null;
        }

        GrantedWealthDO grantedWealthDO = new GrantedWealthDO();
        BeanUtils.copyProperties(grantedWealthDTO, grantedWealthDO);
        return grantedWealthDO;
    }

    public static GrantedWealthDTO genGrantedWealthDTO(GrantedWealthDO grantedWealthDO) {
        if (grantedWealthDO == null) {
            return null;
        }

        GrantedWealthDTO grantedWealthDTO = new GrantedWealthDTO();
        BeanUtils.copyProperties(grantedWealthDO, grantedWealthDTO);
        return grantedWealthDTO;
    }
}