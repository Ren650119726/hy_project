package com.mockuai.dts.core.manager;

import com.mockuai.deliverycenter.common.dto.fee.RegionDTO;
import com.mockuai.dts.core.exception.DtsException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by yindingyu on 15/11/3.
 */
@Service
public interface RegionManager {

    RegionDTO getRegionByName(String name, String appKey) throws DtsException;

}
