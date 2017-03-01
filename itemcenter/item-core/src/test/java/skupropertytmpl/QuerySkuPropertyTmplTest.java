package skupropertytmpl;

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
import com.mockuai.itemcenter.common.domain.qto.SkuPropertyTmplQTO;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class QuerySkuPropertyTmplTest {
	@Resource
	private ItemService itemService;

	@Test
	/**
	 * 正确查询
	 */
	public void test001() {
		Request request = new BaseRequest();
		SkuPropertyTmplQTO skuPropertyTmplQTO = new SkuPropertyTmplQTO();
		skuPropertyTmplQTO.setCurrentPage(1);
		skuPropertyTmplQTO.setPageSize(2);
		skuPropertyTmplQTO.setCategoryId(5L);
		skuPropertyTmplQTO.setNeedPaging(false);
		request.setParam("skuPropertyTmplQTO", skuPropertyTmplQTO);
		request.setParam("needPropertyValue",true);
		request.setCommand(ActionEnum.QUERY_SKU_PROPERTY_TMPL.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}
	
	
	/**
	 * skuPropertyTmplQTO 为空
	 */
	public void test002() {
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.QUERY_SKU_PROPERTY_TMPL.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}
}
