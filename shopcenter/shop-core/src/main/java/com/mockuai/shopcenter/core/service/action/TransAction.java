package com.mockuai.shopcenter.core.service.action;

import com.mockuai.shopcenter.api.ShopResponse;
import com.mockuai.shopcenter.constant.ResponseCode;
import com.mockuai.shopcenter.core.exception.ShopException;
import com.mockuai.shopcenter.core.service.RequestContext;
import com.mockuai.shopcenter.core.util.ResponseUtil;
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


    protected abstract ShopResponse doTransaction(RequestContext context)
            throws ShopException;


    public ShopResponse execute(final RequestContext context) throws ShopException {

        return (ShopResponse) this.transactionTemplate.execute(new TransactionCallback() {

            public Object doInTransaction(TransactionStatus status) {
                try {
                    ShopResponse itemResponse = TransAction.this.doTransaction(context);
                    if(itemResponse.isSuccess() == false){
                        log.error("", itemResponse.getMessage());
                        status.setRollbackOnly();
                    }
                    return itemResponse;
                } catch (ShopException e) {
                    log.error(e.getMessage(), e);
                    status.setRollbackOnly();
                    return ResponseUtil.getErrorResponse(e.getCode(), e.getMessage());
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                    status.setRollbackOnly();
                    return ResponseUtil.getErrorResponse(ResponseCode.SYS_E_DEFAULT_ERROR, e.getMessage());
                }

            }

        });

    }

}