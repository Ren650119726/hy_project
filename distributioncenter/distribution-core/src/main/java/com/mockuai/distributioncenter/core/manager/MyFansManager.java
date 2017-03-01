package com.mockuai.distributioncenter.core.manager;

import com.mockuai.distributioncenter.common.domain.dto.FansDistDTO;
import com.mockuai.distributioncenter.common.domain.dto.MyFansDTO;
import com.mockuai.distributioncenter.common.domain.qto.FansDistQTO;
import com.mockuai.distributioncenter.common.domain.qto.MyFansQTO;
import com.mockuai.distributioncenter.core.exception.DistributionException;

import java.util.List;

/**
 * Created by lizg on 2016/9/1.
 */
public interface MyFansManager {

    List<MyFansDTO> getMyFans (MyFansQTO myFansQTO,String appKey) throws DistributionException;

    Long totalCount(MyFansQTO myFansQTO, String appKey) throws DistributionException;


    Long totalCountByUserId(Long userId, String appKey) throws DistributionException;

    List<FansDistDTO> queryDistListFromFans(FansDistQTO fansDistQTO, String appKey) throws DistributionException;
}
