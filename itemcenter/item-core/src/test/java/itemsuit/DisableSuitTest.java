package itemsuit;

import com.mockuai.itemcenter.common.api.BaseRequest;
import com.mockuai.itemcenter.common.api.ItemService;
import com.mockuai.itemcenter.common.api.Request;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.domain.qto.ItemSuitQTO;
import com.mockuai.itemcenter.core.dao.ItemSuitDAO;
import com.mockuai.itemcenter.core.domain.ItemSuitDO;
import com.mockuai.itemcenter.core.manager.ItemSuitManager;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by yindingyu on 15/9/24.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class DisableSuitTest {

    @Resource
    private ItemService itemService;

    @Resource
    private ItemSuitManager itemSuitManager;

    @Resource
    private ItemSuitDAO itemSuitDAO;

    private final  static String BIZCODE= "mockuai_demo";

    private final static Long SELLER_ID = 38699L;

    private final static String APP_KEY = "af7433eda4883d7139e2934dd8d035f1";

    /**
     * 添加只有通用模板的运费模板
     */

    @Test
     public void test01(){

        Request request = new BaseRequest();
        request.setParam("appKey",APP_KEY);
        request.setParam("sellerId",SELLER_ID);
        request.setParam("itemId", 101273L);

        request.setCommand(ActionEnum.GET_SUIT_EXTRA_INFO.getActionName());
        Response<ItemSuitDO> response = itemService.execute(request);
        System.currentTimeMillis();

    }

    @Test
    public void test02(){

        Request request = new BaseRequest();

        request.setParam("appKey",APP_KEY);
        request.setParam("sellerId",38699L);
        request.setParam("itemId",101023);

        request.setCommand(ActionEnum.DISABLE_ITEM_SUIT.getActionName());
        Response<ItemSuitDO> response = itemService.execute(request);
        System.currentTimeMillis();

    }

}
