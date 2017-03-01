package com.mockuai.tradecenter.core.manager.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.mockuai.tradecenter.common.domain.DataQTO;
import com.mockuai.tradecenter.common.domain.OrderConsigneeDTO;
import com.mockuai.tradecenter.common.domain.OrderDTO;
import com.mockuai.tradecenter.common.domain.OrderQTO;
import com.mockuai.tradecenter.common.domain.refund.RefundOrderItemDTO;
import com.mockuai.tradecenter.common.domain.settlement.ApplyFrozenDTO;
import com.mockuai.tradecenter.common.domain.settlement.ApplyWithDrawDTO;
import com.mockuai.tradecenter.common.domain.settlement.NotifyWithdrawResultDTO;
import com.mockuai.tradecenter.common.domain.settlement.SellerMoneyDTO;
import com.mockuai.tradecenter.common.domain.settlement.SellerMoneyQTO;
import com.mockuai.tradecenter.common.domain.settlement.SellerTransLogDTO;
import com.mockuai.tradecenter.common.domain.settlement.SellerTransLogQTO;
import com.mockuai.tradecenter.common.domain.settlement.ShopDepositDTO;
import com.mockuai.tradecenter.common.domain.settlement.WithdrawDTO;
import com.mockuai.tradecenter.common.domain.settlement.WithdrawQTO;
import com.mockuai.tradecenter.common.enums.EnumChannelType;
import com.mockuai.tradecenter.common.enums.EnumInOutMoneyType;
import com.mockuai.tradecenter.common.enums.EnumNotAvailableBalance;
import com.mockuai.tradecenter.common.enums.EnumSettlementMark;
import com.mockuai.tradecenter.common.enums.EnumWithdrawStatus;
import com.mockuai.tradecenter.common.util.DateUtil;
import com.mockuai.tradecenter.common.util.StringUtil;
import com.mockuai.tradecenter.core.dao.OrderDAO;
import com.mockuai.tradecenter.core.dao.SellerMoneyDAO;
import com.mockuai.tradecenter.core.dao.SellerTransLogDAO;
import com.mockuai.tradecenter.core.dao.WithdrawBatchDAO;
import com.mockuai.tradecenter.core.dao.WithdrawInfoDAO;
import com.mockuai.tradecenter.core.domain.OrderConsigneeDO;
import com.mockuai.tradecenter.core.domain.OrderDO;
import com.mockuai.tradecenter.core.domain.SellerMoneyDO;
import com.mockuai.tradecenter.core.domain.SellerTransLogDO;
import com.mockuai.tradecenter.core.domain.WithdrawBatchDO;
import com.mockuai.tradecenter.core.domain.WithdrawInfoDO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.BaseService;
import com.mockuai.tradecenter.core.manager.OrderConsigneeManager;
import com.mockuai.tradecenter.core.manager.SellerTransLogManager;
import com.mockuai.tradecenter.core.util.DozerBeanService;
import com.mockuai.tradecenter.core.util.ModelUtil;
import com.mockuai.tradecenter.core.util.TradeUtil.ShopType;
import com.mockuai.tradecenter.core.util.TradeUtil.WithdrawAuditStatus;

public class SellerTransLogManagerImpl extends BaseService implements SellerTransLogManager {

	private static final Logger log = LoggerFactory.getLogger(SellerTransLogManagerImpl.class);

	@Autowired
	private SellerMoneyDAO sellerMoneyDAO;

	@Autowired
	private SellerTransLogDAO sellerTransLogDAO;

	@Resource
	private TransactionTemplate transactionTemplate;

	@Autowired
	private WithdrawInfoDAO withdrawInfoDAO;

	@Resource
	private DozerBeanService dozerBeanService;

	@Autowired
	private OrderDAO orderDAO;

	@Resource
	private WithdrawBatchDAO withdrawBatchDAO;

	@Resource
	private OrderConsigneeManager orderConsigneeManager;

	private SellerMoneyDO getSellerMoney(Long sellerId, String bizCode) throws Exception {
		printIntoService(log, "getSellerMoney", sellerId + bizCode, "");
		SellerMoneyDO sellerMoney = sellerMoneyDAO.getSellerMoneyBySellerId(sellerId);
		if (null == sellerMoney) {
			sellerMoney = new SellerMoneyDO();
			sellerMoney.setSellerId(sellerId);
			sellerMoney.setBizCode(bizCode);
			sellerMoney.setCurrentBalance(0L);
			sellerMoney.setFreezeAmount(0L);
			sellerMoney.setAvailableBalance(0L);
			Long id = sellerMoneyDAO.addSellerMoney(sellerMoney);
			sellerMoney.setId(id);
		}
		printIntoService(log, "getSellerMoney", sellerId + bizCode, "");
		return sellerMoney;
	}

	private void modifyBalance(SellerMoneyDO sellerMoneyDO, Long inAmount, Long outAmount, Long freezeAmount)
			throws Exception {
		printIntoService(log, "modifyBalance", sellerMoneyDO, "");
		sellerMoneyDO.setCurrentBalance(sellerMoneyDO.getCurrentBalance() + inAmount - outAmount);
		sellerMoneyDO.setFreezeAmount(sellerMoneyDO.getFreezeAmount() + freezeAmount);
		sellerMoneyDO.setAvailableBalance(sellerMoneyDO.getCurrentBalance() - sellerMoneyDO.getFreezeAmount());
		sellerMoneyDAO.updateSellerMoney(sellerMoneyDO);
		printOutService(log, "modifyBalance", sellerMoneyDO, "");
	}

