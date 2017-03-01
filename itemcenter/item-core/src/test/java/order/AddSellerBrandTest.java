package order;

import com.google.common.collect.Lists;
import com.mockuai.itemcenter.common.api.ItemService;
import com.mockuai.itemcenter.common.api.Request;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.domain.dto.SellerBrandDTO;
import com.mockuai.itemcenter.common.domain.qto.SellerBrandQTO;
import com.mockuai.tradecenter.client.OrderClient;
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
public class AddSellerBrandTest {
	@Resource
	private ItemService itemService;

    @Resource
    private OrderClient orderClient;

	@Test
	public void addTest() {

        com.mockuai.tradecenter.common.api.Response response = orderClient.getOrder(509601L,1841254L,AppKeyEnum.HAIYUN.getAppKey());

        System.currentTimeMillis();
	}


}
