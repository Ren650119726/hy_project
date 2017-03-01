package com.mockuai.distributioncenter.core.dao;

import com.mockuai.distributioncenter.common.domain.qto.DistRecordQTO;
import com.mockuai.distributioncenter.core.domain.DistRecordDO;

import java.util.List;

/**
 * Created by duke on 16/3/11.
 */
public interface DistRecordDAO {
    /**
     * 添加记录
     * @param distRecordDO
     * */
    Long add(DistRecordDO distRecordDO);

    /**
     * 查询
     * @param distRecordQTO
     * */
    List<DistRecordDO> query(DistRecordQTO distRecordQTO);

    /**
     * 通过订单ID聚合查询记录
     * */
    List<Long> queryValuableOrderIds(DistRecordQTO distRecordQTO);

    /**
     * 更新
     * */
    Integer update(DistRecordDO distRecordDO);
    
    /**
     * 统计查询
     * @param distRecordQTO
     * */
    List<DistRecordDO> queryStatistics(DistRecordQTO distRecordQTO);

    Long getAmountBySellerId(DistRecordDO distRecordDO);
}
