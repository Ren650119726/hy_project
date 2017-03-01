package store;

import com.mockuai.shopcenter.api.BaseRequest;
import com.mockuai.shopcenter.api.Request;
import com.mockuai.shopcenter.api.Response;
import com.mockuai.shopcenter.api.ShopService;
import com.mockuai.shopcenter.constant.ActionEnum;
import com.mockuai.shopcenter.constant.PropertyConsts;
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
 * Created by yindingyu on 15/11/13.
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class GetStoreByCoorTest {

    private static final Long SELLER_ID = 38699L;
    private static final String APP_KEY = "af7433eda4883d7139e2934dd8d035f1";

    @Resource
    private ShopService shopService;

    @Test
    public void test01(){


        String longitude = "116.18455";
        String latitude = "39.938867";


        Request shopRequest = new BaseRequest();

        shopRequest.setParam("longitude", longitude);
        shopRequest.setParam("latitude", latitude);
        shopRequest.setParam("sellerId", SELLER_ID);
        shopRequest.setParam("appKey", APP_KEY);


        shopRequest.setCommand(ActionEnum.GET_STORE_BY_COORDINATES.getActionName());

        Response<StoreDTO> shopResponse = shopService.execute(shopRequest);

        assertThat(shopResponse.getCode(),is(10000));
    }

    @Test
    public void test02(){


        String longitude = "116.18455";
        String latitude = "39.938867";


        Request shopRequest = new BaseRequest();

        shopRequest.setParam("longitude", longitude);
        shopRequest.setParam("latitude", latitude);
        shopRequest.setParam("sellerId", SELLER_ID);
        shopRequest.setParam("appKey", APP_KEY);
        shopRequest.setParam("supportRecovery","1");


        shopRequest.setCommand(ActionEnum.GET_STORE_BY_COORDINATES.getActionName());

        Response<StoreDTO> shopResponse = shopService.execute(shopRequest);

        assertThat(shopResponse.getCode(),is(10000));
    }

    @Test
    public void test03(){




        Request shopRequest = new BaseRequest();

        shopRequest.setParam("userId", 38757L);
        shopRequest.setParam("addressId", 254L);
        shopRequest.setParam("sellerId", SELLER_ID);
        shopRequest.setParam("appKey", APP_KEY);
        shopRequest.setParam("condition", PropertyConsts.SUPPORT_DELIVERY);


        shopRequest.setCommand(ActionEnum.GET_STORE_BY_ADDRESS.getActionName());

        Response<StoreDTO> shopResponse = shopService.execute(shopRequest);

        assertThat(shopResponse.getCode(),is(10000));
    }
}
