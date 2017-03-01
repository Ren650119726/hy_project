package com.mockuai.suppliercenter.core.service.action;

import com.mockuai.suppliercenter.common.api.SupplierResponse;
import com.mockuai.suppliercenter.common.constant.ResponseCode;
import com.mockuai.suppliercenter.core.exception.SupplierException;
import com.mockuai.suppliercenter.core.service.RequestContext;
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

    protected abstract SupplierResponse doTransaction(RequestContext context)
            throws SupplierException;

    @SuppressWarnings("unchecked")
    @Override
    public SupplierResponse execute(final RequestContext context)
            throws SupplierException {
        return (SupplierResponse) transactionTemplate
                .execute(new TransactionCallback() {
                    @Override
                    public Object doInTransaction(TransactionStatus status) {
                        try {
                            SupplierResponse supplierResponse = doTransaction(context);
                            if (supplierResponse.isSuccess() == false) {
                                log.error("errorCode:{}, errorMsg:{}",
                                        supplierResponse.getCode(), supplierResponse.getMessage());
                                status.setRollbackOnly();
                            }
                            return supplierResponse;
                        } catch (SupplierException e) {
                            log.error(e.getMessage(), e);
                            status.setRollbackOnly();
                            return new SupplierResponse(e.getResponseCode(),
                                    e.getMessage());
                        } catch (Exception e) {
                            log.error(e.getMessage(), e);
                            status.setRollbackOnly();
                            return new SupplierResponse(ResponseCode.SYS_E_SERVICE_EXCEPTION);
                        }
                    }
                });
    }
}
