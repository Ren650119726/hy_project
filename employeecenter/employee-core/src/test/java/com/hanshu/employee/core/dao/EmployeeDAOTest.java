/*package com.hanshu.employee.core.dao;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.hanshu.employee.common.qto.EmployeeQTO;

*//**
 * Created by yeliming on 16/5/21.
 *//*
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class EmployeeDAOTest {
    @Resource
    private EmployeeDAO employeeDAO;

    @Test
    public void totalRoleGroupCount() throws Exception {
        EmployeeQTO employeeQTO = new EmployeeQTO();
        Map<Long, Long> map = this.employeeDAO.totalRoleGroupCount(employeeQTO);
        System.out.println(JSON.toJSONString(map));
    }

}*/