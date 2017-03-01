package spupropertytmpl;

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
import com.mockuai.itemcenter.common.domain.qto.SpuPropertyTmplQTO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class QuerySpuPropertyTmplTest {
	@Resource
	private ItemService itemService;

	@Test
	public void test() {
		Request request = new BaseRequest();
		SpuPropertyTmplQTO spuPropertyTmplQTO = new SpuPropertyTmplQTO();
		spuPropertyTmplQTO.setCurrentPage(1);
		spuPropertyTmplQTO.setPageSize(3);
		request.setParam("spuPropertyTmplQTO", spuPropertyTmplQTO);
		request.setCommand(ActionEnum.QUERY_SPU_PROPERTY_TMPL.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}
}
