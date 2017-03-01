package com.mockuai.tradecenter.core.service.action;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.mockuai.tradecenter.common.api.TradeResponse;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.service.RequestContext;
import com.mockuai.tradecenter.core.service.RuleChainService;

/**
 * 
 * 顶层Action的抽象
 *
 */
public abstract class TopTradeAction implements Action {
	protected final Log log = LogFactory.getLog(this.getClass());
	@Autowired
	protected TransactionTemplate transactionTemplate;

	public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
		this.transactionTemplate = transactionTemplate;
	}
	
	/**
	 * 返回当前执行的服务的名称
	 * @return
	 */
	public abstract String getServiceName();
	
	/**
	 * 取得当前服务的逻辑执行链
	 * @return
	 */
	public abstract RuleChainService getNextRuleChain();
	
	/**
	 * 
	 * @param context
	 * @return
	 * @throws TradeException
	 */
	public abstract TradeResponse beginExecute(RequestContext context) throws TradeException;

	@Override
	public TradeResponse<?> execute(final RequestContext context) throws TradeException {
		this.log.info("###################################Start Execute Service " + this.getServiceName()
				+ ",Thread ID=" + Thread.currentThread().getId() + "###################################");
		this.log.info("$$$$$$$$$$$$=" + context.toString());
		TradeResponse<?> result = new TradeResponse(ResponseCode.RESPONSE_SUCCESS);
		try {
			result = this.transactionTemplate.execute(new TransactionCallback<TradeResponse<?>>() {
				TradeResponse<?> result = new TradeResponse(ResponseCode.RESPONSE_SUCCESS);

				@Override
				public TradeResponse<?> doInTransaction(TransactionStatus status) {
					try {
						result = beginExecute(context);
						// 如果开始就不成功、只能response了
						if(result.isSuccess()==false){
							return result;
						}
						//如果配置了 next 、就next
						if (getNextRuleChain() != null) {
							result = getNextRuleChain().executeRuleChain(context);
						} else {
							log.error("Current Service Not Has Rule Chain");
						}
					} catch (Exception ex) {
						log.error("TopTradeAction execute inner doInTransaction",ex);
						result.setCode(ResponseCode.SYS_E_SERVICE_EXCEPTION.getCode());
						result.setMessage(ex.getMessage());
						status.setRollbackOnly();
					}

					return result;
				}

			});
		} catch (Exception ex) {
			log.error("TopTradeAction execute error",ex);
			result.setCode(ResponseCode.SYS_E_SERVICE_EXCEPTION.getCode());
			result.setMessage(ResponseCode.SYS_E_SERVICE_EXCEPTION.getComment());
		}
		return result;
	}

	

}
