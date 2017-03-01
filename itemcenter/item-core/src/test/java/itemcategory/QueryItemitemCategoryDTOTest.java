package itemcategory;

import javax.annotation.Resource;

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
import com.mockuai.itemcenter.common.domain.qto.ItemCategoryQTO;
import util.AppKeyEnum;
import util.RequestFactory;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class QueryItemitemCategoryDTOTest {
	@Resource
	private ItemService itemService;

	@Test
	/**
	 * 正常查询
	 */
	public void test001() {
		Request request = RequestFactory.newRequest(AppKeyEnum.HAIYUN,ActionEnum.QUERY_ITEM_LEAF_CATEGORY);
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}
	
	
	/**
	 * itemCategoryQTO为空
	 */
	public void test002() {
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.QUERY_ITEM_CATEGORY.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}

    /**
     * itemCategoryQTO为空
     */
    @Test
    public void test003() {
        Request request = RequestFactory.newRequest(AppKeyEnum.MOCKUAI_DEMO, ActionEnum.QUERY_ITEM_CATEGORY);

        ItemCategoryQTO itemCategoryQTO = new ItemCategoryQTO();
        itemCategoryQTO.setCateLevel(2);
        itemCategoryQTO.setOrderBy("gmt_used");

        request.setParam("itemCategoryQTO", itemCategoryQTO);
        Response response = itemService.execute(request);

        System.currentTimeMillis();
    }
}
