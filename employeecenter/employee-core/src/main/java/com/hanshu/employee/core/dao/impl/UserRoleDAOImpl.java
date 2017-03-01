package com.hanshu.employee.core.dao.impl;

import com.hanshu.employee.common.constant.ResponseCode;
import com.hanshu.employee.common.dto.UserRoleDTO;
import com.hanshu.employee.common.qto.UserRoleQTO;
import com.hanshu.employee.core.dao.UserRoleDAO;
import com.hanshu.employee.core.domain.UserRoleDO;
import com.hanshu.employee.core.exception.EmployeeException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by yeliming on 16/5/11.
 */
@Service
public class UserRoleDAOImpl extends SqlMapClientDaoSupport implements UserRoleDAO {
    @Override
    public Long addUserRole(UserRoleDO userRoleDO) {
        return (Long) this.getSqlMapClientTemplate().insert("userRole.addUserRole", userRoleDO);
    }

    @Override
    public UserRoleDO getUserRoleById(Long id) {
        return (UserRoleDO) this.getSqlMapClientTemplate().queryForObject("userRole.getUserRoleById", id);
    }

    @Override
    public int deleteUserRole(Long userRoleId) {
        return this.getSqlMapClientTemplate().update("userRole.deleteUserRoleById", userRoleId);
    }

    @Override
    public List<UserRoleDO> queryUserRole(UserRoleQTO userRoleQTO) {
        return this.getSqlMapClientTemplate().queryForList("userRole.queryUserRole", userRoleQTO);
    }

    @Override
    public Long getTotalCount(UserRoleQTO userRoleQTO) {
        return (Long) this.getSqlMapClientTemplate().queryForObject("userRole.getTotalCount", userRoleQTO);
    }

    @Override
    public int updateUserRole(UserRoleDO userRoleDO) {
        return this.getSqlMapClientTemplate().update("userRole.updateUserRole", userRoleDO);
    }

    @Override
    public UserRoleDO getUserRoleByRoleName(String roleName) throws EmployeeException {
        try {
            return (UserRoleDO) this.getSqlMapClientTemplate().queryForObject("userRole.getUserRoleByRoleName", roleName);
        } catch (Exception e) {
            throw new EmployeeException(ResponseCode.B_SELECT_ERROR);
        }
    }
}
