//package com.mockuai.virtualwealthcenter.core.service.action.recharge;
//
//import com.mockuai.appcenter.common.domain.AppInfoDTO;
//import com.mockuai.virtualwealthcenter.common.api.Request;
//import com.mockuai.virtualwealthcenter.common.api.VirtualWealthResponse;
//import com.mockuai.virtualwealthcenter.common.constant.ActionEnum;
//import com.mockuai.virtualwealthcenter.common.constant.ResponseCode;
//import com.mockuai.virtualwealthcenter.common.domain.dto.RechargeRecordDTO;
//import com.mockuai.virtualwealthcenter.common.domain.qto.RechargeRecordQTO;
//import com.mockuai.virtualwealthcenter.core.exception.VirtualWealthException;
//import com.mockuai.virtualwealthcenter.core.manager.RechargeRecordManager;
//import com.mockuai.virtualwealthcenter.core.service.RequestContext;
//import com.mockuai.virtualwealthcenter.core.service.action.Action;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.Resource;
//import java.util.List;
//
///**
// * Created by duke on 16/4/19.
// */
//@Service
//public class QueryRechargeRecordAction implements Action {
//    private static final Logger log = LoggerFactory.getLogger(QueryRechargeRecordAction.class);
//
//    @Resource
//    private RechargeRecordManager rechargeRecordManager;
//
//    @Override
//    public VirtualWealthResponse execute(RequestContext context) throws VirtualWealthException {
//        Request request = context.getRequest();
//        RechargeRecordQTO rechargeRecordQTO = (RechargeRecordQTO) request.getParam("rechargeRecordQTO");
//        AppInfoDTO appInfo = (AppInfoDTO) context.get("appInfo");
//
//        if (rechargeRecordQTO == null) {
//            log.error("rechargeRecordQTO is null, bizCode: {}", appInfo.getBizCode());
//            throw new VirtualWealthException(ResponseCode.PARAMETER_NULL, "rechargeRecordQTO is null");
//        }
//
//        rechargeRecordQTO.setBizCode(appInfo.getBizCode());
//
//        List<RechargeRecordDTO> rechargeRecordDTOs = rechargeRecordManager.queryRecord(rechargeRecordQTO);
//        Long totalCount = rechargeRecordManager.totalCount(rechargeRecordQTO);
//
//        return new VirtualWealthResponse(rechargeRecordDTOs, totalCount);
//    }
//
//    @Override
//    public String getName() {
//        return ActionEnum.QUERY_RECHARGE_RECORD.getActionName();
//    }
//}