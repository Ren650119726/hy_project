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
//import com.mockuai.virtualwealthcenter.core.service.action.TransAction;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
///**
// * 更新商家财富，目前暂时支持更新积分的使用配置
// * <p/>
// * Created by edgar.zr on 11/11/15.
// */
//@Service
//public class UpdateVirtualWealthAction extends TransAction {
//
//    @Autowired
//    private VirtualWealthManager virtualWealthManager;
//
//    @Override
//    protected VirtualWealthResponse doTransaction(RequestContext context) throws VirtualWealthException {
//
//        VirtualWealthDTO virtualWealthDTO = (VirtualWealthDTO) context.getRequest().getParam("virtualWealthDTO");
//        AppInfoDTO appInfo = (AppInfoDTO) context.get("appInfo");
//
//        if (virtualWealthDTO == null) {
//            return new VirtualWealthResponse(ResponseCode.PARAMETER_NULL, "virtualWealthDTO is null");
//        }
//        virtualWealthDTO.setBizCode(appInfo.getBizCode());
//        if (virtualWealthDTO.getType() == null) {
//            return new VirtualWealthResponse(ResponseCode.PARAMETER_NULL, "wealthType is null");
//        }
//        if (virtualWealthDTO.getType().intValue() == WealthType.CREDIT.getValue()) {
//            if (virtualWealthDTO.getExchangeRate() == null) {
//                return new VirtualWealthResponse(ResponseCode.PARAMETER_NULL, "exchangeRate is null");
//            }
//            if (virtualWealthDTO.getUpperLimit() == null) {
//                return new VirtualWealthResponse(ResponseCode.PARAMETER_NULL, "upperLimit is null");
//            }
//            if (WealthType.getByValue(virtualWealthDTO.getType()) == null) {
//                return new VirtualWealthResponse(ResponseCode.PARAMETER_ERROR, "wealthType is invalid");
//            }
//            if (virtualWealthDTO.getUpperLimit().intValue() > 100 || virtualWealthDTO.getUpperLimit().intValue() < 0) {
//                return new VirtualWealthResponse(ResponseCode.PARAMETER_ERROR, "upperLimit should be in [0, 100]");
//            }
//        } else {
//            if (virtualWealthDTO.getAmount() == null) {
//                return new VirtualWealthResponse(ResponseCode.PARAMETER_ERROR, "amount is null");
//            }
//            if (virtualWealthDTO.getGrantedAmount() == null) {
//                virtualWealthDTO.setGrantedAmount(0L);
//            }
//        }
//
//        VirtualWealthDTO virtualWealthDTODB = virtualWealthManager.getVirtualWealth(appInfo.getBizCode(), virtualWealthDTO.getCreatorId(), virtualWealthDTO.getType());
//
//        virtualWealthDTO.setId(virtualWealthDTODB.getId());
//        virtualWealthManager.updateVirtualWealth(appInfo.getBizCode(), virtualWealthDTO.getId(), virtualWealthDTO.getExchangeRate(), virtualWealthDTO.getUpperLimit());
//
//        return new VirtualWealthResponse(ResponseCode.SUCCESS);
//    }
//
//    @Override
//    public String getName() {
//        return ActionEnum.UPDATE_VIRTUAL_WEALTH.getActionName();
//    }
//}