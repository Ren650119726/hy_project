package com.mockuai.tradecenter.core.manager.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.mockuai.tradecenter.core.manager.*;
import com.mockuai.tradecenter.core.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.mockuai.appcenter.common.domain.AppInfoDTO;
import com.mockuai.itemcenter.common.domain.dto.CommissionUnitDTO;
import com.mockuai.tradecenter.common.domain.OrderItemQTO;
import com.mockuai.tradecenter.common.domain.refund.RefundImageDTO;
import com.mockuai.tradecenter.common.domain.refund.RefundItemLogDTO;
import com.mockuai.tradecenter.common.domain.refund.RefundOrderDTO;
import com.mockuai.tradecenter.common.domain.refund.RefundOrderItemDTO;
import com.mockuai.tradecenter.common.enums.EnumOrderStatus;
import com.mockuai.tradecenter.common.enums.EnumRefundReason;
import com.mockuai.tradecenter.common.enums.EnumRefundStatus;
import com.mockuai.tradecenter.core.base.ClientExecutorFactory;
import com.mockuai.tradecenter.core.dao.OrderItemDAO;
import com.mockuai.tradecenter.core.dao.RefundDAO;
import com.mockuai.tradecenter.core.dao.RefundItemImageDAO;
import com.mockuai.tradecenter.core.domain.OrderDO;
import com.mockuai.tradecenter.core.domain.OrderItemDO;
import com.mockuai.tradecenter.core.domain.RefundItemImageDO;
import com.mockuai.tradecenter.core.domain.RefundItemLogDO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.util.DozerBeanService;
import com.mockuai.tradecenter.core.util.TradeUtil.DeliveryMark;

public class RefundOrderItemManagerImpl extends BaseService implements RefundOrderItemManager {
	private static final Logger log = LoggerFactory.getLogger(RefundOrderItemManagerImpl.class);
	 public static final Integer REFUNDING_MARK = 1;
	 public static final Integer NOREFUND_MARK=0;
	@Autowired
	private RefundDAO refundDAO;

	@Resource
	private TransactionTemplate transactionTemplate;
	
	
	@Resource
	private SellerTransLogManager sellerTransLogManager;
	
	@Resource
	private OrderItemDAO orderItemDAO;
	
	@Resource
	private RefundItemImageDAO refundItemImageDAO;
	
	@Resource
    private DozerBeanService  dozerBeanService;
	
	@Resource
	private AppManager appManager;
	
	@Resource
	private ClientExecutorFactory clientExecutorFactory;
	
	@Resource
	private MsgQueueManager msgQueueManager;
	
	@Resource
	private MarketingManager marketingManager;

	@Resource
	private VirtualWealthManager virtualWealthManager;
	
	@Autowired
	ShopManager shopManager;
	
	@Autowired
	OrderManager orderManager;

	@Override
	public String validator4RefundAudit(RefundOrderItemDTO refundOrderItemDTO) throws TradeException {
		// TODO Auto-generated method stub
		if(refundOrderItemDTO.getOrderId()==null){
			return "orderId is null";
		}
//		else if(refundOrderItemDTO.getItemSkuId() == null ){
//			return "itemSkuId is null";
//		}
		else if(refundOrderItemDTO.getOrderItemId() == null){
			return "orderItemId is null";
		}
		else if(refundOrderItemDTO.getAuditResult() == null ){
			return "auditResult is null";
		}else if(refundOrderItemDTO.getUserId() == null ){
			return "userId is null";
		}
		
		if(refundOrderItemDTO.getAuditResult()==0&&refundOrderItemDTO.getRefuseReason()==null ){
			log.error("refuse reason:"+refundOrderItemDTO.getRefuseReason());
			return "refuseReason is null";
		}
		OrderItemQTO orderItemQTO = new OrderItemQTO();
		orderItemQTO.setOrderId(refundOrderItemDTO.getOrderId());
		orderItemQTO.setOrderItemId(refundOrderItemDTO.getOrderItemId());
		OrderItemDO orderItemDO = orderItemDAO.getOrderItem(orderItemQTO);
		if( null== orderItemDO ){
			 return "orderItem is null";
		}
		
		return null;
	}
	
