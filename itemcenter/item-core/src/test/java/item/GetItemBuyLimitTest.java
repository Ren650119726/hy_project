package item;

import com.mockuai.itemcenter.common.api.BaseRequest;
import com.mockuai.itemcenter.common.api.ItemService;
import com.mockuai.itemcenter.common.api.Request;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by luliang on 15/7/17.
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class GetItemBuyLimitTest {
    @Resource
    private ItemService itemService;

    @Test
    public void testGetItemBuyLimit() {
        Request request = new BaseRequest();
        request.setParam("itemId", 1);
        request.setParam("sellerId", 1);
        request.setCommand(ActionEnum.GET_ITEM_BUY_LIMIT.getActionName());
        System.out.print(itemService.execute(request));
    }
}
