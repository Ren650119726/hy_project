package com.mockuai.distributioncenter.core.service.action.message;

import com.aliyun.openservices.ons.api.Message;
import com.mockuai.distributioncenter.common.api.DistributionResponse;
import com.mockuai.distributioncenter.common.constant.ActionEnum;
import com.mockuai.distributioncenter.common.constant.BizType;
import com.mockuai.distributioncenter.common.constant.DistributeSource;
import com.mockuai.distributioncenter.common.constant.DistributionStatus;
import com.mockuai.distributioncenter.common.domain.dto.DistRecordDTO;
import com.mockuai.distributioncenter.common.domain.dto.MessageRecordDTO;
import com.mockuai.distributioncenter.common.domain.qto.DistRecordQTO;
import com.mockuai.distributioncenter.core.api.RequestAdapter;
import com.mockuai.distributioncenter.core.exception.DistributionException;
import com.mockuai.distributioncenter.core.manager.DistRecordManager;
import com.mockuai.distributioncenter.core.manager.MessageRecordManager;
import com.mockuai.distributioncenter.core.manager.VirtualWealthManager;
import com.mockuai.distributioncenter.core.message.msg.RefundSuccessMsg;
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
 * Created by duke on 16/3/7.退款
 */
@Service
public class RefundSuccessListenerAction extends TransAction {
    private static final Logger log = LoggerFactory.getLogger(RefundSuccessListenerAction.class);

    @Autowired
    private MessageRecordManager messageRecordManager;

    @Autowired
    private DistRecordManager distRecordManager;

    @Autowired
    private VirtualWealthManager virtualWealthManager;