	@Override
	public String validator4RefundConfirm(RefundOrderItemDTO refundOrderItemDTO) throws TradeException {
		// TODO Auto-generated method stub
		if(refundOrderItemDTO.getOrderId()==null){
			return "orderId is null";
//		}else if(refundOrderItemDTO.getItemSkuId() == null ){
//			return "itemSkuId is null";
		}else if(refundOrderItemDTO.getUserId() == null ){
			return "userId is null";
		}else if(refundOrderItemDTO.getOrderItemId() == null ){
			return "orderItemId is null";
		}
		OrderItemQTO orderItemQTO = new OrderItemQTO();
		orderItemQTO.setOrderId(refundOrderItemDTO.getOrderId());
		orderItemQTO.setOrderItemId(refundOrderItemDTO.getOrderItemId());
		orderItemQTO.setUserId(refundOrderItemDTO.getUserId());
		OrderItemDO orderItemDO = orderItemDAO.getOrderItem(orderItemQTO);
		if( null== orderItemDO ){
			 return "orderItem is null";
		}
		if( orderItemDO.getRefundStatus() == Integer.parseInt(EnumRefundStatus.REFUNDING.getCode()) ){
			return "sku is return processing";
		}
		BeanUtils.copyProperties(orderItemDO, refundOrderItemDTO);
		
		
		refundOrderItemDTO.setRefundAmount(orderItemDO.getRefundAmount());
		return null;
	}
	
	private void addRefundItemImage(List<RefundImageDTO> refundImageDTOs,String bizCode,Long refundItemId,Long itemId,Long orderId,Long itemSkuId,Long sellerId){
		if(null!=refundImageDTOs&&refundImageDTOs.size()>0){
			for(RefundImageDTO refundImageDTO:refundImageDTOs){
				RefundItemImageDO refundItemImageDO = null;
				refundItemImageDO = dozerBeanService.cover(refundImageDTO, RefundItemImageDO.class);
				refundItemImageDO.setBizCode(bizCode);
				refundItemImageDO.setItemId(itemId);
				refundItemImageDO.setRefundItemLogId(refundItemId);
				refundItemImageDO.setItemSkuId(itemSkuId);
				refundItemImageDO.setSellerId(sellerId);
				refundItemImageDO.setOrderId(orderId);
				refundItemImageDAO.addRefundItemImage(refundItemImageDO);
			}
		}
	}
	
	
	private Long addRefundItemLog(RefundOrderItemDTO refundOrderItemDTO,boolean sellerOperator){
		RefundItemLogDO refundOrderItemDO = dozerBeanService.cover(refundOrderItemDTO, RefundItemLogDO.class);
		if(sellerOperator){
			refundOrderItemDO.setOperatorFrom(1);
		}else{
			refundOrderItemDO.setOperatorFrom(0);
		}
		return refundDAO.addRefundItemLog(refundOrderItemDO);
	}
	

	@Override
	public Boolean applyOrderItemRefund(final RefundOrderDTO refundOrderDTO,final Integer refundType) throws TradeException {
		printIntoService(log, "applyOrderItemRefund", refundOrderDTO, "");
		Boolean result = transactionTemplate.execute(new TransactionCallback<Boolean>() {
			@Override
			public Boolean doInTransaction(TransactionStatus status) {
				try {
					for (RefundOrderItemDTO refundOrderItem : refundOrderDTO.getReturnItems()) {
						refundOrderItem.setOrderId(refundOrderDTO.getOrderId());
						refundOrderItem.setRefundStatus(Integer.parseInt(EnumRefundStatus.APPLY.getCode()));
						refundOrderItem.setRefundType(refundType);
						refundOrderItem.setRefundTime(new Date());
						refundDAO.updateOrderItemRefundInfo(refundOrderItem);
						refundDAO.updateSuitSubItemRefundInfo(refundOrderItem);
						
						
						Long refundItemId = addRefundItemLog(refundOrderItem,false);
						
						
						addRefundItemImage(refundOrderItem.getRefundImageList(),refundOrderItem.getBizCode(),
								refundItemId,
								refundOrderItem.getOrderId(),
								refundOrderItem.getItemId(),
								refundOrderItem.getItemSkuId(),
								refundOrderItem.getSellerId());
						
						//增加申请退款发送mq消息
						try{
							msgQueueManager.sendApplyRefundMsg(refundOrderItem);
						}catch(Exception e){
							log.error("applyOrderItemRefund  send mq message error",e);
						}catch(Throwable e){
							log.error("applyOrderItemRefund send mq message error",e);
						}
						
						
					}
					refundDAO.updateOrderRefundMark(refundOrderDTO.getOrderId(),REFUNDING_MARK);
					return true;

				} catch (Exception e) {
					log.error("applyOrderItemRefund error", e);
					status.setRollbackOnly();
					return false;
				}

				
			}
		});
		return result;

	}
	
