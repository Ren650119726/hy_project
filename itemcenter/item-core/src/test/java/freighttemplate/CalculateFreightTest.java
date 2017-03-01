package freighttemplate;

import com.google.common.collect.Maps;
import com.mockuai.itemcenter.common.api.BaseRequest;
import com.mockuai.itemcenter.common.api.ItemService;
import com.mockuai.itemcenter.common.api.Request;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.domain.dto.FreightTemplateDTO;
import com.mockuai.itemcenter.common.domain.dto.area.AddressDTO;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import util.AppKeyEnum;
import util.RequestFactory;

import javax.annotation.Resource;

import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by yindingyu on 15/10/21.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class CalculateFreightTest {

    @Resource
    private ItemService itemService;

    private final static String TEST_BIZCODE = "mockuai_demo";

    /**
     * 添加只有通用模板的运费模板
     */

    @Test
    public void test01() {

        Request request = RequestFactory.newRequest(AppKeyEnum.HAODADA, ActionEnum.CALCULATE_FREIGHT_ACTION);

        Map<Long,Integer> maps = Maps.newHashMap();
        maps.put(102626L, 2);
        request.setParam("itemNums", maps);

        Long consigneeId = 232324L;


        request.setCommand(ActionEnum.CALCULATE_FREIGHT_ACTION.getActionName());

        Response response = itemService.execute(request);

        int code = response.getCode();
        assertThat(code, is(10000));


        long result = ((Long)response.getModule()).longValue();


        assertThat(result, is(3000L));

    }

    @Test
    public void test02() {

        Request request = new BaseRequest();
        request.setParam("appKey", "af7433eda4883d7139e2934dd8d035f1");

        Map<Long,Integer> maps = Maps.newHashMap();
        maps.put(101096L, 10);
        request.setParam("itemNums", maps);

        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setProvinceCode("仙女座");
        addressDTO.setCityCode("α星");
        request.setParam("addressDTO",addressDTO);


        request.setCommand(ActionEnum.CALCULATE_FREIGHT_ACTION.getActionName());
        //request.setCommand(ActionEnum.QUERY_FREIGHT_TEMPLATE_ACTION.getActionName());

        Response response = itemService.execute(request);

        int code = response.getCode();
        assertThat(code, is(30000));


        long result = ((Long)response.getModule()).longValue();


        assertThat(result, is(100L));

    }

    @Test
    public void test03() {

        Request request = new BaseRequest();
        request.setParam("appKey", "af7433eda4883d7139e2934dd8d035f1");

        Map<Long,Integer> maps = Maps.newHashMap();
        maps.put(101052L, 4);
        request.setParam("itemNums", maps);

        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setProvinceCode("zhejiang");
        addressDTO.setCityCode("α星");
        request.setParam("addressDTO",addressDTO);


        request.setCommand(ActionEnum.CALCULATE_FREIGHT_ACTION.getActionName());
        //request.setCommand(ActionEnum.QUERY_FREIGHT_TEMPLATE_ACTION.getActionName());

        Response response = itemService.execute(request);

        int code = response.getCode();
        assertThat(code, is(10000));


        long result = ((Long)response.getModule()).longValue();


        assertThat(result, is(5500L));

    }

    @Test
    public void test04() {

        Request request = new BaseRequest();
        request.setParam("appKey", "af7433eda4883d7139e2934dd8d035f1");

        Map<Long,Integer> maps = Maps.newHashMap();
        maps.put(101052L, 4);
        request.setParam("itemNums", maps);

        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setProvinceCode("zhejiangxx");
        addressDTO.setCityCode("hangzhou");
        request.setParam("addressDTO",addressDTO);


        request.setCommand(ActionEnum.CALCULATE_FREIGHT_ACTION.getActionName());
        //request.setCommand(ActionEnum.QUERY_FREIGHT_TEMPLATE_ACTION.getActionName());

        Response response = itemService.execute(request);

        int code = response.getCode();
        assertThat(code, is(10000));


        long result = ((Long)response.getModule()).longValue();


        assertThat(result, is(15000L));

    }

    @Test
    public void test05() {

        Request request = new BaseRequest();
        request.setParam("appKey", "af7433eda4883d7139e2934dd8d035f1");

        Map<Long,Integer> maps = Maps.newHashMap();
        maps.put(101052L, 4);
        maps.put(101050L,7);
        request.setParam("itemNums", maps);

        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setProvinceCode("zhejiangxx");
        addressDTO.setCityCode("hangzhou");
        request.setParam("addressDTO",addressDTO);


        request.setCommand(ActionEnum.CALCULATE_FREIGHT_ACTION.getActionName());
        //request.setCommand(ActionEnum.QUERY_FREIGHT_TEMPLATE_ACTION.getActionName());

        Response response = itemService.execute(request);

        int code = response.getCode();
        assertThat(code, is(10000));


        long result = ((Long)response.getModule()).longValue();


        assertThat(result, is(50000L));

    }

    @Test
    public void test06() {

        Request request = new BaseRequest();
        request.setParam("appKey", "af7433eda4883d7139e2934dd8d035f1");

        Map<Long,Integer> maps = Maps.newHashMap();
        maps.put(101052L,4);
        maps.put(101050L,7);
        maps.put(101056L,10);
        request.setParam("itemNums", maps);

        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setProvinceCode("zhejiangxx");
        addressDTO.setCityCode("hangzhou");
        request.setParam("addressDTO",addressDTO);


        request.setCommand(ActionEnum.CALCULATE_FREIGHT_ACTION.getActionName());
        //request.setCommand(ActionEnum.QUERY_FREIGHT_TEMPLATE_ACTION.getActionName());

        Response response = itemService.execute(request);

        int code = response.getCode();
        assertThat(code, is(10000));


        long result = ((Long)response.getModule()).longValue();


        assertThat(result, is(51160L));

    }
}
