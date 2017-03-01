package com.mockuai.shopcenter.core.manager;

import com.mockuai.itemcenter.common.domain.dto.area.AddressDTO;
import com.mockuai.tradecenter.common.domain.UserConsigneeDTO;

/**
 * Created by yindingyu on 16/1/20.
 */
public interface AddressManager {
    com.mockuai.usercenter.common.dto.UserConsigneeDTO getAddress(Long userId, Long consigneeId, String appKey);
}
