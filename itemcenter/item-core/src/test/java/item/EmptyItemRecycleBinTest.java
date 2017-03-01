package item;

import com.mockuai.itemcenter.common.api.ItemService;
import com.mockuai.itemcenter.common.api.Request;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.constant.ActionEnum;
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
public class EmptyItemRecycleBinTest {

    @Resource
    private ItemService itemService;


    @Test
    /**
     * 正常GET
     */
    public void test001() {

        Request request = RequestFactory.newRequest(AppKeyEnum.MOCKUAI_DEMO, ActionEnum.EMPTY_ITEM_RECYCLE_BIN);

        request.setParam("sellerId", 38699L);

        Response<Long> response = itemService.execute(request);

        assertThat(response.getCode(), is(10000));

    }
}
