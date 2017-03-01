package com.mockuai.giftscenter.core.service.action;

import com.mockuai.giftscenter.common.api.GiftsResponse;
import com.mockuai.giftscenter.core.exception.GiftsException;
import com.mockuai.giftscenter.core.service.RequestContext;
import com.mockuai.giftscenter.core.util.ResponseUtil;

import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;

@Service
public abstract class TransAction extends BaseAction {

//    private static final Logger log = LoggerFactory.getLogger(TransAction.class);

    @Resource
    TransactionTemplate transactionTemplate;

    protected abstract GiftsResponse doTransaction(RequestContext context)
            throws GiftsException;

    public GiftsResponse execute(final RequestContext context) throws GiftsException {

        return (GiftsResponse) this.transactionTemplate.execute(new TransactionCallback() {

            public Object doInTransaction(TransactionStatus status) {
                try {
                    GiftsResponse marketingResponse = TransAction.this.doTransaction(context);
                    if (marketingResponse.isSuccess() == false) {
//                        log.error("Action [{}] is unsuccess, {}",
//                                context.getRequest().getCommand(), marketingResponse.getMessage());
                        status.setRollbackOnly();
                    }
//                    log.debug("-------------------------------------------------------------");
//                    log.debug("{}, {}", context.getRequest().getCommand(), JsonUtil.toJson(marketingResponse));
//                    log.debug("-------------------------------------------------------------");
                    return marketingResponse;
                } catch (GiftsException e) {
//                    log.error(e.getMessage(), e);
                    status.setRollbackOnly();
                    return ResponseUtil.getResponse(e.getCode(), e.getMessage());
                }
            }

        });
    }
}