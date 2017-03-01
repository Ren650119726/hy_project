//package com.mockuai.virtualwealthcenter.core.service.action;
//
//import com.mockuai.appcenter.common.domain.AppInfoDTO;
//import com.mockuai.virtualwealthcenter.common.api.VirtualWealthResponse;
//import com.mockuai.virtualwealthcenter.common.constant.ActionEnum;
//import com.mockuai.virtualwealthcenter.common.constant.ResponseCode;
//import com.mockuai.virtualwealthcenter.common.constant.SourceType;
//import com.mockuai.virtualwealthcenter.common.constant.WealthType;
//import com.mockuai.virtualwealthcenter.common.domain.dto.VirtualWealthDTO;
//import com.mockuai.virtualwealthcenter.core.domain.GrantedWealthDO;
//import com.mockuai.virtualwealthcenter.core.domain.WealthAccountDO;
//import com.mockuai.virtualwealthcenter.core.exception.VirtualWealthException;
//import com.mockuai.virtualwealthcenter.core.manager.GrantedWealthManager;
//import com.mockuai.virtualwealthcenter.core.manager.VirtualWealthManager;
//import com.mockuai.virtualwealthcenter.core.manager.WealthAccountManager;
//import com.mockuai.virtualwealthcenter.core.service.RequestContext;
//import com.mockuai.virtualwealthcenter.core.util.VirtualWealthPreconditions;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.Resource;
//
///**
// * Created by zengzhangqiang on 5/25/15.
// */
//@Service
//public class GrantVirtualWealthAction extends TransAction {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(GrantVirtualWealthAction.class.getName());
//
//    @Resource
//    private VirtualWealthManager virtualWealthManager;
//    @Resource
//    private GrantedWealthManager grantedWealthManager;
//    @Resource
//    private WealthAccountManager wealthAccountManager;
//
//    @Override
//    protected VirtualWealthResponse doTransaction(RequestContext context) throws VirtualWealthException {
//        Long wealthCreatorId = (Long) context.getRequest().getParam("wealthCreatorId");
//        Integer wealthType = (Integer) context.getRequest().getParam("wealthType");
//        Integer sourceType = (Integer) context.getRequest().getParam("sourceType");
//        Long grantAmount = (Long) context.getRequest().getParam("grantAmount");
//        Long orderId = (Long) context.getRequest().getParam("orderId");
//        Long receiverId = (Long) context.getRequest().getParam("receiverId");
//
//        AppInfoDTO appInfo = (AppInfoDTO) context.get("appInfo");
//
//        // 兼容原有的发放流程
//        if (wealthType == null) {
//            wealthType = WealthType.VIRTUAL_WEALTH.getValue();
//        }
//        if (WealthType.getByValue(wealthType) == null) {
//            return new VirtualWealthResponse(ResponseCode.PARAMETER_NULL, "wealthType is empty or invalid");
//        }
//        // 兼容原有的发放流程
//        if (sourceType == null) {
//            sourceType = SourceType.OTHER.getValue();
//        }
//        if (SourceType.getByValue(sourceType) == null) {
//            return new VirtualWealthResponse(ResponseCode.PARAMETER_NULL, "sourceType is empty or invalid");
//        }
//        VirtualWealthPreconditions.checkNotNull(receiverId, "receiverId");
//
//        try {
//
//            // 根据平台和财富类型
//            VirtualWealthDTO virtualWealthDTO = virtualWealthManager.getVirtualWealth(appInfo.getBizCode(), wealthCreatorId, wealthType);
//
//            //虚拟财富的业务状态校验，总量必须少于（已发放数量＋待发放数量）或者财富总量为无限
//            if (virtualWealthDTO.getAmount() < virtualWealthDTO.getGrantedAmount() + grantAmount && virtualWealthDTO.getAmount().longValue() != -1) {
//                return new VirtualWealthResponse(ResponseCode.VIRTUAL_WEALTH_NOT_ENOUGH);
//            }
//
//            //查询用户虚拟财富账户，如果账户不存在则创建
//            WealthAccountDO wealthAccountDO = wealthAccountManager.getWealthAccount(receiverId, wealthType, appInfo.getBizCode());
//            if (wealthAccountDO == null) {
//                WealthAccountDO userWealthAccount = new WealthAccountDO();
//                userWealthAccount.setBizCode(appInfo.getBizCode());
//                userWealthAccount.setWealthType(wealthType);
//                userWealthAccount.setUserId(receiverId);
//                try {
//                    Long wealthAccountId = wealthAccountManager.addWealthAccount(userWealthAccount);
//                    userWealthAccount.setId(wealthAccountId);
//                    wealthAccountDO = userWealthAccount;
//                } catch (VirtualWealthException e) {
//                    if (e.getCode() == ResponseCode.DB_OP_ERROR_OF_DUPLICATE_ENTRY.getCode()) {
//                        wealthAccountDO = wealthAccountManager.getWealthAccount(receiverId, wealthType, appInfo.getBizCode());
//                    }
//                }
//            }
//
//            //发放虚拟财富
//            GrantedWealthDO grantedWealthDO = new GrantedWealthDO();
//            grantedWealthDO.setBizCode(appInfo.getBizCode());
//            grantedWealthDO.setAmount(grantAmount);
//            grantedWealthDO.setWealthId(virtualWealthDTO.getId());
//            grantedWealthDO.setWealthType(wealthType);
//            grantedWealthDO.setSourceType(sourceType);
//            grantedWealthDO.setGranterId(wealthCreatorId == null ? virtualWealthDTO.getCreatorId() : wealthCreatorId);
//            grantedWealthDO.setReceiverId(receiverId);
//            grantedWealthDO.setOrderId(orderId);
//            grantedWealthManager.addGrantedWealth(grantedWealthDO);
//
//            int opCount = wealthAccountManager.increaseAccountBalance(wealthAccountDO.getId(), wealthAccountDO.getUserId(), grantAmount);
//
//            if (opCount != 1) {
//                LOGGER.error("error of increaseAccountBalance, wealthAccountId : {}, userId : {}, grantAmount : {}",
//                        wealthAccountDO.getId(), wealthAccountDO.getUserId(), grantAmount);
//                return new VirtualWealthResponse(ResponseCode.SERVICE_EXCEPTION);
//            }
//
//            virtualWealthManager.increaseGrantedVirtualWealth(virtualWealthDTO.getId(), grantAmount);
//
//            return new VirtualWealthResponse(ResponseCode.SUCCESS);
//        } catch (VirtualWealthException e) {
//            LOGGER.error("Action failed, {}, wealthType : {}, sourceType : {}, grantAmount : {}, receiverId : {}, bizCode : {}",
//                    getName(), wealthType, sourceType, grantAmount, receiverId, appInfo.getBizCode());
//            return new VirtualWealthResponse(e.getCode(), e.getMessage());
//        }
//    }
//
//    @Override
//    public String getName() {
//        return ActionEnum.GRANT_VIRTUAL_WEALTH.getActionName();
//    }
//}