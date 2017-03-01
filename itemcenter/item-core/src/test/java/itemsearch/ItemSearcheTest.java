package itemsearch;

import com.google.common.collect.Lists;
import com.mockuai.itemcenter.common.api.BaseRequest;
import com.mockuai.itemcenter.common.api.ItemService;
import com.mockuai.itemcenter.common.api.Request;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.domain.dto.ItemCategoryDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemSearchDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemSearchResultDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemCategoryQTO;
import com.mockuai.itemcenter.common.domain.qto.ItemSearchQTO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.ItemCategoryManager;
import com.mockuai.itemcenter.core.manager.ItemManager;
import com.mockuai.itemcenter.core.manager.ItemSalesVolumeManager;
import com.mockuai.itemcenter.core.manager.ItemSearchManager;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import util.AppKeyEnum;
import util.RequestFactory;

import javax.annotation.Resource;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Created by yindingyu on 15/9/24.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class ItemSearcheTest {

    @Resource
    private ItemService itemService;


    @Resource
    private ItemSearchManager itemSearchManager;

    @Resource
    private ItemCategoryManager itemCategoryManager;

    @Resource
    private ItemSalesVolumeManager itemSalesVolumeManager;

    @Resource
    private ItemManager itemManager;

    private final  static String TEST_BIZCODE= "mockuai_demo";


    private final static String APP_KEY = "af7433eda4883d7139e2934dd8d035f1";

    /**
     * 添加只有通用模板的运费模板
     */

    @Test
    public void test01(){

        Request request = RequestFactory.newRequest(AppKeyEnum.HAIYUN, ActionEnum.SEARCH_ITEM);

        ItemSearchQTO itemSearchQTO = new ItemSearchQTO();
        itemSearchQTO.setOrderBy(1);
        request.setParam("itemSearchQTO",itemSearchQTO);



        request.setCommand(ActionEnum.SEARCH_ITEM.getActionName());
        //request.setCommand(ActionEnum.QUERY_FREIGHT_TEMPLATE_ACTION.getActionName());
        Response<List<ItemSearchDTO>> response = itemService.execute(request);



        int code = response.getCode();

        assertThat(code,is(10000));

    }

    @Test
    public void test044() {

        Request request = new BaseRequest();
        request.setParam("appKey", APP_KEY);

        ItemSearchQTO itemSearchQTO = new ItemSearchQTO();
        itemSearchQTO.setBizCode("mockuai_demo");
        itemSearchQTO.setItemUids(Lists.newArrayList("38699_101497", "38699_101498"));
        request.setParam("itemSearchQTO",itemSearchQTO);



        request.setCommand(ActionEnum.SEARCH_ITEM.getActionName());
        //request.setCommand(ActionEnum.QUERY_FREIGHT_TEMPLATE_ACTION.getActionName());
        Response<List<ItemSearchDTO>> response = itemService.execute(request);



        int code = response.getCode();

        assertThat(code,is(10000));

    }




    @Test
    public void test02(){


        try {
            boolean result = itemSearchManager.updateItemSalesVolume(38699L,101060L,2,"mockuai_demo");

            assertThat(result,is(true));
        } catch (ItemException e) {
            e.printStackTrace();
        }



    }



    @Test
    public void test03(){

//        ItemSearchDTO itemSearchDTO = new ItemSearchDTO();
//
//        itemSearchDTO.setItemUid("38699_101081");
//
//        itemSearchDTO.setBizCode("mockuai_demo");
//
//        try {
//            Long result = itemSearchManager.fixItemSalesVolume(itemSearchDTO);
//
//            assertThat(result,is(100L));
//        } catch (ItemException e) {
//            e.printStackTrace();
//        }
    }


    @Test
    public void test04() {

        Request request = RequestFactory.newRequest(AppKeyEnum.MOCKUAI_DEMO, ActionEnum.GET_ITEM);

        request.setParam("appKey", "d71e02210598f6d73ce3e6f0cc4314e7");
        request.setParam("supplierId", 39381);
        request.setParam("id", 102963L);

        Response<ItemDTO> response = itemService.execute(request);

        ItemDTO itemDTO = response.getModule();

        for (int i = 0; i < 600; i++) {


            try {
                itemSearchManager.setItemIndex(itemDTO);
            } catch (ItemException e) {
                e.printStackTrace();
            }

            itemDTO.setId(itemDTO.getId() + 1);
        }

        System.currentTimeMillis();
    }

    @Test
    public void test05() {

//        try {
//
////            ItemCategoryQTO itemCategoryQTO = new ItemCategoryQTO();
////            itemCategoryQTO.setBizCode("mockuai_demo");
////            itemCategoryQTO.setCateLevel(2);
////
////            List<ItemCategoryDTO> itemCategoryDTOList = itemCategoryManager.queryItemCategory(itemCategoryQTO);
////
////            for(ItemCategoryDTO itemCategoryDTO : itemCategoryDTOList){
////                itemSearchManager.updateItemDirectCommission(itemCategoryDTO.getId(), "mockuai_demo", 0.33);
////            }
//
//           // itemSearchManager.updateItemDirectCommission(3030L, "mockuai_demo", 0.05);
//
//        } catch (ItemException e) {
//            e.printStackTrace();
////        }

        System.currentTimeMillis();
    }

    @Test
    public void test06() {

        try {


            ItemDTO itemDTO = itemManager.getItem(101482L,38699L,"mockuai_demo");

            itemSearchManager.setItemIndex(itemDTO);

        } catch (ItemException e) {
            e.printStackTrace();
        }

        System.currentTimeMillis();
    }

    @Test
    public void test07() {

        try {

            ItemSearchQTO itemSearchQTO = new ItemSearchQTO();
            //itemSearchQTO.setKeyword("一叶子");
            itemSearchQTO.setOrderBy(0);


            Request request = RequestFactory.newRequest(AppKeyEnum.HAIYUN,ActionEnum.SEARCH_ITEM);

            request.setParam("itemSearchQTO",itemSearchQTO);
            Response response = itemService.execute(request);


            System.currentTimeMillis();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.currentTimeMillis();
    }
}