	private void modifyBalanceforUnfreeze(SellerMoneyDO sellerMoneyDO, Long unfreezeAmount) throws Exception {
		printIntoService(log, "modifyBalanceforUnfreeze", sellerMoneyDO, "");
		sellerMoneyDO.setFreezeAmount(sellerMoneyDO.getFreezeAmount() - unfreezeAmount);
		sellerMoneyDO.setCurrentBalance(sellerMoneyDO.getCurrentBalance() - unfreezeAmount);
		sellerMoneyDO.setAvailableBalance(sellerMoneyDO.getCurrentBalance() - sellerMoneyDO.getFreezeAmount());
		sellerMoneyDAO.updateSellerMoney(sellerMoneyDO);
		printOutService(log, "modifyBalanceforUnfreeze", sellerMoneyDO, "");
	}

	private void modifyBalanceforWithdrawFailed(SellerMoneyDO sellerMoneyDO, Long unfreezeAmount) throws Exception {
		printIntoService(log, "modifyBalanceforWithdrawFailed", sellerMoneyDO, "");
		sellerMoneyDO.setFreezeAmount(sellerMoneyDO.getFreezeAmount() - unfreezeAmount);
		sellerMoneyDO.setCurrentBalance(sellerMoneyDO.getAvailableBalance() + unfreezeAmount);
		sellerMoneyDO.setAvailableBalance(sellerMoneyDO.getCurrentBalance() - sellerMoneyDO.getFreezeAmount());
		sellerMoneyDAO.updateSellerMoney(sellerMoneyDO);
		printOutService(log, "modifyBalanceforWithdrawFailed", sellerMoneyDO, "");
	}
	

	private void addSellerTransLog(SellerTransLogDTO transLogDTO,boolean needRecodr) throws Exception {
		
		if(needRecodr){
			SellerTransLogDO sellerTransLog = new SellerTransLogDO();
			sellerTransLog.setBizCode(transLogDTO.getBizCode());
			sellerTransLog.setSellerId(transLogDTO.getSellerId());
			sellerTransLog.setOrderId(transLogDTO.getOrderId());
			sellerTransLog.setOrderSn(transLogDTO.getOrderSn());
			sellerTransLog.setFundInAmount(transLogDTO.getFundInAmount());
			sellerTransLog.setFundOutAmount(transLogDTO.getFundOutAmount());
			sellerTransLog.setType(transLogDTO.getType());
			sellerTransLog.setWithdrawId(transLogDTO.getWithdrawId());
			SellerMoneyDO sellerMoney = sellerMoneyDAO.getSellerMoneyBySellerId(transLogDTO.getSellerId());
			sellerTransLog.setLastAmount(sellerMoney.getAvailableBalance());
			sellerTransLog.setSettlementMark(EnumSettlementMark.toMap().get(transLogDTO.getSettlementMark()));
			sellerTransLog.setPaymentId(transLogDTO.getPaymentId());
			sellerTransLog.setUserId(transLogDTO.getUserId());
			sellerTransLog.setItemSkuId(transLogDTO.getItemSkuId());
			sellerTransLog.setShopType(transLogDTO.getShopType());
			printIntoService(log, "addSellerTransLog", sellerTransLog, "");

			sellerTransLogDAO.addSellerTransLog(sellerTransLog);
		}
		
	}

	private void modifyOrderSettlementStatus(SellerTransLogDTO transLogDTO) throws Exception {
		if (null != transLogDTO.getOrderId()) {
			orderDAO.modifySettlementStatus(transLogDTO.getOrderId(), "Y");
		}
	}
	
	@Override
	public Boolean processRefundNotify(final RefundOrderItemDTO refundItemDTO) throws TradeException {
		printIntoService(log, "processRefundNotify", refundItemDTO, "");
		final SellerTransLogDO  translogDO = sellerTransLogDAO.getTransLogByOrderSnAndSkuId(
				refundItemDTO.getOrderSn(), refundItemDTO.getItemSkuId());
		if(null==translogDO){
			throw new TradeException("refund sellerTrans is null");
		}
		return transactionTemplate.execute(new TransactionCallback<Boolean>() {
			@Override
			public Boolean doInTransaction(TransactionStatus status) {
				try{
					SellerMoneyDO sellerMoney = getSellerMoney(refundItemDTO.getSellerId(), refundItemDTO.getBizCode());
					modifyBalanceforUnfreeze(sellerMoney,refundItemDTO.getRefundAmount());
					translogDO.setSettlementMark(EnumSettlementMark.toMap().get("Y"));
					
					translogDO.setCancelMark(Integer.parseInt(EnumSettlementMark.toMap().get("N")));

					translogDO.setFundOutAmount(refundItemDTO.getRefundAmount());
					
					sellerTransLogDAO.updateById(translogDO);	
					return true;
				}catch (Exception e) {
					log.error("processRefundNotify error", e);
					status.setRollbackOnly();
					return false;
				}
			}
		});
	}
	
	@Override
	public boolean recordTransLogForRefund(final RefundOrderItemDTO refundItemDTO) {
		// TODO Auto-generated method stub
		printIntoService(log, "recordTransLogForRefund", refundItemDTO, "");

		return transactionTemplate.execute(new TransactionCallback<Boolean>() {

			@Override
			public Boolean doInTransaction(TransactionStatus status) {
				try {
					

					if(null!=refundItemDTO.getOrderSn()){
						SellerTransLogDO  translogDO = sellerTransLogDAO.getTransLogByOrderSnAndSkuId(
								refundItemDTO.getOrderSn(), refundItemDTO.getItemSkuId());
						if(null==translogDO){
							//1 
							SellerMoneyDO sellerMoney = getSellerMoney(refundItemDTO.getSellerId(), refundItemDTO.getBizCode());
							modifyBalance(sellerMoney,0L, 0L,refundItemDTO.getRefundAmount());
							
							SellerTransLogDTO sellerTransLog  = new SellerTransLogDTO();
							sellerTransLog.setBizCode(refundItemDTO.getBizCode());
							sellerTransLog.setSellerId(refundItemDTO.getSellerId());
							sellerTransLog.setOrderId(refundItemDTO.getOrderId());
							sellerTransLog.setOrderSn(refundItemDTO.getOrderSn());
							sellerTransLog.setFundInAmount(0L);
							sellerTransLog.setFundOutAmount(0L);
							sellerTransLog.setType(EnumInOutMoneyType.REFUND.getCode());
							sellerTransLog.setLastAmount(sellerMoney.getAvailableBalance());
							sellerTransLog.setSettlementMark(EnumSettlementMark.NOT_SETTLED.getDescription());//未结算
							sellerTransLog.setPaymentId(1);
							sellerTransLog.setUserId(refundItemDTO.getUserId());
							sellerTransLog.setItemSkuId(refundItemDTO.getItemSkuId());
							addSellerTransLog(sellerTransLog,true);
							
						}
					}

					return true;
				} catch (Exception e) {
					log.error("recordTransLogForRefund error", e);
					status.setRollbackOnly();
					return false;
				}
			}
		});
	}

