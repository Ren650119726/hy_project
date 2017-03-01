//package com.mockuai.virtualwealthcenter.core.service.action;
//
//import com.mockuai.appcenter.common.domain.AppInfoDTO;
//import com.mockuai.usercenter.common.dto.UserDTO;
//import com.mockuai.virtualwealthcenter.common.api.VirtualWealthResponse;
//import com.mockuai.virtualwealthcenter.common.constant.ActionEnum;
//import com.mockuai.virtualwealthcenter.common.domain.dto.WealthAccountDTO;
//import com.mockuai.virtualwealthcenter.common.domain.qto.WealthAccountQTO;
//import com.mockuai.virtualwealthcenter.core.exception.VirtualWealthException;
//import com.mockuai.virtualwealthcenter.core.manager.UserManager;
//import com.mockuai.virtualwealthcenter.core.manager.WealthAccountManager;
//import com.mockuai.virtualwealthcenter.core.service.RequestContext;
//import com.mockuai.virtualwealthcenter.core.util.JsonUtil;
//import com.mockuai.virtualwealthcenter.core.util.ModelUtil;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * 批量查询帐号
// * <p/>
// * Created by edgar.zr on 12/21/15.
// */
//@Service
//public class ListWealthAccountBatchAction extends TransAction {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(ListWealthAccountBatchAction.class.getName());
//
//    @Autowired
//    private WealthAccountManager wealthAccountManager;
//    @Autowired
//    private UserManager userManager;
//
//    @Override
//    protected VirtualWealthResponse doTransaction(RequestContext context) throws VirtualWealthException {
//
//        Integer offset = (Integer) context.getRequest().getParam("offset");
//        Integer count = (Integer) context.getRequest().getParam("count");
//        Long startTime = (Long) context.getRequest().getParam("startTime");
//        Long endTime = (Long) context.getRequest().getParam("endTime");
//        AppInfoDTO appInfo = (AppInfoDTO) context.get("appInfo");
//
//        if (offset == null)
//            offset = 0;
//        if (count == null || count > 200)
//            count = 200;
//
//        Integer wealthType = null;
//        if (context.getRequest().getParam("wealthType") != null) {
//            wealthType = (Integer) context.getRequest().getParam("wealthType");
//        }
//
//        WealthAccountQTO wealthAccountQTO = new WealthAccountQTO();
//        wealthAccountQTO.setBizCode(appInfo.getBizCode());
//        wealthAccountQTO.setWealthType(wealthType);
//        wealthAccountQTO.setOffset(offset);
//        wealthAccountQTO.setCount(count);
//        if (startTime != null)
//            wealthAccountQTO.setStartTime(new Date(startTime));
//        if (endTime != null)
//            wealthAccountQTO.setEndTime(new Date(endTime));
//
//        List<WealthAccountDTO> wealthAccountDTOs =
//                ModelUtil.genWealthAccountDTOList(wealthAccountManager.queryWealthAccount(wealthAccountQTO));
//
//        Map<Long, WealthAccountDTO> userIdKeyAccountValue = new HashMap<Long, WealthAccountDTO>();
//        for (WealthAccountDTO wealthAccountDTO : wealthAccountDTOs)
//            userIdKeyAccountValue.put(wealthAccountDTO.getUserId(), wealthAccountDTO);
//
//        try {
//            List<UserDTO> userDTOs = userManager.queryByUserIdList(new ArrayList<>(userIdKeyAccountValue.keySet()), appInfo.getAppKey());
//            WealthAccountDTO wealthAccountDTO;
//            for (UserDTO userDTO : userDTOs) {
//                wealthAccountDTO = userIdKeyAccountValue.get(userDTO.getId());
//                if (wealthAccountDTO != null)
//                    wealthAccountDTO.setMobile(userDTO.getMobile());
//            }
//        } catch (VirtualWealthException e) {
//            LOGGER.error("fill mobile error, userIds : {}", JsonUtil.toJson(userIdKeyAccountValue.keySet()));
//        }
//
//        return new VirtualWealthResponse(wealthAccountDTOs, wealthAccountQTO.getTotalCount());
//    }
//
//    @Override
//    public String getName() {
//        return ActionEnum.QUERY_WEALTH_ACCOUNT_BATCH.getActionName();
//    }
//}