	private void updateOrderStatus(RefundOrderItemDTO refundOrderItemDTO){
		OrderItemQTO orderItemQTO = new OrderItemQTO();
		orderItemQTO.setOrderId(refundOrderItemDTO.getOrderId());
		Integer refundProcessingCount = orderItemDAO.getProcessingRefundOrderItemCount(orderItemQTO);
		if(refundProcessingCount==0){
			refundDAO.updateOrderRefundMark(refundOrderItemDTO.getOrderId(), NOREFUND_MARK);
		}
//		Integer unDeliveryItemCount = orderItemDAO.getUnDeliveryOrderItemCount(orderItemQTO);
//		if(unDeliveryItemCount>0){
//			refundDAO.updateOrderRefundStatus(refundOrderItemDTO.getOrderId(), Integer.parseInt(EnumOrderStatus.PAID.getCode()));
//		}else{
//			refundDAO.updateOrderRefundStatus(refundOrderItemDTO.getOrderId(), Integer.parseInt(EnumOrderStatus.DELIVERIED.getCode()));
//		}
	}
	

	@Override
	public Boolean auditOrderItemRefund(final RefundOrderItemDTO refundOrderItemDTO) throws TradeException {
		Boolean result = false;
		printIntoService(log, "auditOrderItemRefund", refundOrderItemDTO, "");
		result = transactionTemplate.execute(new TransactionCallback<Boolean>() {
			@Override
			public Boolean doInTransaction(TransactionStatus status) {
				try{
//					OrderItemDO orderItem = refundDAO.getOrderItem(refundOrderItemDTO.getUserId(), refundOrderItemDTO.getOrderId(), 
//							refundOrderItemDTO.getItemSkuId());
					
					OrderItemQTO orderItemQuery = new OrderItemQTO();
					orderItemQuery.setOrderId(refundOrderItemDTO.getOrderId());
					orderItemQuery.setOrderItemId(refundOrderItemDTO.getOrderItemId());
					OrderItemDO orderItem = orderItemDAO.getOrderItem(orderItemQuery);
					
					BeanUtils.copyProperties(orderItem, refundOrderItemDTO);
					if(refundOrderItemDTO.getAuditResult()==0){
						refundOrderItemDTO.setRefundStatus(Integer.parseInt(EnumRefundStatus.REFUSE.getCode()));
						
						
					}else if(refundOrderItemDTO.getAuditResult()==1){
						refundOrderItemDTO.setRefundStatus(Integer.parseInt(EnumRefundStatus.RETURNING.getCode()));
					}
					
					
					refundDAO.updateOrderItemRefundInfo(refundOrderItemDTO);
					updateOrderStatus(refundOrderItemDTO);
					addRefundItemLog(refundOrderItemDTO,true);
					return true;
					
				}catch (Exception e) {
					log.error("auditOrderItemRefund error", e);
					status.setRollbackOnly();
					return false;
				}
			}
		});
		return result;
	}
	
	
	private void updateOrderRefundStatus(Integer processingRefundCount,Integer noRefundOrderItemCount ,OrderItemDO orderItemDO){
		
		
		if(processingRefundCount==0&&noRefundOrderItemCount>0){
//			refundDAO.updateOrderRefundMark(refundOrderItemDTO.getOrderId(), 
//					Integer.parseInt(EnumOrderStatus.PARTIAL_REFUND_FINISHED.getCode()),NOREFUND_MARK);
			refundDAO.updateOrderRefundMark(orderItemDO.getOrderId(), NOREFUND_MARK);
//			refundDAO.updateOrderRefundStatus(refundOrderItemDTO.getOrderId(), Integer.parseInt(EnumOrderStatus.PARTIAL_REFUND_FINISHED.getCode()));
		}
		
		if(processingRefundCount==0&&noRefundOrderItemCount==0){
//			refundDAO.updateOrderRefundStatus(refundOrderItemDTO.getOrderId(), 
//					Integer.parseInt(EnumOrderStatus.REFUND_FINISHED.getCode()),NOREFUND_MARK);
			refundDAO.updateOrderRefundMark(orderItemDO.getOrderId(), NOREFUND_MARK);
			refundDAO.updateOrderRefundStatus(orderItemDO.getOrderId(),
					Integer.parseInt(EnumOrderStatus.REFUND_FINISHED.getCode()));
		}
		
		if(processingRefundCount>0&&noRefundOrderItemCount==0){
//			refundDAO.updateOrderRefundStatus(refundOrderItemDTO.getOrderId(), 
//					Integer.parseInt(EnumOrderStatus.REFUND_APPLY.getCode()),REFUNDING_MARK);
			refundDAO.updateOrderRefundMark(orderItemDO.getOrderId(), REFUNDING_MARK);
		}
		
		if(processingRefundCount>0&&noRefundOrderItemCount>0){
//			refundDAO.updateOrderRefundStatus(refundOrderItemDTO.getOrderId(), 
//					Integer.parseInt(EnumOrderStatus.PARTIAL_REFUND.getCode()),REFUNDING_MARK);
			refundDAO.updateOrderRefundMark(orderItemDO.getOrderId(), REFUNDING_MARK);
		}
	}

