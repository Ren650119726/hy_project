package item;

import com.mockuai.common.uils.StarterRunner;
import com.mockuai.itemcenter.common.api.BaseRequest;
import com.mockuai.itemcenter.common.api.ItemService;
import com.mockuai.itemcenter.common.api.Request;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class CopyItemTest {
	@Resource
	private ItemService itemService;
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
	/**
	 */
	public void test001() {
		Request request = new BaseRequest();
        request.setParam("appKey", "6562b5ddf0aed2aad8fe471ce2a2c8a0");
        request.setParam("id", 3599L);
        request.setParam("sellerId",1841254L);
        request.setParam("bizCode","hanshu");
		request.setCommand(ActionEnum.COPY_ITEM.getActionName());
		Response response = itemService.execute(request);
		System.out.println("**************************************");
		System.out.println("Model:" + response.getModule());
		System.out.println("message:" + response.getMessage());
		System.out.println("ResponseCode:" + response.getCode());
		System.out.println("TotalCount:" + response.getTotalCount());
		System.out.println("**************************************");
	}

	

}
