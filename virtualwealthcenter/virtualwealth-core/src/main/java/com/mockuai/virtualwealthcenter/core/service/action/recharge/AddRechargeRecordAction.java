//package com.mockuai.virtualwealthcenter.core.service.action.recharge;
//
//import com.mockuai.appcenter.common.domain.AppInfoDTO;
//import com.mockuai.usercenter.common.dto.UserDTO;
//import com.mockuai.virtualwealthcenter.common.api.BaseRequest;
//import com.mockuai.virtualwealthcenter.common.api.Request;
//import com.mockuai.virtualwealthcenter.common.api.Response;
//import com.mockuai.virtualwealthcenter.common.api.VirtualWealthResponse;
//import com.mockuai.virtualwealthcenter.common.api.VirtualWealthService;
//import com.mockuai.virtualwealthcenter.common.constant.ActionEnum;
//import com.mockuai.virtualwealthcenter.common.constant.ResponseCode;
//import com.mockuai.virtualwealthcenter.common.constant.SourceType;
//import com.mockuai.virtualwealthcenter.common.constant.WealthType;
//import com.mockuai.virtualwealthcenter.common.domain.dto.RechargeRecordDTO;
//import com.mockuai.virtualwealthcenter.common.domain.dto.VirtualWealthItemDTO;
//import com.mockuai.virtualwealthcenter.common.domain.qto.RechargeRecordQTO;
//import com.mockuai.virtualwealthcenter.common.domain.qto.VirtualWealthItemQTO;
//import com.mockuai.virtualwealthcenter.core.exception.VirtualWealthException;
//import com.mockuai.virtualwealthcenter.core.manager.RechargeRecordManager;
//import com.mockuai.virtualwealthcenter.core.manager.UserManager;
//import com.mockuai.virtualwealthcenter.core.manager.VirtualWealthItemManager;
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
//public class AddRechargeRecordAction implements Action {
//    private static final Logger log = LoggerFactory.getLogger(AddRechargeRecordAction.class);
//
//    @Resource
//    private RechargeRecordManager rechargeRecordManager;
//    @Resource
//    private UserManager userManager;
//    @Resource
//    private VirtualWealthItemManager virtualWealthItemManager;
//    @Resource
//    private VirtualWealthService virtualWealthService;
//
//    @Override
//    public VirtualWealthResponse execute(RequestContext context) throws VirtualWealthException {
//        Request request = context.getRequest();
//        RechargeRecordDTO rechargeRecordDTO = (RechargeRecordDTO) request.getParam("rechargeRecordDTO");
//        AppInfoDTO appInfo = (AppInfoDTO) context.get("appInfo");
//
//        if (rechargeRecordDTO == null) {
//            log.error("rechargeRecordDTO is null, bizCode: {}", appInfo.getBizCode());
//            throw new VirtualWealthException(ResponseCode.PARAMETER_NULL, "rechargeRecordDTO is null");
//        }
//
//        rechargeRecordDTO.setBizCode(appInfo.getBizCode());
//        rechargeRecordDTO.setOrderStatus(1);
//
//        // 在添加前做检查，检查该订单是否已经被创建
//        RechargeRecordQTO rechargeRecordQTO = new RechargeRecordQTO();
//        rechargeRecordQTO.setOrderId(rechargeRecordDTO.getOrderId());
//        List<RechargeRecordDTO> rechargeRecordDTOs = rechargeRecordManager.queryRecord(rechargeRecordQTO);
//        if (rechargeRecordDTOs.isEmpty()) {
//            // 获得充值的金额
//            VirtualWealthItemQTO itemQTO = new VirtualWealthItemQTO();
//            itemQTO.setBizCode(appInfo.getBizCode());
//            itemQTO.setItemId(rechargeRecordDTO.getItemId());
//            itemQTO.setOffset(0);
//            itemQTO.setCount(1);
//            List<VirtualWealthItemDTO> itemDTOs = virtualWealthItemManager.queryItems(itemQTO);
//            if (itemDTOs.isEmpty()) {
//                log.error("item id: {} not exists", itemQTO.getItemId());
//                throw new VirtualWealthException(ResponseCode.SERVICE_EXCEPTION);
//            }
//            rechargeRecordDTO.setRechargeAmount(itemDTOs.get(0).getAmount());
//            rechargeRecordDTO.setPayAmount((long) (itemDTOs.get(0).getAmount() * itemDTOs.get(0).getDiscount()));
//
//            // 获得用户名
//            UserDTO user = userManager.getUserById(rechargeRecordDTO.getUserId(), appInfo.getAppKey());
//            if (user == null) {
//                log.error("user is not exists, userId: {}", rechargeRecordDTO.getUserId());
//                throw new VirtualWealthException(ResponseCode.SERVICE_EXCEPTION);
//            }
//            rechargeRecordDTO.setUserName(user.getName());
//            Long id = rechargeRecordManager.addRecord(rechargeRecordDTO);
//            rechargeRecordDTO.setId(id);
//
//            // 给用户新增虚拟财富
//            request = new BaseRequest();
//            request.setParam("wealthCreatorId", 0L);
//            request.setParam("wealthType", WealthType.VIRTUAL_WEALTH.getValue());
//            request.setParam("sourceType", SourceType.RECHARGE.getValue());
//            request.setParam("grantAmount", rechargeRecordDTO.getRechargeAmount());
//            request.setParam("receiverId", rechargeRecordDTO.getUserId());
//            request.setParam("appKey", appInfo.getAppKey());
//            request.setCommand(ActionEnum.GRANT_VIRTUAL_WEALTH.getActionName());
//            Response<Boolean> response = virtualWealthService.execute(request);
//            if (!response.isSuccess()) {
//                log.error("grant virtual error, wealthType: VIRTUAL_WEALTH, sourceType: RECHARGE, grantAmount: {}, receiverId: {}",
//                        rechargeRecordDTO.getRechargeAmount(), rechargeRecordDTO.getUserId());
//                throw new VirtualWealthException(ResponseCode.SERVICE_EXCEPTION);
//            }
//            return new VirtualWealthResponse(rechargeRecordDTO);
//        } else {
//            rechargeRecordDTO = rechargeRecordDTOs.get(0);
//        }
//        return new VirtualWealthResponse(rechargeRecordDTO);
//    }
//
//    @Override
//    public String getName() {
//        return ActionEnum.ADD_RECHARGE_RECORD.getActionName();
//    }
//}