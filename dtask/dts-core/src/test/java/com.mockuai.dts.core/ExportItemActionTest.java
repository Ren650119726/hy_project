import com.mockuai.dts.common.api.DtsService;
import com.mockuai.dts.common.api.action.DtsRequest;
import com.mockuai.dts.common.api.action.Request;
import com.mockuai.dts.common.api.action.Response;
import com.mockuai.dts.common.constant.ActionEnum;
import com.mockuai.dts.common.domain.ExportTaskQTO;
import com.mockuai.dts.common.domain.ItemExportQTO;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by luliang on 15/7/24.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class ExportItemActionTest {

    @Resource
    private DtsService dtsService;

    @Test
    public void testExport() {
        Request request = new DtsRequest();
        ItemExportQTO itemExportQTO = new ItemExportQTO();

        itemExportQTO.setKey("p");
        itemExportQTO.setSellerId(1L);
        itemExportQTO.setCategoryId(1L);
        itemExportQTO.setItemStatus(4);

        request.setParam("itemExportQTO", itemExportQTO);
        request.setCommand(ActionEnum.EXPORT_ITEM.getActionName());
        Response response = dtsService.execute(request);
        System.out.println(response.getCode());
        System.out.println(response.getModule());

        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            Runtime.getRuntime().halt(-1);
        }
    }
}