	@Override
	public boolean recordTransLog(final SellerTransLogDTO transLogDTO) {

		printIntoService(log, "recordTransLog", transLogDTO, "");

		return transactionTemplate.execute(new TransactionCallback<Boolean>() {
			
			
//			public void addMallTransLogRecord() throws Exception{
//				SellerMoneyDO sellerMoney = sellerMoneyDAO.getSellerMoney(transLogDTO.getBizCode(),ShopType.MALL);
//				if(sellerMoney==null){
//					sellerMoney = new SellerMoneyDO();
//					sellerMoney.setBizCode(transLogDTO.getBizCode());
//					sellerMoney.setCurrentBalance(0L);
//					sellerMoney.setFreezeAmount(0L);
//					sellerMoney.setAvailableBalance(0L);
//					sellerMoney.setShopType(ShopType.MALL);
//					Long sellerMoneyId = sellerMoneyDAO.addSellerMoney(sellerMoney);
//					sellerMoney.setId(sellerMoneyId);
//				}
//				
//				boolean needRecord = true;
//				if(null!=transLogDTO.getOrderSn()){
////					SellerTransLogDO  translogDO = sellerTransLogDAO.getTransLogByOrderIdAndType(transLogDTO.getOrderSn(), transLogDTO.getType());
//					SellerTransLogDO translogDO = sellerTransLogDAO.getSingleTransLog(transLogDTO.getOrderSn(), 
//							transLogDTO.getType(), 3);
//					if(null!=translogDO)
//						needRecord = false;
//				}
//				if(needRecord){
//					modifyBalance(sellerMoney, transLogDTO.getFundInAmount(), transLogDTO.getFundOutAmount(),
//							transLogDTO.getFreezeAmount());
//					if (StringUtils.isBlank(transLogDTO.getOrderSn())) {
//						transLogDTO.setFundOutAmount(transLogDTO.getFreezeAmount());
//					}
//					SellerTransLogDO sellerTransLog = new SellerTransLogDO();
//					sellerTransLog.setBizCode(transLogDTO.getBizCode());
//					sellerTransLog.setSellerId(transLogDTO.getSellerId());
//					sellerTransLog.setOrderId(transLogDTO.getOrderId());
//					sellerTransLog.setOrderSn(transLogDTO.getOrderSn());
//					sellerTransLog.setFundInAmount(transLogDTO.getFundInAmount());
//					sellerTransLog.setFundOutAmount(transLogDTO.getFundOutAmount());
//					sellerTransLog.setType(transLogDTO.getType());
//					sellerTransLog.setWithdrawId(transLogDTO.getWithdrawId());
//					sellerTransLog.setLastAmount(sellerMoney.getAvailableBalance());
//					sellerTransLog.setSettlementMark(EnumSettlementMark.toMap().get(transLogDTO.getSettlementMark()));
//					sellerTransLog.setPaymentId(transLogDTO.getPaymentId());
//					sellerTransLog.setUserId(transLogDTO.getUserId());
//					sellerTransLog.setItemSkuId(transLogDTO.getItemSkuId());
//					sellerTransLog.setShopType(ShopType.MALL);
////					sellerTransLog.setMallMark(1);
//					printIntoService(log, "addSellerTransLog", sellerTransLog, "");
//
//					sellerTransLogDAO.addSellerTransLog(sellerTransLog);
//				}
//			}

			@Override
			public Boolean doInTransaction(TransactionStatus status) {

				try {

					SellerMoneyDO sellerMoney = getSellerMoney(transLogDTO.getSellerId(), transLogDTO.getBizCode());

					boolean needRecord = true;
					if(null!=transLogDTO.getOrderSn()){
//						SellerTransLogDO  translogDO = sellerTransLogDAO.getTransLogByOrderIdAndType(transLogDTO.getOrderSn(), transLogDTO.getType());
						SellerTransLogDO translogDO = sellerTransLogDAO.getSingleTransLog(transLogDTO.getOrderSn(), 
								transLogDTO.getType(), transLogDTO.getShopType());
						if(null!=translogDO)
							needRecord = false;
					}
					if(needRecord){
						modifyBalance(sellerMoney, transLogDTO.getFundInAmount(), transLogDTO.getFundOutAmount(),
								transLogDTO.getFreezeAmount());
						if (StringUtils.isBlank(transLogDTO.getOrderSn())) {
							transLogDTO.setFundOutAmount(transLogDTO.getFreezeAmount());
						}
					
						addSellerTransLog(transLogDTO,needRecord);
					}
					
					if(transLogDTO.getShopType()!=null&&transLogDTO.getShopType().intValue()==1){
//						addMallTransLogRecord();
						recordMallTransLog(transLogDTO);
					}
					

					modifyOrderSettlementStatus(transLogDTO);

					return true;
				} catch (Exception e) {
					log.error("recordTransLog error", e);
					status.setRollbackOnly();
					return false;
				}
			}
		});
	}

