package com.mockuai.usercenter.core.manager;

import com.mockuai.deliverycenter.common.dto.fee.RegionDTO;
import com.mockuai.usercenter.core.exception.UserException;

import java.util.List;

/**
 * Created by zengzhangqiang on 6/4/15.
 */
public interface DeliveryManager {
    public List<RegionDTO> queryRegion(List<String> regionCodes, String appKey) throws UserException;
}
