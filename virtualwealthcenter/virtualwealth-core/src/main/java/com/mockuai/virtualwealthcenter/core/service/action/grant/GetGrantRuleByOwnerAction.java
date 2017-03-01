//package com.mockuai.virtualwealthcenter.core.service.action.grant;
//
//import com.mockuai.appcenter.common.domain.AppInfoDTO;
//import com.mockuai.virtualwealthcenter.common.api.VirtualWealthResponse;
//import com.mockuai.virtualwealthcenter.common.constant.ActionEnum;
//import com.mockuai.virtualwealthcenter.common.domain.dto.GrantRuleDTO;
//import com.mockuai.virtualwealthcenter.core.exception.VirtualWealthException;
//import com.mockuai.virtualwealthcenter.core.manager.GrantRuleManager;
//import com.mockuai.virtualwealthcenter.core.service.RequestContext;
//import com.mockuai.virtualwealthcenter.core.service.action.TransAction;
//import com.mockuai.virtualwealthcenter.core.util.VirtualWealthPreconditions;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
///**
// * Created by edgar.zr on 12/12/15.
// */
//@Service
//public class GetGrantRuleByOwnerAction extends TransAction {
//
//    @Autowired
//    private GrantRuleManager grantRuleManager;
//
//    @Override
//    protected VirtualWealthResponse doTransaction(RequestContext context) throws VirtualWealthException {
//
//        Long ownerId = (Long) context.getRequest().getParam("ownerId");
//        Integer wealthType = (Integer) context.getRequest().getParam("wealthType");
//        Integer sourceType = (Integer) context.getRequest().getParam("sourceType");
//        Long creatorId = (Long) context.getRequest().getParam("creatorId");
//        AppInfoDTO appInfo = (AppInfoDTO) context.get("appInfo");
//
//        VirtualWealthPreconditions.checkNotNull(ownerId, "ownerId");
//        VirtualWealthPreconditions.checkNotNull(wealthType, "wealthType");
//        VirtualWealthPreconditions.checkNotNull(sourceType, "sourceType");
//        VirtualWealthPreconditions.checkNotNull(creatorId, "creatorId");
//
//        GrantRuleDTO grantRuleDTO = grantRuleManager.getGrantRuleByOwnerId(appInfo.getBizCode(), ownerId, wealthType, sourceType);
//
//        return new VirtualWealthResponse(grantRuleDTO);
//    }
//
//    @Override
//    public String getName() {
//        return ActionEnum.GET_GRANT_RULE_BY_OWNER.getActionName();
//    }
//}