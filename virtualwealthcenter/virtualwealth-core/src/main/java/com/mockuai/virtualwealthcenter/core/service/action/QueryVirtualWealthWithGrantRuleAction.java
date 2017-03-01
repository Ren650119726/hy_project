//package com.mockuai.virtualwealthcenter.core.service.action;
//
//import com.mockuai.appcenter.common.domain.AppInfoDTO;
//import com.mockuai.virtualwealthcenter.common.api.VirtualWealthResponse;
//import com.mockuai.virtualwealthcenter.common.constant.ActionEnum;
//import com.mockuai.virtualwealthcenter.common.constant.ResponseCode;
//import com.mockuai.virtualwealthcenter.common.constant.SourceType;
//import com.mockuai.virtualwealthcenter.common.constant.WealthType;
//import com.mockuai.virtualwealthcenter.common.domain.dto.GrantRuleDTO;
//import com.mockuai.virtualwealthcenter.common.domain.dto.RuleModuleDTO;
//import com.mockuai.virtualwealthcenter.common.domain.dto.VirtualWealthDTO;
//import com.mockuai.virtualwealthcenter.core.exception.VirtualWealthException;
//import com.mockuai.virtualwealthcenter.core.manager.GrantRuleManager;
//import com.mockuai.virtualwealthcenter.core.manager.RuleModuleManager;
//import com.mockuai.virtualwealthcenter.core.manager.VirtualWealthManager;
//import com.mockuai.virtualwealthcenter.core.service.RequestContext;
//import com.mockuai.virtualwealthcenter.core.service.action.TransAction;
//import com.mockuai.virtualwealthcenter.core.util.VirtualWealthPreconditions;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * 查询商家虚拟财富和对应的财富发放规则
// * <p/>
// * Created by edgar.zr on 11/11/15.
// */
//@Service
//public class QueryVirtualWealthWithGrantRuleAction extends TransAction {
//
//    @Autowired
//    private VirtualWealthManager virtualWealthManager;
//
//    @Autowired
//    private GrantRuleManager grantRuleManager;
//
//    @Autowired
//    private RuleModuleManager ruleModuleManager;
//
//    @Override
//    protected VirtualWealthResponse doTransaction(RequestContext context) throws VirtualWealthException {
//
//        Integer wealthType = (Integer) context.getRequest().getParam("wealthType");
//        Integer sourceType = (Integer) context.getRequest().getParam("sourceType");
//        AppInfoDTO appInfo = (AppInfoDTO) context.get("appInfo");
//
//        VirtualWealthPreconditions.checkNotNull(wealthType, "wealthType");
//        if (WealthType.getByValue(wealthType) == null) {
//            return new VirtualWealthResponse(ResponseCode.PARAMETER_ERROR, "wealthType is invalid");
//        }
//        VirtualWealthPreconditions.checkNotNull(sourceType, "sourceType");
//        if (SourceType.getByValue(sourceType) == null) {
//            return new VirtualWealthResponse(ResponseCode.PARAMETER_ERROR, "sourceType is invalid");
//        }
//
//        Map<String, Object> result = new HashMap<>();
//        VirtualWealthDTO virtualWealthDTO = virtualWealthManager.getVirtualWealth(appInfo.getBizCode(), 0L, wealthType);
//        result.put("virtualWealth", virtualWealthDTO);
//
//        try {
//            GrantRuleDTO grantRuleDTO = grantRuleManager.getGrantRuleStatusOn(appInfo.getBizCode(), wealthType, sourceType);
//            // 如果没配置使用规则或者没有开启一个发放规则，fake 一个发放规则
//            if (virtualWealthDTO == null) {
//                throw new VirtualWealthException(ResponseCode.SERVICE_EXCEPTION);
//            }
//            List<RuleModuleDTO> ruleModuleDTOs = ruleModuleManager.getRuleModuleListByGrantRuleId(appInfo.getBizCode(), grantRuleDTO.getId(), null);
//            grantRuleDTO.setRuleModuleDTOs(ruleModuleDTOs);
//            result.put("grantRule", grantRuleDTO);
//        } catch (VirtualWealthException e) {
//            result.put("grantRule", grantRuleManager.fakeGrantRule(wealthType, sourceType).get(0));
//        }
//
//        return new VirtualWealthResponse(result);
//    }
//
//    @Override
//    public String getName() {
//        return ActionEnum.QUERY_VIRTUAL_WEALTH_WITH_GRANT_RULE.getActionName();
//    }
//}