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
// * 批量虚拟财富由预使用转换到正式使用
// * <p/>
// * Created by edgar.zr on 1/13/16.
// */
//@Service
//public class UseMultiUserWealthAction extends TransAction {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(UseMultiUserWealthAction.class);
//
//    @Resource
//    private UsedWealthManager usedWealthManager;
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
//            //查询虚拟财富预使用记录
//            UsedWealthQTO usedWealthQTO = new UsedWealthQTO();
//            usedWealthQTO.setOrderId(orderId);
//            usedWealthQTO.setUserId(userId);
//            List<UsedWealthDO> usedWealthDOs = usedWealthManager.queryUsedWealth(usedWealthQTO);
//
//            //如果该订单下面没有任何预使用的虚拟财富信息，则打印日志，并直接返回成功
//            if (usedWealthDOs == null || usedWealthDOs.isEmpty()) {
//                LOGGER.warn("there is not any used wealth under this order, orderId:{}, userId:{}", orderId, userId);
//                return;
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
//                    WealthUseStatus.PRE_USE.getValue(), WealthUseStatus.USED.getValue());
//
//            if (opNum != idList.size()) {
//                LOGGER.error("error to update wealth status, userId:{}, fromStatus:{}, toStatus:{}",
//                        userId, WealthUseStatus.PRE_USE.getValue(), WealthUseStatus.USED.getValue());
//                throw new VirtualWealthException(ResponseCode.SERVICE_EXCEPTION);
//            }
//        } catch (VirtualWealthException e) {
//            LOGGER.error("Action failed, {}, userId : {}, orderId : {}, bizCode : {}", getName(), userId, orderId, bizCode);
//            throw new VirtualWealthException(e.getCode(), e.getMessage());
//        }
//    }
//
//    @Override
//    public String getName() {
//        return ActionEnum.USE_MULTI_USER_WEALTH.getActionName();
//    }
//}