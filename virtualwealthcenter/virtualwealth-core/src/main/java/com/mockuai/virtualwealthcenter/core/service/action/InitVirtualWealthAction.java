//package com.mockuai.virtualwealthcenter.core.service.action;
//
//import com.mockuai.appcenter.common.domain.AppInfoDTO;
//import com.mockuai.virtualwealthcenter.common.api.VirtualWealthResponse;
//import com.mockuai.virtualwealthcenter.common.constant.ActionEnum;
//import com.mockuai.virtualwealthcenter.common.constant.WealthType;
//import com.mockuai.virtualwealthcenter.common.domain.dto.VirtualWealthDTO;
//import com.mockuai.virtualwealthcenter.core.domain.VirtualWealthDO;
//import com.mockuai.virtualwealthcenter.core.exception.VirtualWealthException;
//import com.mockuai.virtualwealthcenter.core.manager.VirtualWealthManager;
//import com.mockuai.virtualwealthcenter.core.service.RequestContext;
//import com.mockuai.virtualwealthcenter.core.util.JsonUtil;
//import com.mockuai.virtualwealthcenter.core.util.VirtualWealthPreconditions;
//import com.mockuai.virtualwealthcenter.core.util.VirtualWealthUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
///**
// * 为入住平台初始化虚拟财富
// * <p/>
// * Created by edgar.zr on 12/22/15.
// */
//@Service
//public class InitVirtualWealthAction extends TransAction {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(InitVirtualWealthAction.class);
//
//    @Autowired
//    private VirtualWealthManager virtualWealthManager;
//
//    @Override
//    protected VirtualWealthResponse doTransaction(RequestContext context) throws VirtualWealthException {
//
//        Long sellerId = (Long) context.getRequest().getParam("sellerId");
//        AppInfoDTO appInfo = (AppInfoDTO) context.get("appInfo");
//
//        VirtualWealthPreconditions.checkNotNull(sellerId, "sellerId");
//
//        VirtualWealthDO virtualWealthDO;
//        VirtualWealthDTO virtualWealthDTO;
//        for (WealthType wealthType : WealthType.values()) {
//            try {
//                virtualWealthDTO = virtualWealthManager.getVirtualWealth(appInfo.getBizCode(), sellerId, wealthType.getValue());
//                LOGGER.warn("the virtualWealth exist, virtualWealthDO : {}", JsonUtil.toJson(virtualWealthDTO));
//                continue;
//            } catch (VirtualWealthException e) {
//                // 不存在帐号会正常抛异常
//            }
//            virtualWealthDO = virtualWealthManager.fakeVirtualWealth(appInfo.getBizCode(), sellerId, wealthType.getValue());
//            virtualWealthManager.addVirtualWealth(virtualWealthDO);
//        }
//        return VirtualWealthUtils.getSuccessResponse();
//    }
//
//    @Override
//    public String getName() {
//        return ActionEnum.INIT_VIRTUAL_WEALTH.getActionName();
//    }
//}