	@Override
	public Boolean notifyRefundSuccess(final RefundOrderItemDTO refundOrderItemDTO,final OrderItemDO orderItem) throws TradeException {
		printIntoService(log, "notifyRefundSuccess", refundOrderItemDTO, "");
		Boolean result = transactionTemplate.execute(new TransactionCallback<Boolean>() {
			@Override
			public Boolean doInTransaction(TransactionStatus status) {
				
				try {
					//更新退款商品的退款状态
					refundOrderItemDTO.setRefundStatus(Integer.parseInt(EnumRefundStatus.REFUND_FINISHED.getCode()));
					refundOrderItemDTO.setDeliveryMark(DeliveryMark.SHIPPED);
					refundDAO.updateOrderItemRefundInfo(refundOrderItemDTO);


					//查询正在处理退款的订单商品数
					OrderItemQTO query = new OrderItemQTO();
					query.setOrderId(orderItem.getOrderId());
					query.setUserId(orderItem.getUserId());
					Integer processingRefundCount = orderItemDAO.getProcessingRefundOrderItemCount(query);

					//查询订单商品中无需退款的商品数
					Integer noRefundOrderItemCount = 0;
					List<OrderItemDO> noRefundOrderItemList = orderItemDAO.queryOrderItemNoRefund(query);
					if (null != noRefundOrderItemList && noRefundOrderItemList.isEmpty()==false) {
						noRefundOrderItemCount = noRefundOrderItemList.size();
					}

					log.info("[REFUND_TRACE] processingRefundCount:{}, noRefundOrderItemCount:{}, " +
									"noRefundOrderItemList:{}, orderItem:{}, query:{}",
							processingRefundCount, noRefundOrderItemCount, JsonUtil.toJson(noRefundOrderItemList),
							JsonUtil.toJson(orderItem), JsonUtil.toJson(query));

					//更新订单的退款状态
					updateOrderRefundStatus(processingRefundCount, noRefundOrderItemCount, orderItem);

					try {
						msgQueueManager.sendRefundSuccessMsg(orderItem);
					} catch (Exception e) {
						log.error("mq send message error", e);
					}
				} catch (Exception e) {
					log.error("confirmOrderItemRefund error", e);
					status.setRollbackOnly();
					return false;
				}

				return true;
			}
		});
		return result;
		
	}

