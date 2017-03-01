//package com.mockuai.virtualwealthcenter.core.service.action;
//
//import com.mockuai.appcenter.common.domain.AppInfoDTO;
//import com.mockuai.virtualwealthcenter.common.api.VirtualWealthResponse;
//import com.mockuai.virtualwealthcenter.common.constant.ActionEnum;
//import com.mockuai.virtualwealthcenter.common.domain.dto.VirtualWealthDTO;
//import com.mockuai.virtualwealthcenter.common.domain.qto.VirtualWealthQTO;
//import com.mockuai.virtualwealthcenter.core.exception.VirtualWealthException;
//import com.mockuai.virtualwealthcenter.core.manager.VirtualWealthManager;
//import com.mockuai.virtualwealthcenter.core.service.RequestContext;
//import com.mockuai.virtualwealthcenter.core.util.ModelUtil;
//import com.mockuai.virtualwealthcenter.core.util.VirtualWealthPreconditions;
//import com.mockuai.virtualwealthcenter.core.util.VirtualWealthUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
///**
// * Created by edgar.zr on 5/11/2016.
// */
//@Service
//public class QueryVirtualWealthAction extends TransAction {
//
//    @Autowired
//    private VirtualWealthManager virtualWealthManager;
//
//    @Override
//    protected VirtualWealthResponse doTransaction(RequestContext context) throws VirtualWealthException {
//
//        VirtualWealthQTO virtualWealthQTO = (VirtualWealthQTO) context.getRequest().getParam("virtualWealthQTO");
//        AppInfoDTO appInfo = (AppInfoDTO) context.get("appInfo");
//
//        VirtualWealthPreconditions.checkNotNull(virtualWealthQTO, "virtualWealthQTO");
//        virtualWealthQTO.setBizCode(appInfo.getBizCode());
//
//        List<VirtualWealthDTO> virtualWealthDTOs =
//                ModelUtil.genVirtualWealthDTOList(virtualWealthManager.queryVirtualWealth(virtualWealthQTO));
//
//        return VirtualWealthUtils.getSuccessResponse(virtualWealthDTOs);
//    }
//
//    @Override
//    public String getName() {
//        return ActionEnum.QUERY_VIRTUAL_WEALTH.getActionName();
//    }
//}