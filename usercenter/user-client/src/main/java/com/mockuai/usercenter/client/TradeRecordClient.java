package com.mockuai.usercenter.client;

import com.mockuai.usercenter.common.api.Response;
import com.mockuai.usercenter.common.dto.TradeRecordDTO;
import com.mockuai.usercenter.common.qto.TradeRecordQTO;

import java.util.List;

/**
 * Created by duke on 15/9/22.
 */
public interface TradeRecordClient {
    /**
     * 添加充值记录
     *
     * @param tradeRecordDTO 充值记录对象
     * @param appKey appKey值
     * @return 充值记录ID
     * */
    Response<Long> addTradeRecord(TradeRecordDTO tradeRecordDTO, String appKey);

    /**
     * 通过用户ID查询充值记录
     *
     * @param userId 用户ID
     * @param appKey appKey值
     * @return 布尔值，表示更新是否成功
     * */
    Response<TradeRecordDTO> queryTradeRecordByUserId(Long userId, String appKey);

    /**
     * 查询充值记录
     * */
    Response<List<TradeRecordDTO>> queryAll(String appKey);

    /**
     * 条件查询记录
     * */
    Response<List<TradeRecordDTO>> query(TradeRecordQTO tradeRecordQTO, String appKey);

    /**
     * 通过用户ID进行查询
     * */
    Response<Boolean> updateByUserId(Long userId, TradeRecordDTO tradeRecordDTO, String appKey);
}
