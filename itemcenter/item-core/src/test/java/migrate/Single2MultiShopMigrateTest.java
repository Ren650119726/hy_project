package migrate;

import com.mockuai.itemcenter.common.api.ItemService;
import com.mockuai.itemcenter.common.api.Request;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import util.AppKeyEnum;
import util.RequestFactory;

import javax.annotation.Resource;

/**
 * Created by yindingyu on 16/2/25.
 */


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class Single2MultiShopMigrateTest {

    @Resource
    private ItemService itemService;

    @Test
    public void test110() {

        Request request = RequestFactory.newRequest(AppKeyEnum.HAODADA, ActionEnum.SINGLE_2_MULTI_SHOP_MIGRATE);

        request.setParam("sellerId", 39127L);


        Response<Long> response = itemService.execute(request);

        System.currentTimeMillis();

    }
}
