package globalpropertyvalue;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ AddGlobalPropertyValueTest.class, DeleteGlobalPropertyValueTest.class,
		GetGlobalPropertyValueTest.class, QueryGlobalPropertyValueTest.class, UpdateGlobalPropertyValueTest.class })
public class ZGlobalPropertyValueAllTest {

}
