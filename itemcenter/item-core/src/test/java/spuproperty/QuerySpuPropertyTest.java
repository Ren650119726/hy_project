package spuproperty;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mockuai.itemcenter.common.api.BaseRequest;
import com.mockuai.itemcenter.common.api.ItemService;
import com.mockuai.itemcenter.common.api.Request;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.domain.qto.SpuPropertyQTO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class QuerySpuPropertyTest {
	@Resource
	private ItemService itemService;

	@Test
	public void test() {
		Request request = new BaseRequest();
		SpuPropertyQTO spuPropertyQTO = new SpuPropertyQTO();
		spuPropertyQTO.setCurrentPage(1);
		spuPropertyQTO.setPageSize(3);
		request.setParam("spuPropertyQTO", spuPropertyQTO);
		request.setCommand(ActionEnum.QUERY_SPU_PROPERTY.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}
}
