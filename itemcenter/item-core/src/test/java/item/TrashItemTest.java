package item;

import com.mockuai.itemcenter.common.api.BaseRequest;
import com.mockuai.itemcenter.common.api.ItemService;
import com.mockuai.itemcenter.common.api.Request;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.core.manager.MarketingManager;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import util.AppKeyEnum;
import util.RequestFactory;

import javax.annotation.Resource;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class TrashItemTest {

    @Resource
    private ItemService itemService;


    @Test
    /**
     * 正常GET
     */
    public void test001() {

        Request request = RequestFactory.newRequest(AppKeyEnum.MOCKUAI_DEMO, ActionEnum.TRASH_ITEM);

        request.setParam("id", 102904L);
        request.setParam("sellerId", 38699L);

        Response<Long> response = itemService.execute(request);

        assertThat(response.getCode(), is(10000));

    }
}
