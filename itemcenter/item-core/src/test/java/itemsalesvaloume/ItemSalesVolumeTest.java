package itemsalesvaloume;

import com.google.common.collect.Lists;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.ItemSalesVolumeManager;
import com.mockuai.tradecenter.common.domain.OrderItemDTO;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yindingyu on 16/2/19.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class ItemSalesVolumeTest {

    @Resource
    private ItemSalesVolumeManager itemSalesVolumeManager;


    @Test
    public void test001() {

        List<OrderItemDTO> orderItemDTOList = Lists.newArrayList();

        OrderItemDTO orderItemDTO = new OrderItemDTO();
        orderItemDTO.setItemId(101026L);
        orderItemDTO.setSellerId(38699L);
        orderItemDTO.setBizCode("mockuai_demo");
        orderItemDTO.setNumber(3);

        orderItemDTOList.add(orderItemDTO);
        try {
            itemSalesVolumeManager.updateItemSalesVolume(orderItemDTOList);

            System.currentTimeMillis();
        } catch (ItemException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