	@Override
	public Boolean notifyRefundFailed(final RefundOrderItemDTO refundOrderItemDTO) throws TradeException {
		// TODO Auto-generated method stub
		printIntoService(log, "notifyRefundFailed", refundOrderItemDTO, "");
		Boolean result = transactionTemplate.execute(new TransactionCallback<Boolean>() {
			@Override
			public Boolean doInTransaction(TransactionStatus status) {
				try {
					refundOrderItemDTO.setRefundStatus(Integer.parseInt(EnumRefundStatus.REFUND_FAILED.getCode()));
					refundDAO.updateOrderItemRefundInfo(refundOrderItemDTO);
					OrderItemQTO query = new OrderItemQTO();
					query.setOrderId(refundOrderItemDTO.getOrderId());
					query.setUserId(refundOrderItemDTO.getUserId());
				
					
//					boolean isPayByMockuai = appManager.getPayByMockuai(refundOrderItemDTO.getBizCode());
//					if(isPayByMockuai){
//						sellerTransLogManager.processRefundNotify(refundOrderItemDTO);
//					}
					
				} catch (Exception e) {
					log.error("confirmOrderItemRefund error", e);
					status.setRollbackOnly();
					return false;
				}

				return true;
			}
		});
		return result;
		
	}

	@Override
	public List<RefundItemLogDTO> queryRefundItemLogList(Long orderId, Long skuId) throws TradeException {
		// TODO Auto-generated method stub
		printIntoService(log, "queryRefundItemLogList", orderId+skuId, "");
		RefundItemLogDO query = new RefundItemLogDO();
		query.setOrderId(orderId);
		query.setItemSkuId(skuId);
		List<RefundItemLogDO> refundItemDOList = refundDAO.queryRefundItemLogDOList(query);
		List<RefundItemLogDTO> returnList = new ArrayList<RefundItemLogDTO>();
		for(RefundItemLogDO refundItemDO:refundItemDOList){
			List<RefundItemImageDO> imageList = refundItemImageDAO.queryRefundItemImage(refundItemDO.getId());
			RefundItemLogDTO refundItemLogDTO = dozerBeanService.cover(refundItemDO, RefundItemLogDTO.class);
			if(null!=refundItemLogDTO.getRefundReasonId()){
				refundItemLogDTO.setRefundReason(EnumRefundReason.toMap().get(refundItemLogDTO.getRefundReasonId()));
			}
			
			if(null!=imageList){
				List<RefundImageDTO> refundItemImageDTOs = dozerBeanService.coverList(imageList, RefundImageDTO.class);
				refundItemLogDTO.setRefundImageList(refundItemImageDTOs);
			}
			returnList.add(refundItemLogDTO);
		}
		return returnList;
	}

