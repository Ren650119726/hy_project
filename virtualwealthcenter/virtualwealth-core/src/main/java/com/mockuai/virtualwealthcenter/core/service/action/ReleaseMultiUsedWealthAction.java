//package com.mockuai.virtualwealthcenter.core.service.action;
//
//import com.mockuai.appcenter.common.domain.AppInfoDTO;
//import com.mockuai.virtualwealthcenter.common.api.VirtualWealthResponse;
//import com.mockuai.virtualwealthcenter.common.constant.ActionEnum;
//import com.mockuai.virtualwealthcenter.common.constant.ResponseCode;
//import com.mockuai.virtualwealthcenter.common.constant.WealthUseStatus;
//import com.mockuai.virtualwealthcenter.common.domain.qto.UsedWealthQTO;
//import com.mockuai.virtualwealthcenter.core.domain.UsedWealthDO;
//import com.mockuai.virtualwealthcenter.core.exception.VirtualWealthException;
//import com.mockuai.virtualwealthcenter.core.manager.UsedWealthManager;
//import com.mockuai.virtualwealthcenter.core.manager.WealthAccountManager;
//import com.mockuai.virtualwealthcenter.core.service.RequestContext;
//import com.mockuai.virtualwealthcenter.core.util.VirtualWealthPreconditions;
//import com.mockuai.virtualwealthcenter.core.util.VirtualWealthUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.Resource;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * 批量释放预使用的虚拟财富
// * <p/>
// * Created by edgar.zr on 1/13/16.
// */
//@Service
//public class ReleaseMultiUsedWealthAction extends TransAction {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(ReleaseMultiUsedWealthAction.class);
//
//    @Resource
//    private UsedWealthManager usedWealthManager;
//    @Resource
//    private WealthAccountManager wealthAccountManager;
//
//    @Override
//    protected VirtualWealthResponse doTransaction(RequestContext context) throws VirtualWealthException {
//
//        List<Long> orderIds = (List<Long>) context.getRequest().getParam("orderIds");
//        Long userId = (Long) context.getRequest().getParam("userId");
//        AppInfoDTO appInfo = (AppInfoDTO) context.get("appInfo");
//
//        VirtualWealthPreconditions.checkNotEmpty(orderIds, "orderIds");
//        VirtualWealthPreconditions.checkNotNull(userId, "userId");
//
//        for (Long orderId : orderIds) {
//            singleShop(orderId, userId, appInfo.getBizCode());
//        }
//
//        return VirtualWealthUtils.getSuccessResponse();
//    }
//
//    private void singleShop(Long orderId, Long userId, String bizCode) throws VirtualWealthException {
//
//        try {
//            UsedWealthQTO usedWealthQTO = new UsedWealthQTO();
//            usedWealthQTO.setOrderId(orderId);
//            usedWealthQTO.setUserId(userId);
//            //查询虚拟财富预使用记录
//            List<UsedWealthDO> usedWealthDOs = usedWealthManager.queryUsedWealth(usedWealthQTO);
//
//            //如果该订单下面没有任何预使用的虚拟财富信息，则打印日志，并直接返回成功
//            if (usedWealthDOs == null || usedWealthDOs.isEmpty()) {
//                LOGGER.error("there is not any used wealth under this order, orderId:{}, userId:{}", orderId, userId);
//                throw new VirtualWealthException(ResponseCode.BIZ_E_USED_WEALTH_UNDER_ORDER_NOT_EXISTS);
//            }
//
//            List<Long> idList = new ArrayList<Long>();
//            //虚拟财富预使用记录状态检查
//            for (UsedWealthDO usedWealthDO : usedWealthDOs) {
//                if (usedWealthDO.getStatus().intValue() != WealthUseStatus.PRE_USE.getValue()) {
//                    throw new VirtualWealthException(ResponseCode.WEALTH_USED_RECORD_STATUS_ILLEGAL);
//                }
//                idList.add(usedWealthDO.getId());
//            }
//
//            int opNum = usedWealthManager.updateWealthStatus(idList, userId,
//                    WealthUseStatus.PRE_USE.getValue(), WealthUseStatus.CANCELED.getValue());
//
//            if (opNum != idList.size()) {
//                LOGGER.error("error to update wealth status, userId:{}, fromStatus:{}, toStatus:{}",
//                        userId, WealthUseStatus.PRE_USE.getValue(), WealthUseStatus.CANCELED.getValue());
//                throw new VirtualWealthException(ResponseCode.SERVICE_EXCEPTION);
//            }
//
//            //将取消的虚拟财富额度回补到虚拟账户中去
//            for (UsedWealthDO usedWealthDO : usedWealthDOs) {
//                opNum = wealthAccountManager.increaseAccountBalance(usedWealthDO.getWealthAccountId(),
//                        usedWealthDO.getUserId(), usedWealthDO.getAmount());
//                if (opNum != 1) {
//                    LOGGER.error("error to increase the wealth account balance, wealthAccountId:{}, userId:{}, userAmount:{}",
//                            usedWealthDO.getWealthAccountId(), usedWealthDO.getUserId(), usedWealthDO.getAmount());
//                    throw new VirtualWealthException(ResponseCode.SERVICE_EXCEPTION);
//                }
//            }
//        } catch (VirtualWealthException e) {
//            LOGGER.error("Action failed, {}, userId : {}, orderId : {}, bizCode : {}", getName(), userId, orderId, bizCode);
//            throw new VirtualWealthException(e.getCode(), e.getMessage());
//        }
//    }
//
//    @Override
//    public String getName() {
//        return ActionEnum.RELEASE_MULTI_USED_WEALTH.getActionName();
//    }
//}