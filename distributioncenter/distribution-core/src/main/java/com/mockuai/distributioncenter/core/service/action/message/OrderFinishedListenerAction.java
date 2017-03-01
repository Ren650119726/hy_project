package com.mockuai.distributioncenter.core.service.action.message;

import com.aliyun.openservices.ons.api.Message;
import com.mockuai.distributioncenter.common.api.DistributionResponse;
import com.mockuai.distributioncenter.common.constant.ActionEnum;
import com.mockuai.distributioncenter.common.constant.BizType;
import com.mockuai.distributioncenter.common.constant.DistributeSource;
import com.mockuai.distributioncenter.common.constant.DistributionStatus;
import com.mockuai.distributioncenter.common.domain.dto.DistRecordDTO;
import com.mockuai.distributioncenter.common.domain.dto.DistributionOrderDTO;
import com.mockuai.distributioncenter.common.domain.dto.MessageRecordDTO;
import com.mockuai.distributioncenter.common.domain.qto.DistRecordQTO;
import com.mockuai.distributioncenter.core.api.RequestAdapter;
import com.mockuai.distributioncenter.core.exception.DistributionException;
import com.mockuai.distributioncenter.core.manager.DistRecordManager;
import com.mockuai.distributioncenter.core.manager.MessageRecordManager;
import com.mockuai.distributioncenter.core.manager.VirtualWealthManager;
import com.mockuai.distributioncenter.core.message.msg.OrderFinishedMsg;
import com.mockuai.distributioncenter.core.service.RequestContext;
import com.mockuai.distributioncenter.core.service.action.TransAction;
import com.mockuai.distributioncenter.core.util.JsonUtil;
import com.mockuai.tradecenter.common.enums.EnumPaymentMethod;
import com.mockuai.virtualwealthcenter.common.constant.GrantedWealthStatus;
import com.mockuai.virtualwealthcenter.common.constant.SourceType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by duke on 16/3/7.完成
 */
@Service
public class OrderFinishedListenerAction extends TransAction {
    private static final Logger log = LoggerFactory.getLogger(OrderFinishedListenerAction.class);

    @Autowired
    private MessageRecordManager messageRecordManager;

    @Autowired
    private DistRecordManager distRecordManager;

