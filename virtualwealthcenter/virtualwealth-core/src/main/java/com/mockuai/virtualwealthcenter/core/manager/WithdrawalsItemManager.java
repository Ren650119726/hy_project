package com.mockuai.virtualwealthcenter.core.manager;

import com.mockuai.virtualwealthcenter.common.domain.dto.WithdrawalsItemDTO;
import com.mockuai.virtualwealthcenter.common.domain.qto.WithdrawalsItemQTO;
import com.mockuai.virtualwealthcenter.core.exception.VirtualWealthException;

import java.util.List;

/**
 * Created by 冠生 on 2016/5/18.
 */
public interface WithdrawalsItemManager {

    WithdrawalsItemDTO getWithdrawalsItemDTO(String withdrawalsNumber) throws  VirtualWealthException;

    List<WithdrawalsItemDTO> queryWithdrawalsItemDTO(WithdrawalsItemQTO withdrawalsItemQTO) throws VirtualWealthException;

    List<WithdrawalsItemDTO> findList(WithdrawalsItemQTO item) throws VirtualWealthException;

    Integer updateRecord(WithdrawalsItemQTO item) throws VirtualWealthException;

    int count(WithdrawalsItemQTO item) throws VirtualWealthException;

}
