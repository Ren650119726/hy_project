package com.mockuai.virtualwealthcenter.core.dao;

import com.mockuai.virtualwealthcenter.common.domain.qto.UsedLogQTO;
import com.mockuai.virtualwealthcenter.core.domain.UsedLogDO;

import java.util.List;

/**
 * Created by edgar.zr on 5/16/2016.
 */
public interface UsedLogDAO {

    Integer batchAddUsedLog(List<UsedLogDO> usedLogDOs);

    List<UsedLogDO> queryUsedLog(UsedLogQTO usedLogQTO);

    int updateUsedLog(UsedLogDO usedLogDO);
}