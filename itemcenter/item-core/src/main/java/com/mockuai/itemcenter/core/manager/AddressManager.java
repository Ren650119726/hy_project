package com.mockuai.itemcenter.core.manager;

import com.mockuai.itemcenter.common.domain.dto.area.AddressDTO;

/**
 * Created by yindingyu on 15/10/26.
 */
public interface AddressManager {

    AddressDTO getAddress(Long userId,Long consigneeId,String appKey);
}
