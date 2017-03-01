package com.mockuai.deliverycenter.core.service.action;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.mockuai.deliverycenter.common.api.DeliveryResponse;
import com.mockuai.deliverycenter.core.exception.DeliveryException;
import com.mockuai.deliverycenter.core.service.RequestContext;
import com.mockuai.deliverycenter.core.util.ResponseUtil;

@Service
public abstract class TransAction implements Action {
	@Resource
	TransactionTemplate transactionTemplate;

	protected abstract DeliveryResponse doTransaction(RequestContext context)
			throws DeliveryException;

	@SuppressWarnings("unchecked")
	@Override
	public DeliveryResponse execute(final RequestContext context)
			throws DeliveryException {
		return (DeliveryResponse) transactionTemplate
				.execute(new TransactionCallback() {
					@Override
					public Object doInTransaction(TransactionStatus status) {
						try {
							return doTransaction(context);
						} catch (DeliveryException e) {
							log.error(e.getMessage(), e);
							status.setRollbackOnly();
							return ResponseUtil.getResponse(e.getCode(),
									e.getMessage());
						}
					}
				});
	}
}
