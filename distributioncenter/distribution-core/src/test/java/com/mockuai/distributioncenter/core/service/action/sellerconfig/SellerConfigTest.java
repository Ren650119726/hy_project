package com.mockuai.distributioncenter.core.service.action.sellerconfig;

import com.alibaba.fastjson.JSON;
import com.mockuai.distributioncenter.common.api.BaseRequest;
import com.mockuai.distributioncenter.common.api.DistributionService;
import com.mockuai.distributioncenter.common.api.Request;
import com.mockuai.distributioncenter.common.api.Response;
import com.mockuai.distributioncenter.common.constant.ActionEnum;
import com.mockuai.distributioncenter.common.domain.dto.SellerConfigDTO;
import com.mockuai.distributioncenter.common.domain.qto.SellerConfigQTO;
import com.mockuai.distributioncenter.core.service.action.Action;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yeliming on 16/5/18.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class SellerConfigTest {

    @Resource
    private DistributionService distributionService;
    private String appKey = "3bc25302234640259fadea047cb7c7d3";
    @Test
    public void testQueryShopGroup(){
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setCommand(ActionEnum.GET_SHOP_GROUP.getActionName());
        baseRequest.setParam("appKey", appKey);
        Response<Long> response = this.distributionService.execute(baseRequest);
        Assert.assertTrue(response.isSuccess());
    }


    @Test
    public void addSellerConfig() throws Exception {
        SellerConfigDTO sellerConfigDTO = new SellerConfigDTO();
        sellerConfigDTO.setLevel(1);
        sellerConfigDTO.setLevelName("11");
        sellerConfigDTO.setUnderCount(11);
        sellerConfigDTO.setTeamCount(22);

        Request request = new BaseRequest();
        request.setParam("sellerConfigDTO", sellerConfigDTO);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.ADD_SELLER_CONFIG.getActionName());
        Response<Long> response = this.distributionService.execute(request);
        Assert.assertTrue(response.isSuccess());
    }

    @Test
    public void updateSellerConfig() throws Exception {
        SellerConfigDTO sellerConfigDTO = new SellerConfigDTO();
        sellerConfigDTO.setId(1L);
        sellerConfigDTO.setLevel(1);
        sellerConfigDTO.setLevelName("2");
        sellerConfigDTO.setUnderCount(22);
        sellerConfigDTO.setTeamCount(222);

        Request request = new BaseRequest();
        request.setParam("sellerConfigDTO", sellerConfigDTO);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.UPDATE_SELLER_CONFIG.getActionName());
        Response<Boolean> response = this.distributionService.execute(request);
        Assert.assertTrue(response.isSuccess());
    }

    @Test
    public void querySellerConfig() throws Exception {
        SellerConfigQTO sellerConfigQTO = new SellerConfigQTO();
        Request request = new BaseRequest();
        request.setParam("sellerConfigQTO", sellerConfigQTO);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.QUERY_SELLER_CONFIG.getActionName());
        Response<List<SellerConfigDTO>> response = this.distributionService.execute(request);
        Assert.assertTrue(response.isSuccess());
        System.out.println(JSON.toJSONString(response));
    }

    @Test
    public void getSellerConfig() throws Exception {
        Request request = new BaseRequest();
        request.setParam("id", 1L);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.GET_SELLER_CONFIG.getActionName());
        Response<SellerConfigDTO> response = this.distributionService.execute(request);
        Assert.assertTrue(response.isSuccess());
        System.out.println(JSON.toJSONString(response));

    }

}