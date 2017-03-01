package com.mockuai.distributioncenter.core.service.action.sellerconfig;

import com.mockuai.distributioncenter.common.api.BaseRequest;
import com.mockuai.distributioncenter.common.api.DistributionService;
import com.mockuai.distributioncenter.common.api.Request;
import com.mockuai.distributioncenter.common.api.Response;
import com.mockuai.distributioncenter.common.constant.ActionEnum;
import com.mockuai.distributioncenter.common.constant.SellerUpgradeApplyStatus;
import com.mockuai.distributioncenter.common.domain.dto.SellerUpgradeDTO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by yeliming on 16/5/18.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class SellerUpgradeActionTest {
    @Resource
    private DistributionService distributionService;

    private String appKey = "3bc25302234640259fadea047cb7c7d3";

    @Test
    public void AddSellerUpgradeTest() {
        SellerUpgradeDTO sellerUpgradeDTO = new SellerUpgradeDTO();
        sellerUpgradeDTO.setApplicantId(111L);
//        sellerUpgradeDTO.setReason("ABC");
        sellerUpgradeDTO.setStatus(SellerUpgradeApplyStatus.Pending.getValue());
        Request request = new BaseRequest();
        request.setParam("sellerUpgradeDTO", sellerUpgradeDTO);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.ADD_SELLER_UPGRADE.getActionName());
        Response<Long> response = this.distributionService.execute(request);
        Assert.assertTrue(response.isSuccess());
    }

    @Test
    public void agreeSellerupgradeTest(){
        Request request = new BaseRequest();
        request.setParam("id", 1L);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.AGREE_SELLER_UPGRADE.getActionName());
        Response<Void> response = this.distributionService.execute(request);
        Assert.assertTrue(response.isSuccess());
    }

    @Test
    public void rejectSellerupgradeTest(){
        Request request = new BaseRequest();
        request.setParam("id", 1L);
        request.setParam("reason","DD");
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.REJECT_SELLER_UPGRADE.getActionName());
        Response<Void> response = this.distributionService.execute(request);
        Assert.assertTrue(response.isSuccess());
    }
}