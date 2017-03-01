package item;

import com.google.common.collect.Lists;
import com.mockuai.itemcenter.common.api.BaseRequest;
import com.mockuai.itemcenter.common.api.ItemService;
import com.mockuai.itemcenter.common.api.Request;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemQTO;
import com.mockuai.itemcenter.common.domain.qto.ItemSearchQTO;
import com.mockuai.marketingcenter.common.constant.ItemType;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import util.AppKeyEnum;
import util.RequestFactory;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class QueryItemTest {
	@Resource
	private ItemService itemService;

    private static final Long SELLER_ID = 38699L;
    private static final String APP_KEY = "1b0044c3653b89673bc5beff190b68a1";
	
	@Test
	/**
	 * 正常查询
	 */
	public void test001() {
        Request request = RequestFactory.newRequest(AppKeyEnum.HAIYUN, ActionEnum.SEARCH_ITEM);
        ItemSearchQTO itemQTO = new ItemSearchQTO();
        request.setParam("itemSearchQTO", itemQTO);

        Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}

	
	/**
	 * itemQTO为空
	 */
	public void test002() {
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.QUERY_ITEM.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}
	
	/**
	 * 供应商ID为空
	 */
	//@Ignore
    @Test
	public void test003() {
		Request request = new BaseRequest();
		ItemQTO itemQTO = new ItemQTO();
		itemQTO.setCurrentPage(1);
		itemQTO.setPageSize(3);
		itemQTO.setDeleteMark(0);
		List<Long> idList = new ArrayList<Long>();
		idList.add(600L);
		idList.add(2509L);
		idList.add(2610L);
		idList.add(2634L);
		idList.add(2182L);
		idList.add(2654L);
		idList.add(2182L);
		idList.add(1749L);
		itemQTO.setIdList(idList);
        itemQTO.setNeedComposite(true);
		itemQTO.setSellerId(1841254L);
		request.setParam("itemQTO", itemQTO);
        request.setParam("appKey",APP_KEY);
		request.setCommand(ActionEnum.QUERY_ITEM.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}

	/**
	 * 供应商ID为空
	 */
	@Test
	public void test004() {
		Request request = new BaseRequest();
		ItemQTO itemQTO = new ItemQTO();
		itemQTO.setCurrentPage(1);
		itemQTO.setPageSize(3);
		itemQTO.setDeleteMark(0);
		itemQTO.setNeedPaging(true);
//		List<Long> idList = new ArrayList<Long>();
//		idList.add(91L);
//		itemQTO.setIdList(idList);
//		itemQTO.setGroupId(1L);
//		itemQTO.setId(1L);
		itemQTO.setSellerId(91L);
		request.setParam("itemQTO", itemQTO);
		request.setCommand(ActionEnum.QUERY_ITEM_GROUP_ACTION.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}

	/**
	 * 供应商ID为空
	 */
	@Test
	public void test005() {
		Request request = new BaseRequest();
		ItemQTO itemQTO = new ItemQTO();
//		List<Long> idList = new ArrayList<Long>();
//		idList.add(91L);
//		itemQTO.setIdList(idList);
//		itemQTO.setGroupId(1L);
//		itemQTO.setId(1L);
		request.setParam("itemQTO", itemQTO);
		request.setCommand(ActionEnum.COUNT_TOTAL_ITEM_ACTION.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}

	/**
	 * itemQTO为空
	 */
	@Test
	public void test007() {
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.QUERY_ITEM.getActionName());

		ItemQTO itemQTO = new ItemQTO();
        itemQTO.setId(3824L);
		//itemQTO.setIdList(Lists.newArrayList(101505L));
		itemQTO.setSellerId(1841254L);

		request.setParam("itemQTO",itemQTO);
		request.setParam("appKey", APP_KEY);
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}

	@Test
	public void test008() {
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.QUERY_ITEM.getActionName());

		ItemQTO itemQTO = new ItemQTO();
		itemQTO.setItemType(ItemType.GROUP_BUYING.getValue());
		itemQTO.setIdList(Lists.newArrayList(101506L, 101509L));
        itemQTO.setSellerId(0L);

		request.setParam("itemQTO", itemQTO);
		request.setParam("appKey", APP_KEY);
        request.setParam("needExtraInfo","1");
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}

    @Test
    public void test009() {
        Request request = new BaseRequest();
        request.setCommand(ActionEnum.QUERY_ITEM.getActionName());

        ItemQTO itemQTO = new ItemQTO();
        itemQTO.setItemType(ItemType.SECKILL.getValue());
        itemQTO.setIdList(Lists.newArrayList(101986L, 101985L));
        itemQTO.setSellerId(0L);

        request.setParam("itemQTO", itemQTO);
        request.setParam("appKey", APP_KEY);
        request.setParam("needExtraInfo","1");
        Response response = itemService.execute(request);
        System.out.println("**************************************");
        System.out.println("Model:" + response.getModule());
        System.out.println("message:" + response.getMessage());
        System.out.println("ResponseCode:" + response.getCode());
        System.out.println("TotalCount:" + response.getTotalCount());
        System.out.println("**************************************");
    }

    @Test
    public void test0010() {

        Request request = RequestFactory.newRequest(AppKeyEnum.MOCKUAI_DEMO, ActionEnum.QUERY_ITEM);

        ItemQTO itemQTO = new ItemQTO();
        itemQTO.setNeedPaging(true);
        itemQTO.setPageSize(10);
        itemQTO.setCurrentPage(1);
        itemQTO.setSellerId(38699L);
        itemQTO.setDeleteMark(2);

        request.setParam("itemQTO", itemQTO);

        Response response = itemService.execute(request);

        System.out.println("**************************************");
        System.out.println("Model:" + response.getModule());
        System.out.println("message:" + response.getMessage());
        System.out.println("ResponseCode:" + response.getCode());
        System.out.println("TotalCount:" + response.getTotalCount());
        System.out.println("**************************************");
    }
    @Test
    public void test0011() {

        Request request = RequestFactory.newRequest(AppKeyEnum.HAIYUN, ActionEnum.QUERY_ITEM_DISCOUNT_INFO);

        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setId(3507L);
        itemDTO.setSellerId(1841254L);
        request.setParam("itemDTO",itemDTO);
        request.setParam("userId",1L);
        Response response = itemService.execute(request);

        System.out.println("**************************************");
        System.out.println("Model:" + response.getModule());
        System.out.println("message:" + response.getMessage());
        System.out.println("ResponseCode:" + response.getCode());
        System.out.println("TotalCount:" + response.getTotalCount());
        System.out.println("**************************************");
    }

}
