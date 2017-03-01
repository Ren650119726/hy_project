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
import com.mockuai.itemcenter.common.domain.dto.SpuPropertyDTO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class UpdateSpuPropertyTest {
	@Resource
	private ItemService itemService;

	@Test
	public void test() {
		Request request = new BaseRequest();
		SpuPropertyDTO spuPropertyDTO = new SpuPropertyDTO();
		spuPropertyDTO.setId(4L);
		spuPropertyDTO.setKeyName("颜色");
		spuPropertyDTO.setVal("黄色");
		spuPropertyDTO.setValType(1);
		request.setParam("spuPropertyDTO", spuPropertyDTO);
		request.setCommand(ActionEnum.UPDATE_SPU_PROPERTY.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}
}
