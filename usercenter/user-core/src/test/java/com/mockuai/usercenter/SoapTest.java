//package com.mockuai.usercenter;
//
//import com.mockuai.external.webservice.client.huaji.CRMInterfaceStub;
//import com.mockuai.usercenter.core.employee.MigrateIntegralManager;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import javax.annotation.Resource;
//
///**
// * Created by duke on 15/11/17.
// */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
//public class SoapTest {
//    @Test
//    public void test1() throws Exception {
//        CRMInterfaceStub stub = new CRMInterfaceStub();
//        CRMInterfaceStub.GetPoint getPoint = new CRMInterfaceStub.GetPoint();
//        CRMInterfaceStub.AuthHeader authHeader = new CRMInterfaceStub.AuthHeader();
//        CRMInterfaceStub.AuthHeaderE authHeaderE = new CRMInterfaceStub.AuthHeaderE();
//        authHeader.setUserName("POS2CRMITF");
//        authHeader.setPassword("A5BFA54BC0C196C43757D943BD82F4AD");
//        authHeaderE.setAuthHeader(authHeader);
//        getPoint.setMemberId("1");
//        CRMInterfaceStub.GetPointResponse response = stub.getPoint(getPoint, authHeaderE);
//        System.out.println(response.getGetPointResult());
//        System.out.println(response.getErrorMessage());
//    }
//
//    @Test
//    public void test2() throws Exception {
//        CRMInterfaceStub stub = new CRMInterfaceStub();
//
//        CRMInterfaceStub.AuthMember authMember = new CRMInterfaceStub.AuthMember();
//        authMember.setPassword("A5BFA54BC0C196C43757D943BD82F4AD");
//        authMember.setMemberId("POS2CRMITF");
//        CRMInterfaceStub.AuthMemberResponse authMemberResponse = stub.authMember(authMember);
//
//        System.out.println(authMemberResponse.getAuthMemberResult());
//        System.out.println(authMemberResponse.getErrorMessage());
//
//        CRMInterfaceStub.IsExistMember isExistMember = new CRMInterfaceStub.IsExistMember();
//        CRMInterfaceStub.AuthHeaderE authHeaderE = new CRMInterfaceStub.AuthHeaderE();
//        CRMInterfaceStub.AuthHeader authHeader = new CRMInterfaceStub.AuthHeader();
//        authHeader.setUserName("POS2CRMITF");
//        authHeader.setPassword(authMemberResponse.getAuthMemberResult());
//        isExistMember.setMemberName("15249274838");
//        isExistMember.setCategoryCode(1);
//        isExistMember.setMemberNameType(1);
//        authHeaderE.setAuthHeader(authHeader);
//        CRMInterfaceStub.IsExistMemberResponse isExistMemberResponse = stub.isExistMember(isExistMember, authHeaderE);
//        System.out.println(isExistMemberResponse.getIsExistMemberResult());
//        System.out.println(isExistMemberResponse.getErrorMessage());
//    }
//
//    @Resource
//    private MigrateIntegralManager migrateIntegralManager;
//
//    @Test
//    public void test3() throws Exception {
//        Long integral = migrateIntegralManager.getIntergalByMobile("15249274838");
//        System.out.println(integral);
//    }
//}
