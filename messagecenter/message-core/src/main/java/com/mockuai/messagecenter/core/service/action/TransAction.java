package com.mockuai.messagecenter.core.service.action;

import javax.annotation.Resource;

import com.mockuai.messagecenter.common.api.MessageResponse;
import com.mockuai.messagecenter.common.constant.ResponseCode;
import com.mockuai.messagecenter.core.exception.MessageException;
import com.mockuai.messagecenter.core.service.RequestContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

@Service
public abstract class TransAction implements Action {
	private static final Logger log = LoggerFactory.getLogger(TransAction.class);

	@Resource
	TransactionTemplate transactionTemplate;

	protected abstract MessageResponse doTransaction(RequestContext context)
			throws MessageException;

	@SuppressWarnings("unchecked")
	@Override
	public MessageResponse execute(final RequestContext context)
			throws MessageException {
		return (MessageResponse) transactionTemplate
				.execute(new TransactionCallback() {
					@Override
					public Object doInTransaction(TransactionStatus status) {
						try {
							MessageResponse userResponse = doTransaction(context);
							if(userResponse.isSuccess() == false){
								log.error("errorCode:{}, errorMsg:{}",
										userResponse.getCode(), userResponse.getMessage());
								status.setRollbackOnly();
							}
							return userResponse;
						} catch (MessageException e) {
							log.error(e.getMessage(), e);
							status.setRollbackOnly();
							return new MessageResponse(e.getResponseCode(),
									e.getMessage());
						} catch (Exception e){
							log.error(e.getMessage(), e);
							status.setRollbackOnly();
							return new MessageResponse(ResponseCode.SYS_E_SERVICE_EXCEPTION);
						}
					}
				});
	}
}
