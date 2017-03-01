import javax.annotation.Resource;

import com.mockuai.itemcenter.common.api.BaseRequest;
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

import com.mockuai.itemcenter.common.api.ItemService;

import java.io.DataInput;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by wanghailong on 15-7-24.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class ItemUpDownTest {
    @Resource
    private ItemService itemService;

    @Test
    /**
     * 测试正常商品上下架
     */
    public void testItemUpDownService(){
        Request request = new BaseRequest();
        ItemQTO itemQTO = new ItemQTO();
        itemQTO.setSellerId(91L);
//        itemQTO.setId(1000L);
        request.setParam("itemQTO", itemQTO);
        request.setCommand(ActionEnum.UP_DOWN_STATE.getActionName());
        Response response = itemService.execute(request);
        System.out.println("**************************************");
        System.out.println("Model:" + response.getModule());
        System.out.println("message:" + response.getMessage());
        System.out.println("ResponseCode:" + response.getCode());
        System.out.println("TotalCount:" + response.getTotalCount());
        System.out.println("**************************************");

    }
}
