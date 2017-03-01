package com.mockuai.tradecenter.core.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import com.mockuai.tradecenter.common.enums.EnumPaymentMethod;
import com.mockuai.tradecenter.core.manager.*;
import com.mockuai.virtualwealthcenter.common.constant.WealthType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.mockuai.appcenter.common.constant.BizPropertyKey;
import com.mockuai.appcenter.common.domain.BizInfoDTO;
import com.mockuai.appcenter.common.domain.BizPropertyDTO;
import com.mockuai.tradecenter.common.api.TradeResponse;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.common.domain.PaymentUrlDTO;
import com.mockuai.tradecenter.common.domain.refund.RefundOrderItemDTO;
import com.mockuai.tradecenter.common.enums.EnumRefundStatus;
import com.mockuai.tradecenter.common.util.DateUtil;
import com.mockuai.tradecenter.core.base.ClientExecutor;
import com.mockuai.tradecenter.core.base.ClientExecutorFactory;
import com.mockuai.tradecenter.core.dao.RefundDAO;
import com.mockuai.tradecenter.core.domain.OrderDO;
import com.mockuai.tradecenter.core.domain.OrderItemDO;
import com.mockuai.tradecenter.core.domain.RefundItemLogDO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.impl.RefundOrderItemManagerImpl;
import com.mockuai.tradecenter.core.service.RefundService;
import com.mockuai.tradecenter.core.service.RequestContext;
import com.mockuai.tradecenter.core.util.DozerBeanService;
import com.mockuai.tradecenter.core.util.TradeUtil;

public class RefundServiceImpl extends BaseService implements RefundService{

	private static final Logger log = LoggerFactory.getLogger(RefundOrderItemManagerImpl.class);
	@Resource
	private AppManager appManager;

	@Resource
	private ClientExecutorFactory clientExecutorFactory;
	
	@Resource
	private TransactionTemplate transactionTemplate;
	
	
	@Resource
	private SellerTransLogManager sellerTransLogManager;
	
	@Resource
    private DozerBeanService  dozerBeanService;
	
	@Autowired
	private RefundDAO refundDAO;
	
	@Autowired
    private RefundOrderItemManager refundOrderItemManager;

	@Resource
	private VirtualWealthManager virtualWealthManager;

	@Resource
	private MsgQueueManager msgQueueManager;

