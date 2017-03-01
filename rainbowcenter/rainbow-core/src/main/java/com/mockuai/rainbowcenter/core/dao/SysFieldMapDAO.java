package com.mockuai.rainbowcenter.core.dao;

import com.mockuai.rainbowcenter.common.qto.SysFieldMapQTO;
import com.mockuai.rainbowcenter.core.domain.SysFieldMapDO;

import java.util.List;

/**
 * Created by yeliming on 16/3/14.
 */
public interface SysFieldMapDAO {
    Long addSysFieldMap(SysFieldMapDO sysFieldMapDO);

    int updateSysFieldMapByOutValue(SysFieldMapDO sysFieldMapDO);

    int updateSysFieldMapByValue(SysFieldMapDO sysFieldMapDO);

    SysFieldMapDO getSysFieldMap(SysFieldMapQTO sysFieldMapQTO);

    List<SysFieldMapDO> querySysFieldMap(SysFieldMapQTO sysFieldMapQTO);

    int updateRemoveByOutValue(SysFieldMapDO sysFieldMapDO);

}
