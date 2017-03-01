package com.hanshu.employee.core.service.action;

import com.hanshu.employee.common.api.EmployeeResponse;
import com.hanshu.employee.common.constant.ResponseCode;
import com.hanshu.employee.core.exception.EmployeeException;
import com.hanshu.employee.core.service.RequestContext;
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

    protected abstract EmployeeResponse doTransaction(RequestContext context)
            throws EmployeeException;

    @SuppressWarnings("unchecked")
    @Override
    public EmployeeResponse execute(final RequestContext context)
            throws EmployeeException {
        return (EmployeeResponse) transactionTemplate
                .execute(new TransactionCallback() {
                    @Override
                    public Object doInTransaction(TransactionStatus status) {
                        try {
                            EmployeeResponse employeeResponse = doTransaction(context);
                            if (employeeResponse.isSuccess() == false) {
                                log.error("errorCode:{}, errorMsg:{}",
                                        employeeResponse.getCode(), employeeResponse.getMessage());
                                status.setRollbackOnly();
                            }
                            return employeeResponse;
                        } catch (EmployeeException e) {
                            log.error(e.getMessage(), e);
                            status.setRollbackOnly();
                            return new EmployeeResponse(e.getResponseCode(),
                                    e.getMessage());
                        } catch (Exception e) {
                            log.error(e.getMessage(), e);
                            status.setRollbackOnly();
                            return new EmployeeResponse(ResponseCode.SYS_E_SERVICE_EXCEPTION);
                        }
                    }
                });
    }
}
