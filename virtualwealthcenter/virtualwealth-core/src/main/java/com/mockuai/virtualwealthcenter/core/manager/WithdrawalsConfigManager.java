package com.mockuai.virtualwealthcenter.core.manager;

import com.mockuai.virtualwealthcenter.common.domain.dto.WithdrawalsConfigDTO;
import com.mockuai.virtualwealthcenter.common.domain.dto.WithdrawalsItemDTO;
import com.mockuai.virtualwealthcenter.common.domain.qto.WithdrawalsConfigQTO;
import com.mockuai.virtualwealthcenter.common.domain.qto.WithdrawalsItemQTO;
import com.mockuai.virtualwealthcenter.core.exception.VirtualWealthException;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by 冠生 on 2016/5/18.
 */
public interface WithdrawalsConfigManager {

    void saveWithdrawalsConfig(WithdrawalsConfigQTO withdrawalsConfigQTO) throws  VirtualWealthException;
    List<WithdrawalsConfigDTO> queryList(WithdrawalsConfigQTO withdrawalsConfigQTO )throws VirtualWealthException;

}
