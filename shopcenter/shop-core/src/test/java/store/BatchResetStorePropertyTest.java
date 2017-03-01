package store;

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
 * Created by yindingyu on 15/12/3.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class BatchResetStorePropertyTest {

    private static final Long SELLER_ID = 38699L;
    private static final String APP_KEY = "af7433eda4883d7139e2934dd8d035f1";

    @Resource
    private ShopService shopService;

    @Test
    public void test01(){

        Request shopRequest = new BaseRequest();

        String key = PropertyConsts.SUPPORT_HOME_RECOVERY;
        String value = "1";

        List<Long> storeIds = Lists.newArrayList(25L,26L,28L);
        shopRequest.setParam("key",key);
        shopRequest.setParam("value",value);
        shopRequest.setParam("sellerId", SELLER_ID);
        shopRequest.setParam("appKey",APP_KEY);
        shopRequest.setParam("storeIds",storeIds);

        shopRequest.setCommand(ActionEnum.BATCH_RESET_STORE_PROPERTY.getActionName());

        Response<Integer> response = shopService.execute(shopRequest);
        assertThat(response.getCode(),is(10000));
    }
}