    @Override
    protected DistributionResponse doTransaction(RequestContext context) throws DistributionException {
        RequestAdapter request = new RequestAdapter(context.getRequest());
        RefundSuccessMsg refundSuccessMsg = (RefundSuccessMsg) request.getObject("refundSuccessMsg");
        String appKey = (String) context.get("appKey");

        // 检查消息是否被消费过，保证消息处理幂等
        MessageRecordDTO messageRecordDTO = messageRecordManager.getRecordByIdentifyAndBizType(
                String.valueOf(refundSuccessMsg.getRefundOrderId()), BizType.TRADE_REFUND.getValue()
        );

        Message msg = refundSuccessMsg.getMsg();
        if (messageRecordDTO != null) {
            // 消息已经被消费过
            log.warn("reduplicate message msgId: {}", msg.getMsgID());
            return new DistributionResponse(true);
        }


        DistRecordQTO distRecordQTO = new DistRecordQTO();
        distRecordQTO.setItemSkuId(refundSuccessMsg.getItemSkuId());
        distRecordQTO.setOrderId(refundSuccessMsg.getOrderId());
        List<DistRecordDTO> distRecordDTOs = distRecordManager.query(distRecordQTO);

        if (distRecordDTOs != null && !distRecordDTOs.isEmpty()) {
        	log.info("-------------:"+refundSuccessMsg.getPaymentId()+"ccccc");
        	//判断是否是嗨币支付
        	if(refundSuccessMsg.getPaymentId() != 12){
        		log.info("-------------:"+refundSuccessMsg.getPaymentId()+"vvvvv");
	            for (DistRecordDTO dto : distRecordDTOs) {
	                log.info("update record: {}", JsonUtil.toJson(dto));
	                // 只有支付成功的记录和已经发货的记录才可以更新为退款的状态
	                if (dto.getStatus().equals(DistributionStatus.FROZEN_DISTRIBUTION.getStatus()) ||
	                        dto.getStatus().equals(DistributionStatus.FINISHING_DISTRIBUTION.getStatus())) {
	                	
	                	// 处理退款的分拥记录
                        dto.setStatus(DistributionStatus.CANCEL_SUCCESS_DISTRIBUTION.getStatus());
                        distRecordManager.update(dto);
	                	
                        if (dto.getSource().equals(DistributeSource.NOSHARE_DIST.getSource())) {
    	                	
    	                	virtualWealthManager.updateStatusOfVirtualWealthDistributorGranted(
    	                            dto.getOrderId(),
    	                            dto.getItemSkuId(),
    	                            SourceType.NOSHARE_DIST.getValue(),
    	                            GrantedWealthStatus.CANCEL.getValue(),
    	                            appKey);
    	                    log.info("update status of virtual wealth, orderId: {}, itemSkuId: {}, sourceType: NOSHARE_DIST, Status: CANCEL",
    	                            dto.getOrderId(), dto.getItemSkuId());
    	                	
    	                }else if (dto.getSource().equals(DistributeSource.SHARE_DIST.getSource())){
    	                	virtualWealthManager.updateStatusOfVirtualWealthDistributorGranted(
    	                            dto.getOrderId(),
    	                            dto.getItemSkuId(),
    	                            SourceType.SHARE_DIST.getValue(),
    	                            GrantedWealthStatus.CANCEL.getValue(),
    	                            appKey);
    	                    log.info("update status of virtual wealth, orderId: {}, itemSkuId: {}, sourceType: SHARE_DIST, Status: CANCEL",
    	                            dto.getOrderId(), dto.getItemSkuId());
    	                	
    	                }else if (dto.getSource().equals(DistributeSource.NOPURCHASE_DIST.getSource())){
    	                	
    	                	virtualWealthManager.updateStatusOfVirtualWealthDistributorGranted(
    	                            dto.getOrderId(),
    	                            dto.getItemSkuId(),
    	                            SourceType.NOPURCHASE_DIST.getValue(),
    	                            GrantedWealthStatus.CANCEL.getValue(),
    	                            appKey);
    	                    log.info("update status of virtual wealth, orderId: {}, itemSkuId: {}, sourceType: NOPURCHASE_DIST, Status: CANCEL",
    	                            dto.getOrderId(), dto.getItemSkuId());
    	                }else if (dto.getSource().equals(DistributeSource.PURCHASE_DIST.getSource())){
    	                	
    	                	virtualWealthManager.updateStatusOfVirtualWealthDistributorGranted(
    	                            dto.getOrderId(),
    	                            dto.getItemSkuId(),
    	                            SourceType.PURCHASE_DIST.getValue(),
    	                            GrantedWealthStatus.CANCEL.getValue(),
    	                            appKey);
    	                    log.info("update status of virtual wealth, orderId: {}, itemSkuId: {}, sourceType: PURCHASE_DIST, Status: CANCEL",
    	                            dto.getOrderId(), dto.getItemSkuId());
    	                }else if (dto.getSource().equals(DistributeSource.TEAM_DIST.getSource())) {

                        virtualWealthManager.updateStatusOfVirtualWealthDistributorGranted(
                                dto.getOrderId(),
                                dto.getItemSkuId(),
                                SourceType.GROUP_SELL.getValue(),
                                GrantedWealthStatus.CANCEL.getValue(),
                                appKey);
                        log.info("update status of virtual wealth, orderId: {}, itemSkuId: {}, sourceType: GROUP_SELL, Status: CANCEL",
                                dto.getOrderId(), dto.getItemSkuId());
	                    } else if (dto.getSource().equals(DistributeSource.SALE_DIST.getSource())) {
	
	                        virtualWealthManager.updateStatusOfVirtualWealthDistributorGranted(
	                                dto.getOrderId(),
	                                dto.getItemSkuId(),
	                                SourceType.SELL.getValue(),
	                                GrantedWealthStatus.CANCEL.getValue(),
	                                appKey);
	                        log.info("update status of virtual wealth, orderId: {}, itemSkuId: {}, sourceType: SELL, Status: CANCEL",
	                                dto.getOrderId(), dto.getItemSkuId());
	                    }
	                	
	                }
	            }
        	}
        } else {
            // 订单不存在
            log.warn("calculate record not exists, orderId: {}", refundSuccessMsg.getOrderId());
            // return new DistributionResponse(false);
        }

        // 记录消费过的消息记录
        messageRecordDTO = new MessageRecordDTO();
        messageRecordDTO.setMsgId(msg.getMsgID());
        messageRecordDTO.setIdentify(String.valueOf(refundSuccessMsg.getRefundOrderId()));
        messageRecordDTO.setBizType(BizType.TRADE_REFUND.getValue());
        messageRecordManager.addRecord(messageRecordDTO);

        return new DistributionResponse(true);
    }
    
    
    
//    @Override
//    protected DistributionResponse doTransaction(RequestContext context) throws DistributionException {
//        RequestAdapter request = new RequestAdapter(context.getRequest());
//        RefundSuccessMsg refundSuccessMsg = (RefundSuccessMsg) request.getObject("refundSuccessMsg");
//        String appKey = (String) context.get("appKey");
//
//        // 检查消息是否被消费过，保证消息处理幂等
//        MessageRecordDTO messageRecordDTO = messageRecordManager.getRecordByIdentifyAndBizType(
//                String.valueOf(refundSuccessMsg.getRefundOrderId()), BizType.TRADE_REFUND.getValue()
//        );
//
//        Message msg = refundSuccessMsg.getMsg();
//        if (messageRecordDTO != null) {
//            // 消息已经被消费过
//            log.warn("reduplicate message msgId: {}", msg.getMsgID());
//            return new DistributionResponse(true);
//        }
//
//
//        DistRecordQTO distRecordQTO = new DistRecordQTO();
//        distRecordQTO.setItemSkuId(refundSuccessMsg.getItemSkuId());
//        distRecordQTO.setOrderId(refundSuccessMsg.getOrderId());
//        List<DistRecordDTO> distRecordDTOs = distRecordManager.query(distRecordQTO);
//
//        if (distRecordDTOs != null && !distRecordDTOs.isEmpty()) {
//        	log.info("-------------:"+refundSuccessMsg.getPaymentId()+"ccccc");
//        	//判断是否是嗨币支付
//        	if(refundSuccessMsg.getPaymentId() != 12){
//        		log.info("-------------:"+refundSuccessMsg.getPaymentId()+"vvvvv");
//	            for (DistRecordDTO dto : distRecordDTOs) {
//	                log.info("update record: {}", JsonUtil.toJson(dto));
//	                // 只有支付成功的记录和已经发货的记录才可以更新为退款的状态
//	                if (dto.getStatus().equals(DistributionStatus.FROZEN_DISTRIBUTION.getStatus()) ||
//	                        dto.getStatus().equals(DistributionStatus.FINISHING_DISTRIBUTION.getStatus())) {
//	                    // 更新余额
//	                    if (dto.getSource().equals(DistributeSource.OPEN_SHOP_DIST.getSource())) {
//	                        // 如果是开店分拥，则不处理退款状态
//	                        continue;
//	                    } else if (dto.getSource().equals(DistributeSource.TEAM_DIST.getSource())) {
//	                        // 处理退款的分拥记录
//	                        dto.setStatus(DistributionStatus.CANCEL_SUCCESS_DISTRIBUTION.getStatus());
//	                        distRecordManager.update(dto);
//	
//	                        virtualWealthManager.updateStatusOfVirtualWealthDistributorGranted(
//	                                dto.getOrderId(),
//	                                dto.getItemSkuId(),
//	                                SourceType.GROUP_SELL.getValue(),
//	                                GrantedWealthStatus.CANCEL.getValue(),
//	                                appKey);
//	                        log.info("update status of virtual wealth, orderId: {}, itemSkuId: {}, sourceType: GROUP_SELL, Status: CANCEL",
//	                                dto.getOrderId(), dto.getItemSkuId());
//	                    } else if (dto.getSource().equals(DistributeSource.SALE_DIST.getSource())) {
//	                        // 处理退款的分拥记录
//	                        dto.setStatus(DistributionStatus.CANCEL_SUCCESS_DISTRIBUTION.getStatus());
//	                        distRecordManager.update(dto);
//	
//	                        virtualWealthManager.updateStatusOfVirtualWealthDistributorGranted(
//	                                dto.getOrderId(),
//	                                dto.getItemSkuId(),
//	                                SourceType.SELL.getValue(),
//	                                GrantedWealthStatus.CANCEL.getValue(),
//	                                appKey);
//	                        log.info("update status of virtual wealth, orderId: {}, itemSkuId: {}, sourceType: SELL, Status: CANCEL",
//	                                dto.getOrderId(), dto.getItemSkuId());
//	                    }
//	                }
//	            }
//        	}
//        } else {
//            // 订单不存在
//            log.warn("calculate record not exists, orderId: {}", refundSuccessMsg.getOrderId());
//            // return new DistributionResponse(false);
//        }
//
//        // 记录消费过的消息记录
//        messageRecordDTO = new MessageRecordDTO();
//        messageRecordDTO.setMsgId(msg.getMsgID());
//        messageRecordDTO.setIdentify(String.valueOf(refundSuccessMsg.getRefundOrderId()));
//        messageRecordDTO.setBizType(BizType.TRADE_REFUND.getValue());
//        messageRecordManager.addRecord(messageRecordDTO);
//
//        return new DistributionResponse(true);
//    }

    @Override
    public String getName() {
        return ActionEnum.ORDER_REFUND_SUCCESS_LISTENER.getActionName();
    }
}
