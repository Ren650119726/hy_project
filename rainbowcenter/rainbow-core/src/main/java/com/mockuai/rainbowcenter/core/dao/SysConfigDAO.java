package com.mockuai.rainbowcenter.core.dao;

import com.mockuai.rainbowcenter.common.qto.SysConfigQTO;
import com.mockuai.rainbowcenter.core.domain.SysConfigDO;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yeliming on 16/3/14.
 */
public interface SysConfigDAO {

    SysConfigDO getSysConfigByValue(SysConfigQTO sysConfigQTO);

    List<SysConfigDO> querySysConfig(SysConfigQTO sysConfigQTO);

    List<SysConfigDO> queryConfigOfAccount(SysConfigQTO sysConfigQTO);
}
