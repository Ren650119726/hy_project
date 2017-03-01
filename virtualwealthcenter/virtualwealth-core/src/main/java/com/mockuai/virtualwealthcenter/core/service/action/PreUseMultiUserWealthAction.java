//package com.mockuai.virtualwealthcenter.core.service.action;
//
//import com.mockuai.appcenter.common.domain.AppInfoDTO;
//import com.mockuai.virtualwealthcenter.common.api.VirtualWealthResponse;
//import com.mockuai.virtualwealthcenter.common.constant.ActionEnum;
//import com.mockuai.virtualwealthcenter.common.constant.ResponseCode;
//import com.mockuai.virtualwealthcenter.common.constant.WealthType;
//import com.mockuai.virtualwealthcenter.common.constant.WealthUseStatus;
//import com.mockuai.virtualwealthcenter.common.domain.dto.UsedWealthDTO;
//import com.mockuai.virtualwealthcenter.common.domain.dto.VirtualWealthDTO;
//import com.mockuai.virtualwealthcenter.core.domain.UsedWealthDO;
//import com.mockuai.virtualwealthcenter.core.domain.WealthAccountDO;
//import com.mockuai.virtualwealthcenter.core.exception.VirtualWealthException;
//import com.mockuai.virtualwealthcenter.core.manager.UsedWealthManager;
//import com.mockuai.virtualwealthcenter.core.manager.VirtualWealthManager;
//import com.mockuai.virtualwealthcenter.core.manager.WealthAccountManager;
//import com.mockuai.virtualwealthcenter.core.service.RequestContext;
//import com.mockuai.virtualwealthcenter.core.util.VirtualWealthPreconditions;
//import com.mockuai.virtualwealthcenter.core.util.VirtualWealthUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.Resource;
//import java.util.List;
//
///**
// * 批量预使用用户虚拟财富
// * <p/>
// * Created by edgar.zr on 1/13/16.
// */
//@Service
//public class PreUseMultiUserWealthAction extends TransAction {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(PreUseMultiUserWealthAction.class);
//
//    @Resource
//    private WealthAccountManager wealthAccountManager;
//
//    @Resource
//    private UsedWealthManager usedWealthManager;
//
//    @Autowired
//    private VirtualWealthManager virtualWealthManager;
//
//    @Override
//    protected VirtualWealthResponse doTransaction(RequestContext context) throws VirtualWealthException {
//
//        List<UsedWealthDTO> usedWealthDTOs = (List<UsedWealthDTO>) context.getRequest().getParam("usedWealthDTOs");
//        Long userId = (Long) context.getRequest().getParam("userId");
//        AppInfoDTO appInfo = (AppInfoDTO) context.get("appInfo");
//        VirtualWealthPreconditions.checkNotEmpty(usedWealthDTOs, "usedWealthDTOs");
//        VirtualWealthPreconditions.checkNotNull(userId, "userId");
//
//        for (UsedWealthDTO usedWealthDTO : usedWealthDTOs) {
//            VirtualWealthPreconditions.checkNotNull(usedWealthDTO, "usedWealthDTO");
//            VirtualWealthPreconditions.checkNotNull(usedWealthDTO.getAmount(), "amount");
//            VirtualWealthPreconditions.checkNotNull(usedWealthDTO.getWealthType(), "wealthType");
//            VirtualWealthPreconditions.checkNotNull(usedWealthDTO.getOrderId(), "orderId");
//            // TODO 增加 sellerId
//            singleShop(userId, usedWealthDTO.getWealthType(), usedWealthDTO.getOrderId(), usedWealthDTO.getAmount(), appInfo.getBizCode());
//        }
//
//        return VirtualWealthUtils.getSuccessResponse();
//    }
//
//    private void singleShop(Long userId, Integer wealthType, Long orderId, Long useAmount, String bizCode) throws VirtualWealthException {
//
//        try {
//            //判断指定虚拟账户业务状态
//            WealthAccountDO wealthAccountDO = wealthAccountManager.getWealthAccount(userId, wealthType, bizCode);
//
//            if (wealthAccountDO == null) {
//                LOGGER.error("error to found wealthAccount, userId : {}, wealthType : {}, bizCode : {}, orderId : {}",
//                        userId, wealthType, bizCode, orderId);
//                throw new VirtualWealthException(ResponseCode.WEALTH_ACCOUNT_IS_NOT_FOUND);
//            }
//
//            VirtualWealthDTO virtualWealthDTO = virtualWealthManager.getVirtualWealth(bizCode, 0L, wealthType);
//
//            if (virtualWealthDTO.getTradeMark().intValue() != 1) {
//                LOGGER.error("the wealthAccount cannot be in trade. wealthAccountId : {}, virtualWealthId : {}, userId : {}, wealthType : {}, orderId : {}, bizCode : {}",
//                        wealthAccountDO.getId(), virtualWealthDTO.getId(), userId, wealthType, orderId, bizCode);
//                throw new VirtualWealthException(ResponseCode.BIZ_E_VIRTUAL_WEALTH_CANNOT_BE_IN_TRADE);
//            }
//
//            // TODO 目前没有全局的虚拟财富使用控制开关，积分暂时用使用上限来做限制
//            if (virtualWealthDTO.getType().intValue() == WealthType.CREDIT.getValue()) {
//                if (virtualWealthDTO.getUpperLimit() == null || virtualWealthDTO.getUpperLimit().intValue() == 0) {
//                    throw new VirtualWealthException(ResponseCode.BIZ_E_VIRTUAL_WEALTH_CANNOT_BE_IN_TRADE);
//                }
//            }
//
//            //判断虚拟账户额度是否足够
//            if (wealthAccountDO.getAmount() < useAmount) {//虚拟账户余额不足
//                throw new VirtualWealthException(ResponseCode.ACCOUNT_BALANCE_NOT_ENOUGH);
//            }
//
//            int opNum = wealthAccountManager.decreaseAccountBalance(wealthAccountDO.getId(), userId, useAmount);
//            if (opNum != 1) {
//                LOGGER.error("error to decrease the wealth account balance, wealthAccountId:{}, userId:{}, userAmount:{}",
//                        wealthAccountDO.getId(), userId, useAmount);
//                throw new VirtualWealthException(ResponseCode.SERVICE_EXCEPTION);
//            }
//
//            //新增一条使用记录
//            UsedWealthDO usedWealthDO = new UsedWealthDO();
//            usedWealthDO.setWealthAccountId(wealthAccountDO.getId());
//            usedWealthDO.setOrderId(orderId);
//            usedWealthDO.setUserId(userId);
//            usedWealthDO.setAmount(useAmount);
//            usedWealthDO.setBizCode(bizCode);
//            usedWealthDO.setStatus(WealthUseStatus.PRE_USE.getValue());
//            usedWealthManager.addUsedWealth(usedWealthDO);
//
//        } catch (VirtualWealthException e) {
//            LOGGER.debug("Action failed, {}, userId : {}, orderId : {}, wealthType : {}, useAmount : {}, bizCode : {}",
//                    getName(), userId, orderId, wealthType, useAmount, bizCode);
//            throw new VirtualWealthException(e.getCode(), e.getMessage());
//        }
//    }
//
//    @Override
//    public String getName() {
//        return ActionEnum.PRE_USE_MULTI_USER_WEALTH.getActionName();
//    }
//}