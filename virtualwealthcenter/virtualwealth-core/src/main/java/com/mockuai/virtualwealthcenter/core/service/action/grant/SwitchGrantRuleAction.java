//package com.mockuai.virtualwealthcenter.core.service.action.grant;
//
//import com.mockuai.appcenter.common.domain.AppInfoDTO;
//import com.mockuai.virtualwealthcenter.common.api.VirtualWealthResponse;
//import com.mockuai.virtualwealthcenter.common.constant.ActionEnum;
//import com.mockuai.virtualwealthcenter.common.constant.GrantRuleStatus;
//import com.mockuai.virtualwealthcenter.common.constant.ResponseCode;
//import com.mockuai.virtualwealthcenter.core.exception.VirtualWealthException;
//import com.mockuai.virtualwealthcenter.core.manager.GrantRuleManager;
//import com.mockuai.virtualwealthcenter.core.service.RequestContext;
//import com.mockuai.virtualwealthcenter.core.service.action.TransAction;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
///**
// * Created by edgar.zr on 11/11/15.
// * 开关财富发送规则
// */
//@Service
//public class SwitchGrantRuleAction extends TransAction {
//
//    @Autowired
//    private GrantRuleManager grantRuleManager;
//
//    @Override
//    protected VirtualWealthResponse doTransaction(RequestContext context) throws VirtualWealthException {
//
//        Long grantRuleId = (Long) context.getRequest().getParam("grantRuleId");
//        Long creatorId = (Long) context.getRequest().getParam("creatorId");
//        Integer status = (Integer) context.getRequest().getParam("status");
//        AppInfoDTO appInfo = (AppInfoDTO) context.get("appInfo");
//
//        if (grantRuleId == null) {
//            return new VirtualWealthResponse(ResponseCode.PARAMETER_NULL, "grantRuleId is null");
//        }
//        if (creatorId == null) {
//            return new VirtualWealthResponse(ResponseCode.PARAMETER_NULL, "creatorId is null");
//        }
//        if (status == null) {
//            return new VirtualWealthResponse(ResponseCode.PARAMETER_NULL, "status is null");
//        }
//        if (GrantRuleStatus.getByValue(status) == null) {
//            return new VirtualWealthResponse(ResponseCode.PARAMETER_ERROR, "status is invalid");
//        }
//
//        grantRuleManager.switchGrantRule(appInfo.getBizCode(), grantRuleId, creatorId, status);
//
//        return new VirtualWealthResponse(ResponseCode.SUCCESS);
//    }
//
//    @Override
//    public String getName() {
//        return ActionEnum.SWITCH_GRANT_RULE.getActionName();
//    }
//}