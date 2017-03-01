package com.mockuai.marketingcenter.client;

import com.mockuai.marketingcenter.common.api.Response;
import com.mockuai.marketingcenter.common.domain.dto.MarketActivityDTO;
import com.mockuai.marketingcenter.common.domain.qto.MarketActivityQTO;

import java.util.List;

/**
 * Created by edgar.zr on 12/2/15.
 */
public interface BarterClient {

    /**
     * 创建营销活动
     *
     * @param marketActivityDTO
     * @return
     */
    public Response<Long> addBarterActivity(MarketActivityDTO marketActivityDTO, String appKey);


    /**
     * 获取指定营销活动
     *
     * @param activityId
     * @param creatorId
     * @param appKey
     * @return
     */
    public Response<MarketActivityDTO> getBarterActivity(Long activityId, Long creatorId, String appKey);

    /**
     * 查询营销活动列表
     *
     * @param marketActivityQTO
     * @param appKey
     * @return
     */
    public Response<List<MarketActivityDTO>> queryBarterActivity(MarketActivityQTO marketActivityQTO, String appKey);
}