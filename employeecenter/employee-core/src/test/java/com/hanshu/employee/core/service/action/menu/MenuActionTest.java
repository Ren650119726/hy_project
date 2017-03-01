/*package com.hanshu.employee.core.service.action.menu;

import com.alibaba.fastjson.JSON;
import com.hanshu.employee.common.action.ActionEnum;
import com.hanshu.employee.common.api.BaseRequest;
import com.hanshu.employee.common.api.EmployeeService;
import com.hanshu.employee.common.api.Request;
import com.hanshu.employee.common.api.Response;
import com.hanshu.employee.common.dto.MenuDTO;
import com.hanshu.employee.common.qto.MenuQTO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.Assert.*;

*//**
 * Created by yeliming on 16/5/19.
 *//*
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class MenuActionTest {

    @Resource
    private EmployeeService employeeService;

    private String appKey = "27c7bc87733c6d253458fa8908001eef";


    @Test
    public void test1(){
        Request request = new BaseRequest();
        request.setParam("url","http://www.baidu.com");
        request.setParam("appKey",appKey);
        request.setCommand(ActionEnum.GET_PARENT_MENU_BY_URL.getActionName());
        Response<MenuDTO> response = this.employeeService.execute(request);
        Assert.assertTrue(response.isSuccess());
        System.out.println(JSON.toJSONString(response));
    }

    @Test
    public void test2(){
        Request request = new BaseRequest();
        request.setParam("url","../seller_info/index.html");
        request.setParam("version","1");
        request.setParam("appKey",appKey);
        request.setCommand(ActionEnum.GET_MENU_BY_URL.getActionName());
        Response<MenuDTO> response = this.employeeService.execute(request);
        Assert.assertTrue(response.isSuccess());
        System.out.println(JSON.toJSONString(response));
    }

    @Test
    public void test3(){
        MenuQTO menuQTO = new MenuQTO();
        menuQTO.setParentId(0L);
        Request request = new BaseRequest();
        request.setParam("appKey",appKey);
        request.setParam("menuQTO",menuQTO);
        request.setCommand(ActionEnum.QUERY_MENU.getActionName());
        Response<List<MenuDTO>> response = this.employeeService.execute(request);
        Assert.assertTrue(response.isSuccess());
        System.out.println(JSON.toJSONString(response));
    }

}*/