	private Long addWithdrawInfo(ApplyWithDrawDTO withDrawDTO) throws Exception {
		printIntoService(log, "addWithdrawInfo", withDrawDTO, "");

		WithdrawInfoDO withdraw = dozerBeanService.cover(withDrawDTO, WithdrawInfoDO.class);
		withdraw.setStatus(EnumWithdrawStatus.WAIT.getCode());
		withdraw.setChannel(EnumChannelType.getByOldCode(withdraw.getChannel()).getCode());
		printOutService(log, "addWithdrawInfo", withdraw, "");
		return withdrawInfoDAO.addWithdrawInfo(withdraw);

	}

	private void modifyWithdrawInfoStatus(Long id, String status) {
		printIntoService(log, "modifyWithdrawInfoStatus", id + status, "");
		WithdrawInfoDO withdrawInfoDO = new WithdrawInfoDO();
		withdrawInfoDO.setId(id);
		withdrawInfoDO.setStatus(EnumWithdrawStatus.getByOldCode(status).getCode());
		withdrawInfoDAO.updateWithdrawInfo(withdrawInfoDO);
	}

	private Boolean processNotifyWithdraw(final NotifyWithdrawResultDTO dto) {
		printIntoService(log, "processNotifyWithdraw", dto, "");
		return transactionTemplate.execute(new TransactionCallback<Boolean>() {
			
			
			
			private void processWithdrawNotify(Long id,boolean success) throws Exception{
				SellerTransLogDO sellerTranslog = sellerTransLogDAO.getTranslogByWithdrawId(id);
				boolean needProcess = true;
				
				if(success&&sellerTranslog.getSettlementMark().equals("Y")){
					needProcess = false;
				}
				
				if(needProcess){
					SellerMoneyDO sellerMoney = getSellerMoney(sellerTranslog.getSellerId(),
							sellerTranslog.getBizCode());
					if(success){
						modifyBalanceforUnfreeze(sellerMoney, sellerTranslog.getFundOutAmount());
					}
						
					else{
						modifyBalanceforWithdrawFailed(sellerMoney,sellerTranslog.getFundOutAmount());
					}
						

					modifyWithdrawInfoStatus(id, success? "FINISHED":"FAILED");

//					sellerTranslog.setSettlementMark(success?"Y":"N");
					sellerTranslog.setSettlementMark(EnumSettlementMark.toMap().get("Y"));
					
					//TODO 如果提现失败 cancelMark设置为Y。。。
					sellerTranslog.setCancelMark(success?Integer.parseInt(EnumSettlementMark.toMap().get("N")):Integer.parseInt(EnumSettlementMark.toMap().get("Y")));

					sellerTransLogDAO.updateById(sellerTranslog);	
				}
			}
			
			@Override
			public Boolean doInTransaction(TransactionStatus status) {
				try {
					List<Long> successIds = dto.getSuccessIds();
					if(null!=successIds&&successIds.size()>0){
						for (Long id : successIds) {
							processWithdrawNotify(id,true);
						}
					}
					
					List<Long> failIds = dto.getFailIds();
					if (null != failIds && failIds.size() > 0) {
						for (Long failId : failIds) {
							processWithdrawNotify(failId,false);
						}
					}
					if(StringUtils.isNotBlank(dto.getBatchNo())){
						withdrawBatchDAO.updateWithdrawBatchStatus(dto.getBatchNo(), "FINISHED");
					}
					

				} catch (Exception e) {
					log.error("recordTransLogForWithDraw error", e);
					status.setRollbackOnly();
					return false;
				}

				return true;
			}
		});
	}

	@Override
	public boolean recordTransLogForWithDraw(final ApplyWithDrawDTO withDrawDTO) {
		printIntoService(log, "recordTransLogForWithDraw", withDrawDTO, "");

		return transactionTemplate.execute(new TransactionCallback<Boolean>() {

			@Override
			public Boolean doInTransaction(TransactionStatus status) {

				try {

					Long withdrawId = addWithdrawInfo(withDrawDTO);

					SellerTransLogDTO dto = new SellerTransLogDTO();
					dto.setSellerId(withDrawDTO.getSellerId());
					dto.setBizCode(withDrawDTO.getBizCode());
					dto.setFundInAmount(0L);
					dto.setFundOutAmount(0L);
					dto.setFreezeAmount(withDrawDTO.getAmount());
					dto.setSettlementMark("N");
					dto.setPaymentId(Integer.parseInt(EnumChannelType.getByOldCode(withDrawDTO.getChannel()).getCode()));
					dto.setWithdrawId(withdrawId);
					dto.setType(EnumInOutMoneyType.WITHDRWD.getCode());
					
					if( null != withDrawDTO.getSellerId() ){
						recordTransLog(dto);
					}else{
						recordMallTransLog(dto);
					}
					

					return true;
				} catch (Exception e) {
					log.error("recordTransLogForWithDraw error", e);
					status.setRollbackOnly();
					return false;
				}
			}
		});

	}

	@Override
	public List<?> queryTransLog(SellerTransLogQTO query) {
		if(query.isAllNotAvailableTransLog()){
			query.setTypeIds(EnumNotAvailableBalance.getCodeList());
		}
		printIntoService(log, "queryTransLog", query, "");
		List<SellerTransLogDO> list = sellerTransLogDAO.querySellerTransLog(query);
		
		List<SellerTransLogDTO> returnList = dozerBeanService.coverList(list, SellerTransLogDTO.class);
		if(null!=returnList&&returnList.size()>0){
			for(SellerTransLogDTO sellerTransLogDTO :returnList ){
				sellerTransLogDTO.setType(EnumInOutMoneyType.getByCode(sellerTransLogDTO.getType()).getOldCode());
			}
		}
		return returnList;
	}

	@Override
	public Long getQueryCount(SellerTransLogQTO query) {
		if(query.isAllNotAvailableTransLog()){
			query.setTypeIds(EnumNotAvailableBalance.getCodeList());
		}
		printIntoService(log, "getQueryCount", query, "");
		return sellerTransLogDAO.getQueryCount(query);
	}

