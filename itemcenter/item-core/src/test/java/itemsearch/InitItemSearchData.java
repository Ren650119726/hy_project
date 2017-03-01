package itemsearch;

import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemPropertyDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemPropertyQTO;
import com.mockuai.itemcenter.common.domain.qto.ItemQTO;
import com.mockuai.itemcenter.core.manager.ItemManager;
import com.mockuai.itemcenter.core.manager.ItemPropertyManager;
import com.mockuai.itemcenter.core.manager.ItemSearchManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zengzhangqiang on 6/8/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class InitItemSearchData {
    @Resource
    private ItemManager itemManager;
    @Resource
    private ItemSearchManager itemSearchManager;
    @Resource
    private ItemPropertyManager itemPropertyManager;

    @Test
    public void initSearchData() throws Exception{
        int count = 1000;

        ItemQTO itemQTO = new ItemQTO();
        itemQTO.setSellerId(91L);
        itemQTO.setOffset(0);
        itemQTO.setPageSize(count);
        itemQTO.setNeedPaging(true);
        //分页查询

        List<ItemDTO> itemDTOs = itemManager.queryItem(itemQTO);
        for(ItemDTO itemDTO: itemDTOs){
            //获取商品属性信息
            // 根据itemId查找该商品下的所有的基本属性
            ItemPropertyQTO itemPropertyQTO = new ItemPropertyQTO();
            itemPropertyQTO.setItemId(itemDTO.getId());
            itemPropertyQTO.setSellerId(itemDTO.getSellerId());
            itemPropertyQTO.setNeedPaging(null); //不需要分页
            List<ItemPropertyDTO> itemPropertyList = itemPropertyManager.queryItemProperty(itemPropertyQTO);
            itemDTO.setItemPropertyList(itemPropertyList);
        }

        System.out.println("offset:"+itemQTO.getOffset()+", size:"+itemDTOs.size());
        updateItemDoc(itemDTOs);

        int offset = 0;
        long totalCount = itemQTO.getTotalCount();
        while(itemDTOs.size() == count){
            offset += itemDTOs.size();
            itemQTO.setOffset(offset);
            itemQTO.setPageSize(count);
            itemQTO.setNeedPaging(true);
            itemQTO.setSellerId(91L);
            itemDTOs= itemManager.queryItem(itemQTO);

            for(ItemDTO itemDTO: itemDTOs){
                //获取商品属性信息
                // 根据itemId查找该商品下的所有的基本属性
                ItemPropertyQTO itemPropertyQTO = new ItemPropertyQTO();
                itemPropertyQTO.setItemId(itemDTO.getId());
                itemPropertyQTO.setSellerId(itemDTO.getSellerId());
                itemPropertyQTO.setNeedPaging(null); //不需要分页
                List<ItemPropertyDTO> itemPropertyList = itemPropertyManager.queryItemProperty(itemPropertyQTO);
                itemDTO.setItemPropertyList(itemPropertyList);
            }

            System.out.println("offset:"+itemQTO.getOffset()+", size:"+itemDTOs.size());

            updateItemDoc(itemDTOs);
        }
    }

    private void updateItemDoc(List<ItemDTO> itemDTOs) throws Exception{
        if(itemDTOs==null || itemDTOs.isEmpty()){
            return;
        }

        for(int i=0; i<itemDTOs.size(); i++){
            ItemDTO itemDTO = itemDTOs.get(i);
            System.out.println("update item index, itemId="+itemDTO.getId()+", i="+i+", totalCount="+itemDTOs.size());
            if(itemDTO.getItemStatus().intValue() == 4){
                itemSearchManager.setItemIndex(itemDTO);
            }else{
                itemSearchManager.deleteItemIndex(itemDTO.getId(), itemDTO.getSellerId());
            }
//            System.out.println("add item doc:"+itemDTO.getId());

        }
    }
}
