package com.mockuai.usercenter.core.dao;

import com.mockuai.usercenter.common.qto.TradeRecordQTO;
import com.mockuai.usercenter.core.domain.TradeRecordDO;

import java.util.List;

/**
 * Created by duke on 15/9/22.
 */
public interface TradeRecordDAO {
    /**
     * 添加充值记录
     * @param tradeRecordDO 充值记录对象
     * */
    Long addRecord(TradeRecordDO tradeRecordDO);

    /**
     * 通过用户ID查询充值记录
     * */
    TradeRecordDO queryRecordByUserId(Long userId);

    /**
     * 查询充值记录
     * */
    List<TradeRecordDO> queryAll();

    /**
     * 条件查询充值记录
     * */
    List<TradeRecordDO> query(TradeRecordQTO qto);

    /**
     * 查询总量
     * */
    Long totalCount(TradeRecordQTO qto);

    /**
     * 指定用户的删除充值记录
     * */
    int deleteByUserId(Long userId);

    /**
     * 通过用户ID更新
     * */
    int updateByUserId(Long userId, TradeRecordDO tradeRecordDO);
}
