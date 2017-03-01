package item;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.domain.dto.ItemSearchDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemSearchQTO;
import com.mockuai.itemcenter.core.manager.ItemSearchManager;
import com.mockuai.itemcenter.core.manager.impl.ItemSearchManagerImpl;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zengzhangqiang on 5/5/15.
 */
public class ItemSearchManagerTest {
    private ItemSearchManager itemSearchManager;

    public ItemSearchManagerTest(){
        itemSearchManager = new ItemSearchManagerImpl();
        ((ItemSearchManagerImpl)itemSearchManager).setSearchServerUrl("http://localhost:8080/solr/item_search");
        ((ItemSearchManagerImpl) itemSearchManager).init();
    }

//    @Test
//    public void  test_addItemSearchDoc() throws Exception{
//        List<ItemSearchDTO> itemSearchDTOs = new ArrayList<ItemSearchDTO>();
//        for (int i = 1; i < 201; i++) {
//            ItemSearchDTO itemSearchDTO = new ItemSearchDTO();
//            itemSearchDTO.setItemUid(""+(i%20)+"_"+i);
//            itemSearchDTO.setItemName("itemTest"+(i%20)+" keyword"+(i%40));
//            itemSearchDTO.setSellerId((long)(i%20));
//            itemSearchDTO.setIconUrl("url_test");
//            itemSearchDTO.setCategoryId((long)(i%5));
//            itemSearchDTO.setBrandId((long)(i%10));
//            itemSearchDTO.setMarketPrice(10000L+i);
//            itemSearchDTO.setPromotionPrice(9000L+i);
//            itemSearchDTO.setWirelessPrice(8000L+i);
//            itemSearchDTOs.add(itemSearchDTO);
//        }
//        itemSearchManager.setItemIndex(itemSearchDTOs);
//    }

    public void test_searchByKeyword() throws Exception {}

    @Test
    public void test_searchByCategoryId() throws Exception {
//        ItemSearchQTO itemSearchQTO = new ItemSearchQTO();
//        itemSearchQTO.setCategoryId(1L);
//        ItemResponse<List<ItemSearchDTO>> itemResponse = itemSearchManager.searchItemIndex(itemSearchQTO);
////        Assert.assertEquals(40, itemSearchDTOs.size());
//        for(ItemSearchDTO itemSearchDTO: itemResponse.getModule()){
//            System.out.println("item_uid:"+itemSearchDTO.getItemUid()+", category_id:"+itemSearchDTO.getCategoryId());
//        }
    }

    public void test_searchByBrandId() throws Exception {}
}
