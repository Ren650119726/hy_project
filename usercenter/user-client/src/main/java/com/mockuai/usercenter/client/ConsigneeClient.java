package com.mockuai.usercenter.client;

import com.mockuai.usercenter.common.api.Response;
import com.mockuai.usercenter.common.dto.UserConsigneeDTO;
import com.mockuai.usercenter.common.qto.UserConsigneeQTO;

import java.util.List;

public interface ConsigneeClient {
    /**
     * 添加收货地址
     */
    Response<UserConsigneeDTO> addConsignee(UserConsigneeDTO userConsigneeDTO, String appKey);

    /**
     * 删除收货地址
     */
    Response<Boolean> deleteConsignee(Long userId, Long consigneeId, String appKey);

    /**
     * 查询用户的收货地址列表
     */
    Response<List<UserConsigneeDTO>> queryConsignee(Long userId, String appKey);

    /**
     * 设置默认地址
     */
    Response<Boolean> setDefConsignee(Long consigneeId, String appKey);

    /**
     * 修改用户的收货地址
     */
    Response<Boolean> updateConsignee(UserConsigneeDTO userConsigneeDto, String appKey);

    /**
     * 根据id获取收货地址
     */
    public Response<UserConsigneeDTO> getConsigneeById(Long userId, Long consigneeId, String appKey);

    /**
     * 查询所有收货地址
     */
    Response<List<UserConsigneeDTO>> queryAllConsignee(UserConsigneeQTO userConsigneeQTO, String appKey);
}
