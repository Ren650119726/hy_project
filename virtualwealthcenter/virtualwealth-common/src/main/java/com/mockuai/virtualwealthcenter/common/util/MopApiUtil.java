package com.mockuai.virtualwealthcenter.common.util;

import com.mockuai.virtualwealthcenter.common.domain.dto.GrantRuleDTO;
import com.mockuai.virtualwealthcenter.common.domain.dto.RuleModuleDTO;
import com.mockuai.virtualwealthcenter.common.domain.dto.VirtualWealthDTO;
import com.mockuai.virtualwealthcenter.common.domain.dto.VirtualWealthItemDTO;
import com.mockuai.virtualwealthcenter.common.domain.dto.WealthAccountDTO;
import com.mockuai.virtualwealthcenter.common.domain.dto.mop.MopGrantRuleDTO;
import com.mockuai.virtualwealthcenter.common.domain.dto.mop.MopRuleModuleDTO;
import com.mockuai.virtualwealthcenter.common.domain.dto.mop.MopVirtualWealthDTO;
import com.mockuai.virtualwealthcenter.common.domain.dto.mop.MopWealthAccountDTO;
import com.mockuai.virtualwealthcenter.common.domain.dto.mop.VirtualWealthUidDTO;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MopApiUtil {

    private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static VirtualWealthUidDTO parseVirtualWealthUid(String virtualWealthUid) {
        if (virtualWealthUid == null) {
            return null;
        }

        String[] strs = virtualWealthUid.split("_");
        if (strs.length != 2) {
            return null;
        }

        VirtualWealthUidDTO virtualWealthUidDTO = new VirtualWealthUidDTO();

        long creatorId = Long.parseLong(strs[0]);
        long virtualWealthId = Long.parseLong(strs[1]);
        virtualWealthUidDTO.setCreatorId(creatorId);
        virtualWealthUidDTO.setVirtualWealthId(virtualWealthId);
        return virtualWealthUidDTO;
    }

    public static MopVirtualWealthDTO genMopVirtualWealthDTO(VirtualWealthDTO virtualWealthDTO) {
        MopVirtualWealthDTO mopVirtualWealthDTO = new MopVirtualWealthDTO();
        mopVirtualWealthDTO.setUpperLimit(virtualWealthDTO.getUpperLimit());
        mopVirtualWealthDTO.setExchangeRate(virtualWealthDTO.getExchangeRate());
        return mopVirtualWealthDTO;
    }

    public static MopGrantRuleDTO genMopGrantRuleDTO(GrantRuleDTO grantRuleDTO) {
        MopGrantRuleDTO mopGrantRuleDTO = new MopGrantRuleDTO();
        mopGrantRuleDTO.setSourceType(grantRuleDTO.getSourceType());
        mopGrantRuleDTO.setRuleType(grantRuleDTO.getRuleType());
        mopGrantRuleDTO.setRuleModuleList(genMopRuleModuleDTOList(grantRuleDTO.getRuleModuleDTOs()));
        return mopGrantRuleDTO;
    }

    public static List<MopRuleModuleDTO> genMopRuleModuleDTOList(List<RuleModuleDTO> ruleModuleDTOs) {
        List<MopRuleModuleDTO> mopRuleModuleDTOs = new ArrayList<MopRuleModuleDTO>();
        MopRuleModuleDTO mopRuleModuleDTO;
        for (RuleModuleDTO ruleModuleDTO : ruleModuleDTOs) {
            mopRuleModuleDTO = genMopRuleModuleDTO(ruleModuleDTO);
            if (mopRuleModuleDTO != null)
                mopRuleModuleDTOs.add(mopRuleModuleDTO);
        }
        return mopRuleModuleDTOs;
    }

    public static List<MopWealthAccountDTO> genMopWealthAccountDTOList(List<WealthAccountDTO> wealthAccountDTOs) {
        if (wealthAccountDTOs == null || wealthAccountDTOs.isEmpty())
            return Collections.EMPTY_LIST;

        List<MopWealthAccountDTO> mopWealthAccountDTOs = new ArrayList<MopWealthAccountDTO>();
        MopWealthAccountDTO mopWealthAccountDTO;
        for (WealthAccountDTO wealthAccountDTO : wealthAccountDTOs) {
            mopWealthAccountDTO = genMopWealthAccountDTO(wealthAccountDTO);
            if (mopWealthAccountDTO != null)
                mopWealthAccountDTOs.add(mopWealthAccountDTO);
        }
        return mopWealthAccountDTOs;
    }

    public static MopRuleModuleDTO genMopRuleModuleDTO(RuleModuleDTO ruleModuleDTO) {
        MopRuleModuleDTO mopRuleModuleDTO = new MopRuleModuleDTO();
        mopRuleModuleDTO.setAmount(ruleModuleDTO.getAmount());
        mopRuleModuleDTO.setParamA(ruleModuleDTO.getParamA());
        mopRuleModuleDTO.setParamB(ruleModuleDTO.getParamB());
        mopRuleModuleDTO.setRatio(ruleModuleDTO.getRatio());
        return mopRuleModuleDTO;
    }

    public static MopWealthAccountDTO genMopWealthAccountDTO(WealthAccountDTO wealthAccountDTO) {
        MopWealthAccountDTO mopWealthAccountDTO = new MopWealthAccountDTO();
        mopWealthAccountDTO.setWealthType(wealthAccountDTO.getWealthType());
        mopWealthAccountDTO.setTotalAmount(wealthAccountDTO.getTotal());
        mopWealthAccountDTO.setAmount(wealthAccountDTO.getAmount());
        mopWealthAccountDTO.setMobile(wealthAccountDTO.getMobile());
        mopWealthAccountDTO.setTransitionAmount(wealthAccountDTO.getTransitionAmount());
        mopWealthAccountDTO.setWillExpireAmount(wealthAccountDTO.getWillExpireAmount());
        if (wealthAccountDTO.getExchangeRate() != null)
            mopWealthAccountDTO.setExchangeRate("" + wealthAccountDTO.getExchangeRate());
        mopWealthAccountDTO.setUpperLimit(wealthAccountDTO.getUpperLimit());
        mopWealthAccountDTO.setWealthAccountUid(wealthAccountDTO.getUserId() + "_" + wealthAccountDTO.getId());
        return mopWealthAccountDTO;
    }

    public static String genVirtualWealthItemUid(VirtualWealthItemDTO virtualWealthItemDTO) {
        if (virtualWealthItemDTO == null) {
            return null;
        }
        return virtualWealthItemDTO.getSellerId() + "_" + virtualWealthItemDTO.getItemId();
    }

    public static String genVirtualWealthItemSkuUid(VirtualWealthItemDTO virtualWealthItemDTO) {
        if (virtualWealthItemDTO == null) {
            return null;
        }
        return virtualWealthItemDTO.getSellerId() + "_" + virtualWealthItemDTO.getSkuId();
    }
}