package itemlabel;

import com.google.common.collect.Lists;
import com.mockuai.itemcenter.common.api.BaseRequest;
import com.mockuai.itemcenter.common.api.ItemService;
import com.mockuai.itemcenter.common.api.Request;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.domain.dto.ItemLabelDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemLabelQTO;
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
public class QueryItemLabelTest {


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

        request.setCommand(ActionEnum.QUERY_ITEM_LABEL.getActionName());


        ItemLabelQTO itemLabelQTO = new ItemLabelQTO();
        itemLabelQTO.setNeedPaging(true);
        itemLabelQTO.setPageSize(1);
        itemLabelQTO.setCurrentPage(1);

        request.setParam("itemLabelQTO", itemLabelQTO);

        Response response = itemService.execute(request);

        int code = response.getCode();

        assertThat(code,is(10000));

    }


    @Test
    public void test02(){

        Request request = new BaseRequest();
        request.setParam("appKey", APP_KEY);

        request.setCommand(ActionEnum.ADD_ITEM_LABEL.getActionName());

        ItemLabelDTO itemLabelDTO = new ItemLabelDTO();

        itemLabelDTO.setSellerId(SELLER_ID);
        itemLabelDTO.setIconUrl("xxx");
        itemLabelDTO.setLabelName("爱咋咋地");
        itemLabelDTO.setLabelDesc("不用解释1");
        itemLabelDTO.setScope(2);

        request.setParam("itemLabelDTO", itemLabelDTO);

        itemLabelDTO.setItemIdList(Lists.newArrayList(101598L,101596L));

        Response response = itemService.execute(request);

        int code = response.getCode();

        assertThat(code,is(10000));

    }

}
