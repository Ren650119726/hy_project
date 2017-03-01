package com.mockuai.distributioncenter.core.manager;

import com.mockuai.distributioncenter.common.domain.dto.DistRecordDTO;
import com.mockuai.distributioncenter.common.domain.qto.DistRecordQTO;
import com.mockuai.distributioncenter.core.exception.DistributionException;

import java.util.List;

/**
 * Created by duke on 16/3/11.
 */
public interface DistRecordManager {
    /**
     * 添加详细统计记录
     * @param distRecordDTO
     * */
    Long add(DistRecordDTO distRecordDTO) throws DistributionException;

    /**
     * 条件查询
     * @param distRecordQTO
     * */
    List<DistRecordDTO> query(DistRecordQTO distRecordQTO) throws DistributionException;

    /**
     * 通过订单ID聚合查询
     * */
    List<Long> queryValuableOrderIds(DistRecordQTO distRecordQTO) throws DistributionException;

    /**
     * 更新
     * @param distRecordDTO
     * */
    Boolean update(DistRecordDTO distRecordDTO) throws DistributionException;
    
    /**
     * 统计条件查询
     * @param distRecordQTO
     * */
    List<DistRecordDTO> queryStatistics(DistRecordQTO distRecordQTO) throws DistributionException;
}
