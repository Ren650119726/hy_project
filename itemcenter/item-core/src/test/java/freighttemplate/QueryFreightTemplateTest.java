package freighttemplate;

import com.mockuai.itemcenter.common.api.BaseRequest;
import com.mockuai.itemcenter.common.api.ItemService;
import com.mockuai.itemcenter.common.api.Request;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.domain.dto.FreightTemplateDTO;
import com.mockuai.itemcenter.common.domain.qto.FreightTemplateQTO;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by yindingyu on 15/9/24.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class QueryFreightTemplateTest {

    @Resource
    private ItemService itemService;

    private final  static String TEST_BIZCODE= "mockuai_demo";

    /**
     * 添加只有通用模板的运费模板
     */

    @Test
    public void test01(){

        Request request = new BaseRequest();
        request.setParam("appKey", "af7433eda4883d7139e2934dd8d035f1");


        FreightTemplateQTO freightTemplateQTO = new FreightTemplateQTO();
        freightTemplateQTO.setSellerId(996L);


        request.setCommand(ActionEnum.QUERY_FREIGHT_TEMPLATE_ACTION.getActionName());
        //request.setCommand(ActionEnum.QUERY_FREIGHT_TEMPLATE_ACTION.getActionName());
        request.setParam("freightTemplateQTO", freightTemplateQTO);

        Response response = itemService.execute(request);

        int code = response.getCode();

        assertThat(code,is(10000));




    }
}
