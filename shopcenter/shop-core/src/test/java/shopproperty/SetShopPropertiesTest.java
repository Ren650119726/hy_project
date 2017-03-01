package shopproperty;

import com.google.common.collect.ImmutableMap;
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

import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by yindingyu on 15/11/5.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class SetShopPropertiesTest {

    private static final Long SELLER_ID = 38699L;
    private static final String APP_KEY = "af7433eda4883d7139e2934dd8d035f1";

    @Resource
    private ShopService shopService;

    @Test
    public void test01(){

        Request shopRequest = new BaseRequest();

        Map<String,String> props = ImmutableMap
                .<String,String>builder()
                .put(PropertyConsts.RECOVERY_RECEIVER,"张二彪")
                .put(PropertyConsts.RECOVERY_MOBILE, "1234667890333")
                .put(PropertyConsts.RECOVERY_ADDRESS, "凤翔路3336号")
                .put(PropertyConsts.RECOVERY_RANGE,"250")
                .put(PropertyConsts.SUPPORT_HOME_RECOVERY,"1")
                .put(PropertyConsts.SUPPORT_STORE_RECOVERY,"2")
                .build();

        shopRequest.setParam("props",props);
        shopRequest.setParam("sellerId", SELLER_ID);
        shopRequest.setParam("appKey",APP_KEY);

        shopRequest.setCommand(ActionEnum.SET_SHOP_PROPERTIES.getActionName());

        Response<Integer> response = shopService.execute(shopRequest);

        assertThat(response.getCode(),is(10000));
    }

    @Test
    public void test02(){

        Request shopRequest = new BaseRequest();

        Map<String,String> props = ImmutableMap
                .<String,String>builder()
                .put(PropertyConsts.REPAIR_RECEIVER,"张二二彪")
                .put(PropertyConsts.REPAIR_MOBILE,"1334667890333")
                .put(PropertyConsts.REPAIR_ADDRESS,"凤翔路336号")
                .put(PropertyConsts.REPAIR_RANGE,"220")
                .put(PropertyConsts.SUPPORT_HOME_REPAIR,"2")
                .put(PropertyConsts.SUPPORT_STORE_REPAIR,"1")
                .build();

        shopRequest.setParam("props",props);
        shopRequest.setParam("sellerId", SELLER_ID);
        shopRequest.setParam("appKey",APP_KEY);

        shopRequest.setCommand(ActionEnum.SET_SHOP_PROPERTIES.getActionName());

        Response<Integer> response = shopService.execute(shopRequest);

        assertThat(response.getCode(),is(10000));
    }
}
