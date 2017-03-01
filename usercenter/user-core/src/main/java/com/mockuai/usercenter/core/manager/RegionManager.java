package com.mockuai.usercenter.core.manager;

import com.mockuai.deliverycenter.common.dto.fee.RegionDTO;
import com.mockuai.deliverycenter.common.qto.fee.RegionQTO;
import com.mockuai.usercenter.core.exception.UserException;

import java.util.List;

/**
 * Created by duke on 15/11/6.
 */
public interface RegionManager {
    /**
     * 条件查询区域
     * @param regionQTO
     * @param appKey
     * */
    List<RegionDTO> queryRegion(RegionQTO regionQTO, String appKey) throws UserException;
}
