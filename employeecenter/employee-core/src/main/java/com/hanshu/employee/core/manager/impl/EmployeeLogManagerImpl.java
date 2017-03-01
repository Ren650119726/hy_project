package com.hanshu.employee.core.manager.impl;

import com.hanshu.employee.common.constant.OpActionEnum;
import com.hanshu.employee.common.constant.OpObjTypeEnum;
import com.hanshu.employee.common.dto.EmployeeLogDTO;
import com.hanshu.employee.common.qto.EmployeeLogQTO;
import com.hanshu.employee.core.dao.EmployeeLogDAO;
import com.hanshu.employee.core.domain.EmployeeLogDO;
import com.hanshu.employee.core.manager.EmployeeLogManager;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yeliming on 16/5/19.
 */
@Service
public class EmployeeLogManagerImpl implements EmployeeLogManager {
    @Resource
    protected EmployeeLogDAO employeeLogDAO;

    @Override
    public Long genEmployeeLog(EmployeeLogDTO employeeLogDTO) {
        EmployeeLogDO employeeLogDO = new EmployeeLogDO();
        BeanUtils.copyProperties(employeeLogDTO, employeeLogDO);
        return this.employeeLogDAO.genEmployeeLog(employeeLogDO);
    }

    @Override
    public List<EmployeeLogDTO> queryEmployeeLog(EmployeeLogQTO employeeLogQTO) {
        List<EmployeeLogDTO> employeeLogDTOs = null;
        List<EmployeeLogDO> employeeLogDOs = this.employeeLogDAO.queryEmployeeLog(employeeLogQTO);
        if (employeeLogDOs != null) {
            employeeLogDTOs = new ArrayList<EmployeeLogDTO>();
            for (EmployeeLogDO employeeLogDO : employeeLogDOs) {
                employeeLogDO.setOperateJson(null);
                EmployeeLogDTO employeeLogDTO = new EmployeeLogDTO();
                BeanUtils.copyProperties(employeeLogDO, employeeLogDTO);
                employeeLogDTO.setOpActionStr(OpActionEnum.getMsg(employeeLogDO.getOpAction()));
                employeeLogDTO.setObjTypeStr(OpObjTypeEnum.getMsg(employeeLogDO.getObjType()));
                employeeLogDTOs.add(employeeLogDTO);
            }
        }
        return employeeLogDTOs;
    }

    @Override
    public Long getTotalCount(EmployeeLogQTO employeeLogQTO) {
        return this.employeeLogDAO.getTotalCount(employeeLogQTO);
    }
}
