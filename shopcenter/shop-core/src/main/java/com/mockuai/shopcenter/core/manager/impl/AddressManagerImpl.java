package com.mockuai.shopcenter.core.manager.impl;

import com.mockuai.shopcenter.core.manager.AddressManager;
import com.mockuai.usercenter.client.ConsigneeClient;
import com.mockuai.usercenter.common.api.Response;
import com.mockuai.usercenter.common.dto.UserConsigneeDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by yindingyu on 16/1/20.
 */
@Service
public class AddressManagerImpl implements AddressManager {

    private static final Logger LOG = LoggerFactory.getLogger(AddressManagerImpl.class);

    @Resource
    private ConsigneeClient consigneeClient;

    @Override
    public UserConsigneeDTO getAddress(Long userId, Long consigneeId, String appKey) {


        Response<UserConsigneeDTO> response;
        try {

            response = consigneeClient.getConsigneeById(userId, consigneeId, appKey);

        }catch (Exception e){
            LOG.error("查询用户地址时出现问题 userId : {} consigneeId : {}  message {}",userId,consigneeId, e.getMessage());
            return null;
        }

        if(response.isSuccess()){
            return response.getModule();
        }else {
            LOG.error("查询用户地址时出现问题 userId : {} consigneeId : {} code {} message {}",userId,consigneeId, response.getCode(), response.getMessage());
            return null;
        }
    }
}
