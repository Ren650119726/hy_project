package com.mockuai.marketingcenter.core.manager;

import com.mockuai.marketingcenter.common.domain.dto.TransmitDTO;
import com.mockuai.marketingcenter.core.exception.MarketingException;

/**
 * Created by duke on 15/9/23.
 */
public interface TransmitManager {
    /**
     * 添加转发有礼信息
     *
     * @param transmitDTO
     * @return 转发有礼编号
     */
    Long addTransmit(TransmitDTO transmitDTO) throws MarketingException;

    /**
     * 更新转发有礼信息
     *
     * @param transmitDTO
     * @returnT
     */
    int updateTransmit(TransmitDTO transmitDTO) throws MarketingException;

    /**
     * 查询转发有礼信息
     *
     * @param bizCode
     * @return 转发有礼对象，如果没有，返回null
     */
    TransmitDTO getTransmit(String bizCode) throws MarketingException;
}
