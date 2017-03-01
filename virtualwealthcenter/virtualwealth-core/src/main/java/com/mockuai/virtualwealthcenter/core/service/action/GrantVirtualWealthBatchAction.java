//package com.mockuai.virtualwealthcenter.core.service.action;
//
//import com.mockuai.appcenter.common.domain.AppInfoDTO;
//import com.mockuai.usercenter.common.dto.UserDTO;
//import com.mockuai.virtualwealthcenter.common.api.VirtualWealthResponse;
//import com.mockuai.virtualwealthcenter.common.constant.ActionEnum;
//import com.mockuai.virtualwealthcenter.common.constant.ResponseCode;
//import com.mockuai.virtualwealthcenter.common.constant.SourceType;
//import com.mockuai.virtualwealthcenter.common.constant.WealthType;
//import com.mockuai.virtualwealthcenter.common.domain.dto.VirtualWealthDTO;
//import com.mockuai.virtualwealthcenter.common.domain.qto.WealthAccountQTO;
//import com.mockuai.virtualwealthcenter.core.domain.GrantedWealthDO;
//import com.mockuai.virtualwealthcenter.core.domain.WealthAccountDO;
//import com.mockuai.virtualwealthcenter.core.exception.VirtualWealthException;
//import com.mockuai.virtualwealthcenter.core.manager.GrantedWealthManager;
//import com.mockuai.virtualwealthcenter.core.manager.UserManager;
//import com.mockuai.virtualwealthcenter.core.manager.VirtualWealthManager;
//import com.mockuai.virtualwealthcenter.core.manager.WealthAccountManager;
//import com.mockuai.virtualwealthcenter.core.service.RequestContext;
//import com.mockuai.virtualwealthcenter.core.util.VirtualWealthUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * 批量发放虚拟财富
// * <p/>
// * Created by edgar.zr on 12/21/15.
// */
//@Service
//public class GrantVirtualWealthBatchAction extends TransAction {
//
//    @Autowired
//    private VirtualWealthManager virtualWealthManager;
//    @Autowired
//    private GrantedWealthManager grantedWealthManager;
//    @Autowired
//    private WealthAccountManager wealthAccountManager;
//    @Autowired
//    private UserManager userManager;
//
//    @Override
//    protected VirtualWealthResponse doTransaction(RequestContext context) throws VirtualWealthException {
//
//        Integer wealthType = (Integer) context.getRequest().getParam("wealthType");
//        Integer sourceType = (Integer) context.getRequest().getParam("sourceType");
//        List<String> mobiles = (List<String>) context.getRequest().getParam("mobiles");
//        List<Long> grantAmounts = (List<Long>) context.getRequest().getParam("grantAmounts");
//        AppInfoDTO appInfo = (AppInfoDTO) context.get("appInfo");
//
//        if (mobiles.isEmpty())
//            return new VirtualWealthResponse(ResponseCode.PARAMETER_NULL, "mobiles 为空");
//        if (grantAmounts.isEmpty())
//            return new VirtualWealthResponse(ResponseCode.PARAMETER_NULL, "grantAmounts 为空");
//        if (mobiles.size() != grantAmounts.size())
//            return new VirtualWealthResponse(ResponseCode.PARAMETER_ERROR, "mobiles and grantAmount 非法");
//
//        if (WealthType.getByValue(wealthType) == null) {
//            return new VirtualWealthResponse(ResponseCode.PARAMETER_NULL, "wealthType is empty or invalid");
//        }
//        if (SourceType.getByValue(sourceType) == null) {
//            return new VirtualWealthResponse(ResponseCode.PARAMETER_NULL, "sourceType is empty or invalid");
//        }
//        if (mobiles.size() > 200)
//            return new VirtualWealthResponse(ResponseCode.PARAMETER_ERROR, "单次发放记录大于 200 条");
//
//        Map<String, Long> mobileKeyAmountValue = new HashMap<String, Long>();
//        Long totalAmount = 0L;
//
//        for (int i = 0; i < mobiles.size(); i++) {
//            totalAmount += grantAmounts.get(i);
//            if (!mobileKeyAmountValue.containsKey(mobiles.get(i)))
//                mobileKeyAmountValue.put(mobiles.get(i), 0L);
//            mobileKeyAmountValue.put(mobiles.get(i), mobileKeyAmountValue.get(mobiles.get(i)) + grantAmounts.get(i));
//        }
//
//        // 每个用户总共需要发放的虚拟财富量
//        Map<Long, Long> userIdKeyAmountValue = new HashMap<Long, Long>();
//        List<Long> userIds = new ArrayList<Long>();
//
//        List<UserDTO> userDTOs = userManager.queryUserByMobiles(new ArrayList<>(mobileKeyAmountValue.keySet()), appInfo.getAppKey());
//        if (userDTOs == null || userDTOs.size() != mobileKeyAmountValue.keySet().size())
//            return new VirtualWealthResponse(ResponseCode.PARAMETER_ERROR, "发放用户非法");
//
//        Long userId;
//        for (UserDTO userDTO : userDTOs) {
//            userId = userDTO.getId();
//            userIds.add(userId);
//            userIdKeyAmountValue.put(userId, mobileKeyAmountValue.get(userDTO.getMobile()));
//        }
//
//        // 根据平台、财富类型查找总账户
//        VirtualWealthDTO virtualWealthDTO = virtualWealthManager.getVirtualWealth(appInfo.getBizCode(), 0L, wealthType);
//
//        // 虚拟财富的业务状态校验，总量必须少于（已发放数量＋待发放数量）或者财富总量为无限
//        if (virtualWealthDTO.getAmount().longValue() != -1 && virtualWealthDTO.getAmount() < virtualWealthDTO.getGrantedAmount() + totalAmount) {
//            return new VirtualWealthResponse(ResponseCode.VIRTUAL_WEALTH_NOT_ENOUGH);
//        }
//
//        WealthAccountQTO wealthAccountQTO = new WealthAccountQTO();
//        wealthAccountQTO.setWealthType(wealthType);
//
//        List<WealthAccountDO> toIncreaseWealthAccountList = new ArrayList<WealthAccountDO>();
//        List<GrantedWealthDO> toAddGrantedWealthList = new ArrayList<GrantedWealthDO>();
//
//        wealthAccountQTO.setUserIdList(userIds);
//        List<WealthAccountDO> wealthAccountDOs = wealthAccountManager.queryWealthAccount(wealthAccountQTO);
//
//        Map<Long, WealthAccountDO> userIdKeyAccountValue = new HashMap<Long, WealthAccountDO>();
//
//        for (WealthAccountDO wealthAccountDO : wealthAccountDOs) {
//            userIdKeyAccountValue.put(wealthAccountDO.getUserId(), wealthAccountDO);
//        }
//
//        WealthAccountDO wealthAccountDO;
//        for (Long receiverId : userIds) {
//            wealthAccountDO = userIdKeyAccountValue.get(receiverId);
//            // 创建用户账户
//            if (wealthAccountDO == null) {
//                wealthAccountDO = new WealthAccountDO();
//                wealthAccountDO.setBizCode(appInfo.getBizCode());
//                wealthAccountDO.setWealthType(wealthType);
//                wealthAccountDO.setUserId(receiverId);
//                try {
//                    wealthAccountDO.setId(wealthAccountManager.addWealthAccount(wealthAccountDO));
//                } catch (VirtualWealthException e) {
//                    if (e.getCode() == ResponseCode.DB_OP_ERROR_OF_DUPLICATE_ENTRY.getCode()) {
//                        wealthAccountDO = wealthAccountManager.getWealthAccount(receiverId, wealthAccountDO.getWealthType(), wealthAccountDO.getBizCode());
//                    }
//                }
//            }
//            wealthAccountDO.setAmount(userIdKeyAmountValue.get(receiverId));
//            toIncreaseWealthAccountList.add(wealthAccountDO);
//
//            //发放虚拟财富
//            GrantedWealthDO grantedWealthDO = new GrantedWealthDO();
//            grantedWealthDO.setBizCode(wealthAccountDO.getBizCode());
//            grantedWealthDO.setAmount(userIdKeyAmountValue.get(receiverId));
//            grantedWealthDO.setWealthId(virtualWealthDTO.getId());
//            grantedWealthDO.setWealthType(wealthAccountDO.getWealthType());
//            grantedWealthDO.setSourceType(sourceType);
//            grantedWealthDO.setGranterId(virtualWealthDTO.getCreatorId());
//            grantedWealthDO.setReceiverId(receiverId);
//
//            toAddGrantedWealthList.add(grantedWealthDO);
//        }
//
//        // 增加用户的虚拟财富总量
//        wealthAccountManager.increaseAccountBalanceBatch(toIncreaseWealthAccountList);
//
//        // 增加发放记录
//        grantedWealthManager.addGrantedWealths(toAddGrantedWealthList);
//
//        // 虚拟财富总账户额度更改
//        virtualWealthManager.increaseGrantedVirtualWealth(virtualWealthDTO.getId(), totalAmount);
//        // FIXME 未减掉财富总量，原有的发放中也未减掉
//        return VirtualWealthUtils.getSuccessResponse();
//    }
//
//    @Override
//    public String getName() {
//        return ActionEnum.GRANT_VIRTUAL_WEALTH_BATCH.getActionName();
//    }
//}