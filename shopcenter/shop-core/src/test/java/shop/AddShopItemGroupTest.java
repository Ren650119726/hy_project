package shop;

import com.google.common.collect.Lists;
import com.mockuai.shopcenter.api.BaseRequest;
import com.mockuai.shopcenter.api.Request;
import com.mockuai.shopcenter.api.Response;
import com.mockuai.shopcenter.api.ShopService;
import com.mockuai.shopcenter.constant.ActionEnum;
import com.mockuai.shopcenter.constant.ShopStatusEnum;
import com.mockuai.shopcenter.domain.dto.ShopCollectionDTO;
import com.mockuai.shopcenter.domain.dto.ShopDTO;
import com.mockuai.shopcenter.domain.dto.ShopItemGroupDTO;
import com.mockuai.shopcenter.domain.qto.ShopCollectionQTO;
import com.mockuai.shopcenter.domain.qto.ShopItemGroupQTO;
import com.mockuai.shopcenter.domain.qto.ShopQTO;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import util.AppKeyEnum;
import util.RequestFactory;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by luliang on 15/7/29.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class AddShopItemGroupTest {

    @Resource
    private ShopService shopService;

    @Test
    public void test001() {

        Request request = RequestFactory.newRequest(AppKeyEnum.HANSHU,ActionEnum.ADD_SHOP_ITEM_GROUP);


        ShopItemGroupDTO shopItemGroupDTO = new ShopItemGroupDTO();
        shopItemGroupDTO.setItemIdList(Lists.newArrayList(1L,2L,3L));
        shopItemGroupDTO.setGroupName("分组a");
        shopItemGroupDTO.setShopId(0L);

        request.setParam("shopItemGroupDTO",shopItemGroupDTO);

        Response<Long> response = shopService.execute(request);


        System.currentTimeMillis();
    }

    @Test
    public void test002() {

        Request request = RequestFactory.newRequest(AppKeyEnum.HANSHU,ActionEnum.QUERY_SHOP_ITEM_GROUP);

        ShopItemGroupQTO qto = new ShopItemGroupQTO();

        qto.setGroupName("66666");
        qto.setSellerId(1841254L);
        qto.setNeedPaging(true);
        qto.setPageSize(3);
        qto.setCurrentPage(2);

        request.setParam("shopItemGroupQTO",qto);

        Response<Long> response = shopService.execute(request);


        System.currentTimeMillis();
    }


}
