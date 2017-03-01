package com.hanshu.employee.core.dao.impl;

import com.hanshu.employee.common.qto.EmployeeLogQTO;
import com.hanshu.employee.core.dao.EmployeeLogDAO;
import com.hanshu.employee.core.domain.EmployeeLogDO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by yeliming on 16/5/19.
 */
@Service
public class EmployeeLogDAOImpl extends SqlMapClientDaoSupport implements EmployeeLogDAO {
    @Override
    public Long genEmployeeLog(EmployeeLogDO employeeLogDO) {
        return (Long) this.getSqlMapClientTemplate().insert("employee_log.gen_employee_log", employeeLogDO);
    }

    @Override
    public List<EmployeeLogDO> queryEmployeeLog(EmployeeLogQTO employeeLogQTO) {
        return this.getSqlMapClientTemplate().queryForList("employee_log.query_employee_log", employeeLogQTO);
    }

    @Override
    public Long getTotalCount(EmployeeLogQTO employeeLogQTO) {
        return (Long) this.getSqlMapClientTemplate().queryForObject("employee_log.get_total_count", employeeLogQTO);
    }
}
