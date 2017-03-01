package comment;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ AddItemCommentTest.class, DeleteItemCommentTest.class, QueryItemCommentTest.class })
public class ZItemCommentAllTest {

}
