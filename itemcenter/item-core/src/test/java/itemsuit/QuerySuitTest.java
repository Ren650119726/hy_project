package itemsuit;

import com.google.common.collect.Lists;
import com.mockuai.itemcenter.common.api.BaseRequest;
import com.mockuai.itemcenter.common.api.ItemService;
import com.mockuai.itemcenter.common.api.Request;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.domain.qto.ItemQTO;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
public class QuerySuitTest {

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
        ItemQTO itemQTO = new ItemQTO();
        itemQTO.setSellerId(SELLER_ID);
        List<Long> IdList = Lists.newArrayList(101273L);
        itemQTO.setBizCode(BIZCODE);
        request.setParam("itemQTO",itemQTO);

        request.setCommand(ActionEnum.QUERY_SUIT.getActionName());
        Response response = itemService.execute(request);

        int code = response.getCode();

        assertThat(code,is(10000));




    }
}
