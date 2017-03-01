package com.mockuai.tradecenter.core.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.mockuai.tradecenter.common.domain.settlement.ProcessWithdrawDTO;
import com.mockuai.tradecenter.common.domain.settlement.WithdrawDTO;
import com.mockuai.tradecenter.common.domain.settlement.WithdrawQTO;
import com.mockuai.tradecenter.common.enums.EnumWithdrawStatus;
import com.mockuai.tradecenter.core.dao.WithdrawBatchDAO;
import com.mockuai.tradecenter.core.dao.WithdrawInfoDAO;
import com.mockuai.tradecenter.core.domain.WithdrawBatchDO;
import com.mockuai.tradecenter.core.domain.WithdrawInfoDO;
import com.mockuai.tradecenter.core.manager.BaseService;
import com.mockuai.tradecenter.core.manager.ShopManager;
import com.mockuai.tradecenter.core.manager.WithdrawManager;
import com.mockuai.tradecenter.core.util.DozerBeanService;

public class WithdrawManagerImpl extends BaseService implements WithdrawManager {
	private static final Logger log = LoggerFactory.getLogger(WithdrawManagerImpl.class);

	@Autowired
	private WithdrawInfoDAO withdrawDAO;

	@Resource
	private DozerBeanService dozerBeanService;
	
	@Resource
	private WithdrawBatchDAO withdrawBatchDAO;
	
	@Resource
	private TransactionTemplate transactionTemplate;
	
	@Resource
	ShopManager shopManager;
	
	@Override
	public List<WithdrawDTO> queryWithdraw(WithdrawQTO query) {
		printIntoService(log, "queryWithdraw", query, "");
		if (null == query.getOffset())
			query.setOffset(0);
		if (null == query.getCount())
			query.setCount(20);
		List<WithdrawDTO> withDTOList = dozerBeanService.coverList(withdrawDAO.queryWithdrawInfo(query),WithdrawDTO.class);
		if(null!=withDTOList&&withDTOList.size()>0){
			for(WithdrawDTO dto:withDTOList){
				dto.setStatus(EnumWithdrawStatus.getByCode(dto.getStatus()).getOldCode());
//				if(dto.gets)
			}
				
		}
		return withDTOList;
	}

	@Override
	public Long getQueryCount(WithdrawQTO query) {
		printIntoService(log, "getQueryCount", query, "");
		return withdrawDAO.getQueryCount(query);
	}

	@Override
	public Boolean processWithdraw(final ProcessWithdrawDTO dto) {
		printIntoService(log, "processWithdraw", dto, "");
		return transactionTemplate.execute(new TransactionCallback<Boolean>() {
			@Override
			public Boolean doInTransaction(TransactionStatus status) {
				try{
					WithdrawBatchDO withdrawBatch = new WithdrawBatchDO();
					withdrawBatch.setChannel(dto.getChannel());
					withdrawBatch.setStatus("PROCESSING");
					withdrawBatch.setBatchNo(dto.getBatchNo());
					Long batchId = withdrawBatchDAO.addWithdrawBatch(withdrawBatch);
					
					for(Long id:dto.getWithdrawId()){
						WithdrawInfoDO withdrawInfo = new WithdrawInfoDO();
						withdrawInfo.setId(id);
						withdrawInfo.setWithdrawBatchId(batchId);
						withdrawInfo.setStatus("PROCESSING");
						withdrawDAO.updateWithdrawInfo(withdrawInfo);
					}
				}catch(Exception e){
					log.error("processWithdraw error",e);
					status.setRollbackOnly();
					return false;
				}
				
				return true;
			}
		});
		
	}

//	@Override
//	public Boolean processWithdrawNotify(NotifyWithdrawResultDTO dto) throws TradeException{
//		printIntoService(log, "processWithdrawNotify", dto, "");
//		
//		final WithdrawBatchDO batchDO = withdrawBatchDAO.getWithdrawBatchByBatchNo(dto.getBatchNo());
//		if(null==batchDO)
//			throw new TradeException(dto.getBatchNo()+" withdrawBatch is not exist");
//		
//		return transactionTemplate.execute(new TransactionCallback<Boolean>() {
//			@Override
//			public Boolean doInTransaction(TransactionStatus status) {
//				
//				return null;
//				
//			}
//		});
//	}

}
