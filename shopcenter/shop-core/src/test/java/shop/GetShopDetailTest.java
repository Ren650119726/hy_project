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
import com.mockuai.shopcenter.domain.dto.ShopDTO;
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
public class GetShopDetailTest {

    private static final Long SELLER_ID = 38699L;
    private static final String APP_KEY = "af7433eda4883d7139e2934dd8d035f1";

    @Resource
    private ShopService shopService;

    @Test
    public void test01(){

        Request shopRequest = new BaseRequest();

        shopRequest.setParam("shopId",85L);
        shopRequest.setParam("userId",38757L);
        shopRequest.setParam("appKey",APP_KEY);

        shopRequest.setCommand(ActionEnum.GET_SHOP_DETAILS.getActionName());

        Response<Integer> response = shopService.execute(shopRequest);

        assertThat(response.getCode(),is(10000));
    }

    @Test
    public void test02(){

        Request shopRequest = new BaseRequest();

        shopRequest.setParam("shopId", 85L);
        shopRequest.setParam("appKey", APP_KEY);

        shopRequest.setCommand(ActionEnum.GET_SHOP_DETAILS.getActionName());

        ShopDTO shopDTO = (ShopDTO) shopService.execute(shopRequest).getModule();

        shopDTO.setItemIdList(Lists.newArrayList(10000L, 10086L));

        shopDTO.setCouponIdList(Lists.newArrayList(1000L,1220L));

        List<BannerDTO> bannerDTOLists = Lists.newArrayList();

        for(int i=0;i<10;i++){
            BannerDTO bannerDTO = new BannerDTO();
            bannerDTO.setBizCode("xx");
            bannerDTO.setSellerId(38699L);
            bannerDTO.setBannerLocation(i);
            bannerDTO.setBannerType(BannerEnum.CAROUSEL_BANNER.getType());
            bannerDTO.setImageDesc("没啥好看的");
            bannerDTO.setImageUrl("xxx" + i);
            bannerDTO.setUrl("xxxx"+i);
            bannerDTO.setParentId(shopDTO.getId());
            bannerDTO.setParentType(ElementEnum.MAIN_PAGE.getType());
            bannerDTOLists.add(bannerDTO);
        }

        shopDTO.setBannerDTOList(bannerDTOLists);

        Request request = new BaseRequest();

        request.setParam("shopDTO",shopDTO);
        request.setParam("appKey", APP_KEY);

        request.setCommand(ActionEnum.SET_SHOP_DETAILS.getActionName());

        Response<Integer> response = shopService.execute(request);

        assertThat(response.getCode(), is(10000));
    }

}
