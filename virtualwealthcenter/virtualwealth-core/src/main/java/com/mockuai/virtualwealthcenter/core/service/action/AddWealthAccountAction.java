//package com.mockuai.virtualwealthcenter.core.service.action;
//
//import com.mockuai.appcenter.common.domain.AppInfoDTO;
//import com.mockuai.virtualwealthcenter.common.api.VirtualWealthResponse;
//import com.mockuai.virtualwealthcenter.common.constant.ActionEnum;
//import com.mockuai.virtualwealthcenter.common.domain.dto.WealthAccountDTO;
//import com.mockuai.virtualwealthcenter.core.exception.VirtualWealthException;
//import com.mockuai.virtualwealthcenter.core.manager.WealthAccountManager;
//import com.mockuai.virtualwealthcenter.core.service.RequestContext;
//import com.mockuai.virtualwealthcenter.core.util.ModelUtil;
//import com.mockuai.virtualwealthcenter.core.util.VirtualWealthPreconditions;
//import com.mockuai.virtualwealthcenter.core.util.VirtualWealthUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
///**
// * 为用户添加虚拟财富账号
// * <p/>
// * Created by edgar.zr
// */
//@Service
//public class AddWealthAccountAction extends TransAction {
//
//    @Autowired
//    private WealthAccountManager wealthAccountManager;
//
//    @Override
//    protected VirtualWealthResponse doTransaction(RequestContext context) throws VirtualWealthException {
//
//        WealthAccountDTO wealthAccountDTO = (WealthAccountDTO) context.getRequest().getParam("wealthAccountDTO");
//        AppInfoDTO appInfo = (AppInfoDTO) context.get("appInfo");
//
//        VirtualWealthPreconditions.checkNotNull(wealthAccountDTO, "wealthAccountDTO");
//        VirtualWealthPreconditions.checkNotNull(wealthAccountDTO.getWealthType(), "wealthType");
//        VirtualWealthPreconditions.checkNotNull(wealthAccountDTO.getUserId(), "userId");
//
//        wealthAccountDTO.setBizCode(appInfo.getBizCode());
//
//        wealthAccountManager.addWealthAccount(ModelUtil.genWealthAccountDO(wealthAccountDTO));
//
//        return VirtualWealthUtils.getSuccessResponse();
//    }
//
//    @Override
//    public String getName() {
//        return ActionEnum.ADD_WEALTH_ACCOUNT.getActionName();
//    }
//}