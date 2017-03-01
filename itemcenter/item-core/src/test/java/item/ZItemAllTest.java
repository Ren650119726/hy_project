package item;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ AddItemTest.class, DeleteItemTest.class, GetItemTest.class, QueryItemTest.class,
		RemoveItemTest.class, UpdateItemTest.class })
public class ZItemAllTest {

}
