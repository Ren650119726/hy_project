package salesfield;

import java.text.ParseException;

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
import com.mockuai.itemcenter.common.domain.dto.SalesFieldDTO;
import com.mockuai.itemcenter.core.util.DateUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class AddSalesFieldTest {
	@Resource
	private ItemService itemService;

	@Test
	public void test() throws ParseException {
		Request request = new BaseRequest();
		SalesFieldDTO salesFieldDTO = new SalesFieldDTO();
		salesFieldDTO.setFieldName("秋季专场3");
		salesFieldDTO.setFieldEnName("aaaaa");
		salesFieldDTO.setLogo("www.163.com/u.jpg");
		salesFieldDTO.setBeginTime(DateUtil.toSecondDate("2000-12-12 12:12:12"));
		salesFieldDTO.setEndTime(DateUtil.toSecondDate("2000-12-12 12:12:13"));
		request.setParam("salesFieldDTO", salesFieldDTO);
		request.setCommand(ActionEnum.ADD_SALES_FIELD.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}
}