	@Override
	public List<?> querySellerMoney(SellerMoneyQTO query) {
		printIntoService(log, "querySellerMoney", query, "");
		List<?> list = sellerMoneyDAO.querySellerMoney(query);
		List<SellerMoneyDTO> moneyDTOs = dozerBeanService.coverList(list, SellerMoneyDTO.class);

		if((null==moneyDTOs||moneyDTOs.isEmpty())&&(query.getIsAllSubShop()!=null&&query.getIsAllSubShop()==1)){
			return Collections.emptyList();
		}
		
		if (null != list && list.size() > 0) {
			DataQTO dataQTO = null;
			for (SellerMoneyDTO moneyDTO : moneyDTOs) {
				dataQTO = new DataQTO();
				dataQTO.setBizCode(moneyDTO.getBizCode());
				dataQTO.setSeller_id(moneyDTO.getSellerId());
				Long unSettlementAmount = orderDAO.getUnSettlementAmount(dataQTO);
				moneyDTO.setUnSettlementAmount(unSettlementAmount);
				moneyDTO.setCanUseBalance(moneyDTO.getAvailableBalance());
				
				WithdrawQTO withdrawQuery = new WithdrawQTO();
				withdrawQuery.setSellerId(moneyDTO.getSellerId());
				withdrawQuery.setBizCode(query.getBizCode());
				withdrawQuery.setStatus(EnumWithdrawStatus.FINISHED.getCode());
				
				moneyDTO.setFinishedWithdrawAmount(withdrawInfoDAO.getWithdrawSumAmount(withdrawQuery));
				
				withdrawQuery.setStatus(EnumWithdrawStatus.PROCESSING.getCode());
				moneyDTO.setProcessingWithdrawAmount(withdrawInfoDAO.getWithdrawSumAmount(withdrawQuery));
				
				
				OrderQTO orderQTO= new OrderQTO();
				orderQTO.setBizCode(moneyDTO.getBizCode());
				orderQTO.setSellerId(moneyDTO.getSellerId());
				orderQTO.setSettlementMark(Integer.parseInt(EnumSettlementMark.SETTLEMENTED.getCode()));
				Long mallCommission = orderDAO.getMallCommissionSumAmount(orderQTO);
				moneyDTO.setMallCommission(mallCommission);
				
				OrderQTO shopTotalAmountQuery = new OrderQTO();
				shopTotalAmountQuery.setSellerId(moneyDTO.getSellerId());
				Long shopTotalAmount = orderDAO.getSellerSettleTotalAmount(shopTotalAmountQuery);
				
				long shopIncomeAmt = shopTotalAmount-mallCommission;
				
				moneyDTO.setShopIncomeSumAmount(shopIncomeAmt);
			}
		}else{
			moneyDTOs = new ArrayList<SellerMoneyDTO>();
			DataQTO dataQTO = new DataQTO();
			dataQTO.setBizCode(query.getBizCode());
			dataQTO.setSeller_id(query.getSellerId());
			Long unSettlementAmount = orderDAO.getUnSettlementAmount(dataQTO);
			
			SellerMoneyDTO sellerMoneyDTO = new SellerMoneyDTO();
			sellerMoneyDTO.setCanUseBalance(0L);
			sellerMoneyDTO.setFreezeAmount(0L);
			sellerMoneyDTO.setCurrentBalance(0L);
			sellerMoneyDTO.setUnSettlementAmount(unSettlementAmount);
			
			WithdrawQTO withdrawQuery = new WithdrawQTO();
			withdrawQuery.setSellerId(query.getSellerId());
			withdrawQuery.setStatus(EnumWithdrawStatus.FINISHED.getCode());
			withdrawQuery.setBizCode(query.getBizCode());
			sellerMoneyDTO.setFinishedWithdrawAmount(withdrawInfoDAO.getWithdrawSumAmount(withdrawQuery));
			
			withdrawQuery.setStatus(EnumWithdrawStatus.PROCESSING.getCode());
			
			sellerMoneyDTO.setProcessingWithdrawAmount(withdrawInfoDAO.getWithdrawSumAmount(withdrawQuery));
			
			
			OrderQTO orderQTO= new OrderQTO();
			orderQTO.setSellerId(query.getSellerId());
			orderQTO.setBizCode(query.getBizCode());
			orderQTO.setSettlementMark(Integer.parseInt(EnumSettlementMark.SETTLEMENTED.getCode()));
			Long mallCommission = orderDAO.getMallCommissionSumAmount(orderQTO);
			sellerMoneyDTO.setMallCommission(mallCommission);
			
			moneyDTOs.add(sellerMoneyDTO);
		}
		return moneyDTOs;
	}

	@Override
	public Boolean processWithdrawNotify(NotifyWithdrawResultDTO dto) throws TradeException {
		printIntoService(log, "processWithdrawNotify", dto, "");
		if(StringUtils.isNotBlank(dto.getBatchNo())){
			final WithdrawBatchDO batchDO = withdrawBatchDAO.getWithdrawBatchByBatchNo(dto.getBatchNo());
			if (null == batchDO)
				throw new TradeException(dto.getBatchNo() + " withdrawBatch is not exist");
			if (batchDO.getStatus().equals(EnumWithdrawStatus.FINISHED.getCode()))
				throw new TradeException(dto.getBatchNo() + " withdrawBatch has been processed");
		}
		
		return processNotifyWithdraw(dto);
	}
	
	

	@Override
	public Long getUnSettlementAmount(OrderQTO query) {
		printIntoService(log, "getUnSettlementAmount", query, "");

		return null;
	}

