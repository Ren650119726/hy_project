package itembrand;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ AddItemBrandTest.class, DeleteItemBrandTest.class, GetItemBrandTest.class,
		QueryItemBrandTest.class, UpdateItemBrandTest.class })
public class ZItemBrandAllTest {

}
