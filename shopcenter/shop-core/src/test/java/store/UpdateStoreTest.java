package store;

import com.mockuai.shopcenter.api.BaseRequest;
import com.mockuai.shopcenter.api.Request;
import com.mockuai.shopcenter.api.Response;
import com.mockuai.shopcenter.api.ShopService;
import com.mockuai.shopcenter.constant.ActionEnum;
import com.mockuai.shopcenter.domain.dto.StoreDTO;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by yindingyu on 15/11/5.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class UpdateStoreTest {

    private static final Long SELLER_ID = 9977L;
    private static final String APP_KEY = "af7433eda4883d7139e2934dd8d035f1";

    @Resource
    private ShopService shopService;

    @Test
    public void test01(){

        Request shopRequest = new BaseRequest();

        StoreDTO storeDTO = new StoreDTO();
        storeDTO.setId(4L);
        storeDTO.setStoreNumber("xx22244");
        storeDTO.setStoreName("凉皮专卖店");
        storeDTO.setStoreImage("xxxxrr");
        storeDTO.setOwnerId(435L);
        storeDTO.setSellerId(SELLER_ID);
        storeDTO.setLongitude("100000");
        storeDTO.setLatitude("62000");
        storeDTO.setAddress("皇后大道西");
        storeDTO.setCountryCode("ax");
        storeDTO.setProvinceCode("bx");
        storeDTO.setCityCode("cx");
        storeDTO.setTownCode("dx");
        storeDTO.setAreaCode("ex");
        storeDTO.setSupportDelivery(0);
        storeDTO.setSupportPickUp(0);
        storeDTO.setDeliveryRange(12000);
        storeDTO.setServiceDesc("啥都不能干");
        storeDTO.setServiceTime("全年不开业");

        shopRequest.setParam("storeDTO",storeDTO);
        shopRequest.setParam("sellerId", SELLER_ID);
        shopRequest.setParam("appKey",APP_KEY);

        shopRequest.setCommand(ActionEnum.UPDATE_STORE.getActionName());

        Response<Integer> response = shopService.execute(shopRequest);

        assertThat(response.getCode(),is(10000));
    }

}
