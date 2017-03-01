package com.mockuai.marketingcenter.core.dao;

import com.mockuai.marketingcenter.core.domain.TransmitDO;

/**
 * Created by duke on 15/9/23.
 */
public interface TransmitDAO {
    /**
     * 添加转发有礼
     *
     * @param transmitDO
     * @return ID值
     */
    Long addTransmit(TransmitDO transmitDO);

    /**
     * 更新转发有礼状态
     *
     * @param transmitDO
     * @return
     */
    int updateTransmit(TransmitDO transmitDO);

    /**
     * 获得转发有礼信息
     *
     * @param bizCode
     * @return 转发有礼对象
     */
    TransmitDO getTransmitByBizCode(String bizCode);
}