//package com.mockuai.virtualwealthcenter.core.service.action.grant;
//
//import com.mockuai.appcenter.common.domain.AppInfoDTO;
//import com.mockuai.virtualwealthcenter.common.api.VirtualWealthResponse;
//import com.mockuai.virtualwealthcenter.common.constant.ActionEnum;
//import com.mockuai.virtualwealthcenter.common.constant.ResponseCode;
//import com.mockuai.virtualwealthcenter.common.constant.SourceType;
//import com.mockuai.virtualwealthcenter.common.constant.WealthType;
//import com.mockuai.virtualwealthcenter.common.domain.dto.GrantRuleDTO;
//import com.mockuai.virtualwealthcenter.common.domain.qto.GrantRuleQTO;
//import com.mockuai.virtualwealthcenter.core.exception.VirtualWealthException;
//import com.mockuai.virtualwealthcenter.core.manager.GrantRuleManager;
//import com.mockuai.virtualwealthcenter.core.service.RequestContext;
//import com.mockuai.virtualwealthcenter.core.service.action.TransAction;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
///**
// * Created by edgar.zr on 11/11/15.
// */
//@Service
//public class QueryGrantRuleAction extends TransAction {
//
//    @Autowired
//    private GrantRuleManager grantRuleManager;
//
//    @Override
//    protected VirtualWealthResponse doTransaction(RequestContext context) throws VirtualWealthException {
//
//        GrantRuleQTO grantRuleQTO = (GrantRuleQTO) context.getRequest().getParam("grantRuleQTO");
//        AppInfoDTO appInfo = (AppInfoDTO) context.get("appInfo");
//
//        if (grantRuleQTO == null) {
//            return new VirtualWealthResponse(ResponseCode.PARAMETER_NULL, "grantRuleQTO is null");
//        }
//
//        grantRuleQTO.setBizCode(appInfo.getBizCode());
//
//        if (grantRuleQTO.getWealthType() == null || WealthType.getByValue(grantRuleQTO.getWealthType()) == null) {
//            return new VirtualWealthResponse(ResponseCode.PARAMETER_NULL, "wealthType is null or invalid");
//        }
//
//        if (grantRuleQTO.getSourceType() == null || SourceType.getByValue(grantRuleQTO.getSourceType()) == null) {
//            return new VirtualWealthResponse(ResponseCode.PARAMETER_NULL, "sourceType is null or invalid");
//        }
//
//        List<GrantRuleDTO> grantRuleDTOs = grantRuleManager.queryGrantRule(grantRuleQTO);
//
//        return new VirtualWealthResponse(grantRuleDTOs);
//    }
//
//    @Override
//    public String getName() {
//        return ActionEnum.QUERY_GRANT_RULE.getActionName();
//    }
//}