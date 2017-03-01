package com.mockuai.tradecenter.core.service.action.datamigrate;

import com.mockuai.tradecenter.common.api.TradeResponse;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.service.RequestContext;
import com.mockuai.tradecenter.core.service.action.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;

/**
 * Created by zengzhangqiang on 7/20/15.
 */
public abstract  class MigrateAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(MigrateAction.class);

    @Resource
    TransactionTemplate transactionTemplate;


    protected abstract TradeResponse doTransaction(RequestContext context)
            throws TradeException;


    public TradeResponse execute(final RequestContext context) throws TradeException {

        return (TradeResponse) this.transactionTemplate.execute(new TransactionCallback() {

            public Object doInTransaction(TransactionStatus status) {
                try {
                    TradeResponse tradeResponse = MigrateAction.this.doTransaction(context);
                    if(tradeResponse.isSuccess() == false){
                        log.error("", tradeResponse.getMessage());
                        status.setRollbackOnly();
                    }
                    return tradeResponse;
                } catch (TradeException e) {
                    log.error(e.getMessage(), e);
                    status.setRollbackOnly();
                    return new TradeResponse(e.getResponseCode(), e.getMessage());
                }

            }

        });

    }

}