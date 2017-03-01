package com.mockuai.virtualwealthcenter.core.dao;

import com.mockuai.virtualwealthcenter.common.domain.qto.RechargeRecordQTO;
import com.mockuai.virtualwealthcenter.core.domain.RechargeRecordDO;

import java.util.List;

/**
 * Created by duke on 16/4/18.
 */
public interface RechargeRecordDAO {
    /**
     * 添加
     */
    Long add(RechargeRecordDO rechargeRecordDO);

    /**
     * 查询
     */
    List<RechargeRecordDO> query(RechargeRecordQTO rechargeRecordQTO);

    /**
     * 更新
     */
    int update(RechargeRecordDO rechargeRecordDO);

    /**
     * 总量
     */
    Long totalCount(RechargeRecordQTO rechargeRecordQTO);
}