package com.mockuai.usercenter.core.service.action;

import javax.annotation.Resource;

import com.mockuai.usercenter.common.constant.ResponseCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.mockuai.usercenter.common.api.UserResponse;
import com.mockuai.usercenter.core.exception.UserException;
import com.mockuai.usercenter.core.service.RequestContext;

@Service
public abstract class TransAction implements Action {
	private static final Logger log = LoggerFactory.getLogger(TransAction.class);

	@Resource
	TransactionTemplate transactionTemplate;

	protected abstract UserResponse doTransaction(RequestContext context)
			throws UserException;

	@SuppressWarnings("unchecked")
	@Override
	public UserResponse execute(final RequestContext context)
			throws UserException {
		return (UserResponse) transactionTemplate
				.execute(new TransactionCallback() {
					@Override
					public Object doInTransaction(TransactionStatus status) {
						try {
							UserResponse userResponse = doTransaction(context);
							if(userResponse.isSuccess() == false){
								log.error("errorCode:{}, errorMsg:{}",
										userResponse.getCode(), userResponse.getMessage());
								status.setRollbackOnly();
							}
							return userResponse;
						} catch (UserException e) {
							log.error(e.getMessage(), e);
							status.setRollbackOnly();
							return new UserResponse(e.getResponseCode(),
									e.getMessage());
						} catch (Exception e){
							log.error(e.getMessage(), e);
							status.setRollbackOnly();
							return new UserResponse(ResponseCode.SYS_E_SERVICE_EXCEPTION);
						}
					}
				});
	}
}
