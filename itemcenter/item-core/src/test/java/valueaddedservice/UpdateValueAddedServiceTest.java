package valueaddedservice;

import com.google.common.collect.Lists;
import com.mockuai.itemcenter.common.api.BaseRequest;
import com.mockuai.itemcenter.common.api.ItemService;
import com.mockuai.itemcenter.common.api.Request;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.domain.dto.ValueAddedServiceDTO;
import com.mockuai.itemcenter.common.domain.dto.ValueAddedServiceTypeDTO;
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
public class UpdateValueAddedServiceTest {


    @Resource
    private ItemService itemService;

    private final  static String BIZ_CODE= "mockuai_demo";

    private final static String APP_KEY = "af7433eda4883d7139e2934dd8d035f1";

    private final static Long SELLER_ID = 38699L;

    /**
     * 添加增值服务
     */

    @Test
    public void test01(){

        Request request = new BaseRequest();
        request.setParam("appKey", APP_KEY);

        request.setCommand(ActionEnum.ADD_VALUE_ADDED_SERVICE.getActionName());

        ValueAddedServiceTypeDTO valueAddedServiceTypeDTO = new ValueAddedServiceTypeDTO();


        valueAddedServiceTypeDTO.setSellerId(SELLER_ID);
        valueAddedServiceTypeDTO.setScope(2);
        valueAddedServiceTypeDTO.setTypeName("延长保修");

        ValueAddedServiceDTO service1 = new ValueAddedServiceDTO();

        service1.setSellerId(SELLER_ID);
        service1.setIconUrl("xxx");
        service1.setServiceName("一年");
        service1.setServiceDesc("延长保修一年");
        service1.setServicePrice(30000L);

        ValueAddedServiceDTO service2 = new ValueAddedServiceDTO();

        service2.setSellerId(SELLER_ID);
        service2.setIconUrl("xxx");
        service2.setServiceName("二年");
        service2.setServiceDesc("延长保修二年");
        service2.setServicePrice(50000L);

        valueAddedServiceTypeDTO.setValueAddedServiceDTOList(Lists.newArrayList(service1, service2));

        request.setParam("valueAddedServiceTypeDTO", valueAddedServiceTypeDTO);

        valueAddedServiceTypeDTO.setItemIdList(Lists.newArrayList(101504L));

        Response response = itemService.execute(request);

        int code = response.getCode();

        assertThat(code,is(10000));

        Boolean result = (Boolean)response.getModule();


    }

    @Test
    public void test02(){

        Request request = new BaseRequest();
        request.setParam("appKey", APP_KEY);

        request.setCommand(ActionEnum.ADD_VALUE_ADDED_SERVICE.getActionName());

        ValueAddedServiceTypeDTO valueAddedServiceTypeDTO = new ValueAddedServiceTypeDTO();


        valueAddedServiceTypeDTO.setSellerId(SELLER_ID);
        valueAddedServiceTypeDTO.setScope(2);
        valueAddedServiceTypeDTO.setTypeName("其他服务");

        ValueAddedServiceDTO service1 = new ValueAddedServiceDTO();

        service1.setSellerId(SELLER_ID);
        service1.setIconUrl("xxxfff");
        service1.setServiceName("上门维修");
        service1.setServiceDesc("开门取快递");
        service1.setServicePrice(40000L);

        ValueAddedServiceDTO service2 = new ValueAddedServiceDTO();

        service2.setSellerId(SELLER_ID);
        service2.setIconUrl("xxxvvv");
        service2.setServiceName("摔坏也管修");
        service2.setServiceDesc("摔坏也管修啊");
        service2.setServicePrice(60000L);

        valueAddedServiceTypeDTO.setValueAddedServiceDTOList(Lists.newArrayList(service1, service2));

        request.setParam("valueAddedServiceTypeDTO", valueAddedServiceTypeDTO);

        valueAddedServiceTypeDTO.setItemIdList(Lists.newArrayList(101504L));

        Response response = itemService.execute(request);

        int code = response.getCode();

        Boolean result = (Boolean)response.getModule();

    }

    @Test
    public void test03(){

        Request request = new BaseRequest();
        request.setParam("appKey", APP_KEY);

        request.setCommand(ActionEnum.ADD_VALUE_ADDED_SERVICE.getActionName());

        ValueAddedServiceTypeDTO valueAddedServiceTypeDTO = new ValueAddedServiceTypeDTO();


        valueAddedServiceTypeDTO.setSellerId(SELLER_ID);
        valueAddedServiceTypeDTO.setScope(2);
        valueAddedServiceTypeDTO.setTypeName("其他服务");

        ValueAddedServiceDTO service1 = new ValueAddedServiceDTO();

        service1.setSellerId(SELLER_ID);
        service1.setIconUrl("xxxfff");
        service1.setServiceName("上门维修");
        service1.setServiceDesc("开门取快递");
        service1.setServicePrice(40000L);

        ValueAddedServiceDTO service2 = new ValueAddedServiceDTO();

        service2.setSellerId(SELLER_ID);
        service2.setIconUrl("xxxvvv");
        service2.setServiceName("摔坏也管修");
        service2.setServiceDesc("摔坏也管修啊");
        service2.setServicePrice(60000L);


        List<Long> idList = Lists.newArrayList(101538L,101504L);

        valueAddedServiceTypeDTO.setItemIdList(idList);

        valueAddedServiceTypeDTO.setValueAddedServiceDTOList(Lists.newArrayList(service1, service2));

        request.setParam("valueAddedServiceTypeDTO",valueAddedServiceTypeDTO);

        Response response = itemService.execute(request);

        int code = response.getCode();

        Boolean result = (Boolean)response.getModule();

    }

}
