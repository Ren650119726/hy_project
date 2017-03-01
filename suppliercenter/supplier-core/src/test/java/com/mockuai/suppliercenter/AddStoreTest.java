package com.mockuai.suppliercenter;

import com.mockuai.common.uils.StarterRunner;
import com.mockuai.suppliercenter.common.action.ActionEnum;
import com.mockuai.suppliercenter.common.api.BaseRequest;
import com.mockuai.suppliercenter.common.api.Request;
import com.mockuai.suppliercenter.common.api.Response;
import com.mockuai.suppliercenter.common.api.SupplierDispatchService;
import com.mockuai.suppliercenter.common.dto.StoreDTO;
import com.mockuai.suppliercenter.core.dao.StoreDAO;
import com.mockuai.suppliercenter.core.domain.StoreDO;
import com.mockuai.suppliercenter.core.util.JsonUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class AddStoreTest {

    @Resource
    private SupplierDispatchService supplierDispatchService;

    @Resource
    private StoreDAO storeDAO;

    private static final String APP_NAME = "supplier";
    private static final String LOCAL_PROPERTIES = "e:/haiyn/haiynParent/haiyn_properties/supplier/haiyn.properties";


    static {
        try {
            StarterRunner.localSystemProperties(APP_NAME,LOCAL_PROPERTIES,new String[]{});
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    //
//	/**
//	 * 参数正确的情况下添加用户 测试结果:成功添加
//	 * */
    @Test
    public void addTest() {
        Request request = new BaseRequest();
        StoreDTO storeDTO = new StoreDTO();
        storeDTO.setName("3333333333");
        storeDTO.setSupplierId(22L);
        storeDTO.setSupplierName("77776");
        storeDTO.setType(1);
        storeDTO.setProvinceCode("13");
        storeDTO.setCityCode("130300000000");
        storeDTO.setAreaCode("130303000000");
        storeDTO.setAddress("银泰城11");
        storeDTO.setStatus(0);


        request.setCommand(ActionEnum.ADD_STORE.getActionName());
        request.setParam("storeDTO", storeDTO);
        request.setParam("appKey", "6562b5ddf0aed2aad8fe471ce2a2c8a0");

        Response response = supplierDispatchService.execute(request);
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%response:" + JsonUtil.toJson(response));
        Assert.assertNotNull(response.getModule());
    }

    @Test
    public void getStore() {

        StoreDO storeDO1 = storeDAO.getStoreById(23L);
        StoreDO storeDO2 = storeDAO.getStoreById(23L);

        storeDO1.setStatus(2);
        storeDO1.setName("33333333334sdds");
        int updateResult1 = storeDAO.updateGoodsUseCAS(storeDO1);

        System.out.println("修改商品信息1" + (updateResult1 == 1 ? "成功" : "失败"));

        storeDO2.setStatus(2);
        storeDO2.setName("33333333334sdds");

        int updateResult2 = storeDAO.updateGoodsUseCAS(storeDO2);
        System.out.println("修改商品信息2" + (updateResult2 == 1 ? "成功" : "失败"));


    }


}
