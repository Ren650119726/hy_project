//package com.mockuai.virtualwealthcenter.core.service.action.grant;
//
//import com.mockuai.appcenter.common.domain.AppInfoDTO;
//import com.mockuai.virtualwealthcenter.common.api.VirtualWealthResponse;
//import com.mockuai.virtualwealthcenter.common.constant.ActionEnum;
//import com.mockuai.virtualwealthcenter.common.constant.GrantRuleType;
//import com.mockuai.virtualwealthcenter.common.constant.ResponseCode;
//import com.mockuai.virtualwealthcenter.common.constant.SourceType;
//import com.mockuai.virtualwealthcenter.common.constant.WealthType;
//import com.mockuai.virtualwealthcenter.common.domain.dto.GrantRuleDTO;
//import com.mockuai.virtualwealthcenter.core.exception.VirtualWealthException;
//import com.mockuai.virtualwealthcenter.core.manager.GrantRuleManager;
//import com.mockuai.virtualwealthcenter.core.service.RequestContext;
//import com.mockuai.virtualwealthcenter.core.service.action.TransAction;
//import com.mockuai.virtualwealthcenter.core.util.VirtualWealthPreconditions;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
///**
// * Created by edgar.zr on 11/11/15.
// */
//@Service
//public class AddGrantRuleAction extends TransAction {
//
//    @Autowired
//    private GrantRuleManager grantRuleManager;
//
//    @Override
//    protected VirtualWealthResponse doTransaction(RequestContext context) throws VirtualWealthException {
//
//        GrantRuleDTO grantRuleDTO = (GrantRuleDTO) context.getRequest().getParam("grantRuleDTO");
//        AppInfoDTO appInfo = (AppInfoDTO) context.get("appInfo");
//        grantRuleDTO.setBizCode(appInfo.getBizCode());
//
//        VirtualWealthPreconditions.checkNotNull(grantRuleDTO, "grantRuleDTO");
//        VirtualWealthPreconditions.checkNotNull(grantRuleDTO.getCreatorId(), "creatorId");
//        VirtualWealthPreconditions.checkNotNull(grantRuleDTO.getRuleType(), "ruleType");
//        VirtualWealthPreconditions.checkNotNull(grantRuleDTO.getSourceType(), "sourceType");
//        VirtualWealthPreconditions.checkNotNull(grantRuleDTO.getWealthType(), "wealthType");
//        VirtualWealthPreconditions.checkNotNull(grantRuleDTO.getRuleModuleDTOs(), "ruleModules");
//
//        if (GrantRuleType.getByValue(grantRuleDTO.getRuleType()) == null) {
//            return new VirtualWealthResponse(ResponseCode.PARAMETER_NULL, "ruleType is invalid");
//        }
//        if (WealthType.getByValue(grantRuleDTO.getWealthType()) == null) {
//            return new VirtualWealthResponse(ResponseCode.PARAMETER_NULL, "wealthType is invalid");
//        }
//        if (SourceType.getByValue(grantRuleDTO.getSourceType()) == null) {
//            return new VirtualWealthResponse(ResponseCode.PARAMETER_NULL, "sourceType is invalid");
//        }
//        if (grantRuleDTO.getRuleModuleDTOs().isEmpty()) {
//            return new VirtualWealthResponse(ResponseCode.PARAMETER_NULL, "ruleModules is empty");
//        }
//        if (StringUtils.isBlank(grantRuleDTO.getName())) {
//            grantRuleDTO.setName(SourceType.getByValue(grantRuleDTO.getSourceType()).getName());
//        }
//
//        grantRuleManager.addGrantRule(grantRuleDTO);
//
//        return new VirtualWealthResponse(grantRuleDTO.getId());
//    }
//
//    @Override
//    public String getName() {
//        return ActionEnum.ADD_GRANT_RULE.getActionName();
//    }
//}