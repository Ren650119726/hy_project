package com.mockuai.tradecenter.core.service.action.order;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.mockuai.tradecenter.common.api.Request;
import com.mockuai.tradecenter.common.api.TradeResponse;
import com.mockuai.tradecenter.common.constant.ActionEnum;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.core.domain.OrderDO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.OrderManager;
import com.mockuai.tradecenter.core.manager.UserManager;
import com.mockuai.tradecenter.core.service.RequestContext;
import com.mockuai.tradecenter.core.service.ResponseUtils;
import com.mockuai.tradecenter.core.service.action.Action;

/**
 * 标记订单为退款 只修改状态 已经线下或商户退款了。
 * 
 * @author hzmk
 *
 */
public class RefundOrder implements Action {

	private static final Logger log = LoggerFactory.getLogger(RefundOrder.class);

	@Resource
	private OrderManager orderManager;

	@Resource
	private TransactionTemplate transactionTemplate;

	@Resource
	private UserManager userManagerImpl;
	@SuppressWarnings("unchecked")
	@Override
	public TradeResponse execute(RequestContext context) throws TradeException {
		Request request = context.getRequest();
		TradeResponse<Boolean> response = null;
		if (request.getParam("userId") == null) {
			log.error("userId is null ");
			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING, "userId is null");
		} else if (request.getParam("orderId") == null) {
			log.error("orderId is null ");
			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING, "orderId is null");
		}
		final long userId = (Long) request.getParam("userId");
		final long orderId = (Long) request.getParam("orderId");
		final String appKey = (String) context.get("appKey");
		OrderDO order;
		try {
			order = this.orderManager.getActiveOrder(orderId, userId);
		} catch (TradeException e) {
			log.error("db error :", e);
			return ResponseUtils.getFailResponse(ResponseCode.SYS_E_DATABASE_ERROR);
		}
		if (order == null) {
			log.error("order doesn't exist orderId:" + orderId + " userId:" + userId);
			return ResponseUtils.getFailResponse(ResponseCode.BIZ_E_RECORD_NOT_EXIST, "order doesn't exist");
		}
		
		final OrderDO finalOrder = order;

		Boolean result = false;

		result = (Boolean) this.transactionTemplate.execute(new TransactionCallback() {

			@Override
			public Boolean doInTransaction(TransactionStatus status) {
				Boolean processStatus = Boolean.TRUE;
				try {

					orderManager.markRefund(userId, orderId);

					// TODO ..... 客户关系管理、、、、

					userManagerImpl.addSellerUserRelate(userId, finalOrder.getSellerId(), finalOrder.getId(),
							"refund", finalOrder.getTotalAmount(), appKey);

				} catch (Exception ex) {
					log.error("==============mark refund Order invoke error================", ex);
					processStatus = Boolean.FALSE;
					status.setRollbackOnly();
				}
				return processStatus;
			}
		});

		if (result) {
			return ResponseUtils.getSuccessResponse(true);
		} else {
			return ResponseUtils.getFailResponse(ResponseCode.SYS_E_SERVICE_EXCEPTION, "order mark refund failed");
		}
	}

	@Override
	public String getName() {
		return ActionEnum.MARK_REFUND.getActionName();
	}

}
