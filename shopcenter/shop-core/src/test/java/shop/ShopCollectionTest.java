package shop;

import com.google.common.collect.Lists;
import com.mockuai.shopcenter.api.BaseRequest;
import com.mockuai.shopcenter.api.Request;
import com.mockuai.shopcenter.api.Response;
import com.mockuai.shopcenter.api.ShopService;
import com.mockuai.shopcenter.constant.ActionEnum;
import com.mockuai.shopcenter.constant.BannerEnum;
import com.mockuai.shopcenter.constant.ElementEnum;
import com.mockuai.shopcenter.domain.dto.BannerDTO;
import com.mockuai.shopcenter.domain.dto.ShopCollectionDTO;
import com.mockuai.shopcenter.domain.dto.ShopDTO;
import com.mockuai.shopcenter.domain.qto.ShopCollectionQTO;
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
public class ShopCollectionTest {

    private static final Long SELLER_ID = 38699L;
    private static final String APP_KEY = "af7433eda4883d7139e2934dd8d035f1";

    @Resource
    private ShopService shopService;

    @Test
    public void test01(){

        Request shopRequest = new BaseRequest();

        shopRequest.setParam("shopIdList",Lists.newArrayList(85L));
        shopRequest.setParam("userId", 38699L);
        shopRequest.setParam("appKey",APP_KEY);

        shopRequest.setCommand(ActionEnum.ADD_SHOP_COLLECTION.getActionName());

        Response<Integer> response = shopService.execute(shopRequest);

        assertThat(response.getCode(),is(10000));
    }

    @Test
    public void test02(){

        Request shopRequest = new BaseRequest();

        shopRequest.setParam("shopIdList",Lists.newArrayList(82L,84L,85L,86L));
        shopRequest.setParam("userId",1333L);
        shopRequest.setParam("appKey",APP_KEY);

        shopRequest.setCommand(ActionEnum.DELETE_SHOP_COLLECTION.getActionName());

        Response<Integer> response = shopService.execute(shopRequest);

        assertThat(response.getCode(),is(10000));
    }

    @Test
    public void test03(){

        Request shopRequest = new BaseRequest();

        ShopCollectionQTO query = new ShopCollectionQTO();

        query.setUserId(1333L);

        shopRequest.setParam("shopCollectionQTO", query);
        shopRequest.setParam("appKey",APP_KEY);

        shopRequest.setCommand(ActionEnum.QUERY_USER_COLLECTION_SHOP.getActionName());

        Response<Integer> response = shopService.execute(shopRequest);

        assertThat(response.getCode(),is(10000));
    }

    @Test
    public void test08(){

        Request shopRequest = new BaseRequest();


        shopRequest.setParam("shopId", 85L);
        shopRequest.setParam("appKey",APP_KEY);

        shopRequest.setCommand(ActionEnum.QUERY_SHOP_ITEM_GROUP_BY_SHOP.getActionName());

        Response response = shopService.execute(shopRequest);

        assertThat(response.getCode(),is(10000));
    }

}
