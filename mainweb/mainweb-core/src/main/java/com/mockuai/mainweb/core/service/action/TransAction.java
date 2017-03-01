package com.mockuai.mainweb.core.service.action;

import com.mockuai.mainweb.common.api.action.MainWebResponse;
import com.mockuai.mainweb.common.constant.ResponseCode;
import com.mockuai.mainweb.core.exception.MainWebException;
import com.mockuai.mainweb.core.util.ResponseUtil;
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


    protected abstract MainWebResponse doTransaction(RequestContext context)
            throws MainWebException;


    public MainWebResponse execute(final RequestContext context) throws MainWebException {

        return (MainWebResponse) this.transactionTemplate.execute(new TransactionCallback() {

            public Object doInTransaction(TransactionStatus status) {
                try {
                    MainWebResponse itemResponse = TransAction.this.doTransaction(context);
                    if(itemResponse.isSuccess() == false){
                        log.error("", itemResponse.getMessage());
                        status.setRollbackOnly();
                    }
                    return itemResponse;
                } catch (MainWebException e) {
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