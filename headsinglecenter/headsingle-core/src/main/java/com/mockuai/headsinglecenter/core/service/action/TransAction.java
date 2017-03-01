package com.mockuai.headsinglecenter.core.service.action;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.mockuai.headsinglecenter.common.api.HeadSingleResponse;
import com.mockuai.headsinglecenter.core.exception.HeadSingleException;
import com.mockuai.headsinglecenter.core.service.RequestContext;
import com.mockuai.headsinglecenter.core.util.ResponseUtil;

@SuppressWarnings("rawtypes")
@Service
public abstract class TransAction implements Action {
    @Resource
    TransactionTemplate transactionTemplate;

	protected abstract HeadSingleResponse doTransaction(RequestContext context)
            throws HeadSingleException;

	@SuppressWarnings("unchecked")
	public HeadSingleResponse execute(final RequestContext context) throws HeadSingleException {

        return (HeadSingleResponse) this.transactionTemplate.execute(new TransactionCallback() {

            public Object doInTransaction(TransactionStatus status) {
                try {
                	HeadSingleResponse marketingResponse = TransAction.this.doTransaction(context);
                    if (marketingResponse.isSuccess() == false) {
                        status.setRollbackOnly();
                    }
                    return marketingResponse;
                } catch (HeadSingleException e) {                   
                    status.setRollbackOnly();
                    return ResponseUtil.getResponse(e.getCode(), e.getMessage());
                }
            }

        });
    }
}