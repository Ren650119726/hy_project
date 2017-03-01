package com.mockuai.distributioncenter.core.service.action.seller;

import com.aliyun.openservices.ons.api.Message;
import com.mockuai.distributioncenter.common.api.BaseRequest;
import com.mockuai.distributioncenter.common.api.DistributionService;
import com.mockuai.distributioncenter.common.constant.ActionEnum;
import com.mockuai.distributioncenter.common.constant.Operator;
import com.mockuai.distributioncenter.common.domain.dto.OperationDTO;
import com.mockuai.distributioncenter.common.domain.qto.SellerQTO;
import com.mockuai.distributioncenter.core.message.msg.SellerCountUpdateMsg;
import com.mockuai.distributioncenter.core.service.action.UnitTestUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by duke on 16/5/24.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class SellerActionTest {
    private static final String appKey = "6562b5ddf0aed2aad8fe471ce2a2c8a0";

    @Autowired
    private DistributionService distributionService;

    @Test
    public void testGetParentSellerByUserId() throws Exception {
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setParam("userId", 1L);
        baseRequest.setParam("appKey", appKey);
        baseRequest.setCommand(ActionEnum.GET_PARENT_SELLER_BY_USER_ID.getActionName());
        UnitTestUtils.assertAndPrint(distributionService.execute(baseRequest));
    }

    @Test
    public void testCreateSeller() throws Exception {
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setParam("realName", "第一个买家");
        baseRequest.setParam("inviterSellerId", 14L);
        baseRequest.setParam("mobile", "12345678911");
        baseRequest.setParam("password", DigestUtils.md5Hex(DigestUtils.md5Hex("12345678")));
        baseRequest.setParam("wechatId", "1234567890");
        baseRequest.setParam("status", 1);
        baseRequest.setParam("appKey", appKey);
        baseRequest.setCommand(ActionEnum.CREATE_SELLER.getActionName());
        UnitTestUtils.assertAndPrint(distributionService.execute(baseRequest));
    }

    @Test
    public void testUpdateSellerCountListener() throws Exception {
        SellerCountUpdateMsg sellerCountUpdateMsg = new SellerCountUpdateMsg();
        OperationDTO operationDTO = new OperationDTO();
        operationDTO.setDirectCount(1L);
        operationDTO.setGroupCount(1L);
        operationDTO.setMsgIdentify("10");
        operationDTO.setUserId(287484L);
        operationDTO.setOperator(Operator.ADD.getOp());
        sellerCountUpdateMsg.setOperationDTO(operationDTO);
        Message msg = new Message();
        msg.setMsgID("1234567890");
        sellerCountUpdateMsg.setMsg(msg);
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setParam("sellerCountUpdateMsg", sellerCountUpdateMsg);
        baseRequest.setParam("appKey", appKey);
        baseRequest.setCommand(ActionEnum.UPDATE_SELLER_COUNT.getActionName());
        UnitTestUtils.assertAndPrint(distributionService.execute(baseRequest));
    }

    @Test
    public void testGetParentByUserId() throws Exception {
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setParam("userId", 287487L);
        baseRequest.setParam("appKey", appKey);
        baseRequest.setCommand(ActionEnum.GET_PARENT_SELLER_BY_USER_ID.getActionName());
        UnitTestUtils.assertAndPrint(distributionService.execute(baseRequest));
    }

    @Test
    public void testGetPosteritySeller() throws Exception {
        SellerQTO sellerQTO = new SellerQTO();
        sellerQTO.setParentId(1L);
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setParam("sellerQTO", sellerQTO);
        baseRequest.setParam("appKey", appKey);
        baseRequest.setCommand(ActionEnum.GET_POSTERITY_SELLER.getActionName());
        UnitTestUtils.assertAndPrint(distributionService.execute(baseRequest));
    }

    @Test
    public void testGetPosteritySellerByUserId() throws Exception {
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setParam("userId", 1L);
        baseRequest.setParam("appKey", appKey);
        baseRequest.setCommand(ActionEnum.GET_POSTERITY_BY_USER_ID.getActionName());
        UnitTestUtils.assertAndPrint(distributionService.execute(baseRequest));
    }

    @Test
    public void testGetSellerAndInviterByUserId() throws Exception {
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setParam("userId", 287484L);
        baseRequest.setParam("appKey", appKey);
        baseRequest.setCommand(ActionEnum.GET_SELLER_AND_INVITER_BY_USER_ID.getActionName());
        UnitTestUtils.assertAndPrint(distributionService.execute(baseRequest));
    }

    @Test
    public void testGetSellerByUserId() throws Exception {
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setParam("userId", 1841254L);
        baseRequest.setParam("appKey", appKey);
        baseRequest.setCommand(ActionEnum.GET_SELLER_BY_USER_ID.getActionName());
        UnitTestUtils.assertAndPrint(distributionService.execute(baseRequest));
    }

    @Test
    public void testGetTeamSummary() throws Exception {
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setParam("userId", 1L);
        baseRequest.setParam("appKey", appKey);
        baseRequest.setCommand(ActionEnum.GET_TEAM_SUMMARY.getActionName());
        UnitTestUtils.assertAndPrint(distributionService.execute(baseRequest));
    }

    @Test
    public void testQuerySeller() throws Exception {
        BaseRequest baseRequest = new BaseRequest();
        SellerQTO sellerQTO = new SellerQTO();
        baseRequest.setParam("sellerQTO", sellerQTO);
        baseRequest.setParam("appKey", appKey);
        baseRequest.setCommand(ActionEnum.QUERY_SELLER.getActionName());
        UnitTestUtils.assertAndPrint(distributionService.execute(baseRequest));
    }

    @Test
    public void testUpdateSellerRealNameByUserId() throws Exception {
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setParam("userId", 1L);
        baseRequest.setParam("realName", "测试姓名");
        baseRequest.setParam("appKey", appKey);
        baseRequest.setCommand(ActionEnum.UPDATE_SELLER_REAL_NAME_BY_USER_ID.getActionName());
        UnitTestUtils.assertAndPrint(distributionService.execute(baseRequest));
    }
}
