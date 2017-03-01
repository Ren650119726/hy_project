package com.mockuai.shopcenter.core.manager;

import com.mockuai.deliverycenter.common.dto.fee.RegionDTO;
import com.mockuai.shopcenter.core.exception.ShopException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by yindingyu on 15/11/3.
 */
@Service
public interface RegionManager {
    List<RegionDTO> queryRegion(String parentId,String appKey) throws ShopException;
}
