package com.mockuai.virtualwealthcenter.core.manager;

import com.mockuai.virtualwealthcenter.common.domain.dto.RechargeRecordDTO;
import com.mockuai.virtualwealthcenter.common.domain.qto.RechargeRecordQTO;
import com.mockuai.virtualwealthcenter.core.exception.VirtualWealthException;

import java.util.List;

/**
 * Created by duke on 16/4/18.
 */
public interface RechargeRecordManager {
    /**
     * 添加记录
     */
    Long addRecord(RechargeRecordDTO rechargeRecordDTO) throws VirtualWealthException;

    /**
     * 查询记录
     */
    List<RechargeRecordDTO> queryRecord(RechargeRecordQTO rechargeRecordQTO) throws VirtualWealthException;

    /**
     * 更新记录
     */
    int updateRecord(RechargeRecordDTO rechargeRecordDTO) throws VirtualWealthException;

    /**
     * 总量
     */
    Long totalCount(RechargeRecordQTO rechargeRecordQTO) throws VirtualWealthException;
}