package sellerbrand;

import javax.annotation.Resource;

import com.google.common.collect.Lists;
import com.mockuai.itemcenter.common.domain.qto.SellerBrandQTO;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mockuai.itemcenter.common.api.BaseRequest;
import com.mockuai.itemcenter.common.api.ItemService;
import com.mockuai.itemcenter.common.api.Request;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.domain.dto.SellerBrandDTO;
import util.AppKeyEnum;
import util.RequestFactory;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class AddSellerBrandTest {
	@Resource
	private ItemService itemService;

	@Test
	public void addTest() {

		Request request = RequestFactory.newRequest(AppKeyEnum.MOCKUAI_DEMO,ActionEnum.ADD_SELLER_BRAND);

        SellerBrandDTO sellerBrandDTO = new SellerBrandDTO();

        sellerBrandDTO.setBrandDesc("这是一个品牌");
        sellerBrandDTO.setCategoryIdList(Lists.newArrayList(23L, 11L, 2L));
        sellerBrandDTO.setBrandName("品牌C");
        sellerBrandDTO.setLogo("https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/logo_white_fe6da1ec.png");

		request.setParam("sellerBrandDTO", sellerBrandDTO);
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}

    @Test
    public void UpdateTest() {

        Request request = RequestFactory.newRequest(AppKeyEnum.MOCKUAI_DEMO,ActionEnum.UPDATE_SELLER_BRAND);

        SellerBrandDTO sellerBrandDTO = new SellerBrandDTO();

        sellerBrandDTO.setBrandDesc("这是一个品牌");
        sellerBrandDTO.setCategoryIdList(Lists.newArrayList(23L, 11L, 2L));
        sellerBrandDTO.setId(2618L);
        sellerBrandDTO.setBrandName("品牌C");
        sellerBrandDTO.setLogo("https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/logo_white_fe6da1ec.png");

        request.setParam("sellerBrandDTO", sellerBrandDTO);
        request.setParam("updateCategory","1");
        Response response = itemService.execute(request);
        System.out.println("**************************************");
        System.out.println("Model:" + response.getModule());
        System.out.println("message:" + response.getMessage());
        System.out.println("ResponseCode:" + response.getCode());
        System.out.println("TotalCount:" + response.getTotalCount());
        System.out.println("**************************************");
    }

    @Test
    public void getTest() {

        Request request = RequestFactory.newRequest(AppKeyEnum.MOCKUAI_DEMO,ActionEnum.GET_SELLER_BRAND);

        request.setParam("id", 2618L);
        request.setParam("needCategory","1");
        Response response = itemService.execute(request);
        System.out.println("**************************************");
        System.out.println("Model:" + response.getModule());
        System.out.println("message:" + response.getMessage());
        System.out.println("ResponseCode:" + response.getCode());
        System.out.println("TotalCount:" + response.getTotalCount());
        System.out.println("**************************************");
    }

    @Test
    public void queryTest() {
     Request request = RequestFactory.newRequest(AppKeyEnum.HAIYUN,ActionEnum.QUERY_SELLER_BRAND);

        SellerBrandQTO sellerBrandQTO = new SellerBrandQTO();
        sellerBrandQTO.setCategoryId(2L);

        request.setParam("sellerBrandQTO",sellerBrandQTO);
        Response response = itemService.execute(request);
        System.out.println("**************************************");
        System.out.println("Model:" + response.getModule());
        System.out.println("message:" + response.getMessage());
        System.out.println("ResponseCode:" + response.getCode());
        System.out.println("TotalCount:" + response.getTotalCount());
        System.out.println("**************************************");
    }

    @Test
    public void deleteTest() {
        Request request = RequestFactory.newRequest(AppKeyEnum.HAIYUN,ActionEnum.QUERY_SELLER_BRAND);

        SellerBrandQTO sellerBrandQTO = new SellerBrandQTO();
        sellerBrandQTO.setCategoryId(2L);

        request.setParam("sellerBrandQTO", sellerBrandQTO);
        Response response = itemService.execute(request);
        System.out.println("**************************************");
        System.out.println("Model:" + response.getModule());
        System.out.println("message:" + response.getMessage());
        System.out.println("ResponseCode:" + response.getCode());
        System.out.println("TotalCount:" + response.getTotalCount());
        System.out.println("**************************************");
    }
}
