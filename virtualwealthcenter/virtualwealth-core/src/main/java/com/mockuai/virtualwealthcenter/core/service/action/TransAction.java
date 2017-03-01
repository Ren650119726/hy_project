package com.mockuai.virtualwealthcenter.core.service.action;

import com.mockuai.virtualwealthcenter.common.api.VirtualWealthResponse;
import com.mockuai.virtualwealthcenter.core.exception.VirtualWealthException;
import com.mockuai.virtualwealthcenter.core.service.RequestContext;
import com.mockuai.virtualwealthcenter.core.util.JsonUtil;
import com.mockuai.virtualwealthcenter.core.util.ResponseUtil;
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

    protected abstract VirtualWealthResponse doTransaction(RequestContext context)
            throws VirtualWealthException;

    public VirtualWealthResponse execute(final RequestContext context) throws VirtualWealthException {

        return (VirtualWealthResponse) this.transactionTemplate.execute(new TransactionCallback() {

            public Object doInTransaction(TransactionStatus status) {
                try {
                    VirtualWealthResponse virtualWealthResponse = TransAction.this.doTransaction(context);
                    if (virtualWealthResponse.isSuccess() == false) {
                        log.error("Action [{}] is unsuccess, {}",
                                context.getRequest().getCommand(), virtualWealthResponse.getMessage());
                        status.setRollbackOnly();
                    }
                    log.debug("------------------------------------------------------");
                    log.debug("{}, {}", context.getRequest().getCommand(), JsonUtil.toJson(virtualWealthResponse));
                    log.debug("------------------------------------------------------");
                    return virtualWealthResponse;
                } catch (VirtualWealthException e) {
                    log.error(e.getMessage(), e);
                    status.setRollbackOnly();
                    return ResponseUtil.getResponse(e.getCode(), e.getMessage());
                }
            }

        });
    }
}