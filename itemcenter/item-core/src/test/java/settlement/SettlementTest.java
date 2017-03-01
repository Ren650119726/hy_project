package settlement;

import com.google.common.collect.Lists;
import com.mockuai.itemcenter.common.api.BaseRequest;
import com.mockuai.itemcenter.common.api.ItemService;
import com.mockuai.itemcenter.common.api.Request;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.domain.dto.ItemSettlementConditionDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemSettlementConfigDTO;
import com.mockuai.itemcenter.common.domain.dto.ValueAddedServiceDTO;
import com.mockuai.itemcenter.common.domain.dto.ValueAddedServiceTypeDTO;
import com.mockuai.itemcenter.core.dao.ItemSettlementConfigDAO;
import com.mockuai.itemcenter.core.domain.ItemSettlementConditionDO;
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
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class SettlementTest {


    @Resource
    private ItemService itemService;

    private final static String BIZ_CODE = "mockuai_demo";

    private final static String APP_KEY = "af7433eda4883d7139e2934dd8d035f1";

    private final static Long SELLER_ID = 38699L;

    /**
     * 添加增值服务
     */

    @Test
    public void test01() {

        Request request = new BaseRequest();
        request.setParam("appKey", APP_KEY);

        request.setCommand(ActionEnum.ADD_ITEM_SETTLEMENT_CONFIG.getActionName());

        ItemSettlementConfigDTO x = new ItemSettlementConfigDTO();

        x.setConfigName("第一个配置");
        x.setEnable(0);
        x.setScope(1);

        List<ItemSettlementConditionDTO>  ll = Lists.newArrayList();

        ItemSettlementConditionDTO s = new ItemSettlementConditionDTO();

        s.setCommissionRatio(23);
        s.setMinPrice(0L);
        s.setMaxPrice(Long.MAX_VALUE);

        ll.add(s);

        x.setConditionList(ll);

        request.setParam("itemSettlementConfigDTO", x);


        Response response = itemService.execute(request);

        int code = response.getCode();

        assertThat(code, is(10000));

        Boolean result = (Boolean) response.getModule();

    }

    @Test
    public void test02() {

        Request request = new BaseRequest();
        request.setParam("appKey", APP_KEY);

        request.setCommand(ActionEnum.ADD_ITEM_SETTLEMENT_CONFIG.getActionName());

        ItemSettlementConfigDTO x = new ItemSettlementConfigDTO();

        x.setConfigName("第二个配置");
        x.setEnable(0);
        x.setScope(4);

        List<ItemSettlementConditionDTO>  ll = Lists.newArrayList();

        ItemSettlementConditionDTO s = new ItemSettlementConditionDTO();

        s.setCommissionRatio(23);
        s.setMinPrice(25L);
        s.setMaxPrice(Long.MAX_VALUE);

        ll.add(s);

        x.setConditionList(ll);

        List<Long> idList = Lists.newArrayList(38699L,38805L,38807L);

        x.setSellerIdList(idList);

        request.setParam("itemSettlementConfigDTO", x);


        Response response = itemService.execute(request);

        int code = response.getCode();

        assertThat(code, is(10000));

        Boolean result = (Boolean) response.getModule();

    }

    @Test
    public void test03() {

        Request request = new BaseRequest();
        request.setParam("appKey", APP_KEY);

        request.setCommand(ActionEnum.GET_ITEM_SETTLEMENT_CONFIG.getActionName());



        request.setParam("id", 14L);



        Response response = itemService.execute(request);

        int code = response.getCode();

        assertThat(code, is(10000));

        Boolean result = (Boolean) response.getModule();

    }

    @Test
    public void test04() {

        Request request = new BaseRequest();
        request.setParam("appKey", APP_KEY);

        request.setCommand(ActionEnum.DISABLE_ITEM_SETTLEMENT_CONFIG.getActionName());



        request.setParam("id", 14L);



        Response response = itemService.execute(request);

        int code = response.getCode();

        assertThat(code, is(10000));

        Boolean result = (Boolean) response.getModule();

    }


    @Test
    public void test05() {

        Request request = new BaseRequest();
        request.setParam("appKey", APP_KEY);

        request.setCommand(ActionEnum.ENABLE_ITEM_SETTLEMENT_CONFIG.getActionName());



        request.setParam("id", 14L);



        Response response = itemService.execute(request);

        int code = response.getCode();

        assertThat(code, is(10000));

        Boolean result = (Boolean) response.getModule();

    }

    @Test
    public void test06() {

        Request request = new BaseRequest();
        request.setParam("appKey", APP_KEY);

        request.setCommand(ActionEnum.DELETE_ITEM_SETTLEMENT_CONFIG.getActionName());



        request.setParam("id", 14L);



        Response response = itemService.execute(request);

        int code = response.getCode();

        assertThat(code, is(10000));

        Boolean result = (Boolean) response.getModule();

    }
}
