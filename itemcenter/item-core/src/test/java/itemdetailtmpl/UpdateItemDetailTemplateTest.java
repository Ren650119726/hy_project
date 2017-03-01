package itemdetailtmpl;

import com.mockuai.itemcenter.common.api.BaseRequest;
import com.mockuai.itemcenter.common.api.ItemService;
import com.mockuai.itemcenter.common.api.Request;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.domain.dto.ItemDetailTemplateDTO;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by mckay on 15-7-22.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class UpdateItemDetailTemplateTest {

    @Resource
    private ItemService itemService;

    @Test
    public void test001() {
        Request request = new BaseRequest();
        ItemDetailTemplateDTO itemDetailTemplateDTO=new ItemDetailTemplateDTO();
        itemDetailTemplateDTO.setId(1);
        itemDetailTemplateDTO.setSellerId(1);
        itemDetailTemplateDTO.setHeaderHtml("head");

        request.setParam("itemDetailTemplateDTO", itemDetailTemplateDTO);
        request.setCommand(ActionEnum.UPDATE_ITEM_DETAIL_TEMPLATE.getActionName());
        Response response=itemService.execute(request);
        System.out.println("**************************************");
        System.out.println("Model:" + response.getModule());
        System.out.println("message:" + response.getMessage());
        System.out.println("ResponseCode:" + response.getCode());
        System.out.println("TotalCount:" + response.getTotalCount());
        System.out.println("**************************************");

    }

    @Test
    public void test002() {
        Request request = new BaseRequest();
        request.setCommand(ActionEnum.UPDATE_ITEM_DETAIL_TEMPLATE.getActionName());
        Response response=itemService.execute(request);
        System.out.println("**************************************");
        System.out.println("Model:" + response.getModule());
        System.out.println("message:" + response.getMessage());
        System.out.println("ResponseCode:" + response.getCode());
        System.out.println("TotalCount:" + response.getTotalCount());
        System.out.println("**************************************");

    }
}
