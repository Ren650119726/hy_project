package support;

import com.mockuai.itemcenter.common.api.BaseRequest;
import com.mockuai.itemcenter.common.api.ItemService;
import com.mockuai.itemcenter.common.api.Request;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.shopcenter.api.ShopService;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by yindingyu on 15/11/13.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class ShopConfigTest {

    private static final Long SELLER_ID = 38699L;
    private static final String APP_KEY = "af7433eda4883d7139e2934dd8d035f1";

    @Resource
    private ItemService itemService;

    @Test
    public void test01(){
        Request request = new BaseRequest();
        request.setCommand(ActionEnum.GET_STORE_SUPPORT_CONFIG.getActionName());
        request.setParam("appKey", APP_KEY);
        request.setParam("sellerId", SELLER_ID);
        request.setParam("key","support_store");

        Response<String> response = itemService.execute(request);

        assertThat(response.getCode(),is(10000));
    }




}
