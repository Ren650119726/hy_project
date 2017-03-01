package com.mockuai.distributioncenter.core.service.action;

import com.mockuai.distributioncenter.common.api.DistributionResponse;
import com.mockuai.distributioncenter.core.service.RequestContext;
import com.mockuai.distributioncenter.core.exception.DistributionException;
import com.mockuai.distributioncenter.core.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;

/**
 * Created by duke on 15/10/28.
 */
public abstract class TransAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(TransAction.class);

    @Autowired
    TransactionTemplate transactionTemplate;

    protected abstract DistributionResponse doTransaction(RequestContext context)
            throws DistributionException;

    public DistributionResponse execute(final RequestContext context) throws DistributionException {

        return (DistributionResponse) this.transactionTemplate.execute(new TransactionCallback() {

            public Object doInTransaction(TransactionStatus status) {
                try {
                    DistributionResponse distributionResponse = TransAction.this.doTransaction(context);
                    if (distributionResponse.isSuccess() == false) {
                        log.error("Action [{}] is unsuccess, {}",
                                context.getRequest().getCommand(), distributionResponse.getMessage());
                        status.setRollbackOnly();
                    }
                    return distributionResponse;
                } catch (DistributionException e) {
                    log.error(e.getMessage(), e);
                    status.setRollbackOnly();
                    return ResponseUtil.getResponse(e.getCode(), e.getMessage());
                }
            }

        });
    }
}
