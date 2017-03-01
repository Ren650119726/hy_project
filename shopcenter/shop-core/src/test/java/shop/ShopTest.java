package shop;

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

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by luliang on 15/7/29.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class ShopTest {

    @Resource
    private ShopService shopService;

    @Ignore
    public void test001() {
        ShopDTO shopDTO = new ShopDTO();
        shopDTO.setSellerId(91L);
        shopDTO.setSellerName("zx1");
        shopDTO.setShopBannerImageUrl("http://a.t");
        shopDTO.setShopIconUrl("http://b.t");
        shopDTO.setShopName("测试店铺1");
        shopDTO.setShopDesc("测试店铺1");
        shopDTO.setCustomerServicePhone("88888888");
        shopDTO.setShopAddress("浙江杭州");
        shopDTO.setShopStatus(ShopStatusEnum.AUDIT_SUCCESS.getStatus());
        Request request = new BaseRequest();
        request.setParam("shopDTO", shopDTO);
        request.setParam("appKey", "0efa3aa4c1d059043ec35cec5b6625d1");
        request.setCommand(ActionEnum.ADD_SHOP.getActionName());
        Response response = shopService.execute(request);
        System.out.println("**************************************");
        System.out.println("Model:" + response.getModule());
        System.out.println("message:" + response.getMessage());
        System.out.println("ResponseCode:" + response.getCode());
        System.out.println("TotalCount:" + response.getTotalCount());
        System.out.println("**************************************");
    }

    @Test
    public void test002() {
        ShopQTO shopQTO = new ShopQTO();
        shopQTO.setNeedPaging(true);
        shopQTO.setPageSize(20);
        shopQTO.setCurrentPage(1);
        shopQTO.setBizCode("mockuai_demo");
        shopQTO.setShopName("我的店铺");
        Request request = new BaseRequest();
        request.setParam("shopQTO", shopQTO);
        request.setParam("appKey", "af7433eda4883d7139e2934dd8d035f1");
        request.setCommand(ActionEnum.QUERY_SHOP.getActionName());
        Response response = shopService.execute(request);

        System.out.println("**************************************");
        System.out.println("Model:" + response.getModule());
        System.out.println("message:" + response.getMessage());
        System.out.println("ResponseCode:" + response.getCode());
        System.out.println("TotalCount:" + response.getTotalCount());
        System.out.println("**************************************");
    }

    @Test
    public void test003() {
        ShopDTO shopDTO = new ShopDTO();
        shopDTO.setSellerId(85L);
        Request request = new BaseRequest();
        request.setParam("sellerId", 38699L);
        request.setParam("appKey", "af7433eda4883d7139e2934dd8d035f1");
        request.setCommand(ActionEnum.GET_SHOP.getActionName());
        Response response = shopService.execute(request);

        System.out.println("**************************************");
        System.out.println("Model:" + response.getModule());
        System.out.println("message:" + response.getMessage());
        System.out.println("ResponseCode:" + response.getCode());
        System.out.println("TotalCount:" + response.getTotalCount());
        System.out.println("**************************************");
    }

    @Ignore
    public void test004() {
        ShopDTO shopDTO = new ShopDTO();
        shopDTO.setSellerId(91L);
        shopDTO.setShopAddress("浙江省杭州市西湖区");
        Request request = new BaseRequest();
        request.setParam("shopDTO", shopDTO);
        request.setParam("appKey", "0efa3aa4c1d059043ec35cec5b6625d1");
        request.setCommand(ActionEnum.UPDATE_SHOP.getActionName());
        Response response = shopService.execute(request);

        System.out.println("**************************************");
        System.out.println("Model:" + response.getModule());
        System.out.println("message:" + response.getMessage());
        System.out.println("ResponseCode:" + response.getCode());
        System.out.println("TotalCount:" + response.getTotalCount());
        System.out.println("**************************************");
    }

    @Ignore
    public void test005() {
        Request request = new BaseRequest();
        request.setParam("sellerId", 91L);
        request.setParam("appKey", "0efa3aa4c1d059043ec35cec5b6625d1");
        request.setCommand(ActionEnum.GET_SHOP_STATUS.getActionName());
        Response response = shopService.execute(request);

        System.out.println("**************************************");
        System.out.println("Model:" + response.getModule());
        System.out.println("message:" + response.getMessage());
        System.out.println("ResponseCode:" + response.getCode());
        System.out.println("TotalCount:" + response.getTotalCount());
        System.out.println("**************************************");
    }


    @Ignore
    public void test006() {
        Request request = new BaseRequest();
        request.setParam("sellerId", 91L);
        request.setParam("appKey", "0efa3aa4c1d059043ec35cec5b6625d1");
        request.setCommand(ActionEnum.FREEZE_SHOP.getActionName());
        Response response = shopService.execute(request);

        System.out.println("**************************************");
        System.out.println("Model:" + response.getModule());
        System.out.println("message:" + response.getMessage());
        System.out.println("ResponseCode:" + response.getCode());
        System.out.println("TotalCount:" + response.getTotalCount());
        System.out.println("**************************************");
    }

    @Ignore
    public void test007() {
        Request request = new BaseRequest();
        request.setParam("sellerId", 91L);
        request.setParam("appKey", "0efa3aa4c1d059043ec35cec5b6625d1");
        request.setCommand(ActionEnum.THAW_SHOP.getActionName());
        Response response = shopService.execute(request);

        System.out.println("**************************************");
        System.out.println("Model:" + response.getModule());
        System.out.println("message:" + response.getMessage());
        System.out.println("ResponseCode:" + response.getCode());
        System.out.println("TotalCount:" + response.getTotalCount());
        System.out.println("**************************************");
    }

    @Ignore
    public void test008() {
        Request request = new BaseRequest();
        ShopItemGroupDTO shopItemGroupDTO = new ShopItemGroupDTO();
        shopItemGroupDTO.setSellerId(91L);
        shopItemGroupDTO.setShopId(12L);
        shopItemGroupDTO.setDeleteMark(0);
        shopItemGroupDTO.setGroupName("test");
        request.setParam("shopItemGroupDTO", shopItemGroupDTO);
        request.setParam("appKey", "0efa3aa4c1d059043ec35cec5b6625d1");
        request.setCommand(ActionEnum.ADD_SHOP_ITEM_GROUP.getActionName());
        Response response = shopService.execute(request);

        System.out.println("**************************************");
        System.out.println("Model:" + response.getModule());
        System.out.println("message:" + response.getMessage());
        System.out.println("ResponseCode:" + response.getCode());
        System.out.println("TotalCount:" + response.getTotalCount());
        System.out.println("**************************************");
    }

    @Ignore
    public void test009() {
        Request request = new BaseRequest();
        request.setParam("sellerId", 91L);
        request.setParam("id", 1L);
        request.setParam("appKey", "0efa3aa4c1d059043ec35cec5b6625d1");
        request.setCommand(ActionEnum.DELETE_SHOP_ITEM_GROUP.getActionName());
        Response response = shopService.execute(request);

        System.out.println("**************************************");
        System.out.println("Model:" + response.getModule());
        System.out.println("message:" + response.getMessage());
        System.out.println("ResponseCode:" + response.getCode());
        System.out.println("TotalCount:" + response.getTotalCount());
        System.out.println("**************************************");
    }

    @Ignore
    public void test0010() {
        Request request = new BaseRequest();
        ShopItemGroupQTO shopItemGroupQTO = new ShopItemGroupQTO();
        shopItemGroupQTO.setSellerId(91L);
        request.setParam("shopItemGroupQTO", shopItemGroupQTO);
        request.setParam("appKey", "0efa3aa4c1d059043ec35cec5b6625d1");
        request.setCommand(ActionEnum.QUERY_SHOP_ITEM_GROUP.getActionName());
        Response response = shopService.execute(request);

        System.out.println("**************************************");
        System.out.println("Model:" + response.getModule());
        System.out.println("message:" + response.getMessage());
        System.out.println("ResponseCode:" + response.getCode());
        System.out.println("TotalCount:" + response.getTotalCount());
        System.out.println("**************************************");
    }

    @Test
    public void test0011() {
        Request request = new BaseRequest();
        ShopCollectionDTO shopCollectionDTO = new ShopCollectionDTO();
        shopCollectionDTO.setUserId(85L);
        shopCollectionDTO.setSellerId(91L);
        shopCollectionDTO.setShopId(12L);
        shopCollectionDTO.setBizCode(null);
        request.setParam("shopCollectionDTO", shopCollectionDTO);
        request.setParam("appKey", "0efa3aa4c1d059043ec35cec5b6625d1");
        request.setCommand(ActionEnum.ADD_SHOP_COLLECTION.getActionName());
        Response response = shopService.execute(request);

        System.out.println("**************************************");
        System.out.println("Model:" + response.getModule());
        System.out.println("message:" + response.getMessage());
        System.out.println("ResponseCode:" + response.getCode());
        System.out.println("TotalCount:" + response.getTotalCount());
        System.out.println("**************************************");
    }

    @Test
    public void test0012() {
        Request request = new BaseRequest();
        request.setParam("sellerId", 91L);
        request.setParam("userId", 85L);
        request.setParam("appKey", "0efa3aa4c1d059043ec35cec5b6625d1");
        request.setCommand(ActionEnum.CANCEL_SHOP_COLLECTION.getActionName());
        Response response = shopService.execute(request);

        System.out.println("**************************************");
        System.out.println("Model:" + response.getModule());
        System.out.println("message:" + response.getMessage());
        System.out.println("ResponseCode:" + response.getCode());
        System.out.println("TotalCount:" + response.getTotalCount());
        System.out.println("**************************************");
    }

    @Test
    public void test0013() {
        Request request = new BaseRequest();
        request.setParam("sellerId", 91L);
        request.setParam("userId", 85L);
        request.setParam("appKey", "0efa3aa4c1d059043ec35cec5b6625d1");
        request.setCommand(ActionEnum.CHECK_SHOP_COLLECTION.getActionName());
        Response response = shopService.execute(request);

        System.out.println("**************************************");
        System.out.println("Model:" + response.getModule());
        System.out.println("message:" + response.getMessage());
        System.out.println("ResponseCode:" + response.getCode());
        System.out.println("TotalCount:" + response.getTotalCount());
        System.out.println("**************************************");
    }

    @Test
    public void test0014() {
        Request request = new BaseRequest();
        ShopCollectionQTO shopCollectionQTO = new ShopCollectionQTO();
        shopCollectionQTO.setUserId(85L);
        shopCollectionQTO.setSellerId(91L);
        request.setParam("shopCollectionQTO", shopCollectionQTO);
        request.setParam("appKey", "0efa3aa4c1d059043ec35cec5b6625d1");
        request.setCommand(ActionEnum.QUERY_USER_COLLECTION_SHOP.getActionName());
        Response response = shopService.execute(request);

        System.out.println("**************************************");
        System.out.println("Model:" + response.getModule());
        System.out.println("message:" + response.getMessage());
        System.out.println("ResponseCode:" + response.getCode());
        System.out.println("TotalCount:" + response.getTotalCount());
        System.out.println("**************************************");
    }

    @Test
    public void test0015() {
        Request request = new BaseRequest();
        ShopCollectionQTO shopCollectionQTO = new ShopCollectionQTO();
        shopCollectionQTO.setSellerId(91L);
        request.setParam("shopCollectionQTO", shopCollectionQTO);
        request.setParam("appKey", "0efa3aa4c1d059043ec35cec5b6625d1");
        request.setCommand(ActionEnum.QUERY_SHOP_COLLECTION_USER.getActionName());
        Response response = shopService.execute(request);

        System.out.println("**************************************");
        System.out.println("Model:" + response.getModule());
        System.out.println("message:" + response.getMessage());
        System.out.println("ResponseCode:" + response.getCode());
        System.out.println("TotalCount:" + response.getTotalCount());
        System.out.println("**************************************");
    }

    @Test
    public void test0016() {
        Request request = new BaseRequest();
        ShopQTO shopQTO = new ShopQTO();
        shopQTO.setDays(10L);
        request.setParam("appKey", "0efa3aa4c1d059043ec35cec5b6625d1");
        request.setParam("shopQTO", shopQTO);
        request.setCommand(ActionEnum.COUNT_ALL_SHOP.getActionName());
        Response response = shopService.execute(request);

        System.out.println("**************************************");
        System.out.println("Model:" + response.getModule());
        System.out.println("message:" + response.getMessage());
        System.out.println("ResponseCode:" + response.getCode());
        System.out.println("TotalCount:" + response.getTotalCount());
        System.out.println("**************************************");
    }

    @Test
    public void test0017() {
        Request request = new BaseRequest();
        ShopQTO shopQTO = new ShopQTO();
        shopQTO.setDays(10L);
        request.setParam("appKey", "0efa3aa4c1d059043ec35cec5b6625d1");
        request.setParam("shopQTO", shopQTO);
        request.setCommand(ActionEnum.COUNT_LAST_DAYS_SHOP.getActionName());
        Response response = shopService.execute(request);

        System.out.println("**************************************");
        System.out.println("Model:" + response.getModule());
        System.out.println("message:" + response.getMessage());
        System.out.println("ResponseCode:" + response.getCode());
        System.out.println("TotalCount:" + response.getTotalCount());
        System.out.println("**************************************");
    }


    @Test
    public void test0018() {
        Request request = new BaseRequest();
        ShopQTO shopQTO = new ShopQTO();
        shopQTO.setStartTime(new Date(System.currentTimeMillis()-1000 *60 *60 *20000000L));
        shopQTO.setEndTime(new Date());
        request.setParam("appKey", "af7433eda4883d7139e2934dd8d035f1");
        request.setParam("shopQTO", shopQTO);
        request.setCommand(ActionEnum.COUNT_LAST_DAYS_SHOP.getActionName());
        Response response = shopService.execute(request);

        System.out.println("**************************************");
        System.out.println("Model:" + response.getModule());
        System.out.println("message:" + response.getMessage());
        System.out.println("ResponseCode:" + response.getCode());
        System.out.println("TotalCount:" + response.getTotalCount());
        System.out.println("**************************************");
    }

}
