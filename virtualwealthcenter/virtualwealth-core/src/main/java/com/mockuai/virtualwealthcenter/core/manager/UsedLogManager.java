package com.mockuai.virtualwealthcenter.core.manager;

import com.mockuai.virtualwealthcenter.common.domain.qto.UsedLogQTO;
import com.mockuai.virtualwealthcenter.core.domain.UsedLogDO;
import com.mockuai.virtualwealthcenter.core.exception.VirtualWealthException;

import java.util.List;

/**
 * Created by edgar.zr on 5/16/2016.
 */
public interface UsedLogManager {

    void batchAddUsedLog(List<UsedLogDO> usedLogDOs) throws VirtualWealthException;

    List<UsedLogDO> queryUsedLog(UsedLogQTO usedLogQTO) throws VirtualWealthException;

    int updateUsedLog(UsedLogDO usedLogDO) throws VirtualWealthException;
}