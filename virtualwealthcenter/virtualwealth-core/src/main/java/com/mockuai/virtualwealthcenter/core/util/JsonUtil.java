package com.mockuai.virtualwealthcenter.core.util;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mockuai.virtualwealthcenter.common.constant.GrantRuleStatus;
import com.mockuai.virtualwealthcenter.common.constant.GrantRuleType;
import com.mockuai.virtualwealthcenter.common.constant.SourceType;
import com.mockuai.virtualwealthcenter.common.constant.WealthType;
import com.mockuai.virtualwealthcenter.common.domain.dto.GrantRuleDTO;
import com.mockuai.virtualwealthcenter.common.domain.dto.RuleModuleDTO;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by zengzhangqiang on 4/24/15.
 */
public class JsonUtil {
    public static Gson gson;

    static {
        GsonBuilder gb = new GsonBuilder();
        gb.disableHtmlEscaping();
        gb.setFieldNamingPolicy(
                FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        gson = gb.create();
    }

    public static <T> T parseJson(String jsonStr, Class<T> tClass) {
        return gson.fromJson(jsonStr, tClass);
    }

    public static <T> T parseJson(String jsonStr, Type type) {
        return gson.fromJson(jsonStr, type);
    }

    /**
     * 使用Gson生成json字符串
     *
     * @param src
     * @return
     */
    public static String toJson(Object src) {
        return gson.toJson(src);
    }

    public static String genGrantRule(GrantRuleDTO grantRuleDTO) {
        ExclusionStrategy myExclusionStrategy = new ExclusionStrategy() {

            private List<String> filedNames = new ArrayList<String>(Arrays.asList("id", "creatorId", "status", "grantRuleId",
                    "deleteMark", "gmtCreated", "gmtModified", "name"));

            @Override
            public boolean shouldSkipField(FieldAttributes fa) {
                return filedNames.contains(fa.getName());
            }

            @Override
            public boolean shouldSkipClass(Class<?> clazz) {
                return false;
            }
        };
        Gson gson = new GsonBuilder()
                .setExclusionStrategies(myExclusionStrategy) // <---
                .create();
        return gson.toJson(grantRuleDTO);
    }

    public static void main(String[] args) {
        GrantRuleDTO grantRuleDTO = new GrantRuleDTO();
        grantRuleDTO.setName("name");
        grantRuleDTO.setRuleType(GrantRuleType.AMOUNT_RATIO.getValue());
        grantRuleDTO.setSourceType(SourceType.ORDER_PAY.getValue());
        grantRuleDTO.setBizCode("bizCode");
        grantRuleDTO.setStatus(GrantRuleStatus.ON.getValue());
        grantRuleDTO.setWealthType(WealthType.VIRTUAL_WEALTH.getValue());
        grantRuleDTO.setCreatorId(19L);
        List<RuleModuleDTO> ruleModuleDTOs = new ArrayList<RuleModuleDTO>();
        RuleModuleDTO ruleModuleDTO;
        ruleModuleDTO = new RuleModuleDTO();
        ruleModuleDTO.setRatio(1);
        ruleModuleDTO.setCreatorId(19L);
        ruleModuleDTO.setAmount(1000L);
        ruleModuleDTO.setBizCode("bbb");
        ruleModuleDTO.setDeleteMark(9);
        ruleModuleDTO.setGrantRuleId(19L);
        ruleModuleDTO.setId(333L);
        ruleModuleDTO.setParamA(119L);
        ruleModuleDTO.setParamB(1119L);
        ruleModuleDTOs.add(ruleModuleDTO);
        grantRuleDTO.setRuleModuleDTOs(ruleModuleDTOs);
        System.err.println(genGrantRule(grantRuleDTO));
    }
}