    @Autowired
    private VirtualWealthManager virtualWealthManager;

    
    @Override
    protected DistributionResponse doTransaction(RequestContext context) throws DistributionException {
        RequestAdapter request = new RequestAdapter(context.getRequest());

        String appKey = (String) context.get("appKey");
        OrderFinishedMsg orderFinishedMsg = (OrderFinishedMsg) request.getObject("orderFinishedMsg");

        // 检查消息是否已经被消费过了，保证消息处理幂等
        MessageRecordDTO messageRecordDTO = messageRecordManager.getRecordByIdentifyAndBizType(
                String.valueOf(orderFinishedMsg.getDistributionOrderDTO().getOrderId()) /* 使用order ID作为唯一标识，保证消息唯一性 */,
                BizType.TRADE_ORDER_FINISHED.getValue()
        );

        Message msg = orderFinishedMsg.getMsg();

        if (messageRecordDTO != null) {
            // 消息已经被消费过
            log.warn("reduplicate message msgId: {}", msg.getMsgID());
            return new DistributionResponse(true);
        }

        DistributionOrderDTO distributionOrderDTO = orderFinishedMsg.getDistributionOrderDTO();

        DistRecordQTO distRecordQTO = new DistRecordQTO();
        distRecordQTO.setOrderId(distributionOrderDTO.getOrderId());
        List<DistRecordDTO> distRecordDTOs =
                distRecordManager.query(distRecordQTO);

        if (distRecordDTOs != null && !distRecordDTOs.isEmpty()) {
        	//判断是否是嗨币支付
        	if(distributionOrderDTO.getPaymentId() != 12){
	            for (DistRecordDTO dto : distRecordDTOs) {
	                log.info("update record: {}", JsonUtil.toJson(dto));
	                // 处理订单完成的分拥记录
	                dto.setStatus(DistributionStatus.FINISHED_DISTRIBUTION.getStatus());
	                distRecordManager.update(dto);
	                
	                
	                if (dto.getSource().equals(DistributeSource.NOSHARE_DIST.getSource())) {
	                	
	                	virtualWealthManager.updateStatusOfVirtualWealthDistributorGranted(
	                            dto.getOrderId(),
	                            dto.getItemSkuId(),
	                            SourceType.NOSHARE_DIST.getValue(),
	                            GrantedWealthStatus.TRANSFERRED.getValue(),
	                            appKey);
	                    log.info("update status of virtual wealth, orderId: {}, itemSkuId: {}, sourceType: NOSHARE_DIST, Status: TRANSFERRED",
	                            dto.getOrderId(), dto.getItemSkuId());
	                	
	                }else if (dto.getSource().equals(DistributeSource.SHARE_DIST.getSource())){
	                	virtualWealthManager.updateStatusOfVirtualWealthDistributorGranted(
	                            dto.getOrderId(),
	                            dto.getItemSkuId(),
	                            SourceType.SHARE_DIST.getValue(),
	                            GrantedWealthStatus.TRANSFERRED.getValue(),
	                            appKey);
	                    log.info("update status of virtual wealth, orderId: {}, itemSkuId: {}, sourceType: SHARE_DIST, Status: TRANSFERRED",
	                            dto.getOrderId(), dto.getItemSkuId());
	                	
	                }else if (dto.getSource().equals(DistributeSource.NOPURCHASE_DIST.getSource())){
	                	
	                	virtualWealthManager.updateStatusOfVirtualWealthDistributorGranted(
	                            dto.getOrderId(),
	                            dto.getItemSkuId(),
	                            SourceType.NOPURCHASE_DIST.getValue(),
	                            GrantedWealthStatus.TRANSFERRED.getValue(),
	                            appKey);
	                    log.info("update status of virtual wealth, orderId: {}, itemSkuId: {}, sourceType: NOPURCHASE_DIST, Status: TRANSFERRED",
	                            dto.getOrderId(), dto.getItemSkuId());
	                }else if (dto.getSource().equals(DistributeSource.PURCHASE_DIST.getSource())){
	                	
	                	virtualWealthManager.updateStatusOfVirtualWealthDistributorGranted(
	                            dto.getOrderId(),
	                            dto.getItemSkuId(),
	                            SourceType.PURCHASE_DIST.getValue(),
	                            GrantedWealthStatus.TRANSFERRED.getValue(),
	                            appKey);
	                    log.info("update status of virtual wealth, orderId: {}, itemSkuId: {}, sourceType: PURCHASE_DIST, Status: TRANSFERRED",
	                            dto.getOrderId(), dto.getItemSkuId());
	                }else if (dto.getSource().equals(DistributeSource.TEAM_DIST.getSource())) {
	                    virtualWealthManager.updateStatusOfVirtualWealthDistributorGranted(
	                            dto.getOrderId(),
	                            dto.getItemSkuId(),
	                            SourceType.GROUP_SELL.getValue(),
	                            GrantedWealthStatus.TRANSFERRED.getValue(),
	                            appKey);
	                    log.info("update status of virtual wealth, orderId: {}, itemSkuId: {}, sourceType: GROUP_SELL, Status: TRANSFERRED",
	                            dto.getOrderId(), dto.getItemSkuId());
	                }else if (dto.getSource().equals(DistributeSource.SALE_DIST.getSource())) {
		                    virtualWealthManager.updateStatusOfVirtualWealthDistributorGranted(
		                            dto.getOrderId(),
		                            dto.getItemSkuId(),
		                            SourceType.SELL.getValue(),
		                            GrantedWealthStatus.TRANSFERRED.getValue(),
		                            appKey);
		                    log.info("update status of virtual wealth, orderId: {}, itemSkuId: {}, sourceType: SELL, Status: TRANSFERRED",
		                            dto.getOrderId(), dto.getItemSkuId());
	                }
	                
	            }
        	}
        } else {
            // 订单不存在
            log.warn("calculate record not exists, orderSn: {}", distributionOrderDTO.getOrderSn());
            // return new DistributionResponse(false);
        }

        // 记录已经成功消费的消息
        messageRecordDTO = new MessageRecordDTO();
        messageRecordDTO.setMsgId(msg.getMsgID());
        messageRecordDTO.setIdentify(String.valueOf(distributionOrderDTO.getOrderId()));
        messageRecordDTO.setBizType(BizType.TRADE_ORDER_FINISHED.getValue());

        messageRecordManager.addRecord(messageRecordDTO);

        return new DistributionResponse(true);
    }
    
    
//    @Override
//    protected DistributionResponse doTransaction(RequestContext context) throws DistributionException {
//        RequestAdapter request = new RequestAdapter(context.getRequest());
//
//        String appKey = (String) context.get("appKey");
//        OrderFinishedMsg orderFinishedMsg = (OrderFinishedMsg) request.getObject("orderFinishedMsg");
//
//        // 检查消息是否已经被消费过了，保证消息处理幂等
//        MessageRecordDTO messageRecordDTO = messageRecordManager.getRecordByIdentifyAndBizType(
//                String.valueOf(orderFinishedMsg.getDistributionOrderDTO().getOrderId()) /* 使用order ID作为唯一标识，保证消息唯一性 */,
//                BizType.TRADE_ORDER_FINISHED.getValue()
//        );
//
//        Message msg = orderFinishedMsg.getMsg();
//
//        if (messageRecordDTO != null) {
//            // 消息已经被消费过
//            log.warn("reduplicate message msgId: {}", msg.getMsgID());
//            return new DistributionResponse(true);
//        }
//
//        DistributionOrderDTO distributionOrderDTO = orderFinishedMsg.getDistributionOrderDTO();
//
//        DistRecordQTO distRecordQTO = new DistRecordQTO();
//        distRecordQTO.setOrderId(distributionOrderDTO.getOrderId());
//        List<DistRecordDTO> distRecordDTOs =
//                distRecordManager.query(distRecordQTO);
//
//        if (distRecordDTOs != null && !distRecordDTOs.isEmpty()) {
//        	//判断是否是嗨币支付
//        	if(distributionOrderDTO.getPaymentId() != 12){
//	            for (DistRecordDTO dto : distRecordDTOs) {
//	                log.info("update record: {}", JsonUtil.toJson(dto));
//	                // 处理订单完成的分拥记录
//	                dto.setStatus(DistributionStatus.FINISHED_DISTRIBUTION.getStatus());
//	                distRecordManager.update(dto);
//	                // 更新余额
//	                if (dto.getSource().equals(DistributeSource.OPEN_SHOP_DIST.getSource())) {
//	                    // 如果是开店分拥，已经是完成的状态了，不再次进入订单完成状态
//	                    continue;
//	                }
//	                else if (dto.getSource().equals(DistributeSource.TEAM_DIST.getSource())) {
//	                    virtualWealthManager.updateStatusOfVirtualWealthDistributorGranted(
//	                            dto.getOrderId(),
//	                            dto.getItemSkuId(),
//	                            SourceType.GROUP_SELL.getValue(),
//	                            GrantedWealthStatus.TRANSFERRED.getValue(),
//	                            appKey);
//	                    log.info("update status of virtual wealth, orderId: {}, itemSkuId: {}, sourceType: GROUP_SELL, Status: TRANSFERRED",
//	                            dto.getOrderId(), dto.getItemSkuId());
//	                }
//	                else if (dto.getSource().equals(DistributeSource.SALE_DIST.getSource())) {
//	                    virtualWealthManager.updateStatusOfVirtualWealthDistributorGranted(
//	                            dto.getOrderId(),
//	                            dto.getItemSkuId(),
//	                            SourceType.SELL.getValue(),
//	                            GrantedWealthStatus.TRANSFERRED.getValue(),
//	                            appKey);
//	                    log.info("update status of virtual wealth, orderId: {}, itemSkuId: {}, sourceType: SELL, Status: TRANSFERRED",
//	                            dto.getOrderId(), dto.getItemSkuId());
//	                }
//	            }
//        	}
//        } else {
//            // 订单不存在
//            log.warn("calculate record not exists, orderSn: {}", distributionOrderDTO.getOrderSn());
//            // return new DistributionResponse(false);
//        }
//
//        // 记录已经成功消费的消息
//        messageRecordDTO = new MessageRecordDTO();
//        messageRecordDTO.setMsgId(msg.getMsgID());
//        messageRecordDTO.setIdentify(String.valueOf(distributionOrderDTO.getOrderId()));
//        messageRecordDTO.setBizType(BizType.TRADE_ORDER_FINISHED.getValue());
//
//        messageRecordManager.addRecord(messageRecordDTO);
//
//        return new DistributionResponse(true);
//    }

    @Override
    public String getName() {
        return ActionEnum.ORDER_FINISHED_LISTENER.getActionName();
    }
}
