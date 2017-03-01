package itemsku;

import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.ItemSkuManager;
import com.mockuai.itemcenter.core.manager.StoreStockManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by duke on 16/1/29.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class FrozenItemTest {
    @Resource
    private ItemSkuManager itemSkuManager;
    @Resource
    private StoreStockManager storeStockManager;
    @Test
    public void test() throws ItemException {
        storeStockManager.increaseStoreNumAction(1L,2L,3L,"6562b5ddf0aed2aad8fe471ce2a2c8a0");
        //itemSkuManager.freezeItemSkuStock(102708L,128L,5,"hanshu");

    }
}
