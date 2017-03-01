//package com.mockuai.virtualwealthcenter.core.service.action;
//
//import com.mockuai.appcenter.common.domain.AppInfoDTO;
//import com.mockuai.virtualwealthcenter.common.api.VirtualWealthResponse;
//import com.mockuai.virtualwealthcenter.common.constant.ActionEnum;
//import com.mockuai.virtualwealthcenter.common.constant.ResponseCode;
//import com.mockuai.virtualwealthcenter.common.constant.SourceType;
//import com.mockuai.virtualwealthcenter.common.constant.WealthType;
//import com.mockuai.virtualwealthcenter.common.domain.dto.GrantedWealthDTO;
//import com.mockuai.virtualwealthcenter.common.domain.dto.VirtualWealthDTO;
//import com.mockuai.virtualwealthcenter.core.exception.VirtualWealthException;
//import com.mockuai.virtualwealthcenter.core.manager.GrantedWealthManager;
//import com.mockuai.virtualwealthcenter.core.manager.VirtualWealthManager;
//import com.mockuai.virtualwealthcenter.core.service.RequestContext;
//import com.mockuai.virtualwealthcenter.core.util.VirtualWealthPreconditions;
//import com.mockuai.virtualwealthcenter.core.util.VirtualWealthUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
///**
// * Created by edgar.zr on 11/13/15.
// */
//@Service
//public class GrantVirtualWealthWithGrantRuleAction extends TransAction {
//
//    @Autowired
//    private GrantedWealthManager grantedWealthManager;
//
//    @Autowired
//    private VirtualWealthManager virtualWealthManager;
//
//    @Override
//    protected VirtualWealthResponse doTransaction(RequestContext context) throws VirtualWealthException {
//
//        GrantedWealthDTO grantedWealthDTO = (GrantedWealthDTO) context.getRequest().getParam("grantedWealthDTO");
//        AppInfoDTO appInfo = (AppInfoDTO) context.get("appInfo");
//
//        VirtualWealthPreconditions.checkNotNull(grantedWealthDTO, "grantedWealthDTO");
//        grantedWealthDTO.setBizCode(appInfo.getBizCode());
//
//        if (grantedWealthDTO.getWealthType() == null || WealthType.getByValue(grantedWealthDTO.getWealthType()) == null) {
//            return new VirtualWealthResponse(ResponseCode.PARAMETER_NULL, "wealthType is null or invalid");
//        }
//        if (grantedWealthDTO.getSourceType() == null || SourceType.getByValue(grantedWealthDTO.getSourceType()) == null) {
//            return new VirtualWealthResponse(ResponseCode.PARAMETER_NULL, "sourceType is null");
//        }
//        VirtualWealthPreconditions.checkNotNull(grantedWealthDTO.getBaseAmount(), "baseAmount");
//        VirtualWealthPreconditions.checkNotEmpty(grantedWealthDTO.getReceiverIdList(), "receiverIdList");
//        VirtualWealthPreconditions.checkNotNull(grantedWealthDTO.getGranterId(), "granterId");
//
//        VirtualWealthDTO virtualWealthDTO = virtualWealthManager.getVirtualWealth(appInfo.getBizCode(), grantedWealthDTO.getGranterId(), grantedWealthDTO.getWealthType());
//        grantedWealthDTO.setWealthId(virtualWealthDTO.getId());
//
//        grantedWealthDTO.setWealthId(virtualWealthDTO.getId());
//        Long grantedCount = grantedWealthManager.grantWealthByGrantRule(grantedWealthDTO);
//
//        return VirtualWealthUtils.getSuccessResponse(grantedCount);
//    }
//
//    @Override
//    public String getName() {
//        return ActionEnum.GRANT_VIRTUAL_WEALTH_WITH_GRANT_RULE.getActionName();
//    }
//}