/*package com.hanshu.employee.core.service.action.employeelog;

import com.alibaba.fastjson.JSON;
import com.hanshu.employee.common.action.ActionEnum;
import com.hanshu.employee.common.api.BaseRequest;
import com.hanshu.employee.common.api.EmployeeService;
import com.hanshu.employee.common.api.Request;
import com.hanshu.employee.common.api.Response;
import com.hanshu.employee.common.constant.OpActionEnum;
import com.hanshu.employee.common.constant.OpObjTypeEnum;
import com.hanshu.employee.common.constant.OpResultEnum;
import com.hanshu.employee.common.dto.EmployeeLogDTO;
import com.hanshu.employee.common.dto.MenuDTO;
import com.hanshu.employee.common.qto.EmployeeLogQTO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

*//**
 * Created by yeliming on 16/5/19.
 *//*
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class EmployeeLogActionTest {
    @Resource
    private EmployeeService employeeService;

    private String appKey = "4f5508d72d9d78c9242bf1c867ac1063";

    @Test
    public void test1(){
        EmployeeLogDTO employeeLogDTO = new EmployeeLogDTO();
        employeeLogDTO.setOperatorId(3L);
        employeeLogDTO.setOperator("AA");
        employeeLogDTO.setOpAction(OpActionEnum.ADD.getCode());
        employeeLogDTO.setObjType(OpObjTypeEnum.ITEM.getCode());
        employeeLogDTO.setObjId(333L);
//        employeeLogDTO.setObjName("test item");
        employeeLogDTO.setOpResult(OpResultEnum.SUCCESS.getCode());
        employeeLogDTO.setIpAddr("127.0.0.1");
        employeeLogDTO.setOperateJson("ABC");
        employeeLogDTO.setComments("coment");

        Request request = new BaseRequest();
        request.setParam("employeeLogDTO",employeeLogDTO);
        request.setParam("appKey",appKey);
        request.setCommand(ActionEnum.GEN_EMPLOYEE_OP_LOG.getActionName());
        Response<MenuDTO> response = this.employeeService.execute(request);
        Assert.assertTrue(response.isSuccess());
        System.out.println(JSON.toJSONString(response));
    }

    @Test
    public void test2(){
        EmployeeLogQTO employeeLogQTO = new EmployeeLogQTO();
        employeeLogQTO.setOffset(0L);
        employeeLogQTO.setCount(100);
        employeeLogQTO.setOperatorId(3L);

        Request request = new BaseRequest();
        request.setParam("employeeLogQTO",employeeLogQTO);
        request.setParam("appKey",appKey);
        request.setCommand(ActionEnum.QUERY_EMPLOYEE_OP_LOG.getActionName());
        Response<MenuDTO> response = this.employeeService.execute(request);
        Assert.assertTrue(response.isSuccess());
        System.out.println(JSON.toJSONString(response));
    }
}*/