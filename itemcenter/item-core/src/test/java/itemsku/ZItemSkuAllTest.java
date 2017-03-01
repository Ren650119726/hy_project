package itemsku;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ AddItemSkuTest.class, DecreaseItemSkuStockTest.class, DeleteItemSkuTest.class,
		GetItemSkuTest.class, IncreaseItemSkuStockTest.class, QueryItemSkuTest.class, UpdateItemSkuTest.class })
public class ZItemSkuAllTest {

}
