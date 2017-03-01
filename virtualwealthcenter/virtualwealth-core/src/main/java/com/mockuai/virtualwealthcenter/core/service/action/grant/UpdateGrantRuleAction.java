//package com.mockuai.virtualwealthcenter.core.service.action.grant;
//
//import com.mockuai.appcenter.common.domain.AppInfoDTO;
//import com.mockuai.virtualwealthcenter.common.api.VirtualWealthResponse;
//import com.mockuai.virtualwealthcenter.common.constant.ActionEnum;
//import com.mockuai.virtualwealthcenter.common.constant.ResponseCode;
//import com.mockuai.virtualwealthcenter.common.domain.dto.GrantRuleDTO;
//import com.mockuai.virtualwealthcenter.core.exception.VirtualWealthException;
//import com.mockuai.virtualwealthcenter.core.manager.GrantRuleManager;
//import com.mockuai.virtualwealthcenter.core.service.RequestContext;
//import com.mockuai.virtualwealthcenter.core.service.action.TransAction;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
///**
// * Created by edgar.zr on 11/11/15.
// */
//@Service
//public class UpdateGrantRuleAction extends TransAction {
//
//    @Autowired
//    private GrantRuleManager grantRuleManager;
//
//    @Override
//    protected VirtualWealthResponse doTransaction(RequestContext context) throws VirtualWealthException {
//
//        GrantRuleDTO grantRuleDTO = (GrantRuleDTO) context.getRequest().getParam("grantRuleDTO");
//        AppInfoDTO appInfo = (AppInfoDTO) context.get("appInfo");
//
//        if (grantRuleDTO == null) {
//            return new VirtualWealthResponse(ResponseCode.PARAMETER_NULL, "grantRuleDTO is null");
//        }
//        grantRuleDTO.setBizCode(appInfo.getBizCode());
//        if (grantRuleDTO.getId() == null) {
//            return new VirtualWealthResponse(ResponseCode.PARAMETER_NULL, "the id is null");
//        }
//        if (grantRuleDTO.getCreatorId() == null) {
//            return new VirtualWealthResponse(ResponseCode.PARAMETER_NULL, "creatorId is null");
//        }
//        if (grantRuleDTO.getRuleModuleDTOs() == null || grantRuleDTO.getRuleModuleDTOs().isEmpty()) {
//            return new VirtualWealthResponse(ResponseCode.PARAMETER_NULL, "ruleModules is empty");
//        }
//
//        grantRuleManager.updateGrantRule(grantRuleDTO);
//
//        return new VirtualWealthResponse(ResponseCode.SUCCESS);
//    }
//
//    @Override
//    public String getName() {
//        return ActionEnum.UPDATE_GRANT_RULE.getActionName();
//    }
//}