	private Boolean frozenSellerMoney(RefundOrderItemDTO refundOrderItemDTO, OrderDO order) throws TradeException {
		Boolean result = true;
		BizInfoDTO bizInfo = appManager.getBizInfo(order.getBizCode());
		if (null != bizInfo) {
			BizPropertyDTO isPayByMockuai = bizInfo.getBizPropertyMap().get(BizPropertyKey.IS_PAY_BY_MOCKUAI);
			if (null != isPayByMockuai) {
				if (isPayByMockuai.getValue().equals("1")) {// 代表走魔筷的帐
					result = sellerTransLogManager.recordTransLogForRefund(refundOrderItemDTO);
				}
			}
		}
		return result;
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

	private PaymentUrlDTO getPaymentUrlDTO(OrderDO order, RequestContext context) throws TradeException {
		log.info(" yolee payment refund : " + order.getPaymentId() + "_Refund");
		ClientExecutor clientExecutor = clientExecutorFactory.getExecutor(order.getPaymentId() + "_Refund");
		PaymentUrlDTO paymentUrlDTO = clientExecutor.getPaymentUrl(context);
		return paymentUrlDTO;
	}
	
	private String getBatchId() throws TradeException {
		String batchId = DateUtil.getFormatDate(new Date(), "yyyyMMdd")+(System.currentTimeMillis() + 102L * 365 * 24 * 60 * 60 * 1000);
		return batchId;
	}

	@Override
	public TradeResponse<?> confirmOrderItemRefund(final RequestContext context) throws TradeException {
		final OrderDO orderDO = (OrderDO) context.get("orderDO");
		final String appKey = (String)context.getRequest().getParam("appKey");

		if (null == orderDO) {
			throw new TradeException("orderDO is null");
		}
		
		final Map<String, BizPropertyDTO> bizPropertyMap = (Map<String, BizPropertyDTO>) context.get("bizPropertyMap");
		if (null == bizPropertyMap) {
			throw new TradeException(orderDO.getBizCode() + " bizPropertyMap is null");
		}
		final RefundOrderItemDTO refundOrderItemDTO = (RefundOrderItemDTO) context.get("refundOrderItemDTO");

		final OrderItemDO orderItem = (OrderItemDO) context.get("orderItemDO");
		
		
		printIntoService(log, "confirmOrderItemRefund", refundOrderItemDTO, "");

		TradeResponse<PaymentUrlDTO> result = transactionTemplate
				.execute(new TransactionCallback<TradeResponse<PaymentUrlDTO>>() {

					@Override
					public TradeResponse<PaymentUrlDTO> doInTransaction(TransactionStatus status) {
						TradeResponse<PaymentUrlDTO> response = null;
						PaymentUrlDTO paymentUrlDTO = null;
						try {
							BeanUtils.copyProperties(orderItem, refundOrderItemDTO);
							refundOrderItemDTO.setRefundStatus(Integer.parseInt(EnumRefundStatus.REFUNDING.getCode()));
							
							String refundBatchId = getBatchId();
							refundOrderItemDTO.setRefundBatchNo(refundBatchId);
							refundDAO.updateOrderItemRefundInfo(refundOrderItemDTO);
							
							//如果是使用余额支付的话，则直接退回去即可
							if(orderDO.getPaymentId().intValue() ==
									Integer.valueOf(EnumPaymentMethod.ACCOUNT_BALANCE_PAY.getCode())) {

								//算出实际可退的余额支付金额
								long refundableWealthAmount = orderItem.getVirtualWealthAmount();

								//获取实际需要退的退款金额
								long refundAmount = getRefundAmount(orderItem.getRefundAmount(),
										refundableWealthAmount);

								Map<Integer, Long> wealthMap = new HashMap<Integer, Long>();
								wealthMap.put(WealthType.VIRTUAL_WEALTH.getValue(), refundAmount);
								virtualWealthManager.returnUserWealth(orderDO.getUserId(),
										orderDO.getId(), orderItem.getId(), wealthMap, appKey);

								//设置实际退款金额
								refundOrderItemDTO.setRefundAmount(refundAmount);

								boolean result =
										refundOrderItemManager.notifyRefundSuccess(refundOrderItemDTO,orderItem);
								if (result == false) {
									status.setRollbackOnly();
									return new TradeResponse<PaymentUrlDTO>(ResponseCode.SYS_E_SERVICE_EXCEPTION,
											"notifyRefundSuccess error");
								}

							}else if(orderDO.getPaymentId().intValue() ==
									Integer.valueOf(EnumPaymentMethod.HI_COIN_PAY.getCode())) {
								//TODO 需要确认下嗨币支付的退款逻辑
							}else{
								//使用第三方支付，则需要退回第三方支付平台
								//算出实际需要退的第三方支付金额
								long refundCashAmount = TradeUtil.getRefundableTotalAmount(orderItem);
								log.info("refundCashAmount : "+refundCashAmount);

								//获取实际需要退的退款金额
								long refundAmount = getRefundAmount(orderItem.getRefundAmount(),
										refundCashAmount);

								//设置实际退款金额
								refundOrderItemDTO.setRefundAmount(refundAmount);

								context.put("refundAmount", refundAmount);
//								refundOrderItemDTO.setRefundAmount(refundAmount);
								context.put("refundOrderItemDTO", refundOrderItemDTO);
								paymentUrlDTO = getPaymentUrlDTO(orderDO,context);
							}

							//添加退款操作记录
							Long refundItemLogId = addRefundItemLog(refundOrderItemDTO, true);
							if (refundItemLogId == 0) {
								status.setRollbackOnly();
								return new TradeResponse<PaymentUrlDTO>(ResponseCode.SYS_E_SERVICE_EXCEPTION,
										"confirm refund error");
							}
						}catch(TradeException e){
							log.error("RefundServiceImpl.confirmOrderItemRefund error", e.getMessage());
							status.setRollbackOnly();
							return new TradeResponse<PaymentUrlDTO>(e.getResponseCode(),e.getMessage());
						} catch (Exception e) {
							log.error("RefundServiceImpl.confirmOrderItemRefund error", e);
							status.setRollbackOnly();
							return new TradeResponse<PaymentUrlDTO>(ResponseCode.SYS_E_SERVICE_EXCEPTION,
									"add refundRefundLog error");
						}
						response = new TradeResponse(paymentUrlDTO);
						return response;
					}

				});
		return result;

	}

	/**
	 * 获取退款金额
	 * @param applyRefundAmount
	 * @param maxRefundableAmount
	 * @return
	 */
	private long getRefundAmount(long applyRefundAmount, long maxRefundableAmount) {
		return applyRefundAmount <= maxRefundableAmount ? applyRefundAmount : maxRefundableAmount;
	}

}
