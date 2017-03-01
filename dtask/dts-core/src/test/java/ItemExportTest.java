import com.mockuai.dts.common.api.DtsService;
import com.mockuai.dts.common.api.action.DtsRequest;
import com.mockuai.dts.common.api.action.Request;
import com.mockuai.dts.common.api.action.Response;
import com.mockuai.dts.common.constant.ActionEnum;
import com.mockuai.dts.common.domain.ItemExportQTO;
import com.mockuai.dts.common.domain.OrderExportQTO;
import com.mockuai.dts.core.domain.ExportTaskDO;
import com.mockuai.dts.core.exception.DtsException;
import com.mockuai.dts.core.manager.ItemExportManager;
import com.mockuai.dts.core.service.ExportTask;
import com.mockuai.dts.core.util.JsonUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class ItemExportTest {

	@Resource
	private DtsService dtsService;

	@Resource
	private ItemExportManager itemExportManager;

	@Test
	public void test(){
		Request request = new DtsRequest();
		request.setCommand(ActionEnum.EXPORT_ITEM.getActionName());
		System.out.println("begin addOrderComment");

		ItemExportQTO qto = new ItemExportQTO();
		qto.setSellerId(38669l);
		request.setParam("itemExportQTO", qto);
	
		request.setParam("appKey", "3bc25302234640259fadea047cb7c7d3");
		
		Response response = dtsService.execute(request);
		System.out.println("response>>>>>>>>>>>>>>>>>>>>>"+ JsonUtil.toJson(response));
	}

    @Test
    public void test01(){

        ItemExportQTO qto = new ItemExportQTO();
        qto.setSellerId(38699l);

        ExportTaskDO exportTaskDO = new ExportTaskDO();
        exportTaskDO.setBizCode("mockuai_demo");
        exportTaskDO.setAppKey("5b036edd2fe8730db1983368a122fb45");

        try {
            itemExportManager.exportItems(qto, exportTaskDO);
        } catch (DtsException e) {
            e.printStackTrace();
        }


    }
}