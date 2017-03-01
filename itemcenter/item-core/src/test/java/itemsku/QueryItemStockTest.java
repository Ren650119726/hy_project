package itemsku;

import com.mockuai.appcenter.common.util.JsonUtil;
import com.mockuai.itemcenter.common.domain.dto.ItemSkuDTO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.ItemSkuManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by duke on 16/1/29.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class QueryItemStockTest {
    @Resource
    private ItemSkuManager itemSkuManager;

    @Test
    public void test() throws ItemException {
        List<Long> itemIdList = new ArrayList<Long>();
        itemIdList.add(102708L);
        itemIdList.add(102718L);
        List<ItemSkuDTO> stocks = itemSkuManager.queryStock(itemIdList, "mockuai_demo");
        System.out.println(JsonUtil.toJson(stocks));
    }
}
