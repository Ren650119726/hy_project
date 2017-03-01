package globalproperty;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ AddGlobalPropertyTest.class, DeleteGlobalPropertyTest.class, GetGlobalPropertyTest.class,
		QueryGlobalPropertyTest.class, UpdateGlobalPropertyTest.class })
public class ZGlobalPropertyAllTest {

}
