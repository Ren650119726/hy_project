package shopproperty;

import com.google.common.collect.Lists;
import com.mockuai.shopcenter.api.BaseRequest;
import com.mockuai.shopcenter.api.Request;
import com.mockuai.shopcenter.api.Response;
import com.mockuai.shopcenter.api.ShopService;
import com.mockuai.shopcenter.constant.ActionEnum;
import com.mockuai.shopcenter.constant.PropertyConsts;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


/**
 * Created by yindingyu on 15/11/5.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class GetShopPropertiesTest {

    private static final Long SELLER_ID = 38699L;
    private static final String APP_KEY = "af7433eda4883d7139e2934dd8d035f1";

    @Resource
    private ShopService shopService;

    @Test
    public void test01(){

        Request shopRequest = new BaseRequest();

        List<String> keys = Lists.newArrayList(
                PropertyConsts.RECOVERY_RECEIVER,
                PropertyConsts.RECOVERY_MOBILE,
                PropertyConsts.RECOVERY_ADDRESS,
                PropertyConsts.RECOVERY_RANGE,
                PropertyConsts.SUPPORT_HOME_RECOVERY,
                PropertyConsts.SUPPORT_STORE_RECOVERY,
                "xxbb"
        );
        shopRequest.setParam("keys",keys);
        shopRequest.setParam("sellerId", SELLER_ID);
        shopRequest.setParam("appKey",APP_KEY);

        shopRequest.setCommand(ActionEnum.GET_SHOP_PROPERTIES.getActionName());

        Response<Integer> response = shopService.execute(shopRequest);

        assertThat(response.getCode(),is(10000));
    }

    @Test
    public void test02(){

        Request shopRequest = new BaseRequest();

        List<String> keys = Lists.newArrayList(
                PropertyConsts.REPAIR_RECEIVER,
                PropertyConsts.REPAIR_MOBILE,
                PropertyConsts.REPAIR_ADDRESS,
                PropertyConsts.REPAIR_RANGE,
                PropertyConsts.SUPPORT_HOME_REPAIR,
                PropertyConsts.SUPPORT_STORE_REPAIR
        );

        shopRequest.setParam("keys",keys);
        shopRequest.setParam("sellerId", SELLER_ID);
        shopRequest.setParam("appKey",APP_KEY);

        shopRequest.setCommand(ActionEnum.GET_SHOP_PROPERTIES.getActionName());

        Response<Integer> response = shopService.execute(shopRequest);

        assertThat(response.getCode(), is(10000));
    }


}
