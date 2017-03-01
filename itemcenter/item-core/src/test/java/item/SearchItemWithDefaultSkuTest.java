package item;

import com.google.common.collect.Lists;
import com.mockuai.itemcenter.common.api.BaseRequest;
import com.mockuai.itemcenter.common.api.ItemService;
import com.mockuai.itemcenter.common.api.Request;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemQTO;
import com.mockuai.itemcenter.common.domain.qto.ItemSearchQTO;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.marketingcenter.common.constant.ItemType;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import util.AppKeyEnum;
import util.RequestFactory;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class SearchItemWithDefaultSkuTest {
    @Resource
    private ItemService itemService;

    private static final Long SELLER_ID = 38699L;
    private static final String APP_KEY = "af7433eda4883d7139e2934dd8d035f1";

    @Test
    /**
     * 正常查询
     */
    public void test001() {
        Request request = new BaseRequest();
        ItemQTO itemQTO = new ItemQTO();
        List list = Arrays.asList(new Long[]{101113L});
        itemQTO.setIdList(list);
        itemQTO.setSellerId(SELLER_ID);
        request.setParam("itemQTO", itemQTO);
        request.setCommand(ActionEnum.QUERY_ITEM.getActionName());
        request.setParam("appKey", APP_KEY);
        Response response = itemService.execute(request);
        System.out.println("**************************************");
        System.out.println("Model:" + response.getModule());
        System.out.println("message:" + response.getMessage());
        System.out.println("ResponseCode:" + response.getCode());
        System.out.println("TotalCount:" + response.getTotalCount());
        System.out.println("**************************************");
    }


    /**
     * itemQTO为空
     */
    public void test002() {
        Request request = new BaseRequest();
        request.setCommand(ActionEnum.QUERY_ITEM.getActionName());
        Response response = itemService.execute(request);
        System.out.println("**************************************");
        System.out.println("Model:" + response.getModule());
        System.out.println("message:" + response.getMessage());
        System.out.println("ResponseCode:" + response.getCode());
        System.out.println("TotalCount:" + response.getTotalCount());
        System.out.println("**************************************");
    }

    /**
     * 供应商ID为空
     */
    @Ignore
    public void test003() {
        Request request = new BaseRequest();
        ItemQTO itemQTO = new ItemQTO();
        itemQTO.setCurrentPage(1);
        itemQTO.setPageSize(3);
        itemQTO.setDeleteMark(0);
        List<Long> idList = new ArrayList<Long>();
        idList.add(37L);
        itemQTO.setIdList(idList);
        itemQTO.setSellerId(91L);
        request.setParam("itemQTO", itemQTO);
        request.setCommand(ActionEnum.QUERY_ITEM.getActionName());
        Response response = itemService.execute(request);
        System.out.println("**************************************");
        System.out.println("Model:" + response.getModule());
        System.out.println("message:" + response.getMessage());
        System.out.println("ResponseCode:" + response.getCode());
        System.out.println("TotalCount:" + response.getTotalCount());
        System.out.println("**************************************");
    }

    /**
     * 供应商ID为空
     */
    @Test
    public void test004() {
        Request request = new BaseRequest();
        ItemQTO itemQTO = new ItemQTO();
        itemQTO.setCurrentPage(1);
        itemQTO.setPageSize(3);
        itemQTO.setDeleteMark(0);
        itemQTO.setNeedPaging(true);
//		List<Long> idList = new ArrayList<Long>();
//		idList.add(91L);
//		itemQTO.setIdList(idList);
//		itemQTO.setGroupId(1L);
//		itemQTO.setId(1L);
        itemQTO.setSellerId(91L);
        request.setParam("itemQTO", itemQTO);
        request.setCommand(ActionEnum.QUERY_ITEM_GROUP_ACTION.getActionName());
        Response response = itemService.execute(request);
        System.out.println("**************************************");
        System.out.println("Model:" + response.getModule());
        System.out.println("message:" + response.getMessage());
        System.out.println("ResponseCode:" + response.getCode());
        System.out.println("TotalCount:" + response.getTotalCount());
        System.out.println("**************************************");
    }

    /**
     * 供应商ID为空
     */
    @Test
    public void test005() {
        Request request = new BaseRequest();
        ItemQTO itemQTO = new ItemQTO();
//		List<Long> idList = new ArrayList<Long>();
//		idList.add(91L);
//		itemQTO.setIdList(idList);
//		itemQTO.setGroupId(1L);
//		itemQTO.setId(1L);
        request.setParam("itemQTO", itemQTO);
        request.setCommand(ActionEnum.COUNT_TOTAL_ITEM_ACTION.getActionName());
        Response response = itemService.execute(request);
        System.out.println("**************************************");
        System.out.println("Model:" + response.getModule());
        System.out.println("message:" + response.getMessage());
        System.out.println("ResponseCode:" + response.getCode());
        System.out.println("TotalCount:" + response.getTotalCount());
        System.out.println("**************************************");
    }

    /**
     * itemQTO为空
     */
    @Test
    public void test007() {
        Request request = new BaseRequest();
        request.setCommand(ActionEnum.QUERY_ITEM.getActionName());

        ItemQTO itemQTO = new ItemQTO();
        itemQTO.setIdList(Lists.newArrayList(101505L));
        itemQTO.setSellerId(38699L);

        request.setParam("itemQTO", itemQTO);
        request.setParam("appKey", APP_KEY);
        Response response = itemService.execute(request);
        System.out.println("**************************************");
        System.out.println("Model:" + response.getModule());
        System.out.println("message:" + response.getMessage());
        System.out.println("ResponseCode:" + response.getCode());
        System.out.println("TotalCount:" + response.getTotalCount());
        System.out.println("**************************************");
    }

    @Test
    public void test008() {
        Request request = new BaseRequest();
        request.setCommand(ActionEnum.QUERY_ITEM.getActionName());

        ItemQTO itemQTO = new ItemQTO();
        itemQTO.setItemType(ItemType.GROUP_BUYING.getValue());
        itemQTO.setIdList(Lists.newArrayList(101506L, 101509L));
        itemQTO.setSellerId(0L);

        request.setParam("itemQTO", itemQTO);
        request.setParam("appKey", APP_KEY);
        request.setParam("needExtraInfo", "1");
        Response response = itemService.execute(request);
        System.out.println("**************************************");
        System.out.println("Model:" + response.getModule());
        System.out.println("message:" + response.getMessage());
        System.out.println("ResponseCode:" + response.getCode());
        System.out.println("TotalCount:" + response.getTotalCount());
        System.out.println("**************************************");
    }

    @Test
    public void test009() {
        Request request = new BaseRequest();
        request.setCommand(ActionEnum.QUERY_ITEM.getActionName());

        ItemQTO itemQTO = new ItemQTO();
        itemQTO.setItemType(ItemType.SECKILL.getValue());
        itemQTO.setIdList(Lists.newArrayList(101986L, 101985L));
        itemQTO.setSellerId(0L);

        request.setParam("itemQTO", itemQTO);
        request.setParam("appKey", APP_KEY);
        request.setParam("needExtraInfo", "1");
        Response response = itemService.execute(request);
        System.out.println("**************************************");
        System.out.println("Model:" + response.getModule());
        System.out.println("message:" + response.getMessage());
        System.out.println("ResponseCode:" + response.getCode());
        System.out.println("TotalCount:" + response.getTotalCount());
        System.out.println("**************************************");
    }

    @Test
    public void test0000() {
        Request request = RequestFactory.newRequest(AppKeyEnum.MOCKUAI_DEMO, ActionEnum.QUERY_ITEM_WITH_DEFAULT_SKU);

        ItemSearchQTO query = new ItemSearchQTO();
        query.setCount(20);
        query.setCategoryId(563L);

        request.setParam("itemSearchQTO", query);

        Response<List<ItemDTO>> response = itemService.execute(request);

        System.currentTimeMillis();
    }
}