	@Override
	public List<?> queryUnsettlementOrders(OrderQTO query) throws TradeException {
		printIntoService(log, "queryUnsettlementOrders", query, "");

		if(null==query.getOffset())
			query.setOffset(0);
		if(null==query.getCount())
			query.setCount(20);
		List<OrderDO> list = orderDAO.queryUnsettlementOrders(query);
		List<OrderDTO> orderDTOList = new ArrayList<OrderDTO>();
		for (OrderDO orderDO : list) {
			OrderConsigneeDO orderConsigneeDO = orderConsigneeManager.getOrderConsignee(orderDO.getId(),
					orderDO.getUserId());
			OrderDTO orderDTO = ModelUtil.convert2OrderDTO(orderDO);
			OrderConsigneeDTO consignessDTO = ModelUtil.convert2OrderConsigneeDTO(orderConsigneeDO);

			orderDTO.setOrderConsigneeDTO(consignessDTO);
			
			orderDTOList.add(orderDTO);
		}
		return orderDTOList;
	}

	@Override
	public Long getUnsettlementOrderCount(OrderQTO query) {
		printIntoService(log, "getUnsettlementOrderCount", query, "");
		return orderDAO.getUnsettlementOrderCount(query);
	}

	@Override
	public int updateOrderSettlementStatus(Long oid, String status) throws TradeException {
		printIntoService(log, "updateOrderSettlementStatus", oid, "");
		
		return orderDAO.modifySettlementStatus(oid, "Y");
	}

	@Override
	public boolean recordMallTransLog(final SellerTransLogDTO transLogDTO) {
		printIntoService(log, "recordMallTransLog", transLogDTO, "");
		return transactionTemplate.execute(new TransactionCallback<Boolean>() {

			@Override
			public Boolean doInTransaction(TransactionStatus status) {
				try{
					SellerMoneyDO sellerMoney = sellerMoneyDAO.getSellerMoney(transLogDTO.getBizCode(),ShopType.MALL);
					if(sellerMoney==null){
						sellerMoney = new SellerMoneyDO();
						sellerMoney.setBizCode(transLogDTO.getBizCode());
						sellerMoney.setCurrentBalance(0L);
						sellerMoney.setFreezeAmount(0L);
						sellerMoney.setAvailableBalance(0L);
						sellerMoney.setShopType(ShopType.MALL);
						sellerMoney.setSellerId(0L);
						Long sellerMoneyId = sellerMoneyDAO.addSellerMoney(sellerMoney);
						sellerMoney.setId(sellerMoneyId);
					}
					
					boolean needRecord = true;
					if(null!=transLogDTO.getOrderSn()){
//						SellerTransLogDO  translogDO = sellerTransLogDAO.getTransLogByOrderIdAndType(transLogDTO.getOrderSn(), transLogDTO.getType());
						SellerTransLogDO translogDO = sellerTransLogDAO.getSingleTransLog(transLogDTO.getOrderSn(), 
								transLogDTO.getType(), 3);
						if(null!=translogDO)
							needRecord = false;
					}
					if(needRecord){
						modifyBalance(sellerMoney, transLogDTO.getFundInAmount(), transLogDTO.getFundOutAmount(),
								transLogDTO.getFreezeAmount());
						if (StringUtils.isBlank(transLogDTO.getOrderSn())) {
							transLogDTO.setFundOutAmount(transLogDTO.getFreezeAmount());
						}
						SellerTransLogDO sellerTransLog = new SellerTransLogDO();
						sellerTransLog.setBizCode(transLogDTO.getBizCode());
						sellerTransLog.setSellerId(transLogDTO.getSellerId());
						sellerTransLog.setOrderId(transLogDTO.getOrderId());
						sellerTransLog.setOrderSn(transLogDTO.getOrderSn());
						sellerTransLog.setFundInAmount(transLogDTO.getFundInAmount());
						sellerTransLog.setFundOutAmount(transLogDTO.getFundOutAmount());
						sellerTransLog.setType(transLogDTO.getType());
						sellerTransLog.setWithdrawId(transLogDTO.getWithdrawId());
						sellerTransLog.setLastAmount(sellerMoney.getAvailableBalance());
						sellerTransLog.setSettlementMark(EnumSettlementMark.toMap().get(transLogDTO.getSettlementMark()));
						sellerTransLog.setPaymentId(transLogDTO.getPaymentId());
						sellerTransLog.setUserId(transLogDTO.getUserId());
						sellerTransLog.setItemSkuId(transLogDTO.getItemSkuId());
						sellerTransLog.setShopType(ShopType.MALL);
//						sellerTransLog.setMallMark(1);
						printIntoService(log, "addSellerTransLog", sellerTransLog, "");

						sellerTransLogDAO.addSellerTransLog(sellerTransLog);
					}
					
					
					return true;
				}catch(Exception e){
					log.error("recordMallTransLog error",e);
					status.setRollbackOnly();
					return false;
				}
				
			}
			
		});

			
		
		
		
		
	}

