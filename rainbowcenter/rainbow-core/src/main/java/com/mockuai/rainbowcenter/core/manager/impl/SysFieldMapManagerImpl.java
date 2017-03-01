package com.mockuai.rainbowcenter.core.manager.impl;

import com.alibaba.fastjson.JSON;
import com.mockuai.rainbowcenter.common.constant.ResponseCode;
import com.mockuai.rainbowcenter.common.dto.SysFieldMapDTO;
import com.mockuai.rainbowcenter.common.qto.SysFieldMapQTO;
import com.mockuai.rainbowcenter.core.dao.SysFieldMapDAO;
import com.mockuai.rainbowcenter.core.domain.SysFieldMapDO;
import com.mockuai.rainbowcenter.core.exception.RainbowException;
import com.mockuai.rainbowcenter.core.manager.SysFieldMapManager;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lizg on 16/6/14.
 */
public class SysFieldMapManagerImpl implements SysFieldMapManager {
    private static final Logger log = LoggerFactory.getLogger(SysFieldMapManagerImpl.class);

    @Resource
    private SysFieldMapDAO sysFieldMapDAO;

    @Override
    public Long addSysFieldMap(SysFieldMapDTO sysFieldMapDTO) throws RainbowException {
        if (sysFieldMapDTO.getFieldName() == null) {
            log.error("field name is null");
            throw new RainbowException(ResponseCode.PARAM_E_PARAM_NULL, "field name is null");
        }
        if (sysFieldMapDTO.getType() == null) {
            log.error("type is null");
            throw new RainbowException(ResponseCode.PARAM_E_PARAM_NULL, "type is null");
        }
        if (sysFieldMapDTO.getBizCode() == null) {
            log.error("bizCode is null");
            throw new RainbowException(ResponseCode.PARAM_E_PARAM_NULL, "bizCode is null");
        }
        if ((sysFieldMapDTO.getOutValue() == null && sysFieldMapDTO.getOutValueType() == null) && (sysFieldMapDTO.getValue() == null && sysFieldMapDTO.getValueType() == null)) {
            log.error("out value or value is null");
            throw new RainbowException(ResponseCode.PARAM_E_PARAM_NULL, "out value or value is null");
        }

        String uniqueSys = DigestUtils.md5Hex(sysFieldMapDTO.getFieldName() + sysFieldMapDTO.getOutValue() + sysFieldMapDTO.getValue() + sysFieldMapDTO.getBizCode() + sysFieldMapDTO.getType());
        if (!uniqueSys.equals("")) {
            sysFieldMapDTO.setUniqueSys(uniqueSys);
        } else {
            log.error("uniqueSys is \"\"");
            throw new RainbowException(ResponseCode.PARAM_E_PARAM_INVALID, "uniqueSys is \"\"");
        }

        SysFieldMapDO edbItemMapDO = new SysFieldMapDO();
        BeanUtils.copyProperties(sysFieldMapDTO, edbItemMapDO);
        return this.sysFieldMapDAO.addSysFieldMap(edbItemMapDO);
    }

    @Override
    public int updateSysFieldMapByOutValue(SysFieldMapDTO sysFieldMapDTO) throws RainbowException {
        if (sysFieldMapDTO.getFieldName() == null) {
            log.error("field name is null");
            throw new RainbowException(ResponseCode.PARAM_E_PARAM_NULL, "field name is null");
        }
        if (sysFieldMapDTO.getType() == null) {
            log.error("type is null");
            throw new RainbowException(ResponseCode.PARAM_E_PARAM_NULL, "type is null");
        }
        if (sysFieldMapDTO.getBizCode() == null) {
            log.error("bizCode is null");
            throw new RainbowException(ResponseCode.PARAM_E_PARAM_NULL, "bizCode is null");
        }
        if (sysFieldMapDTO.getOutValue() == null) {
            log.error("out value is null");
            throw new RainbowException(ResponseCode.PARAM_E_PARAM_NULL, "out value is null");
        }

        String uniqueSys = sysFieldMapDTO.getFieldName() + sysFieldMapDTO.getOutValue() + sysFieldMapDTO.getValue() + sysFieldMapDTO.getBizCode() + sysFieldMapDTO.getType();
        if (!uniqueSys.equals("")) {
            sysFieldMapDTO.setUniqueSys(DigestUtils.md5Hex(uniqueSys));
        } else {
            log.error("uniqueSys is \"\"");
            throw new RainbowException(ResponseCode.PARAM_E_PARAM_INVALID, "uniqueSys is \"\"");
        }

        SysFieldMapDO sysFieldMapDO = new SysFieldMapDO();
        BeanUtils.copyProperties(sysFieldMapDTO, sysFieldMapDO);
        Integer result = this.sysFieldMapDAO.updateSysFieldMapByOutValue(sysFieldMapDO);
        if (result < 1) {
            log.error("update error, sysFieldMapDO: {}", JSON.toJSONString(sysFieldMapDO));
            throw new RainbowException(ResponseCode.SYS_E_DATABASE_ERROR, "update record error");
        }
        return result;
    }

