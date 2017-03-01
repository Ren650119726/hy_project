package com.mockuai.itemcenter.core.manager.impl;

import com.mockuai.itemcenter.common.domain.dto.area.AddressDTO;
import com.mockuai.itemcenter.core.manager.AddressManager;
import com.mockuai.usercenter.client.ConsigneeClient;
import com.mockuai.usercenter.common.api.Response;
import com.mockuai.usercenter.common.dto.UserConsigneeDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 *
 */
@Service
public class AddressManagerImpl implements AddressManager{

    private static final Logger LOG = LoggerFactory.getLogger(AddressManagerImpl.class);

    @Resource
    private ConsigneeClient consigneeClient;

    @Override
    public AddressDTO getAddress(Long userId,Long consigneeId,String appKey) {

        AddressDTO addressDTO = new AddressDTO();

        Response<UserConsigneeDTO> response = consigneeClient.getConsigneeById(userId, consigneeId, appKey);

        UserConsigneeDTO userConsigneeDTO = response.getModule();

        if(userConsigneeDTO!=null) {



            String province = userConsigneeDTO.getProvinceCode();
            String city = userConsigneeDTO.getCityCode();


            addressDTO.setCityCode(city);
            addressDTO.setProvinceCode(province);
        }else{
            LOG.warn("没有匹配到任何地址信息userId:{}  consigneeId:{}  appKey:{}",userId,consigneeId,appKey);
            addressDTO.setCityCode("");
            addressDTO.setProvinceCode("");
        }

        //TODO 转化为AddressDTO
        return addressDTO;
    }
}
