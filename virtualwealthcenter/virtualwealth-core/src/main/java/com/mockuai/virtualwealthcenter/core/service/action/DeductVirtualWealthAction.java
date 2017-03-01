//package com.mockuai.virtualwealthcenter.core.service.action;
//
//import com.mockuai.appcenter.common.domain.AppInfoDTO;
//import com.mockuai.virtualwealthcenter.common.api.VirtualWealthResponse;
//import com.mockuai.virtualwealthcenter.common.constant.ActionEnum;
//import com.mockuai.virtualwealthcenter.common.constant.ResponseCode;
//import com.mockuai.virtualwealthcenter.common.constant.WealthType;
//import com.mockuai.virtualwealthcenter.core.exception.VirtualWealthException;
//import com.mockuai.virtualwealthcenter.core.manager.WealthAccountManager;
//import com.mockuai.virtualwealthcenter.core.service.RequestContext;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
///**
// * Created by edgar.zr on 11/20/15.
// */
//@Service
//public class DeductVirtualWealthAction extends TransAction {
//
//    @Autowired
//    private WealthAccountManager wealthAccountManager;
//
//    @Override
//    protected VirtualWealthResponse doTransaction(RequestContext context) throws VirtualWealthException {
//
//        Long userId = (Long) context.getRequest().getParam("userId");
//        Integer wealthType = (Integer) context.getRequest().getParam("wealthType");
//        Long amount = (Long) context.getRequest().getParam("amount");
//        AppInfoDTO appInfo = (AppInfoDTO) context.get("appInfo");
//
//        if (amount == null || amount.longValue() <= 0) {
//            return new VirtualWealthResponse(ResponseCode.PARAMETER_ERROR, "amount is null or invalid");
//        }
//        if (wealthType == null || WealthType.getByValue(wealthType) == null) {
//            return new VirtualWealthResponse(ResponseCode.PARAMETER_ERROR, "wealthType is null or invalid");
//        }
//        if (userId == null) {
//            return new VirtualWealthResponse(ResponseCode.PARAMETER_NULL, "userId is null");
//        }
//        wealthAccountManager.deductWealthAccount(userId, wealthType, amount, appInfo.getBizCode());
//        return new VirtualWealthResponse(ResponseCode.SUCCESS);
//    }
//
//    @Override
//    public String getName() {
//        return ActionEnum.DEDUCT_VIRTUAL_WEALTH.getActionName();
//    }
//}