package shopproperty;

import com.mockuai.shopcenter.api.BaseRequest;
import com.mockuai.shopcenter.api.Request;
import com.mockuai.shopcenter.api.Response;
import com.mockuai.shopcenter.api.ShopService;
import com.mockuai.shopcenter.constant.ActionEnum;
import com.mockuai.shopcenter.core.service.ShopRequest;
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
public class GetShopPropertyTest {

    private static final Long SELLER_ID = 9977L;
    private static final String APP_KEY = "af7433eda4883d7139e2934dd8d035f1";

    @Resource
    private ShopService shopService;

    @Test
    public void test01(){

        Request shopRequest = new BaseRequest();
        shopRequest.setParam("key","xxxxxfxx");
        shopRequest.setParam("sellerId", SELLER_ID);
        shopRequest.setParam("appKey",APP_KEY);

        shopRequest.setCommand(ActionEnum.GET_SHOP_PROPERTY.getActionName());

        Response<Integer> response = shopService.execute(shopRequest);

        assertThat(response.getCode(),is(10000));
    }

    @Test
    public void test02(){

        Request shopRequest = new BaseRequest();
        shopRequest.setParam("key","xxxxxxx");
        shopRequest.setParam("sellerId", SELLER_ID);
        shopRequest.setParam("appKey",APP_KEY);

        shopRequest.setCommand(ActionEnum.GET_SHOP_PROPERTY.getActionName());

        Response<Integer> response = shopService.execute(shopRequest);

        assertThat(response.getCode(), is(10000));
    }


}
