package itemsuit;

import com.mockuai.itemcenter.common.constant.DBConst;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemQTO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.ItemSuitManager;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yindingyu on 15/12/10.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class SuitTest {

    @Resource
    private ItemSuitManager itemSuitManager;


    @Test
    public void test01(){

        ItemQTO itemQTO = new ItemQTO();
        itemQTO.setBizCode("mockuai_demo");
        itemQTO.setSellerId(38699L);
        itemQTO.setItemType(DBConst.SUIT_ITEM.getCode());

        try {
            List<ItemDTO> itemDTOList = itemSuitManager.querySuit(itemQTO);

            System.currentTimeMillis();
        } catch (ItemException e) {
            e.printStackTrace();
        }

        System.currentTimeMillis();
    }
}
