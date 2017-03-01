package com.mockuai.usercenter.core.manager;

import com.mockuai.usercenter.common.dto.TradeRecordDTO;
import com.mockuai.usercenter.common.qto.TradeRecordQTO;
import com.mockuai.usercenter.core.domain.TradeRecordDO;
import com.mockuai.usercenter.core.exception.UserException;

import java.util.List;

/**
 * Created by duke on 15/9/22.
 */
public interface TradeRecordManager {
    /**
     * 插入充值记录
     * @param tradeRecordDTO 订单记录对象
     * @return 充值记录ID
     * @throws UserException
     * */
    Long addTradeRecord(TradeRecordDTO tradeRecordDTO) throws UserException;

    /**
     * 根据用户ID查询充值记录
     * @param userId 用户编号
     * @return 更新是否成功
     * @throws UserException
     * */
    TradeRecordDTO queryTradeRecordByUserId(Long userId) throws UserException;

    /**
     * 查询充值记录
     * */
    List<TradeRecordDTO> queryAll() throws UserException;

    /**
     * 条件查询充值记录
     * */
    List<TradeRecordDTO> query(TradeRecordQTO tradeRecordQTO) throws UserException;

    /**
     * 查询总量
     * */
    Long totalCount(TradeRecordQTO tradeRecordQTO) throws UserException;

    /**
     * 通过用户ID删除充值记录
     * */
    int deleteByUserId(Long userId) throws UserException;

    /**
     * 通过用户ID进行更新
     * */
    int updateByUserId(Long userId, TradeRecordDO tradeRecordDO) throws UserException;
}
