package com.mockuai.marketingcenter.core.service.action;

import com.mockuai.marketingcenter.common.api.MarketingResponse;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.marketingcenter.core.service.RequestContext;
import com.mockuai.marketingcenter.core.util.JsonUtil;
import com.mockuai.marketingcenter.core.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;

@Service
public abstract class TransAction implements Action {

    private static final Logger log = LoggerFactory.getLogger(TransAction.class);

    @Resource
    TransactionTemplate transactionTemplate;

    protected abstract MarketingResponse doTransaction(RequestContext context)
            throws MarketingException;

    public MarketingResponse execute(final RequestContext context) throws MarketingException {

        return (MarketingResponse) this.transactionTemplate.execute(new TransactionCallback() {

            public Object doInTransaction(TransactionStatus status) {
                try {
                    MarketingResponse marketingResponse = TransAction.this.doTransaction(context);
                    if (marketingResponse.isSuccess() == false) {
                        log.error("Action [{}] is unsuccess, {}",
                                context.getRequest().getCommand(), marketingResponse.getMessage());
                        status.setRollbackOnly();
                    }
                    log.debug("------------------------------------------------------");
                    log.debug("{}, {}", context.getRequest().getCommand(), JsonUtil.toJson(marketingResponse));
                    log.debug("------------------------------------------------------");
                    return marketingResponse;
                } catch (MarketingException e) {
                    log.error(e.getMessage(), e);
                    status.setRollbackOnly();
                    return ResponseUtil.getResponse(e.getCode(), e.getMessage());
                }
            }

        });
    }
}