package item;

import com.mockuai.common.uils.StarterRunner;
import com.mockuai.itemcenter.common.api.BaseRequest;
import com.mockuai.itemcenter.common.api.ItemService;
import com.mockuai.itemcenter.common.api.Request;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.mop.MopBaseItemDTO;
import com.mockuai.itemcenter.core.manager.ItemManager;
import com.mockuai.itemcenter.core.util.JsonUtil;
import com.mockuai.itemcenter.core.util.MopApiUtil;
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
public class GetItemTest {
	@Resource
	private ItemService itemService;

    @Resource
    private ItemManager itemManager;

    private String appKey = "6562b5ddf0aed2aad8fe471ce2a2c8a0";
    private static final String APP_NAME = "itemcenter";
    private static final String LOCAL_PROPERTIES = "e:/haiyn/haiyn/haiyn_properties/itemcenter/haiyn.properties";

        static {
            try {
                StarterRunner.localSystemProperties(APP_NAME,LOCAL_PROPERTIES,new String[]{});
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    @Test
    public void testStatus(){


      Integer status =  itemManager.selectItemStatus(222L);
      System.out.print("status="+status);
    }


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
    @Test
    public void testDy() {
		Request request = new BaseRequest();
		request.setParam("id", 271L);
		request.setParam("supplierId", 1841254L);
		request.setParam("needDetail", false);
		request.setParam("appKey", AppKeyEnum.HAIYUN.getAppKey());
		request.setCommand(ActionEnum.GET_ITEM_DYNAMIC.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
		ItemDTO item  = (ItemDTO)(response.getModule());
        MopBaseItemDTO mopBaseItemDTO = MopApiUtil.genMopBaseItem(item);

        	System.out.println(mopBaseItemDTO);
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
    @Test
	public void test004() {
		Request request = new BaseRequest();
		request.setParam("id", 2230L);
		request.setParam("supplierId", 1841254L);
        request.setParam("appKey",appKey);
        request.setParam("needDetail",true);

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
		request.setParam("userId", 30045L);
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
		request.setParam("id", 28954L);
		request.setParam("supplierId", 1841254L);
		request.setParam("needDetail", true);
		request.setParam("appKey", AppKeyEnum.HAIYUN.getAppKey());
		request.setCommand(ActionEnum.GET_ITEM.getActionName());
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

	/**
	 * 正常GET
	 */
    @Test
	public void test010() {
		Request request = new BaseRequest();

        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setId(28954L);
        itemDTO.setSellerId(1841254L);
		request.setParam("itemDTO", itemDTO);
		request.setParam("userId", 1841254L);
		request.setParam("needDetail", true);
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


	@Test
	public void test0011() {
		Request request = new BaseRequest();
		request.setParam("itemId", 993L);
		request.setParam("supplierId", 1841254L);
		request.setParam("needDetail", true);
		request.setParam("appKey", AppKeyEnum.HAIYUN.getAppKey());
		request.setCommand(ActionEnum.WRITER_PUBLISH_ITEM.getActionName());
		Response<ItemDTO> response = itemService.execute(request);
		System.out.println("Model:" + JsonUtil.toJson(response.getModule()));
	}


	@Test
	/**
	 * 正常GET
	 */
	public void test0012() {
		Request request = new BaseRequest();
		request.setParam("id",1093L);
		request.setParam("supplierId", 1841254L);
		request.setParam("needDetail", true);
		request.setParam("appKey", AppKeyEnum.HAIYUN.getAppKey());
		request.setCommand(ActionEnum.GET_ITEM_STOCK.getActionName());
		Response<ItemDTO> response = itemService.execute(request);

		System.out.println("Model:" + JsonUtil.toJson(response.getModule()));
	}
}
