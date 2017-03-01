package com.mockuai.virtualwealthcenter.core.dao;

import com.mockuai.virtualwealthcenter.common.domain.dto.WithdrawalsItemDTO;
import com.mockuai.virtualwealthcenter.common.domain.qto.WithdrawalsItemQTO;
import com.mockuai.virtualwealthcenter.core.domain.WithdrawalsItemDO;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by 冠生 on 2016/5/17.
 * 提现记录
 */
public interface WithdrawalsItemDAO {

    List<WithdrawalsItemDO> query(WithdrawalsItemQTO withdrawalsItemQTO);
    List<WithdrawalsItemDTO> findList(WithdrawalsItemQTO item);

    Integer updateRecord(WithdrawalsItemQTO withdrawalsItemQTO) throws SQLException;
     int count(WithdrawalsItemQTO item);


    WithdrawalsItemDO getWithdrawalItem(String withdrawalsNumber);
    WithdrawalsItemDO getWithdrawalItemSimple(String withdrawalsNumber);
}
