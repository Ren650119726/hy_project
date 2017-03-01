/*package com.mockuai.usercenter.core.manager.impl;

import com.mockuai.customer.client.CustomerClient;
import com.mockuai.customer.common.api.CustomerResponse;
import com.mockuai.customer.common.dto.MemberDTO;
import com.mockuai.usercenter.common.constant.ResponseCode;
import com.mockuai.usercenter.core.exception.UserException;
import com.mockuai.usercenter.core.manager.CustomerManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

*//**
 * Created by duke on 15/12/17.
 *//*
@Service
public class CustomerManagerImpl implements CustomerManager {
    @Resource
    private CustomerClient customerClient;

    @Override
    public MemberDTO getMemberByUserId(Long userId, String appKey) throws UserException {
        CustomerResponse<MemberDTO> response = customerClient.getMemberByUserId(userId, appKey);
        if (!response.isSuccess()) {
            throw new UserException(ResponseCode.SYS_E_SERVICE_EXCEPTION, response.getMessage());
        } else {
            return response.getModule();
        }
    }
}
*/