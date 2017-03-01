package itemdetailtmpl;

import com.mockuai.itemcenter.common.api.BaseRequest;
import com.mockuai.itemcenter.common.api.ItemService;
import com.mockuai.itemcenter.common.api.Request;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.domain.dto.ItemDetailTemplateDTO;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.mockuai.itemcenter.common.api.Response;
import javax.annotation.Resource;


/**
 * Created by mckay on 15-7-22.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class AddItemDetailTemplateTest {
    @Resource
    private ItemService itemService;

    @Test
    public void test001() {
        Request request = new BaseRequest();
        ItemDetailTemplateDTO itemDetailTemplateDTO=new ItemDetailTemplateDTO();
        itemDetailTemplateDTO.setBizCode("yangdongxi");
//        itemDetailTemplateDTO.setDeleteMark(0);
        itemDetailTemplateDTO.setSellerId(1);
        itemDetailTemplateDTO.setTemplateName("test");
        itemDetailTemplateDTO.setHeaderHtml("header");
        itemDetailTemplateDTO.setHeaderTmsId("123444");
        itemDetailTemplateDTO.setFooterHtml("footer");
        itemDetailTemplateDTO.setFooterTmsId("234445");

        request.setParam("itemDetailTemplateDTO", itemDetailTemplateDTO);
        request.setCommand(ActionEnum.ADD_ITEM_DETAIL_TEMPLATE.getActionName());
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
        request.setCommand(ActionEnum.ADD_ITEM_DETAIL_TEMPLATE.getActionName());
        Response response=itemService.execute(request);
        System.out.println("**************************************");
        System.out.println("Model:" + response.getModule());
        System.out.println("message:" + response.getMessage());
        System.out.println("ResponseCode:" + response.getCode());
        System.out.println("TotalCount:" + response.getTotalCount());
        System.out.println("**************************************");

    }
}