	@Override
	public Boolean batchReviseRefund(final List<RefundOrderItemDTO> refundOrderItemDTOs) {
		// TODO Auto-generated method stub
		if(null==refundOrderItemDTOs||refundOrderItemDTOs.isEmpty()){
			return true;
		}
		printIntoService(log, "batchReviseRefund", refundOrderItemDTOs, "");
		
		//for validator
		for(RefundOrderItemDTO refundOrderItemDTO:refundOrderItemDTOs){
			if(null==refundOrderItemDTO.getOrderId()){
				log.error("orderId is null");
				return false;
			}
			if(null==refundOrderItemDTO.getOrderItemId()){
				log.error("orderItemId is null");
				return false;			}
			if(null==refundOrderItemDTO.getRefundStatus()){
				log.error("refundStatuss is null");
				return false;
			}
		}
		
		final List<OrderItemDO> finishedOrderItems = new ArrayList<OrderItemDO>();
		Boolean result = transactionTemplate.execute(new TransactionCallback<Boolean>() {

			@Override
			public Boolean doInTransaction(TransactionStatus status) {
				try{
					for(RefundOrderItemDTO refundOrderItemDTO:refundOrderItemDTOs){
						OrderItemDO orderItemDO = orderItemDAO.getOrderItemById(refundOrderItemDTO.getOrderItemId());
						if(null==orderItemDO){
							log.error(refundOrderItemDTO.getOrderItemId()+" orderItem is not exist");
							return false;
						}
						
						if(orderItemDO.getRefundStatus()==refundOrderItemDTO.getRefundStatus()){
							return true;
						}
						
						if(refundOrderItemDTO.getRefundStatus()==4){
							
							refundOrderItemDTO.setRefundStatus(Integer.parseInt(EnumRefundStatus.REFUND_FINISHED.getCode()));
							refundOrderItemDTO.setDeliveryMark(DeliveryMark.SHIPPED);
							refundDAO.updateOrderItemRefundInfo(refundOrderItemDTO);
							OrderItemQTO query = new OrderItemQTO();
							query.setOrderId(orderItemDO.getOrderId());
							query.setUserId(orderItemDO.getUserId());
							Integer processingRefundCount =  orderItemDAO.getProcessingRefundOrderItemCount(query);
							Integer noRefundOrderItemCount = 0;
							List<OrderItemDO> noRefundOrderItem = orderItemDAO.queryOrderItemNoRefund(query);
							
							if(null!=noRefundOrderItem&&noRefundOrderItem.size()!=0){
								noRefundOrderItemCount = noRefundOrderItem.size();
							}
							
							updateOrderRefundStatus(processingRefundCount,noRefundOrderItemCount,orderItemDO);
							
							AppInfoDTO appInfoDTO = appManager.getAppInfoByBizCode(orderItemDO.getBizCode(),3);
							
							OrderDO orderDO = orderManager.getOrder(orderItemDO.getOrderId(),orderItemDO.getUserId());
							List<CommissionUnitDTO> commissionUnitDTOList = new ArrayList<CommissionUnitDTO>();
							CommissionUnitDTO commisionUnitDTO = new CommissionUnitDTO();
							commisionUnitDTO.setItemId(orderItemDO.getItemId());
							commisionUnitDTO.setSkuId(orderItemDO.getItemSkuId());
							commisionUnitDTO.setPrice(orderItemDO.getUnitPrice());
							commisionUnitDTO.setNum(orderItemDO.getNumber());
							commisionUnitDTO.setSellerId(orderDO.getSellerId());
							commissionUnitDTOList.add(commisionUnitDTO);
							
							Long orderItemCommission = 0L;
							try{
								orderItemCommission  = shopManager.getOrderCommission(commissionUnitDTOList, appInfoDTO.getAppKey());
							}catch(Exception e){
								log.error("get commission error",e);
							}
							
							if(orderItemCommission>0&&orderDO.getMallCommission()>0){
								// 修改佣金
								long mallCommission = orderDO.getMallCommission()-orderItemCommission;
								if(mallCommission<0){
									mallCommission = 0L;
								}
								orderDO.setMallCommission(mallCommission);
								orderManager.updateOrderMallCommission(orderDO);
							}
							
							finishedOrderItems.add(orderItemDO);
							
						}
						
						if(refundOrderItemDTO.getRefundStatus()==5){
							refundOrderItemDTO.setRefundStatus(Integer.parseInt(EnumRefundStatus.REFUND_FAILED.getCode()));
							refundDAO.updateOrderItemRefundInfo(refundOrderItemDTO);
						}
						
						
						
					}
				}catch(Exception e){
					log.error("batchReviseRefund error",e);
					status.setRollbackOnly();
					return false;
				}
				
				return true;
			}
			
		});
		
		if(result){
			for(OrderItemDO orderItemDO: finishedOrderItems)
			try{
				msgQueueManager.sendRefundSuccessMsg(orderItemDO);
			}catch(Exception e){
				log.error("mq send message error", e);
			}
			
		}
		
		return result;
	}
	
	
	
	
	


	


}
