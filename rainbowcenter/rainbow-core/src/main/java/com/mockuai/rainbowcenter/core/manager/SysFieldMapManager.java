package com.mockuai.rainbowcenter.core.manager;

import com.mockuai.rainbowcenter.common.dto.SysFieldMapDTO;
import com.mockuai.rainbowcenter.common.qto.SysFieldMapQTO;
import com.mockuai.rainbowcenter.core.exception.RainbowException;

import java.util.List;

/**
 * Created by lizg on 16/6/14.
 */
public interface SysFieldMapManager {
    Long addSysFieldMap(SysFieldMapDTO sysFieldMapDTO) throws RainbowException;

    int updateSysFieldMapByOutValue(SysFieldMapDTO sysFieldMapDTO) throws RainbowException;

    int updateSysFieldMapByValue(SysFieldMapDTO sysFieldMapDTO) throws RainbowException;

    SysFieldMapDTO getSysFieldMap(SysFieldMapQTO sysFieldMapQTO, boolean allowResultNull) throws RainbowException;

    List<SysFieldMapDTO> querySysFieldMap(SysFieldMapQTO sysFieldMapQTO, boolean allowResultNull) throws RainbowException;

    int updateRemoveByOutValue(SysFieldMapDTO sysFieldMapDTO) throws RainbowException;
}
