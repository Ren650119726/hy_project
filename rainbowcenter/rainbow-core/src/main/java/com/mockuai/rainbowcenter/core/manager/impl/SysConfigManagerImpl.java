package com.mockuai.rainbowcenter.core.manager.impl;

import com.alibaba.fastjson.JSON;
import com.mockuai.rainbowcenter.common.constant.ResponseCode;
import com.mockuai.rainbowcenter.common.dto.SysConfigDTO;
import com.mockuai.rainbowcenter.common.qto.SysConfigQTO;
import com.mockuai.rainbowcenter.core.dao.SysConfigDAO;
import com.mockuai.rainbowcenter.core.domain.SysConfigDO;
import com.mockuai.rainbowcenter.core.exception.RainbowException;
import com.mockuai.rainbowcenter.core.manager.SysConfigManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yeliming on 16/3/14.
 */
public class SysConfigManagerImpl implements SysConfigManager {
    private static final Logger log = LoggerFactory.getLogger(SysConfigManagerImpl.class);

    @Resource
    private SysConfigDAO sysConfigDAO;

    @Override
    public SysConfigDTO getSysConfigByValue(SysConfigQTO sysConfigQTO) throws RainbowException {
        SysConfigDO sysConfigDO = sysConfigDAO.getSysConfigByValue(sysConfigQTO);
        if (sysConfigDO == null) {
            log.error("result is null, sysconfigQTO: {}", JSON.toJSONString(sysConfigQTO));
            throw new RainbowException(ResponseCode.SYS_E_DATABASE_ERROR);
        }
        SysConfigDTO sysConfigDTO = new SysConfigDTO();
        BeanUtils.copyProperties(sysConfigDO, sysConfigDTO);
        return sysConfigDTO;
    }

    @Override
    public List<SysConfigDTO> queryConfig(SysConfigQTO sysConfigQTO) throws RainbowException {
        List<SysConfigDO> sysConfigDOs = this.sysConfigDAO.querySysConfig(sysConfigQTO);
        if (sysConfigDOs == null || sysConfigDOs.size() == 0) {
            log.error("result is null or ressult size = 0, sysconfigQTO: {}", JSON.toJSONString(sysConfigQTO));
            throw new RainbowException(ResponseCode.SYS_E_DATABASE_ERROR);
        }
        List<SysConfigDTO> sysConfigDTOs = new ArrayList<SysConfigDTO>();
        for (SysConfigDO sysConfigDO : sysConfigDOs) {
            SysConfigDTO sysConfigDTO = new SysConfigDTO();
            BeanUtils.copyProperties(sysConfigDO, sysConfigDTO);
            sysConfigDTOs.add(sysConfigDTO);
        }
        return sysConfigDTOs;
    }

    @Override
    public List<SysConfigDTO> queryConfigOfAccount(String fieldName, String type) throws RainbowException {
        SysConfigQTO sysConfigQTO = new SysConfigQTO();
        sysConfigQTO.setType(type);
        sysConfigQTO.setFieldName(fieldName);
        List<SysConfigDO> sysConfigDOs = this.sysConfigDAO.queryConfigOfAccount(sysConfigQTO);
        if (sysConfigDOs == null || sysConfigDOs.size() == 0) {
            log.error("result is null or ressult size = 0, sysconfigQTO: {}", JSON.toJSONString(sysConfigQTO));
            throw new RainbowException(ResponseCode.SYS_E_DATABASE_ERROR);
        }
        List<SysConfigDTO> sysConfigDTOs = new ArrayList<SysConfigDTO>();
        for (SysConfigDO sysConfigDO : sysConfigDOs) {
            SysConfigDTO sysConfigDTO = new SysConfigDTO();
            BeanUtils.copyProperties(sysConfigDO, sysConfigDTO);
            sysConfigDTOs.add(sysConfigDTO);
        }
        return sysConfigDTOs;
    }
}