	@Override
	public Boolean processRefuseWithdraw(final WithdrawDTO withdrawDTO) {
		// TODO Auto-generated method stub
		printIntoService(log, "processRefuseWithdraw", withdrawDTO, "");
		return transactionTemplate.execute(new TransactionCallback<Boolean>() {

			@Override
			public Boolean doInTransaction(TransactionStatus status) {
				try{
					WithdrawInfoDO withdrawInfoDO = withdrawInfoDAO.getWithdrawInfoById(withdrawDTO.getId());
					
					if(withdrawInfoDO.getStatus().equals(EnumWithdrawStatus.REFUSE.getCode())){
						return true;
					}
					
					   withdrawInfoDO.setGmtOperate(new Date());
				        withdrawInfoDO.setRefuseReason(withdrawDTO.getRefuseReason());
				        withdrawInfoDO.setStatus(EnumWithdrawStatus.REFUSE.getCode());
				        withdrawInfoDO.setAuditStatus(WithdrawAuditStatus.REFUSE);
				        int updateResult = withdrawInfoDAO.updateWithdrawInfo(withdrawInfoDO);
				        if(updateResult<=0){
				        	throw new TradeException(" update withdrawInfo error ");
				        }
				        SellerTransLogDO sellerTransLog =   sellerTransLogDAO.getTranslogByWithdrawId(withdrawInfoDO.getId());
				        if(null==sellerTransLog){
				        	throw new TradeException(" sellerTransLog is null ");
				        }
				        sellerTransLog.setCancelMark(1);
				        sellerTransLogDAO.updateById(sellerTransLog);
				        
				        SellerMoneyDO  sellerMoney = null;
				        if( null!= withdrawInfoDO.getSellerId() ){
				        	sellerMoney = sellerMoneyDAO.getSellerMoneyBySellerId(withdrawInfoDO.getSellerId());
				        }else{
				        	sellerMoney = sellerMoneyDAO.getSellerMoney(withdrawInfoDO.getBizCode(), ShopType.MALL);
				        }
				        if(null==sellerMoney)
				        	throw new TradeException("sellerMoney is null");
				        //TODO 金额判断
				        
				        sellerMoney.setFreezeAmount(sellerMoney.getFreezeAmount() - sellerTransLog.getFundOutAmount());
				        sellerMoney.setCurrentBalance(sellerMoney.getCurrentBalance() + sellerTransLog.getFundOutAmount());
				        sellerMoney.setAvailableBalance(sellerMoney.getAvailableBalance() + sellerTransLog.getFundOutAmount());
				        
				        sellerMoneyDAO.updateSellerMoney(sellerMoney);
				        
				        return true;
				}catch(Exception e){
					log.error("processRefuseWithdraw error",e);
					status.setRollbackOnly();
					return false;
				}
			}
			
		});
	}

	@Override
	public boolean recordTransLogForForzen(final ApplyFrozenDTO frozenDTO) {
		printIntoService(log, "recordTransLogForForzen", frozenDTO, "");
		return transactionTemplate.execute(new TransactionCallback<Boolean>() {

			@Override
			public Boolean doInTransaction(TransactionStatus status) {
				try{
					  SellerMoneyDO  sellerMoney = null;
				        if( null!= frozenDTO.getSellerId() ){
				        	sellerMoney = sellerMoneyDAO.getSellerMoneyBySellerId(frozenDTO.getSellerId());
				        }
				        if( null==sellerMoney ){
				        	throw new TradeException(" frozen account is null");
				        }
				        //TODO 金额判断
				        if(sellerMoney.getAvailableBalance()<frozenDTO.getAmount())
				        	throw new TradeException(" 冻结金额不能大于可用金额 ");
				        
				        modifyBalance( sellerMoney,0L,0L,frozenDTO.getAmount() );
				        
				        //记录 translog
				        SellerTransLogDO sellerTransLogDO = new SellerTransLogDO();
				        sellerTransLogDO.setBizCode(frozenDTO.getBizCode());
				        sellerTransLogDO.setSellerId(frozenDTO.getSellerId());
				        
				        sellerTransLogDO.setShopType(ShopType.SUB_SHOP);
				        sellerTransLogDO.setFundInAmount(0L);
				        sellerTransLogDO.setFundOutAmount(frozenDTO.getAmount());
				        sellerTransLogDO.setType(frozenDTO.getType()+""); //
				        sellerTransLogDO.setLastAmount(sellerMoney.getAvailableBalance());
				        sellerTransLogDO.setOrderSn(DateUtil.convertDateToString("yyyyMMddHHmmssS",new Date()));
				        sellerTransLogDO.setMemo(frozenDTO.getReason());
				        sellerTransLogDO.setCancelMark(0);
				        sellerTransLogDO.setPaymentId(1);
				        //TODO
				        sellerTransLogDO.setSettlementMark(0+"");
				        //TODO ..
				        sellerTransLogDAO.addSellerTransLog(sellerTransLogDO);
				}catch(Exception e){
					log.error("recordTransLogForForzen error",e);
					return false;
				}
				return true;
			}
			
		});
	}

