package valueaddedservice;

import com.google.common.collect.Lists;
import com.mockuai.itemcenter.common.api.BaseRequest;
import com.mockuai.itemcenter.common.api.ItemService;
import com.mockuai.itemcenter.common.api.Request;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.domain.dto.ValueAddedServiceDTO;
import com.mockuai.itemcenter.common.domain.dto.ValueAddedServiceTypeDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemPriceQTO;
import com.mockuai.itemcenter.common.domain.qto.ValueAddedServiceQTO;
import com.mockuai.itemcenter.common.domain.qto.ValueAddedServiceTypeQTO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.ValueAddedServiceManager;
import com.mockuai.itemcenter.core.service.ItemRequest;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yindingyu on 15/9/24.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class PriceTest {


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

        ValueAddedServiceQTO valueAddedServiceQTO = new ValueAddedServiceQTO();
        valueAddedServiceQTO.setIdList(Lists.newArrayList(9L,29L));
        valueAddedServiceQTO.setBizCode(BIZ_CODE);

        try {

            Request request  = new BaseRequest();

            request.setParam("appKey", APP_KEY);

            ItemPriceQTO itemPriceQTO = new ItemPriceQTO();

            itemPriceQTO.setSellerId(SELLER_ID);
            itemPriceQTO.setItemId(101504L);
            itemPriceQTO.setItemSkuId(1254L);
            itemPriceQTO.setServiceIdList(Lists.newArrayList(36L, 37L));
            request.setParam("itemPriceQTOList", Lists.newArrayList(itemPriceQTO));

            request.setCommand(ActionEnum.QUERY_ITEMS_PRICE.getActionName());

            Response response = itemService.execute(request);

            System.currentTimeMillis();
        } catch (Exception e) {
            e.printStackTrace();
        }



    }





}
