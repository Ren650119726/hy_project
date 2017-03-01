package itemsuit;

import com.google.common.collect.Lists;
import com.mockuai.itemcenter.common.api.BaseRequest;
import com.mockuai.itemcenter.common.api.ItemService;
import com.mockuai.itemcenter.common.api.Request;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.domain.dto.ItemPriceDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemPriceQTO;
import com.mockuai.itemcenter.common.domain.qto.ItemQTO;
import com.mockuai.itemcenter.core.util.JsonUtil;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import util.AppKeyEnum;
import util.RequestFactory;

import javax.annotation.Resource;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by yindingyu on 15/9/24.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class QueryItemPriceTest {

    @Resource
    private ItemService itemService;

    private final  static String BIZCODE= "mockuai_demo";

    private final static Long SELLER_ID = 38699L;

    private final static String APP_KEY = "af7433eda4883d7139e2934dd8d035f1";

    /**
     * 添加只有通用模板的运费模板
     */

    @Test
    public void test01(){

        Request request = new BaseRequest();
        request.setParam("appKey", APP_KEY);

        List<ItemPriceQTO> itemPriceQTOList = Lists.newArrayList();

        ItemPriceQTO itemPriceQTO = new ItemPriceQTO();
        itemPriceQTO.setItemSkuId(534L);
        itemPriceQTO.setItemId(101056L);
        itemPriceQTO.setSellerId(38699L);

        itemPriceQTOList.add(itemPriceQTO);

        request.setParam("itemPriceQTOList",itemPriceQTOList);

        request.setCommand(ActionEnum.QUERY_ITEMS_PRICE.getActionName());
        Response response = itemService.execute(request);

        int code = response.getCode();

        assertThat(code,is(10000));

    }

    @Test
    public void test02(){

        Request request = RequestFactory.newRequest(AppKeyEnum.HAIYUN,ActionEnum.QUERY_ITEMS_PRICE);

        List<ItemPriceQTO> itemPriceQTOList = Lists.newArrayList();

        ItemPriceQTO itemPriceQTO = new ItemPriceQTO();
        itemPriceQTO.setItemSkuId(18555L);
        itemPriceQTO.setSellerId(1841254L);

        itemPriceQTOList.add(itemPriceQTO);

        ItemPriceQTO itemPriceQTO1 = new ItemPriceQTO();
        itemPriceQTO1.setItemSkuId(4941L);
        itemPriceQTO1.setSellerId(1841254L);

        itemPriceQTOList.add(itemPriceQTO1);

        ItemPriceQTO itemPriceQTO2 = new ItemPriceQTO();
        itemPriceQTO2.setItemSkuId(4942L);
        itemPriceQTO2.setSellerId(1841254L);

        itemPriceQTOList.add(itemPriceQTO2);

        request.setParam("itemPriceQTOList", itemPriceQTOList);

        Response response = itemService.execute(request);

        int code = response.getCode();

        assertThat(code,is(10000));

        System.out.println("************************************");
        System.out.println(JsonUtil.toJson(response.getModule()));
        System.out.println("************************************");

    }
}
