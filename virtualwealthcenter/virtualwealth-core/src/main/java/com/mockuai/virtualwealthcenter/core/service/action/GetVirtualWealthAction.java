//package com.mockuai.virtualwealthcenter.core.service.action;
//
//import com.mockuai.appcenter.common.domain.AppInfoDTO;
//import com.mockuai.virtualwealthcenter.common.api.VirtualWealthResponse;
//import com.mockuai.virtualwealthcenter.common.constant.ActionEnum;
//import com.mockuai.virtualwealthcenter.common.constant.ResponseCode;
//import com.mockuai.virtualwealthcenter.common.constant.WealthType;
//import com.mockuai.virtualwealthcenter.common.domain.dto.VirtualWealthDTO;
//import com.mockuai.virtualwealthcenter.core.exception.VirtualWealthException;
//import com.mockuai.virtualwealthcenter.core.manager.VirtualWealthManager;
//import com.mockuai.virtualwealthcenter.core.service.RequestContext;
//import com.mockuai.virtualwealthcenter.core.util.VirtualWealthPreconditions;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
///**
// * 查看商家虚拟财富，通过 bizCode
// * <p/>
// * Created by edgar.zr on 11/11/15.
// */
//@Service
//public class GetVirtualWealthAction extends TransAction {
//
//    @Autowired
//    private VirtualWealthManager virtualWealthManager;
//
//    @Override
//    protected VirtualWealthResponse doTransaction(RequestContext context) throws VirtualWealthException {
//
//        Long creatorId = (Long) context.getRequest().getParam("creatorId");
//        Integer wealthType = (Integer) context.getRequest().getParam("wealthType");
//        AppInfoDTO appInfo = (AppInfoDTO) context.get("appInfo");
//
//        VirtualWealthPreconditions.checkNotNull(creatorId, "creatorId");
//        VirtualWealthPreconditions.checkNotNull(wealthType, "wealthType");
//        if (WealthType.getByValue(wealthType) == null) {
//            return new VirtualWealthResponse(ResponseCode.PARAMETER_ERROR, "wealthType is invalid");
//        }
//
//        VirtualWealthDTO virtualWealthDTO = virtualWealthManager.getVirtualWealth(appInfo.getBizCode(), creatorId, wealthType);
//
//        return new VirtualWealthResponse(virtualWealthDTO);
//    }
//
//    @Override
//    public String getName() {
//        return ActionEnum.GET_VIRTUAL_WEALTH.getActionName();
//    }
//}