package itemspce;

import com.mockuai.itemcenter.common.api.BaseRequest;
import com.mockuai.itemcenter.common.api.ItemService;
import com.mockuai.itemcenter.common.api.Request;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.domain.dto.SpecDTO;
import com.mockuai.itemcenter.common.domain.qto.SpecQTO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.SpecManager;
import com.mockuai.itemcenter.core.service.action.itemspec.AddSpecAction;
import com.mockuai.marketingcenter.common.domain.qto.PageQTO;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by yindingyu on 15/9/24.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class AddItemSpecTest {

    Logger logger = LoggerFactory.getLogger(AddItemSpecTest.class);
    @Resource
    private ItemService itemService;

    private final  static String BIZ_CODE= "mockuai_demo";

    private final static String APP_KEY = "6562b5ddf0aed2aad8fe471ce2a2c8a0";

    private final static Long SELLER_ID = 38699L;
    @Resource
    private SpecManager specManager;
    @Resource
    private AddSpecAction addSpecAction;

    /**
     * 添加增值服务
     */

    @Test
    public void test01() throws ItemException {
        SpecDTO specDO = new SpecDTO();
        specDO.setSpecName("保质期");
        specDO.setSortOrder(1);
        specManager.addSpec(specDO);
    }

    @Test
    public void testDelete() throws ItemException {
        SpecDTO specDO = new SpecDTO();
        specDO.setSpecName("保质期");
        specDO.setSortOrder(1);
        specManager.deleteSpec(3L);
    }


    @Test
    public void testUpdate() throws ItemException {
        SpecDTO specDO = new SpecDTO();
        specDO.setSpecName("生产日期");
        specDO.setSortOrder(1);
        specDO.setId(3L);
        specManager.updateSpec(specDO);
    }
    @Test
    public void testCount() throws ItemException {
        SpecQTO specQTO = new SpecQTO();
        specManager.countSpec(specQTO);
    }

    @Test
    public void testQuery() throws ItemException {
        SpecQTO specQTO = new SpecQTO();
        specQTO.setOffset(PageQTO.DEFAULT_OFFSET);
        specQTO.setPageSize(PageQTO.DEFAULT_COUNT);
        specManager.querySpec(specQTO);
    }

    @Test
    public void testAdd(){
        Request request = new BaseRequest();
        SpecDTO specDO = new SpecDTO();
        specDO.setSpecName("保质期");
        specDO.setSortOrder(2);
        request.setParam("specDTO",specDO);
        request.setParam("appKey",APP_KEY);
        request.setCommand(ActionEnum.ADD_SPEC                .getActionName());

        Response response =  itemService.execute(request);
        logger.info("response:{}",response.getCode());
    }
    @Test
    public void testUpdateService(){
        Request request = new BaseRequest();
        SpecDTO specDO = new SpecDTO();
        specDO.setSpecName("胸围");
        specDO.setSortOrder(2);
        specDO.setId(4L);
        request.setParam("specDTO",specDO);
        request.setParam("appKey",APP_KEY);
        request.setCommand(ActionEnum.UPDATE_SPEC                .getActionName());

        Response response =  itemService.execute(request);
        logger.info("response:{}",response.getCode());
    }

    @Test
    public void testQueryService(){
        Request request = new BaseRequest();
        SpecQTO specQTO = new SpecQTO();
        specQTO.setPageSize(PageQTO.DEFAULT_COUNT);
        specQTO.setOffset(PageQTO.DEFAULT_OFFSET);
        request.setParam("specQTO",specQTO);
        request.setParam("appKey",APP_KEY);
        request.setCommand(ActionEnum.QUERY_SPEC                .getActionName());
        Response response =  itemService.execute(request);
        logger.info("response:{}",response.getCode());
    }
    @Test
    public void testDeleteService(){
        Request request = new BaseRequest();

        request.setParam("id",4L);
        request.setParam("appKey",APP_KEY);
        request.setCommand(ActionEnum.DELETE_SPEC                .getActionName());
        Response response =  itemService.execute(request);
        logger.info("response:{}",response.getCode());
    }


    @Test
    public void testGetService(){
        Request request = new BaseRequest();
        request.setParam("id",3L);
        request.setParam("appKey",APP_KEY);
        request.setCommand(ActionEnum.GET_SPEC                .getActionName());
        Response response =  itemService.execute(request);
        logger.info("response:{}",response.getCode());
    }

}