	@Override
	public Boolean batchProcessAuditWithdraw(String bizCode,final List<WithdrawDTO> withdrawDTOs) throws TradeException{
		// TODO Auto-generated method stub
		printIntoService(log, bizCode+" batchProcessAuditWithdraw params", withdrawDTOs, "");
		
		final SellerMoneyDO  sellerMoney = sellerMoneyDAO.getSellerMoney(bizCode, ShopType.MALL);
	        	
	    if(null==sellerMoney){
	    	throw new TradeException(bizCode +"sellerMoney is null");
	    }
		
	    
		String processResult =  transactionTemplate.execute(new TransactionCallback<String>() {

			@Override
			public String doInTransaction(TransactionStatus status) {
				try{
					for(WithdrawDTO withdrawDTO:withdrawDTOs){
						WithdrawInfoDO withdrawInfoDO = withdrawInfoDAO.getWithdrawInfoById(withdrawDTO.getId());
						if(null==withdrawInfoDO){
							throw new Exception(withdrawDTO.getId() +"withdrawInfo is null");
						}
						
						if(withdrawDTO.getAuditStatus()==2){
							withdrawInfoDO.setAuditStatus(2);
							if(withdrawInfoDO.getOperByMockuai()==0){
								withdrawInfoDO.setStatus(EnumWithdrawStatus.FINISHED.getCode());
								
								SellerTransLogDO sellerTransLogDO = sellerTransLogDAO.getTranslogByWithdrawId(withdrawDTO.getId());
								if(sellerTransLogDO == null){
									throw new Exception(withdrawDTO.getId() +"sellerTransLog is null");
								}
								sellerTransLogDO.setSettlementMark(EnumSettlementMark.SETTLEMENTED.getCode());
								sellerTransLogDAO.updateById(sellerTransLogDO);
								
								//TODO ....少一条记录
								
								if(sellerTransLogDO.getSellerId()!=null){
									SellerMoneyDO  subSellerMoney =	sellerMoneyDAO.getSellerMoneyBySellerId(sellerTransLogDO.getSellerId());
									subSellerMoney.setFreezeAmount(subSellerMoney.getFreezeAmount() - sellerTransLogDO.getFundOutAmount());
									subSellerMoney.setCurrentBalance(subSellerMoney.getCurrentBalance() - sellerTransLogDO.getFundOutAmount());
									subSellerMoney.setAvailableBalance(subSellerMoney.getCurrentBalance() - subSellerMoney.getFreezeAmount());
							        
							        sellerMoneyDAO.updateSellerMoney(subSellerMoney);
								}
								
							}
							withdrawInfoDAO.updateWithdrawInfo(withdrawInfoDO);
						}
						
					}
					return "";
				}catch(Exception e){
					log.error("batchProcessAuditWithdraw inner process error",e);
					return e.getMessage();
				}
				
			}
			
		});
		
		
		if(StringUtil.isNotBlank(processResult)){
			throw new TradeException(processResult);
		}
		return true;
		
	}
	
	
	private void recordShopDepositTranslog(ShopDepositDTO shopDepositDTO)throws Exception{
		SellerMoneyDO  shopSellerMoney =	sellerMoneyDAO.getSellerMoneyBySellerId(shopDepositDTO.getSellerId());
		if(null==shopSellerMoney){
			shopSellerMoney = new SellerMoneyDO();
			shopSellerMoney.setSellerId(shopDepositDTO.getSellerId());
			shopSellerMoney.setBizCode(shopDepositDTO.getBizCode());
			shopSellerMoney.setCurrentBalance(0L);
			shopSellerMoney.setFreezeAmount(0L);
			shopSellerMoney.setAvailableBalance(0L);
			shopSellerMoney.setShopType(ShopType.SUB_SHOP);
			Long id = sellerMoneyDAO.addSellerMoney(shopSellerMoney);
			shopSellerMoney.setId(id);
		}
		//先加钱
		modifyBalance(shopSellerMoney,shopDepositDTO.getAmount(),0L,0L);
		
		//TODO ...少了条保证金入资金的流水
		
		//冻结
		modifyBalance(shopSellerMoney,0L,0L,shopDepositDTO.getAmount());
		SellerTransLogDO transLogDO = new SellerTransLogDO();
		transLogDO.setBizCode(shopSellerMoney.getBizCode());
		transLogDO.setSellerId(shopSellerMoney.getSellerId());
		transLogDO.setLastAmount(shopSellerMoney.getAvailableBalance());
		transLogDO.setSettlementMark(EnumSettlementMark.toMap().get("Y"));
		transLogDO.setPaymentId(1);
		transLogDO.setShopType(2);
		transLogDO.setFundInAmount(0L);
		transLogDO.setFundOutAmount(shopDepositDTO.getAmount());
		transLogDO.setType(EnumInOutMoneyType.DEPOSIT.getCode());
		transLogDO.setOrderSn(DateUtil.convertDateToString("yyyyMMddHHmmssS",new Date()));
		printIntoService(log, "add shop add deposit transLog", transLogDO, "");
		
		sellerTransLogDAO.addSellerTransLog(transLogDO);
		
	}
	
	private void recordMallTranslogForShopDeposit(ShopDepositDTO shopDepositDTO)throws Exception{
		SellerMoneyDO  mallSellerMoney = sellerMoneyDAO.getSellerMoney(shopDepositDTO.getBizCode(), ShopType.MALL);
		if(null==mallSellerMoney){
//			throw new TradeException("mall selley money record is null");
			mallSellerMoney = new SellerMoneyDO();
			mallSellerMoney.setBizCode(shopDepositDTO.getBizCode());
			mallSellerMoney.setCurrentBalance(0L);
			mallSellerMoney.setFreezeAmount(0L);
			mallSellerMoney.setAvailableBalance(0L);
			mallSellerMoney.setShopType(ShopType.MALL);
			Long id = sellerMoneyDAO.addSellerMoney(mallSellerMoney);
			mallSellerMoney.setId(id);
			
		}
		modifyBalance(mallSellerMoney,shopDepositDTO.getAmount(),0L,0L);
		SellerTransLogDO transLogDO = new SellerTransLogDO();
		transLogDO.setBizCode(mallSellerMoney.getBizCode());
		transLogDO.setSellerId(shopDepositDTO.getSellerId());
		transLogDO.setFundInAmount(shopDepositDTO.getAmount());
		transLogDO.setFundOutAmount(0L);
		transLogDO.setLastAmount(mallSellerMoney.getAvailableBalance());
		transLogDO.setSettlementMark(EnumSettlementMark.toMap().get("Y"));
		transLogDO.setPaymentId(1);
		transLogDO.setShopType(ShopType.MALL);
		transLogDO.setType(EnumInOutMoneyType.DEPOSIT.getCode());
		transLogDO.setOrderSn(DateUtil.convertDateToString("yyyyMMddHHmmssS",new Date()));
		printIntoService(log, "add shop add deposit transLog", transLogDO, "");
		sellerTransLogDAO.addSellerTransLog(transLogDO);
	}

	@Override
	public boolean recordAddShopDepositTransLog(final ShopDepositDTO shopDepositDTO) throws TradeException{
		printIntoService(log, " recordAddShopDepositTransLog params", shopDepositDTO, "");
		String processResult =  transactionTemplate.execute(new TransactionCallback<String>() {

			@Override
			public String doInTransaction(TransactionStatus status) {
				
				try{
					recordShopDepositTranslog(shopDepositDTO);
					
					recordMallTranslogForShopDeposit(shopDepositDTO);
					
					
				}catch(Exception e){
					return e.getMessage();
				}
				return null;
			}
			
		});
		
		if(StringUtil.isNotBlank(processResult)){
			throw new TradeException(processResult);
		}
		return true;
	}

	
	

}
