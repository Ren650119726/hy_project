package itemcomposite;

import com.google.common.collect.Lists;
import com.mockuai.itemcenter.common.constant.DBConst;
import com.mockuai.itemcenter.common.constant.ItemType;
import com.mockuai.itemcenter.common.domain.dto.CompositeItemDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemSkuQTO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.CompositeItemManager;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/*@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })*/
public class AddCompositeTest {
	//@Resource
	private CompositeItemManager compositeItemManager;
    private static final Logger log = LoggerFactory.getLogger(AddCompositeTest.class);

    @Test
    public void testQuery(){
        ItemSkuQTO itemSkuQTO = new ItemSkuQTO();
        itemSkuQTO.setIdList(Lists.newArrayList(21937L));
        compositeItemManager.queryCompositeItemByItemSkuQTO(itemSkuQTO);
    }

@Test
    public void testType(){
    ItemDTO itemDTO = new ItemDTO();
    itemDTO.setItemType(22);
    if (itemDTO.getItemType() == null || itemDTO.getItemType() != DBConst.NORMAL_ITEM.getCode()

            ) {
        log.info("type:{}",ItemType.COMPOSITE_ITEM.getType());
        log.info("不可加入索引:{}",itemDTO.getItemName());
    }
    }

    @Test
    public void testAddComposite() throws ItemException {
        List<CompositeItemDTO> compositeItems = new ArrayList<CompositeItemDTO>();
        CompositeItemDTO compositeItem1 = new CompositeItemDTO();
        compositeItem1.setItemId(1L);
        compositeItem1.setNum(1);
        compositeItem1.setSubItemId(221L);
        compositeItem1.setSubSkuId(12218L);
        compositeItems.add(compositeItem1);
        CompositeItemDTO compositeItem2 = new CompositeItemDTO();
        compositeItem2.setItemId(3L);
        compositeItem2.setNum(1);
        compositeItem2.setSubItemId(225L);
        compositeItem2.setSubSkuId(12238L);
        compositeItems.add(compositeItem2);
        compositeItemManager.batchAddCompositeItem(compositeItems);
    }


}
