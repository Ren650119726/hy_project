/*package com.hanshu.employee.core.service.action.userrole;

import com.hanshu.employee.common.dto.UserRoleDTO;
import com.hanshu.employee.core.exception.EmployeeException;
import com.hanshu.employee.core.manager.UserRoleManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

*//**
 * Created by yeliming on 16/6/2.
 *//*
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class UserRoleActionTest {
    @Resource
    private UserRoleManager userRoleManager;

    @Test
    public void test1() throws EmployeeException {
        boolean b = userRoleManager.deleteUserRole(24L);
        System.out.println(b);
    }

    @Test
    public void test2() throws EmployeeException {
        UserRoleDTO userRoleDTO = new UserRoleDTO();
        userRoleDTO.setId(1L);
        userRoleDTO.setRoleName("ABC");
        userRoleDTO.setRole("[\"284\"]");
        Boolean b = userRoleManager.updateUserRole(userRoleDTO);
        System.out.println(b);
    }

}*/