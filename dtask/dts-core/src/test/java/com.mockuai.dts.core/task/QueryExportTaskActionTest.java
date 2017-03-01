package task;

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
public class QueryExportTaskActionTest {

    @Resource
    private DtsService dtsService;

    @Test
    public void testQuery() {
        Request request = new DtsRequest();
        ExportTaskQTO exportTaskQTO = new ExportTaskQTO();
        exportTaskQTO.setSellerId(1L);
        request.setParam("exportTaskQTO", exportTaskQTO);
        request.setCommand(ActionEnum.QUERY_EXPORT_TASK.getActionName());
        Response response = dtsService.execute(request);
        System.out.println(response.getCode());
        System.out.println(response.getModule());
    }

}
