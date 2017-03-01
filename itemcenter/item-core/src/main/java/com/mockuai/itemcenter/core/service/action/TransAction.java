package com.mockuai.itemcenter.core.service.action;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.util.ResponseUtil;
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


    protected abstract ItemResponse doTransaction(RequestContext context)
            throws ItemException;


    public ItemResponse execute(final RequestContext context) throws ItemException {

        return (ItemResponse) this.transactionTemplate.execute(new TransactionCallback() {

            public Object doInTransaction(TransactionStatus status) {
                try {
                    ItemResponse itemResponse = TransAction.this.doTransaction(context);
                    if(itemResponse.isSuccess() == false){
                        log.error("", itemResponse.getMessage());
                        status.setRollbackOnly();
                    }
                    return itemResponse;
                } catch (ItemException e) {
                    log.error(e.getMessage(), e);
                    status.setRollbackOnly();
                    return ResponseUtil.getErrorResponse(e.getCode(), e.getMessage());
                } catch (Exception e){
                    log.error(e.getMessage(), e);
                    status.setRollbackOnly();
                    return ResponseUtil.getErrorResponse(ResponseCode.SYS_E_SERVICE_EXCEPTION);
                }

            }

        });

    }

}