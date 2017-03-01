//package com.mockuai.virtualwealthcenter.core.service.action.recharge;
//
//import com.mockuai.appcenter.common.domain.AppInfoDTO;
//import com.mockuai.virtualwealthcenter.common.api.Request;
//import com.mockuai.virtualwealthcenter.common.api.VirtualWealthResponse;
//import com.mockuai.virtualwealthcenter.common.constant.ActionEnum;
//import com.mockuai.virtualwealthcenter.common.domain.dto.RechargeRecordDTO;
//import com.mockuai.virtualwealthcenter.core.exception.VirtualWealthException;
//import com.mockuai.virtualwealthcenter.core.manager.RechargeRecordManager;
//import com.mockuai.virtualwealthcenter.core.service.RequestContext;
//import com.mockuai.virtualwealthcenter.core.service.action.Action;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.Resource;
//
///**
// * Created by duke on 16/4/19.
// */
//@Service
//public class UpdateRechargeRecordAction implements Action {
//    private static final Logger log = LoggerFactory.getLogger(UpdateRechargeRecordAction.class);
//
//    @Resource
//    private RechargeRecordManager rechargeRecordManager;
//
//    @Override
//    public VirtualWealthResponse execute(RequestContext context) throws VirtualWealthException {
//        Request request = context.getRequest();
//        RechargeRecordDTO rechargeRecordDTO = (RechargeRecordDTO) request.getParam("rechargeRecordDTO");
//        AppInfoDTO appInfo = (AppInfoDTO) context.get("appInfo");
//
//        if (rechargeRecordDTO == null) {
//            log.error("rechargeRecordDTO is null, bizCode: {}", appInfo.getBizCode());
//        }
//
//        rechargeRecordDTO.setBizCode(appInfo.getBizCode());
//
//        rechargeRecordManager.updateRecord(rechargeRecordDTO);
//
//        return new VirtualWealthResponse(true);
//    }
//
//    @Override
//    public String getName() {
//        return ActionEnum.UPDATE_RECHARGE_RECORD.getActionName();
//    }
//}