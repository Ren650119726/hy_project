package task;

import com.mockuai.dts.common.api.DtsService;
import com.mockuai.dts.common.api.action.DtsRequest;
import com.mockuai.dts.common.api.action.Request;
import com.mockuai.dts.common.api.action.Response;
import com.mockuai.dts.common.constant.ActionEnum;
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
public class DeleteExportTaskActionTest {

    @Resource
    private DtsService dtsService;

    @Test
    public void testDelete() {
        Request request = new DtsRequest();
        request.setParam("id", 1L);
        request.setParam("sellerId", 1L);
        request.setCommand(ActionEnum.DELETE_EXPORT_TASK.getActionName());
        Response response = dtsService.execute(request);
        System.out.println(response.getCode());
        System.out.println(response.getModule());
    }
}
