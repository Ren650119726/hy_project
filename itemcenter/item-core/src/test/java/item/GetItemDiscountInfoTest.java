package item;

import com.mockuai.itemcenter.common.api.BaseRequest;
import com.mockuai.itemcenter.common.api.ItemService;
import com.mockuai.itemcenter.common.api.Request;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.core.manager.MarketingManager;
import com.mockuai.itemcenter.core.util.JsonUtil;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import util.AppKeyEnum;
import util.RequestFactory;

import javax.annotation.Resource;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class GetItemDiscountInfoTest {
	@Resource
	private ItemService itemService;

    @Resource
    private MarketingManager marketingManager;

	@Test
	/**
	 * 正常GET
	 */
	public void test001() {
		Request request = new BaseRequest();
		request.setParam("id", 1227L);
		request.setParam("supplierId", 1841254L);
		request.setParam("needDetail", false);
		request.setParam("appKey",AppKeyEnum.HAIYUN.getAppKey());
		request.setCommand(ActionEnum.GET_ITEM.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
		ItemDTO item  = (ItemDTO)(response.getModule());
		System.out.println(item.getItemImageDTOList().get(0));
	}

	
	/**
	 * ID为空
	 */
	public void test002() {
		Request request = new BaseRequest();
		request.setParam("sellerId", 1L);
		request.setCommand(ActionEnum.GET_ITEM.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}

	
	/**
	 * sellerId为空
	 */
	public void test003() {
		Request request = new BaseRequest();
		request.setParam("ID", 22L);
		request.setCommand(ActionEnum.GET_ITEM.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}

	
	/**
	 * ID在数据库中不存在
	 */
	public void test004() {
		Request request = new BaseRequest();
		request.setParam("ID", 99999L);
		request.setParam("sellerId", 1L);
		request.setCommand(ActionEnum.GET_ITEM.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}

//    @Test
//    public void  test005(){
//
//        ItemDTO itemDTO = new ItemDTO();
//        itemDTO.setBizCode("mockuai_demo");
//        itemDTO.setSellerId(38699L);
//        itemDTO.setId(101217L);
//        itemDTO.setPromotionPrice(1000L);
//
//        try {
//            marketingManager.queryItemDiscountInfo(itemDTO,"af7433eda4883d7139e2934dd8d035f1");
//            System.currentTimeMillis();
//        } catch (ItemException e) {
//            e.printStackTrace();
//        }
//    }

	/**
	 * sellerId为空
	 */
    @Test
	public void test006() {
		Request request = new BaseRequest();
		request.setParam("id", 101450L);
		request.setParam("supplierId",38699L);
		request.setParam("userId",30045L);
        request.setParam("appKey","af7433eda4883d7139e2934dd8d035f1");
		request.setCommand(ActionEnum.GET_ITEM.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}

	@Test
	public void test007() {
        Request request = RequestFactory.newRequest(AppKeyEnum.MOCKUAI_DEMO, ActionEnum.GET_ITEM, 38699L, 38699L);
        request.setParam("id", 153832L);
        request.setParam("needDetail", true);

		request.setCommand(ActionEnum.GET_ITEM.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
        System.out.println(JsonUtil.toJson(response.getModule()));
        System.out.println("**************************************");

        Request request1 = RequestFactory.newRequest(AppKeyEnum.MOCKUAI_DEMO, ActionEnum.QUERY_ITEM_DISCOUNT_INFO);

        request1.setParam("itemDTO", response.getModule());

        Response response1 = itemService.execute(request1);


        System.currentTimeMillis();
    }

	@Test
	public void test008() {
		Request request = RequestFactory.newRequest(AppKeyEnum.HAIYUN, ActionEnum.GET_ITEM);
		request.setParam("id", 28956L);
		request.setParam("distributorId",11L);
		request.setParam("needDetail", true);
		request.setParam("supplierId",1841254L);

		request.setCommand(ActionEnum.GET_ITEM.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println(JsonUtil.toJson(response.getModule()));
		System.out.println("**************************************");

		Request request1 = RequestFactory.newRequest(AppKeyEnum.MOCKUAI_DEMO, ActionEnum.QUERY_ITEM_DISCOUNT_INFO);

		request1.setParam("itemDTO", response.getModule());

		Response response1 = itemService.execute(request1);


		System.currentTimeMillis();
	}
	@Test
	/**
	 * 正常GET
	 */
	public void test009() {
		Request request = new BaseRequest();

		ItemDTO itemDTO = new ItemDTO();
		itemDTO.setSellerId(1841254L);
		itemDTO.setItemType(1);
		itemDTO.setId(28954L);
		itemDTO.setPromotionPrice(200L);
		itemDTO.setVirtualMark(0);


		request.setParam("itemDTO", itemDTO);
		request.setParam("appKey", AppKeyEnum.HAIYUN.getAppKey());
		request.setCommand(ActionEnum.QUERY_ITEM_DISCOUNT_INFO.getActionName());
		Response<ItemDTO> response = itemService.execute(request);

//		ItemDTO x = response.getModule();
//		Date x1 =  new Date();
//
//		x1.setTime(new Date().getTime() + 26000000L);
//		x.setSaleBegin(x1);
//
//		Date x2 =  new Date();
//		x2.setTime(new Date().getTime() + 96000000L);
//		x.setSaleEnd(x2);
//
//		request.setParam("itemDTO",x);
//		request.setCommand(ActionEnum.UPDATE_ITEM.getActionName());
//        request.setParam("updateDetail",true);
//
//		Response response1 = itemService.execute(request);
//		System.out.println("**************************************");
		System.out.println("Model:" + JsonUtil.toJson(response.getModule()));
//		System.out.println("message:" + response.getMessage());
//		System.out.println("ResponseCode:" + response.getCode());
//		System.out.println("TotalCount:" + response.getTotalCount());
//		System.out.println("**************************************");
//		ItemDTO item  = (ItemDTO)(response.getModule());
//		System.out.println(item.getItemImageDTOList().get(0));
	}

}