    @Override
    public int updateSysFieldMapByValue(SysFieldMapDTO sysFieldMapDTO) throws RainbowException {
        if (sysFieldMapDTO.getFieldName() == null) {
            log.error("field name is null");
            throw new RainbowException(ResponseCode.PARAM_E_PARAM_NULL, "field name is null");
        }
        if (sysFieldMapDTO.getType() == null) {
            log.error("type is null");
            throw new RainbowException(ResponseCode.PARAM_E_PARAM_NULL, "type is null");
        }
        if (sysFieldMapDTO.getBizCode() == null) {
            log.error("bizCode is null");
            throw new RainbowException(ResponseCode.PARAM_E_PARAM_NULL, "bizCode is null");
        }
        if (sysFieldMapDTO.getValue() == null) {
            log.error("value is null");
            throw new RainbowException(ResponseCode.PARAM_E_PARAM_NULL, "value is null");
        }

        String uniqueSys = sysFieldMapDTO.getFieldName() + sysFieldMapDTO.getOutValue() + sysFieldMapDTO.getValue() + sysFieldMapDTO.getBizCode() + sysFieldMapDTO.getType();
        if (!uniqueSys.equals("")) {
            sysFieldMapDTO.setUniqueSys(DigestUtils.md5Hex(uniqueSys));
        } else {
            log.error("uniqueSys is \"\"");
            throw new RainbowException(ResponseCode.PARAM_E_PARAM_INVALID, "uniqueSys is \"\"");
        }

        SysFieldMapDO sysFieldMapDO = new SysFieldMapDO();
        BeanUtils.copyProperties(sysFieldMapDTO, sysFieldMapDO);
        Integer result = this.sysFieldMapDAO.updateSysFieldMapByValue(sysFieldMapDO);
        if (result < 1) {
            log.error("update error, sysFieldMapDO: {}", JSON.toJSONString(sysFieldMapDO));
            throw new RainbowException(ResponseCode.SYS_E_DATABASE_ERROR, "update record error");
        }
        return result;
    }

    @Override
    public SysFieldMapDTO getSysFieldMap(SysFieldMapQTO sysFieldMapQTO, boolean allowResultNull) throws RainbowException {
        if (sysFieldMapQTO.getFieldName() == null) {
            log.error("field name is null");
            throw new RainbowException(ResponseCode.PARAM_E_PARAM_NULL, "field name is null");
        }
        if (sysFieldMapQTO.getType() == null) {
            log.error("type is null");
            throw new RainbowException(ResponseCode.PARAM_E_PARAM_NULL, "type is null");
        }
        if (sysFieldMapQTO.getBizCode() == null) {
            log.error("bizCode is null");
            throw new RainbowException(ResponseCode.PARAM_E_PARAM_NULL, "bizCode is null");
        }

        SysFieldMapDO sysFieldMapDO = this.sysFieldMapDAO.getSysFieldMap(sysFieldMapQTO);
        if (sysFieldMapDO == null && !allowResultNull) {
            log.error("there is no record, sysFieldMapQTO:{}", JSON.toJSONString(sysFieldMapQTO));
            throw new RainbowException(ResponseCode.SYS_E_DATABASE_ERROR, sysFieldMapQTO.getFieldName() + " can not find");
        } else {
            SysFieldMapDTO sysFieldMapDTO = null;

            if (sysFieldMapDO != null) {
                sysFieldMapDTO = new SysFieldMapDTO();
                BeanUtils.copyProperties(sysFieldMapDO, sysFieldMapDTO);
            }
            return sysFieldMapDTO;
        }
    }

    @Override
    public List<SysFieldMapDTO> querySysFieldMap(SysFieldMapQTO sysFieldMapQTO, boolean allowResultNull) throws RainbowException {
        if (sysFieldMapQTO.getFieldName() == null) {
            log.error("field name is null");
            throw new RainbowException(ResponseCode.PARAM_E_PARAM_NULL, "field name is null");
        }
        if (sysFieldMapQTO.getType() == null) {
            log.error("type is null");
            throw new RainbowException(ResponseCode.PARAM_E_PARAM_NULL, "type is null");
        }
        if (sysFieldMapQTO.getBizCode() == null) {
            log.error("bizCode is null");
            throw new RainbowException(ResponseCode.PARAM_E_PARAM_NULL, "bizCode is null");
        }

        List<SysFieldMapDO> sysFieldMapDOs = this.sysFieldMapDAO.querySysFieldMap(sysFieldMapQTO);
        if (sysFieldMapDOs == null && !allowResultNull) {
            log.error("there is no record, sysFieldMapQTO:{}", JSON.toJSONString(sysFieldMapQTO));
            throw new RainbowException(ResponseCode.SYS_E_DATABASE_ERROR, "there is no record");
        }
        List<SysFieldMapDTO> sysFieldMapDTOs = null;
        if (sysFieldMapDOs != null) {
            sysFieldMapDTOs = new ArrayList<SysFieldMapDTO>();
            for (SysFieldMapDO sysFieldMapDO : sysFieldMapDOs) {
                SysFieldMapDTO sysFieldMapDTO = new SysFieldMapDTO();
                BeanUtils.copyProperties(sysFieldMapDO, sysFieldMapDTO);
                sysFieldMapDTOs.add(sysFieldMapDTO);
            }
        }
        return sysFieldMapDTOs;
    }

    @Override
    public int updateRemoveByOutValue(SysFieldMapDTO sysFieldMapDTO) throws RainbowException {
        if (sysFieldMapDTO.getType() == null) {
            log.error("type is null");
            throw new RainbowException(ResponseCode.PARAM_E_PARAM_NULL, "type is null");
        }
        if (sysFieldMapDTO.getOutValue() == null) {
            log.error("outValue is null");
            throw new RainbowException(ResponseCode.PARAM_E_PARAM_NULL, "outValue is null");
        }

        SysFieldMapDO sysFieldMapDO = new SysFieldMapDO();
        BeanUtils.copyProperties(sysFieldMapDTO, sysFieldMapDO);
        Integer result = this.sysFieldMapDAO.updateRemoveByOutValue(sysFieldMapDO);
        if (result < 1) {
            log.error("update error, sysFieldMapDO: {}", JSON.toJSONString(sysFieldMapDO));
            throw new RainbowException(ResponseCode.SYS_E_DATABASE_ERROR, "update record error");
        }
        return result;
    }
}
