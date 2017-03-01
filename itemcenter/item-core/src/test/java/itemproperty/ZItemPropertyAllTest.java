package itemproperty;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ AddItemPropertyTest.class, DeleteItemPropertyTest.class, GetItemPropertyTest.class,
		QueryItemPropertyTest.class, UpdateItemPropertyTest.class })
public class ZItemPropertyAllTest {

}
