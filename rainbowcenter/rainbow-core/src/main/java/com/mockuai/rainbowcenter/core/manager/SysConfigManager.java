package com.mockuai.rainbowcenter.core.manager;

import com.mockuai.rainbowcenter.common.dto.SysConfigDTO;
import com.mockuai.rainbowcenter.common.qto.SysConfigQTO;
import com.mockuai.rainbowcenter.core.exception.RainbowException;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yeliming on 16/3/14.
 */
public interface SysConfigManager {

    /**
     * 根据魔筷的值,查找对应的配置,如店铺id对应的配置
     * @param sysConfigQTO
     */
    SysConfigDTO getSysConfigByValue(SysConfigQTO sysConfigQTO) throws RainbowException;

    List<SysConfigDTO> queryConfig(SysConfigQTO sysConfigQTO) throws RainbowException;

    List<SysConfigDTO> queryConfigOfAccount(String fieldName, String type) throws RainbowException;
}
